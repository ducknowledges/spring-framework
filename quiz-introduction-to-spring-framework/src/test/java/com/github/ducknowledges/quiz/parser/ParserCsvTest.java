package com.github.ducknowledges.quiz.parser;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.ducknowledges.quiz.domain.Quiz;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


@DisplayName("correctly created by the constructor")
class ParserCsvTest {

    private ByteArrayOutputStream err;

    @BeforeEach
    public void setup() {
        err = new ByteArrayOutputStream();
        System.setErr(new PrintStream(err));
    }

    @Test
    @DisplayName("correctly parse csv to list of quizzes")
    void shouldParseCsvToQuizzes() {
        String pathToCsvResource = "quizTest.csv";
        Parser parser = new ParserCsv(pathToCsvResource);
        List<Quiz> quizzes = List.of(
                new Quiz("Is this a question?", "yes"),
                new Quiz("Is this a question with options?", "yes", List.of("yes", "no"))
        );
        assertThat(parser.parseToQuizzes()).hasSize(2).hasSameElementsAs(quizzes);
    }

    @Test
    @DisplayName("parser return empty list")
    void shouldReturnEmptyListWhenHasParseError() {
        String pathToCsvResource = "wrongPath.csv";
        Parser parser = new ParserCsv(pathToCsvResource);
        assertThat(parser.parseToQuizzes()).isEmpty();
    }

    @Test
    @DisplayName("print error to console when resource is not available")
    void shouldPrintErrorToConsoleWhenWrongResourcePath() {
        String pathToCsvResource = "wrongPath.csv";
        Parser parser = new ParserCsv(pathToCsvResource);
        parser.parseToQuizzes();
        assertThat(err.toString()).isEqualTo(
                pathToCsvResource
                        + " "
                        + ParserErrorMessage.RESOURCE_ERROR.message()
                        + System.lineSeparator());
    }

    @Test
    @DisplayName("print error to console when csv has only question")
    void shouldPrintErrorToConsoleWhenQuizCsvHasOnlyQuestion() {
        String pathToCsvResource = "quizWithOnlyQuestion.csv";
        Parser parser = new ParserCsv(pathToCsvResource);
        assertThat(parser.parseToQuizzes()).isEmpty();
        assertThat(err.toString()).isEqualTo(
                pathToCsvResource
                        + " "
                        + ParserErrorMessage.WRONG_FORMAT.message(1) + System.lineSeparator());
    }

    @Test
    @DisplayName("print error to console when csv has only question")
    void shouldPrintErrorToConsoleWhenQuizCsvHasMissedQuestion() {
        String pathToCsvResource = "quizWithMissedQuestion.csv";
        Parser parser = new ParserCsv(pathToCsvResource);
        assertThat(parser.parseToQuizzes()).isEmpty();
        assertThat(err.toString()).isEqualTo(
                pathToCsvResource
                        + " "
                        + ParserErrorMessage.WRONG_FORMAT.message(2) + System.lineSeparator());
    }

    @Test
    @DisplayName("print error to console when csv has missed answer")
    void shouldPrintErrorToConsoleWhenQuizCsvHasMissedAnswer() {
        String pathToCsvResource = "quizWithMissedAnswer.csv";
        Parser parser = new ParserCsv(pathToCsvResource);
        assertThat(parser.parseToQuizzes()).isEmpty();
        assertThat(err.toString()).isEqualTo(
                pathToCsvResource
                        + " "
                        + ParserErrorMessage.WRONG_FORMAT.message(2) + System.lineSeparator());
    }

    @Test
    @DisplayName("print error to console when csv has missed answer options")
    void shouldPrintErrorToConsoleWhenQuizCsvHasMissedAnswerOptions() {
        String pathToCsvResource = "quizWithMissedAnswerOptions.csv";
        Parser parser = new ParserCsv(pathToCsvResource);
        assertThat(parser.parseToQuizzes()).isEmpty();
        assertThat(err.toString()).isEqualTo(
                pathToCsvResource
                        + " "
                        + ParserErrorMessage.WRONG_FORMAT.message(2) + System.lineSeparator());
    }

}