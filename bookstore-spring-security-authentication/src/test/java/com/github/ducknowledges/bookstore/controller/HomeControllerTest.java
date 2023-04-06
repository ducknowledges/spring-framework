package com.github.ducknowledges.bookstore.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.github.ducknowledges.bookstore.service.AuthorService;
import com.github.ducknowledges.bookstore.service.BookService;
import com.github.ducknowledges.bookstore.service.GenreService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(HomeController.class)
class HomeControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private GenreService genreService;

    @MockBean
    private BookService bookService;

    @Test
    @WithMockUser
    @DisplayName("should correct handle get / request")
    void shouldCorrectHandleRootPageGetRequest() throws Exception {
        when(authorService.count()).thenReturn(1L);
        when(genreService.count()).thenReturn(1L);
        when(bookService.count()).thenReturn(1L);

        mvc.perform(get("/")).andExpect(status().isOk())
            .andExpect(view().name("index"))
            .andExpect(model().attribute("bookCounter", 1L))
            .andExpect(model().attribute("authorCounter", 1L))
            .andExpect(model().attribute("genreCounter", 1L));
    }

    @Test
    @DisplayName("should return Unauthorized status")
    void shouldReturnUnauthorizedStatus() throws Exception {
        mvc.perform(get("/")).andExpect(status().isUnauthorized());
    }
}
