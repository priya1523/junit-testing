package com.junit.testing.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.junit.testing.entity.Book;
import com.junit.testing.service.BookService;

@SpringBootTest(classes = {BookControllerTest.class})
public class BookControllerTest 
{
	//for request api testing
	private MockMvc mockMvc;
	
	//to convert the object into string
	ObjectMapper objectMapper=new ObjectMapper();
	ObjectWriter objectWriter=objectMapper.writer();
	
	@Mock
	private BookService bookService; //which is to mock
	
	@InjectMocks
	private BookController bookController; //where to mock
	
	//dummy data
	Book book_1=new Book(1L,"JAVA","Basics Of Java",5);
	Book book_2=new Book(2L,"PYTHON","Bsics Of Python",4);
	Book book_3=new Book(3L,"ANGULAR","Basics Of Angular",5);
	
	List<Book> books=new ArrayList<>(Arrays.asList(book_1,book_2,book_3));
	
	@SuppressWarnings("deprecation")
	@BeforeEach //execute before every testcase
	public void setUp()
	{
		MockitoAnnotations.initMocks(this);
		this.mockMvc=MockMvcBuilders.standaloneSetup(bookController).build();  //create the mockmvc object
	}
	
	@Test
	public void getAllRecords() throws Exception //test case of get all record
	{
		Mockito.when(this.bookService.getAllBookRecords()).thenReturn(books); //return the fake record
		
		mockMvc.perform(MockMvcRequestBuilders //perform()->which actions has to perform
						.get("/book") //for get http request
						.contentType(MediaType.APPLICATION_JSON)) //which data has to return
				.andExpect(status().isOk()) //first test case
				.andExpect(MockMvcResultMatchers.jsonPath("$.size()",is(3))) //second test case
				.andExpect(jsonPath("$[2].name", is("ANGULAR"))); //third test case
				//we can write multiple expected test cased
	}
	
	@Test
	public void getBookById() throws Exception //test case for get book by id
	{
		Mockito.when(this.bookService.getBookById(book_1.getBookId())).thenReturn(book_1);
		
		mockMvc.perform(MockMvcRequestBuilders
						.get("/book/1")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$",notNullValue()))
				.andExpect(jsonPath("$.rating", is(5)));
	}
	
	@Test
	public void createBookRecord() throws Exception //test case for create the object
	{
		//creating mock object of book to pass while request sending of post
		Book book=Book.builder()
				.bookId(4L)
				.name("Introduction to C")
				.summary("Basics Of C")
				.rating(5)
				.build();
		
		//mock the object while calling the method
		Mockito.when(this.bookService.createBookRecord(book)).thenReturn(book);
		
		//converting the book object to string json format	
		String book_json=objectMapper.writeValueAsString(book);
		
		MockHttpServletRequestBuilder requestBuilder=MockMvcRequestBuilders //return the MockHttpServletRequestBuilder object
													.post("/book/") //for post request
													.contentType(MediaType.APPLICATION_JSON_VALUE) //which format should return
													.accept(MediaType.APPLICATION_JSON_VALUE) //which format should accept
													.content(book_json);//the value which is pass as request body
		
		mockMvc.perform(requestBuilder) //perform method accept the MockHttpServletRequestBuilder object
				.andExpect(status().isOk());
	}
	
	@Test
	public void updateBookRecord() throws Exception //test case for update the book record
	{
		//creating updated mock object of book to pass while request sending of post
		Book book=Book.builder()
					.bookId(1L)
					.name("Introduction to C")
					.summary("Basics Of C")
					.rating(5)
					.build();
		
		Mockito.when(this.bookService.updateBookRecord(book)).thenReturn(book);
		
		String book_json=objectMapper.writeValueAsString(book);
//		String requestBody = "{\"bookId\":1,\"name\":\"Introduction to C\",\"summary\":\"Basics Of C\",\"rating\":5}";

		 
		
		MockHttpServletRequestBuilder requestBuilder=MockMvcRequestBuilders //return the MockHttpServletRequestBuilder object
				.put("/book/") //for post request
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE) //which format should return
				.accept(MediaType.APPLICATION_JSON_UTF8_VALUE) //which format should accept
				.content(book_json);//the value which is pass as request body

		mockMvc.perform(requestBuilder) //perform method accept the MockHttpServletRequestBuilder object
		.andExpect(status().isOk());
	}
	
	@Test
	public void deleteBookById() throws Exception
	{
		Mockito.when(this.bookService.deleteBookById(book_2.getBookId())).thenReturn("Deleted");
		
		mockMvc.perform(MockMvcRequestBuilders
						.delete("/book/2")
						.contentType(MediaType.APPLICATION_JSON))
						.andExpect(status().isOk());
	}
}
