package com.kp.cms.bo.employee;

public class EmpChapterArticlBookBO {
	private int id;
	
	private String title;
	private String editorsName;
	private String titleChapterArticle;
	private String language;
	private String placePublication;
	private String isbn;
	private String totalPages;
	private String authorName;
	private String monthYear;
	private String companyInstitution;
	private String pagesFrom;
	private String pagesTo;
	private String doi;
	private Boolean isChapterArticleBook;
	
	public EmpChapterArticlBookBO() {
		
	}
	
	public EmpChapterArticlBookBO(int id) {
		super();
		this.id = id;
	}
	
	public EmpChapterArticlBookBO(String title, String editorsName,
			String titleChapterArticle, String language,
			String placePublication, String isbn, String totalPages,
			String authorName, String monthYear, String companyInstitution,
			String pagesFrom, String pagesTo, String doi, Boolean isChapterArticleBook) {
		super();
		this.title = title;
		this.editorsName = editorsName;
		this.titleChapterArticle = titleChapterArticle;
		this.language = language;
		this.placePublication = placePublication;
		this.isbn = isbn;
		this.totalPages = totalPages;
		this.authorName = authorName;
		this.monthYear = monthYear;
		this.companyInstitution = companyInstitution;
		this.pagesFrom = pagesFrom;
		this.pagesTo = pagesTo;
		this.doi = doi;
		this.isChapterArticleBook = isChapterArticleBook;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getEditorsName() {
		return editorsName;
	}
	public void setEditorsName(String editorsName) {
		this.editorsName = editorsName;
	}
	public String getTitleChapterArticle() {
		return titleChapterArticle;
	}
	public void setTitleChapterArticle(String titleChapterArticle) {
		this.titleChapterArticle = titleChapterArticle;
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
	public String getAuthorName() {
		return authorName;
	}
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
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
	public String getPagesFrom() {
		return pagesFrom;
	}
	public void setPagesFrom(String pagesFrom) {
		this.pagesFrom = pagesFrom;
	}
	public String getPagesTo() {
		return pagesTo;
	}
	public void setPagesTo(String pagesTo) {
		this.pagesTo = pagesTo;
	}
	public String getDoi() {
		return doi;
	}
	public void setDoi(String doi) {
		this.doi = doi;
	}
	public Boolean getIsChapterArticleBook() {
		return isChapterArticleBook;
	}
	public void setIsChapterArticleBook(Boolean isChapterArticleBook) {
		this.isChapterArticleBook = isChapterArticleBook;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

}
