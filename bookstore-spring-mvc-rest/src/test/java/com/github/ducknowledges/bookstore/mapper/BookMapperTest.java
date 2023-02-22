package com.github.ducknowledges.bookstore.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.github.ducknowledges.bookstore.domain.Author;
import com.github.ducknowledges.bookstore.domain.Book;
import com.github.ducknowledges.bookstore.domain.Genre;
import com.github.ducknowledges.bookstore.dto.BookDto;
import com.github.ducknowledges.bookstore.dto.PageResponseDto;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

@DisplayName("Class BookMapper")
class BookMapperTest {

    @Test
    @DisplayName("should convert book comment page to book comment dto request page")
    void shouldConvertBookPageToBookPageDto() {
        Author author = mock(Author.class);
        when(author.getId()).thenReturn(1L);
        Genre genre = mock(Genre.class);
        when(genre.getId()).thenReturn(1L);
        List<Book> content = List.of(
            new Book(1L, "message", author, genre),
            new Book(2L, "message", author, genre));
        Page<Book> pageBook = new PageImpl<>(content);

        PageResponseDto<BookDto> expected = new PageResponseDto<>(pageBook.map(BookDto::new));
        PageResponseDto<BookDto> actual = new BookMapper().getPageResponseDto(pageBook);

        assertAll(
            () -> assertThat(actual.getContent()).isEqualTo(expected.getContent()),
            () -> assertThat(actual.getTotalPages()).isEqualTo(expected.getTotalPages()),
            () -> assertThat(actual.getTotalElements()).isEqualTo(expected.getTotalElements()),
            () -> assertThat(actual.getCurrentPage()).isEqualTo(expected.getCurrentPage())
        );
    }

    @Test
    @DisplayName("should convert dto to domain")
    void shouldConvertDtoToDomain() {
        Author author = new Author();
        author.setId(1L);
        Genre genre = new Genre();
        genre.setId(1L);
        Book expectedBook = new Book(1L, "book1", author, genre);
        BookDto dto = new BookDto(expectedBook);
        Book actualBook = new BookMapper().toDomain(dto);
        assertThat(actualBook).isEqualTo(expectedBook);
    }

    @Test
    @DisplayName("should convert domain to dto")
    void shouldConvertDomainToDto() {
        Author author = new Author(1L, "author");
        Genre genre = new Genre(1L, "genre");
        Book book = new Book(1L, "book1", author, genre);
        BookDto expectedDto = new BookDto(book);
        BookDto actualDto = new BookMapper().toDto(book);
        assertThat(actualDto).isEqualTo(expectedDto);
    }

}