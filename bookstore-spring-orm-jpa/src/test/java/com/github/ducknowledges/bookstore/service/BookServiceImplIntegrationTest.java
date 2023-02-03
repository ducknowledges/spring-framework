package com.github.ducknowledges.bookstore.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.github.ducknowledges.bookstore.dao.AuthorDao;
import com.github.ducknowledges.bookstore.dao.BookDao;
import com.github.ducknowledges.bookstore.dao.BookDaoJpa;
import com.github.ducknowledges.bookstore.dao.GenreDao;
import com.github.ducknowledges.bookstore.dao.GenreDaoJpa;
import com.github.ducknowledges.bookstore.domain.Author;
import com.github.ducknowledges.bookstore.domain.Book;
import com.github.ducknowledges.bookstore.domain.Genre;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import({BookServiceImpl.class, BookDaoJpa.class})
@DisplayName("Class BookServiceImpl")
class BookServiceImplIntegrationTest {

    private static final long FIRST_BOOK_ID = 1L;
    private static final long BOOK_ENTRIES_SIZE = 3;

    @Autowired
    private BookServiceImpl bookService;

    private Book book;
    private Author author;
    private Genre genre;

    @BeforeEach
    void setUp() {
        author = new Author(1L, "author1");
        genre = new Genre(1L, "genre1");
        book = new Book(FIRST_BOOK_ID, "book1", author, genre);
    }

    @Test
    @DisplayName("should create book")
    void shouldCreateBook() {
        Book newBook = new Book("book4", author, genre);
        Book actualBook = bookService.createBook(newBook);
        book.setId(BOOK_ENTRIES_SIZE + 1);
        book.setName("book4");
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(book);
    }

    @Test
    @DisplayName("should return book by id")
    void shouldReturnBookById() {
        Optional<Book> expectedBook = Optional.of(book);
        Optional<Book> actualBook = bookService.getBook(FIRST_BOOK_ID);
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @Test
    @DisplayName("should return empty book by id")
    void shouldReturnEmptyBookById() {
        Optional<Book> expected = Optional.empty();
        Optional<Book> actual = bookService.getBook(BOOK_ENTRIES_SIZE + 1);
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    @DisplayName("should return all books")
    void shouldReturnAllBooks() {
        int from = 1;
        int size = 1;
        List<Book> expectedBooks = List.of(book);
        List<Book> actualBooks = bookService.getBooks(from, size);
        assertThat(actualBooks)
            .usingRecursiveComparison().isEqualTo(expectedBooks);
    }

    @Test
    @DisplayName("should throw exception when book does not exist")
    void shouldUpdateBook() {
        book.setName("new book");
        book.setAuthor(author);
        book.setGenre(genre);
        bookService.update(book);
        Optional<Book> expectedBook = Optional.of(book);
        Optional<Book> actualBook = bookService.getBook(book.getId());
        assertThat(actualBook)
            .usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @Test
    @DisplayName("should delete book")
    void shouldDeleteBook() {
        Optional<Book> bookBefore = bookService.getBook(FIRST_BOOK_ID);
        assertThat(bookBefore).isPresent();
        bookService.delete(FIRST_BOOK_ID);
        Optional<Book> bookAfter = bookService.getBook(FIRST_BOOK_ID);
        assertThat(bookAfter).isEmpty();
    }
}