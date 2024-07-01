package PriorIT.sb_books_mvc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import PriorIT.sb_books_mvc.db.Database;
import PriorIT.sb_books_mvc.dto.BookDTO;
import PriorIT.sb_books_mvc.dto.BookListDTO;
import PriorIT.sb_books_mvc.dto.ResultDTO;
import PriorIT.sb_books_mvc.model.Book;
import PriorIT.sb_books_mvc.model.OpenLib;
import PriorIT.sb_books_mvc.model.OpenLibData;


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
			
			Double compensation = compensation(currentBook.getPages(),currentBook.getPublishYear(),currentBook.getLanguage());
			
			BookDTO currentBookDTO = new BookDTO(currentBook.getIsbn(),
												 currentBook.getTitle(),
												 currentBook.getPublishYear(),
												 currentBook.getPages(),
												 currentBook.getLanguage(),
												 currentBook.getGenre(),
												 compensation
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
		ResultDTO resultDTO = null;
		
		try {
		
			if((isEmpty(isbn) == false) && (isEmpty(title) == false))
			{
				if(isbnValidator(isbn) == true)
				{
					
					RestTemplate rt = new RestTemplate();
					OpenLib openLib = rt.getForObject("https://openlibrary.org/search.json?isbn=" + isbn, OpenLib.class);
					
					if(openLib.getDocs().size() > 0)
					{
						OpenLibData openLibDataList = openLib.getDocs().get(0);
						Integer OpenLibFirstPublishYear = openLibDataList.getFirst_publish_year();
						Integer OpenLibNumberOfPagesMedian = openLibDataList.getNumber_of_pages_median();
						String OpenLibPublisher = openLibDataList.getPublisher().get(0);
						String OpenLibLanguage = openLibDataList.getLanguage().get(0);
					}
					
					Book book = new Book(	isbn,
											title,
											publishYear,
											pages,
											language,
											genre
											);
					
					result = true;
					message = "The book has been successfully registered!";
					
					
					System.out.println(compensation(pages, publishYear, language));
					
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
			
			if(bookListResultDTO.isAllBooksResult() == true)
			{
				BookListDTO bookListDTO = bookListResultDTO.getBookListDTO();
				
				resultDTO = new ResultDTO(bookListDTO,result,true,message);
			}
			else
			{
				resultDTO = new ResultDTO(null,result,false,bookListResultDTO.getMessage());
			}
		}
		catch(org.hibernate.exception.ConstraintViolationException e)
		{
			message ="ISBN number is alredy registered!";
			
			resultDTO = new ResultDTO(null,false,false,message);
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
	
	private boolean isEmpty(String input)
	{
		boolean result = false;
		if(input.length() == 0)
		{
			result = true;
		}
		
		return result;
	}
	
	private double factor(int pages)
	{
		
		double factor = 0;

		if ((pages > 0) && (pages <50))
		{
			factor = 0.7;
		}
		else if((pages > 49) && (pages < 100))
		{
			factor = 1;
		}
		else if((pages > 99) && (pages < 200))
		{
			factor = 1.1;
		}
		else if((pages > 199) && (pages < 300))
		{
			factor = 1.2;
		}
		else if((pages > 299) && (pages < 500))
		{
			factor = 1.3;
		}
		else
		{
			factor = 1.5;
		}

		return factor;
	}
	
	private double compensation(Integer pages, Integer publishYear, String language)
	{
		
		double basicAmount = 100;
		double compensation = 0;
		double supplement = 0;
		
		if (publishYear != null && (isEmpty(language) == false) && (pages != null))
		{
			if (publishYear < 1990)
			{
				supplement+= 15;	
			}
			
			if(language.equals("de"))
			{
				supplement+= ((basicAmount * 1.1) - basicAmount);
				
			}
			
			compensation = (basicAmount*factor(pages)) + supplement;
		}
		
		return compensation;
	}
	
	
}
