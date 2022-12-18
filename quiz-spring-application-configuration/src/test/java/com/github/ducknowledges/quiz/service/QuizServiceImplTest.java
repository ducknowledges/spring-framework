package com.github.ducknowledges.quiz.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.github.ducknowledges.quiz.domain.Quiz;
import com.github.ducknowledges.quiz.domain.Score;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Class QuizServiceImpl")
class QuizServiceImplTest {

    List<Quiz> quizzes;
    private CommunicationService communicationService;
    private QuizService quizService;

    @BeforeEach
    void setUp() {
        quizzes = List.of(
            new Quiz("Is this a question with options?", "yes", List.of("yes", "no")),
            new Quiz("Is this a question?", "yes")
        );

        communicationService = mock(CommunicationService.class);
        quizService = new QuizServiceImpl(communicationService);

    }

    @Test
    @DisplayName("should complete quiz with success attempt")
    void shouldReturnScoreWithSuccessAttempt() {
        when(communicationService.askToUser(anyString())).thenReturn("yes");
        Score actual = quizService.getScoreForQuiz(quizzes);
        Score expected = new Score(2, 0);
        assertThat(actual).isEqualTo(expected);

    }

    @Test
    @DisplayName("should complete quiz with failure attempt")
    void shouldReturnScoreWithFailingAttempt() {
        when(communicationService.askToUser(anyString())).thenReturn("no");
        QuizService quizService = new QuizServiceImpl(communicationService);
        Score actual = quizService.getScoreForQuiz(quizzes);
        Score expected = new Score(0, 2);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("should correctly ask to user question from quiz")
    void shouldAskQuestionsToUser() {
        quizService.getScoreForQuiz(quizzes);
        verify(communicationService, times(1)).askToUser("Is this a question with options?"
            + System.lineSeparator() + "Options: yes, no");
        verify(communicationService, times(1)).askToUser("Is this a question?");
    }
}