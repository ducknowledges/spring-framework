package com.github.ducknowledges.quiz.service;

import com.github.ducknowledges.quiz.dao.QuizDao;
import com.github.ducknowledges.quiz.domain.Quiz;
import java.util.List;

public class QuizServiceImpl implements QuizService {

    private final QuizDao quizDao;

    public QuizServiceImpl(QuizDao quizDao) {
        this.quizDao = quizDao;
    }

    @Override
    public void run() {
        List<Quiz> quizzes = quizDao.getQuizzes();
        quizzes.forEach((Quiz quiz) -> {
            System.out.println(quiz.getQuestion());
            if (!quiz.getAnswerOptions().isEmpty()) {
                System.out.println("Options: " + String.join(",", quiz.getAnswerOptions()));
            }
        });
    }
}
