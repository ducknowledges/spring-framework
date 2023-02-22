package com.github.ducknowledges.bookstore.dto;

import java.util.List;
import org.springframework.data.domain.Page;

public class PageResponseDto<T> {
    private final List<T> content;
    private final int currentPage;
    private final int totalPages;
    private final long totalElements;

    public PageResponseDto(Page<T> page) {
        content = page.getContent();
        currentPage = page.getNumber();
        totalPages = page.getTotalPages();
        totalElements = page.getTotalElements();
    }

    public List<T> getContent() {
        return content;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public long getTotalElements() {
        return totalElements;
    }
}
