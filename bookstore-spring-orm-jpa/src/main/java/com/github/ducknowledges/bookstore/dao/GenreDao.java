package com.github.ducknowledges.bookstore.dao;

import com.github.ducknowledges.bookstore.domain.Genre;
import java.util.List;
import java.util.Optional;

public interface GenreDao {
    Optional<Genre> readById(long id);

    List<Genre> readAll(long fromId, long toId);
}
