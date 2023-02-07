package com.github.ducknowledges.bookstore.dao;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.github.ducknowledges.bookstore.dao.mapper.BookCommentRowMapper;
import com.github.ducknowledges.bookstore.dao.mapper.BookRowMapper;
import com.github.ducknowledges.bookstore.domain.Author;
import com.github.ducknowledges.bookstore.domain.Book;
import com.github.ducknowledges.bookstore.domain.BookComment;
import com.github.ducknowledges.bookstore.domain.Genre;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@JdbcTest
@Import({BookCommentDaoJdbc.class, BookCommentRowMapper.class, BookRowMapper.class})
@DisplayName("Class BookCommentDaoJdbc")
class BookCommentDaoJdbcTest {

    private static final long FIRST_BOOK_COMMENT_ID = 1L;
    private static final long BOOK_COMMENT_ENTRIES_SIZE = 4;
    private static final long FIRST_BOOK_ID = 1L;

    @Autowired
    private BookCommentDaoJdbc bookCommentDao;

    @Autowired
    private BookCommentRowMapper rowMapper;

    @Autowired
    private BookRowMapper bookMapper;

    @Autowired
    private NamedParameterJdbcTemplate jdbc;

    @Test
    @DisplayName("should return expected book comment by id")
    void shouldReadExpectedBookById() {
        BookComment bookComment = jdbc.queryForObject(
            "select c.id,\n"
                + "       c.content,\n"
                + "       c.book_id,\n"
                + "       b.name as book_name,\n"
                + "       a.name as author_name,\n"
                + "       g.name as genre_name\n"
                + "from book_comment as c\n"
                + "         join book b on b.id = c.book_id\n"
                + "         join author a on a.id = b.author_id\n"
                + "         join genre g on g.id = b.genre_id\n"
                + "where c.id = :id",
            Map.of("id", FIRST_BOOK_COMMENT_ID),
            rowMapper);
        Optional<BookComment> expectedComment = Optional.ofNullable(bookComment);
        Optional<BookComment> actualComment = bookCommentDao.findById(FIRST_BOOK_COMMENT_ID);
        assertThat(actualComment).isNotEmpty()
            .usingRecursiveComparison().isEqualTo(expectedComment);
    }

    @Test
    @DisplayName("should return empty book comment by id if book comment does not exist")
    void shouldReturnEmptyBook() {
        Optional<BookComment> actualComment = bookCommentDao.findById(
            BOOK_COMMENT_ENTRIES_SIZE + 1);
        assertThat(actualComment).isEmpty();
    }

    @Test
    @DisplayName("should create book comment")
    void shouldCreateBookComment() {
        Book book = jdbc.queryForObject(
            "select\n"
                + "    book.id as id,\n"
                + "    book.name as name,\n"
                + "    book.author_id as author_id,\n"
                + "    a.name as author_name,\n"
                + "    book.genre_id as genre_id,\n"
                + "    g.name as genre_name\n"
                + "from book\n"
                + "         join author a on a.id = book.author_id\n"
                + "         join genre g on g.id = book.genre_id\n"
                + "where book.id = :id",
            Map.of("id", 1L),
            bookMapper);
        BookComment bookComment = new BookComment("content", book);

        bookCommentDao.create(bookComment);

        Optional<BookComment> expectedComment = Optional.of(bookComment);
        Optional<BookComment> actualComment = bookCommentDao.findById(
            BOOK_COMMENT_ENTRIES_SIZE + 1);
        assertThat(actualComment).isNotEmpty()
            .matches(c -> c.get().getContent().equals(expectedComment.get().getContent()))
            .matches(c -> c.get().getBook().equals(expectedComment.get().getBook()));
    }

    @Test
    @DisplayName("should return all book comments")
    void shouldFindAllBookComments() {
        List<BookComment> expectComments = jdbc.query(
            "select c.id,\n"
                + "       c.content,\n"
                + "       c.book_id,\n"
                + "       b.name as book_name,\n"
                + "       a.name as author_name,\n"
                + "       g.name as genre_name\n"
                + "from book_comment as c\n"
                + "         join book b on b.id = c.book_id\n"
                + "         join author a on a.id = b.author_id\n"
                + "         join genre g on g.id = b.genre_id\n"
                + "where c.id >= :fromId and c.id <= :toId",
            Map.of("fromId", FIRST_BOOK_COMMENT_ID, "toId", BOOK_COMMENT_ENTRIES_SIZE),
            rowMapper);

        List<BookComment> actualComments = bookCommentDao.findAll(
            FIRST_BOOK_COMMENT_ID,
            BOOK_COMMENT_ENTRIES_SIZE);
        assertThat(actualComments).hasSize(4).isEqualTo(expectComments);
    }

    @Test
    @DisplayName("should return all book comments bi book id")
    void shouldFindAllBookCommentsByBookId() {
        List<BookComment> expectComments = jdbc.query(
            "select c.id,\n"
                + "       c.content,\n"
                + "       c.book_id,\n"
                + "       b.name as book_name,\n"
                + "       a.name as author_name,\n"
                + "       g.name as genre_name\n"
                + "from book_comment as c\n"
                + "         join book b on b.id = c.book_id\n"
                + "         join author a on a.id = b.author_id\n"
                + "         join genre g on g.id = b.genre_id\n"
                + "where c.book_id = :bookId",
            Map.of("bookId", FIRST_BOOK_ID),
            rowMapper);

        List<BookComment> actualComments = bookCommentDao.findAllByBookId(FIRST_BOOK_ID);
        assertThat(actualComments).hasSize(2).isEqualTo(expectComments);
    }

    @Test
    @DisplayName("should update book comment")
    void shouldUpdateBook() {
        BookComment commentBefore = jdbc.queryForObject(
            "select c.id,\n"
                + "       c.content,\n"
                + "       c.book_id,\n"
                + "       b.name as book_name,\n"
                + "       a.name as author_name,\n"
                + "       g.name as genre_name\n"
                + "from book_comment as c\n"
                + "         join book b on b.id = c.book_id\n"
                + "         join author a on a.id = b.author_id\n"
                + "         join genre g on g.id = b.genre_id\n"
                + "where c.id = :id",
            Map.of("id", FIRST_BOOK_COMMENT_ID),
            rowMapper);
        String newContent = "new content";
        bookCommentDao.updateContent(commentBefore.getId(), newContent);
        Optional<BookComment> commentAfter = bookCommentDao.findById(FIRST_BOOK_COMMENT_ID);
        assertThat(commentAfter).isNotEmpty()
            .matches(c -> c.get().getId().equals(commentBefore.getId()))
            .matches(c -> c.get().getContent().equals(newContent))
            .matches(c -> c.get().getBook().equals(commentBefore.getBook()));
    }

    @Test
    @DisplayName("should delete book comment by id")
    void shouldDeleteBookById() {
        bookCommentDao.deleteById(FIRST_BOOK_COMMENT_ID);
        Optional<BookComment> actualComment = bookCommentDao.findById(FIRST_BOOK_COMMENT_ID);
        assertThat(actualComment).isEmpty();
    }

    @Test
    @DisplayName("should delete all book comments by book id")
    void deleteAllByBookId() {
        List<BookComment> commentsBefore = bookCommentDao.findAllByBookId(FIRST_BOOK_ID);
        assertThat(commentsBefore).hasSize(2);
        bookCommentDao.deleteAllByBookId(FIRST_BOOK_ID);
        List<BookComment> actualComments = bookCommentDao.findAllByBookId(FIRST_BOOK_ID);
        assertThat(actualComments).isEmpty();
    }
}