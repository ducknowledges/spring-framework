package com.github.ducknowledges.bookstore.dao;

import com.github.ducknowledges.bookstore.dao.crud.DeleteDao;
import com.github.ducknowledges.bookstore.dao.crud.ReadDao;
import com.github.ducknowledges.bookstore.dao.crud.SaveDao;
import com.github.ducknowledges.bookstore.domain.Book;

public interface BookDao extends SaveDao<Book>, ReadDao<Book>, DeleteDao {
}
