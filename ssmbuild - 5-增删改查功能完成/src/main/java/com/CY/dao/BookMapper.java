package com.CY.dao;

import com.CY.pojo.Books;
import com.CY.service.BookService;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface BookMapper {

    //����һ����
    int addBook(Books books);

    //ɾ��
    int deleteBookById(@Param("bookId") int id);

    //�޸�
    int updateBook(Books books);

    //��ѯ
    Books queryBookById(@Param("bookId") int id);

    //��ѯȫ��
    List<Books> queryAllBook();

    //������ѯ
    List<Books> queryBooksByName(String bookName);

}
