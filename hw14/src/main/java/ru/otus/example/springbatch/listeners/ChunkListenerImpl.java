package ru.otus.example.springbatch.listeners;

import org.slf4j.Logger;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.lang.NonNull;

public class ChunkListenerImpl implements ChunkListener {

    private final Logger logger;

    public ChunkListenerImpl(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void beforeChunk(@NonNull ChunkContext chunkContext) {
        logger.info("Начало пачки");
    }

    @Override
    public void afterChunk(@NonNull ChunkContext chunkContext) {
        logger.info("Конец пачки");
    }

    @Override
    public void afterChunkError(@NonNull ChunkContext chunkContext) {
        logger.info("Ошибка пачки");
    }
}
