package com.github.ducknowledges.bookstore.service;

import com.github.ducknowledges.bookstore.dao.BookCommentDao;
import com.github.ducknowledges.bookstore.dao.BookDao;
import com.github.ducknowledges.bookstore.domain.BookComment;
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
    public void createComment(BookComment comment) {
        bookCommentDao.create(comment);
    }

    @Override
    public Optional<BookComment> getComment(long id) {
        return bookCommentDao.findById(id);
    }

    @Override
    public List<BookComment> getComments(long fromId, long toId) {
        return bookCommentDao.findAll(fromId, toId);
    }

    @Override
    public List<BookComment> getCommentsByBookId(long bookId) {
        return bookDao.findById(bookId).map(
            b -> bookCommentDao.findAllByBookId(b.getId())
        ).orElse(List.of());
    }

    @Override
    public void updateContent(long id, String content) {
        bookCommentDao.updateContent(id, content);
    }

    @Override
    public void delete(long id) {
        bookCommentDao.deleteById(id);
    }
}
