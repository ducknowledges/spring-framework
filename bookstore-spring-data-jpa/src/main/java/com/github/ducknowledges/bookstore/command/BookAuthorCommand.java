package com.github.ducknowledges.bookstore.command;

import com.github.ducknowledges.bookstore.domain.Author;
import com.github.ducknowledges.bookstore.printformatter.PrintFormatter;
import com.github.ducknowledges.bookstore.service.AuthorService;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class BookAuthorCommand {

    private final AuthorService authorService;
    private final PrintFormatter<Author> printformatter;

    public BookAuthorCommand(AuthorService authorService,
                             PrintFormatter<Author> printformatter) {
        this.authorService = authorService;
        this.printformatter = printformatter;
    }

    @ShellMethod(value = "Read all authors command", key = "read-authors")
    public String getAuthors(@ShellOption int page,
                             @ShellOption int size) {
        Page<Author> authors = authorService.getAuthors(page, size);
        return printformatter.format(authors);
    }

    @ShellMethod(value = "Read author command", key = "read-author")
    public String getAuthor(@ShellOption long authorId) {
        Optional<Author> author = authorService.getAuthor(authorId);
        if (author.isPresent()) {
            return printformatter.format(author.get());
        }
        return "Author doesn't exist";
    }
}
