package com.github.ducknowledges.quiz.domain;

import java.util.List;
import java.util.Objects;

public class Record {

    private final List<String> content;
    private final long recordNumber;

    public Record(List<String> content, long recordNumber) {
        this.content = content;
        this.recordNumber = recordNumber;
    }

    public int size() {
        return content.size();
    }

    public String getContentValue(int index) {
        return content.get(index);
    }

    public List<String> getContent() {
        return content;
    }

    public long getRecordNumber() {
        return recordNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Record record = (Record) o;
        return Objects.equals(content, record.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content);
    }

    @Override
    public String toString() {
        return "Record{"
            + "content=" + content
            + ", recordNumber=" + recordNumber
            + '}';
    }
}
