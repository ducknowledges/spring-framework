package com.github.ducknowledges.bookstore.rest;

import com.github.ducknowledges.bookstore.domain.BookComment;
import com.github.ducknowledges.bookstore.dto.BookCommentDto;
import com.github.ducknowledges.bookstore.dto.PageRequestDto;
import com.github.ducknowledges.bookstore.dto.PageResponseDto;
import com.github.ducknowledges.bookstore.mapper.PageResponseMapper;
import com.github.ducknowledges.bookstore.service.BookCommentService;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookCommentController {

    private final BookCommentService commentService;
    private final PageResponseMapper<BookComment, BookCommentDto> mapper;

    public BookCommentController(BookCommentService commentService,
                                 PageResponseMapper<BookComment, BookCommentDto> mapper) {
        this.commentService = commentService;
        this.mapper = mapper;
    }

    @GetMapping("/api/comments")
    public PageResponseDto<BookCommentDto> getBookComments(
        @RequestParam("book_id") Optional<Long> bookId, PageRequestDto requestDto) {
        Page<BookComment> comments = bookId
            .map(id -> commentService.getCommentsByBookId(id, requestDto.toPageRequest()))
            .orElse(commentService.getComments(requestDto.toPageRequest()));
        return mapper.getPageResponseDto(comments);
    }
}
