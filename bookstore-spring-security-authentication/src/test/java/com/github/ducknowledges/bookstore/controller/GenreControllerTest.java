package com.github.ducknowledges.bookstore.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.github.ducknowledges.bookstore.domain.Genre;
import com.github.ducknowledges.bookstore.service.GenreService;
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

@WebMvcTest(GenreController.class)
class GenreControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private GenreService genreService;


    @Test
    @WithMockUser
    @DisplayName("should correct handle get /genres request")
    void shouldCorrectHandleGenresPageGetRequest() throws Exception {
        int page = 1;
        int size = 5;
        List<Genre> genres = List.of(new Genre(1L, "genre1"), new Genre(2L, "genre2"));
        Page<Genre> genrePage = new PageImpl<>(genres);
        when(genreService.getGenres(0, size)).thenReturn(genrePage);

        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("page", Integer.toString(page));
        parameters.add("size", Integer.toString(size));
        MockHttpServletRequestBuilder request = get("/genres").params(parameters);

        mvc.perform(request).andExpect(status().isOk())
            .andExpect(view().name("pages/genres"))
            .andExpect(model().attribute("genres", genres))
            .andExpect(model().attribute("totalPages", genrePage.getTotalPages()))
            .andExpect(model().attribute("currentPage", page));
    }

    @Test
    @DisplayName("should return Unauthorized status")
    void shouldRedirectWhenUnauthorized() throws Exception {
        MockHttpServletRequestBuilder request = get("/genres");
        mvc.perform(request).andExpect(status().isUnauthorized());
    }
}

//    @Autowired
//    private MockMvc mvc;
//
//    @MockBean
//    private GenreService genreService;
//
//    @Autowired
//    private WebApplicationContext context;
//
//    @BeforeEach
//    public void setup() {
//        mvc = MockMvcBuilders
//            .webAppContextSetup(context)
//            .apply(springSecurity())
//            .build();
//    }
