package com.github.ducknowledges.bookstore.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.ducknowledges.bookstore.dao.BookCommentDaoJdbc;
import com.github.ducknowledges.bookstore.dao.BookDaoJdbc;
import com.github.ducknowledges.bookstore.dao.mapper.BookCommentRowMapper;
import com.github.ducknowledges.bookstore.dao.mapper.BookRowMapper;
import com.github.ducknowledges.bookstore.domain.Book;
import com.github.ducknowledges.bookstore.domain.BookComment;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

@JdbcTest
@Import({
    BookServiceImpl.class,
    BookDaoJdbc.class,
    BookCommentDaoJdbc.class,
    BookRowMapper.class,
    BookCommentRowMapper.class})
@DisplayName("Class BookServiceImpl")
class BookServiceIntegrationTest {

    @Autowired
    private BookCommentDaoJdbc commentDao;

    @Autowired
    private BookServiceImpl bookService;

    private static final long FIRST_BOOK_ID = 1L;

    @Test
    @DisplayName("should delete book with comments")
    void shouldDeleteBookWithComments() {
        bookService.deleteWithChildComments(FIRST_BOOK_ID);
        Optional<Book> book = bookService.getBook(FIRST_BOOK_ID);
        assertThat(book).isEmpty();
        List<BookComment> comments = commentDao.findAllByBookId(FIRST_BOOK_ID);
        assertThat(comments).isEmpty();
    }
}