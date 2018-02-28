package kr.or.connect.bookserver.service;

import java.util.Collection;

import org.springframework.stereotype.Service;

import kr.or.connect.domain.Book;
import kr.or.connect.persistence.BookDao;

@Service
public class BookService {
	private BookDao dao;

	public BookService(BookDao dao) {
		this.dao = dao;
	}
	
	public Book findById(Integer id) {
		//return new Book(1, "Java 이렇게 공부하자", "김자바", 300);
		return dao.selectById(id);
	}

	public Collection<Book> findAll() {
		/*
		return Arrays.asList(
			new Book(1, "네이버 네비 좋아요", "김광근", 300),
			new Book(2, "HTTP 완벽 이해하기", "김명호", 300)
		);
		*/
		return dao.selectAll();
	}
	
	public Book create(Book book) {
		Integer id = dao.insert(book);
		book.setId(id);		
		return book;
	}
	
	public boolean update(Book book) {
		int affected = dao.update(book);
		return affected == 1;
	}
	
	public boolean delete(Integer id) {
		int affected = dao.deleteById(id); 
		return affected == 1;
	}
}
