package com.github.ducknowledges.quiz.service;

import com.github.ducknowledges.quiz.domain.Quiz;
import com.github.ducknowledges.quiz.domain.Score;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuizServiceImpl implements QuizService {
    private final CommunicationService communicationService;

    @Autowired
    public QuizServiceImpl(CommunicationService communicationService) {
        this.communicationService = communicationService;
    }

    @Override
    public Score getScoreForQuiz(List<Quiz> quizzes) {
        Score score = new Score();
        for (Quiz quiz : quizzes) {
            boolean isSuccessQuiz = isSuccessQuiz(quiz);
            score.checkAttempt(isSuccessQuiz);
        }
        return score;
    }

    private boolean isSuccessQuiz(Quiz quiz) {
        String question = quizToQuestion(quiz);
        String answer = communicationService.askToUser(question);
        return quiz.getAnswer().equals(answer);
    }

    private String quizToQuestion(Quiz quiz) {
        StringBuilder question = new StringBuilder();
        question
            .append(quiz.getQuestion());
        if (quiz.hasOptions()) {
            question
                .append(System.lineSeparator())
                .append("Options: ")
                .append(String.join(", ", quiz.getOptions()));
        }
        return question.toString();
    }
}
