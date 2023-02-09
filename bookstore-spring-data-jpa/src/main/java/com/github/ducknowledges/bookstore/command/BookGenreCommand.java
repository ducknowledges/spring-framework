package com.github.ducknowledges.bookstore.command;

import com.github.ducknowledges.bookstore.domain.Genre;
import com.github.ducknowledges.bookstore.printformatter.PrintFormatter;
import com.github.ducknowledges.bookstore.service.GenreService;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class BookGenreCommand {

    private final GenreService genreService;
    private final PrintFormatter<Genre> printformatter;

    public BookGenreCommand(GenreService genreService,
                            PrintFormatter<Genre> printformatter) {
        this.genreService = genreService;
        this.printformatter = printformatter;
    }

    @ShellMethod(value = "Read all genres command", key = "read-genres")
    public String getGenres(@ShellOption int page,
                            @ShellOption int size) {
        Page<Genre> genres = genreService.getGenres(page, size);
        return printformatter.format(genres);
    }

    @ShellMethod(value = "Read genre command", key = "read-genre")
    public String getAuthor(@ShellOption long genreId) {
        Optional<Genre> author = genreService.getGenre(genreId);
        if (author.isPresent()) {
            return printformatter.format(author.get());
        }
        return "Genre doesn't exist";
    }
}
