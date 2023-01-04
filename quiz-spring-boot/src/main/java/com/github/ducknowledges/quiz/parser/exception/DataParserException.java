package com.github.ducknowledges.quiz.parser.exception;

public class DataParserException extends RuntimeException {
    public DataParserException(String message) {
        super(message);
    }

    public DataParserException(String message, Throwable cause) {
        super(message, cause);
    }
}
