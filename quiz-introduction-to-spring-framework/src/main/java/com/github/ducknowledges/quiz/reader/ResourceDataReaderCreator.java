package com.github.ducknowledges.quiz.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class ResourceDataReaderCreator implements DataReaderCreator {

    private final String resourcePath;

    public ResourceDataReaderCreator(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    @Override
    public String getDataPath() {
        return resourcePath;
    }

    @Override
    public BufferedReader createReader() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(resourcePath);
        if (inputStream == null) {
            throw new IOException("input stream is null");
        }
        return new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
    }
}
