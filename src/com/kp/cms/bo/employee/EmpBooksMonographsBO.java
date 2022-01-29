package com.kp.cms.bo.employee;

public class EmpBooksMonographsBO {
	
	private int id;
	private String title;
	private String authorName;
	private String language;
	private String placePublication;
	private String isbn;
	private String totalPages;
	private String monthYear;
	private String companyInstitution;
	private Boolean isBookMonographs;
	
	public EmpBooksMonographsBO()
	{}
	
	public EmpBooksMonographsBO(int id) {
		super();
		this.id = id;
	}

	public EmpBooksMonographsBO(String title, String authorName,
			String language, String placePublication, String isbn,
			String totalPages, String monthYear, String companyInstitution,
			Boolean isBookMonographs) {
		super();
		this.title = title;
		this.authorName = authorName;
		this.language = language;
		this.placePublication = placePublication;
		this.isbn = isbn;
		this.totalPages = totalPages;
		this.monthYear = monthYear;
		this.companyInstitution = companyInstitution;
		this.isBookMonographs = isBookMonographs;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getPlacePublication() {
		return placePublication;
	}

	public void setPlacePublication(String placePublication) {
		this.placePublication = placePublication;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(String totalPages) {
		this.totalPages = totalPages;
	}

	public String getMonthYear() {
		return monthYear;
	}

	public void setMonthYear(String monthYear) {
		this.monthYear = monthYear;
	}

	public String getCompanyInstitution() {
		return companyInstitution;
	}

	public void setCompanyInstitution(String companyInstitution) {
		this.companyInstitution = companyInstitution;
	}

	public Boolean getIsBookMonographs() {
		return isBookMonographs;
	}

	public void setIsBookMonographs(Boolean isBookMonographs) {
		this.isBookMonographs = isBookMonographs;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
