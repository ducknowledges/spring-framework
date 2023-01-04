package com.github.ducknowledges.quiz.domain;

import java.util.List;
import java.util.Objects;

public class Quiz {

    private final String question;
    private final List<String> options;
    private final String answer;


    public Quiz(String question, String rightAnswer) {
        this(question, rightAnswer, List.of());
    }

    public Quiz(String question, String answer, List<String> options) {
        this.question = question;
        this.options = options;
        this.answer = answer;

    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public List<String> getOptions() {
        return options;
    }

    public boolean hasOptions() {
        return !options.isEmpty();
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
        return Objects.equals(question, quiz.question)
            && Objects.equals(options, quiz.options)
            && Objects.equals(answer, quiz.answer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(question, answer, options);
    }

    @Override
    public String toString() {
        return "Quiz{"
                + "question='" + question + '\''
                + ", rightAnswer='" + answer + '\''
                + ", answerOptions=" + options
                + '}';
    }
}
