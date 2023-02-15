package com.github.ducknowledges.bookstore.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.github.ducknowledges.bookstore.domain.Author;
import com.github.ducknowledges.bookstore.domain.Book;
import com.github.ducknowledges.bookstore.domain.BookComment;
import com.github.ducknowledges.bookstore.domain.Genre;
import com.github.ducknowledges.bookstore.dto.BookDto;
import com.github.ducknowledges.bookstore.service.AuthorService;
import com.github.ducknowledges.bookstore.service.BookService;
import com.github.ducknowledges.bookstore.service.GenreService;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private GenreService genreService;

    @MockBean
    private BookService bookService;

    @Test
    @DisplayName("should correct handle get /books request")
    void shouldCorrectHandleBooksPageGetRequest() throws Exception {
        int page = 1;
        int size = 5;
        Author author = new Author(1L, "author");
        Genre genre = new Genre(1L, "genre");
        List<Book> books = List.of(
            new Book(1L, "book1", author, genre),
            new Book(2L, "book2", author, genre)
        );
        Page<Book> bookPage = new PageImpl<>(books);
        when(bookService.getBooks(0, size)).thenReturn(bookPage);

        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("page", Integer.toString(page));
        parameters.add("size", Integer.toString(size));
        MockHttpServletRequestBuilder request = get("/books").params(parameters);

        mvc.perform(request).andExpect(status().isOk())
            .andExpect(view().name("pages/books"))
            .andExpect(model().attribute("books", bookPage.getContent()))
            .andExpect(model().attribute("totalPages", bookPage.getTotalPages()))
            .andExpect(model().attribute("currentPage", page));
    }

    @Test
    @DisplayName("should correct handle get /book/create request")
    void shouldCorrectHandleBookCreatePageGetRequest() throws Exception {
        when(authorService.getAllAuthors()).thenReturn(List.of());
        when(genreService.getAllGenres()).thenReturn(List.of());
        mvc.perform(get("/book/create")).andExpect(status().isOk())
            .andExpect(model().attribute("book", new BookDto()))
            .andExpect(model().attribute("authors", List.of()))
            .andExpect(model().attribute("genres", List.of()))
            .andExpect(view().name("pages/book/create"));
    }

    @Test
    @DisplayName("should correct handle post /book/create request")
    void shouldCorrectHandleCreateBook() throws Exception {
        BookDto bookDto = new BookDto("name", 1L, 1L);
        MockHttpServletRequestBuilder request = post("/book/create")
            .param("name", "name")
            .param("authorId", "1")
            .param("genreId", "1");

        mvc.perform(request)
            .andExpect(status().is(302))
            .andExpect(view().name("redirect:/books"));

        verify(bookService).create(bookDto.toDomainObject());
    }

    @Test
    @DisplayName("should correct handle post /book/create request with error")
    void shouldCorrectHandleCreateBookWithErrors() throws Exception {
        BookDto bookDto = new BookDto("", 1L, 1L);
        when(authorService.getAllAuthors()).thenReturn(List.of());
        when(genreService.getAllGenres()).thenReturn(List.of());
        MockHttpServletRequestBuilder request = post("/book/create")
            .param("name", "")
            .param("authorId", "1")
            .param("genreId", "1");

        mvc.perform(request)
            .andExpect(status().isOk())
            .andExpect(model().attribute("book", bookDto))
            .andExpect(model().attribute("authors", List.of()))
            .andExpect(model().attribute("genres", List.of()))
            .andExpect(view().name("pages/book/create"));
    }

    @Test
    @DisplayName("should correct handle get /book/edit request")
    void shouldCorrectHandleBookEditPageGetRequest() throws Exception {
        Author author = new Author(1L, "name");
        Genre genre = new Genre(1L, "name");
        Book book = new Book(1L, "name", author, genre);
        BookDto bookDto = BookDto.toDto(book);
        when(bookService.getBook(1L)).thenReturn(Optional.of(book));
        when(authorService.getAllAuthors()).thenReturn(List.of());
        when(genreService.getAllGenres()).thenReturn(List.of());

        mvc.perform(get("/book/edit/1"))
            .andExpect(status().isOk())
            .andExpect(model().attribute("book", bookDto))
            .andExpect(model().attribute("authors", List.of()))
            .andExpect(model().attribute("genres", List.of()))
            .andExpect(view().name("pages/book/edit"));
    }

    @Test
    @DisplayName("should throw exception on get /book/edit request with non exist book")
    void shouldCorrectHandleBookEditPageWithNonExistBook() {
        when(bookService.getBook(1L)).thenReturn(Optional.empty());
        try {
            mvc.perform(get("/book/edit/1"));
        } catch (Exception e) {
            assertThat(e.getCause()).isInstanceOf(NoSuchElementException.class);
        }
    }

    @Test
    @DisplayName("should correct handle post /book/edit request")
    void shouldCorrectHandleSaveBook() throws Exception {
        BookDto bookDto = new BookDto(1L, "name", 1L, 1L);
        MockHttpServletRequestBuilder request = post("/book/edit")
            .param("id", "1")
            .param("name", "name")
            .param("authorId", "1")
            .param("genreId", "1");

        mvc.perform(request)
            .andExpect(status().is(302))
            .andExpect(view().name("redirect:/books"));

        verify(bookService).save(bookDto.toDomainObject());
    }

    @Test
    @DisplayName("should correct handle post /book/edit request with error")
    void shouldCorrectHandleSaveBookWithErrors() throws Exception {
        BookDto bookDto = new BookDto("", 1L, 1L);
        when(authorService.getAllAuthors()).thenReturn(List.of());
        when(genreService.getAllGenres()).thenReturn(List.of());
        MockHttpServletRequestBuilder request = post("/book/edit")
            .param("name", "")
            .param("authorId", "1")
            .param("genreId", "1");

        mvc.perform(request)
            .andExpect(status().isOk())
            .andExpect(model().attribute("book", bookDto))
            .andExpect(model().attribute("authors", List.of()))
            .andExpect(model().attribute("genres", List.of()))
            .andExpect(view().name("pages/book/edit"));
    }

    @Test
    @DisplayName("should correct handle get /book/delete request ")
    void shouldCorrectHandleDeleteBook() throws Exception {
        mvc.perform(get("/book/delete/1"))
            .andExpect(status().is(302))
            .andExpect(view().name("redirect:/books"));
        verify(bookService).deleteById(1L);
    }
}