package com.github.ducknowledges.bookstore.service;

import com.github.ducknowledges.bookstore.domain.Book;
import java.util.List;
import java.util.Optional;

public interface BookService {
    Book createBook(Book book);

    Optional<Book> getBook(long id);

    List<Book> getBooks(int from, int size);

    Book update(Book book);

    void delete(long id);
}
