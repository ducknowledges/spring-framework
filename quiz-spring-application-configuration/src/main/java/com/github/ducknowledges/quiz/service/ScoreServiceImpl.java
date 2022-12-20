package com.github.ducknowledges.quiz.service;

import com.github.ducknowledges.quiz.domain.Score;
import com.github.ducknowledges.quiz.domain.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ScoreServiceImpl implements ScoreService {

    private final int successScore;
    private final CommunicationService communicationService;

    public ScoreServiceImpl(@Value("${user.success.score}") int successScore,
                            CommunicationService communicationService) {
        this.successScore = successScore;
        this.communicationService = communicationService;
    }

    public void summarizeScore(User user) {
        Score score = user.getScore();
        StringBuilder scoreReport = new StringBuilder()
            .append(user.getFullName())
            .append(" ");
        if (score.getSuccessAttempts() >= successScore) {
            scoreReport.append("Congratulations! You pass the quiz");
        } else {
            scoreReport.append("Sorry! You fail the quiz");
        }
        scoreReport
            .append(System.lineSeparator())
            .append(score);
        communicationService.reportToUser(scoreReport.toString());
    }
}
