package com.github.ducknowledges.bookstore.rest;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.ducknowledges.bookstore.domain.Book;
import com.github.ducknowledges.bookstore.domain.BookComment;
import com.github.ducknowledges.bookstore.dto.BookCommentDto;
import com.github.ducknowledges.bookstore.dto.PageResponseDto;
import com.github.ducknowledges.bookstore.mapper.BookCommentMapper;
import com.github.ducknowledges.bookstore.service.BookCommentService;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

@WebMvcTest({BookCommentController.class, BookCommentMapper.class})
class BookCommentControllerTest {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookCommentService commentService;



    @Test
    @DisplayName("should correct handle get /comments request")
    void shouldCorrectHandleCommentsPageByBookId() throws Exception {
        long bookId = 1L;
        int page = 1;
        int size = 5;
        List<BookComment> comments = List.of(
            new BookComment("comment1", new Book()),
            new BookComment("comment1", new Book())
        );
        Page<BookComment> commentPage = new PageImpl<>(comments);
        PageResponseDto<BookCommentDto> commentPageDto = new PageResponseDto<>(
            commentPage.map(BookCommentDto::new)
        );
        when(commentService.getCommentsByBookId(bookId, PageRequest.of(1, 5)))
            .thenReturn(commentPage);

        MockHttpServletRequestBuilder request = get("/api/comments")
            .param("book_id", Long.toString(bookId))
            .param("page", Integer.toString(page))
            .param("size", Integer.toString(size));

        mvc.perform(request).andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(mapper.writeValueAsString(commentPageDto)));
    }
}