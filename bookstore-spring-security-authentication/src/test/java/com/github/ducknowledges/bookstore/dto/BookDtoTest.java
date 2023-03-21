package com.github.ducknowledges.bookstore.dto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.github.ducknowledges.bookstore.domain.Author;
import com.github.ducknowledges.bookstore.domain.Book;
import com.github.ducknowledges.bookstore.domain.Genre;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Class BookDto")
class BookDtoTest {

    @Test
    @DisplayName("correctly created by the constructor without id arg")
    void shouldHasCorrectConstructorWithoutId() {
        BookDto bookDto = new BookDto(1L, "name", 1L, 1L);
        assertAll(
            () -> assertThat(bookDto.getId()).isEqualTo(1),
            () -> assertThat(bookDto.getName()).isEqualTo("name"),
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

    @Test
    @DisplayName("should get book with id")
    void toDomainObjectWithId() {
        Author author = new Author();
        author.setId(1L);
        Genre genre = new Genre();
        genre.setId(1L);
        Book expectedBook = new Book(1L, "name", author, genre);
        Book actualBook = new BookDto(1L, "name", 1L, 1L).toDomainObject();
        assertThat(actualBook).isEqualTo(expectedBook);
    }

    @Test
    @DisplayName("should get book without id")
    void toDomainObjectWhithoutId() {
        Author author = new Author();
        author.setId(1L);
        Genre genre = new Genre();
        genre.setId(1L);
        Book actualBook = new Book("name", author, genre);
        Book expectedBook = new BookDto("name", 1L, 1L).toDomainObject();
        assertThat(actualBook).isEqualTo(expectedBook);
    }

    @Test
    @DisplayName("should get book dto")
    void toDto() {
        Author author = new Author(1L, "name");
        Genre genre = new Genre(1L, "name");
        Book book = new Book(1L, "name", author, genre);
        BookDto actualBookDto = BookDto.toDto(book);
        assertAll(
            () -> assertThat(actualBookDto.getId()).isEqualTo(1L),
            () -> assertThat(actualBookDto.getName()).isEqualTo("name"),
            () -> assertThat(actualBookDto.getAuthorId()).isEqualTo(1L),
            () -> assertThat(actualBookDto.getGenreId()).isEqualTo(1L)
        );
    }

    @Test
    @DisplayName("should has correct equality")
    void shouldHasCorrectEquality() {
        assertThat(new BookDto(1L, "name", 1L, 1L))
            .isEqualTo(new BookDto(1L, "name", 1L, 1L));
    }
}