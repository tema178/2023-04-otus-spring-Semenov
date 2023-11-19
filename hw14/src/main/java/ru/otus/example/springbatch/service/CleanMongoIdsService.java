package ru.otus.example.springbatch.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CleanMongoIdsService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @SuppressWarnings("unused")
    public void cleanIds() {
        jdbcTemplate.update("""
                ALTER TABLE AUTHORS DROP COLUMN MONGO_ID;
                ALTER TABLE GENRES DROP COLUMN MONGO_ID;
                ALTER TABLE BOOKS DROP COLUMN MONGO_ID;
                """);
    }
}
