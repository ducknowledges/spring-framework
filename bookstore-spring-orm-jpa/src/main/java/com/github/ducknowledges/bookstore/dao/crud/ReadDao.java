package com.github.ducknowledges.bookstore.dao.crud;

import java.util.List;
import java.util.Optional;

public interface ReadDao<T> {
    Optional<T> readById(long id);

    List<T> readAll(int from, int size);
}
