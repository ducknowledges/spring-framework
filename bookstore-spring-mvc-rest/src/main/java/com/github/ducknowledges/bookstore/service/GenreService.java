package com.github.ducknowledges.bookstore.service;

import com.github.ducknowledges.bookstore.domain.Genre;
import com.github.ducknowledges.bookstore.dto.PageResponseDto;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GenreService {

    Optional<Genre> getGenre(long id);

    Page<Genre> getGenres(Pageable pageable);

    public List<Genre> getAllGenres();

    long count();
}
