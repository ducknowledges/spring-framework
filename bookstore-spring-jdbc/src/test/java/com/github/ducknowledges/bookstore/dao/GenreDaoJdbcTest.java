package com.github.ducknowledges.bookstore.dao;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.ducknowledges.bookstore.dao.mapper.GenreRowMapper;
import com.github.ducknowledges.bookstore.domain.Genre;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@JdbcTest
@Import({GenreDaoJdbc.class, GenreRowMapper.class})
@DisplayName("Class GenreDaoJdbcTest")
class GenreDaoJdbcTest {

    private static final long FIRST_GENRE_ID = 1L;
    private static final long GENRE_ENTRIES_SIZE = 2L;

    @Autowired
    private GenreDao genreDao;

    @Autowired
    GenreRowMapper rowMapper;

    @Autowired
    private NamedParameterJdbcTemplate jdbc;

    @Test
    @DisplayName("should read genre by id")
    void shouldReadGenreById() {
        Genre genre = jdbc.queryForObject("select id, name from genre where id = :id",
            Map.of("id", FIRST_GENRE_ID), rowMapper);

        Optional<Genre> expectedGenre = Optional.ofNullable(genre);
        Optional<Genre> actualGenre = genreDao.findById(FIRST_GENRE_ID);
        assertThat(actualGenre).isNotEmpty().isEqualTo(expectedGenre);
    }

    @Test
    @DisplayName("should return empty genre by id if genre does not exist")
    void shouldReturnEmptyAuthor() {
        Optional<Genre> actualGenre = genreDao.findById(GENRE_ENTRIES_SIZE + 1);
        assertThat(actualGenre).isEmpty();
    }

    @Test
    @DisplayName("should read all genres")
    void shouldReadAllAuthors() {
        List<Genre> expectedGenres = jdbc.query(
            "select id, name from genre where id >= :fromId and id <= :toId",
            Map.of("fromId", FIRST_GENRE_ID,
                "toId", FIRST_GENRE_ID + 1),
            rowMapper);

        List<Genre> actualGenres = genreDao.findAll(FIRST_GENRE_ID, FIRST_GENRE_ID + 1);
        assertThat(actualGenres).isEqualTo(expectedGenres);
    }
}