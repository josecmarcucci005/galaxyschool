package com.galaxyschool.controller;

import com.galaxyschool.model.Exam;
import com.galaxyschool.view.StudentExamSession;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class WelcomeStudentController extends GalaxyController {

    private static Stage stage;

    @FXML
    private ComboBox<String> ageComboBox;
    @FXML
    private ComboBox<Exam> examComboBox;

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
        examComboBox.setEditable(false);

        examComboBox.setCellFactory(new Callback<ListView<Exam>, ListCell<Exam>>() {
            @Override
            public ListCell<Exam> call(ListView<Exam> param) {
                return new ListCell<Exam>() {
                    @Override
                    protected void updateItem(Exam obj, boolean empty) {
                        super.updateItem(obj, empty);
                        if (obj == null || empty) {
                            setGraphic(null);
                        } else {
                            setText(obj.getName());
                        }
                    }
                } ;
            }
        });

        examComboBox.setConverter(new StringConverter<Exam>() {

            @Override
            public String toString(Exam object) {
                if (object != null) {
                    return object.getName();
                }
                return "";
            }

            @Override
            public Exam fromString(String string) {
                return examComboBox.getItems().stream().filter(ap ->
                        ap.getName().equals(string)).findFirst().orElse(null);
            }
        });

        ageComboBox.setItems(FXCollections.observableList(Arrays.asList("10","11", "12", "13")));
        ageComboBox.setPromptText("Select your age...");

        ageComboBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                try {
                    if (newValue == null || newValue.isEmpty()) {
                        examComboBox.setEditable(false);

                        examComboBox.setItems(FXCollections.observableList(new ArrayList<Exam>()));
                    } else {
                        List<Exam> exams = Exam.getExamsByYear(newValue);

                        if (exams.isEmpty()) {
                            examComboBox.setPromptText("No exams...");
                            examComboBox.setEditable(false);
                        }
                        else {
                            examComboBox.setEditable(true);

                            examComboBox.setItems(FXCollections.observableList(exams));
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void showStudentExamSession(ActionEvent actionEvent) throws Exception {
        if (examComboBox.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning!");
            alert.setHeaderText(null);
            alert.setContentText("You must select an exam!" );
            alert.showAndWait();
        } else {
            StudentExamSession studentExamSession = new StudentExamSession();
            studentExamSession.setExam(examComboBox.getSelectionModel().getSelectedItem());
            studentExamSession.start(new Stage());

            stage.hide();
        }
    }
}
