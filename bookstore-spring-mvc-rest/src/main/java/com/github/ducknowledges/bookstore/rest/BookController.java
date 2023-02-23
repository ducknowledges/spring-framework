package com.github.ducknowledges.bookstore.rest;


import com.github.ducknowledges.bookstore.domain.Book;
import com.github.ducknowledges.bookstore.dto.BookDto;
import com.github.ducknowledges.bookstore.dto.PageRequestDto;
import com.github.ducknowledges.bookstore.dto.PageResponseDto;
import com.github.ducknowledges.bookstore.mapper.DomainMapper;
import com.github.ducknowledges.bookstore.rest.error.ElementNotFoundException;
import com.github.ducknowledges.bookstore.service.BookService;
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
    private final DomainMapper<Book, BookDto> mapper;

    public BookController(BookService bookService, DomainMapper<Book, BookDto> mapper) {
        this.bookService = bookService;
        this.mapper = mapper;
    }

    @PostMapping("/api/book")
    public BookDto createBook(@RequestBody @Valid BookDto bookDto) {
        Book book = bookService.create(mapper.toDomain(bookDto));
        return mapper.toDto(book);
    }

    @GetMapping("/api/book/{id}")
    public BookDto getBook(@PathVariable("id") long id) {
        return bookService.getBook(id).map(mapper::toDto)
            .orElseThrow(() -> new ElementNotFoundException(id));
    }

    @GetMapping("/api/books")
    public PageResponseDto<BookDto> getBooks(PageRequestDto requestDto) {
        Page<Book> books = bookService.getBooks(requestDto.toPageRequest());
        return mapper.getPageResponseDto(books);
    }

    @PutMapping("/api/book/{id}")
    public BookDto updateBook(@PathVariable("id") long id, @RequestBody @Valid BookDto bookDto) {
        return bookService.getBook(id).map(b -> {
            bookDto.setId(b.getId());
            Book updatedBook = bookService.save(mapper.toDomain(bookDto));
            return mapper.toDto(updatedBook);
        }).orElseThrow(() -> new ElementNotFoundException(id));
    }

    @DeleteMapping("/api/book/{id}")
    public void deleteBook(@PathVariable("id") long id) {
        bookService.deleteById(id);
    }

}
