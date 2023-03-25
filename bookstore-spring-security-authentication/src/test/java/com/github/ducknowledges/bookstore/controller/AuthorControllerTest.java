package com.github.ducknowledges.bookstore.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.github.ducknowledges.bookstore.domain.Author;
import com.github.ducknowledges.bookstore.service.AuthorService;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@WebMvcTest(AuthorController.class)
class AuthorControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AuthorService authorService;

    @Test
    @WithMockUser
    @DisplayName("should correct handle get /author request")
    void shouldCorrectHandleGenresPageGetRequest() throws Exception {
        int page = 1;
        int size = 5;
        List<Author> authors = List.of(new Author(1L, "author1"), new Author(2L, "author2"));
        Page<Author> authorPage = new PageImpl<>(authors);
        when(authorService.getAuthors(0, size)).thenReturn(authorPage);

        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("page", Integer.toString(page));
        parameters.add("size", Integer.toString(size));
        MockHttpServletRequestBuilder request = get("/authors").params(parameters);

        mvc.perform(request).andExpect(status().isOk())
            .andExpect(view().name("pages/authors"))
            .andExpect(model().attribute("authors", authors))
            .andExpect(model().attribute("totalPages", authorPage.getTotalPages()))
            .andExpect(model().attribute("currentPage", page));
    }
}
