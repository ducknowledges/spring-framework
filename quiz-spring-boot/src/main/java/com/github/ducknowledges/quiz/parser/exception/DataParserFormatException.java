package com.github.ducknowledges.quiz.parser.exception;

public class DataParserFormatException extends DataParserException {
    public DataParserFormatException(String message) {
        super("Parsed data has wrong formatted lines " + message);
    }
}
