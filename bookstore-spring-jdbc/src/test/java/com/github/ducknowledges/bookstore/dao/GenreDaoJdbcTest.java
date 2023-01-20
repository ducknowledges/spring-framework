package com.github.ducknowledges.bookstore.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.github.ducknowledges.bookstore.dao.mapper.GenreRowMapper;
import com.github.ducknowledges.bookstore.domain.Author;
import com.github.ducknowledges.bookstore.domain.Genre;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@SpringBootTest
@DisplayName("Class GenreDaoJdbcTest")
class GenreDaoJdbcTest {
    @MockBean
    private GenreRowMapper genreRowMapper;

    @MockBean
    private NamedParameterJdbcTemplate jdbc;

    @Autowired
    private GenreDao genreDao;

    @Test
    @DisplayName("should read author by id")
    void shouldReadGenreById() {
        Genre genre = new Genre(1, "genre");
        when(jdbc.queryForObject("select ID, NAME from GENRE where ID = :id",
            Map.of("id", genre.getId()), genreRowMapper)).thenReturn(genre);

        Optional<Genre> expectedGenre = Optional.of(genre);
        Optional<Genre> actualGenre = genreDao.readById(genre.getId());
        assertThat(actualGenre).isEqualTo(expectedGenre);
    }

    @Test
    @DisplayName("should return empty book by id if book does not exist")
    void shouldReturnEmptyBook() {
        int notExistId = 2;
        when(jdbc.queryForObject(
            "select ID, NAME from GENRE where ID = :id",
            Map.of("id", notExistId), genreRowMapper))
            .thenThrow(EmptyResultDataAccessException.class);
        Optional<Genre> expectedGenre = Optional.empty();
        Optional<Genre> actualGenre = genreDao.readById(notExistId);
        assertThat(actualGenre).isEqualTo(expectedGenre);
    }

    @Test
    @DisplayName("should read all authors")
    void shouldReadAllAuthors() {
        Genre genre = new Genre(1, "author");
        when(jdbc.query("select ID, NAME from GENRE", genreRowMapper)).thenReturn(List.of(genre));

        List<Genre> expectedGenres = List.of(genre);
        List<Genre> actualGenres = genreDao.readAll();
        assertThat(actualGenres).isEqualTo(expectedGenres);
    }

}