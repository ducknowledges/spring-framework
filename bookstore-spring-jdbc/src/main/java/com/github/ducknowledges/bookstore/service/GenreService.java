package com.github.ducknowledges.bookstore.service;

import com.github.ducknowledges.bookstore.domain.Genre;
import java.util.List;
import java.util.Optional;

public interface GenreService {

    Optional<Genre> getGenreById(int id);

    List<Genre> getGenres();
}
