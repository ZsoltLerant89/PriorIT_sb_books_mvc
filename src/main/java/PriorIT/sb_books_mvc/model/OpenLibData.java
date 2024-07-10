package PriorIT.sb_books_mvc.model;

import java.util.List;

public class OpenLibData {
	
	private Integer first_publish_year;
	private Integer number_of_pages_median;
	private List<String> publisher;
	private List<String> language;
	
	
	public Integer getFirst_publish_year() {
		return first_publish_year;
	}
	public void setFirst_publish_year(Integer first_publish_year) {
		this.first_publish_year = first_publish_year;
	}
	public Integer getNumber_of_pages_median() {
		return number_of_pages_median;
	}
	public void setNumber_of_pages_median(Integer number_of_pages_median) {
		this.number_of_pages_median = number_of_pages_median;
	}
	public List<String> getPublisher() {
		return publisher;
	}
	public void setPublisher(List<String> publisher) {
		this.publisher = publisher;
	}
	public List<String> getLanguage() {
		return language;
	}
	public void setLanguage(List<String> language) {
		this.language = language;
	}
	

}
