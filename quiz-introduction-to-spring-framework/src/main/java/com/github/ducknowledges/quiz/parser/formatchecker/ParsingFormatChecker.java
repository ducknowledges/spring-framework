package com.github.ducknowledges.quiz.parser.formatchecker;

import java.util.List;

public interface ParsingFormatChecker<T> {
    boolean hasWrongFormattedRecords(List<T> records);

    String getDescriptionOfWrongRecords(List<T> records);
}
