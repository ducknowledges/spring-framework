package com.github.ducknowledges.bookstore.service;

import com.github.ducknowledges.bookstore.dao.BookDao;
import com.github.ducknowledges.bookstore.domain.Author;
import com.github.ducknowledges.bookstore.domain.Book;
import com.github.ducknowledges.bookstore.domain.Genre;
import com.github.ducknowledges.bookstore.service.exception.BookServiceException;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;
    private final AuthorService authorService;
    private final GenreService genreService;

    public BookServiceImpl(BookDao bookDao,
                           AuthorService authorService,
                           GenreService genreService) {
        this.bookDao = bookDao;
        this.authorService = authorService;
        this.genreService = genreService;
    }

    @Override
    public void createBook(Book book) {
        bookDao.create(book);
    }

    @Override
    public Book getBook(int id) {
        return bookDao.readById(id).orElseThrow(
            () -> new BookServiceException("Book does not exist")
        );
    }

    @Override
    public List<Book> getBooks() {
        return bookDao.readAll();
    }

    @Override
    public void update(Book newBook) {
        getBook(newBook.getId());
        bookDao.update(newBook);
    }

    @Override
    public void delete(int id) {
        bookDao.delete(id);
    }
}
