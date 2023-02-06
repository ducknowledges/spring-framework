package com.github.ducknowledges.bookstore.service;

import com.github.ducknowledges.bookstore.domain.Book;
import java.util.List;
import java.util.Optional;

public interface BookService {
    Book createBook(Book book);

    Optional<Book> getBook(long id);

    List<Book> getBooks(long fromId, long toId);

    Book update(Book book);

    void deleteById(long id);
}
