package com.github.ducknowledges.quiz.integrationtest;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.ducknowledges.quiz.dao.QuizDao;
import com.github.ducknowledges.quiz.dao.QuizDaoImpl;
import com.github.ducknowledges.quiz.parser.DataParser;
import com.github.ducknowledges.quiz.parser.DataParserCsv;
import com.github.ducknowledges.quiz.parser.recordchecker.RecordChecker;
import com.github.ducknowledges.quiz.parser.recordchecker.RecordCheckerImpl;
import com.github.ducknowledges.quiz.reader.DataReader;
import com.github.ducknowledges.quiz.reader.DataReaderResource;
import com.github.ducknowledges.quiz.service.CommunicationService;
import com.github.ducknowledges.quiz.service.CommunicationServiceImpl;
import com.github.ducknowledges.quiz.service.IoService;
import com.github.ducknowledges.quiz.service.IoServiceStream;
import com.github.ducknowledges.quiz.service.QuizManagerService;
import com.github.ducknowledges.quiz.service.QuizManagerServiceImpl;
import com.github.ducknowledges.quiz.service.QuizService;
import com.github.ducknowledges.quiz.service.QuizServiceImpl;
import com.github.ducknowledges.quiz.service.ScoreService;
import com.github.ducknowledges.quiz.service.ScoreServiceImpl;
import com.github.ducknowledges.quiz.service.UserService;
import com.github.ducknowledges.quiz.service.UserServiceImpl;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Integration Test QuizServiceImpl")
public class QuizManagerServiceImplIntegrationTest {

    private InputStream in;
    private OutputStream out;
    private OutputStream err;
    private QuizManagerService managerService;

    @BeforeEach
    void setUp() {
        String firstName = "firstName" + System.lineSeparator();
        String lastName = "lastName" + System.lineSeparator();
        String answer1 = "yes" + System.lineSeparator();
        String answer2 = "no" + System.lineSeparator();
        String userInput = firstName + lastName + answer1 + answer2;
        in = new ByteArrayInputStream(userInput.getBytes(StandardCharsets.UTF_8));
        out = new ByteArrayOutputStream();
        err = new ByteArrayOutputStream();
        IoService ioService = new IoServiceStream(in, out, err);
        CommunicationService communicationService = new CommunicationServiceImpl(ioService);
        DataReader dataReader = new DataReaderResource("quizTest.csv");
        RecordChecker recordChecker = new RecordCheckerImpl();
        DataParser dataParse = new DataParserCsv(dataReader, recordChecker, communicationService);
        QuizDao quizDao = new QuizDaoImpl(dataParse);
        QuizService quizService = new QuizServiceImpl(communicationService);
        UserService userService = new UserServiceImpl(communicationService);
        ScoreService scoreService = new ScoreServiceImpl(1, communicationService);
        managerService = new QuizManagerServiceImpl(
            quizDao, userService, quizService, scoreService
        );
    }

    @Test
    @DisplayName("should run quiz")
    void shouldReturnScore() {
        StringBuilder expected = new StringBuilder();
        expected.append("Please, introduce yourself")
            .append(System.lineSeparator())
            .append("Enter firstName:")
            .append(System.lineSeparator())
            .append("Enter lastName:")
            .append(System.lineSeparator())
            .append("Is this a question?")
            .append(System.lineSeparator())
            .append("Is this a question with options?")
            .append(System.lineSeparator())
            .append("Options: yes, no")
            .append(System.lineSeparator())
            .append("firstName lastName Congratulations! You pass the quiz")
            .append(System.lineSeparator())
            .append("Score: success attempts=1, failing attempts=1, attempts=2")
            .append(System.lineSeparator());
        managerService.run();
        assertThat(out.toString()).isEqualTo(expected.toString());
    }
}
