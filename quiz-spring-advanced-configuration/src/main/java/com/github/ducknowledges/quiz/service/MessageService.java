package com.github.ducknowledges.quiz.service;

public interface MessageService {

    String getMessage(String messageCode);

    String getMessage(String messageCode, String[] args);
}
