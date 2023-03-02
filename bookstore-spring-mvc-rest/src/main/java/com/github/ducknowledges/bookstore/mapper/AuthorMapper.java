package com.github.ducknowledges.bookstore.mapper;

import com.github.ducknowledges.bookstore.domain.Author;
import com.github.ducknowledges.bookstore.dto.PageResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapper implements PageResponseMapper<Author, Author> {

    @Override
    public PageResponseDto<Author> getPageResponseDto(Page<Author> authorPage) {
        return new PageResponseDto<>(authorPage);
    }

}
