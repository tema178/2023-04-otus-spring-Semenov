package ru.otus.example.springbatch.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class FillCacheService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @SuppressWarnings("unused")
    public void fillCache(String tableName, Map<String, Long> cache) {
        jdbcTemplate.query(
                        String.format(
                                "Select id, mongo_id from %s where mongo_id is NOT null", tableName),
                        (rs, rowNum) -> Pair.of(rs.getString("mongo_id"), rs.getLong("id")))
                .forEach(pair -> cache.put(pair.getFirst(), pair.getSecond()));
    }
}
