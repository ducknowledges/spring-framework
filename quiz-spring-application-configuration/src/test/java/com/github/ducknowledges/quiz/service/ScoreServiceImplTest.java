package com.github.ducknowledges.quiz.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.github.ducknowledges.quiz.domain.Score;
import com.github.ducknowledges.quiz.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Class ScoreServiceImpl")
class ScoreServiceImplTest {

    private Score score;
    private User user;
    private CommunicationService communicationService;

    @BeforeEach
    void setUp() {
        score = mock(Score.class);
        int successAttempts = 4;
        int failingAttempts = 1;
        when(score.getSuccessAttempts()).thenReturn(successAttempts);
        when(score.getFailingAttempts()).thenReturn(failingAttempts);
        when(score.toString()).thenReturn("Score:"
            + " success attempts=" + successAttempts
            + ", failing attempts=" + failingAttempts
            + ", attempts=" + (successAttempts + failingAttempts)
        );
        user = mock(User.class);
        when(user.getFullName()).thenReturn("firstName lastName");
        when(user.getScore()).thenReturn(score);
        communicationService = mock(CommunicationServiceImpl.class);
    }

    @Test
    @DisplayName("should summarize user passed score")
    void summarizePassedScore() {
        int successScore = 4;
        String reportedMessage = "firstName lastName Congratulations! You pass the quiz"
            + System.lineSeparator()
            + score;
        ScoreService scoreService = new ScoreServiceImpl(successScore, communicationService);
        scoreService.summarizeScore(user);
        verify(communicationService, times(1)).reportToUser(reportedMessage);
    }

    @Test
    @DisplayName("should summarize user failed score")
    void summarizeFailedScore() {
        int successScore = 5;
        String reportedMessage = "firstName lastName Sorry! You fail the quiz"
            + System.lineSeparator()
            + score;
        ScoreService scoreService = new ScoreServiceImpl(successScore, communicationService);
        scoreService.summarizeScore(user);
        verify(communicationService, times(1)).reportToUser(reportedMessage);
    }
}