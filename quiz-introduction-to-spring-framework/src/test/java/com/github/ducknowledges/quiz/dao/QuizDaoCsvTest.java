package com.github.ducknowledges.quiz.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.github.ducknowledges.quiz.domain.Quiz;
import com.github.ducknowledges.quiz.parser.Parser;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class QuizDaoCsvTest {

    @Test
    @DisplayName("correctly return quizzes")
    void shouldReturnQuizzes() {
        List<Quiz> quizzes = List.of(
                new Quiz("Is this a question?", "yes"),
                new Quiz("Is this a question with options?", "yes", List.of("yes", "no"))
        );
        Parser parser = mock(Parser.class);
        when(parser.parseToQuizzes()).thenReturn(quizzes);
        QuizDao quizDao = new QuizDaoCsv(parser);
        assertThat(quizDao.getQuizzes()).hasSize(2).hasSameElementsAs(quizzes);
    }
}