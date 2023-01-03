package com.github.ducknowledges.quiz.integrationtest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.fail;

import com.github.ducknowledges.quiz.QuizApplication;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Integration Test QuizServiceImpl")
public class QuizManagerServiceImplIntegrationTest {

    private InputStream in;
    private OutputStream out;

    @BeforeEach
    void setUp() {
        String userInput = getUserInput();
        in = new ByteArrayInputStream(userInput.getBytes(StandardCharsets.UTF_8));
        out = new ByteArrayOutputStream();
        System.setIn(in);
        System.setOut(new PrintStream(out));
    }

    private String getUserInput() {
        return "firstName" + System.lineSeparator()
            + "lastName" + System.lineSeparator()
            + "yes" + System.lineSeparator()
            + "no" + System.lineSeparator();
    }

    @Test
    @DisplayName("should complete quiz")
    void shouldCompleteQuiz() {
        StringBuilder expected = new StringBuilder();
        expected.append(getAppBanner())
            .append(System.lineSeparator())
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
            .append("1.yes, 2.no")
            .append(System.lineSeparator())
            .append("firstName lastName, congratulations! You pass the quiz")
            .append(System.lineSeparator())
            .append("Score: success answers=1, failing answers=1")
            .append(System.lineSeparator());
        QuizApplication.main(new String[]{});
        assertThat(out.toString()).isEqualTo(expected.toString());
    }

    private String getAppBanner() {
        Path path = Paths.get("src/test/resources/banner-test.txt");
        String appBanner = "";
        try {
            appBanner = Files.readString(path);
        } catch (IOException e) {
            fail("Test failed");
        }
        return appBanner;
    }
}
