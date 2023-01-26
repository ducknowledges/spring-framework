package com.github.ducknowledges.bookstore.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Class Author")
class AuthorTest {
    @Test
    @DisplayName("correctly created by the constructor")
    void shouldHaveCorrectConstructor() {
        Author author = new Author(1, "name");
        assertAll(
            () -> assertThat(author.getId()).isEqualTo(1),
            () -> assertThat(author.getName()).isEqualTo("name")
        );
    }

    @Test
    @DisplayName("correctly created by the constructor without id")
    void shouldHaveCorrectConstructorWithoutId() {
        Author author = new Author("name");
        assertAll(
            () -> assertThat(author.getName()).isEqualTo("name")
        );
    }

    @Test
    @DisplayName("should has correct equality")
    void shouldHasCorrectEquality() {
        assertThat(new Author(1, "name"))
            .isEqualTo(new Author(1, "name"));
    }
}