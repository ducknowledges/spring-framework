package com.github.ducknowledges.quiz.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Class Record")
class RecordTest {

    private List<String> content;
    private Record record;
    private long recordNumber;


    @BeforeEach
    void setUp() {
        content = List.of("Is this a question?", "yes", "yes", "no");
        recordNumber = 1L;
        record = new Record(content, recordNumber);
    }

    @Test
    @DisplayName("correctly created by the constructor with 2 arguments")
    void shouldHaveCorrectConstructorWithThreeArguments() {
        assertAll(
            () -> assertThat(record.size()).isEqualTo(4),
            () -> assertThat(record.getContentValue(0)).isEqualTo("Is this a question?"),
            () -> assertThat(record.getRecordNumber()).isEqualTo(1),
            () -> assertThat(record.getContent()).isEqualTo(content)
        );
    }

    @Test
    @DisplayName("has correctly hashCode")
    void shouldHaveCorrectHashCode() {
        int hashCode = Objects.hash(content);
        assertThat(record.hashCode()).isEqualTo(hashCode);
    }

    @Test
    @DisplayName("correctly converted to string")
    void shouldHaveCorrectToSting() {
        String string = "Record{"
            + "content=" + content
            + ", recordNumber=" + recordNumber
            + '}';
        assertThat(record.toString()).isEqualTo(string);
    }
}