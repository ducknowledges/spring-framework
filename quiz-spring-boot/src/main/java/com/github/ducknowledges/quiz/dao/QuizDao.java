package com.github.ducknowledges.quiz.dao;

import com.github.ducknowledges.quiz.domain.Quiz;
import java.util.List;

public interface QuizDao {

    List<Quiz> getQuizzes();

}
