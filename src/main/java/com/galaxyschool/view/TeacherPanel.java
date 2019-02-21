package com.galaxyschool.view;

import com.galaxyschool.controller.TeacherController;
import com.galaxyschool.model.Exam;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


import java.io.IOException;

public class TeacherPanel extends GalaxyApp {

    private static Stage stage;

    private Exam predefineExam;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        if (stage != null) {
            stage.hide();
        }

        stage = primaryStage;

        primaryStage.initStyle(StageStyle.TRANSPARENT);
        
        FXMLLoader loader = new FXMLLoader(ClassLoader.getSystemClassLoader().getResource("./TeacherPanel.fxml"));

        TeacherController teacherController = new TeacherController();
        teacherController.setPredefineExam(predefineExam);
        teacherController.setStage(stage);

        loader.setController(teacherController);

        Parent root = loader.load();

        initWindowButtons(root, stage);

        Scene scene = new Scene(root);
        scene.getStylesheets().setAll(ClassLoader.getSystemClassLoader().getResource("./style.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public void setPredefineExam(Exam predefineExam) {
        this.predefineExam = predefineExam;
    }
}
