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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@SpringBootTest
@Import(AuthorDaoJdbc.class)
@DisplayName("Class AuthorDaoJdbc")
class AuthorDaoJdbcTest {

    @MockBean
    private NamedParameterJdbcTemplate jdbc;

    @Autowired
    private AuthorDao authorDao;

    @Test
    @DisplayName("should read author by id")
    void shouldReadAuthorById() {
        Author author = new Author(1, "author");

        when(jdbc.queryForObject(
            eq("select id, name from author where id = :id"),
            eq(Map.of("id", 1)),
            any(AuthorRowMapper.class))
        ).thenReturn(author);
        Optional<Author> expectedAuthor = Optional.of(author);
        Optional<Author> actualAuthor = authorDao.readById(author.getId());
        assertThat(actualAuthor).isEqualTo(expectedAuthor);
    }

    @Test
    @DisplayName("should return empty book by id if book does not exist")
    void shouldReturnEmptyBook() {
        int notExistId = 2;
        when(jdbc.queryForObject(
            eq("select id, name from author where id = :id"),
            eq(Map.of("id", 1)),
            any(AuthorRowMapper.class))
        ).thenThrow(EmptyResultDataAccessException.class);
        Optional<Author> expectedAuthor = Optional.empty();
        Optional<Author> actualAuthor = authorDao.readById(notExistId);
        assertThat(actualAuthor).isEqualTo(expectedAuthor);
    }

    @Test
    @DisplayName("should read all authors")
    void shouldReadAllAuthors() {
        Author author = new Author(1, "author");
        when(jdbc.query(
            eq("select id, name from author"),
            any(AuthorRowMapper.class))
        ).thenReturn(List.of(author));

        List<Author> expectedAuthors = List.of(author);
        List<Author> actualAuthors = authorDao.readAll();
        assertThat(actualAuthors).isEqualTo(expectedAuthors);
    }
}