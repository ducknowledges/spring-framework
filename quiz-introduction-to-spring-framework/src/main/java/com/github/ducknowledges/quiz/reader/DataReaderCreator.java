package com.github.ducknowledges.quiz.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Path;

public interface DataReaderCreator {
    BufferedReader createReader() throws IOException;

    String getDataPath();
}
