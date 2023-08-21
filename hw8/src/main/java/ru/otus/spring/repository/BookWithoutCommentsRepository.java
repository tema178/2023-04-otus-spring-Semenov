package ru.otus.spring.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.domain.BookWithoutComments;

import java.util.List;

public interface BookWithoutCommentsRepository extends MongoRepository<BookWithoutComments, String> {

    @Override
    List<BookWithoutComments> findAll();
}
