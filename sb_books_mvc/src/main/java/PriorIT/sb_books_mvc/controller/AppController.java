package PriorIT.sb_books_mvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import PriorIT.sb_books_mvc.dto.ResultDTO;
import PriorIT.sb_books_mvc.service.AppService;


@Controller
public class AppController {

	private AppService service;

	@Autowired
	public AppController(AppService service) {
		super();
		this.service = service;
	}
	
	@GetMapping("/index")
	private String loadIndex(Model model)
	{
		ResultDTO resultDTO = service.getBooks();
		model.addAttribute("resultDTO", resultDTO );
		
		return "index.html";
	}
	
	@GetMapping("/index/register")
	private String register(
							Model model,
							@RequestParam(name = "isbn",required = true ) String isbn,
							@RequestParam(name = "title", required = true) String title,
							@RequestParam(name = "publishyear", required = false) Integer publishYear,
							@RequestParam(name = "pages", required = false) Integer pages,
							@RequestParam(name = "language", required = false) String language,
							@RequestParam(name = "genre", required = false) String genre
							)
	{
		
		ResultDTO resultDTO = service.registerABook(isbn,
												title,
												publishYear,
												pages,
												language,
												genre
												);
		
		model.addAttribute("resultDTO", resultDTO );
		
		return "index.html";
	}
}
