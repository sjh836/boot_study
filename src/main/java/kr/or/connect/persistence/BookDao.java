package kr.or.connect.persistence;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

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
	private SimpleJdbcInsert insertAction;
	
	private static final String SELECT_ALL = "SELECT id, title, author, pages FROM book";
	private static final String COUNT_BOOK = "SELECT COUNT(*) FROM book";
	private static final String SELECT_BY_ID = "SELECT id, title, author, pages FROM book where id = :id";
	private static final String DELETE_BY_ID = "DELETE FROM book WHERE id= :id";
	private static final String UPDATE = "UPDATE book SET title = :title, author = :author, pages = :pages WHERE id = :id";
	
	RowMapper<Book> rowMapper = BeanPropertyRowMapper.newInstance(Book.class); //BeanPropertyRowMapper.newInstance() 메서드를 이용해서 Book 클래스와 대응되는 RowMapper 인스턴스를 생성

	public BookDao(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
		this.insertAction = new SimpleJdbcInsert(dataSource).withTableName("book").usingGeneratedKeyColumns("id");
	}
	
	public List<Book> selectAll() {
		Map<String, Object> params = Collections.emptyMap();
		return jdbc.query(SELECT_ALL, params, rowMapper);
	}
	
	public int countBooks() {
		Map<String, Object> params = Collections.emptyMap();
		return jdbc.queryForObject(COUNT_BOOK, params, Integer.class);
	}
	
	public Book selectById(Integer id) {
		Map<String, Object> params = new HashMap<>();
		params.put("id", id);
		return jdbc.queryForObject(SELECT_BY_ID, params, rowMapper);
	}
	
	public Integer insert(Book book) {
		SqlParameterSource params = new BeanPropertySqlParameterSource(book);
		return insertAction.executeAndReturnKey(params).intValue(); //생성된 키값을 반환
	}
	
	public int deleteById(Integer id) {
		Map<String, ?> params = Collections.singletonMap("id", id);
		return jdbc.update(DELETE_BY_ID, params);
	}
	
	public int update(Book book) {
		SqlParameterSource params = new BeanPropertySqlParameterSource(book);
		return jdbc.update(UPDATE, params);
	}
}
