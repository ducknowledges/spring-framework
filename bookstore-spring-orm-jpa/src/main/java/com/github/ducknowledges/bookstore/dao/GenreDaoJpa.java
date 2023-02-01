package com.github.ducknowledges.bookstore.dao;

import com.github.ducknowledges.bookstore.domain.Genre;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class GenreDaoJpa implements GenreDao {

    private final EntityManager manager;

    public GenreDaoJpa(EntityManager manager) {
        this.manager = manager;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Genre> readById(long id) {
        return Optional.ofNullable(manager.find(Genre.class, id));
    }

    @Transactional(readOnly = true)
    @Override
    public List<Genre> readAll() {
        return manager.createQuery("select g from Genre g", Genre.class).getResultList();
    }
}
