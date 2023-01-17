package com.github.ducknowledges.quiz.domain;

import java.util.Objects;

public class User {
    private final String firstName;
    private final String lastName;
    private Score score;

    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.score = new Score();
    }

    public User() {
        this.firstName = "";
        this.lastName = "";
        this.score = new Score();
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public Score getScore() {
        return this.score;
    }

    public void updateScore(Score score) {
        int successAttempts = this.score.getSuccessAttempts() + score.getSuccessAttempts();
        int failingAttempts = this.score.getFailingAttempts() + score.getFailingAttempts();
        this.score = new Score(successAttempts, failingAttempts);
    }

    @Override
    public String toString() {
        return "User{"
            + "firstName='" + firstName + '\''
            + ", lastName='" + lastName + '\''
            + ", score=" + score.toString()
            + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(firstName, user.firstName)
            && Objects.equals(lastName, user.lastName)
            && Objects.equals(score, user.score);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, score);
    }
}
