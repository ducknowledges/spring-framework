package com.github.ducknowledges.quiz.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Class Score")
class ScoreTest {

    private Score emptyScore;
    private Score score;
    private int successAttempts;
    private int failingAttempts;


    @BeforeEach
    void setUp() {
        successAttempts = 1;
        failingAttempts = 2;
        emptyScore = new Score();
        score = new Score(successAttempts, failingAttempts);
    }

    @Test
    @DisplayName("correctly created by the constructor")
    void shouldHaveCorrectConstructor() {
        assertAll(
            () -> assertThat(emptyScore.getSuccessAttempts()).isEqualTo(0),
            () -> assertThat(emptyScore.getFailingAttempts()).isEqualTo(0)
        );
    }

    @Test
    @DisplayName("correctly created by the constructor with 2 arguments")
    void shouldHaveCorrectConstructorWithThreeArguments() {
        assertAll(
                () -> assertThat(score.getSuccessAttempts()).isEqualTo(successAttempts),
                () -> assertThat(score.getFailingAttempts()).isEqualTo(failingAttempts)
        );
    }

    @Test
    @DisplayName("should increase success attempts")
    void shouldIncreaseSuccessAttempts() {
        score.checkAttempt(true);
        assertThat(score.getSuccessAttempts()).isEqualTo(successAttempts + 1);
    }

    @Test
    @DisplayName("should increase failure attempts")
    void shouldIncreaseFailureAttempts() {
        score.checkAttempt(false);
        assertThat(score.getFailingAttempts()).isEqualTo(failingAttempts + 1);
    }

    @Test
    @DisplayName("correctly converted to string")
    void shouldHaveCorrectToSting() {

        String string = "Score:"
            + " success attempts=" + successAttempts
            + ", failing attempts=" + failingAttempts
            + ", attempts=" + (successAttempts + failingAttempts);
        assertThat(score.toString()).isEqualTo(string);
    }

    @Test
    @DisplayName("has correctly hashCode")
    void shouldHaveCorrectHashCode() {
        int hashCode = Objects.hash(successAttempts, failingAttempts);
        assertThat(score.hashCode()).isEqualTo(hashCode);
    }

}