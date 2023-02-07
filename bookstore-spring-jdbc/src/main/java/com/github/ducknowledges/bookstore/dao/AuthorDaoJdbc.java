package com.github.ducknowledges.bookstore.dao;

import com.github.ducknowledges.bookstore.domain.Author;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AuthorDaoJdbc implements AuthorDao {

    private final NamedParameterJdbcTemplate jdbc;
    private final RowMapper<Author> authorMapper;

    public AuthorDaoJdbc(NamedParameterJdbcTemplate jdbc, RowMapper<Author> authorMapper) {
        this.jdbc = jdbc;
        this.authorMapper = authorMapper;
    }

    @Override
    public Optional<Author> findById(long id) {
        Author author;
        try {
            author = jdbc.queryForObject("select id, name from author where id = :id",
                Map.of("id", id), authorMapper);
        } catch (EmptyResultDataAccessException e) {
            author = null;
        }
        return Optional.ofNullable(author);
    }

    @Override
    public List<Author> findAll(long fromId, long toId) {
        return jdbc.query(
            "select id, name from author where id >= :fromId and id <= :toId",
            Map.of("fromId", fromId, "toId", toId),
            authorMapper);
    }
}
