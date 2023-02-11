package com.github.ducknowledges.bookstore.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.github.ducknowledges.bookstore.dao.AuthorDao;
import com.github.ducknowledges.bookstore.domain.Author;
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
@DisplayName("Class AuthorServiceImpl")
class AuthorServiceImplTest {

    @MockBean
    private AuthorDao authorDao;

    @Autowired
    private AuthorService authorService;

    @Test
    @DisplayName("should get author by id")
    void shouldGetAuthorById() {
        Author author = new Author(1L, "author");
        when(authorDao.findById(author.getId())).thenReturn(Optional.of(author));

        Optional<Author> expectedAuthor = Optional.of(author);
        Optional<Author> actualAuthor = authorService.getAuthor(author.getId());
        assertThat(actualAuthor).isEqualTo(expectedAuthor);
    }

    @Test
    @DisplayName("should get all authors")
    void shouldGetAuthors() {
        int page = 0;
        int size = 2;
        Author author = new Author(1L, "author");
        when(authorDao.findAll(PageRequest.of(page, size)))
            .thenReturn(new PageImpl<>(List.of(author)));

        Page<Author> expectedAuthors = new PageImpl<>(List.of(author));
        Page<Author> actualAuthors = authorService.getAuthors(page, size);
        assertThat(actualAuthors).isEqualTo(expectedAuthors);
    }
}