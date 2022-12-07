package com.github.ducknowledges.quiz.parser;

import java.util.List;

public interface DataParser<T> {
    List<T> parseToRecords();
}
