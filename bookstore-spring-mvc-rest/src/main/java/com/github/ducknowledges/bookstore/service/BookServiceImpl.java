package com.github.ducknowledges.bookstore.service;

import com.github.ducknowledges.bookstore.dao.BookDao;
import com.github.ducknowledges.bookstore.domain.Book;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;

    public BookServiceImpl(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Override
    public Book create(Book book) {
        return bookDao.save(book);
    }

    @Override
    public Optional<Book> getBook(long id) {
        return bookDao.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Book> getBooks(Pageable pageable) {
        return bookDao.findAll(pageable);
    }

    @Override
    public Book save(Book book) {
        return bookDao.save(book);
    }

    @Override
    public void deleteById(long id) {
        bookDao.deleteById(id);
    }

    @Override
    public long count() {
        return bookDao.count();
    }
}
