package com.github.ducknowledges.bookstore.service;

import com.github.ducknowledges.bookstore.domain.Book;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {
    Book create(Book book);

    Optional<Book> getBook(long id);

    Page<Book> getBooks(Pageable pageable);

    Book save(Book book);

    void deleteById(long id);

    long count();
}
