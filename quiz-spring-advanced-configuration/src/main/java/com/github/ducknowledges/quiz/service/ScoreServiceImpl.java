package com.github.ducknowledges.quiz.service;

import com.github.ducknowledges.quiz.config.AppProps;
import com.github.ducknowledges.quiz.domain.Score;
import com.github.ducknowledges.quiz.domain.User;
import org.springframework.stereotype.Service;

@Service
public class ScoreServiceImpl implements ScoreService {

    private final int successScore;
    private final MessageService messageService;
    private final CommunicationService communicationService;

    public ScoreServiceImpl(AppProps props,
                            MessageService messageService,
                            CommunicationService communicationService) {
        this.successScore = props.getSuccessScore();
        this.messageService = messageService;
        this.communicationService = communicationService;
    }

    public void summarizeScore(User user) {
        String scoreReport = getSummarizeMessage(user)
            + System.lineSeparator()
            + getScoreResultMessage(user);
        communicationService.reportToUser(scoreReport);
    }

    private String getSummarizeMessage(User user) {
        Score score = user.getScore();
        String messageCode;
        if (score.getSuccessAttempts() >= successScore) {
            messageCode = "score.success";
        } else {
            messageCode = "score.fail";
        }
        return messageService.getMessage(messageCode, new String[] {user.getFullName()});
    }

    private String getScoreResultMessage(User user) {
        Score score = user.getScore();
        String successAttempts = String.valueOf(score.getSuccessAttempts());
        String failingAttempts = String.valueOf(score.getFailingAttempts());
        return messageService.getMessage(
            "score.result",
            new String[]{successAttempts, failingAttempts});
    }
}
