package com.github.ducknowledges.quiz.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.github.ducknowledges.quiz.domain.Quiz;
import com.github.ducknowledges.quiz.domain.Record;
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
        List<Quiz> quizzes = List.of(
            new Quiz(strings1.get(0), strings1.get(1)),
            new Quiz(strings2.get(0), strings2.get(1), strings2.subList(2, strings2.size()))
        );

        Record record1 = mock(Record.class);
        when(record1.size()).thenReturn(2);
        when(record1.getContentValue(0)).thenReturn(strings1.get(0));
        when(record1.getContentValue(1)).thenReturn(strings1.get(1));

        Record record2 = mock(Record.class);
        when(record2.size()).thenReturn(4);
        when(record2.getContentValue(0)).thenReturn(strings2.get(0));
        when(record2.getContentValue(1)).thenReturn(strings2.get(1));
        when(record2.getContent()).thenReturn(strings2);

        DataParserCsv parser = mock(DataParserCsv.class);
        when(parser.parseToRecords()).thenReturn(List.of(record1, record2));
        QuizDao quizDao = new QuizDaoImpl(parser);
        assertThat(quizDao.getQuizzes()).hasSize(2).hasSameElementsAs(quizzes);
    }
}