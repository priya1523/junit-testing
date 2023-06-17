package com.junit.testing.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.junit.testing.entity.Book;
import com.junit.testing.repository.BookRepository;
import com.junit.testing.service.BookService;

@Service
public class BookServiceImple implements BookService
{
	@Autowired
	private BookRepository bookRepository;

	//get all the book records
	@Override
	public List<Book> getAllBookRecords() 
	{
		return this.bookRepository.findAll();
	}

	//get the book record based on bookId
	@Override
	public Book getBookById(long bookId)
	{
		Optional<Book> book=this.bookRepository.findById(bookId);
		return book.get();
	}

	//add the book record
	@Override
	public Book createBookRecord(Book book) 
	{
		return this.bookRepository.save(book);
	}

	//update the book record
	@Override
	public Book updateBookRecord(Book book) throws Exception 
	{
		if(book==null)
		{
			throw new Exception("Book Should not be null !!");
		}
		Optional<Book> optionBook=this.bookRepository.findById(book.getBookId());
		if(optionBook.isPresent())
		{
			return this.bookRepository.save(book);
		}
		else
		{
			throw new Exception("Book is not there !!");
		}
	}

	//delete the book by bookId
	@Override
	public String deleteBookById(long bookId) 
	{
		Optional<Book> book=this.bookRepository.findById(bookId);
		if(book.isPresent())
		{
			this.bookRepository.deleteById(bookId);
			return "Deleted";
		}
		
		return "Not Deleted";
		
	}
	
}
