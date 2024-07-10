package PriorIT.sb_books_mvc.dto;

import java.util.ArrayList;
import java.util.List;

public class ResultDTO {

	private BookListDTO bookListDTO;
	private boolean registrationResult;
	private boolean allBooksResult;
	private String message;
	private List<Integer> publishYears ;
	private List<String> languages;
	
	public ResultDTO(BookListDTO bookListDTO, boolean registrationResult, boolean allBooksResult, String message) {
		super();
		this.bookListDTO = bookListDTO;
		this.registrationResult = registrationResult;
		this.allBooksResult = allBooksResult;
		this.message = message;
		this.publishYears = new ArrayList<>();
		this.languages = new ArrayList<>();
	}
	
	
	public BookListDTO getBookListDTO() {
		return bookListDTO;
	}
	public void setBookListDTO(BookListDTO bookListDTO) {
		this.bookListDTO = bookListDTO;
	}
	public boolean isRegistrationResult() {
		return registrationResult;
	}
	public void setRegistrationResult(boolean registrationResult) {
		this.registrationResult = registrationResult;
	}
	public boolean isAllBooksResult() {
		return allBooksResult;
	}
	public void setAllBooksResult(boolean allBooksResult) {
		this.allBooksResult = allBooksResult;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public List<Integer> getPublishYears() {
		return publishYears;
	}

	public void setPublishYears(List<Integer> publishYears) {
		this.publishYears = publishYears;
	}

	public List<String> getLanguages() {
		return languages;
	}

	public void setLanguages(List<String> languages) {
		this.languages = languages;
	}
	
	
}
