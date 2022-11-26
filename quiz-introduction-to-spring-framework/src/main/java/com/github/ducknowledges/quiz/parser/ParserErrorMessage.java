package com.github.ducknowledges.quiz.parser;

public enum ParserErrorMessage {

    WRONG_FORMAT("wrong format at line"),
    RESOURCE_ERROR("resource not available");

    private final String message;

    ParserErrorMessage(String message) {
        this.message = message;
    }

    public String message(int line) {
        return message + " " + line;
    }

    public String message() {
        return message;
    }
}
