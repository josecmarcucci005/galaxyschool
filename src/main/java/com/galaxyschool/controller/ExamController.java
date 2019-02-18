package com.galaxyschool.controller;

import com.galaxyschool.db.ExamDao;
import com.galaxyschool.model.Exam;;

import java.io.IOException;
import java.util.List;

public class ExamController {

    private ExamDao examDao = new ExamDao();

    public ExamController() throws IOException {
    }

    public List<Exam> getExams() {
        return examDao.getAll();
    }

    public void updateExam(Exam exam) throws IOException {
        examDao.update(exam);
    }

    public void deleteExam(Exam exam) throws IOException {
        examDao.delete(exam);
    }

    public void saveExam(Exam exam) throws Exception {
        examDao.save(exam);
    }
}
