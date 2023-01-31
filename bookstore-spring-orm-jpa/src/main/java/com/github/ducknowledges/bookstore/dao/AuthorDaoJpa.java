package com.github.ducknowledges.bookstore.dao;

import com.github.ducknowledges.bookstore.domain.Author;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class AuthorDaoJpa implements AuthorDao {

    private final EntityManager manager;

    public AuthorDaoJpa(EntityManager manager) {
        this.manager = manager;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Author> readById(long id) {
        return Optional.ofNullable(manager.find(Author.class, id));
    }

    @Transactional(readOnly = true)
    @Override
    public List<Author> readAll() {
        return manager.createQuery("select a from Author a", Author.class).getResultList();
    }
}
