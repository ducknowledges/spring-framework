package com.github.ducknowledges.bookstore.dao;

import com.github.ducknowledges.bookstore.domain.Genre;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface GenreDao extends PagingAndSortingRepository<Genre, Long> {
}
