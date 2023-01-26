package com.github.ducknowledges.bookstore.command;

import com.github.ducknowledges.bookstore.domain.Author;
import com.github.ducknowledges.bookstore.domain.Book;
import com.github.ducknowledges.bookstore.domain.Genre;
import com.github.ducknowledges.bookstore.printformatter.PrintFormatter;
import com.github.ducknowledges.bookstore.service.AuthorService;
import com.github.ducknowledges.bookstore.service.BookService;
import com.github.ducknowledges.bookstore.service.GenreService;
import com.github.ducknowledges.bookstore.service.exception.BookServiceException;
import java.util.List;
import java.util.Optional;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class BookCommand {

    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final PrintFormatter<Book> printFormatter;

    public BookCommand(BookService bookService,
                       AuthorService authorService,
                       GenreService genreService,
                       PrintFormatter<Book> printFormatter) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.genreService = genreService;
        this.printFormatter = printFormatter;
    }

    @ShellMethod(value = "Create book command", key = "create-book")
    public String create(@ShellOption String name,
                         @ShellOption int authorId,
                         @ShellOption int genreId) {
        Optional<Author> author = authorService.getAuthorById(authorId);
        if (author.isEmpty()) {
            return  "Author does not exist";
        }
        Optional<Genre> genre = genreService.getGenreById(genreId);
        if (genre.isEmpty()) {
            return "Genre does not exist";
        }
        Book book = new Book(name, author.get(), genre.get());
        bookService.createBook(book);
        return "Book was created";
    }

    @ShellMethod(value = "Read book command", key = "read-book")
    public String book(@ShellOption int bookId) {
        try {
            Book book = bookService.getBook(bookId);
            return printFormatter.format(book);
        } catch (BookServiceException e) {
            return e.getMessage();
        }
    }

    @ShellMethod(value = "Read all books command", key = "read-books")
    public String books() {
        List<Book> books = bookService.getBooks();
        return printFormatter.format(books);
    }

    @ShellMethod(value = "Update book command", key = "update-book")
    public String update(@ShellOption int bookId,
                         @ShellOption String name,
                         @ShellOption int authorId,
                         @ShellOption int genreId) {
        Optional<Author> author = authorService.getAuthorById(authorId);
        if (author.isEmpty()) {
            return "Author does not exist";
        }
        Optional<Genre> genre = genreService.getGenreById(genreId);
        if (genre.isEmpty()) {
            return "Genre does not exist";
        }

        try {
            Book book = new Book(bookId, name, author.get(), genre.get());
            bookService.update(book);
            return "Book was updated";
        } catch (BookServiceException e) {
            return e.getMessage();
        }
    }

    @ShellMethod(value = "Delete book command", key = "delete-book")
    public String delete(@ShellOption int bookId) {
        bookService.delete(bookId);
        return "Book was deleted";
    }
}
