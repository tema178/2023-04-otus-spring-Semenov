package ru.otus.example.springbatch.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ReferenceConstraintAddingService {

    private final JdbcTemplate template;

    public ReferenceConstraintAddingService(JdbcTemplate template) {
        this.template = template;
    }

    @SuppressWarnings("unused")
    public void addCommentToBookReference() {
        template.update("ALTER TABLE COMMENTS ADD foreign key (BOOK_ID) REFERENCES BOOKS(id)");
    }
}
