package ru.otus.spring.integration.services;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;
import ru.otus.spring.integration.domain.Review;

@Service
public class RKNService implements ReviewControlService {

    @Override
    public Review controlReview(Review review) {
        if (review.isReviewerForKinopoisk()) {
            switch (review.getMovie().getCountry()) {
                case RUSSIA -> {
                    if (review.getRating() > 7) {
                        return review;
                    }
                    return new Review(review.getMovie(), Math.floor(RandomUtils.nextDouble(7, 10) * 10.0) / 10.0,
                            review.isReviewerForKinopoisk());
                }
                case USA -> {
                    if (review.getRating() < 7) {
                        return review;
                    }
                    return new Review(review.getMovie(), Math.floor(RandomUtils.nextDouble(0, 7) * 10.0) / 10.0,
                            review.isReviewerForKinopoisk());
                }
                default -> {
                    return review;
                }
            }
        }
        return review;
    }
}
