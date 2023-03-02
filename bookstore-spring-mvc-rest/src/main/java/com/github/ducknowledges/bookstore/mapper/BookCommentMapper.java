package com.github.ducknowledges.bookstore.mapper;

import com.github.ducknowledges.bookstore.domain.BookComment;
import com.github.ducknowledges.bookstore.dto.BookCommentDto;
import com.github.ducknowledges.bookstore.dto.PageResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class BookCommentMapper implements PageResponseMapper<BookComment, BookCommentDto> {

    @Override
    public PageResponseDto<BookCommentDto> getPageResponseDto(Page<BookComment> commentPage) {
        return new PageResponseDto<>(commentPage.map(BookCommentDto::new));
    }

}
