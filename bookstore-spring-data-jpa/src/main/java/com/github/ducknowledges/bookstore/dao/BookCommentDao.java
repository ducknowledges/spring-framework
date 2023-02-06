package com.github.ducknowledges.bookstore.dao;

import com.github.ducknowledges.bookstore.domain.BookComment;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;

public interface BookCommentDao extends CrudRepository<BookComment, Long> {

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, attributePaths = {"book"})
    List<BookComment> findAllByIdGreaterThanEqualAndIdLessThanEqual(Long from, Long to);
}
