package kr.or.connect.persistence;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import kr.or.connect.BookServerApplication;
import kr.or.connect.domain.Book;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookServerApplication.class)
@Transactional // 해당 클래스가 트랜잭션 안에서 실행되는 것을 의미,, 디폴트 정책은 메서드 실행이 끝나는 시점에 트랜잭션 롤백한다.
public class BookDaoTest {
		
	@Autowired
	private BookDao dao;
	
	@Test
	public void shouldCount() {
		int count = dao.countBooks();
		System.out.println(count);
	}
	
	@Test
	public void shouldInsertAndSelect() {
		//given
		Book book = new Book("zero", "brain", 77);
		
		//when
		Integer id = dao.insert(book);
		
		//then
		Book selected = dao.selectById(id);
		System.out.println(selected);
		assertThat(selected.getTitle(), is("Java 웹개발"));
	}
	
	@Test
	public void shouldDelete() {
		//given
		Book book = new Book("네이버 자바", "네이버", 142);
		Integer id = dao.insert(book);
		
		//when
		int affected = dao.deleteById(id);

		//Then
		assertThat(affected, is(1));
	}
	
	@Test
	public void shouldUpdate() {
		//given
		Book book = new Book("네버다이", "네이버", 142);
		Integer id = dao.insert(book);
		
		//when
		book.setId(id);
		book.setTitle("다이다이");
		int affected = dao.update(book);
		
		//then
		assertThat(affected, is(1));
		Book updated = dao.selectById(id);
		assertThat(updated.getTitle(), is("다이소"));
	}
	
	@Test
	public void shouldSelectAll() {
		List<Book> allBooks = dao.selectAll();
		assertThat(allBooks, is(notNullValue()));
	}
}
