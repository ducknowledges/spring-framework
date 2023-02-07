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

@SpringBootTest
@DisplayName("Class BookServiceImpl")
class BookServiceImplTest {

    @MockBean
    private BookDao bookDao;

    @MockBean
    private BookCommentDao commentDao;

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
        author = new Author(1L, "author");
        genre = new Genre(1L, "genre");
        book = new Book(1L, "book", author, genre);
    }

    @Test
    @DisplayName("should create book")
    void shouldCreateBook() {
        when(authorService.getAuthor(author.getId())).thenReturn(Optional.of(author));
        when(genreService.getGenre(genre.getId())).thenReturn(Optional.of(genre));

        bookService.createBook(book);
        verify(bookDao, times(1)).create(book);
    }

    @Test
    @DisplayName("should return book by id")
    void shouldReturnBookById() {
        Optional<Book> expected = Optional.of(book);
        when(bookDao.findById(book.getId())).thenReturn(expected);
        Optional<Book> actual = bookService.getBook(book.getId());
        assertThat(actual).isEqualTo(expected);
        verify(bookDao, times(1)).findById(book.getId());
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
    @DisplayName("should check book if exists by id")
    void shouldCheckBookIfExists() {
        when(bookDao.existsById(book.getId())).thenReturn(true);
        bookService.bookExists(book.getId());
        verify(bookDao, times(1)).existsById(book.getId());
    }

    @Test
    @DisplayName("should return all books")
    void shouldReturnAllBooks() {
        long fromId = 1;
        long toId = 2;
        when(bookDao.findAll(fromId, toId)).thenReturn(List.of(book));
        List<Book> expectedBooks = List.of(book);
        List<Book> actualBooks = bookService.getBooks(fromId, toId);
        assertThat(actualBooks).isEqualTo(expectedBooks);
    }

    @Test
    @DisplayName("should update book")
    void shouldUpdateBook() {
        when(bookDao.findById(1L)).thenReturn(Optional.of(book));
        bookService.update(book);
        verify(bookDao, times(1)).update(book);
    }

    @Test
    @DisplayName("should delete book with comments")
    void shouldDeleteBookWithComments() {
        bookService.deleteWithChildComments(book.getId());
        verify(bookDao, times(1)).deleteById(book.getId());
        verify(commentDao, times(1)).deleteAllByBookId(book.getId());
    }
}