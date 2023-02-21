package com.github.ducknowledges.bookstore.service;

import com.github.ducknowledges.bookstore.dao.BookCommentDao;
import com.github.ducknowledges.bookstore.dao.BookDao;
import com.github.ducknowledges.bookstore.domain.Book;
import com.github.ducknowledges.bookstore.domain.BookComment;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public BookComment createComment(BookComment comment) {
        return bookCommentDao.save(comment);
    }

    @Override
    public Optional<BookComment> getComment(long id) {
        return bookCommentDao.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BookComment> getComments(Pageable pageable) {
        return bookCommentDao.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BookComment> getCommentsByBookId(long bookId, Pageable pageable) {
        return bookDao.findById(bookId).map(
            b -> bookCommentDao.findAllByBookId(b.getId(), pageable)
        ).orElse(Page.empty());
    }

    @Override
    public BookComment update(BookComment comment) {
        return bookCommentDao.save(comment);
    }

    @Override
    public void deleteById(long id) {
        bookCommentDao.deleteById(id);
    }
}
