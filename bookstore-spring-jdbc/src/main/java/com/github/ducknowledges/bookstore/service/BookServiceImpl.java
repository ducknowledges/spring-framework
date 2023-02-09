package com.github.ducknowledges.bookstore.service;

import com.github.ducknowledges.bookstore.dao.BookCommentDao;
import com.github.ducknowledges.bookstore.dao.BookDao;
import com.github.ducknowledges.bookstore.domain.Book;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;
    private final BookCommentDao commentDao;

    public BookServiceImpl(BookDao bookDao, BookCommentDao commentDao) {
        this.bookDao = bookDao;
        this.commentDao = commentDao;
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
        return bookDao.findById(id);
    }

    @Override
    public List<Book> getBooks(long fromId, long toId) {
        return bookDao.findAll(fromId, toId);
    }

    @Override
    public void update(Book book) {
        bookDao.update(book);
    }

    @Override
    @Transactional
    public void deleteWithChildComments(long id) {
        commentDao.deleteAllByBookId(id);
        bookDao.deleteById(id);
    }
}
