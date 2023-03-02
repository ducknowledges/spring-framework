package com.github.ducknowledges.bookstore.dto;

public class CounterStatisticsDto {

    private final long bookCounter;
    private final long authorCounter;
    private final long genreCounter;


    public CounterStatisticsDto(long bookCounter, long authorCounter, long genreCounter) {
        this.bookCounter = bookCounter;
        this.authorCounter = authorCounter;
        this.genreCounter = genreCounter;
    }

    public long getBookCounter() {
        return bookCounter;
    }

    public long getAuthorCounter() {
        return authorCounter;
    }

    public long getGenreCounter() {
        return genreCounter;
    }
}
