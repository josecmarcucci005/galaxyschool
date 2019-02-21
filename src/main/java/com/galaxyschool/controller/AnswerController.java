package com.galaxyschool.controller;

import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AnswerController extends GalaxyController {

    private static Stage stage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @Override
    public void hideWindow(MouseEvent mouseEvent) {
        stage.setIconified(true);
    }

    @Override
    public void closeWindow(MouseEvent mouseEvent) {
        stage.hide();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
