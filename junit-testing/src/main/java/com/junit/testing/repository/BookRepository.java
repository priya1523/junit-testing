package com.junit.testing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.junit.testing.entity.Book;

@Repository
@EnableJpaRepositories
public interface BookRepository extends JpaRepository<Book, Long>
{

}
