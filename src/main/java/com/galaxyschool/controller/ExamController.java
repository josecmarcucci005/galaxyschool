package com.galaxyschool.controller;

import com.galaxyschool.db.DuplicateExamException;
import com.galaxyschool.model.Exam;
import com.galaxyschool.model.Question;
import com.galaxyschool.view.TeacherPanel;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.ResourceBundle;

public class ExamController implements Initializable {

    private static Stage stage;

    private Exam exam;
    
    @FXML
    private ComboBox<String> levelCbx;
    @FXML
    private TextField examNmField;
    @FXML
    private TextField authorField;
    @FXML
    private DatePicker creationDateP;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        levelCbx.setItems(FXCollections.observableList(Arrays.asList("11", "12", "13")));

        if (exam != null) {
            examNmField.setText(exam.getName());
            examNmField.editableProperty().setValue(false);
            examNmField.setDisable(true);
            authorField.setText(exam.getAuthor());
            levelCbx.getSelectionModel().select(Long.toString(exam.getLevel()));
            creationDateP.setValue(exam.getCreationDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        } else {
            creationDateP.setValue(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        }

    }
    
    public void setExam(String examNm) throws Exception {
        if (examNm != null) {
            exam = Exam.getExamByName(examNm);
        }
    }

    public void hideWindow(MouseEvent mouseEvent) {
        stage.setIconified(true);
    }

    public void closeWindow(MouseEvent mouseEvent) {
        stage.hide();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void saveExam(ActionEvent actionEvent)  {

        if (creationDateP.getValue() == null || examNmField.getText() == null || examNmField.getText().isEmpty() ||
                levelCbx.getValue() == null || levelCbx.getValue().isEmpty() || authorField.getText() == null || authorField.getText().isEmpty() ) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning!");
            alert.setHeaderText(null);
            alert.setContentText("You must enter all fields to be able to save the answer!" );
            alert.showAndWait();

            return;
        }

        Date date = Date.from(creationDateP.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());

        try {
            String operation;
            if (exam == null) {
                exam = new Exam(examNmField.getText(), authorField.getText(), Long.valueOf(levelCbx.getValue()), date, new ArrayList<>());

                Exam.saveExam(exam);

                operation = "created";
            } else {
                Exam.update(exam);

                operation = "updated";
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Info!");
            alert.setHeaderText(null);
            alert.setContentText("The Exam: '" + exam.getName() + "' was successfully "  + operation + "." );
            alert.showAndWait();

            stage.hide();

            new TeacherPanel().start(new Stage());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DuplicateExamException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}
