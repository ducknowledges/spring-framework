package com.github.ducknowledges.quiz.parser.exception;

public class DataParserRecordException extends Exception {
    public enum ParserError {

        RECORD_ERROR("parsed data has wrong format lines");
        private final String message;

        ParserError(String message) {
            this.message = message;
        }

        public String message() {
            return this.message;
        }
    }

    public DataParserRecordException(ParserError error, String description) {
        super(error.message() + " " + description);
    }

}
