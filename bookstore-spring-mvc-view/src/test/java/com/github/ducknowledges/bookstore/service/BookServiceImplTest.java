package com.github.ducknowledges.bookstore.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.github.ducknowledges.bookstore.dao.BookCommentDao;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@SpringBootTest
@DisplayName("Class BookServiceImpl")
class BookServiceImplTest {

    @MockBean
    private BookDao bookDao;

    @MockBean
    private BookCommentDao commentDao;

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
        bookService.create(book);
        verify(bookDao, times(1)).save(book);
    }

    @Test
    @DisplayName("should return book by id")
    void shouldReturnBookById() {
        long id = 1;
        Optional<Book> expected = Optional.of(book);
        when(bookDao.findById(id)).thenReturn(expected);
        Optional<Book> actual = bookService.getBook(id);
        assertThat(actual).isEqualTo(expected);
        verify(bookDao, times(1)).findById(id);
    }

    @Test
    @DisplayName("should return empty book by id")
    void shouldReturnEmptyBookById() {
        long id = 1;
        Optional<Book> expected = Optional.empty();
        when(bookDao.findById(id)).thenReturn(expected);
        Optional<Book> actual = bookService.getBook(id);
        assertThat(actual).isEqualTo(expected);
        verify(bookDao, times(1)).findById(id);
    }

    @Test
    @DisplayName("should return all books")
    void shouldReturnAllBooks() {
        int page = 0;
        int size = 1;
        when(bookDao.findAll(PageRequest.of(page, size)))
            .thenReturn(new PageImpl<>(List.of(book)));
        Page<Book> expectedBooks = new PageImpl<>(List.of(book));
        Page<Book> actualBooks = bookService.getBooks(page, size);
        assertThat(actualBooks).isEqualTo(expectedBooks);
    }

    @Test
    @DisplayName("should update book")
    void shouldUpdateBook() {
        long id = 1;
        when(bookDao.findById(id)).thenReturn(Optional.of(book));
        bookService.save(book);
        verify(bookDao, times(1)).save(book);
    }

    @Test
    @DisplayName("should delete book with all comments")
    void shouldDeleteBookWithAllComments() {
        long id = 1;
        bookService.deleteById(id);
        verify(bookDao, times(1)).deleteById(id);
    }
}