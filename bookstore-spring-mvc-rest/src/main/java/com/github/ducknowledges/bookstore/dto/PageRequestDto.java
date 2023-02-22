package com.github.ducknowledges.bookstore.dto;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class PageRequestDto {
    private int page;
    private int size;

    public PageRequestDto() {
        this.page = 0;
        this.size = 5;
    }

    public Pageable toPageRequest() {
        return PageRequest.of(page, size);
    }

    public void setPage(int page) {
        this.page = Math.max(page, 0);
    }

    public void setSize(int size) {
        this.size = Math.min(size, 25);
    }
}
