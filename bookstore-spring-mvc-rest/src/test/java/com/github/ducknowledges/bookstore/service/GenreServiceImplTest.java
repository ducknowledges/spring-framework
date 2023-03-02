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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

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
        Genre genre = new Genre(1L, "genre");
        when(genreDao.findById(genre.getId())).thenReturn(Optional.of(genre));

        Optional<Genre> expectedAuthor = Optional.of(genre);
        Optional<Genre> actualAuthor = genreService.getGenre(genre.getId());
        assertThat(actualAuthor).isEqualTo(expectedAuthor);
    }

    @Test
    @DisplayName("should get genres")
    void shouldGetGenres() {
        int page = 0;
        int size = 1;
        Genre genre = new Genre(1L, "genre");
        when(genreDao.findAll(PageRequest.of(page, size)))
            .thenReturn(new PageImpl<>(List.of(genre)));

        Page<Genre> expectedGenres = new PageImpl<>(List.of(genre));
        Page<Genre> actualGenres = genreService.getGenres(PageRequest.of(page, size));
        assertThat(actualGenres).isEqualTo(expectedGenres);
    }

    @Test
    @DisplayName("should get all genres")
    void shouldGetAllGenres() {
        Genre genre = new Genre(1L, "genre");
        when(genreDao.findAll())
            .thenReturn(List.of(genre));

        List<Genre> expectedGenres = List.of(genre);
        List<Genre> actualGenres = genreService.getAllGenres();
        assertThat(actualGenres).isEqualTo(expectedGenres);
    }
}