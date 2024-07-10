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

	public List<Book> getBookList(Integer publishYear, String language) {
		List<Book> bookList = null;
		
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		
		if (publishYear == null && language == null)
		{
			SelectionQuery<Book> query = session.createSelectionQuery("SELECT b FROM Book b",Book.class);
			bookList = query.getResultList();
		}
		else if(publishYear != null && language == null)
		{
			SelectionQuery<Book> query = session.createSelectionQuery("SELECT b FROM Book b WHERE b.publishYear=?1",Book.class);
			query.setParameter(1, publishYear);
			bookList = query.getResultList();
		}
		else if (publishYear == null && language != null)
		{
			SelectionQuery<Book> query = session.createSelectionQuery("SELECT b FROM Book b WHERE b.language=?1",Book.class);
			query.setParameter(1, language);
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
	
	public List<String> getLanguages()
	{
		List<String> languages = null;
		
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		
		SelectionQuery<String> query = session.createSelectionQuery("SELECT b.language FROM Book b WHERE (b.language IS NOT NULL) AND (LENGTH(b.language) > 0) GROUP BY b.language ORDER BY b.language ASC",String.class);
		languages = query.getResultList();
		
		tx.commit();
		session.close();
		
		return languages;
	}
	

}
