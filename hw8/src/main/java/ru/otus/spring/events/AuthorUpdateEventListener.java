package ru.otus.spring.events;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent;
import org.springframework.stereotype.Component;
import ru.otus.spring.domain.Author;
import ru.otus.spring.repository.BookRepository;

@Component
@RequiredArgsConstructor
public class AuthorUpdateEventListener extends AbstractMongoEventListener<Author> {

    private final BookRepository bookRepository;

    @Override
    public void onAfterSave(AfterSaveEvent<Author> event) {
        super.onAfterSave(event);
        Author source = event.getSource();
        bookRepository.updateAuthor(source);
    }
}
