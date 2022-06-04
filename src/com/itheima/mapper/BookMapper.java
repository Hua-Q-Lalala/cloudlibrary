package com.itheima.mapper;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import com.github.pagehelper.Page;
import com.itheima.domain.Book;

/**
 * 图书接口
 * @author hua
 *
 */
public interface BookMapper {
	
	@Select("SELECT * FROM book where book_status != '3' order by book_uploadtime DESC")
	@ResultMap("bookMap")
	/**
	 * 新书推荐
	 * @return
	 */
	Page<Book> selectNewBooks();
	
	@Select("SELECT * from Book where book_id = #{id}")
	@ResultMap("bookMap")
	//根据id查询图书信息
	Book findById(String id);
	
	//编辑图书信息
	Integer editBook(Book book);
	
	@Select({"<script>" + 
			"SELECT * FROM book " + 
			"where book_status != '3'" +
			"<if test=\"name != null\"> AND book_name like CONCAT('%', #{name}, '%') </if>" + 
			"<if test=\"press != null\"> AND book_press like CONCAT('%', #{press}, '%') </if>" +
			"<if test=\"author != null\"> AND book_author like CONCAT('%', #{author}, '%') </if>" +
			"order by book_status" + 
			"</script>"	
	})
	@ResultMap("bookMap")
	//查询图书
	Page<Book> searchBooks(Book book);
	
	//新增图书
	Integer addBook(Book book);
	
	//查询借阅但未归还的图书和所有待确认归还的图书
	Page<Book> selectBorrowed(Book book);
	
	//查询借阅但未归还的图书
	Page<Book> selectMyBorrowed(Book book);
	
}
