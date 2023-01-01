package com.github.ducknowledges.quiz.parser.exception;

public class DataParserException extends Exception {

    public enum ParserError {

        PARSE_ERROR("can't parse data");
        private final String message;

        ParserError(String message) {
            this.message = message;
        }

        public String message() {
            return this.message;
        }
    }

    public DataParserException(ParserError error, String description) {
        super(error.message() + " " + description);
    }
}
