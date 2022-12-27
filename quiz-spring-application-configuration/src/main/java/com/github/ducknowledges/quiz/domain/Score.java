package com.github.ducknowledges.quiz.domain;

import java.util.Objects;

public class Score {

    private int successAttempts;
    private int failingAttempts;

    public Score() {
        this(0, 0);
    }

    public Score(int successAttempts, int failingAttempts) {
        this.successAttempts = successAttempts;
        this.failingAttempts = failingAttempts;
    }

    public void checkAttempt(boolean success) {
        if (success) {
            this.increaseSuccessAttempts();
        } else {
            this.increaseFailingAttempts();
        }
    }

    private void increaseSuccessAttempts() {
        this.successAttempts += 1;
    }

    private void increaseFailingAttempts() {
        this.failingAttempts += 1;
    }

    public int getSuccessAttempts() {
        return successAttempts;
    }

    public int getFailingAttempts() {
        return failingAttempts;
    }

    @Override
    public String toString() {
        return "Score:"
            + " success attempts=" + successAttempts
            + ", failing attempts=" + failingAttempts
            + ", attempts=" + (successAttempts + failingAttempts);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Score score = (Score) o;
        return successAttempts == score.successAttempts && failingAttempts == score.failingAttempts;
    }

    @Override
    public int hashCode() {
        return Objects.hash(successAttempts, failingAttempts);
    }
}
