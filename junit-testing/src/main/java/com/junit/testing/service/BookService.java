package com.junit.testing.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.junit.testing.entity.Book;

@Service
public interface BookService
{

	//get all the book records
	List<Book> getAllBookRecords();

	//get the book record based on bookId
	Book getBookById(long bookId);

	//add the book record
	Book createBookRecord(Book book);

	//update the book record
	Book updateBookRecord(Book book) throws Exception;

	//delete the book by bookId
	String deleteBookById(long bookId);

}
