package ru.otus.spring.service;

import org.springframework.stereotype.Component;
import ru.otus.spring.dao.BookDao;
import ru.otus.spring.domain.Book;

import java.util.List;

@Component
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;

    public BookServiceImpl(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Override
    public Book create(Book book) {
        return bookDao.create(book);
    }

    @Override
    public boolean update(Book book) {
        return bookDao.update(book) > 0;
    }

    @Override
    public List<Book> getAll() {
        return bookDao.getAll();
    }

    @Override
    public Book getById(long id) {
        return bookDao.getById(id);
    }

    @Override
    public boolean deleteById(long id) {
        return bookDao.deleteById(id) > 0;
    }


}
