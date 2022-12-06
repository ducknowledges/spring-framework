package com.github.ducknowledges.quiz.parser.formatchecker;

import static com.github.ducknowledges.quiz.parser.formatchecker.ParsingFormatError.WRONG_FORMAT;

import java.util.List;
import org.apache.commons.csv.CSVRecord;

public class ParsingFormatCheckerCsv implements ParsingFormatChecker<CSVRecord> {

    public ParsingFormatCheckerCsv() {}

    @Override
    public boolean hasWrongFormattedRecords(List<CSVRecord> records) {
        for (CSVRecord record : records) {
            if (isWrongFormattedRecord(record)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getDescriptionOfWrongRecords(List<CSVRecord> records) {
        StringBuilder wrongFormatMessageBuilder = new StringBuilder();
        for (CSVRecord record : records) {
            if (isWrongFormattedRecord(record)) {
                long recordLine = record.getRecordNumber();
                wrongFormatMessageBuilder
                    .append(WRONG_FORMAT.message(recordLine))
                    .append(System.lineSeparator());
            }
        }
        return wrongFormatMessageBuilder.toString();
    }

    private boolean isWrongFormattedRecord(CSVRecord record) {
        return record.size() < 2
                || record.get(0).isEmpty()
                || record.get(1).isEmpty()
                || record.toList().subList(2, record.size()).contains("");
    }
}
