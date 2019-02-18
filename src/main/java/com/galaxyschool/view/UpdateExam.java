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

public class UpdateExam extends Application {

    private static Stage stage;

    private double xOffset = 0;
    private double yOffset = 0;
    private String examName;
    
    public UpdateExam(String examName) {
        this.examName = examName;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;

        primaryStage.initStyle(StageStyle.TRANSPARENT);

        ExamController examController = new ExamController();
        examController.setExam(examName);
        examController.setStage(stage);

        FXMLLoader loader = new FXMLLoader();
        loader.setController(examController);

        Parent root = loader.load(ClassLoader.getSystemClassLoader().getResource("./UpdateExam.fxml"));

        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            }
        });

        Scene scene = new Scene(root);
        scene.getStylesheets().setAll(ClassLoader.getSystemClassLoader().getResource("./style.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public void hideWindow(MouseEvent mouseEvent) {
        UpdateExam.stage.setIconified(true);
    }

    public void closeWindow(MouseEvent mouseEvent) {
        UpdateExam.stage.hide();
    }
}
