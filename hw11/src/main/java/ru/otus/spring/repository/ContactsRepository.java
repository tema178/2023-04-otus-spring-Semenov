package ru.otus.spring.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.domain.Contact;

public interface ContactsRepository extends MongoRepository<Contact, String> {
}
