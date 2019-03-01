package com.galaxyschool.view;

import com.galaxyschool.controller.GalaxyController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public abstract class GalaxyApp extends Application {

    private double xOffset = 0;
    private double yOffset = 0;

    private Scene scene;

    protected void initWindowButtons(Parent root, Stage stage) {
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
    }

    protected void initApp(Stage stage, GalaxyController controller, String fxmlFilePath) throws IOException {
        stage.setResizable(false);

        stage.initStyle(StageStyle.TRANSPARENT);

        FXMLLoader loader = new FXMLLoader(ClassLoader.getSystemClassLoader().getResource(fxmlFilePath));
        loader.setController(controller);

        Parent root = loader.load();

        initWindowButtons(root, stage);

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();

        this.scene = scene;
    }

    public Scene getScene() {
        return scene;
    }

}
