package com.galaxyschool.controller;

import com.galaxyschool.model.Exam;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ExamController implements Initializable {

    private static Stage stage;
    
    @FXML
    private Exam exam;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
    public void setExam(String examNm) throws Exception {
        this.exam = Exam.getExamByName(examNm);
    }

    public void hideWindow(MouseEvent mouseEvent) {
        stage.setIconified(true);
    }

    public void closeWindow(MouseEvent mouseEvent) {
        stage.hide();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
