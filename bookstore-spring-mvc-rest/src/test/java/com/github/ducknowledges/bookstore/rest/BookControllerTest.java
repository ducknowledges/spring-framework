package com.github.ducknowledges.bookstore.rest;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.ducknowledges.bookstore.domain.Author;
import com.github.ducknowledges.bookstore.domain.Book;
import com.github.ducknowledges.bookstore.domain.Genre;
import com.github.ducknowledges.bookstore.dto.BookDto;
import com.github.ducknowledges.bookstore.dto.PageResponseDto;
import com.github.ducknowledges.bookstore.dto.error.ErrorDto;
import com.github.ducknowledges.bookstore.dto.error.ValidationErrorDto;
import com.github.ducknowledges.bookstore.mapper.BookMapper;
import com.github.ducknowledges.bookstore.service.AuthorService;
import com.github.ducknowledges.bookstore.service.BookService;
import com.github.ducknowledges.bookstore.service.GenreService;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

@WebMvcTest({BookController.class, BookMapper.class})
class BookControllerTest {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private GenreService genreService;

    @MockBean
    private BookService bookService;

    private Book book;
    private BookDto bookDto;
    private Book newBook;
    private BookDto newBookDto;

    @BeforeEach
    void setUp() {
        Author author = new Author();
        author.setId(1L);
        Genre genre = new Genre();
        genre.setId(1L);
        book = new Book(1L, "book1", author, genre);
        bookDto = new BookDto(book);
        newBook = new Book("book1", author, genre);
        newBookDto = new BookDto(newBook);
    }

    @Test
    @DisplayName("should correct handle post /book request")
    void shouldCreateBook() throws Exception {
        MockHttpServletRequestBuilder request = post("/api/book")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(newBookDto));
        when(authorService.isExistById(newBookDto.getAuthorId())).thenReturn(true);
        when(genreService.isExistById(newBookDto.getGenreId())).thenReturn(true);
        when(bookService.create(newBook)).thenReturn(book);

        mvc.perform(request).andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(mapper.writeValueAsString(bookDto)));
    }

    @Test
    @DisplayName("should correct handle post /book request if author not exist")
    void shouldGetErrorIfAuthorNotExist() throws Exception {
        newBookDto.setAuthorId(123L);
        MockHttpServletRequestBuilder request = post("/api/book")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(newBookDto));
        when(authorService.isExistById(newBookDto.getAuthorId())).thenReturn(false);
        ErrorDto dto = new ErrorDto(HttpStatus.NOT_FOUND, "Author with id = 123 was not found");

        mvc.perform(request).andExpect(status().isNotFound())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(mapper.writeValueAsString(dto)));
    }

    @Test
    @DisplayName("should correct handle post /book request if genre not exist")
    void shouldGetErrorIfGenreNotExist() throws Exception {
        newBookDto.setGenreId(123L);
        MockHttpServletRequestBuilder request = post("/api/book")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(newBookDto));
        when(authorService.isExistById(newBookDto.getAuthorId())).thenReturn(true);
        when(genreService.isExistById(newBookDto.getGenreId())).thenReturn(false);
        ErrorDto dto = new ErrorDto(HttpStatus.NOT_FOUND, "Genre with id = 123 was not found");

        mvc.perform(request).andExpect(status().isNotFound())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(mapper.writeValueAsString(dto)));
    }

    @Test
    @DisplayName("should correct handle post /book request if not valid book field")
    void shouldGetValidationError() throws Exception {
        newBookDto.setName("");
        MockHttpServletRequestBuilder request = post("/api/book")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(newBookDto));
        ValidationErrorDto errorDto = new ValidationErrorDto();
        errorDto.setFieldError("name", "Should has length 2-255");

        mvc.perform(request).andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(mapper.writeValueAsString(errorDto)));
    }

    @Test
    @DisplayName("should correct handle get /book/{id} request")
    void shouldGetBook() throws Exception {
        when(bookService.getBook(book.getId())).thenReturn(Optional.of(book));

        mvc.perform(get("/api/book/1")).andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(mapper.writeValueAsString(bookDto)));
    }

    @Test
    @DisplayName("should correct handle get /book/{id} request if id not exist")
    void shouldGetErrorResponse() throws Exception {
        when(authorService.isExistById(newBookDto.getAuthorId())).thenReturn(true);
        when(genreService.isExistById(newBookDto.getGenreId())).thenReturn(true);
        when(bookService.getBook(book.getId())).thenReturn(Optional.empty());
        ErrorDto errorDto = new ErrorDto(
            HttpStatus.NOT_FOUND,
            String.format("Book with id = %d was not found", book.getId())
        );
        mvc.perform(get("/api/book/1")).andExpect(status().isNotFound())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(mapper.writeValueAsString(errorDto)));
    }

    @Test
    @DisplayName("should correct handle get /authors request")
    void shouldCorrectHandleAuthorsPageGetRequest() throws Exception {
        int page = 1;
        int size = 5;
        Book book1 = new Book(1L, "book1",
            new Author(1L, "author1"), new Genre(1L, "genre1"));
        Book book2 = new Book(2L, "book2",
            new Author(1L, "author1"), new Genre(1L, "genre1"));
        List<Book> books = List.of(book1, book2);
        Page<Book> booksPage = new PageImpl<>(books);
        when(bookService.getBooks(PageRequest.of(page, size))).thenReturn(booksPage);

        MockHttpServletRequestBuilder request = get("/api/books")
            .param("page", Integer.toString(page))
            .param("size", Integer.toString(size));

        PageResponseDto<BookDto> bookPageDto = new PageResponseDto<>(booksPage.map(BookDto::new));
        mvc.perform(request).andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(mapper.writeValueAsString(bookPageDto)));
    }

    @Test
    @DisplayName("should correct handle put /book/{id} request")
    void shouldUpdateBook() throws Exception {
        MockHttpServletRequestBuilder request = put("/api/book/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(bookDto));
        when(authorService.isExistById(newBookDto.getAuthorId())).thenReturn(true);
        when(genreService.isExistById(newBookDto.getGenreId())).thenReturn(true);
        when(bookService.getBook(bookDto.getId())).thenReturn(Optional.of(book));
        when(bookService.save(book)).thenReturn(book);

        mvc.perform(request).andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(mapper.writeValueAsString(bookDto)));
    }

    @Test
    @DisplayName("should correct handle delete /book/{id} request")
    void shouldCorrectHandleDeleteBook() throws Exception {
        mvc.perform(delete("/api/book/1"))
            .andExpect(status().isOk());

        verify(bookService).deleteById(1L);
    }
}