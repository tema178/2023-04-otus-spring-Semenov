package ru.otus.spring.actuators;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import ru.otus.spring.repository.BookRepository;

@Component
@RequiredArgsConstructor
public class LibrarySizeHealthIndicator implements HealthIndicator {

    private final BookRepository bookRepository;

    @Override
    public Health health() {
        long librarySize = bookRepository.count();
        return librarySize > 0 ? Health.up().withDetail("message", "Library size: " + librarySize).build() :
                Health.down().withDetail("message", "Books not found").build();
    }
}
