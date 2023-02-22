package com.github.ducknowledges.bookstore.dto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

@DisplayName("Class PageResponseDto")
class PageResponseDtoTest {

    @Test
    @DisplayName("correctly created by the constructor without id arg")
    void shouldHasCorrectConstructor() {
        List<String> strings = List.of("first", "second");
        Page<String> stringPage = new PageImpl<>(strings);
        PageResponseDto<String> dto = new PageResponseDto<>(stringPage);
        assertAll(
            () -> assertThat(dto.getContent()).isEqualTo(strings),
            () -> assertThat(dto.getCurrentPage()).isZero(),
            () -> assertThat(dto.getTotalPages()).isEqualTo(1),
            () -> assertThat(dto.getTotalElements()).isEqualTo(2)
        );
    }
}