package com.github.ducknowledges.quiz.service;

import com.github.ducknowledges.quiz.dao.QuizDao;
import com.github.ducknowledges.quiz.domain.Quiz;
import com.github.ducknowledges.quiz.domain.Score;
import com.github.ducknowledges.quiz.domain.User;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class QuizManagerServiceImpl implements QuizManagerService {

    private final QuizDao quizDao;
    private final UserService userService;
    private final QuizService quizService;
    private final ScoreService scoreService;


    public QuizManagerServiceImpl(QuizDao quizDao,
                                  UserService userService,
                                  QuizService quizService,
                                  ScoreService scoreService) {
        this.quizDao = quizDao;
        this.userService = userService;
        this.quizService = quizService;
        this.scoreService = scoreService;
    }

    @Override
    public void run() {
        List<Quiz> quizzes = quizDao.getQuizzes();
        if (!quizzes.isEmpty()) {
            User user = userService.createUser();
            Score score = quizService.getScoreForQuiz(quizzes);
            user.updateScore(score);
            scoreService.summarizeScore(user);
        }
    }
}
