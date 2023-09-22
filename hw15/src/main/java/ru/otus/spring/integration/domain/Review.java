package ru.otus.spring.integration.domain;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Review {

    @NotNull
    private final Movie movie;

    @DecimalMin(value = "0", message = "Rate couldn't be negative")
    @DecimalMax(value = "10", message = "Max rate is ten")
    @Digits(integer = 2, fraction = 1, message = "Rate could have only 1 digit after point")
    private final double rating;

    private final boolean reviewerForKinopoisk;

    @Override
    public String toString() {
        return "Review{" +
                "movie=" + movie.getName() +
                ", rating=" + rating +
                ", reviewerForKinopoisk=" + reviewerForKinopoisk +
                '}';
    }
}
