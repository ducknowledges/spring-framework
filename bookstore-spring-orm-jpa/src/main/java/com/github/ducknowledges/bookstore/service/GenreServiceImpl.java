package com.github.ducknowledges.bookstore.service;

import com.github.ducknowledges.bookstore.dao.GenreDao;
import com.github.ducknowledges.bookstore.domain.Genre;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class GenreServiceImpl implements GenreService {
    private final GenreDao genreDao;

    public GenreServiceImpl(GenreDao genreDao) {
        this.genreDao = genreDao;
    }

    @Override
    public Optional<Genre> getGenre(long id) {
        return genreDao.readById(id);
    }

    @Override
    public List<Genre> getGenres() {
        return genreDao.readAll();
    }
}
