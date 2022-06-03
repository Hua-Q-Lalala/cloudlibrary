package com.itheima.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.itheima.domain.Book;
import com.itheima.domain.User;
import com.itheima.service.BookService;

import entity.PageResult;
import entity.Result;

@Controller
@RequestMapping("/book")
public class BookController {
	
	//注入BookService对象
	@Autowired
	private BookService bookService;
	
	/**
	 * 查询最新上架的图书
	 * @return
	 */
	@RequestMapping("/selectNewbooks")
	public ModelAndView selectNewbooks(){
		//查询最新上架的5个图书信息
		int pageNum = 1;
		int pageSize = 5;
		PageResult pageResult = bookService.selectNewBooks(pageNum, pageSize);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("books_new");
		modelAndView.addObject("pageResult", pageResult);
		return modelAndView;
	}
	
	/**
	 * 根据id查询图书信息
	 */
	@RequestMapping("/findById")
	@ResponseBody
	public Result<Book> findById(String id){
		try {
			Book book = bookService.findById(id);
			if(book == null) {
				return new Result(false, "查询图书失败！");
			}
			return new Result(true, "查询图书成功", book);
		}catch(Exception e) {
			e.printStackTrace();
			return new Result(false, "查询图书失败！");
		}
	}
	
	@RequestMapping("/borrowBook")
	@ResponseBody
	public Result borrowBook(Book book, HttpSession session) {
		//获取当前登录的用户姓名
		String pname = ((User)session.getAttribute("USER_SESSION")).getName();
		book.setBorrower(pname);
		try {
			//根据图书的id和用户进行图书借阅
			Integer count = bookService.borrowBook(book);
			if(count != 1) {
				return new Result(false, "借阅图书失败!");
			}
			return new Result(true, "借阅成功，请到行政中心取书！");
		}catch(Exception e) {
			e.printStackTrace();
			return new Result(false, "借阅图书失败!");
		}
	}
	
	/**
	 * 分页查询符合条件且未下架图书信息
	 * @param book
	 * @param pageNum
	 * @param pageSize
	 * @param reqeust
	 * @return
	 */
	@RequestMapping("/search")
	public ModelAndView search(Book book, Integer pageNum, Integer pageSize, 
			HttpServletRequest reqeust){
		if(pageNum == null){
			pageNum = 1;
		}
		if(pageSize == null){
			pageSize = 10;
		}
		System.out.println(book);
		//查询到的图书信息
		PageResult pageResult = bookService.search(book, pageNum, pageSize);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("books");
		//将查询到的数据存放在ModelAndView对象中
		modelAndView.addObject("pageResult", pageResult);
		//将查询的参数返回到页面，用于回显到查询的输入框中
		modelAndView.addObject("search", book);
		//将当前页码返回到页面，用于分页插件的分页显示
		modelAndView.addObject("pageNum", pageNum);
		//将当前查询的控制器路径返回到页面，页码变化时继续向该路径发送请求
		modelAndView.addObject("gourl", reqeust.getRequestURI());
		return modelAndView;
	}
	
	/**
	 * 新增图书
	 * @param book
	 * @return
	 */
	@RequestMapping("/addBook")
	@ResponseBody
	public Result addBook(Book book) {
		try {
			Integer count = bookService.addBook(book);
			if(count != 1) {
				return new Result(false, "新增图书失败！");
			}
			return new Result(true, "新增图书成功！");
		}catch(Exception e) {
			e.printStackTrace();
			return new Result(false, "新增图书失败！");
		}
	}
	
	/**
	 * 编辑图书
	 * @param book
	 * @return
	 */
	@RequestMapping("/editBook")
	@ResponseBody
	public Result editBook(Book book) {
		try {
			Integer count = bookService.editBook(book);
			if(count != 1) {
				return new Result(false, "编辑失败！"); 
			}
			return new Result(true, "编辑成功！"); 
		}catch(Exception e) {
			e.printStackTrace();
			return new Result(false, "编辑失败！"); 
		}
	}
}
