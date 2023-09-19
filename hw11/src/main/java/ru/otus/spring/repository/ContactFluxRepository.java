package ru.otus.spring.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.domain.Contact;

public interface ContactFluxRepository extends ReactiveMongoRepository<Contact, String> {

    Mono<Contact> save(Mono<Contact> contacts);

    Flux<Contact> findAllByName(String name);
}
