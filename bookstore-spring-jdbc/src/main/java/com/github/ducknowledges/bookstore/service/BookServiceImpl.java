package com.github.ducknowledges.bookstore.service;

import com.github.ducknowledges.bookstore.dao.BookDao;
import com.github.ducknowledges.bookstore.domain.Book;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;

    public BookServiceImpl(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Override
    public void createBook(Book book) {
        bookDao.create(book);
    }

    @Override
    public boolean bookExists(long id) {
        return bookDao.existsById(id);
    }

    @Override
    public Optional<Book> getBook(long id) {
        return bookDao.readById(id);
    }

    @Override
    public List<Book> getBooks() {
        return bookDao.readAll();
    }

    @Override
    public void update(Book newBook) {
        bookDao.update(newBook);
    }

    @Override
    public void delete(long id) {
        bookDao.delete(id);
    }
}
