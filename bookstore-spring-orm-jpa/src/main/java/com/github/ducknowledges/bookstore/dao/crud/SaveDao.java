package com.github.ducknowledges.bookstore.dao.crud;

public interface SaveDao<T> {
    T save(T type);
}
