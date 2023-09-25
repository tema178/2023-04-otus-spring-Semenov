package ru.otus.spring.integration.domain;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ReviewTag {

    private final Genre genre;

    private final Tag tag;

    public enum Tag {
        THE_BEST,
        GOOD,
        NORMAL,
        BAD
    }

    @Override
    public String toString() {
        return "ReviewTag{" +
                "genre=" + genre +
                ", tag=" + tag +
                '}';
    }
}
