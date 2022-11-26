package com.github.ducknowledges.quiz.domain;

import java.util.List;
import java.util.Objects;

public class Quiz {

    private final String question;
    private final String rightAnswer;
    private final List<String> answerOptions;

    public Quiz(String question, String rightAnswer) {
        this(question, rightAnswer, List.of());
    }

    public Quiz(String question, String rightAnswer, List<String> answerOptions) {
        this.question = question;
        this.rightAnswer = rightAnswer;
        this.answerOptions = answerOptions;
    }

    public String getQuestion() {
        return question;
    }

    public String getRightAnswer() {
        return rightAnswer;
    }

    public List<String> getAnswerOptions() {
        return answerOptions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Quiz quiz = (Quiz) o;
        return question.equals(quiz.question)
                && rightAnswer.equals(quiz.rightAnswer)
                && answerOptions.equals(quiz.answerOptions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(question, rightAnswer, answerOptions);
    }

    @Override
    public String toString() {
        return "Quiz{"
                + "question='" + question + '\''
                + ", rightAnswer='" + rightAnswer + '\''
                + ", answerOptions=" + answerOptions
                + '}';
    }
}
