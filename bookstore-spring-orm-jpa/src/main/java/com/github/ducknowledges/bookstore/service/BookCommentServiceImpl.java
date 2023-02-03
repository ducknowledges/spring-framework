package com.github.ducknowledges.bookstore.service;

import com.github.ducknowledges.bookstore.dao.BookCommentDao;
import com.github.ducknowledges.bookstore.dao.BookDao;
import com.github.ducknowledges.bookstore.domain.Book;
import com.github.ducknowledges.bookstore.domain.BookComment;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookCommentServiceImpl implements BookCommentService {

    private final BookCommentDao bookCommentDao;
    private final BookDao bookDao;

    public BookCommentServiceImpl(BookCommentDao bookCommentDao, BookDao bookDao) {
        this.bookCommentDao = bookCommentDao;
        this.bookDao = bookDao;
    }

    @Override
    @Transactional
    public BookComment createComment(BookComment comment) {
        return bookCommentDao.create(comment);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BookComment> getComment(long id) {
        return bookCommentDao.readById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookComment> getComments(int from, int size) {
        return bookCommentDao.readAll(from, size);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookComment> getCommentsByBookId(long bookId) {
        Optional<Book> book = bookDao.readById(bookId);
        return book.map(b -> new ArrayList<>(b.getComments()))
            .orElse(new ArrayList<>());
    }

    @Override
    @Transactional
    public BookComment update(BookComment comment) {
        return bookCommentDao.update(comment);
    }

    @Override
    @Transactional
    public void delete(long id) {
        bookCommentDao.delete(id);
    }
}
