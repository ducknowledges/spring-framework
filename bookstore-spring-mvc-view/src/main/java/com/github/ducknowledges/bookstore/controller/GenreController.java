package com.github.ducknowledges.bookstore.controller;

import com.github.ducknowledges.bookstore.domain.Genre;
import com.github.ducknowledges.bookstore.service.GenreService;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GenreController {
    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping("/genres")
    public String genresPage(@RequestParam("page") Optional<Integer> optionalPage,
                             @RequestParam("size") Optional<Integer> optionalSize,
                             Model model) {
        int page = optionalPage.filter(p -> p > 0).orElse(1);
        int size = optionalSize.filter(s -> s == 5).orElse(5);
        Page<Genre> genres = genreService.getGenres(page - 1, size);
        model.addAttribute("genres", genres.getContent())
            .addAttribute("totalPages", genres.getTotalPages())
            .addAttribute("currentPage", page);
        return "pages/genres";
    }
}
