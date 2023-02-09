package com.github.ducknowledges.bookstore.dao;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.ducknowledges.bookstore.dao.mapper.BookRowMapper;
import com.github.ducknowledges.bookstore.domain.Author;
import com.github.ducknowledges.bookstore.domain.Book;
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
@Import({BookDaoJdbc.class, BookRowMapper.class})
@DisplayName("Class BookDaoJdbc")
class BookDaoJdbcTest {

    private static final long FIRST_BOOK_ID = 1L;
    private static final long BOOK_ENTRIES_SIZE = 4L;

    @Autowired
    private BookDaoJdbc bookDao;

    @Autowired
    private BookRowMapper rowMapper;

    @Autowired
    private NamedParameterJdbcTemplate jdbc;

    @Test
    @DisplayName("should check existing book by id")
    void shouldCheckExistingBookById() {
        boolean actual1 = bookDao.existsById(FIRST_BOOK_ID);
        assertThat(actual1).isTrue();

        boolean actual2 = bookDao.existsById(BOOK_ENTRIES_SIZE + 1);
        assertThat(actual2).isFalse();
    }

    @Test
    @DisplayName("should return expected book by id")
    void shouldReadExpectedBookById() {
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
            Map.of("id", FIRST_BOOK_ID),
            rowMapper);
        Optional<Book> expectedBook = Optional.ofNullable(book);
        Optional<Book> actualBook = bookDao.findById(FIRST_BOOK_ID);
        assertThat(actualBook).isNotEmpty()
            .usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @Test
    @DisplayName("should return empty book by id if book does not exist")
    void shouldReturnEmptyBook() {
        Optional<Book> actualBook = bookDao.findById(BOOK_ENTRIES_SIZE + 1);
        assertThat(actualBook).isEmpty();
    }

    @Test
    @DisplayName("should create book")
    void shouldCreateBook() {
        Author author = new Author(2L, "author2");
        Genre genre = new Genre(2L, "genre2");
        Book book = new Book("book", author, genre);
        bookDao.create(book);
        Optional<Book> expectedBook = Optional.of(book);
        Optional<Book> actualBook = bookDao.findById(BOOK_ENTRIES_SIZE + 1);
        assertThat(actualBook).isNotEmpty().isEqualTo(expectedBook);
    }

    @Test
    @DisplayName("should return all books")
    void shouldFindAllBooks() {
        List<Book> expectedBooks = jdbc.query(
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
                + "where book.id >= :fromId and book.id <= :toId",
            Map.of("fromId", FIRST_BOOK_ID, "toId", BOOK_ENTRIES_SIZE - 1),
            rowMapper);
        List<Book> actualBooks = bookDao.findAll(FIRST_BOOK_ID, BOOK_ENTRIES_SIZE - 1);
        assertThat(actualBooks).hasSize(3).isEqualTo(expectedBooks);
    }

    @Test
    @DisplayName("should update book")
    void shouldUpdateBook() {
        Author newAuthor = new Author(2L, "author2");
        Genre newGenre = new Genre(2L, "genre2");
        Book newBook = new Book(FIRST_BOOK_ID, "newBook", newAuthor, newGenre);
        bookDao.update(newBook);
        Optional<Book> expectedBook = Optional.of(newBook);
        Optional<Book> actualBook = bookDao.findById(FIRST_BOOK_ID);
        assertThat(actualBook).isNotEmpty().isEqualTo(expectedBook);
    }


    @Test
    @DisplayName("should delete book by id")
    void shouldDeleteBookById() {
        Optional<Book> bookBefore = bookDao.findById(BOOK_ENTRIES_SIZE);
        assertThat(bookBefore).isNotEmpty();
        bookDao.deleteById(BOOK_ENTRIES_SIZE);
        Optional<Book> actualBook = bookDao.findById(BOOK_ENTRIES_SIZE);
        assertThat(actualBook).isEmpty();
    }
}