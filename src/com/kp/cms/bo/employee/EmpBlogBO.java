package com.kp.cms.bo.employee;

public class EmpBlogBO {
	
	private int id;
	private String title;
	private String url;
	private String numberOfComments;
	private String subject;
	private String language;
	private String monthYear;
	private Boolean isBlog;
	
	public EmpBlogBO()
	{}
	
	public EmpBlogBO(String title, String url, String numberOfComments,
			String subject, String language, String monthYear, Boolean isBlog) {
		super();
		this.title = title;
		this.url = url;
		this.numberOfComments = numberOfComments;
		this.subject = subject;
		this.language = language;
		this.monthYear = monthYear;
		this.isBlog = isBlog;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getNumberOfComments() {
		return numberOfComments;
	}

	public void setNumberOfComments(String numberOfComments) {
		this.numberOfComments = numberOfComments;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getMonthYear() {
		return monthYear;
	}

	public void setMonthYear(String monthYear) {
		this.monthYear = monthYear;
	}

	public Boolean getIsBlog() {
		return isBlog;
	}

	public void setIsBlog(Boolean isBlog) {
		this.isBlog = isBlog;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
