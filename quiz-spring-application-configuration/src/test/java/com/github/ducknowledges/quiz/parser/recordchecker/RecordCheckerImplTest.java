package com.github.ducknowledges.quiz.parser.recordchecker;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.ducknowledges.quiz.domain.Record;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Class RecordCheckerImpl")
class RecordCheckerImplTest {

    private static List<Record> correctRecords;
    private static List<Record> wrongRecords;
    private static RecordChecker formatChecker;


    @BeforeAll
    static void setUpAll() {
        Record record1 = getRecord(
            List.of("Is this a question with options?", "yes", "yes", "no"),
            1
        );
        Record record2 = getRecord(List.of("", "yes", "yes", "no"), 2);
        Record record3 = getRecord(List.of("Is this a question with options?", "", "yes", "no"), 3);
        Record record4 = getRecord(List.of("Is this a question?", "yes", "", "no"), 4);
        Record record5 = getRecord(List.of("Is this a question?"), 5);
        correctRecords = List.of(record1);
        wrongRecords = List.of(record2, record3, record4, record5);
        formatChecker = new RecordCheckerImpl();
    }

    private static Record getRecord(List<String> strings, int recordNumber) {
        return new Record(strings, recordNumber);
    }

    @Test
    @DisplayName("should return filtered records with wrong records")
    void shouldHasExceptionMessage() {
        List<Record> records = new ArrayList<>(correctRecords);
        records.addAll(wrongRecords);
        List<Record> actual = formatChecker.filterWrongRecords(records);
        assertThat(actual).isEqualTo(wrongRecords);
    }

    @Test
    @DisplayName("should return empty list of records")
    void shouldNotThrowException() {
        assertThat(formatChecker.filterWrongRecords(correctRecords)).isEmpty();
    }
}