package com.github.ducknowledges.bookstore.rest.error;

public class ElementNotFoundException extends RuntimeException {
    private final Long id;

    public ElementNotFoundException(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
