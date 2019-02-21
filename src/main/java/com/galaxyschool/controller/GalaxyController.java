package com.galaxyschool.controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconName;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

public abstract class GalaxyController implements Initializable {

    protected String BUTTON_COLOR_BLUE = "#00A2D3";
    protected String BUTTON_COLOR_RED = "#ff4d4d";

    protected Button generateButtonWithFontAwesomeIcon(FontAwesomeIconName icon, String color) {
        FontAwesomeIcon awesomeIcon = new FontAwesomeIcon();
        awesomeIcon.setGlyphStyle("-fx-fill:#fff");
        awesomeIcon.setIcon(icon);
        awesomeIcon.setSize("1.5em");

        Button butt = new Button();
        butt.setGraphic(awesomeIcon);
        butt.setStyle("-fx-background-color: " + color);

        return butt;
    }

    protected HBox generateRowListWithButton(long labelMaxWidth, String labelTxt, Button... butts) {
        HBox hbox = new HBox();

        Label label = new Label(labelTxt);
        label.setWrapText(true);
        label.setMaxWidth(labelMaxWidth);

        Pane pane = new Pane();

        HBox.setHgrow(pane, Priority.ALWAYS);

        hbox.getChildren().addAll(label, pane);
        hbox.getChildren().addAll(butts);

        return hbox;
    }

    public abstract void hideWindow(MouseEvent mouseEvent);

    public abstract void closeWindow(MouseEvent mouseEvent);

    public abstract void setStage(Stage stage);

}
