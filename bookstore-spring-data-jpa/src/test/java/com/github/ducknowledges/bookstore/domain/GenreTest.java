package com.github.ducknowledges.bookstore.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Class Genre")
class GenreTest {
    @Test
    @DisplayName("correctly created by the constructor")
    void shouldHaveCorrectConstructor() {
        Genre genre = new Genre(1L, "name");
        assertAll(
            () -> assertThat(genre.getId()).isEqualTo(1),
            () -> assertThat(genre.getName()).isEqualTo("name")
        );
    }

    @Test
    @DisplayName("correctly created by the constructor without id")
    void shouldHaveCorrectConstructorWithoutId() {
        Genre genre = new Genre("name");
        assertAll(
            () -> assertThat(genre.getName()).isEqualTo("name")
        );
    }

    @Test
    @DisplayName("should has correct equality")
    void shouldHasCorrectEquality() {
        assertThat(new Genre(1L, "name"))
            .isEqualTo(new Genre(1L, "name"));
    }

    @Test
    @DisplayName("should has correct setters")
    void shouldHasCorrectSetters() {
        Genre genre = new Genre();
        genre.setId(1L);
        genre.setName("name");
        assertAll(
            () -> assertThat(genre.getId()).isEqualTo(1L),
            () -> assertThat(genre.getName()).isEqualTo("name")
        );
    }
}