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
    private Quiz quizWithOptions;
    private String question;
    private String answer;
    private List<String> options;

    @BeforeEach
    void setUp() {
        question = "Is this a question?";
        answer = "yes";
        options = List.of("yes", "no");
        quizWithOptions = new Quiz(question, answer, options);
        quiz = new Quiz(question, answer);
    }

    @Test
    @DisplayName("correctly created by the constructor with 3 arguments")
    void shouldHaveCorrectConstructorWithThreeArguments() {
        assertAll(
                () -> assertThat(quizWithOptions.getQuestion()).isEqualTo(question),
                () -> assertThat(quizWithOptions.getAnswer()).isEqualTo(answer),
                () -> assertThat(quizWithOptions.getOptions()).hasSize(2).isEqualTo(options)
        );
    }

    @Test
    @DisplayName("correctly created by the constructor with 2 arguments")
    void shouldHaveCorrectConstructorWithTwoArguments() {
        assertAll(
                () -> assertThat(quiz.getQuestion()).isEqualTo(question),
                () -> assertThat(quiz.getAnswer()).isEqualTo(answer),
                () -> assertThat(quiz.getOptions()).isEmpty()
        );
    }

    @Test
    @DisplayName("should return true if quiz has options")
    void shouldReturnTrueWhenHasOptions() {
        assertThat(quizWithOptions.hasOptions()).isTrue();
    }

    @Test
    @DisplayName("should return false if quiz has no options")
    void shouldReturnFalseWhenHasNoOptions() {
        assertThat(quiz.hasOptions()).isFalse();
    }

    @Test
    @DisplayName("has correctly hashCode")
    void shouldHaveCorrectHashCode() {
        int hashCode1 = Objects.hash(question, answer, options);
        int hashCode2 = Objects.hash(question, answer, List.of());
        assertThat(quizWithOptions.hashCode()).isEqualTo(hashCode1);
        assertThat(quiz.hashCode()).isEqualTo(hashCode2);
    }

    @Test
    @DisplayName("correctly converted to string")
    void shouldHaveCorrectToSting() {
        String string = "Quiz{"
                + "question='" + question + '\''
                + ", rightAnswer='" + answer + '\''
                + ", answerOptions=" + options
                + '}';
        assertThat(quizWithOptions.toString()).isEqualTo(string);
        string = "Quiz{"
            + "question='" + question + '\''
            + ", rightAnswer='" + answer + '\''
            + ", answerOptions=" + List.of()
            + '}';
        assertThat(quiz.toString()).isEqualTo(string);
    }

}