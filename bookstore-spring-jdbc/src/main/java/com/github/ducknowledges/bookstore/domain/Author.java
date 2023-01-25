package com.github.ducknowledges.bookstore.domain;

import java.util.Objects;

public class Author {

    private final Integer id;
    private final String name;

    public Author(String name) {
        this.id = null;
        this.name = Objects.requireNonNull(name);
    }

    public Author(int id, String name) {
        this.id = id;
        this.name = Objects.requireNonNull(name);
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Author author = (Author) o;
        return name.equals(author.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
