package com.github.ducknowledges.bookstore.dao;

import com.github.ducknowledges.bookstore.dao.crud.CreateDao;
import com.github.ducknowledges.bookstore.dao.crud.DeleteDao;
import com.github.ducknowledges.bookstore.dao.crud.ReadDao;
import com.github.ducknowledges.bookstore.dao.crud.UpdateDao;
import com.github.ducknowledges.bookstore.domain.Book;

public interface BookDao extends CreateDao<Book>,
    ReadDao<Book>, UpdateDao<Book>, DeleteDao {
    int count();
}
