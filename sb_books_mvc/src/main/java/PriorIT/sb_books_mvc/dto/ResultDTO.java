package PriorIT.sb_books_mvc.dto;

public class ResultDTO {

	private BookListDTO bookListDTO;
	private boolean registrationResult;
	private boolean allBooksResult;
	private String message;
	
	
	public ResultDTO(BookListDTO bookListDTO, boolean registrationResult, boolean allBooksResult, String message) {
		super();
		this.bookListDTO = bookListDTO;
		this.registrationResult = registrationResult;
		this.allBooksResult = allBooksResult;
		this.message = message;
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
	
	
	
	
	
	
	
}
