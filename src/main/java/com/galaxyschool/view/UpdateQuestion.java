package com.galaxyschool.view;

import com.galaxyschool.controller.ExamController;
import com.galaxyschool.controller.QuestionController;
import com.galaxyschool.model.Exam;
import com.galaxyschool.model.Question;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class UpdateQuestion extends GalaxyApp {

    private static Stage stage;

    private Question question;
    private Exam parentExam;

    public UpdateQuestion() {
    }

    public UpdateQuestion(Question question, Exam parentExam) {
        this.question = question;
        this.parentExam = parentExam;
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

        QuestionController questionController = new QuestionController();
        questionController.setQuestion(question);
        questionController.setExamParent(parentExam);
        questionController.setStage(stage);

        initApp(stage, questionController, "./UpdateQuestion.fxml");
    }

}
