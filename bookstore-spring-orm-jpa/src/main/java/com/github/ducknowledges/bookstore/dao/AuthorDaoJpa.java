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
    public List<Author> readAll(long fromId, long toId) {
        return manager.createQuery(
            "select a from Author a where a.id >= :fromId and a.id <= :toId",
                Author.class)
            .setParameter("fromId", fromId)
            .setParameter("toId", toId)
            .getResultList();
    }
}
