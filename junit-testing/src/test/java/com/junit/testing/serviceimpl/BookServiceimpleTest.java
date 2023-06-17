package com.junit.testing.serviceimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.junit.testing.entity.Book;
import com.junit.testing.repository.BookRepository;

@SpringBootTest(classes = {BookServiceimpleTest.class})
@TestMethodOrder(OrderAnnotation.class)
public class BookServiceimpleTest
{
	@Mock
	private BookRepository bookRepository; //which records should have to mock
	
	@InjectMocks
	private BookServiceImple bookServiceImple; //where to inject the mock records
	
	//fake records
	Book book_1=new Book(1L,"JAVA","Basics Of Java",5);
	Book book_2=new Book(2L,"PYTHON","Bsics Of Python",4);
	Book book_3=new Book(3L,"ANGULAR","Basics Of Angular",5);
	Optional<Book> book_4=Optional.of(new Book(4L,".NET","Basics Of .Net",4));
	
	//list of fake book records
	List<Book> books=new ArrayList<>(Arrays.asList(book_1,book_2,book_3,book_4.get()));
	
	@Test
	@Order(1) //first test case to execute
	public void getAllBooksRecords() //test case for the get all the books records
	{
		Mockito.when(this.bookRepository.findAll()).thenReturn(books); //Getting the fake records
		List<Book> actual=this.bookServiceImple.getAllBookRecords(); //getting the actual output
		assertEquals(books.size(), actual.size()); //test case one
		assertEquals(books.get(0).getName(), actual.get(0).getName()); //test case two
	}
	
	@Test
	@Order(2) //second test case to execute
	public void getBookById()
	{
		Mockito.when(this.bookRepository.findById(book_4.get().getBookId())).thenReturn(book_4);
		Book actual=this.bookServiceImple.getBookById(4);
		assertEquals(book_4.get().getBookId(),actual.getBookId());
		assertEquals(book_4.get().getName(), actual.getName());
		assertEquals(book_4.get().getSummary(), actual.getSummary());
		assertEquals(book_4.get().getRating(), actual.getRating());
	}
	
	@Test
	@Order(3) //third test case to execute
	public void createBookRecord()
	{
		Book book=new Book(5L,"REACT","Basics Of React",5);
		Mockito.when(this.bookRepository.save(book)).thenReturn(book);
		Book actual=this.bookServiceImple.createBookRecord(book);
		assertEquals(book, actual);
		assertEquals(book.getName(), actual.getName());
	}
	
	@Test
	@Order(4) //fourth test case to execute
	public void updateBookRecord() throws Exception
	{
		book_3.setName("Update");
		book_3.setRating(5);
		Mockito.when(this.bookRepository.findById(book_4.get().getBookId())).thenReturn(book_4);
		Mockito.when(this.bookRepository.save(book_4.get())).thenReturn(book_4.get());
		Book actual=this.bookServiceImple.updateBookRecord(book_4.get());
		assertEquals(book_4.get(), actual);
		assertEquals(book_4.get().getSummary(), actual.getSummary());
	}
	
	@Test
	@Order(5)
	public void deleteBookById()
	{
		//here deleteById() method in repository don't return anything
		//so we can't mock the deleteById() method return value
		Mockito.when(this.bookRepository.findById(book_4.get().getBookId())).thenReturn(book_4);
		String actual=this.bookServiceImple.deleteBookById(book_4.get().getBookId());
		assertEquals("Deleted", actual);
		verify(this.bookRepository,times(1)).deleteById(book_4.get().getBookId());
		
	}
	
}
