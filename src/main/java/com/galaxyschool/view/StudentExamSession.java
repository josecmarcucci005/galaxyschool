package com.galaxyschool.view;

import com.galaxyschool.controller.StudentExamSessionController;
import com.galaxyschool.controller.WelcomeStudentController;
import com.galaxyschool.model.Exam;
import javafx.stage.Stage;

public class StudentExamSession extends GalaxyApp {

    private static Stage stage;
    private Exam exam;

    @Override
    public void start(Stage primaryStage) throws Exception {
        if (stage != null) {
            stage.hide();
        }

        stage = primaryStage;

        StudentExamSessionController studentExamSessionController = new StudentExamSessionController();
        studentExamSessionController.setStage(stage);
        studentExamSessionController.setExam(exam);

        initApp(stage, studentExamSessionController, "StudentExamSession.fxml");
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }
}
