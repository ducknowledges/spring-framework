package com.github.ducknowledges.quiz.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.github.ducknowledges.quiz.config.AppProps;
import com.github.ducknowledges.quiz.domain.Score;
import com.github.ducknowledges.quiz.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Class ScoreServiceImpl")
class ScoreServiceImplTest {

    private Score score;
    private User user;
    private AppProps props;
    private CommunicationService communicationService;
    private MessageService messageService;

    @BeforeEach
    void setUp() {
        props = mock(AppProps.class);
        score = mock(Score.class);
        int successAttempts = 4;
        int failingAttempts = 1;
        when(score.getSuccessAttempts()).thenReturn(successAttempts);
        when(score.getFailingAttempts()).thenReturn(failingAttempts);

        user = mock(User.class);
        when(user.getFullName()).thenReturn("firstName lastName");
        when(user.getScore()).thenReturn(score);

        messageService = mock(MessageServiceImpl.class);
        when(messageService.getMessage(
            "result.score", new String[]{"4", "1"}))
            .thenReturn("Score: success answers=4, failing answers=1");


        communicationService = mock(CommunicationServiceImpl.class);
    }

    @Test
    @DisplayName("should summarize user passed score")
    void summarizePassedScore() {
        when(messageService.getMessage(
            "success.user", new String[]{user.getFullName()}))
            .thenReturn("firstName lastName, congratulations! You pass the quiz");
        when(props.getSuccessScore()).thenReturn(4);
        ScoreService scoreService = new ScoreServiceImpl(
            props, messageService, communicationService);
        scoreService.summarizeScore(user);

        String reportedMessage = "firstName lastName, congratulations! You pass the quiz"
            + System.lineSeparator()
            + "Score: success answers=4, failing answers=1";
        verify(communicationService, times(1)).reportToUser(reportedMessage);
    }

    @Test
    @DisplayName("should summarize user failed score")
    void summarizeFailedScore() {
        when(messageService.getMessage(
            "fail.user", new String[]{user.getFullName()}))
            .thenReturn("firstName lastName, sorry! You fail the quiz");
        when(props.getSuccessScore()).thenReturn(5);
        ScoreService scoreService = new ScoreServiceImpl(
            props, messageService, communicationService);
        scoreService.summarizeScore(user);

        String reportedMessage = "firstName lastName, sorry! You fail the quiz"
            + System.lineSeparator()
            + "Score: success answers=4, failing answers=1";
        verify(communicationService, times(1)).reportToUser(reportedMessage);
    }
}