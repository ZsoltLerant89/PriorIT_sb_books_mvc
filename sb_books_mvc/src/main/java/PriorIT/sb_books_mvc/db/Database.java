package PriorIT.sb_books_mvc.db;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.SelectionQuery;
import org.springframework.stereotype.Repository;

import PriorIT.sb_books_mvc.model.Book;

@Repository
public class Database {
	
	private SessionFactory sessionFactory;

	public Database()
	{
		Configuration cfg = new Configuration();
		cfg.configure();
		this.sessionFactory = cfg.buildSessionFactory();
	}
	
	public void closeDb()
	{
		this.sessionFactory.close();
	}

	public List<Book> getBookList() {
		
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		
		SelectionQuery<Book> query = session.createSelectionQuery("SELECT b FROM Book b",Book.class);
		List<Book> bookList = query.getResultList();
		
		tx.commit();
		session.close();
		
		return bookList;
	}

	public void registerABook(Book book) {
		
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
	
		session.persist(book);
	
		tx.commit();
		session.close();
		
	}
	
	

}
