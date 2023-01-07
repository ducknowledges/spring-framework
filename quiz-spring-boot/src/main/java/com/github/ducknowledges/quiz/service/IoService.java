package com.github.ducknowledges.quiz.service;

public interface IoService {

    String read();

    void write(String message);

    void writeError(String errorMessage);
}
