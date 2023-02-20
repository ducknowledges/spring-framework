package com.github.ducknowledges.bookstore.dto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.github.ducknowledges.bookstore.domain.Book;
import com.github.ducknowledges.bookstore.domain.BookComment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("BookCommentDto")
class BookCommentDtoTest {

    @Test
    @DisplayName("correctly created by the constructor")
    void shouldHasCorrectConstructorWithoutId() {
        Book book = mock(Book.class);
        when(book.getName()).thenReturn("book");
        BookComment bookComment = new BookComment(1L, "message", book);
        BookCommentDto bookCommentDto = new BookCommentDto(bookComment);
        assertAll(
            () -> assertThat(bookCommentDto.getId()).isEqualTo(1L),
            () -> assertThat(bookCommentDto.getMessage()).isEqualTo("message"),
            () -> assertThat(bookCommentDto.getBookName()).isEqualTo("book")
        );
    }

}