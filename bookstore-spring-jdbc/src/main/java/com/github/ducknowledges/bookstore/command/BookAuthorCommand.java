package com.github.ducknowledges.bookstore.command;

import com.github.ducknowledges.bookstore.domain.Author;
import com.github.ducknowledges.bookstore.printformatter.PrintFormatter;
import com.github.ducknowledges.bookstore.service.AuthorService;
import java.util.List;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

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
    public String authors() {
        List<Author> authors = authorService.getAuthors();
        return printformatter.format(authors);
    }
}
