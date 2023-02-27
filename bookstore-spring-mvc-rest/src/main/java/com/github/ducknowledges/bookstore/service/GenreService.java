package com.github.ducknowledges.bookstore.service;

import com.github.ducknowledges.bookstore.domain.Genre;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GenreService {

    boolean isExistById(Long genreId);

    Optional<Genre> getGenre(long id);

    Page<Genre> getGenres(Pageable pageable);

    List<Genre> getAllGenres();

    long count();
}
