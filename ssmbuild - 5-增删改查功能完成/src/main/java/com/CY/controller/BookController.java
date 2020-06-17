package com.CY.controller;

import com.CY.pojo.Books;
import com.CY.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/book")
public class BookController {
    //controller����service��
    @Autowired
    @Qualifier("bookServiceImpl")
    private BookService bookService;

    //��ѯ���е��鼮���ҷ��ص��鼮չʾҳ��
    @RequestMapping("/allBook")
    public String list(Model model){
        List<Books> booksList = bookService.queryAllBook();
        model.addAttribute("list",booksList);
        return "allBook";
    }

    //��ת�������鼮ҳ��
    @RequestMapping("/toAddBook")
    public String toAddPaper(){
        return "addBook";
    }

    //����鼮
    @RequestMapping("/addBook")
    public String addBook(Books books){
        bookService.addBook(books);
        return "redirect:/book/allBook"; //�ض���@RequestMapping("allBook")����;
    }

    //��ת���޸�ҳ��
    @RequestMapping("/toUpdate")
    public String toUpdatePaper(int id,Model model){
        Books books = bookService.queryBookById(id);
       model.addAttribute("querybooks", books);
        return "updateBook";
    }

    //�޸��鼮
    @RequestMapping("/update")
    public String Update(Books books){
        bookService.updateBook(books);
        return "redirect:/book/allBook";
    }

    //ɾ�������鼮
    @RequestMapping("deleteById")
    public String deleteById(int id){
        bookService.deleteBookById(id);
        return "redirect:/book/allBook";
    }

    //��ѯ�鼮
    @RequestMapping("queryBook")
    public String queryBook(String queryBookName, Model model){
        List<Books> booksList = bookService.queryBooksByName(queryBookName);
        if (booksList==null){
            booksList=bookService.queryAllBook();
            model.addAttribute("bookNotFound","δ��ѯ�������Ϣ");
        }
        model.addAttribute("list",booksList);
        return "allBook";
    }



}