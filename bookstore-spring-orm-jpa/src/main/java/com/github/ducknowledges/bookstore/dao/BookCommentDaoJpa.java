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
    public List<BookComment> readAll(int from, int size) {
        return manager.createQuery("select c from BookComment c",
                BookComment.class)
            .setFirstResult(from < 1 ? 0 : from - 1)
            .setMaxResults(size < 1 ? 0 : size)
            .getResultList();
    }

    @Override
    public List<BookComment> readAllByBookId(long bookId) {
        return manager.createQuery("select c from BookComment c where c.book.id = :bookId",
                BookComment.class)
            .setParameter("bookId", bookId)
            .getResultList();
    }

    @Override
    public BookComment update(BookComment comment) {
        return this.save(comment);
    }

    @Override
    public void deleteAllByBookId(long bookId) {
        manager.createQuery("delete from BookComment c where c.book.id = :bookId")
             .setParameter("bookId", bookId)
             .executeUpdate();
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
