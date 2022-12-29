package com.github.ducknowledges.quiz.service;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.github.ducknowledges.quiz.dao.QuizDao;
import com.github.ducknowledges.quiz.dao.QuizDaoImpl;
import com.github.ducknowledges.quiz.domain.Quiz;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Class QuizManagerServiceImpl")
class QuizManagerServiceImplTest {

    private QuizService quizService;
    private QuizDao quizDao;

    @BeforeEach
    void setUp() {
        quizDao = mock(QuizDaoImpl.class);
        quizService = mock(QuizServiceImpl.class);
    }

    @Test
    @DisplayName("should complete the quiz")
    void shouldCompleteQuiz() {
        List<Quiz> quizzes = List.of(new Quiz("Is this a question?", "yes"));
        when(quizDao.getQuizzes()).thenReturn(quizzes);

        QuizManagerService quizManagerService = new QuizManagerServiceImpl(
            quizDao, quizService
        );
        quizManagerService.run();
        verify(quizDao, times(1)).getQuizzes();
        verify(quizService, times(1)).askQuiz(quizzes);
    }

    @Test
    @DisplayName("should not complete the quiz")
    void shouldNotCompleteQuiz() {
        List<Quiz> quizzes = List.of();
        when(quizDao.getQuizzes()).thenReturn(quizzes);
        QuizManagerService quizManagerService = new QuizManagerServiceImpl(
            quizDao, quizService
        );
        quizManagerService.run();
        verify(quizDao, times(1)).getQuizzes();
        verify(quizService, times(0)).askQuiz(anyList());
    }
}