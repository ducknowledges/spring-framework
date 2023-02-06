package com.github.ducknowledges.bookstore.dao;

import com.github.ducknowledges.bookstore.domain.Book;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;

public interface BookDao extends CrudRepository<Book, Long> {

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, attributePaths = {"author", "genre"})
    List<Book> findAllByIdGreaterThanEqualAndIdLessThanEqual(Long from, Long to);
}
