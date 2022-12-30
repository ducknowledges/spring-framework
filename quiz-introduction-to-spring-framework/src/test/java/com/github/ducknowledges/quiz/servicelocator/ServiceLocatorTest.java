package com.github.ducknowledges.quiz.servicelocator;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.ducknowledges.quiz.service.IoService;
import com.github.ducknowledges.quiz.service.IoServiceStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Class ServiceLocator")
class ServiceLocatorTest {

    private static IoService ioService;
    private static ByteArrayOutputStream out;
    private static ByteArrayOutputStream err;

    @BeforeAll
    static void setUp() {
        out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        err = new ByteArrayOutputStream();
        System.setErr(new PrintStream(err));
        ioService = new ServiceLocator().createIoServiceInstance();
    }

    @Test
    @DisplayName("should create instance of ioService")
    void createIoServiceInstance() {
        assertThat(ioService).isInstanceOf(IoServiceStream.class);
    }

    @Test
    @DisplayName("created instance of ioService should write to System.out")
    void shouldWriteToSystemOut() {
        String expected = "output" + System.lineSeparator();
        ioService.write("output");

        assertThat(out.toString()).isEqualTo(expected);
    }

    @Test
    @DisplayName("created instance of ioService should write to System.err")
    void shouldWriteToSystemErr() {
        String expected = "error" + System.lineSeparator();
        ioService.writeError("error");

        assertThat(err.toString()).isEqualTo(expected);
    }
}