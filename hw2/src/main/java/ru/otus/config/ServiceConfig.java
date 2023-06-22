package ru.otus.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.service.IOServiceStreams;
import ru.otus.service.OutputService;

@Configuration
@SuppressWarnings("unused")
public class ServiceConfig {

    @Bean
    public OutputService printService(){
        return new IOServiceStreams(System.out, System.in);
    }
}
