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
        return bookCommentDao.save(comment);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BookComment> getComment(long id) {
        return bookCommentDao.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookComment> getComments(long from, long to) {
        return bookCommentDao.findAllByIdGreaterThanEqualAndIdLessThanEqual(from, to);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookComment> getCommentsByBookId(long bookId) {
        Optional<Book> book = bookDao.findById(bookId);
        return book.map(b -> new ArrayList<>(b.getComments()))
            .orElse(new ArrayList<>());
    }

    @Override
    @Transactional
    public BookComment update(BookComment comment) {
        return bookCommentDao.save(comment);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        bookCommentDao.deleteById(id);
    }
}
