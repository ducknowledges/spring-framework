package com.github.ducknowledges.bookstore.domain;

import java.util.Objects;

public class Genre {

    private final Long id;
    private final String name;

    public Genre(String name) {
        this.id = null;
        this.name = Objects.requireNonNull(name);
    }

    public Genre(Long id, String name) {
        this.id = id;
        this.name = Objects.requireNonNull(name);
    }

    public Long getId() {
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
        Genre genre = (Genre) o;
        return Objects.equals(name, genre.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
