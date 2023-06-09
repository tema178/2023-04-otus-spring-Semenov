package ru.otus.service;

import java.io.PrintStream;

public class PrintService {

    private final PrintStream printStream;

    @SuppressWarnings("unused")
    public PrintService() {
        this.printStream = System.out;
    }

    public PrintService(PrintStream printStream) {
        this.printStream = printStream;
    }

    public void println(String s) {
        printStream.println(s);
    }

    public void printf(String format, Object... args) {
        printStream.printf(format, args);
    }

}
