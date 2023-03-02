package com.github.ducknowledges.bookstore.mapper;

import static java.util.Objects.nonNull;

import com.github.ducknowledges.bookstore.domain.Author;
import com.github.ducknowledges.bookstore.domain.Book;
import com.github.ducknowledges.bookstore.domain.Genre;
import com.github.ducknowledges.bookstore.dto.BookDto;
import com.github.ducknowledges.bookstore.dto.PageResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class BookMapper implements Mapper<Book, BookDto> {

    @Override
    public PageResponseDto<BookDto> getPageResponseDto(Page<Book> bookPage) {
        return new PageResponseDto<>(bookPage.map(BookDto::new));
    }

    @Override
    public Book toDomain(BookDto dto) {
        Author author = new Author();
        author.setId(dto.getAuthorId());
        Genre genre = new Genre();
        genre.setId(dto.getGenreId());
        return nonNull(dto.getId())
            ? new Book(dto.getId(), dto.getName(), author, genre)
            : new Book(dto.getName(), author, genre);
    }

    @Override
    public BookDto toDto(Book book) {
        return new BookDto(book);
    }
}
