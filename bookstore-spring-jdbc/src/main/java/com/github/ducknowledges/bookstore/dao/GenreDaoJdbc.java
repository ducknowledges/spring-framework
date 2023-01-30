package com.github.ducknowledges.bookstore.dao;

import com.github.ducknowledges.bookstore.domain.Genre;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class GenreDaoJdbc implements GenreDao {

    private final NamedParameterJdbcTemplate jdbc;
    private final RowMapper<Genre> genreMapper;

    public GenreDaoJdbc(NamedParameterJdbcTemplate jdbc, RowMapper<Genre> genreMapper) {
        this.jdbc = jdbc;
        this.genreMapper = genreMapper;
    }

    @Override
    public Optional<Genre> readById(long id) {
        Genre genre;
        try {
            genre = jdbc.queryForObject("select id, name from genre where id = :id",
                Map.of("id", id), genreMapper);
        } catch (EmptyResultDataAccessException e) {
            genre = null;
        }
        return Optional.ofNullable(genre);
    }

    @Override
    public List<Genre> readAll() {
        return jdbc.query("select id, name from genre", genreMapper);
    }
}
