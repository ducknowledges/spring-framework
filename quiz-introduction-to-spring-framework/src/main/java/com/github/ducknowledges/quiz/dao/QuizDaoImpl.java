package com.github.ducknowledges.quiz.dao;

import com.github.ducknowledges.quiz.domain.Quiz;
import com.github.ducknowledges.quiz.parser.DataParser;
import java.util.ArrayList;
import java.util.List;

public class QuizDaoImpl implements QuizDao {

    private final DataParser<List<String>> dataParser;

    public QuizDaoImpl(DataParser<List<String>> dataParser) {
        this.dataParser = dataParser;
    }

    @Override
    public List<Quiz> getQuizzes() {
        List<List<String>> records = dataParser.parseToRecords();
        List<Quiz> quizzes = new ArrayList<>();
        for (List<String> record : records) {
            if (record.size() == 2) {
                Quiz quiz = new Quiz(record.get(0), record.get(1));
                quizzes.add(quiz);
            }
            if (record.size() > 2) {
                List<String> options = record.subList(2, record.size());
                Quiz quiz = new Quiz(record.get(0), record.get(1), options);
                quizzes.add(quiz);
            }
        }
        return quizzes;
    }
}
