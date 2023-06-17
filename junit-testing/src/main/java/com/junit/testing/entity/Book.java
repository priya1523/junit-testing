package com.junit.testing.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "book_record")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book 
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "book_id")
	private long bookId;
	
	@Column(name = "book_name")
	private String name;
	
	@Column(name = "book_summary")
	private String summary;
	
	@Column(name = "book_rating")
	private int rating;
}
