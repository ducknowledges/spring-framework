package com.github.ducknowledges.bookstore.service;

import com.github.ducknowledges.bookstore.domain.Author;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AuthorService {

    boolean isExistById(Long authorId);

    Optional<Author> getAuthor(long id);

    Page<Author> getAuthors(Pageable pageable);

    List<Author> getAllAuthors();

    Long count();
}
