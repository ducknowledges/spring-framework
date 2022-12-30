package com.github.ducknowledges.quiz.service;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class IoServiceStream implements IoService {

    private final PrintStream output;
    private final PrintStream error;

    public IoServiceStream(OutputStream output, OutputStream error) {
        this.output = new PrintStream(output);
        this.error = new PrintStream(error);
    }

    @Override
    public void write(String message) {
        output.println(message);
    }

    @Override
    public void writeError(String errorMessage) {
        error.println(errorMessage);
    }
}
