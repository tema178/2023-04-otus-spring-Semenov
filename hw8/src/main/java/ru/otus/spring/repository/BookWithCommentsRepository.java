package ru.otus.spring.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.domain.BookWithComments;

public interface BookWithCommentsRepository extends MongoRepository<BookWithComments, String> {
}
