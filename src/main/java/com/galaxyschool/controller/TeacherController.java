package com.galaxyschool.controller;

import com.galaxyschool.model.Answer;
import com.galaxyschool.model.Exam;
import com.galaxyschool.model.Question;
import com.galaxyschool.view.UpdateAnswer;
import com.galaxyschool.view.UpdateExam;
import com.galaxyschool.view.UpdateQuestion;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconName;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TeacherController extends GalaxyController {
    
    private static Stage stage;

    private Exam predefineExam;
    private Question predefineQuestion;
    
    @FXML
    private ListView examList;
    @FXML
    private ListView questionListView;
    @FXML
    private TableView<Answer> tableViewAnswers;
    @FXML
    private TableColumn<Answer, String> answerColumn;
    @FXML
    private TableColumn<Answer, String> correctColumn;
    @FXML
    private TableColumn<Answer, String> buttonColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        //Populating exam list view
        examList.setCellFactory(new Callback<ListView<Exam>, ListCell>() {
            @Override
            public ListCell call(ListView<Exam> param) {
                return new ButtonListExamCell();
            }
        });
        try {
            examList.setItems(FXCollections.observableList(Exam.getExams()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        examList.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                Exam e = (Exam)examList.getSelectionModel().getSelectedItem();

                updateQuestionListView(e, null);
            }
        });

        if (predefineExam != null) {
            examList.getSelectionModel().select(predefineExam);
            updateQuestionListView(predefineExam, predefineQuestion);
        }

    }
    
    public void hideWindow(MouseEvent mouseEvent) {
        stage.setIconified(true);
    }

    public void closeWindow(MouseEvent mouseEvent) {
        stage.hide();
    }

    public void updateQuestionListView(Exam exam, Question predefineQuestion) {

        if (exam.getQuestions().isEmpty()) {
            questionListView.getItems().clear();

            Label placeholder = new Label("There are not question for exam '" + exam.getName() + "'");
            placeholder.setMaxWidth(300);

            questionListView.setPlaceholder(placeholder);
        } else {
            //updating question list view
            questionListView.setCellFactory(new Callback<ListView<Question>, ListCell>() {
                @Override
                public ListCell call(ListView<Question> param) {
                    return new ButtonListQuestionCell(exam);
                }
            });
            questionListView.setItems(FXCollections.observableList(exam.getQuestions()));

            questionListView.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    Question question = (Question)questionListView.getSelectionModel().getSelectedItem();

                    updateAnswerTableView(question, exam);
                }
            });

            if (predefineQuestion != null) {
                questionListView.getSelectionModel().select(predefineQuestion);
                updateAnswerTableView(predefineQuestion, exam);
            }
            updateAnswerTableView(predefineQuestion, exam);
        }
    }

    public void updateAnswerTableView(Question question, Exam parentExam) {
        tableViewAnswers.getItems().clear();

        if (question == null) {
            Label placeholder = new Label("Select a question");
            placeholder.setMaxWidth(400);
            placeholder.alignmentProperty().setValue(Pos.CENTER);

            tableViewAnswers.setPlaceholder(placeholder);
        }else if (question.getAnswers().isEmpty()) {
            Label placeholder = new Label("There are not answers for question '" + question.getText() + "'");
            placeholder.setMaxWidth(400);

            tableViewAnswers.setPlaceholder(placeholder);
        } else {
            answerColumn.setCellFactory(new Callback<TableColumn<Answer, String>, TableCell<Answer, String>>(){

                @Override
                public TableCell<Answer, String> call(TableColumn<Answer, String> param) {
                    final TableCell<Answer, String> cell = new TableCell<Answer, String>() {
                        @Override
                        public void updateItem(String obj, boolean empty) {
                            super.updateItem(obj, empty);
                            if (empty) {
                                setGraphic(null);
                            } else {
                                Answer answer = getTableView().getItems().get(getIndex());
                                setText(answer.getText());
                                this.setMaxWidth(315);
                                this.setWrapText(true);
                            }
                        }
                    };
                    return cell;
                }
            });
            correctColumn.setCellFactory(new Callback<TableColumn<Answer, String>, TableCell<Answer, String>>(){

                @Override
                public TableCell<Answer, String> call(TableColumn<Answer, String> param) {
                    final TableCell<Answer, String> cell = new TableCell<Answer, String>() {
                        @Override
                        public void updateItem(String obj, boolean empty) {
                            super.updateItem(obj, empty);
                            if (empty) {
                                setGraphic(null);
                            } else {
                                Answer answer = getTableView().getItems().get(getIndex());
                                setText(answer.isCorrectAnswer() ? "Yes" : "No");
                            }
                        }
                    };
                    return cell;
                }
            });
            buttonColumn.setCellFactory(new AnswerTableCellFactory(question, parentExam));

            tableViewAnswers.getItems().setAll((FXCollections.observableArrayList(question.getAnswers())));

            tableViewAnswers.refresh();
        }
    }

    public void showUpdateExam(String examName) throws Exception {
        new UpdateExam(examName).start(new Stage());
    }

    public void showUpdateQuestion(Question question, Exam parentExam) throws Exception {
        new UpdateQuestion(question, parentExam).start(new Stage());
    }

    public void showUpdateAnswer(Answer answer, Question parentQuestion, Exam parentExam) throws Exception {
        new UpdateAnswer(parentQuestion, parentExam, answer).start(new Stage());
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setPredefineExam(Exam predefineExam) {
        this.predefineExam = predefineExam;
    }

    public void setPredefineQuestion(Question predefineQuestion) {
        this.predefineQuestion = predefineQuestion;
    }

    public void createNewExam(ActionEvent actionEvent) throws Exception {
        new UpdateExam().start(new Stage());
    }

    public void createNewQuestion(ActionEvent actionEvent) throws Exception {
        if (examList.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning!");
            alert.setHeaderText(null);
            alert.setContentText("You must select an exam to be able to attach questions!" );
            alert.showAndWait();
        } else {
            new UpdateQuestion(null, (Exam)examList.getSelectionModel().getSelectedItem()).start(new Stage());
        }
    }

    public void createNewAnswer(ActionEvent actionEvent) throws Exception {
        if (questionListView.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning!");
            alert.setHeaderText(null);
            alert.setContentText("You must select a question to be able to attach answers!" );
            alert.showAndWait();
        } else {
            new UpdateAnswer((Question)questionListView.getSelectionModel().getSelectedItem(), (Exam)examList.getSelectionModel().getSelectedItem(), null).start(new Stage());
        }
    }

    class ButtonListExamCell extends ListCell<Exam> {

        @Override
        public void updateItem(Exam obj, boolean empty) {
            super.updateItem(obj, empty);
            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                Button editButt = generateButtonWithFontAwesomeIcon(FontAwesomeIconName.EDIT, BUTTON_COLOR_BLUE);
                editButt.setTooltip(new Tooltip("Edit"));

                Button deleteButt = generateButtonWithFontAwesomeIcon(FontAwesomeIconName.MINUS_CIRCLE, BUTTON_COLOR_RED);
                deleteButt.setTooltip(new Tooltip("Delete"));

                editButt.setOnAction(event -> {
                        try {
                            showUpdateExam(obj.getName());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });

                deleteButt.setOnAction(event -> {
                    try {
                        if (examList.getSelectionModel().getSelectedItem().equals(obj)) {
                            questionListView.getItems().clear();

                            Label placeholderQuestion = new Label("Select an Exam");
                            placeholderQuestion.setMaxWidth(300);
                            placeholderQuestion.setAlignment(Pos.CENTER);

                            questionListView.setPlaceholder(placeholderQuestion);


                            tableViewAnswers.getItems().clear();

                            Label placeholderAnswer = new Label("Select a question");
                            placeholderAnswer.setMaxWidth(400);
                            placeholderAnswer.alignmentProperty().setValue(Pos.CENTER);

                            tableViewAnswers.setPlaceholder(placeholderAnswer);
                        }

                        Exam.deleteExam(obj);
                        getListView().getItems().remove(getItem());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

                HBox hbox = generateRowListWithButton(190, obj.getName(), editButt, deleteButt);

                setGraphic(hbox);
            }
        }
    }

    class ButtonListQuestionCell extends ListCell<Question> {

        private Exam parentExam;

        ButtonListQuestionCell(Exam parentExam) {
            this.parentExam = parentExam;
        }

        @Override
        public void updateItem(Question obj, boolean empty) {
            super.updateItem(obj, empty);
            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                Button editButt = generateButtonWithFontAwesomeIcon(FontAwesomeIconName.EDIT, BUTTON_COLOR_BLUE);
                editButt.setTooltip(new Tooltip("Edit"));

                Button deleteButt = generateButtonWithFontAwesomeIcon(FontAwesomeIconName.MINUS_CIRCLE, BUTTON_COLOR_RED);
                deleteButt.setTooltip(new Tooltip("Delete"));

                editButt.setOnAction(event -> {
                    try {
                        showUpdateQuestion(obj, parentExam);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

                deleteButt.setOnAction(event -> {
                    try {

                        if (questionListView.getSelectionModel().getSelectedItem().equals(obj)) {
                            tableViewAnswers.getItems().clear();

                            Label placeholderAnswer = new Label("Select a question");
                            placeholderAnswer.setMaxWidth(400);
                            placeholderAnswer.alignmentProperty().setValue(Pos.CENTER);

                            tableViewAnswers.setPlaceholder(placeholderAnswer);
                        }

                        parentExam.getQuestions().remove(obj);

                        Exam.update(parentExam);

                        getListView().getItems().remove(getItem());

                        getListView().refresh();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

                HBox hbox = generateRowListWithButton(290, obj.getText(), editButt, deleteButt);

                setGraphic(hbox);
            }
        }
    }

    public class AnswerTableCellFactory implements Callback<TableColumn<Answer, String>, TableCell<Answer, String>> {

        private Question parentQuestion;
        private Exam parentExam;

        AnswerTableCellFactory(Question parentQuestion, Exam parentExam) {
            this.parentQuestion = parentQuestion;
            this.parentExam = parentExam;
        }

        @Override
        public TableCell<Answer, String> call(TableColumn<Answer, String> param) {
            return new AnswerTableCell(parentQuestion, parentExam);
        }
    }


    class AnswerTableCell extends TableCell<Answer, String> {

        private Question parentQuestion;
        private Exam parentExam;

        AnswerTableCell(Question parentQuestion, Exam parentExam) {
            this.parentQuestion = parentQuestion;
            this.parentExam = parentExam;
        }

        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setGraphic(null);
                setText(null);
            } else {
                Button editButt = generateButtonWithFontAwesomeIcon(FontAwesomeIconName.EDIT, BUTTON_COLOR_BLUE);
                editButt.setTooltip(new Tooltip("Edit"));

                Button deleteButt = generateButtonWithFontAwesomeIcon(FontAwesomeIconName.MINUS_CIRCLE, BUTTON_COLOR_RED);
                deleteButt.setTooltip(new Tooltip("Delete"));

                editButt.setOnAction(event -> {
                    try {
                        showUpdateAnswer((Answer) getTableRow().getItem(), parentQuestion, parentExam);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

                deleteButt.setOnAction(event -> {
                    try {
                        parentQuestion.getAnswers().remove((Answer) getTableRow().getItem());

                        Exam.update(parentExam);

                        getTableView().getItems().remove(getTableRow().getItem());

                        getTableView().refresh();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

                HBox hbox = generateRowListWithButton(0, null, editButt, deleteButt);

                setGraphic(hbox);
            }
        }
    }
}
