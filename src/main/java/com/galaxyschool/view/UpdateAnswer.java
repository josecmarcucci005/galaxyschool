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

    private Question question;
    private Exam parentExam;
    private Answer answer;

    public UpdateAnswer() {
    }

    public UpdateAnswer(Question question, Exam parentExam, Answer answer) {
        this.question = question;
        this.parentExam = parentExam;
        this.answer = answer;
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

        primaryStage.initStyle(StageStyle.TRANSPARENT);

        AnswerController answerController = new AnswerController();
        answerController.setParentExam(parentExam);
        answerController.setParentQuestion(question);
        answerController.setAnswer(answer);
        answerController.setStage(stage);

        FXMLLoader loader = new FXMLLoader(ClassLoader.getSystemClassLoader().getResource("./UpdateAnswer.fxml"));
        loader.setController(answerController);

        Parent root = loader.load();

        initWindowButtons(root, stage);

        Scene scene = new Scene(root);
        scene.getStylesheets().setAll(ClassLoader.getSystemClassLoader().getResource("./style.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
