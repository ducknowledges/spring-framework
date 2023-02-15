package com.github.ducknowledges.bookstore.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.github.ducknowledges.bookstore.domain.Book;
import com.github.ducknowledges.bookstore.domain.BookComment;
import com.github.ducknowledges.bookstore.service.BookCommentService;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@WebMvcTest(BookCommentController.class)
class BookCommentControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookCommentService commentService;

    @Test
    @DisplayName("should correct handle get /comments request")
    void shouldCorrectHandleCommentsPageGetRequest() throws Exception {
        int page = 1;
        int size = 5;
        List<BookComment> comments = List.of(
            new BookComment("comment1", new Book()),
            new BookComment("comment1", new Book())
        );
        Page<BookComment> commentPage = new PageImpl<>(comments);
        when(commentService.getComments(0, size)).thenReturn(commentPage);

        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("page", Integer.toString(page));
        parameters.add("size", Integer.toString(size));
        MockHttpServletRequestBuilder request = get("/comments").params(parameters);

        mvc.perform(request).andExpect(status().isOk())
            .andExpect(view().name("pages/comments"))
            .andExpect(model().attribute("comments", comments))
            .andExpect(model().attribute("totalPages", commentPage.getTotalPages()))
            .andExpect(model().attribute("currentPage", page));
    }
}