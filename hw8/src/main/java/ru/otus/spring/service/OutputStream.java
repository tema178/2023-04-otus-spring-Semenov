package ru.otus.spring.service;

import java.io.PrintStream;

public class OutputStream implements OutputService {
    private final PrintStream output;

    public OutputStream(PrintStream outputStream) {
        output = outputStream;
    }

    @Override
    public void outputString(String s) {
        output.println(s);
    }
}
