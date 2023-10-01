package ru.otus.spring.integration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.otus.spring.integration.services.MovieProducerService;

@Slf4j
@SpringBootApplication
public class App {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(App.class, args);

        MovieProducerService movieProducerService = ctx.getBean(MovieProducerService.class);
        movieProducerService.startGenerateMovies();
    }
}
