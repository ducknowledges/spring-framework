package com.github.ducknowledges.bookstore.dao;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.ducknowledges.bookstore.domain.Author;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import(AuthorDaoJpa.class)
@DisplayName("Class AuthorDaoJpa")
class AuthorDaoJpaTest {

    private static final long FIRST_AUTHOR_ID = 1L;
    private static final long AUTHOR_ENTRIES_SIZE = 2;

    @Autowired
    private TestEntityManager manager;

    @Autowired
    private AuthorDao authorDao;

    @Test
    @DisplayName("should read author by id")
    void shouldReadAuthorById() {
        var author = manager.find(Author.class, FIRST_AUTHOR_ID);
        Optional<Author> expectedAuthor = Optional.of(author);
        Optional<Author> actualAuthor = authorDao.readById(FIRST_AUTHOR_ID);
        assertThat(actualAuthor).isEqualTo(expectedAuthor);
    }

    @Test
    @DisplayName("should return empty author by id if author does not exist")
    void shouldReturnEmptyAuthor() {
        Optional<Author> actualAuthor = authorDao.readById(AUTHOR_ENTRIES_SIZE + 1);
        assertThat(actualAuthor).isEmpty();
    }

    @Test
    @DisplayName("should read all authors")
    void shouldReadAllAuthors() {
        List<Author> expectedAuthors = manager.getEntityManager()
            .createQuery(
                "select a from Author a where a.id >= :fromId and a.id <= :toId",
                Author.class)
            .setParameter("fromId", FIRST_AUTHOR_ID)
            .setParameter("toId", FIRST_AUTHOR_ID + 2)
            .getResultList();
        List<Author> actualAuthors = authorDao.readAll(FIRST_AUTHOR_ID, FIRST_AUTHOR_ID + 2);
        assertThat(actualAuthors).hasSize(2).usingRecursiveComparison().isEqualTo(expectedAuthors);
    }

    @Test
    @DisplayName("should return empty authors")
    void shouldReadEmptyAuthors() {
        List<Author> actualAuthors = authorDao.readAll(
            AUTHOR_ENTRIES_SIZE + 1,
            AUTHOR_ENTRIES_SIZE + 2
        );
        assertThat(actualAuthors).isEmpty();
    }
}