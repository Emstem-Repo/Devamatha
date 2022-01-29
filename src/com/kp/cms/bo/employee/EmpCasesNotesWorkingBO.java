package com.kp.cms.bo.employee;

public class EmpCasesNotesWorkingBO {
	
	private int id;
	private String title;
	private String abstractObjectives;
	private String authorName;
	private String sponsors;
	private String caseNoteWorkPaper;
	private Boolean isCasesNoteWorking;
	
	public EmpCasesNotesWorkingBO()
	{}
	
	public EmpCasesNotesWorkingBO(int id) {
		super();
		this.id = id;
	}

	public EmpCasesNotesWorkingBO(String title, String abstractObjectives,
			String authorName, String sponsors, String caseNoteWorkPaper, 
			Boolean isCasesNoteWorking) {
		super();
		this.title = title;
		this.abstractObjectives = abstractObjectives;
		this.authorName = authorName;
		this.sponsors = sponsors;
		this.caseNoteWorkPaper = caseNoteWorkPaper;
		this.isCasesNoteWorking = isCasesNoteWorking;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAbstractObjectives() {
		return abstractObjectives;
	}

	public void setAbstractObjectives(String abstractObjectives) {
		this.abstractObjectives = abstractObjectives;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public String getSponsors() {
		return sponsors;
	}

	public void setSponsors(String sponsors) {
		this.sponsors = sponsors;
	}

	public String getCaseNoteWorkPaper() {
		return caseNoteWorkPaper;
	}

	public void setCaseNoteWorkPaper(String caseNoteWorkPaper) {
		this.caseNoteWorkPaper = caseNoteWorkPaper;
	}

	public Boolean getIsCasesNoteWorking() {
		return isCasesNoteWorking;
	}

	public void setIsCasesNoteWorking(Boolean isCasesNoteWorking) {
		this.isCasesNoteWorking = isCasesNoteWorking;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


}
