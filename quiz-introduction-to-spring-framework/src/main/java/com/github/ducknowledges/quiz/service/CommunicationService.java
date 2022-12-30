package com.github.ducknowledges.quiz.service;

public interface CommunicationService {
    void reportToUser(String string);

    void reportErrorToUser(String string);
}
