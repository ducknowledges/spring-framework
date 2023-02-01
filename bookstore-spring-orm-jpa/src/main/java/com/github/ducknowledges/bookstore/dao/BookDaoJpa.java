package com.github.ducknowledges.bookstore.dao;

import static java.util.Objects.isNull;

import com.github.ducknowledges.bookstore.domain.Book;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class BookDaoJpa implements BookDao {

    @PersistenceContext
    private final EntityManager manager;

    public BookDaoJpa(EntityManager manager) {
        this.manager = manager;
    }

    @Override
    public Book save(Book book) {
        if (isNull(book.getId())) {
            manager.persist(book);
            return book;
        }
        return manager.merge(book);
    }

    @Override
    public Optional<Book> readById(long id) {
        return Optional.ofNullable(manager.find(Book.class, id));
    }

    @Override
    public List<Book> readAll(int from, int size) {
        return manager.createQuery("select b from Book b join fetch b.author join fetch b.genre",
                Book.class)
            .setFirstResult(from < 1 ? 0 : from - 1)
            .setMaxResults(size < 1 ? 0 : size)
            .getResultList();
    }

    @Override
    public void delete(long id) {
        Optional<Book> book = this.readById(id);
        book.ifPresent(manager::remove);
    }
}
