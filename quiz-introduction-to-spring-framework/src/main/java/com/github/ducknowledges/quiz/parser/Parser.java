package com.github.ducknowledges.quiz.parser;

import com.github.ducknowledges.quiz.domain.Quiz;
import java.util.List;

public interface Parser {
    List<Quiz> parseToQuizzes();
}
