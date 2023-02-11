package com.github.ducknowledges.bookstore.command;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.github.ducknowledges.bookstore.domain.Genre;
import com.github.ducknowledges.bookstore.printformatter.PrintFormatter;
import com.github.ducknowledges.bookstore.service.GenreServiceImpl;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
@DisplayName("Class BookGenreCommandTest")
class BookGenreCommandTest {

    @MockBean
    private GenreServiceImpl genreService;

    @MockBean
    private PrintFormatter<Genre> printFormatter;

    @Autowired
    private BookGenreCommand bookGenreCommand;


    @Test
    @DisplayName("should return genres formatted to string")
    void shouldFormatAuthorsToString() {
        long fromId = 0;
        long toId = 1;
        List<Genre> genres = List.of();
        when(genreService.getGenres(fromId, toId)).thenReturn(genres);
        when(printFormatter.format(genres)).thenReturn("genre");
        String expected = "genre";
        String actual = bookGenreCommand.getGenres(fromId, toId);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("should return genres formatted to string")
    void shouldFormatAuthorToString() {
        long genreId = 1L;
        Genre genre = new Genre(1L, "name");
        when(genreService.getGenre(genreId)).thenReturn(Optional.of(genre));
        when(printFormatter.format(genre)).thenReturn("genre");
        String expected = "genre";
        String actual = bookGenreCommand.getAuthor(genreId);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("should return authors does not exist")
    void shouldReturnAuthorDoesNotExist() {
        long genreId = 1L;
        when(genreService.getGenre(genreId)).thenReturn(Optional.empty());
        String expected = "Genre doesn't exist";
        String actual = bookGenreCommand.getAuthor(genreId);
        assertThat(actual).isEqualTo(expected);
    }
}