package com.github.ducknowledges.bookstore.dto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.github.ducknowledges.bookstore.domain.Author;
import com.github.ducknowledges.bookstore.domain.Book;
import com.github.ducknowledges.bookstore.domain.Genre;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Class BookDto")
class BookDtoTest {

    private static Book book;

    @BeforeAll
    static void setUpAll() {
        Author author = new Author(1L, "author");
        Genre genre = new Genre(1L, "genre");
        book = new Book(1L, "book1", author, genre);
    }

    @Test
    @DisplayName("correctly created by the constructor")
    void shouldHasCorrectConstructor() {
        BookDto bookDto = new BookDto(book);
        assertAll(
            () -> assertThat(bookDto.getId()).isEqualTo(1),
            () -> assertThat(bookDto.getName()).isEqualTo("book1"),
            () -> assertThat(bookDto.getAuthorId()).isEqualTo(1),
            () -> assertThat(bookDto.getGenreId()).isEqualTo(1)
        );
    }

    @Test
    @DisplayName("should has correct setters")
    void shouldHasCorrectSetters() {
        BookDto bookDto = new BookDto();
        bookDto.setId(1L);
        bookDto.setName("name");
        bookDto.setAuthorId(1L);
        bookDto.setGenreId(1L);
        assertAll(
            () -> assertThat(bookDto.getId()).isEqualTo(1L),
            () -> assertThat(bookDto.getName()).isEqualTo("name"),
            () -> assertThat(bookDto.getAuthorId()).isEqualTo(1L),
            () -> assertThat(bookDto.getGenreId()).isEqualTo(1L)
        );
    }
}