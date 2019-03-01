package com.galaxyschool.controller;

import com.galaxyschool.model.Answer;
import com.galaxyschool.model.Exam;
import com.galaxyschool.model.Question;
import com.galaxyschool.view.TeacherPanel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class AnswerController extends GalaxyController {

    private static Stage stage;

    private Question parentQuestion;
    private Exam parentExam;
    private Answer answer;
    private Integer questionParentIdx;
    private boolean isReadyOnly;

    @FXML
    private TextArea answerTxt;
    @FXML
    private TextArea explanationTxt;
    @FXML
    private RadioButton isCorrectRB;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        answerTxt.setWrapText(true);
        explanationTxt.setWrapText(true);

        if (answer != null) {
            answerTxt.setText(answer.getText());
            explanationTxt.setText(answer.getExplanation());
            isCorrectRB.setSelected(answer.isCorrectAnswer());
        }
        if (isReadyOnly) {
            answerTxt.setEditable(false);
            explanationTxt.setEditable(false);
            isCorrectRB.setDisable(true);
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setReadyOnly(boolean readyOnly) {
        isReadyOnly = readyOnly;
    }

    @Override
    public Stage getStage() {
        return stage;
    }

    public void setParentReference(Exam parentExam, Integer questionParentIdx) {
        this.parentExam = parentExam;
        this.questionParentIdx = questionParentIdx;

        if (questionParentIdx != null) {
            this.parentQuestion = parentExam.getQuestions().get(questionParentIdx);
        }
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    public void saveAnswer(ActionEvent actionEvent) throws IOException {
        if (answerTxt.getText() == null || answerTxt.getText().isEmpty() || explanationTxt.getText() == null || explanationTxt.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning!");
            alert.setHeaderText(null);
            alert.setContentText("You must enter all fields to be able to save the answer!" );
            alert.showAndWait();

            return;
        }

        String operation;
        if (answer == null) {
            answer = new Answer(answerTxt.getText(), isCorrectRB.isSelected(), explanationTxt.getText());

            parentQuestion.getAnswers().add(answer);

            operation = "created";
        } else {
            answer.setText(answerTxt.getText());
            answer.setExplanation(explanationTxt.getText());
            answer.setCorrectAnswer(isCorrectRB.isSelected());

            System.out.println("At not is null");

            operation = "updated";
        }

        Exam.update(parentExam);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info!");
        alert.setHeaderText(null);
        alert.setContentText("The Answer: was successfully "  + operation + "." );
        alert.showAndWait();

        stage.hide();

        TeacherPanel teacherPanel = new TeacherPanel();
        teacherPanel.setPredefineExam(parentExam);
        teacherPanel.setQuestionParentIdx(questionParentIdx);

        teacherPanel.start(new Stage());
    }
}

