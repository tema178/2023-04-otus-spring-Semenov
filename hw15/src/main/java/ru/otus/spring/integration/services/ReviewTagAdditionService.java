package ru.otus.spring.integration.services;

import org.springframework.stereotype.Service;
import ru.otus.spring.integration.domain.Review;
import ru.otus.spring.integration.domain.ReviewTag;

import static ru.otus.spring.integration.domain.ReviewTag.Tag.BAD;
import static ru.otus.spring.integration.domain.ReviewTag.Tag.GOOD;
import static ru.otus.spring.integration.domain.ReviewTag.Tag.NORMAL;
import static ru.otus.spring.integration.domain.ReviewTag.Tag.THE_BEST;

@Service
public class ReviewTagAdditionService implements AdditionInfoService {

    @Override
    public Review addInfo(Review review) {
        if (review.getRating() > 8) {
            review.setReviewTag(new ReviewTag(review.getMovie().getGenre(), THE_BEST));
        } else if (review.getRating() > 7) {
            review.setReviewTag(new ReviewTag(review.getMovie().getGenre(), GOOD));
        } else if (review.getRating() > 5) {
            review.setReviewTag(new ReviewTag(review.getMovie().getGenre(), NORMAL));
        } else {
            review.setReviewTag(new ReviewTag(review.getMovie().getGenre(), BAD));
        }
        return review;
    }
}
