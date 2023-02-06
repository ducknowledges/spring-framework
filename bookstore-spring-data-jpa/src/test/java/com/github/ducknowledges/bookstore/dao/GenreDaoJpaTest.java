package com.github.ducknowledges.bookstore.dao;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.ducknowledges.bookstore.domain.Author;
import com.github.ducknowledges.bookstore.domain.Genre;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

@DataJpaTest
@DisplayName("Class GenreDaoJpa")
class GenreDaoJpaTest {

    private static final long FIRST_GENRE_ID = 1L;
    private static final long GENRE_ENTRIES_SIZE = 2;

    @Autowired
    private TestEntityManager manager;

    @Autowired
    private GenreDao genreDao;

    @Test
    @DisplayName("should read all genre")
    void shouldReadAllGenres() {
        List<Genre> expectedGenres = manager.getEntityManager()
            .createQuery(
                "select g from Genre g where g.id >= :fromId and g.id <= :toId",
                Genre.class)
            .setParameter("fromId", FIRST_GENRE_ID)
            .setParameter("toId", GENRE_ENTRIES_SIZE - 1)
            .getResultList();
        List<Genre> actualGenres = genreDao.findAllByIdGreaterThanEqualAndIdLessThanEqual(
            FIRST_GENRE_ID, GENRE_ENTRIES_SIZE - 1
        );
        assertThat(actualGenres)
            .usingRecursiveComparison().isEqualTo(expectedGenres);
    }

    @Test
    @DisplayName("should return empty genre")
    void shouldReadEmptyGenres() {
        List<Genre> expectedGenres = manager.getEntityManager()
            .createQuery(
                "select g from Genre g where g.id >= :fromId and g.id <= :toId",
                Genre.class)
            .setParameter("fromId", GENRE_ENTRIES_SIZE + 1)
            .setParameter("toId", GENRE_ENTRIES_SIZE + 2)
            .getResultList();
        List<Genre> actualGenres = genreDao.findAllByIdGreaterThanEqualAndIdLessThanEqual(
            GENRE_ENTRIES_SIZE + 1, GENRE_ENTRIES_SIZE + 2
        );
        assertThat(actualGenres).usingRecursiveComparison().isEqualTo(expectedGenres);
    }
}