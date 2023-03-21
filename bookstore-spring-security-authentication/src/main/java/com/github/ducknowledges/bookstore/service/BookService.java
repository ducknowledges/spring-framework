package com.github.ducknowledges.bookstore.service;

import com.github.ducknowledges.bookstore.domain.Book;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface BookService {
    Book create(Book book);

    Optional<Book> getBook(long id);

    Page<Book> getBooks(int page, int size);

    public Book save(Book book);

    void deleteById(long id);

    long count();
}
