package com.github.ducknowledges.bookstore.dao;

import com.github.ducknowledges.bookstore.domain.Author;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface AuthorDao extends CrudRepository<Author, Long> {
    List<Author> findAllByIdGreaterThanEqualAndIdLessThanEqual(Long from, Long to);
}
