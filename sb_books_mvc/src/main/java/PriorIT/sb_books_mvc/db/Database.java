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

	public List<Book> getBookList(Integer publishYear) {
		List<Book> bookList = null;
		
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		
		if (publishYear == null)
		{
			SelectionQuery<Book> query = session.createSelectionQuery("SELECT b FROM Book b",Book.class);
			bookList = query.getResultList();
		}
		else
		{
			SelectionQuery<Book> query = session.createSelectionQuery("SELECT b FROM Book b WHERE b.publishYear=?1",Book.class);
			query.setParameter(1, publishYear);
			bookList = query.getResultList();
		}
		
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
	 
	public List<Integer> getPublishYears()
	{
		List<Integer> publishYears = null;
		
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		
		SelectionQuery<Integer> query = session.createSelectionQuery("SELECT b.publishYear FROM Book b WHERE b.publishYear IS NOT NULL GROUP BY b.publishYear ORDER BY b.publishYear ASC",Integer.class);
		publishYears = query.getResultList();
		
		tx.commit();
		session.close();
		
		return publishYears;
	}
	
	

}
