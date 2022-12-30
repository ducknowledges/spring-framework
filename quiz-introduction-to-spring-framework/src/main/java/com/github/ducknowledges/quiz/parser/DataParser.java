package com.github.ducknowledges.quiz.parser;

import com.github.ducknowledges.quiz.domain.Record;
import java.util.List;

public interface DataParser {
    List<Record> parseToRecords();
}
