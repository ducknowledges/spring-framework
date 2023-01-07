package com.github.ducknowledges.quiz.integrationtest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.github.ducknowledges.quiz.service.IoService;
import com.github.ducknowledges.quiz.service.IoServiceStream;
import com.github.ducknowledges.quiz.service.QuizManagerService;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootTest
@DisplayName("Integration Test QuizServiceImpl")
public class QuizManagerServiceImplIntegrationTest {

    private static InputStream in;
    private static OutputStream out;
    private static OutputStream err;

    @BeforeAll
    static void setUpAll() {
        String userInput = "firstName" + System.lineSeparator()
            + "lastName" + System.lineSeparator()
            + "yes" + System.lineSeparator()
            + "no" + System.lineSeparator();
        in = new ByteArrayInputStream(userInput.getBytes(StandardCharsets.UTF_8));
        out = new ByteArrayOutputStream();
        err = new ByteArrayOutputStream();
    }

    @TestConfiguration
    public static class Configuration {
        @Bean
        public IoService ioService() {
            return new IoServiceStream(in, out, err);
        }

    }

    @Autowired
    private QuizManagerService quizManagerService;

    @Test
    @DisplayName("should complete quiz")
    void shouldCompleteQuiz() {
        StringBuilder expected = new StringBuilder();
        expected
            .append("Please, introduce yourself!")
            .append(System.lineSeparator())
            .append("Enter firstName:")
            .append(System.lineSeparator())
            .append("Enter lastName:")
            .append(System.lineSeparator())
            .append("Is this a question?")
            .append(System.lineSeparator())
            .append("Is this a question with options?")
            .append(System.lineSeparator())
            .append("Options:yes, no")
            .append(System.lineSeparator())
            .append("firstName lastName, congratulations! You pass the quiz")
            .append(System.lineSeparator())
            .append("Score: success answers=1, failing answers=1")
            .append(System.lineSeparator());
        quizManagerService.run();
        assertThat(out.toString()).isEqualTo(expected.toString());
    }
}
