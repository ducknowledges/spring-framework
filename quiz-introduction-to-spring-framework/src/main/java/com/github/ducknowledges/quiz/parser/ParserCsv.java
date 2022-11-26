package com.github.ducknowledges.quiz.parser;

import static com.github.ducknowledges.quiz.parser.ParserErrorMessage.RESOURCE_ERROR;
import static com.github.ducknowledges.quiz.parser.ParserErrorMessage.WRONG_FORMAT;

import com.github.ducknowledges.quiz.domain.Quiz;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

public class ParserCsv implements Parser {

    private final String pathToCsvResource;

    public ParserCsv(String pathToCsvResource) {
        this.pathToCsvResource = pathToCsvResource;
    }

    @Override
    public List<Quiz> parseToQuizzes() {
        try (Reader reader = getResourceAsStream(pathToCsvResource)) {
            return getQuizzesFrom(reader);
        } catch (ParserException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            System.err.println(pathToCsvResource + " " + RESOURCE_ERROR.message());
        }
        return List.of();
    }

    private InputStreamReader getResourceAsStream(String pathToCsvResource) throws IOException {
        InputStream stream = getClass().getClassLoader().getResourceAsStream(pathToCsvResource);
        if (stream == null) {
            throw new IOException();
        }
        return new InputStreamReader(stream, StandardCharsets.UTF_8);
    }

    private List<Quiz> getQuizzesFrom(Reader csvReader) throws ParserException, IOException {
        List<Quiz> quizzes = new ArrayList<>();
        Iterable<CSVRecord> records = CSVFormat.RFC4180.parse(csvReader);
        int line = 1;
        for (CSVRecord record : records) {
            if (record.size() == 1) {
                throw new ParserException(pathToCsvResource + " " + WRONG_FORMAT.message(line));
            }
            if (record.size() == 2) {
                String question = record.get(0);
                String rightAnswer = record.get(1);
                if (question.isEmpty() || rightAnswer.isEmpty()) {
                    throw new ParserException(pathToCsvResource + " " + WRONG_FORMAT.message(line));
                } else {
                    quizzes.add(new Quiz(question, rightAnswer));
                }
            }
            if (record.size() > 2) {

                String question = record.get(0);
                String rightAnswer = record.get(1);
                List<String> answerOptions = record.toList().subList(2, record.size());
                if (question.isEmpty() || rightAnswer.isEmpty() || answerOptions.contains("")) {
                    throw new ParserException(pathToCsvResource + " " + WRONG_FORMAT.message(line));
                } else {
                    quizzes.add(new Quiz(question, rightAnswer, answerOptions));
                }
            }
            line++;
        }
        return quizzes;
    }
}
