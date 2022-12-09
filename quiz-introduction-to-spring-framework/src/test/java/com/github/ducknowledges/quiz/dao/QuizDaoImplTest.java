package com.github.ducknowledges.quiz.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.github.ducknowledges.quiz.domain.Quiz;
import com.github.ducknowledges.quiz.parser.DataParserCsv;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Class QuizDaoImpl")
class QuizDaoImplTest {

    @Test
    @DisplayName("correctly return quizzes")
    void shouldReturnQuizzes() {
        List<String> strings1 = List.of("Is this a question?", "yes");
        List<String> strings2 = List.of("Is this a question with options?", "yes", "yes", "no");
        List<List<String>> records = List.of(strings1, strings2);
        List<Quiz> quizzes = List.of(
            new Quiz("Is this a question?", "yes"),
            new Quiz("Is this a question with options?", "yes", List.of("yes", "no"))
        );
        DataParserCsv parser = mock(DataParserCsv.class);
        when(parser.parseToRecords()).thenReturn(records);
        QuizDao quizDao = new QuizDaoImpl(parser);
        assertThat(quizDao.getQuizzes()).hasSize(2).hasSameElementsAs(quizzes);
    }
}