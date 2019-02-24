package com.galaxyschool.model;

import com.galaxyschool.db.DuplicateExamException;
import com.galaxyschool.db.ExamDao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Exam implements Comparable<Exam> {

    private String name;
    private String author;
    private long level;
    private Date creationDate;
    private List<Question> questions;

    public Exam(String name, String author, long level, Date creationDate, List<Question> questions) throws IOException {
        this.name = name;
        this.author = author;
        this.level = level;
        this.creationDate = creationDate;
        this.questions = questions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public long getLevel() {
        return level;
    }

    public void setLevel(long level) {
        this.level = level;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Exam exam = (Exam) o;
        return level == exam.level &&
                Objects.equals(name, exam.name) &&
                Objects.equals(author, exam.author) &&
                Objects.equals(creationDate, exam.creationDate) &&
                Objects.equals(questions, exam.questions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, author, level, creationDate, questions);
    }

    @Override
    public String toString() {
        return "Exam{" +
                "name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", level=" + level +
                ", creationDate=" + creationDate +
                ", questions=" + questions +
                '}';
    }

    public static List<Exam> getExams() throws IOException {
        return ExamDao.getInstance().getAll();
    }

    public static void update(Exam exam) throws IOException {
        ExamDao.getInstance().update(exam);
    }

    public static void deleteExam(Exam exam) throws IOException {
        ExamDao.getInstance().delete(exam);
    }

    public static void saveExam(Exam exam) throws IOException, DuplicateExamException {
        ExamDao.getInstance().save(exam);
    }
    
    public static Exam getExamByName(String examNm) throws Exception {
        return ExamDao.getInstance().get(examNm);
    }

    public static List<Exam> getExamsByYear(String year) throws IOException {
        return ExamDao.getInstance().getExamsByAge(Long.valueOf(year));
    }

    @Override
    public int compareTo(Exam o) {
        return name.compareTo(o.getName());
    }
}
