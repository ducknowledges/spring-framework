package com.github.ducknowledges.bookstore.rest;

import com.github.ducknowledges.bookstore.domain.Author;
import com.github.ducknowledges.bookstore.dto.PageRequestDto;
import com.github.ducknowledges.bookstore.dto.PageResponseDto;
import com.github.ducknowledges.bookstore.mapper.PageResponseMapper;
import com.github.ducknowledges.bookstore.service.AuthorService;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthorController {

    private final AuthorService authorService;
    private final PageResponseMapper<Author, Author> mapper;

    public AuthorController(AuthorService authorService,
                            PageResponseMapper<Author, Author> mapper) {
        this.authorService = authorService;
        this.mapper = mapper;
    }

    @GetMapping("/api/authors")
    public PageResponseDto<Author> getGenresPage(PageRequestDto requestDto) {
        Page<Author> authors = authorService.getAuthors(requestDto.toPageRequest());
        return mapper.getPageResponseDto(authors);
    }

    @GetMapping("/api/authors/all")
    public List<Author> getAllGenres() {
        return authorService.getAllAuthors();
    }
}
