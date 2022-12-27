package com.github.ducknowledges.quiz.parser.recordchecker;

import com.github.ducknowledges.quiz.domain.Record;
import java.util.List;

public interface RecordChecker {
    List<Record> filterWrongRecords(List<Record> records);
}
