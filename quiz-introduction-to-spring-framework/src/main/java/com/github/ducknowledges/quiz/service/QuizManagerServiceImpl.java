package com.github.ducknowledges.quiz.service;

import com.github.ducknowledges.quiz.dao.QuizDao;
import com.github.ducknowledges.quiz.domain.Quiz;
import java.util.List;

public class QuizManagerServiceImpl implements QuizManagerService {

    private final QuizDao quizDao;
    private final QuizService quizService;

    public QuizManagerServiceImpl(QuizDao quizDao,
                                  QuizService quizService) {
        this.quizDao = quizDao;
        this.quizService = quizService;
    }

    @Override
    public void run() {
        List<Quiz> quizzes = quizDao.getQuizzes();
        if (!quizzes.isEmpty()) {
            quizService.askQuiz(quizzes);
        }
    }
}
