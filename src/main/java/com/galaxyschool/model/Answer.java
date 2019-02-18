package com.galaxyschool.model;

import java.util.Objects;

public class Answer {

    private String text;
    private boolean correctAnswer;

    public Answer(String text, boolean correctAnswer) {
        this.text = text;
        this.correctAnswer = correctAnswer;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(boolean correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Answer answer = (Answer) o;
        return correctAnswer == answer.correctAnswer &&
                Objects.equals(text, answer.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, correctAnswer);
    }

    @Override
    public String toString() {
        return "Answer{" +
                "text='" + text + '\'' +
                ", correctAnswer=" + correctAnswer +
                '}';
    }
}
