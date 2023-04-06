package com.github.ducknowledges.bookstore.controller;

import com.github.ducknowledges.bookstore.domain.Author;
import com.github.ducknowledges.bookstore.service.AuthorService;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/authors")
    public String authorsPage(@RequestParam("page") Optional<Integer> optionalPage,
                              @RequestParam("size") Optional<Integer> optionalSize,
                              Model model) {
        int page = optionalPage.filter(p -> p > 0).orElse(1);
        int size = optionalSize.filter(s -> s == 5).orElse(5);
        Page<Author> authors = authorService.getAuthors(page - 1, size);
        model.addAttribute("authors", authors.getContent())
            .addAttribute("totalPages", authors.getTotalPages())
            .addAttribute("currentPage", page);
        return "pages/authors";
    }
}
