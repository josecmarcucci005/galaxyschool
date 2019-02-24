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

public class WelcomeMenu extends GalaxyApp {

    private static Stage stage;

    private double xOffset = 0;
    private double yOffset = 0;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        if (stage != null) {
            stage.hide();
        }

        stage = primaryStage;
        stage.setResizable(false);

        primaryStage.initStyle(StageStyle.TRANSPARENT);

        Parent root = FXMLLoader.load(ClassLoader.getSystemClassLoader().getResource("./WelcomeMenu.fxml"));

        initWindowButtons(root, stage);

        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void showStudentPanel(ActionEvent actionEvent) throws Exception {
        new WelcomeStudent().start(new Stage());

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
