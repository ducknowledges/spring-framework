package com.github.ducknowledges.bookstore.rest;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.ducknowledges.bookstore.domain.Genre;
import com.github.ducknowledges.bookstore.dto.PageResponseDto;
import com.github.ducknowledges.bookstore.mapper.GenreMapper;
import com.github.ducknowledges.bookstore.service.GenreService;
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

@WebMvcTest({GenreController.class, GenreMapper.class})
class GenreControllerTest {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mvc;

    @MockBean
    private GenreService genreService;

    @Test
    @DisplayName("should correct handle get /genres request")
    void shouldCorrectHandleGenresPageGetRequest() throws Exception {
        int page = 1;
        int size = 5;
        List<Genre> genres = List.of(new Genre(1L, "genre1"), new Genre(2L, "genre2"));
        Page<Genre> genrePage = new PageImpl<>(genres);
        PageResponseDto<Genre> genrePageDto = new PageResponseDto<>(genrePage);
        when(genreService.getGenres(PageRequest.of(page, size))).thenReturn(genrePage);


        MockHttpServletRequestBuilder request = get("/api/genres")
            .param("page", Integer.toString(page))
            .param("size", Integer.toString(size));

        mvc.perform(request).andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(mapper.writeValueAsString(genrePageDto)));
    }

    @Test
    @DisplayName("should correct handle get /authors/all request")
    void shouldCorrectHandleAllGenresGetRequest() throws Exception {
        List<Genre> genres = List.of(new Genre(1L, "genre1"), new Genre(2L, "genre2"));
        when(genreService.getAllGenres()).thenReturn(genres);

        mvc.perform(get("/api/genres/all")).andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(mapper.writeValueAsString(genres)));
    }
}