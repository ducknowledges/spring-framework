package com.github.ducknowledges.quiz.service;

import com.github.ducknowledges.quiz.domain.Quiz;
import com.github.ducknowledges.quiz.domain.Score;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuizServiceImpl implements QuizService {

    private final MessageService messageService;
    private final CommunicationService communicationService;

    @Autowired
    public QuizServiceImpl(MessageService messageService,
                           CommunicationService communicationService) {
        this.messageService = messageService;
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
        return Objects.equals(quiz.getAnswer(), answer);
    }

    private String quizToQuestion(Quiz quiz) {
        StringBuilder question = new StringBuilder();
        question
            .append(quiz.getQuestion());
        if (quiz.hasOptions()) {
            question
                .append(System.lineSeparator())
                .append(messageService.getMessage("quiz.options"))
                .append(String.join(", ", quiz.getOptions()));
        }
        return question.toString();
    }
}
