package com.github.ducknowledges.bookstore.service;

import com.github.ducknowledges.bookstore.dao.BookCommentDao;
import com.github.ducknowledges.bookstore.dao.BookDao;
import com.github.ducknowledges.bookstore.domain.Book;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
        return bookDao.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Book> getBooks(int page, int size) {
        return bookDao.findAll(PageRequest.of(page, size));
    }

    @Override
    @Transactional
    public Book update(Book book) {
        return bookDao.save(book);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        bookDao.deleteById(id);
    }
}
