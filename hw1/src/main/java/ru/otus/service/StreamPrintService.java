package ru.otus.service;

import java.io.PrintStream;

public class StreamPrintService implements PrintService {

    private final PrintStream printStream;

    public StreamPrintService(PrintStream printStream) {
        this.printStream = printStream;
    }

    public void println(String s) {
        printStream.println(s);
    }

    public void printf(String format, Object... args) {
        printStream.printf(format, args);
    }

}
