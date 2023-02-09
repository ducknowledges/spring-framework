package com.github.ducknowledges.bookstore.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.github.ducknowledges.bookstore.dao.mapper.AuthorRowMapper;
import com.github.ducknowledges.bookstore.domain.Author;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@JdbcTest
@Import({AuthorDaoJdbc.class, AuthorRowMapper.class})
@DisplayName("Class AuthorDaoJdbc")
class AuthorDaoJdbcTest {

    private static final long FIRST_AUTHOR_ID = 1L;
    private static final long AUTHOR_ENTRIES_SIZE = 2L;

    @Autowired
    private AuthorDao authorDao;

    @Autowired
    AuthorRowMapper rowMapper;

    @Autowired
    private NamedParameterJdbcTemplate jdbc;

    @Test
    @DisplayName("should read author by id")
    void shouldReadAuthorById() {
        Author author = jdbc.queryForObject(
            "select id, name from author where id = :id",
            Map.of("id", FIRST_AUTHOR_ID), rowMapper);

        Optional<Author> expectedAuthor = Optional.ofNullable(author);
        Optional<Author> actualAuthor = authorDao.findById(FIRST_AUTHOR_ID);
        assertThat(actualAuthor).isNotEmpty().isEqualTo(expectedAuthor);
    }

    @Test
    @DisplayName("should return empty author by id if author does not exist")
    void shouldReturnEmptyAuthor() {
        Optional<Author> actualAuthor = authorDao.findById(AUTHOR_ENTRIES_SIZE + 1);
        assertThat(actualAuthor).isEmpty();
    }

    @Test
    @DisplayName("should read all authors")
    void shouldReadAllAuthors() {
        List<Author> expectedAuthors = jdbc.query(
            "select id, name from author where id >= :fromId and id <= :toId",
            Map.of("fromId", FIRST_AUTHOR_ID, "toId", FIRST_AUTHOR_ID + 1),
            rowMapper);
        List<Author> actualAuthors = authorDao.findAll(
            FIRST_AUTHOR_ID, FIRST_AUTHOR_ID + 1);
        assertThat(actualAuthors).hasSize(2).isEqualTo(expectedAuthors);
    }
}