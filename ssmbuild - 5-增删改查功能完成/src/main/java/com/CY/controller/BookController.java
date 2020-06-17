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
    //controller调用service层
    @Autowired
    @Qualifier("bookServiceImpl")
    private BookService bookService;

    //查询所有的书籍并且返回到书籍展示页面
    @RequestMapping("/allBook")
    public String list(Model model){
        List<Books> booksList = bookService.queryAllBook();
        model.addAttribute("list",booksList);
        return "allBook";
    }

    //跳转到增加书籍页面
    @RequestMapping("/toAddBook")
    public String toAddPaper(){
        return "addBook";
    }

    //添加书籍
    @RequestMapping("/addBook")
    public String addBook(Books books){
        bookService.addBook(books);
        return "redirect:/book/allBook"; //重定向到@RequestMapping("allBook")请求;
    }

    //跳转到修改页面
    @RequestMapping("/toUpdate")
    public String toUpdatePaper(int id,Model model){
        Books books = bookService.queryBookById(id);
       model.addAttribute("querybooks", books);
        return "updateBook";
    }

    //修改书籍
    @RequestMapping("/update")
    public String Update(Books books){
        bookService.updateBook(books);
        return "redirect:/book/allBook";
    }

    //删除单列书籍
    @RequestMapping("deleteById")
    public String deleteById(int id){
        bookService.deleteBookById(id);
        return "redirect:/book/allBook";
    }

    //查询书籍
    @RequestMapping("queryBook")
    public String queryBook(String queryBookName, Model model){
        List<Books> booksList = bookService.queryBooksByName(queryBookName);
        if (booksList==null){
            booksList=bookService.queryAllBook();
            model.addAttribute("bookNotFound","未查询到相关信息");
        }
        model.addAttribute("list",booksList);
        return "allBook";
    }



}