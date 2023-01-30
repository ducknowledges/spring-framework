package com.github.ducknowledges.bookstore.dao;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.github.ducknowledges.bookstore.dao.mapper.BookRowMapper;
import com.github.ducknowledges.bookstore.domain.Author;
import com.github.ducknowledges.bookstore.domain.Book;
import com.github.ducknowledges.bookstore.domain.Genre;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestPropertySource;

@JdbcTest
@Import({BookDaoJdbc.class, BookRowMapper.class})
@DisplayName("Class BookDaoJdbc")
class BookDaoJdbcTest {

    private static int existingBookCount;
    private static Book existingBook;

    @BeforeAll
    static void setUpAll() {
        existingBookCount = 1;
        Author author = new Author(1L, "author1");
        Genre genre = new Genre(1L, "genre1");
        existingBook = new Book(1L, "book1", author, genre);
    }

    @Autowired
    private BookDaoJdbc bookDao;

    @Test
    @DisplayName("should create book")
    void shouldCreateBook() {
        Author author = new Author(2L, "author2");
        Genre genre = new Genre(2L, "genre2");
        Book book = new Book(2L, "book", author, genre);
        bookDao.create(book);
        Optional<Book> expectedBook = Optional.of(book);
        Optional<Book> actualBook = bookDao.readById(existingBookCount + 1);
        assertThat(actualBook).isEqualTo(expectedBook);
    }

    @Test
    @DisplayName("should check existing book by id")
    void shouldCheckExistingBookById() {
        long existedId = 1;
        long notExistedId = 3;
        boolean actual = bookDao.existsById(existedId);
        assertThat(actual).isTrue();
        actual = bookDao.existsById(notExistedId);
        assertThat(actual).isFalse();
    }

    @Test
    @DisplayName("should return expected book by id")
    void shouldReadExpectedBookById() {
        Optional<Book> expectedBook = Optional.of(existingBook);
        Optional<Book> actualBook = bookDao.readById(existingBook.getId());
        assertThat(actualBook).isEqualTo(expectedBook);
    }

    @Test
    @DisplayName("should return empty book by id if book does not exist")
    void shouldReturnEmptyBook() {
        int notExistedId = existingBookCount + 1;
        Optional<Book> expectedBook = Optional.empty();
        Optional<Book> actualBook = bookDao.readById(notExistedId);
        assertThat(actualBook).isEqualTo(expectedBook);
    }

    @Test
    @DisplayName("should return all books")
    void readAll() {
        List<Book> expectedBooks = List.of(existingBook);
        List<Book> actualBooks = bookDao.readAll();
        assertThat(actualBooks).isEqualTo(expectedBooks);
    }

    @Test
    void update() {
        Author newAuthor = new Author(2L, "author2");
        Genre newGenre = new Genre(2L, "genre2");
        Book newBook = new Book(existingBook.getId(), "newBook", newAuthor, newGenre);
        bookDao.update(newBook);
        Optional<Book> expectedBook = Optional.of(newBook);
        Optional<Book> actualBook = bookDao.readById(existingBook.getId());
        assertThat(actualBook).isEqualTo(expectedBook);
    }

    @Test
    void delete() {
        bookDao.delete(existingBook.getId());
        Optional<Book> expectedBook = Optional.empty();
        Optional<Book> actualBook = bookDao.readById(existingBook.getId());
        assertThat(actualBook).isEqualTo(expectedBook);
    }
}