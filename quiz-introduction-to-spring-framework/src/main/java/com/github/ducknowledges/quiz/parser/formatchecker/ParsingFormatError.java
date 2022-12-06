package com.github.ducknowledges.quiz.parser.formatchecker;

public enum ParsingFormatError {

    WRONG_FORMAT("wrong format at line: ");
    private final String message;

    ParsingFormatError(String message) {
        this.message = message;
    }

    public String message(long line) {
        return message + line;
    }
}
