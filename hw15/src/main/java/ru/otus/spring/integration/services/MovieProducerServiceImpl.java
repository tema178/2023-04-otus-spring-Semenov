package ru.otus.spring.integration.services;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;
import ru.otus.spring.integration.domain.Genre;
import ru.otus.spring.integration.domain.Movie;
import ru.otus.spring.integration.domain.Review;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MovieProducerServiceImpl implements MovieProducerService {

    private final String[] titles = {"Child Of Stardust", "Robot Of The Dead", "Recruit Of Mars", "Guardian Of Mars",
            "Invaders With Spaceships", "Children From Outer Space", "Clones Of Life", "Martians Of The New World",
            "Emperors And Spies", "Guests And Droids", "Strangers And Traitors", "Creators And Guardians",
            "Symbols Of Moondust", "Origin Of New Life", "Restoration Of The Aliens", "Deception Of War",
            "Abandoned By A Robot Takeover", "Better The Robotic Police", "Abandoned On The Machines",
            "Light Of The Machines", "Closed For The Void", "Life With Technolic Advancements", "Alive In Stardust",
            "Inspired By The Aliens"};

    private final MovieToReviewGateway movieToReviewGateway;

    public MovieProducerServiceImpl(MovieToReviewGateway movieToReviewGateway) {
        this.movieToReviewGateway = movieToReviewGateway;
    }

    @Override
    public void startGenerateMovies() {
        ForkJoinPool pool = ForkJoinPool.commonPool();
        for (int i = 0; i < 10; i++) {
            int num = i + 1;
            pool.execute(() -> {
                Collection<Movie> items = generateMovies();
                log.info("{}, New movies: {}", num,
                        items.stream().map(Movie::getName)
                                .collect(Collectors.joining(",")));
                Collection<Review> movies = movieToReviewGateway.process(items);
                log.info("{}, Ready reviews: {}", num, movies.stream()
                        .map(Review::toString)
                        .collect(Collectors.joining(",")));
            });
            delay();
        }
    }

    public Movie generateMovie() {
        return new Movie(titles[RandomUtils.nextInt(0, titles.length)],
                Genre.values()[RandomUtils.nextInt(0, Genre.values().length)]);
    }

    private Collection<Movie> generateMovies() {
        List<Movie> movies = new ArrayList<>();
        for (int i = 0; i < RandomUtils.nextInt(1, 5); ++i) {
            movies.add(generateMovie());
        }
        return movies;
    }

    private void delay() {
        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
