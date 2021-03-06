package com.galaxyschool.controller;

import com.galaxyschool.model.Answer;
import com.galaxyschool.model.Exam;
import com.galaxyschool.model.Question;
import com.galaxyschool.view.WelcomeStudent;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconName;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.net.URL;
import java.util.*;

public class StudentExamSessionController extends GalaxyController {

    private static Stage stage;
    private Exam exam;
    private int questionIdx = 1;
    private Question currentQuestion;
    private List<StudentAnswer> selectionAnswer = new ArrayList<>();

    @FXML
    private Label questionLabel;
    @FXML
    private TextArea questionTxt;
    @FXML
    private ListView answerListView;

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public Stage getStage() {
        return stage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        answerListView.setCellFactory(new Callback<ListView<Exam>, ListCell>() {
            @Override
            public ListCell call(ListView<Exam> param) {
                return new ButtonListAnswerCell();
            }
        });

        setQuestion();
    }

    private void setQuestion() {
        questionLabel.setText("Question " + questionIdx + " of " + exam.getQuestions().size() + ":");

        currentQuestion = exam.getQuestions().get(questionIdx - 1);
        questionTxt.setEditable(false);
        questionTxt.setText(currentQuestion.getText());

        selectionAnswer.clear();

        for (int i=0; i < currentQuestion.getAnswers().size(); i++) {
            StudentAnswer studentAnswer = new  StudentAnswer(currentQuestion.getAnswers().get(i), new CheckBox());
            selectionAnswer.add(studentAnswer);
        }

        answerListView.getItems().clear();

        answerListView.getItems().addAll(FXCollections.observableList(currentQuestion.getAnswers()));
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public void submit(ActionEvent event) throws Exception {
        List<Answer> incorrectAnswers = new ArrayList();
        List<Answer> correctAnswers = new ArrayList();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Well Done!");
        alert.setHeaderText(null);

        GridPane grid = new GridPane();
        grid.setVgap(10);

        int gridIdx = 0;
        for (StudentAnswer studentAnswer : selectionAnswer) {
            Label questionLb = new Label();
            questionLb.setMaxWidth(500);
            questionLb.setWrapText(true);

            TextArea explanationLb = new TextArea();
            explanationLb.setPrefWidth(400);
            explanationLb.setPrefHeight(100);
            explanationLb.setWrapText(true);
            explanationLb.setEditable(false);
            explanationLb.setText(studentAnswer.answer.getExplanation());

            FontAwesomeIcon awesomeIcon = new FontAwesomeIcon();
            awesomeIcon.setSize("1.5em");

            if (studentAnswer.answer.isCorrectAnswer() != studentAnswer.checkBox.isSelected()) {
                incorrectAnswers.add(studentAnswer.answer);

                awesomeIcon.setGlyphStyle("-fx-fill:" + BUTTON_COLOR_RED);
                awesomeIcon.setIcon(FontAwesomeIconName.MINUS_CIRCLE);
                awesomeIcon.setSize("1.5em");

                if (studentAnswer.answer.isCorrectAnswer() && !studentAnswer.checkBox.isSelected()) {
                    questionLb.setText("Wrong! The answer '" + studentAnswer.answer.getText() + "' is correct and it had to be selected!");
                } else {
                    questionLb.setText("Wrong! The answer '" + studentAnswer.answer.getText() + "' is not correct and shouldn't had to be selected!");
                }

            } else {
                correctAnswers.add(studentAnswer.answer);

                awesomeIcon.setGlyphStyle("-fx-fill:" + BUTTON_COLOR_GREEN);
                awesomeIcon.setIcon(FontAwesomeIconName.PLUS_CIRCLE);

                if (studentAnswer.answer.isCorrectAnswer()) {
                    questionLb.setText("Correct! The answer: '" + studentAnswer.answer.getText() + "' is correct and you select it!");
                } else {
                    questionLb.setText("Correct! The answer: '" + studentAnswer.answer.getText() + "' is not correct and you didn't select it!");
                }
            }

            HBox hboxR1 = new HBox();
            hboxR1.getChildren().addAll(awesomeIcon, questionLb);

            grid.addRow(gridIdx++, hboxR1);
            grid.addRow(gridIdx++, explanationLb);
        }

        ButtonType  tryAgainButt = new ButtonType("Try again?", ButtonBar.ButtonData.CANCEL_CLOSE);

        List<ButtonType> buttonTypes = new ArrayList<>();
        if (incorrectAnswers.size() > 0) {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setTitle("Wrong!");
            buttonTypes.add(tryAgainButt);
        }

        alert.getDialogPane().setContent(grid);

        ButtonType  continueButt = new ButtonType("Next Question", ButtonBar.ButtonData.OK_DONE);
        ButtonType  finishButt = new ButtonType("Finish", ButtonBar.ButtonData.FINISH);

        if (questionIdx == exam.getQuestions().size()) {
            buttonTypes.add(finishButt);
            alert.getButtonTypes().setAll(buttonTypes);
        } else {
            buttonTypes.add(continueButt);
            alert.getButtonTypes().setAll(buttonTypes);
        }

        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == continueButt) {
            questionIdx++;
            setQuestion();
        } else if (result.get() == finishButt) {
            goToWelcomeMenu();
        }
    }

    public void goToWelcomeMenu() throws Exception {
        getStage().hide();

        new WelcomeStudent().start(new Stage());
    }

    class ButtonListAnswerCell extends ListCell<Answer> {

        @Override
        public void updateItem(Answer obj, boolean empty) {
            super.updateItem(obj, empty);
            if (empty) {
                setText(null);
                setGraphic(null);
            } else {

                HBox hbox = new HBox();
                StudentAnswer studentAnswer = selectionAnswer.get(getIndex());

                Label label = new Label(studentAnswer.answer.getText());
                label.setWrapText(true);
                label.setMaxWidth(400);
                label.setAlignment(Pos.BASELINE_LEFT);

                hbox.getChildren().addAll(studentAnswer.getButton(), label);

                setGraphic(hbox);
            }
        }
    }

    class StudentAnswer {
        Answer answer;
        CheckBox checkBox;

        StudentAnswer(Answer answer, CheckBox checkBox) {
            this.answer = answer;
            this.checkBox = checkBox;
        }

        Node getButton() {
            return checkBox;
        }
    }
}
