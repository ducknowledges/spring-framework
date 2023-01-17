package com.github.ducknowledges.quiz.parser.recordchecker;

import com.github.ducknowledges.quiz.domain.Record;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class RecordCheckerImpl implements RecordChecker {

    public RecordCheckerImpl() {}

    @Override
    public List<Record> filterWrongRecords(List<Record> records) {
        return records.stream()
            .filter(this::isWrongRecord)
            .collect(Collectors.toList());
    }

    private boolean isWrongRecord(Record record) {
        return record.size() < 2
                || record.getContentValue(0).isEmpty()
                || record.getContentValue(1).isEmpty()
                || record.getContent().subList(2, record.size()).contains("");
    }
}
