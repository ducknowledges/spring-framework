package com.github.ducknowledges.bookstore.service;

import com.github.ducknowledges.bookstore.dao.GenreDao;
import com.github.ducknowledges.bookstore.domain.Author;
import com.github.ducknowledges.bookstore.domain.Genre;
import com.github.ducknowledges.bookstore.dto.PageResponseDto;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class GenreServiceImpl implements GenreService {
    private final GenreDao genreDao;

    public GenreServiceImpl(GenreDao genreDao) {
        this.genreDao = genreDao;
    }

    @Override
    public Optional<Genre> getGenre(long id) {
        return genreDao.findById(id);
    }

    @Override
    public Page<Genre> getGenres(Pageable pageable) {
        return genreDao.findAll(pageable);
    }

    @Override
    public List<Genre> getAllGenres() {
        return (List<Genre>) genreDao.findAll();
    }

    @Override
    public long count() {
        return genreDao.count();
    }
}
