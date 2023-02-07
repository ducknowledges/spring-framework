package com.github.ducknowledges.bookstore.dao;

import com.github.ducknowledges.bookstore.domain.Author;
import java.util.List;
import java.util.Optional;

public interface AuthorDao {
    Optional<Author> findById(long id);

    List<Author> findAll(long fromId, long toId);
}
