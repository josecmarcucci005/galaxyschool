package com.galaxyschool.view;

import com.galaxyschool.controller.AnswerController;
import com.galaxyschool.controller.QuestionController;
import com.galaxyschool.model.Answer;
import com.galaxyschool.model.Exam;
import com.galaxyschool.model.Question;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class UpdateAnswer extends GalaxyApp {

    private static Stage stage;

    private Integer questionParentIdx;
    private Exam parentExam;
    private Answer answer;
    private boolean isReadOnly = false;

    public UpdateAnswer() {
    }

    public UpdateAnswer(Integer questionParentIdx, Exam parentExam, Answer answer, boolean isReadOnly) {
        this.questionParentIdx = questionParentIdx;
        this.parentExam = parentExam;
        this.answer = answer;
        this.isReadOnly = isReadOnly;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        if (stage != null) {
            stage.hide();
        }

        stage = primaryStage;

        AnswerController answerController = new AnswerController();
        answerController.setParentReference(parentExam, questionParentIdx);
        answerController.setAnswer(answer);
        answerController.setStage(stage);
        answerController.setReadyOnly(isReadOnly);

        initApp(stage, answerController, "UpdateAnswer.fxml");
    }

}
