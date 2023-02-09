package com.github.ducknowledges.bookstore.dao;

import com.github.ducknowledges.bookstore.domain.BookComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BookCommentDao extends PagingAndSortingRepository<BookComment, Long> {

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, attributePaths = {"book"})
    Page<BookComment> findAllByBookId(long bookId, Pageable pageable);

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, attributePaths = {"book"})
    Page<BookComment> findAll(Pageable pageable);

}
