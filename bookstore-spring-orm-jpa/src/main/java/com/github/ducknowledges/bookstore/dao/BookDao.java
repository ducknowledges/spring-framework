package com.github.ducknowledges.bookstore.dao;

import com.github.ducknowledges.bookstore.domain.Book;
import java.util.List;
import java.util.Optional;

public interface BookDao {
    Book create(Book book);

    Optional<Book> readById(long id);

    List<Book> readAll(int from, int size);

    Book update(Book book);

    void delete(long id);
}
