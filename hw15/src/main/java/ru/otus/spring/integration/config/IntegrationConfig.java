package ru.otus.spring.integration.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.MessageChannelSpec;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.PollerSpec;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.scheduling.PollerMetadata;
import ru.otus.spring.integration.services.AdditionInfoService;
import ru.otus.spring.integration.services.ReviewService;

@Configuration
public class IntegrationConfig {

    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerSpec poller() {
        return Pollers.fixedRate(100).maxMessagesPerPoll(2);
    }

    @Bean
    public MessageChannelSpec<?, ?> movieChannel() {
        return MessageChannels.queue(10);
    }

    @Bean
    public MessageChannelSpec<?, ?> reviewChannel() {
        return MessageChannels.publishSubscribe();
    }

    @Bean
    public IntegrationFlow movieReviewFlow(ReviewService reviewService, AdditionInfoService controlService) {
        return IntegrationFlow.from(movieChannel())
                .split()
                .handle(reviewService, "makeReview")
                .transform(controlService, "addInfo")
                .aggregate()
                .channel(reviewChannel())
                .get();
    }
}
