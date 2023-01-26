package com.github.ducknowledges.bookstore.command;

import com.github.ducknowledges.bookstore.domain.Genre;
import com.github.ducknowledges.bookstore.printformatter.PrintFormatter;
import com.github.ducknowledges.bookstore.service.GenreService;
import java.util.List;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

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
    public String authors() {
        List<Genre> genres = genreService.getGenres();
        return printformatter.format(genres);
    }
}
