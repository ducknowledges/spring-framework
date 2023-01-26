package com.github.ducknowledges.bookstore.service;

import com.github.ducknowledges.bookstore.domain.Book;
import java.util.List;

public interface BookService {
    void createBook(Book book);

    Book getBook(int id);

    List<Book> getBooks();

    void update(Book book);

    void delete(int id);
}
