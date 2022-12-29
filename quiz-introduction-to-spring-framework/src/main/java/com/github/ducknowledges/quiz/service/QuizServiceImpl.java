package com.github.ducknowledges.quiz.service;

import com.github.ducknowledges.quiz.domain.Quiz;
import java.util.List;

public class QuizServiceImpl implements QuizService {
    private final CommunicationService communicationService;

    public QuizServiceImpl(CommunicationService communicationService) {
        this.communicationService = communicationService;
    }

    @Override
    public void askQuiz(List<Quiz> quizzes) {
        for (Quiz quiz : quizzes) {
            String question = quizToQuestion(quiz);
            communicationService.reportToUser(question);
        }
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
