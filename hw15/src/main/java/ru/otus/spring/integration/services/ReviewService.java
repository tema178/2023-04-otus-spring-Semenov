package ru.otus.spring.integration.services;

import ru.otus.spring.integration.domain.Movie;
import ru.otus.spring.integration.domain.Review;

public interface ReviewService {

    Review makeReview(Movie movie);
}
