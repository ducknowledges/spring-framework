package com.github.ducknowledges.bookstore.dao;

import com.github.ducknowledges.bookstore.domain.Author;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class AuthorDaoJpa implements AuthorDao {

    @PersistenceContext
    private final EntityManager manager;

    public AuthorDaoJpa(EntityManager manager) {
        this.manager = manager;
    }

    @Override
    public Optional<Author> readById(long id) {
        return Optional.ofNullable(manager.find(Author.class, id));
    }

    @Override
    public List<Author> readAll(int from, int size) {
        return manager.createQuery("select a from Author a", Author.class)
            .setFirstResult(from < 1 ? 0 : from - 1)
            .setMaxResults(size < 1 ? 0 : size)
            .getResultList();
    }
}
