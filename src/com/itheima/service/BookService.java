package com.itheima.service;

import com.itheima.domain.Book;

import entity.PageResult;

/**
 * 图书接口
 * @author hua
 *
 */
public interface BookService {
	//查询最新上架的图书
	PageResult selectNewBooks(Integer pageNum, Integer pageSize);
	
	//根据id查询图书信息
	Book findById(String id);
	
	//借阅图书
	Integer borrowBook(Book book);
	
	//分页查询图书
	PageResult search(Book book, Integer pageNum, Integer pageSize);
	
	//新增图书
	Integer addBook(Book book);
	
	//编辑图书信息
	Integer editBook(Book book);
}
