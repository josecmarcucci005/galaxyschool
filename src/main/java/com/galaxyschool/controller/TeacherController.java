package com.galaxyschool.controller;

import com.galaxyschool.db.ExamDao;
import com.galaxyschool.model.Exam;
import com.galaxyschool.view.TeacherPanel;
import com.galaxyschool.view.UpdateExam;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconName;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TeacherController implements Initializable {
    
    private static Stage stage;
    
    @FXML
    private ListView examList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        //Populating exam list view
        examList.setCellFactory(new Callback<ListView<Exam>, ListCell>() {
            @Override
            public ListCell call(ListView<Exam> param) {
                return new ButtonListExamCell(220, "Edit", FontAwesomeIconName.EDIT);
            }
        });
        try {
            examList.setItems(FXCollections.observableList(Exam.getExams()));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    
    public void hideWindow(MouseEvent mouseEvent) {
        stage.setIconified(true);
    }

    public void closeWindow(MouseEvent mouseEvent) {
        stage.hide();
    }

    public void showTeacherPanel(String examName) throws Exception {
        new UpdateExam(examName).start(new Stage());
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    class ButtonListExamCell extends ListCell<Exam> {
        
        long maxWidth;
        String toolTipText;
        EventHandler eventHandler;
        FontAwesomeIconName icon;

        ButtonListExamCell(long maxWidth, String toolTipText, FontAwesomeIconName icon) {
            this.maxWidth = maxWidth;
            this.toolTipText = toolTipText;
            this.eventHandler = eventHandler;
            this.icon = icon;
        }
        
        @Override
        public void updateItem(Exam obj, boolean empty) {
            super.updateItem(obj, empty);
            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                HBox hbox = new HBox();

                Label label = new Label(obj.getName());
                label.setWrapText(true);
                label.setMaxWidth(maxWidth);
                
                Pane pane = new Pane();

                HBox.setHgrow(pane, Priority.ALWAYS);

                FontAwesomeIcon saveIcon = new FontAwesomeIcon();
                saveIcon.setGlyphStyle("-fx-fill:#fff");
                saveIcon.setIcon(icon);
                saveIcon.setSize("1.5em");

                Button butt = new Button();
                butt.setGraphic(saveIcon);
                butt.setStyle("-fx-background-color: #00A2D3");

                butt.setOnAction(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent event) {
                        System.out.println("Clicking");
                        try {
                            showTeacherPanel(obj.getName());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

                //Tooltip tooltip = new Tooltip();
                //tooltip.setText(toolTipText);
                //butt.setTooltip(tooltip);
                hbox.getChildren().addAll(label, pane, butt);

                setGraphic(hbox);
            }
        }
    }

    
}
