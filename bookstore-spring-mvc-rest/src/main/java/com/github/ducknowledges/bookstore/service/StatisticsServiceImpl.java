package com.github.ducknowledges.bookstore.service;

import com.github.ducknowledges.bookstore.dto.CounterStatisticsDto;
import org.springframework.stereotype.Service;

@Service
public class StatisticsServiceImpl implements StatisticsService {
    private final BookService bookService;
    private final GenreService genreService;
    private final AuthorService authorService;

    public StatisticsServiceImpl(BookService bookService,
                                 GenreService genreService,
                                 AuthorService authorService) {
        this.bookService = bookService;
        this.genreService = genreService;
        this.authorService = authorService;
    }

    @Override
    public CounterStatisticsDto getCounterStatistics() {
        long bookCounter = bookService.count();
        long authorCounter = authorService.count();
        long genreCounter = genreService.count();
        return new CounterStatisticsDto(bookCounter, authorCounter, genreCounter);
    }
}
