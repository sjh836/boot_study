package kr.or.connect.persistence;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import kr.or.connect.domain.Book;

public class BookLauncher {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		BookDao dao = context.getBean(BookDao.class);
		
		int count=dao.countBooks();
		System.out.println(count);
		
		Book book = dao.selectById(1);
		System.out.println(book);
		
		Book book2 = new Book();
		book2.setId(3);
		book2.setTitle("테스트 java22");
		int updateId = dao.update(book2);
		System.out.println(updateId);
		System.out.println(dao.selectById(updateId));
		
		dao.deleteById(3);
		
		context.close();
	}

}
