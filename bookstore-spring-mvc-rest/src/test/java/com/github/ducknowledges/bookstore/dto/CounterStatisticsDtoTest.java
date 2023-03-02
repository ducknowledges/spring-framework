package com.github.ducknowledges.bookstore.dto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Class CounterStatisticsDto")
class CounterStatisticsDtoTest {

    @Test
    @DisplayName("correctly created by the constructor without id arg")
    void shouldHasCorrectConstructor() {
        CounterStatisticsDto dto = new CounterStatisticsDto(1L, 1L, 1L);
        assertAll(
            () -> assertThat(dto.getBookCounter()).isEqualTo(1L),
            () -> assertThat(dto.getAuthorCounter()).isEqualTo(1L),
            () -> assertThat(dto.getGenreCounter()).isEqualTo(1L)
        );
    }

}