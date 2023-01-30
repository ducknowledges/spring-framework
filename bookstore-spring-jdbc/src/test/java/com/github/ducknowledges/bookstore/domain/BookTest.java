package com.github.ducknowledges.bookstore.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

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
}