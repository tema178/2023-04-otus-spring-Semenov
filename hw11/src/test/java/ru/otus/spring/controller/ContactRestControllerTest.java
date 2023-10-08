package ru.otus.spring.controller;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.domain.Contact;
import ru.otus.spring.repository.ContactRepository;

import java.time.Duration;
import java.util.List;

import static org.mockito.BDDMockito.given;

@DisplayName("Контроллер контактов должен")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ContactRestControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private ContactRepository repository;

    private final String contact1Id = ObjectId.get().toString();

    private final Contact contact1 = new Contact(contact1Id, "Ivan", "123114123");

    private final Contact contact2 = new Contact(ObjectId.get().toString(), "Artem", "23434553242");

    @DisplayName("возвращать все контакты")
    @Test
    void shouldReturnContactList() {
        given(repository.findAll()).willReturn(Flux.fromIterable(List.of(contact1, contact2)));
        var webTestClientForTest = webTestClient.mutate()
                .responseTimeout(Duration.ofSeconds(20))
                .build();

        var result = webTestClientForTest
                .get().uri("/api/contact")
                .accept(MediaType.TEXT_EVENT_STREAM)
                .exchange()
                .expectStatus().isOk()
                .returnResult(Contact.class)
                .getResponseBody();

        Assertions.assertEquals(2, result.count().block());

    }

    @DisplayName("возвращать контакт по id")
    @Test
    void shouldReturnContactById() {
        given(repository.findById(contact1Id)).willReturn(Mono.just(contact1));
        var webTestClientForTest = webTestClient.mutate()
                .responseTimeout(Duration.ofSeconds(20))
                .build();

        webTestClientForTest
                .get().uri("/api/contact/" + contact1Id)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Contact.class)
                .isEqualTo(contact1);
    }
}
