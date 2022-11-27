package com.github.ducknowledges.quiz.dao;

import com.github.ducknowledges.quiz.domain.Quiz;
import com.github.ducknowledges.quiz.parser.Parser;
import java.util.List;

public class QuizDaoCsv implements QuizDao {

    private final Parser parser;

    public QuizDaoCsv(Parser parser) {
        this.parser = parser;
    }

    @Override
    public List<Quiz> getQuizzes() {
        return parser.parseToQuizzes();
    }
}
