package PriorIT.sb_books_mvc.dto;

import java.util.ArrayList;
import java.util.List;

public class BookListDTO {

	private List<BookDTO> books;

	
	public BookListDTO() {
		super();
		this.books = new ArrayList<>();
	}


	public List<BookDTO> getBooks() {
		return books;
	}

	public void setBooks(List<BookDTO> books) {
		this.books = books;
	}
	
	public void addToBooks(BookDTO bookDTO)
	{
		this.books.add(bookDTO);
	}


	@Override
	public String toString() {
		return "BookListDTO [books=" + books + "]";
	}
	
	
	
}
