package com.github.ducknowledges.quiz.parser.exception;

public class DataParserReadException extends DataParserException {

    public DataParserReadException(String message) {
        super("Can't read data " + message);
    }

    public DataParserReadException(String message, Throwable cause) {
        super("Can't read data " + message, cause);
    }
}
