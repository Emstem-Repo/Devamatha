package com.kp.cms.bo.employee;

public class EmpArticlInPeriodicalsBO {
	private int id;
	
	private String title;
	private String authorName;
	private String monthYear;
	private String namePeriodical;
	private String language;
	private String volumeNumber;
	private String issueNumber;
	private String pagesFrom;
	private String pagesTo;
	private String isbn;
	private Boolean isArticleInPeriodicals;
	
	
	
	public EmpArticlInPeriodicalsBO() {
		super();
	}
	
	public EmpArticlInPeriodicalsBO(int id) {
		super();
		this.id = id;
	}
	public EmpArticlInPeriodicalsBO(String title, String authorName,
			String monthYear, String namePeriodical, String language,
			String volumeNumber, String issueNumber, String pagesFrom,
			String pagesTo, String isbn, Boolean isArticleInPeriodicals) {
		super();
		
		this.title = title;
		this.authorName = authorName;
		this.monthYear = monthYear;
		this.namePeriodical = namePeriodical;
		this.language = language;
		this.volumeNumber = volumeNumber;
		this.issueNumber = issueNumber;
		this.pagesFrom = pagesFrom;
		this.pagesTo = pagesTo;
		this.isbn = isbn;
		this.isArticleInPeriodicals= isArticleInPeriodicals;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getMonthYear() {
		return monthYear;
	}
	public void setMonthYear(String monthYear) {
		this.monthYear = monthYear;
	}
	public String getNamePeriodical() {
		return namePeriodical;
	}
	public void setNamePeriodical(String namePeriodical) {
		this.namePeriodical = namePeriodical;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getVolumeNumber() {
		return volumeNumber;
	}
	public void setVolumeNumber(String volumeNumber) {
		this.volumeNumber = volumeNumber;
	}
	public String getIssueNumber() {
		return issueNumber;
	}
	public void setIssueNumber(String issueNumber) {
		this.issueNumber = issueNumber;
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
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public Boolean getIsArticleInPeriodicals() {
		return isArticleInPeriodicals;
	}
	public void setIsArticleInPeriodicals(Boolean isArticleInPeriodicals) {
		this.isArticleInPeriodicals = isArticleInPeriodicals;
	}
	

}
