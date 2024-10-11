package PriorIT.sb_books_mvc.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import PriorIT.sb_books_mvc.dto.ResultDTO;
import PriorIT.sb_books_mvc.service.AppService;

@WebMvcTest(AppController.class)
public class AppControllerUnitTest {
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private AppService service;
	
	
	@Test
	public void testLoadIndex() throws Exception
	{
		ResultDTO resultDTO = new ResultDTO(null,false,false,null);
		BDDMockito.given(service.getBooks(null,null)).willReturn(resultDTO);
		
		mvc.perform(MockMvcRequestBuilders.get("/"))
			.andExpect(MockMvcResultMatchers.view().name("index.html"))
			.andExpect(MockMvcResultMatchers.model().attribute("resultDTO", resultDTO))
			.andExpect(MockMvcResultMatchers.model().attribute("resultDTO", Matchers.hasProperty("registrationResult", Matchers.is(false))))
			.andExpect(MockMvcResultMatchers.model().attribute("resultDTO", Matchers.hasProperty("allBooksResult", Matchers.is(false))))
			.andExpect(MockMvcResultMatchers.model().attribute("resultDTO", Matchers.hasProperty("bookListDTO", Matchers.nullValue())))
			.andExpect(MockMvcResultMatchers.model().attribute("resultDTO", Matchers.hasProperty("message", Matchers.nullValue())))
			;
	}
	
	@Test
	public void testRegister() throws Exception
	{
		/*Input*/
		String isbn = "5123456648945";
		String title = "Test";
		Integer publishYear = 2024;
		Integer pages = 300;
		String language = "hu"
;		String genre = "horror";
		
		/*Mocking*/
		ResultDTO resultDTO = new ResultDTO(null,true,false,"test");
		BDDMockito.given(service.registerABook(isbn,title,publishYear,pages,language,genre)).willReturn(resultDTO);
		
		/*Test*/
		mvc.perform(MockMvcRequestBuilders.post("/books/register")
				.param("isbn", isbn)
				.param("title", title)
				.param("publishyear", publishYear.toString())
				.param("pages", pages.toString())
				.param("language", language)
				.param("genre", genre)
				)
			/*Expected output*/
			.andExpect(MockMvcResultMatchers.view().name("index.html"))
			.andExpect(MockMvcResultMatchers.model().attribute("resultDTO", resultDTO));
	}
	
	

	
}
