package com.github.ducknowledges.bookstore.rest;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.ducknowledges.bookstore.domain.Author;
import com.github.ducknowledges.bookstore.dto.PageResponseDto;
import com.github.ducknowledges.bookstore.mapper.AuthorMapper;
import com.github.ducknowledges.bookstore.service.AuthorService;
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

@WebMvcTest({AuthorController.class, AuthorMapper.class})
class AuthorControllerTest {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AuthorService authorService;

    @Test
    @DisplayName("should correct handle get /authors request")
    void shouldCorrectHandleAuthorsPageGetRequest() throws Exception {
        int page = 1;
        int size = 5;
        List<Author> authors = List.of(new Author(1L, "author1"), new Author(2L, "author2"));
        Page<Author> authorPage = new PageImpl<>(authors);
        PageResponseDto<Author> authorPageDto = new PageResponseDto<>(authorPage);
        when(authorService.getAuthors(PageRequest.of(page, size))).thenReturn(authorPage);

        MockHttpServletRequestBuilder request = get("/api/authors")
            .param("page", Integer.toString(page))
            .param("size", Integer.toString(size));

        mvc.perform(request).andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(mapper.writeValueAsString(authorPageDto)));
    }

    @Test
    @DisplayName("should correct handle get /authors/all request")
    void shouldCorrectHandleAllAuthorsGetRequest() throws Exception {
        List<Author> authors = List.of(new Author(1L, "author1"), new Author(2L, "author2"));
        when(authorService.getAllAuthors()).thenReturn(authors);

        mvc.perform(get("/api/authors/all")).andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(mapper.writeValueAsString(authors)));
    }
}