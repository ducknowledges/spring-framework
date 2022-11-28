package com.github.ducknowledges.quiz.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.github.ducknowledges.quiz.dao.QuizDao;
import com.github.ducknowledges.quiz.domain.Quiz;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class QuizServiceImplTest {

    private ByteArrayOutputStream out;

    @BeforeEach
    public void setup() {
        out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
    }

    @Test
    @DisplayName("print question to console")
    void shouldPrintQuestionToConsole() {
        List<Quiz> quizzes = List.of(
                new Quiz("Is this a question?", "yes"),
                new Quiz("Is this a question with options?", "yes", List.of("yes", "no"))
        );
        String outputString = "Is this a question?\n" + "Is this a question with options?\n";
        QuizDao quizDao = mock(QuizDao.class);
        when(quizDao.getQuizzes()).thenReturn(quizzes);
        QuizService quizService = new QuizServiceImpl(quizDao);
        quizService.run();
        assertThat(out.toString()).isEqualTo(outputString);
    }
}