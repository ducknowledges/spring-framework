package com.github.ducknowledges.bookstore.controller;

import com.github.ducknowledges.bookstore.service.AuthorService;
import com.github.ducknowledges.bookstore.service.BookService;
import com.github.ducknowledges.bookstore.service.GenreService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final BookService bookService;
    private final GenreService genreService;
    private final AuthorService authorService;

    public HomeController(BookService bookService,
                          GenreService genreService,
                          AuthorService authorService) {
        this.bookService = bookService;
        this.genreService = genreService;
        this.authorService = authorService;
    }

    @GetMapping("/")
    public String homePage(Model model) {
        model.addAttribute("bookCounter", bookService.count())
            .addAttribute("authorCounter", authorService.count())
            .addAttribute("genreCounter", genreService.count());
        return "index";
    }
}
