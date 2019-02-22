package com.galaxyschool.controller;

import com.galaxyschool.model.Exam;
import com.galaxyschool.model.Question;
import com.galaxyschool.view.TeacherPanel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class QuestionController extends GalaxyController {

    private static Stage stage;

    private Question question;
    private Exam examParent;

    @FXML
    private TextArea questionTxt;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        questionTxt.setWrapText(true);

        if (question != null) {
            questionTxt.setText(question.getText());
        }
    }

    public QuestionController() {

    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public void setExamParent(Exam examParent) {
        this.examParent = examParent;
    }

    @Override
    public void hideWindow(MouseEvent mouseEvent) {
        stage.setIconified(true);
    }

    @Override
    public void closeWindow(MouseEvent mouseEvent) {
        stage.hide();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void saveQuestion(ActionEvent actionEvent) throws IOException {
        if (questionTxt.getText() == null || questionTxt.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning!");
            alert.setHeaderText(null);
            alert.setContentText("You must enter the text field to be able to save the question!" );
            alert.showAndWait();

            return;
        }

        String operation;
        if (question == null) {
            question = new Question(questionTxt.getText(), new ArrayList<>());

            examParent.getQuestions().add(question);

            operation = "created";
        } else {
            question.setText(questionTxt.getText());

            operation = "updated";
        }

        Exam.update(examParent);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info!");
        alert.setHeaderText(null);
        alert.setContentText("The Question: was successfully "  + operation + "." );
        alert.showAndWait();

        stage.hide();

        TeacherPanel teacherPanel = new TeacherPanel();
        teacherPanel.setPredefineExam(examParent);

        teacherPanel.start(new Stage());
    }
}
