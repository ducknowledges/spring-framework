package com.github.ducknowledges.bookstore.rest.error;

public class ElementNotFoundException extends RuntimeException {
    private final Long id;
    private final String domainName;

    public ElementNotFoundException(Long id, String domainName) {
        this.id = id;
        this.domainName = domainName;
    }

    public Long getId() {
        return id;
    }

    public String getDomainName() {
        return domainName;
    }
}
