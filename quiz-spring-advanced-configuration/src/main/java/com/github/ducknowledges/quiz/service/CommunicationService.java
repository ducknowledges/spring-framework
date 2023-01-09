package com.github.ducknowledges.quiz.service;

public interface CommunicationService {
    String askToUser(String string);

    void reportToUser(String string);

    void reportErrorToUser(String string);
}
