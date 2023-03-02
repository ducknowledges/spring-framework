package com.github.ducknowledges.bookstore.mapper;

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

@DisplayName("Class GenreMapper")
class GenreMapperTest {

    @Test
    @DisplayName("should convert genre page to genre dto request page")
    void shouldConvertGenrePageToGenrePageDto() {
        List<Genre> content = List.of(new Genre(1L, "name"), new Genre(2L, "name"));
        Page<Genre> pageGenre = new PageImpl<>(content);
        PageResponseDto<Genre> expected = new PageResponseDto<>(pageGenre);
        PageResponseDto<Genre> actual = new GenreMapper().getPageResponseDto(pageGenre);
        assertAll(
            () -> assertThat(actual.getContent()).isEqualTo(expected.getContent()),
            () -> assertThat(actual.getTotalPages()).isEqualTo(expected.getTotalPages()),
            () -> assertThat(actual.getTotalElements()).isEqualTo(expected.getTotalElements()),
            () -> assertThat(actual.getCurrentPage()).isEqualTo(expected.getCurrentPage())
        );
    }

}