package com.github.ducknowledges.quiz.parser.exception;

public class DataParserException extends Exception {

    public enum ParserError {

        ACCESS_ERROR("parsed data not available"),
        RECORD_ERROR("parsed data has wrong format lines"),
        PARSE_ERROR("can't parse data");
        private final String message;

        ParserError(String message) {
            this.message = message;
        }

        public String message() {
            return this.message;
        }

        public String message(String prefix) {
            return prefix + " " + this.message;
        }
    }

    public DataParserException(ParserError error) {
        super(error.message() + System.lineSeparator());
    }

    public DataParserException(ParserError error, String description) {
        super(error.message() + System.lineSeparator() + description);
    }

    public String getMessage(String prefix) {
        return prefix + " " + this.getMessage();
    }
}
