package com.github.ducknowledges.bookstore.dao;

import com.github.ducknowledges.bookstore.domain.Author;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AuthorDao extends PagingAndSortingRepository<Author, Long> {
}
