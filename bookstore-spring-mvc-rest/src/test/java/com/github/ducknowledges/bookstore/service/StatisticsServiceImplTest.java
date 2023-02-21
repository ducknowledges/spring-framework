package com.github.ducknowledges.bookstore.service;

import static java.util.function.Predicate.isEqual;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

import com.github.ducknowledges.bookstore.dao.AuthorDao;
import com.github.ducknowledges.bookstore.dao.BookDao;
import com.github.ducknowledges.bookstore.dao.GenreDao;
import com.github.ducknowledges.bookstore.dto.CounterStatisticsDto;
import java.util.function.Predicate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
@DisplayName("Class StatisticsServiceImpl")
class StatisticsServiceImplTest {

    @MockBean
    private BookDao bookDao;

    @MockBean
    private AuthorDao authorDao;

    @MockBean
    private GenreDao genreDao;

    @Autowired
    private StatisticsService statisticsService;

    @Test
    @DisplayName("should get bookstore counter statistics")
    void getCounterStatistics() {
        when(bookDao.count()).thenReturn(1L);
        when(authorDao.count()).thenReturn(1L);
        when(genreDao.count()).thenReturn(1L);

        CounterStatisticsDto actualDto = statisticsService.getCounterStatistics();
        assertAll(
            () -> assertThat(actualDto.getBookCounter()).isEqualTo(1L),
            () -> assertThat(actualDto.getAuthorCounter()).isEqualTo(1L),
            () -> assertThat(actualDto.getGenreCounter()).isEqualTo(1L)
        );
    }
}