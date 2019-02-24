package com.galaxyschool.view;

import com.galaxyschool.controller.WelcomeStudentController;
import javafx.stage.Stage;

public class WelcomeStudent extends GalaxyApp {

    private static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        if (stage != null) {
            stage.hide();
        }

        stage = primaryStage;

        WelcomeStudentController welcomeStudentController = new WelcomeStudentController();
        welcomeStudentController.setStage(stage);

        initApp(stage, welcomeStudentController, "./WelcomeStudent.fxml");
    }
}
