package com.CY.service.Impl;

import com.CY.dao.BookMapper;
import com.CY.pojo.Books;
import com.CY.service.BookService;

import java.util.List;

public class BookServiceImpl implements BookService {
    /*service调DAO层*/
    private BookMapper bookMapper;
    /*添加set方法*/
    public void setBookMapper(BookMapper bookMapper) {
        this.bookMapper = bookMapper;
    }

    public int addBook(Books books) {
        return bookMapper.addBook(books);
    }

    public int deleteBookById(int id) {
        return bookMapper.deleteBookById(id);
    }

    public int updateBook(Books books) {
        return bookMapper.updateBook(books);
    }

    public Books queryBookById(int id) {
        return bookMapper.queryBookById(id);
    }

    public List<Books> queryAllBook() {
        return bookMapper.queryAllBook();
    }

    public List<Books> queryBooksByName(String bookName) {
        return bookMapper.queryBooksByName(bookName);
    }
}
