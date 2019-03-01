package com.galaxyschool.view;

import com.galaxyschool.controller.ExamController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class UpdateExam extends GalaxyApp {

    private static Stage stage;

    private String examName;

    public UpdateExam() {
    }

    public UpdateExam(String examName) {
        this.examName = examName;
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

        ExamController examController = new ExamController();
        examController.setExam(examName);
        examController.setStage(stage);

        initApp(stage, examController, "UpdateExam.fxml");
    }

}
