package com.github.ducknowledges.bookstore.dao;

import com.github.ducknowledges.bookstore.domain.Genre;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class GenreDaoJpa implements GenreDao {

    @PersistenceContext
    private final EntityManager manager;

    public GenreDaoJpa(EntityManager manager) {
        this.manager = manager;
    }

    @Override
    public Optional<Genre> readById(long id) {
        return Optional.ofNullable(manager.find(Genre.class, id));
    }

    @Override
    public List<Genre> readAll(int from, int size) {
        return manager.createQuery("select g from Genre g", Genre.class)
            .setFirstResult(from < 1 ? 0 : from - 1)
            .setMaxResults(size < 1 ? 0 : size)
            .getResultList();
    }
}
