package com.github.ducknowledges.quiz.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.github.ducknowledges.quiz.dao.QuizDao;
import com.github.ducknowledges.quiz.domain.Quiz;
import com.github.ducknowledges.quiz.domain.Score;
import com.github.ducknowledges.quiz.domain.User;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
@DisplayName("Class QuizManagerServiceImpl")
class QuizManagerServiceImplTest {

    @Mock
    private User user;
    @MockBean
    private QuizService quizService;
    @MockBean
    private ScoreService scoreService;
    @MockBean
    private QuizDao quizDao;

    @Autowired
    QuizManagerService quizManagerService;

    @Test
    @DisplayName("should complete the quiz")
    void shouldCompleteQuiz() {
        List<Quiz> quizzes = List.of(new Quiz("Is this a question?", "yes"));
        when(quizDao.getQuizzes()).thenReturn(quizzes);
        when(quizService.getScoreForQuiz(anyList())).thenReturn(new Score(1, 1));

        quizManagerService.run(user);
        verify(quizDao, times(1)).getQuizzes();
        verify(quizService, times(1)).getScoreForQuiz(quizzes);
        verify(user, times(1)).updateScore(new Score(1, 1));
        verify(scoreService, times(1)).summarizeScore(user);
    }

    @Test
    @DisplayName("should not complete the quiz")
    void shouldNotCompleteQuiz() {
        List<Quiz> quizzes = List.of();
        when(quizDao.getQuizzes()).thenReturn(quizzes);

        quizManagerService.run(user);
        verify(quizDao, times(1)).getQuizzes();
        verify(quizService, times(0)).getScoreForQuiz(anyList());
        verify(user, times(0)).updateScore(any(Score.class));
        verify(scoreService, times(0)).summarizeScore(user);
    }
}