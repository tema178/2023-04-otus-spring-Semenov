package ru.otus.example.springbatch.listeners;

import org.slf4j.Logger;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.batch.item.Chunk;

public class ItemWriteListenerImpl<T> implements ItemWriteListener<T> {

    private final Logger logger;

    public ItemWriteListenerImpl(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void afterWrite(Chunk<? extends T> items) {
        ItemWriteListener.super.afterWrite(items);
        logger.info("Начало записи");
    }

    @Override
    public void onWriteError(Exception exception, Chunk<? extends T> items) {
        ItemWriteListener.super.onWriteError(exception, items);
        logger.info("Ошибка записи");
    }

    @Override
    public void beforeWrite(Chunk<? extends T> items) {
        ItemWriteListener.super.beforeWrite(items);
        logger.info("Конец записи");
    }
}
