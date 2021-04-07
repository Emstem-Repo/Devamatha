package com.kp.cms.bo.employee;

public class EmpArticleJournalsBO {
	
	private int id;
	private String empResPubMasterId;
	private String title;
	private String authorName;
	private String language;
	private String isbn;
	private String monthYear;
	private String nameJournal;
	private String volumeNumber;
	private String issueNumber;
	private String pagesFrom;
	private String pagesTo;
	private String doi;
	private Boolean isArticleJournal; 
	
	public EmpArticleJournalsBO() {
		
	}
	public EmpArticleJournalsBO(int id) {
		this.id = id;
	}
	
	public EmpArticleJournalsBO(int id, String empResPubMasterId, String title,
			String authorName, String language, String isbn, String monthYear,
			String nameJournal, String volumeNumber, String issueNumber,
			String pagesFrom, String pagesTo, String doi, Boolean isArticleJournal) {
		super();
		this.id = id;
		this.empResPubMasterId = empResPubMasterId;
		this.title = title;
		this.authorName = authorName;
		this.language = language;
		this.isbn = isbn;
		this.monthYear = monthYear;
		this.nameJournal = nameJournal;
		this.volumeNumber = volumeNumber;
		this.issueNumber = issueNumber;
		this.pagesFrom = pagesFrom;
		this.pagesTo = pagesTo;
		this.doi = doi;
		this.isArticleJournal= isArticleJournal;
	}
	
	
	public String getEmpResPubMasterId() {
		return empResPubMasterId;
	}
	public void setEmpResPubMasterId(String empResPubMasterId) {
		this.empResPubMasterId = empResPubMasterId;
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
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public String getMonthYear() {
		return monthYear;
	}
	public void setMonthYear(String monthYear) {
		this.monthYear = monthYear;
	}
	public String getNameJournal() {
		return nameJournal;
	}
	public void setNameJournal(String nameJournal) {
		this.nameJournal = nameJournal;
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
	public String getDoi() {
		return doi;
	}
	public void setDoi(String doi) {
		this.doi = doi;
	}


	public Boolean getIsArticleJournal() {
		return isArticleJournal;
	}


	public void setIsArticleJournal(Boolean isArticleJournal) {
		this.isArticleJournal = isArticleJournal;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}
	
}
