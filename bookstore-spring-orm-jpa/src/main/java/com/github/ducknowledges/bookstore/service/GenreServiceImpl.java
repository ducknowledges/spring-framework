package com.github.ducknowledges.bookstore.service;

import com.github.ducknowledges.bookstore.dao.GenreDao;
import com.github.ducknowledges.bookstore.domain.Genre;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GenreServiceImpl implements GenreService {
    private final GenreDao genreDao;

    public GenreServiceImpl(GenreDao genreDao) {
        this.genreDao = genreDao;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Genre> getGenre(long id) {
        return genreDao.readById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Genre> getGenres(long fromId, long toId) {
        return genreDao.readAll(fromId, toId);
    }
}
