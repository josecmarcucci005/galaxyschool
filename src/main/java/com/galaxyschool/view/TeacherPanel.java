package com.galaxyschool.view;

import com.galaxyschool.controller.TeacherController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


import java.io.IOException;

public class TeacherPanel extends Application {

    private static Stage stage;

    private double xOffset = 0;
    private double yOffset = 0;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        stage = primaryStage;

        primaryStage.initStyle(StageStyle.TRANSPARENT);
        
        FXMLLoader loader = new FXMLLoader();

        TeacherController teacherController = new TeacherController();
        loader.setController(teacherController);

        Parent root = loader.load(ClassLoader.getSystemClassLoader().getResource("./TeacherPanel.fxml"));
        teacherController.setStage(stage);

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
    
}
