package com.itheima.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.domain.Book;
import com.itheima.mapper.BookMapper;
import com.itheima.service.BookService;

import entity.PageResult;

@Service
@Transactional
public class BookServiceImpl implements BookService{

	//注入BookMapper对象
	@Autowired
	private BookMapper bookMapper;
	
	/**
	 * 根据当前页码和每页需要显示的数据条数，查询最新上架的图书信息
	 */
	@Override
	public PageResult selectNewBooks(Integer pageNum, Integer pageSize) {
		//设置分页查询的参数，开始分页
		PageHelper.startPage(pageNum, pageSize);
		System.out.println(pageNum + pageSize);
		Page<Book> page = bookMapper.selectNewBooks();
		
		System.out.println(page);
		return new PageResult(page.getTotal(), page.getResult());
	}

	/**
	 * 根据id查询图书信息
	 */
	@Override
	public Book findById(String id) {
		return bookMapper.findById(id);
	}

	/**
	 * 借阅图书
	 */
	@Override
	public Integer borrowBook(Book book) {
		//根据id查询出需要借阅的完整图书信息
		Book b = this.findById(book.getId() + "");
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		//设置当天为借阅时间
		book.setBorrowTime(dateFormat.format(new Date()));
		//设置所借阅的图书状态为借阅中
		book.setStatus("1");
		//将图书的价格设置在book对象中
		book.setPrice(b.getPrice());
		//将图书的上架设置在book对象中
		book.setUploadTime(b.getBorrowTime());
		return bookMapper.editBook(book);
		
	}

}
