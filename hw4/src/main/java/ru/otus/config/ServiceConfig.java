package ru.otus.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.service.IOServiceStreams;

@Configuration
@EnableConfigurationProperties(AppProps.class)
@SuppressWarnings("unused")
public class ServiceConfig {

    @Bean
    public IOServiceStreams printService() {
        return new IOServiceStreams(System.out, System.in);
    }
}
