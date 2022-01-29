package com.kp.cms.bo.applicationform;

public class ProfessorBooksPublificationDetailsBO {
	private int id;
	private ProfessorEducationDetailsBO professorEducationDataId;

	private String bookName;
	private String articles;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public ProfessorEducationDetailsBO getProfessorEducationDataId() {
		return professorEducationDataId;
	}
	public void setProfessorEducationDataId(
			ProfessorEducationDetailsBO professorEducationDataId) {
		this.professorEducationDataId = professorEducationDataId;
	}

	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	public String getArticles() {
		return articles;
	}
	public void setArticles(String articles) {
		this.articles = articles;
	}

}
