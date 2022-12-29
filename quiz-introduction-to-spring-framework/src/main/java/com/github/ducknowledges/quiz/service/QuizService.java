package com.github.ducknowledges.quiz.service;

import com.github.ducknowledges.quiz.domain.Quiz;
import java.util.List;

public interface QuizService {
    void askQuiz(List<Quiz> quizzes);
}
