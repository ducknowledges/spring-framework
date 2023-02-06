package com.github.ducknowledges.bookstore.service;

import com.github.ducknowledges.bookstore.domain.Author;
import java.util.List;
import java.util.Optional;

public interface AuthorService {

    Optional<Author> getAuthor(long id);

    List<Author> getAuthors(long fromId, long toId);
}
