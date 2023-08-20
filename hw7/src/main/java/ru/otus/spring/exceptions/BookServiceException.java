package ru.otus.spring.exceptions;

public class BookServiceException extends Exception {

    public BookServiceException(String message) {
        super(message);
    }
}
