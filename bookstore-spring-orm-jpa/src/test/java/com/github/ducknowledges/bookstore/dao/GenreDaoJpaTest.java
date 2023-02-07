package com.github.ducknowledges.bookstore.dao;

import static org.assertj.core.api.Assertions.assertThat;

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
@Import(GenreDaoJpa.class)
@DisplayName("Class GenreDaoJpa")
class GenreDaoJpaTest {

    private static final long FIRST_GENRE_ID = 1L;
    private static final long GENRE_ENTRIES_SIZE = 2;

    @Autowired
    private TestEntityManager manager;

    @Autowired
    private GenreDao genreDao;

    @Test
    @DisplayName("should read genre by id")
    void shouldReadGenreById() {
        Genre genre = manager.find(Genre.class, FIRST_GENRE_ID);
        Optional<Genre> expectedGenre = Optional.of(genre);
        Optional<Genre> actualGenre = genreDao.readById(FIRST_GENRE_ID);
        assertThat(actualGenre).isNotEmpty().isEqualTo(expectedGenre);
    }

    @Test
    @DisplayName("should return empty genre by id if genre does not exist")
    void shouldReturnEmptyGenre() {
        Optional<Genre> actualGenre = genreDao.readById(GENRE_ENTRIES_SIZE + 1);
        assertThat(actualGenre).isEmpty();
    }

    @Test
    @DisplayName("should read all genre")
    void shouldReadAllGenres() {
        List<Genre> expectedGenres = manager.getEntityManager().createQuery(
                "select g from Genre g where g.id >= :fromId and g.id <= :toId",
                Genre.class)
            .setParameter("fromId", FIRST_GENRE_ID)
            .setParameter("toId", FIRST_GENRE_ID + 1)
            .getResultList();
        List<Genre> actualGenres = genreDao.readAll(
            FIRST_GENRE_ID,
            FIRST_GENRE_ID + 1);
        assertThat(actualGenres).hasSize(2)
            .usingRecursiveComparison().isEqualTo(expectedGenres);
    }

    @Test
    @DisplayName("should return empty genre")
    void shouldReadEmptyGenres() {
        List<Genre> actualGenres = genreDao.readAll(
            GENRE_ENTRIES_SIZE + 1,
            GENRE_ENTRIES_SIZE + 2
        );
        assertThat(actualGenres).isEmpty();
    }
}