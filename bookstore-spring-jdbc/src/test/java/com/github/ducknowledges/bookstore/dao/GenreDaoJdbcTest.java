package com.github.ducknowledges.bookstore.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.github.ducknowledges.bookstore.dao.mapper.AuthorRowMapper;
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
    private NamedParameterJdbcTemplate jdbc;

    @Autowired
    private GenreDao genreDao;

    @Test
    @DisplayName("should read genre by id")
    void shouldReadGenreById() {
        Genre genre = new Genre(1L, "genre");
        when(jdbc.queryForObject(
            eq("select id, name from genre where id = :id"),
            eq(Map.of("id", genre.getId())),
            any(GenreRowMapper.class))
        ).thenReturn(genre);

        Optional<Genre> expectedGenre = Optional.of(genre);
        Optional<Genre> actualGenre = genreDao.readById(genre.getId());
        assertThat(actualGenre).isEqualTo(expectedGenre);
    }

    @Test
    @DisplayName("should return empty genre by id if genre does not exist")
    void shouldReturnEmptyBook() {
        long notExistId = 3;
        when(jdbc.queryForObject(
            eq("select id, name from genre where id = :id"),
            eq(Map.of("id", notExistId)),
            any(GenreRowMapper.class))
        ).thenThrow(EmptyResultDataAccessException.class);
        Optional<Genre> expectedGenre = Optional.empty();
        Optional<Genre> actualGenre = genreDao.readById(notExistId);
        assertThat(actualGenre).isEqualTo(expectedGenre);
    }

    @Test
    @DisplayName("should read all genres")
    void shouldReadAllAuthors() {
        Genre genre = new Genre(1L, "author");
        when(jdbc.query(
            eq("select id, name from genre"),
            any(GenreRowMapper.class))
        ).thenReturn(List.of(genre));

        List<Genre> expectedGenres = List.of(genre);
        List<Genre> actualGenres = genreDao.readAll();
        assertThat(actualGenres).isEqualTo(expectedGenres);
    }

}