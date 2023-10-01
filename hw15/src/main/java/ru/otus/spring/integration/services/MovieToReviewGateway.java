package ru.otus.spring.integration.services;


import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.spring.integration.domain.Movie;
import ru.otus.spring.integration.domain.Review;

import java.util.Collection;

@MessagingGateway
public interface MovieToReviewGateway {

    @Gateway(requestChannel = "movieChannel", replyChannel = "reviewChannel")
    Collection<Review> process(Collection<Movie> orderItem);
}
