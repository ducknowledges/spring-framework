package com.github.ducknowledges.quiz.service;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class IoServiceStream implements IoService {

    private final Scanner input;
    private final PrintStream output;
    private final PrintStream error;

    public IoServiceStream(InputStream input, OutputStream output, OutputStream error) {
        this.input = new Scanner(input);
        this.output = new PrintStream(output);
        this.error = new PrintStream(error);
    }

    @Override
    public String read() {
        return input.nextLine();
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
