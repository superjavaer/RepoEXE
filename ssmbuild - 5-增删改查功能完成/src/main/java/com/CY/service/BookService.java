package com.CY.service;

import com.CY.pojo.Books;

import java.util.List;

public interface BookService {
    //����һ����
    int addBook(Books books);

    //ɾ��
    int deleteBookById(int id);

    //�޸�
    int updateBook(Books books);

    //��ѯ
    Books queryBookById(int id);

    //��ѯȫ��
    List<Books> queryAllBook();

    //������ѯ
    List<Books> queryBooksByName(String bookName);
}
