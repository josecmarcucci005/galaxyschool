package com.galaxyschool.model;

import java.util.Objects;

public class Answer {

    private String text;
    private boolean correctAnswer;
    private String explanation;

    public Answer(String text, boolean correctAnswer, String explanation) {
        this.text = text;
        this.correctAnswer = correctAnswer;
        this.explanation = explanation;
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

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Answer answer = (Answer) o;
        return correctAnswer == answer.correctAnswer &&
                text.equals(answer.text) &&
                explanation.equals(answer.explanation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, correctAnswer, explanation);
    }

    @Override
    public String toString() {
        return "Answer{" +
                "text='" + text + '\'' +
                ", correctAnswer=" + correctAnswer +
                ", explanation='" + explanation + '\'' +
                '}';
    }
}
