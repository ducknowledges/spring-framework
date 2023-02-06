package com.github.ducknowledges.bookstore.dao;

import com.github.ducknowledges.bookstore.domain.Author;
import java.util.List;
import java.util.Optional;

public interface AuthorDao {
    Optional<Author> readById(long id);

    List<Author> readAll(long fromId, long toId);
}
