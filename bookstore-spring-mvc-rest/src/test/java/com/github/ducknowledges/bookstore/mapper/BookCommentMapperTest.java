package com.github.ducknowledges.bookstore.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.github.ducknowledges.bookstore.domain.Book;
import com.github.ducknowledges.bookstore.domain.BookComment;
import com.github.ducknowledges.bookstore.domain.Genre;
import com.github.ducknowledges.bookstore.dto.BookCommentDto;
import com.github.ducknowledges.bookstore.dto.PageResponseDto;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

@DisplayName("Class BookCommentMapper")
class BookCommentMapperTest {

    @Test
    @DisplayName("should convert book comment page to book comment dto request page")
    void shouldConvertGenrePageToGenrePageDto() {
        Book book = mock(Book.class);
        when(book.getName()).thenReturn("bookName");
        List<BookComment> content = List.of(
            new BookComment(1L, "message", book),
            new BookComment(2L, "message", book));
        Page<BookComment> pageBookComment = new PageImpl<>(content);

        PageResponseDto<BookCommentDto> expected = new PageResponseDto<>(
            pageBookComment.map(BookCommentDto::new)
        );
        PageResponseDto<BookCommentDto> actual = new BookCommentMapper()
            .getPageResponseDto(pageBookComment);

        assertAll(
            () -> assertThat(actual.getContent()).isEqualTo(expected.getContent()),
            () -> assertThat(actual.getTotalPages()).isEqualTo(expected.getTotalPages()),
            () -> assertThat(actual.getTotalElements()).isEqualTo(expected.getTotalElements()),
            () -> assertThat(actual.getCurrentPage()).isEqualTo(expected.getCurrentPage())
        );
    }

}