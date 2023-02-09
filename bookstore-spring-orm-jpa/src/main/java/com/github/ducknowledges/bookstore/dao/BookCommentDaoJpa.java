package com.github.ducknowledges.bookstore.dao;

import static java.util.Objects.isNull;

import com.github.ducknowledges.bookstore.domain.BookComment;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class BookCommentDaoJpa implements BookCommentDao {

    @PersistenceContext
    private final EntityManager manager;

    public BookCommentDaoJpa(EntityManager manager) {
        this.manager = manager;
    }

    @Override
    public BookComment create(BookComment comment) {
        return this.save(comment);
    }

    @Override
    public Optional<BookComment> readById(long id) {
        return Optional.ofNullable(manager.find(BookComment.class, id));
    }

    @Override
    public List<BookComment> readAll(long fromId, long toId) {
        return manager.createQuery(
                "select c from BookComment c where c.id >= :fromId and c.id <= :toId",
                BookComment.class)
            .setParameter("fromId", fromId)
            .setParameter("toId", toId)
            .getResultList();
    }

    @Override
    public BookComment update(BookComment comment) {
        return this.save(comment);
    }

    @Override
    public void delete(long id) {
        Optional<BookComment> comment = this.readById(id);
        comment.ifPresent(manager::remove);
    }

    private BookComment save(BookComment comment) {
        if (isNull(comment.getId())) {
            manager.persist(comment);
            return comment;
        }
        return manager.merge(comment);
    }
}
