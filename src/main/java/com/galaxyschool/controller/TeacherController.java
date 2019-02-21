package com.galaxyschool.controller;

import com.galaxyschool.db.ExamDao;
import com.galaxyschool.model.Answer;
import com.galaxyschool.model.Exam;
import com.galaxyschool.model.Question;
import com.galaxyschool.view.TeacherPanel;
import com.galaxyschool.view.UpdateExam;
import com.galaxyschool.view.UpdateQuestion;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconName;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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

public class TeacherController extends GalaxyController {
    
    private static Stage stage;

    private Exam predefineExam;
    
    @FXML
    private ListView examList;

    @FXML
    private ListView questionListView;

    @FXML
    private TableView tableViewAnswers;

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

                updateQuestionListView(e);
            }
        });

        if (predefineExam != null) {
            examList.getSelectionModel().select(predefineExam);
            updateQuestionListView(predefineExam);
        }

    }
    
    public void hideWindow(MouseEvent mouseEvent) {
        stage.setIconified(true);
    }

    public void closeWindow(MouseEvent mouseEvent) {
        stage.hide();
    }

    public void updateQuestionListView(Exam exam) {

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
                    Question e = (Question)questionListView.getSelectionModel().getSelectedItem();

                    updateAnswerTableView(e);
                }
            });
        }

    }

    public void updateAnswerTableView(Question question) {
        if (question.getAnswers().isEmpty()) {
            questionListView.getItems().clear();

            Label placeholder = new Label("There are not answers for question '" + question.getText() + "'");
            placeholder.setMaxWidth(400);

            questionListView.setPlaceholder(placeholder);
        } else {
            for (Answer a : question.getAnswers()) {
                //tableViewAnswers.getRowFactory().
            }

            //tableViewAnswers.getColumns().add(0,  );
        }
    }

    public void showUpdateExam(String examName) throws Exception {
        new UpdateExam(examName).start(new Stage());
    }

    public void showUpdateQuestion(Question question, Exam parentExam) throws Exception {
        new UpdateQuestion(question, parentExam).start(new Stage());
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setPredefineExam(Exam predefineExam) {
        this.predefineExam = predefineExam;
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

    class AnswerTableCellFactory implements Callback<TableColumn<Answer, String>, TableCell<Answer, String>> {

        private Question fatherQuestion;

        AnswerTableCellFactory(Question fatherQuestion) {
            this.fatherQuestion = fatherQuestion;
        }

        @Override
        public TableCell<Answer, String> call(TableColumn<Answer, String> param) {
            final TableCell<Answer, String> cell = new TableCell<Answer, String>() {

                /**
                Button editButt = generateButtonWithFontAwesomeIcon(FontAwesomeIconName.EDIT, BUTTON_COLOR_BLUE);
                editButt.setTooltip(new Tooltip("Edit"));

                Button deleteButt = generateButtonWithFontAwesomeIcon(FontAwesomeIconName.MINUS_CIRCLE, BUTTON_COLOR_RED);
                deleteButt.setTooltip(new Tooltip("Delete"));

                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {

                    }
                }
                */
            };
            return cell;
        }

        private TableCell createCell() {

        }
    }

}
