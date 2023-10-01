package ru.otus.spring.integration.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Movie {

    private final String name;

    private final Genre genre;
}
