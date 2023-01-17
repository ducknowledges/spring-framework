package com.github.ducknowledges.quiz.integrationtest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.github.ducknowledges.quiz.commands.QuizCommands;
import com.github.ducknowledges.quiz.service.IoService;
import com.github.ducknowledges.quiz.service.IoServiceStream;
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
@DisplayName("Integration Quiz Test")
public class QuizIntegrationTest {

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
    private QuizCommands quizCommands;

    @Test
    @DisplayName("should complete quiz")
    void shouldCompleteQuiz() {
        String userGreet = quizCommands.login();
        quizCommands.run();
        quizCommands.logout();
        assertThat(userGreet).isEqualTo("firstName lastName you are logged in!");
        String expected = getExpected();
        assertThat(out.toString()).isEqualTo(expected);
    }

    private String getExpected() {
        return "Please, introduce yourself!"
            + System.lineSeparator()
            + "Enter first name:"
            + System.lineSeparator()
            + "Enter last name:"
            + System.lineSeparator()
            + "Is this a question?"
            + System.lineSeparator()
            + "Is this a question with options?"
            + System.lineSeparator()
            + "Options:yes, no"
            + System.lineSeparator()
            + "firstName lastName, congratulations! You pass the quiz."
            + System.lineSeparator()
            + "Score: success answers=1, failing answers=1"
            + System.lineSeparator();
    }
}
