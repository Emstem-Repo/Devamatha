package com.kp.cms.bo.applicationform;

import java.util.Date;

public class ProfessorTeachingExpDetailsBO {
	
	private int id;
	private ProfessorEducationDetailsBO professorEducationDataId;
	private Date fromDate;
	private Date toDate;
	private String collegeName;
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
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	public String getCollegeName() {
		return collegeName;
	}
	public void setCollegeName(String collegeName) {
		this.collegeName = collegeName;
	}

}
