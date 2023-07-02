package ru.otus.service;

public interface MessageLocalizationService {
    String getMessage(String messageCode, String... args);
}
