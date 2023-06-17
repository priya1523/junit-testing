package com.junit.testing.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.junit.testing.entity.Book;
import com.junit.testing.service.BookService;

@RestController
@RequestMapping(value = "/book")
public class BookController 
{
	@Autowired
	private BookService bookService;
	
	// get all the book records
	@GetMapping
	public List<Book> getAllBookRecords()
	{
		return this.bookService.getAllBookRecords();
	}
	
	//get the book record based on bookId
	@GetMapping(value = "{bookId}")
	public Book getBookById(@PathVariable(name = "bookId") long bookId)
	{
		return this.bookService.getBookById(bookId);
	}
	
	//add the book record
	@PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	public Book createBookRecord(@RequestBody Book book)
	{
		return this.bookService.createBookRecord(book);
	}
	
	//update the book record
	@PutMapping(value = "/",produces = MediaType.APPLICATION_JSON_UTF8_VALUE,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Book updateBookRecord(@RequestBody  Book book) throws Exception
	{
		return this.bookService.updateBookRecord(book);
	}
	
	//delete the book record
	@DeleteMapping(value = "{bookId}")
	public String deleteBookById(@PathVariable(name = "bookId") long bookId)
	{
		return this.bookService.deleteBookById(bookId);
	}
	
	
}
