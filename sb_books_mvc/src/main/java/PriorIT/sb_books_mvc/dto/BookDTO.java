package PriorIT.sb_books_mvc.dto;

public class BookDTO {

	private String isbn;

	private String title;

	private Integer publishYear;

	private Integer pages;
	
	private String language;
	
	private String genre;

	public BookDTO(String isbn,
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

	
	@Override
	public String toString() {
		return "BookDTO [isbn=" + isbn + ", title=" + title + ", publishYear=" + publishYear + ", pages=" + pages
				+ ", language=" + language + ", genre=" + genre + "]";
	}
	
	
	
}
