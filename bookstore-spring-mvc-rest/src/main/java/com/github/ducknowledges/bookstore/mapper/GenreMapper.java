package com.github.ducknowledges.bookstore.mapper;

import com.github.ducknowledges.bookstore.domain.Genre;
import com.github.ducknowledges.bookstore.dto.PageResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class GenreMapper implements PageResponseMapper<Genre, Genre> {

    @Override
    public PageResponseDto<Genre> getPageResponseDto(Page<Genre> genrePage) {
        return new PageResponseDto<>(genrePage);
    }

}
