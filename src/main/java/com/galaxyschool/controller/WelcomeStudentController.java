package com.galaxyschool.controller;

import com.galaxyschool.db.ExamDao;
import com.galaxyschool.model.Exam;
import com.galaxyschool.view.StudentExamSession;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class WelcomeStudentController extends GalaxyController {

    private static Stage stage;

    @FXML
    private ListView<Exam> examList;


    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public Stage getStage() {
        return stage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Populating exam list view
        examList.setCellFactory(new Callback<ListView<Exam>, ListCell<Exam>>() {
            @Override
            public ListCell<Exam> call(ListView<Exam> param) {
                ListCell<Exam> cell = new ListCell<Exam>() {

                    @Override
                    public void updateItem(Exam obj, boolean empty) {
                        super.updateItem(obj, empty);
                        if (empty) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            setText(obj.getName());
                            setGraphic(null);
                        }
                    }
                };
                return cell;
            }
        });

        try {
            ExamDao examDao = new ExamDao(new File(System.getProperty("user.home") + "/galaxyschool/studentExams.json"));

            List<Exam> exams = examDao.getAll();

            if (exams.isEmpty()) {
                Label placeholder = new Label("Currently you don't have any exam. Please import an exam...");
                placeholder.setMaxWidth(300);
                placeholder.setWrapText(true);

                examList.setPlaceholder(placeholder);
            } else {
                Collections.sort(exams);
                examList.setItems(FXCollections.observableList(exams));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showStudentExamSession(ActionEvent actionEvent) throws Exception {
        if (examList.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning!");
            alert.setHeaderText(null);
            alert.setContentText("You must select an exam!" );
            alert.showAndWait();
        } else {
            StudentExamSession studentExamSession = new StudentExamSession();
            studentExamSession.setExam(examList.getSelectionModel().getSelectedItem());
            studentExamSession.start(new Stage());

            stage.hide();
        }
    }

    public void importExam(ActionEvent event) throws Exception {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json");
        fileChooser.getExtensionFilters().add(extFilter);

        fileChooser.setTitle("Open Exams File");

        ExamDao examDao = new ExamDao(new File(System.getProperty("user.home") + "/galaxyschool/studentExams.json"));

        File file = fileChooser.showOpenDialog(stage);

        try {
            if (file != null) {
                examDao.importAndLoadExams(file);

                examList.getItems().clear();

                List<Exam> exams = examDao.getAll();
                Collections.sort(exams);
                examList.setItems(FXCollections.observableList(exams));

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("File Imported!");
                alert.setHeaderText(null);
                alert.setContentText("The file: '" + file.getAbsolutePath() + "'  was successfully imported!");
                alert.showAndWait();
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText(null);
            alert.setContentText("There was an error when importing the file: '" + file.getAbsolutePath() + "', the error was: \r\n" + e.getMessage() );
            alert.showAndWait();
        }
    }
}
