package PriorIT.sb_books_mvc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import PriorIT.sb_books_mvc.db.Database;
import PriorIT.sb_books_mvc.dto.BookDTO;
import PriorIT.sb_books_mvc.dto.BookListDTO;
import PriorIT.sb_books_mvc.dto.ResultDTO;
import PriorIT.sb_books_mvc.model.Book;


@Service
public class AppService {

	private Database db;

	@Autowired
	public AppService(Database db) {
		super();
		this.db = db;
	}

	public ResultDTO getBooks() {
		
		BookListDTO bookListDTO = new BookListDTO() ;
		
		List<Book> bookList = db.getBookList();
		
		for(int index = 0; index < bookList.size(); index++)
		{
			Book currentBook = bookList.get(index);
			BookDTO currentBookDTO = new BookDTO(currentBook.getIsbn(),
												 currentBook.getTitle(),
												 currentBook.getPublishYear(),
												 currentBook.getPages(),
												 currentBook.getLanguage(),
												 currentBook.getGenre()
												 );
			
			bookListDTO.addToBooks(currentBookDTO);
		}
		
		ResultDTO resultDTO = new ResultDTO(bookListDTO,true,true,null);
		
		return resultDTO;
	}

	public ResultDTO registerABook(String isbn,
								 String title,
								 Integer publishYear, 
								 Integer pages, 
								 String language, 
								 String genre
								 ) 
	{
		boolean result = false;
		String message = "";
		
		if((isEmpty(isbn) == false) && (isEmpty(title) == false))
		{
			if(isbnValidator(isbn) == true)
			{
				Book book = new Book(	isbn,
										title,
										publishYear,
										pages,
										language,
										genre
										);
				
				result = true;
				message = null;
				
				db.registerABook(book);
			}
			else
			{
				message = "Invalid ISBN number!";
			}
		}
		else
		{
			message = "* marked fields must be filled! ";
		}
		
		
		ResultDTO bookListResultDTO = getBooks();
		ResultDTO resultDTO = null;
		
		if(bookListResultDTO.isAllBooksResult() == true)
		{
			BookListDTO bookListDTO = bookListResultDTO.getBookListDTO();
			
			resultDTO = new ResultDTO(bookListDTO,result,true,message);
		}
		else
		{
			resultDTO = new ResultDTO(null,result,false,bookListResultDTO.getMessage());
		}
		
		return resultDTO;
	}
	
	
	private boolean isbnValidator(String isbn)
	{
		boolean result = false;
		
		if(isbn.length() == 10 || isbn.length() == 13)
		{	
			try {
				Long.parseLong(isbn);
				result = true;
			}
			catch (NumberFormatException e){
				result = false;
			}
		}
		return result;
	}
	
	public boolean isEmpty(String input)
	{
		boolean result = false;
		if(input.length() == 0)
		{
			result = true;
		}
		
		return result;
	}
		
}
