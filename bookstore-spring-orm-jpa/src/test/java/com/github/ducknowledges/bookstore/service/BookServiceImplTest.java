package com.github.ducknowledges.bookstore.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.github.ducknowledges.bookstore.dao.BookDao;
import com.github.ducknowledges.bookstore.domain.Author;
import com.github.ducknowledges.bookstore.domain.Book;
import com.github.ducknowledges.bookstore.domain.Genre;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
@DisplayName("Class BookServiceImpl")
class BookServiceImplTest {

    @MockBean
    private BookDao bookDao;

    @Autowired
    private BookService bookService;

    private static Book book;

    @BeforeAll
    static void setUpAll() {
        Author author = new Author(1L, "author");
        Genre genre = new Genre(1L, "genre");
        book = new Book(1L, "book", author, genre);
    }

    @Test
    @DisplayName("should create book")
    void shouldCreateBook() {
        bookService.createBook(book);
        verify(bookDao, times(1)).create(book);
    }

    @Test
    @DisplayName("should return book by id")
    void shouldReturnBookById() {
        int id = 1;
        Optional<Book> expected = Optional.of(book);
        when(bookDao.readById(id)).thenReturn(expected);
        Optional<Book> actual = bookService.getBook(id);
        assertThat(actual).isEqualTo(expected);
        verify(bookDao, times(1)).readById(id);
    }

    @Test
    @DisplayName("should return empty book by id")
    void shouldReturnEmptyBookById() {
        long id = 1;
        Optional<Book> expected = Optional.empty();
        when(bookDao.readById(id)).thenReturn(expected);
        Optional<Book> actual = bookService.getBook(id);
        assertThat(actual).isEqualTo(expected);
        verify(bookDao, times(1)).readById(id);
    }

    @Test
    @DisplayName("should return all books")
    void shouldReturnAllBooks() {
        long fromId = 1;
        long toId = 1;
        when(bookDao.readAll(fromId, toId)).thenReturn(List.of(book));
        List<Book> expectedBooks = List.of(book);
        List<Book> actualBooks = bookService.getBooks(fromId, toId);
        assertThat(actualBooks).isEqualTo(expectedBooks);
    }

    @Test
    @DisplayName("should update book")
    void shouldUpdateBook() {
        long id = 1;
        when(bookDao.readById(id)).thenReturn(Optional.of(book));
        bookService.update(book);
        verify(bookDao, times(1)).update(book);
    }

    @Test
    @DisplayName("should delete book with all comments")
    void shouldDeleteBookWithAllComments() {
        long id = 1;
        bookService.deleteWithChildComments(id);
        verify(bookDao, times(1)).delete(id);
    }
}