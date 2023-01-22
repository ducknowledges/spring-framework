package com.github.ducknowledges.bookstore.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.github.ducknowledges.bookstore.dao.AuthorDao;
import com.github.ducknowledges.bookstore.dao.mapper.AuthorRowMapper;
import com.github.ducknowledges.bookstore.domain.Author;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@SpringBootTest
@DisplayName("Class AuthorServiceImpl")
class AuthorServiceImplTest {

    @MockBean
    private AuthorDao authorDao;

    @Autowired
    private AuthorService authorService;

    @Test
    @DisplayName("should get author by id")
    void shouldGetAuthorById() {
        Author author = new Author(1, "author");
        when(authorDao.readById(author.getId())).thenReturn(Optional.of(author));

        Optional<Author> expectedAuthor = Optional.of(author);
        Optional<Author> actualAuthor = authorService.getAuthorById(author.getId());
        assertThat(actualAuthor).isEqualTo(expectedAuthor);
    }

    @Test
    @DisplayName("should get all authors")
    void shouldGetAuthors() {
        Author author = new Author(1, "author");
        when(authorDao.readAll()).thenReturn(List.of(author));

        List<Author> expectedAuthors = List.of(author);
        List<Author> actualAuthors = authorService.getAuthors();
        assertThat(actualAuthors).isEqualTo(expectedAuthors);
    }
}