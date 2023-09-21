package ru.otus.spring.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import ru.otus.spring.domain.Contact;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest()
@DisplayName("Репозиторий контактов должен")
class ContactRepositoryTest {

    @Autowired
    private ContactRepository repository;

    @Test
    @DisplayName("сохранять новый контакт и присваивать id")
    void shouldSetIdOnSave() {
        Mono<Contact> personMono = repository.save(new Contact("Bill", "12123123"));
        StepVerifier
                .create(personMono)
                .assertNext(person -> assertNotNull(person.getId()))
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("возвращать список контактов")
    void shouldReturnContactList() {
        Flux<Contact> all = repository.findAll();
        Assertions.assertEquals(2, all.count().block());
    }
}
