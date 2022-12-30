package com.github.ducknowledges.quiz.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Class IoServiceStream")
class IoServiceStreamTest {

    private OutputStream output;
    private OutputStream error;

    private IoService ioService;

    @BeforeEach
    void setUp() {
        output = new ByteArrayOutputStream();
        error = new ByteArrayOutputStream();
        ioService = new IoServiceStream(output, error);
    }


    @Test
    @DisplayName("should write string to output stream")
    void write() {
        String expected = "message" + System.lineSeparator();
        ioService.write("message");
        assertThat(output.toString()).isEqualTo(expected);
    }

    @Test
    @DisplayName("should write string to error stream")
    void writeError() {
        String expected = "error message" + System.lineSeparator();
        ioService.writeError("error message");
        assertThat(error.toString()).isEqualTo(expected);
    }
}