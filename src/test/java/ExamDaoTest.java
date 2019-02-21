
import com.galaxyschool.db.ExamDao;
import com.galaxyschool.model.Answer;
import com.galaxyschool.model.Exam;
import com.galaxyschool.model.Question;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExamDaoTest {

    @Test
    public void testExamDao() throws Exception {
        ExamDao examDao = new ExamDao(new File(System.getProperty("user.home") + "/galaxyschool/examsTest.json"));

        List<Answer> answers = new ArrayList<>();
        answers.add(new Answer("This is wrong answer", false, "This is the reason answer"));
        answers.add(new Answer("This is correct answer", true, "This is the reason answer"));

        List<Question> questions = new ArrayList<>();
        questions.add(new Question("Who is this?", answers));
        questions.add(new Question("What is your name?", answers));

        Exam exam1 = new Exam("eleven years exam", "galaxy", 11, new Date(), questions);
        Exam exam2 = new Exam("twelve years exam", "galaxy", 12, new Date(), questions);
        Exam exam3 = new Exam("thirteen years exam", "galaxy", 13, new Date(), questions);

        //testing save exams
        examDao.save(exam1);
        examDao.save(exam2);
        examDao.save(exam3);

        assertThat("There are 3 exams in json file", examDao.getAll().size() == 3);

        //testing update exams
        List<Answer> answers2 = new ArrayList<>();
        answers2.add(new Answer("This is wrong answer2", false, "Another explanation"));
        answers2.add(new Answer("This is correct answer2", true, "Another explanation"));

        List<Question> questions2 = new ArrayList<>();
        questions2.add(new Question("Who is this2?", answers2));
        questions2.add(new Question("What is your name2?", answers2));

        assertThat("There are exams was updated in json file before update", examDao.get("eleven years exam").getLevel() == 11);

        exam1 = new Exam("eleven years exam", "galaxy", 12, new Date(), questions2);
        examDao.update(exam1);

        assertThat("There are exams was updated in json file after update", examDao.get("eleven years exam").getLevel() == 12);

        //testing deleting exam
        examDao.delete(exam1);
        assertThat("There are 2 exams in json file after deletion", examDao.getAll().size() == 2);

    }



    @AfterClass
    public void deleteFile() throws IOException {
        ExamDao examDao = new ExamDao(new File(System.getProperty("user.home") + "/galaxyschool/examsTest.json"));
        examDao.deleteAll();
    }
}
