package com.github.ducknowledges.quiz.reader;

import java.io.Reader;
import java.util.Optional;

public interface DataReader {
    Optional<Reader> createReader();

    String getDataPath();
}
