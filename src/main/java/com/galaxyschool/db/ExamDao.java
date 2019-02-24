package com.galaxyschool.db;

import com.cedarsoftware.util.io.JsonWriter;
import com.galaxyschool.model.Answer;
import com.galaxyschool.model.Exam;
import com.galaxyschool.model.Question;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class ExamDao implements Dao<Exam> {

    private File galaxyStarJsonFile;
    private List<Exam> exams = new ArrayList<>();

    private static ExamDao instance;

    public static ExamDao getInstance() throws IOException {
        if (instance == null) {
            instance = new ExamDao();
        }
        return instance;
    }

    private ExamDao() throws IOException {
        galaxyStarJsonFile = new File(System.getProperty("user.home") + "/galaxyschool/exams.json");

        initJsonFile();
        getAll();
    }

    public ExamDao(File galaxyStarJsonFile) throws IOException {
        this.galaxyStarJsonFile = galaxyStarJsonFile;

        initJsonFile();
        getAll();
    }

    private void initJsonFile() throws IOException {
        galaxyStarJsonFile.getParentFile().mkdirs();

        if (!galaxyStarJsonFile.exists()) {
            galaxyStarJsonFile.createNewFile();
            updateExamFile();
        }
    }

    @Override
    public Exam get(String name) {
        return exams.stream()
                .filter(exam -> name.equals(exam.getName()))
                .findAny()
                .orElse(null);
    }

    public List<Exam> getExamsByAge(long level) {
        return exams.stream()
                .filter(exam -> level == exam.getLevel())
                .collect(Collectors.toCollection(() -> new ArrayList<Exam>()));
    }

    @Override
    public List<Exam> getAll() {
        JSONParser parser = new JSONParser();

        if (exams.isEmpty()) {
            try {

                Object obj = parser.parse(new FileReader(galaxyStarJsonFile));

                JSONObject jsonObject = (JSONObject) obj;

                JSONArray examList = (JSONArray) jsonObject.get("exams");

                Iterator<JSONObject> examsIterator = examList.iterator();
                while (examsIterator.hasNext()) {
                    JSONObject examJsonObject = examsIterator.next();

                    String name = (String) examJsonObject.get("name");
                    String author = (String) examJsonObject.get("author");
                    long level = (Long) examJsonObject.get("level");
                    Date creationDate = new SimpleDateFormat("dd/MM/yyyy").parse((String) examJsonObject.get("creationDate"));
                    JSONArray questionList = (JSONArray) examJsonObject.get("questionList");

                    Iterator<JSONObject> questionsIterator = questionList.iterator();
                    List<Question> questions = new ArrayList<>();
                    while (questionsIterator.hasNext()) {
                        JSONObject questionJsonObject = questionsIterator.next();

                        String questionText = (String) questionJsonObject.get("text");

                        JSONArray answerList = (JSONArray) questionJsonObject.get("answerList");

                        Iterator<JSONObject> answerIterator = answerList.iterator();
                        List<Answer> answers = new ArrayList<>();
                        while (answerIterator.hasNext()) {
                            JSONObject answerJsonObject = answerIterator.next();

                            String answerText = (String) answerJsonObject.get("text");
                            boolean correctAnswer = (Boolean) answerJsonObject.get("correctAnswer");
                            String explanation = (String) answerJsonObject.get("explanation");

                            answers.add(new Answer(answerText, correctAnswer, explanation));
                        }

                        questions.add(new Question(questionText, answers));
                    }
                    exams.add(new Exam(name, author, level, creationDate, questions));
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return exams;
    }

    @Override
    public void save(Exam exam) throws DuplicateExamException, IOException {
        if (get(exam.getName()) != null) {
            throw new DuplicateExamException("The exam with name '" + exam.getName() + "' already exists so it can't be created!");
        }

        exams.add(exam);

        updateExamFile();
    }

    @Override
    public void update(Exam exam) throws IOException {
        exams.replaceAll(s -> s.getName().equals(exam.getName()) ? exam : s);

        updateExamFile();
    }

    @Override
    public void delete(Exam exam) throws IOException {
        exams.removeIf(e -> e.getName().equals(exam.getName()));

        updateExamFile();
    }

    public void deleteAll() throws IOException {
        exams.clear();

        updateExamFile();
    }

    private void updateExamFile() throws IOException {
        JSONArray examsJsonArray = new JSONArray();

        for (Exam e : exams) {
            examsJsonArray.add(getExamJsonObject(e));
        }

        JSONObject examJson = new JSONObject();
        examJson.put("exams", examsJsonArray);

        FileWriter file = new FileWriter(galaxyStarJsonFile);
        file.write(JsonWriter.formatJson(examJson.toJSONString()));
        file.flush();
        file.close();
    }

    private JSONObject getExamJsonObject(Exam exam) {
        List<Question> questionList = exam.getQuestions();

        JSONArray questions = new JSONArray();
        for (Question q : questionList) {
            List<Answer> answerList = q.getAnswers();

            JSONArray answers = new JSONArray();
            for (Answer a : answerList) {
                JSONObject answerObject = new JSONObject();
                answerObject.put("text", a.getText());
                answerObject.put("correctAnswer", a.isCorrectAnswer());
                answerObject.put("explanation", a.getExplanation());

                answers.add(answerObject);
            }

            JSONObject questionObject = new JSONObject();
            questionObject.put("text", q.getText());
            questionObject.put("answerList", answers);

            questions.add(questionObject);
        }

        JSONObject examObject = new JSONObject();
        examObject.put("name", exam.getName());
        examObject.put("author", exam.getAuthor());
        examObject.put("level", exam.getLevel());
        examObject.put("creationDate", new SimpleDateFormat("dd/MM/yyyy").format(exam.getCreationDate()));
        examObject.put("questionList", questions);

        return examObject;
    }
}
