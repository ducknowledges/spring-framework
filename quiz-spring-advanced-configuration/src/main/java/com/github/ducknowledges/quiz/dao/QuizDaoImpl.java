package com.github.ducknowledges.quiz.dao;

import com.github.ducknowledges.quiz.domain.Quiz;
import com.github.ducknowledges.quiz.domain.Record;
import com.github.ducknowledges.quiz.parser.DataParser;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class QuizDaoImpl implements QuizDao {

    private final DataParser dataParser;

    @Autowired
    public QuizDaoImpl(DataParser dataParser) {
        this.dataParser = dataParser;
    }

    @Override
    public List<Quiz> getQuizzes() {
        List<Record> records = dataParser.parseToRecords();
        List<Quiz> quizzes = new ArrayList<>();
        for (Record record : records) {
            if (record.size() == 2) {
                Quiz quiz = new Quiz(record.getContentValue(0), record.getContentValue(1));
                quizzes.add(quiz);
            }
            if (record.size() > 2) {
                List<String> options = record.getContent().subList(2, record.size());
                Quiz quiz = new Quiz(record.getContentValue(0), record.getContentValue(1), options);
                quizzes.add(quiz);
            }
        }
        return quizzes;
    }
}
