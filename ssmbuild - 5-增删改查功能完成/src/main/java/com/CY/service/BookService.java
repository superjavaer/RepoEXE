package com.CY.service;

import com.CY.pojo.Books;

import java.util.List;

public interface BookService {
    //增加一本书
    int addBook(Books books);

    //删除
    int deleteBookById(int id);

    //修改
    int updateBook(Books books);

    //查询
    Books queryBookById(int id);

    //查询全部
    List<Books> queryAllBook();

    //条件查询
    List<Books> queryBooksByName(String bookName);
}
