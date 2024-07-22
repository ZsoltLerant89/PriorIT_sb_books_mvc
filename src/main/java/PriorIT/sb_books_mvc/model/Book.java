package PriorIT.sb_books_mvc.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="books")
public class Book {

	@Id
	@Column(name="isbn")
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String isbn;
	
	@Column(name="title")
	private String title;
	
	@Column(name="publish_year")
	private Integer publishYear;
	
	@Column(name="pages")
	private Integer pages;
	
	@Column(name="language")
	private String language;
	
	@Column(name="genre")
	private String genre;

	
	public Book() {
		super();
	}


	public Book(String isbn,
				String title,
				Integer publishYear, 
				Integer pages, 
				String language, 
				String genre
				) 
	{
		super();
		this.isbn = isbn;
		this.title = title;
		this.publishYear = publishYear;
		this.pages = pages;
		this.language = language;
		this.genre = genre;
	}


	public String getIsbn() {
		return isbn;
	}


	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public Integer getPublishYear() {
		return publishYear;
	}


	public void setPublishYear(Integer publishYear) {
		this.publishYear = publishYear;
	}


	public Integer getPages() {
		return pages;
	}


	public void setPages(Integer pages) {
		this.pages = pages;
	}


	public String getLanguage() {
		return language;
	}


	public void setLanguage(String language) {
		this.language = language;
	}


	public String getGenre() {
		return genre;
	}


	public void setGenre(String genre) {
		this.genre = genre;
	}


	

}
