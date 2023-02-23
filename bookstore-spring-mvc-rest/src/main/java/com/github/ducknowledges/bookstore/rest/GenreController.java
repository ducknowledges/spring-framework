package com.github.ducknowledges.bookstore.rest;

import com.github.ducknowledges.bookstore.domain.Genre;
import com.github.ducknowledges.bookstore.dto.PageRequestDto;
import com.github.ducknowledges.bookstore.dto.PageResponseDto;
import com.github.ducknowledges.bookstore.mapper.PageResponseMapper;
import com.github.ducknowledges.bookstore.service.GenreService;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GenreController {
    private final GenreService genreService;
    private final PageResponseMapper<Genre, Genre> mapper;

    public GenreController(GenreService genreService,
                           PageResponseMapper<Genre, Genre> mapper) {
        this.genreService = genreService;
        this.mapper = mapper;
    }

    @GetMapping("/api/genres")
    public PageResponseDto<Genre> getGenresPage(PageRequestDto requestDto) {
        Page<Genre> genres = genreService.getGenres(requestDto.toPageRequest());
        return mapper.getPageResponseDto(genres);
    }

    @GetMapping("/api/genres/all")
    public List<Genre> getAllGenres() {
        return genreService.getAllGenres();
    }
}
