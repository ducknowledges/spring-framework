package com.github.ducknowledges.bookstore.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.github.ducknowledges.bookstore.dao.GenreDao;
import com.github.ducknowledges.bookstore.domain.Genre;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
@DisplayName("Class GenreServiceImplImpl")
class GenreServiceImplTest {

    @MockBean
    private GenreDao genreDao;

    @Autowired
    private GenreService genreService;

    @Test
    @DisplayName("should get genre by id")
    void shouldGetGenreById() {
        Genre genre = new Genre(1, "genre");
        when(genreDao.readById(genre.getId())).thenReturn(Optional.of(genre));

        Optional<Genre> expectedAuthor = Optional.of(genre);
        Optional<Genre> actualAuthor = genreService.getGenreById(genre.getId());
        assertThat(actualAuthor).isEqualTo(expectedAuthor);
    }

    @Test
    @DisplayName("should get all genres")
    void shouldGetGenres() {
        Genre genre = new Genre(1, "genre");
        when(genreDao.readAll()).thenReturn(List.of(genre));

        List<Genre> expectedGenres = List.of(genre);
        List<Genre> actualGenre = genreService.getGenres();
        assertThat(actualGenre).isEqualTo(expectedGenres);
    }
}