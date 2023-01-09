package com.github.ducknowledges.quiz.service;

import com.github.ducknowledges.quiz.domain.Quiz;
import com.github.ducknowledges.quiz.domain.Score;
import java.util.List;

public interface QuizService {
    Score getScoreForQuiz(List<Quiz> quizzes);
}
