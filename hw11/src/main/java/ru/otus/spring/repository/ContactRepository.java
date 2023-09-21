package ru.otus.spring.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;
import ru.otus.spring.domain.Contact;

public interface ContactRepository extends ReactiveMongoRepository<Contact, String> {

    Mono<Contact> save(Mono<Contact> contacts);
}
