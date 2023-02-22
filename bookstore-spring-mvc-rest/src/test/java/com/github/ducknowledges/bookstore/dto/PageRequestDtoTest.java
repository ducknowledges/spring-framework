package com.github.ducknowledges.bookstore.dto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@DisplayName("Class PageRequestDto")
class PageRequestDtoTest {

    @Test
    @DisplayName("correctly created by the constructor")
    void shouldHasCorrectConstructor() {
        PageRequestDto dto = new PageRequestDto();
        Pageable expected = PageRequest.of(0, 5);
        Pageable actual = dto.toPageRequest();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("correctly created by the constructor without id arg")
    void shouldHasCorrectSetters() {
        PageRequestDto dto = new PageRequestDto();
        dto.setPage(1);
        dto.setSize(2);
        Pageable expected = PageRequest.of(1, 2);
        Pageable actual = dto.toPageRequest();
        assertThat(actual).isEqualTo(expected);
    }

}