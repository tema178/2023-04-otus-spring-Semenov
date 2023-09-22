package ru.otus.spring.integration.services;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;
import ru.otus.spring.integration.domain.Movie;
import ru.otus.spring.integration.domain.Review;

@Service
@Slf4j
public class ReviewServiceImpl implements ReviewService {
    @Override
    public Review makeReview(Movie movie) {
        log.info("Start making review to {}", movie.getName());
        delay();
        log.info("Review to {} done", movie.getName());
        return new Review(movie, Math.floor(RandomUtils.nextDouble(0, 10) * 10.0) / 10.0,
                RandomUtils.nextBoolean());
    }

    private static void delay() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
