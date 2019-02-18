package com.galaxyschool.view;

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

public class WelcomeMenu extends Application {

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

        Parent root = FXMLLoader.load(ClassLoader.getSystemClassLoader().getResource("./WelcomeMenu.fxml"));

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

    public void showStudentPanel(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(ClassLoader.getSystemClassLoader().getResource("./WelcomeMenu.fxml"));

        Scene scene = new Scene(root);

        Stage studentStage = new Stage();

        studentStage.setTitle("Student Section");
        studentStage.setScene(scene);
        studentStage.show();

        WelcomeMenu.stage.hide();
    }

    public void showTeacherPanel(ActionEvent actionEvent) throws IOException {
        new TeacherPanel().start(new Stage());

        WelcomeMenu.stage.hide();
    }

    public void hideWindow(MouseEvent mouseEvent) {
        WelcomeMenu.stage.setIconified(true);
    }

    public void closeWindow(MouseEvent mouseEvent) {
        WelcomeMenu.stage.hide();
    }
}
