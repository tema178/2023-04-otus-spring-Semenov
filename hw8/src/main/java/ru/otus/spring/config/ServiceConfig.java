package ru.otus.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.spring.service.OutputStream;

@Configuration
@SuppressWarnings("unused")
public class ServiceConfig {

    @Bean
    public OutputStream printService() {
        return new OutputStream(System.out);
    }
}
