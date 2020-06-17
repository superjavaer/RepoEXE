package com.CY.dao;

import com.CY.pojo.Books;
import com.CY.service.BookService;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface BookMapper {

    //增加一本书
    int addBook(Books books);

    //删除
    int deleteBookById(@Param("bookId") int id);

    //修改
    int updateBook(Books books);

    //查询
    Books queryBookById(@Param("bookId") int id);

    //查询全部
    List<Books> queryAllBook();

    //条件查询
    List<Books> queryBooksByName(String bookName);

}
