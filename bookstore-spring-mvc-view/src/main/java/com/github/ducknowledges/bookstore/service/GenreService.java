package com.github.ducknowledges.bookstore.service;

import com.github.ducknowledges.bookstore.domain.Genre;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface GenreService {

    Optional<Genre> getGenre(long id);

    Page<Genre> getGenres(int page, int size);

    public List<Genre> getAllGenres();

    long count();
}
