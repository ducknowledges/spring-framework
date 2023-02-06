package com.github.ducknowledges.bookstore.dao;

import com.github.ducknowledges.bookstore.domain.Genre;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface GenreDao extends CrudRepository<Genre, Long> {
    List<Genre> findAllByIdGreaterThanEqualAndIdLessThanEqual(Long from, Long to);
}
