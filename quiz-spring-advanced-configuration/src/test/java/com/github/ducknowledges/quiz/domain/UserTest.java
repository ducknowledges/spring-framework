package com.github.ducknowledges.quiz.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Class User")
class UserTest {

    private User user;
    private String firstName;
    private String lastName;
    private Score score;

    @BeforeEach
    void setUp() {
        firstName = "firstName";
        lastName = "lastName";
        user = new User("firstName", "lastName");
        score = new Score();
    }

    @Test
    @DisplayName("correctly created by the constructor with 2 arguments")
    void shouldHaveCorrectConstructorWithThreeArguments() {
        assertAll(
            () -> assertThat(user.getFirstName()).isEqualTo(firstName),
            () -> assertThat(user.getLastName()).isEqualTo(lastName),
            () -> assertThat(user.getFullName()).isEqualTo(firstName + " " + lastName),
            () -> assertThat(user.getScore()).isEqualTo(score)
        );
    }

    @Test
    @DisplayName("should correctly update user score")
    void shouldUpdateScore() {
        Score score = new Score();
        score.checkAttempt(true);
        score.checkAttempt(false);
        user.updateScore(score);
        score = new Score();
        score.checkAttempt(true);
        score.checkAttempt(false);
        user.updateScore(score);
        assertThat(user.getScore()).isEqualTo(new Score(2, 2));
    }

    @Test
    @DisplayName("correctly converted to string")
    void shouldHaveCorrectToSting() {
        String string = "User{"
            + "firstName='" + firstName + '\''
            + ", lastName='" + lastName + '\''
            + ", score=" + score.toString()
            + '}';;
        assertThat(user.toString()).isEqualTo(string);
    }

    @Test
    @DisplayName("has correctly hashCode")
    void shouldHaveCorrectHashCode() {
        int hashCode = Objects.hash(firstName, lastName, score);
        assertThat(user.hashCode()).isEqualTo(hashCode);
    }
}