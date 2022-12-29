package com.github.ducknowledges.quiz.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.github.ducknowledges.quiz.domain.Quiz;
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
    @DisplayName("should correctly report user question from quiz")
    void shouldAskQuestionsToUser() {
        quizService.askQuiz(quizzes);
        verify(communicationService, times(1)).reportToUser("Is this a question with options?"
            + System.lineSeparator() + "Options: yes, no");
        verify(communicationService, times(1)).reportToUser("Is this a question?");
    }
}