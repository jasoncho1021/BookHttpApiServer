package kr.or.connect.persistence;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource; // DriverManagerDataSource BasicDataSource 의 공통 인터페이스

//BookDao의 코드만 본다면 컴파일 시점이 아닌 실행 시점에 의존성 주입됨. DI기법, 
//datasource 얻는 다른 라이브러릴 써도 BookDao 변경 안 해도 된다. 
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import kr.or.connect.domain.Book;

@Repository
public class BookDao {

	private NamedParameterJdbcTemplate jdbc;
	private static final String COUNT_BOOK = "SELECT COUNT(*) FROM book";
	private static final String SELECT_BY_ID = "SELECT id, title, author, page FROM book where id = :id";
	private static final String DELETE_BY_ID = "DELETE FROM book WHERE id = :id";
	private static final String UPDATE = "UPDATE book SET\n"
										+ "title = :title,"
										+ "author = :author,"
										+ "page = :page\n"
										+ "WHERE id = :id";
	private static final String SELECT_ALL = "SELECT id, title, author, page FROM book";
	
	private RowMapper<Book> rowMapper = BeanPropertyRowMapper.newInstance(Book.class); // 멀티스레드 접근에도 안전하기 때문에 멤버변수로 선언하고 초기화해도 된다.
	private SimpleJdbcInsert insertAction;
	
	//public BookDao(DriverManagerDataSource dataSource) {
	public BookDao(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
		
		this.insertAction = new SimpleJdbcInsert(dataSource)
				.withTableName("book")
				.usingGeneratedKeyColumns("id");
	}
	
	public int countBooks() {
		Map<String, Object> params = Collections.emptyMap();
		return jdbc.queryForObject(COUNT_BOOK, params, Integer.class);
	}
	
	public Book selectById(Integer id) {
		
		/*
		RowMapper<Book> rowMapper = (rs, i) -> {
			Book book = new Book();
			book.setId(rs.getInt("id"));
			book.setTitle(rs.getString("title"));
			book.setAuthor(rs.getString("author"));
			book.setPage((Integer)rs.getObject("page"));
			return book;
		};
		*/
						
		Map<String, Object> params = new HashMap<>();
		params.put("id", id);
		return jdbc.queryForObject(SELECT_BY_ID, params, rowMapper);
	}
	
	public Integer insert(Book book) {
		SqlParameterSource params = new BeanPropertySqlParameterSource(book);
		return insertAction.executeAndReturnKey(params).intValue();
	}
	
	public int deleteById(Integer id) {
		Map<String, ?> params = Collections.singletonMap("id", id);
		return jdbc.update(DELETE_BY_ID, params);
	}
	
	public int update(Book book) {
		SqlParameterSource params = new BeanPropertySqlParameterSource(book);
		return jdbc.update(UPDATE, params);
	}
	
	public List<Book> selectAll() {
		Map<String, Object> params = Collections.emptyMap();
		return jdbc.query(SELECT_ALL, params, rowMapper);
	}
	
	
}
