package com.github.ducknowledges.bookstore.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.github.ducknowledges.bookstore.dao.BookDao;
import com.github.ducknowledges.bookstore.dao.GenreDao;
import com.github.ducknowledges.bookstore.domain.Author;
import com.github.ducknowledges.bookstore.domain.Book;
import com.github.ducknowledges.bookstore.domain.Genre;
import com.github.ducknowledges.bookstore.service.exception.BookServiceException;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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

    @MockBean
    private AuthorService authorService;

    @MockBean
    private GenreService genreService;

    @Autowired
    private BookServiceImpl bookService;

    private static Author author;
    private static Genre genre;
    private static Book book;

    @BeforeAll
    static void setUpAll() {
        author = new Author(1, "author");
        genre = new Genre(1, "genre");
        book = new Book(1, "book", author, genre);
    }

    @Test
    @DisplayName("should create book")
    void shouldCreateBook() {
        when(authorService.getAuthorById(anyInt())).thenReturn(Optional.of(author));
        when(genreService.getGenreById(anyInt())).thenReturn(Optional.of(genre));

        bookService.createBook(book);
        verify(bookDao, times(1)).create(book);
    }

    @Test
    @DisplayName("should return book by id")
    void shouldReturnBookById() {
        int id = 1;
        when(bookDao.readById(id)).thenReturn(Optional.of(book));
        bookService.getBook(id);
        verify(bookDao, times(1)).readById(id);
    }

    @Test
    @DisplayName("should throw exception when book not exist")
    void shouldThrowExceptionWhenBookNotExist() {
        int id = 1;
        when(bookDao.readById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> bookService.getBook(id))
            .isInstanceOf(BookServiceException.class)
            .hasMessage("Book does not exist");
        verify(bookDao, times(1)).readById(id);
    }

    @Test
    @DisplayName("should return all books")
    void shouldReturnAllBooks() {
        when(bookDao.readAll()).thenReturn(List.of(book));
        List<Book> expectedBooks = List.of(book);
        List<Book> actualBooks = bookService.getBooks();
        assertThat(actualBooks).isEqualTo(expectedBooks);
    }

    @Test
    @DisplayName("should throw exception when book does not exist")
    void shouldUpdateBook() {
        int id = 1;
        when(bookDao.readById(id)).thenReturn(Optional.of(book));
        bookService.update(book);
        verify(bookDao, times(1)).update(book);
    }

    @Test
    @DisplayName("should delete book")
    void delete() {
        int id = 1;
        bookService.delete(id);
        verify(bookDao, times(1)).delete(id);
    }
}