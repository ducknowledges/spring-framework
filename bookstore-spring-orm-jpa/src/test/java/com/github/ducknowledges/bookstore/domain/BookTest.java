package com.github.ducknowledges.bookstore.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Class Book")
class BookTest {

    @Test
    @DisplayName("correctly created by the constructor")
    void shouldHasCorrectConstructor() {
        Author author = new Author(1L, "name");
        Genre genre = new Genre(1L, "name");
        Book book = new Book(1L, "name", author, genre);
        assertAll(
            () -> assertThat(book.getId()).isEqualTo(1),
            () -> assertThat(book.getName()).isEqualTo("name"),
            () -> assertThat(book.getAuthor()).isEqualTo(new Author(1L, "name")),
            () -> assertThat(book.getGenre()).isEqualTo(new Genre(1L, "name"))
        );
    }

    @Test
    @DisplayName("correctly created by the constructor without id arg")
    void shouldHasCorrectConstructorWithoutId() {
        Author author = new Author(1L, "name");
        Genre genre = new Genre(1L, "name");
        Book book = new Book("name", author, genre);
        assertAll(
            () -> assertThat(book.getName()).isEqualTo("name"),
            () -> assertThat(book.getAuthor()).isEqualTo(new Author(1L, "name")),
            () -> assertThat(book.getGenre()).isEqualTo(new Genre(1L, "name"))
        );
    }

    @Test
    @DisplayName("should has correct equality")
    void shouldHasCorrectEquality() {
        Author author = new Author(1L, "name");
        Genre genre = new Genre(1L, "name");
        assertThat(new Book(1L, "name", author, genre))
            .isEqualTo(new Book(1L, "name", author, genre));
    }

    @Test
    @DisplayName("should has correct setters")
    void shouldHasCorrectSetters() {
        Book book = new Book();
        book.setId(1L);
        book.setName("name");
        book.setAuthor(new Author("name"));
        book.setGenre(new Genre("name"));
        book.setComments(List.of(new BookComment("content", book)));
        assertAll(
            () -> assertThat(book.getId()).isEqualTo(1L),
            () -> assertThat(book.getName()).isEqualTo("name"),
            () -> assertThat(book.getAuthor()).isEqualTo(new Author("name")),
            () -> assertThat(book.getGenre()).isEqualTo(new Genre("name")),
            () -> assertThat(book.getComments()).isEqualTo(
                List.of(new BookComment("content", book)))
        );
    }
}