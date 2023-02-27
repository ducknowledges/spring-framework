package com.github.ducknowledges.bookstore.rest;

import com.github.ducknowledges.bookstore.domain.Book;
import com.github.ducknowledges.bookstore.dto.BookDto;
import com.github.ducknowledges.bookstore.dto.PageRequestDto;
import com.github.ducknowledges.bookstore.dto.PageResponseDto;
import com.github.ducknowledges.bookstore.mapper.Mapper;
import com.github.ducknowledges.bookstore.rest.error.ElementNotFoundException;
import com.github.ducknowledges.bookstore.service.AuthorService;
import com.github.ducknowledges.bookstore.service.BookService;
import com.github.ducknowledges.bookstore.service.GenreService;
import javax.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final Mapper<Book, BookDto> mapper;

    public BookController(BookService bookService,
                          AuthorService authorService,
                          GenreService genreService,
                          Mapper<Book, BookDto> mapper) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.genreService = genreService;
        this.mapper = mapper;
    }

    @PostMapping("/api/book")
    public BookDto createBook(@RequestBody @Valid BookDto bookDto) {
        checkAuthorExist(bookDto.getAuthorId());
        checkGenreExist(bookDto.getGenreId());
        Book book = bookService.create(mapper.toDomain(bookDto));
        return mapper.toDto(book);
    }

    @GetMapping("/api/book/{id}")
    public BookDto getBook(@PathVariable("id") long id) {
        return bookService.getBook(id).map(mapper::toDto)
            .orElseThrow(() -> new ElementNotFoundException(id, "Book"));
    }

    @GetMapping("/api/books")
    public PageResponseDto<BookDto> getBooks(PageRequestDto requestDto) {
        Page<Book> books = bookService.getBooks(requestDto.toPageRequest());
        return mapper.getPageResponseDto(books);
    }

    @PutMapping("/api/book/{id}")
    public BookDto updateBook(@PathVariable("id") long id, @RequestBody @Valid BookDto bookDto) {
        checkAuthorExist(bookDto.getAuthorId());
        checkGenreExist(bookDto.getGenreId());
        return bookService.getBook(id).map(b -> {
            bookDto.setId(b.getId());
            Book updatedBook = bookService.save(mapper.toDomain(bookDto));
            return mapper.toDto(updatedBook);
        }).orElseThrow(() -> new ElementNotFoundException(id, "Book"));
    }

    @DeleteMapping("/api/book/{id}")
    public void deleteBook(@PathVariable("id") long id) {
        bookService.deleteById(id);
    }

    private void checkAuthorExist(Long authorId) {
        if (!authorService.isExistById(authorId)) {
            throw new ElementNotFoundException(authorId, "Author");
        }
    }

    private void checkGenreExist(Long genreId) {
        if (!genreService.isExistById(genreId)) {
            throw new ElementNotFoundException(genreId, "Genre");
        }
    }

}
