package com.github.ducknowledges.quiz.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Class Quiz")
class QuizTest {

    private Quiz quiz;
    private String question;
    private String rightAnswer;
    private List<String> answerOptions;


    @BeforeEach
    void setUp() {
        question = "Is this a question?";
        rightAnswer = "yes";
        answerOptions = List.of("yes", "no");
        quiz = new Quiz(question, rightAnswer, answerOptions);
    }

    @Test
    @DisplayName("correctly created by the constructor with 3 arguments")
    void shouldHaveCorrectConstructorWithThreeArguments() {
        assertAll(
                () -> assertThat(quiz.getQuestion()).isEqualTo(question),
                () -> assertThat(quiz.getRightAnswer()).isEqualTo(rightAnswer),
                () -> assertThat(quiz.getAnswerOptions()).hasSize(2).isEqualTo(answerOptions)
        );
    }

    @Test
    @DisplayName("correctly created by the constructor with 2 arguments")
    void shouldHaveCorrectConstructorWithTwoArguments() {
        Quiz quiz = new Quiz(question, rightAnswer);
        assertAll(
                () -> assertThat(quiz.getQuestion()).isEqualTo(question),
                () -> assertThat(quiz.getRightAnswer()).isEqualTo(rightAnswer),
                () -> assertThat(quiz.getAnswerOptions()).isEmpty()
        );
    }

    @Test
    @DisplayName("has correctly hashCode")
    void shouldHaveCorrectHashCode() {
        int hashCode = Objects.hash(question, rightAnswer, answerOptions);
        assertThat(quiz.hashCode()).isEqualTo(hashCode);
    }

    @Test
    @DisplayName("correctly converted to string")
    void shouldHaveCorrectToSting() {
        String string = "Quiz{"
                + "question='" + question + '\''
                + ", rightAnswer='" + rightAnswer + '\''
                + ", answerOptions=" + answerOptions
                + '}';
        Quiz quiz = new Quiz(question, rightAnswer, answerOptions);
        assertThat(quiz.toString()).isEqualTo(string);
    }

}