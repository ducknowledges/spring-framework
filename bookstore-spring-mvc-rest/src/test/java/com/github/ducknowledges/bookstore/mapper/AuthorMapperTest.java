package com.github.ducknowledges.bookstore.mapper;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.github.ducknowledges.bookstore.domain.Author;
import com.github.ducknowledges.bookstore.domain.Genre;
import com.github.ducknowledges.bookstore.dto.PageResponseDto;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

@DisplayName("Class AuthorMapper")
class AuthorMapperTest {

    @Test
    @DisplayName("should convert author page to author dto request page")
    void shouldConvertAuthorPageToAuthorPageDto() {
        List<Author> content = List.of(new Author(1L, "name"), new Author(2L, "name"));
        Page<Author> pageAuthor = new PageImpl<>(content);
        PageResponseDto<Author> expected = new PageResponseDto<>(pageAuthor);
        PageResponseDto<Author> actual = new AuthorMapper().getPageResponseDto(pageAuthor);
        assertAll(
            () -> assertThat(actual.getContent()).isEqualTo(expected.getContent()),
            () -> assertThat(actual.getTotalPages()).isEqualTo(expected.getTotalPages()),
            () -> assertThat(actual.getTotalElements()).isEqualTo(expected.getTotalElements()),
            () -> assertThat(actual.getCurrentPage()).isEqualTo(expected.getCurrentPage())
        );
    }

}