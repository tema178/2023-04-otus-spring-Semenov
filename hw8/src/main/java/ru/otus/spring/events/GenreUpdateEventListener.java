package ru.otus.spring.events;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent;
import org.springframework.stereotype.Component;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.repository.BookRepository;

@Component
@RequiredArgsConstructor
public class GenreUpdateEventListener extends AbstractMongoEventListener<Genre> {

    private final BookRepository bookRepository;

    @Override
    public void onAfterSave(AfterSaveEvent<Genre> event) {
        super.onAfterSave(event);
        Genre source = event.getSource();
        bookRepository.updateGenre(source);
    }
}
