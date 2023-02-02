package com.github.ducknowledges.bookstore.service;

import com.github.ducknowledges.bookstore.dao.BookDao;
import com.github.ducknowledges.bookstore.domain.Book;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;

    public BookServiceImpl(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Override
    @Transactional
    public Book createBook(Book book) {
        return bookDao.save(book);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Book> getBook(long id) {
        return bookDao.readById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> getBooks(int from, int size) {
        return bookDao.readAll(from, size);
    }

    @Override
    @Transactional
    public Book update(Book book) {
        return bookDao.save(book);
    }

    @Override
    @Transactional
    public void delete(long id) {
        bookDao.delete(id);
    }
}
