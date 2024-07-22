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

	public ResultDTO getBooks(Integer publishYear, String language) {
		
		BookListDTO bookListDTO = new BookListDTO() ;
		
		List<Book> bookList = db.getBookList(publishYear,language);
		
		for(int index = 0; index < bookList.size(); index++)
		{
			Book currentBook = bookList.get(index);
			
			Double compensation = compensation(	currentBook.getPages(),
												currentBook.getPublishYear(),
												currentBook.getLanguage()
												);
			
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
		
		ResultDTO resultDTO = new ResultDTO(bookListDTO,
											true,
											true,
											null
											);
		
		resultDTO.setPublishYears(db.getPublishYears());
		resultDTO.setLanguages(db.getLanguages());
		
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
			/*Check ISBN and title length.*/
			if((isEmpty(isbn) == false) && (isEmpty(title) == false))
			{
				/*Check if the ISBN number is valid.*/
				if(isbnValidator(isbn) == true)
				{
					/*Get date from openlibrary.org if:
					 * - publish year is null or  
					 * - pages is null or
					 * - the language is empty. */
					if( (publishYear == null) || 
						(pages == null) || 
						(isEmpty(language) == true)
						)
					{
						RestTemplate rt = new RestTemplate();
						OpenLib openLib = rt.getForObject("https://openlibrary.org/search.json?isbn=" + isbn, OpenLib.class);
						
						Integer openLibFirstPublishYear = null;
						Integer openLibNumberOfPagesMedian = null;
						
						/** I encountered a contradiction at the beginning and the end of the task, this is why i am not using line 103. */
//						String openLibPublisher = "";
						String openLibLanguage = "";
						
						if(openLib.getDocs().size() > 0)
						{
							OpenLibData openLibDataList = openLib.getDocs().get(0);
							
							openLibFirstPublishYear = openLibDataList.getFirst_publish_year();
							openLibNumberOfPagesMedian = openLibDataList.getNumber_of_pages_median();
							
							/** I encountered a contradiction at the beginning and the end of the task, this is why i am not using line 114. */
//							openLibPublisher = openLibDataList.getPublisher().get(0); 
							openLibLanguage = openLibDataList.getLanguage().get(0);
							
							Book book = new Book(isbn,
												 title,
												 openLibFirstPublishYear,
												 openLibNumberOfPagesMedian,
												 openLibLanguage,
												 genre
												 );
							
							result = true;
							message = "Successfully registered! The data has been updated from openlibrary.org!";
							
							db.registerABook(book);
							
						}
						else {
							Book book = new Book(isbn,
												 title,
												 publishYear,
												 pages,
												 language,
												 genre
												 );	
							
							result = true;
							message = "Successfully registered!";
							
							db.registerABook(book);
						}
			
					}
					else
					{
						Book book = new Book(isbn,
											 title,
											 publishYear,
											 pages,
											 language,
											 genre
											 );
						
						result = true;
						message = "Successfully registered!";
						
						db.registerABook(book);
					}
						
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
			
			
			ResultDTO bookListResultDTO = getBooks(null,null);
			
			if(bookListResultDTO.isAllBooksResult() == true)
			{
				BookListDTO bookListDTO = bookListResultDTO.getBookListDTO();
				
				resultDTO = new ResultDTO(	bookListDTO,
											result,
											true,
											message
											);
			}
			else
			{
				resultDTO = new ResultDTO(	null,
											result,
											false,
											bookListResultDTO.getMessage()
											);
			}
		}
		catch(org.hibernate.exception.ConstraintViolationException e)
		{
			message ="ISBN number is already registered!";
			
			/*Get all books*/
			ResultDTO bookListResultDTO = getBooks(null,null);
			BookListDTO bookListDTO = bookListResultDTO.getBookListDTO();
			
			resultDTO = new ResultDTO(	bookListDTO,
										false,
										true,
										message
										);
		}
		
		resultDTO.setPublishYears(db.getPublishYears());
		resultDTO.setLanguages(db.getLanguages());
		
		return resultDTO;
	}
	
	/** Check if the ISBN number length  is valid. */
	/**
	 * 
	 * @param input
	 * This is the String what we check.
	 * @return
	 * 	false: 	- not 10 or 13 characters long or
	 * 			- we can not make a Long from that String
	 * 	true: 	This is a correct String because:
	 * 			- 10 or 13 characters long and
	 * 			- we can made a Long from that String
	 */
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
	
	/** Check if the String is empty.*/
	/**
	 * 
	 * @param input
	 * 	This is the String what we check.
	 * @return
	 * 	false: String length > 0
	 * 	true: String length == 0 (String is empty)
	 */
	private boolean isEmpty(String input)
	{
		boolean result = false;
		if((input != null) && (input.length() == 0))
		{
			result = true;
		}
		
		return result;
	}
	
	/* Get the factor from page numbers.*/
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
	
	/* Calculating of compensation if we have:
	 * 	- publish year and
	 * 	- language and
	 * 	- number of pages
	 */
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
