package ru.otus.spring.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.domain.Contact;
import ru.otus.spring.repository.ContactFluxRepository;

@RestController
public class ContactRestController {

    private final ContactFluxRepository repository;


    public ContactRestController(ContactFluxRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/api/contact")
    public Flux<Contact> getAll() {
        return repository.findAll();
    }

    @PutMapping("/api/contact")
    public Mono<Contact> save(@RequestBody Contact contact) {
        return repository.save(contact);
    }

    @GetMapping("/api/contact/{id}")
    public Mono<ResponseEntity<Contact>> getById(@PathVariable String id) {
        return repository.findById(id).map(ResponseEntity::ok)
                .switchIfEmpty(Mono.fromCallable(() -> ResponseEntity.notFound().build()));
    }

    @DeleteMapping("/api/contact/{id}")
    public void delete(@PathVariable String id) {
        repository.deleteById(id);
    }
}
