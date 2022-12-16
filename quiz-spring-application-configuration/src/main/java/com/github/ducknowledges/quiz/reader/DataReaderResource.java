package com.github.ducknowledges.quiz.reader;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DataReaderResource implements DataReader {

    private final String resourcePath;

    @Autowired
    public DataReaderResource(@Value("${quiz.resource.path}") String resourcePath) {
        this.resourcePath = resourcePath;
    }

    @Override
    public String getDataPath() {
        return resourcePath;
    }

    @Override
    public Optional<Reader> createReader() {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(resourcePath);
        if (inputStream == null) {
            return Optional.empty();
        }
        return Optional.of(
            new BufferedReader(
                new InputStreamReader(inputStream, StandardCharsets.UTF_8)));
    }
}
