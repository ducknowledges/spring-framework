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
@DisplayName("Class AuthorDaoJpa")
class AuthorDaoJpaTest {

    private static final long FIRST_AUTHOR_ID = 1L;
    private static final long AUTHOR_ENTRIES_SIZE = 2;

    @Autowired
    private TestEntityManager manager;

    @Autowired
    private AuthorDao authorDao;

    @Test
    @DisplayName("should read all authors")
    void shouldReadAllAuthors() {
        List<Author> expectedAuthors = manager.getEntityManager()
            .createQuery("select a from Author a where a.id >= :from and a.id <= :to", Author.class)
            .setParameter("from", FIRST_AUTHOR_ID)
            .setParameter("to", AUTHOR_ENTRIES_SIZE - 1)
            .getResultList();
        List<Author> actualAuthors = authorDao.findAllByIdGreaterThanEqualAndIdLessThanEqual(
            FIRST_AUTHOR_ID, AUTHOR_ENTRIES_SIZE - 1
        );
        assertThat(actualAuthors).usingRecursiveComparison().isEqualTo(expectedAuthors);
    }

    @Test
    @DisplayName("should return empty authors")
    void shouldReadEmptyAuthors() {
        List<Author> expectedAuthors = manager.getEntityManager()
            .createQuery("select a from Author a where a.id >= :from and a.id <= :to", Author.class)
            .setParameter("from", AUTHOR_ENTRIES_SIZE + 1)
            .setParameter("to", AUTHOR_ENTRIES_SIZE + 2)
            .getResultList();
        List<Author> actualAuthors = authorDao.findAllByIdGreaterThanEqualAndIdLessThanEqual(
            AUTHOR_ENTRIES_SIZE + 1, AUTHOR_ENTRIES_SIZE + 2
        );
        assertThat(actualAuthors).usingRecursiveComparison().isEqualTo(expectedAuthors);
    }
}