package com.github.ducknowledges.quiz.parser;

public enum DataParserError {

    PARSE_DATA_ERROR(": can't parse data"),
    PARSE_FORMAT_ERROR(": has wrong format");

    private final String message;

    DataParserError(String message) {
        this.message = message;
    }

    public String message(String path) {
        return path + message;
    }

    public String message(String path, String description) {
        return path + message + System.lineSeparator() + description;
    }
}
