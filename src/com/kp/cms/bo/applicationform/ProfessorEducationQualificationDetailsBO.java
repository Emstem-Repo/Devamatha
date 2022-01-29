package com.kp.cms.bo.applicationform;

public class ProfessorEducationQualificationDetailsBO {
	private int id;
	private ProfessorEducationDetailsBO professorEducationDataId;
	private ProfessorQualificationBO professorQualificationId;
	private String subjectName;
	private String collegeName;
	private String universityName;
	private Boolean isMphil;
	private Boolean isPhd;
	private String percentage;
	

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
	public ProfessorQualificationBO getProfessorQualificationId() {
		return professorQualificationId;
	}
	public void setProfessorQualificationId(
			ProfessorQualificationBO professorQualificationId) {
		this.professorQualificationId = professorQualificationId;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public String getCollegeName() {
		return collegeName;
	}
	public void setCollegeName(String collegeName) {
		this.collegeName = collegeName;
	}
	public String getUniversityName() {
		return universityName;
	}
	public void setUniversityName(String universityName) {
		this.universityName = universityName;
	}
	public Boolean getIsMphil() {
		return isMphil;
	}
	public void setIsMphil(Boolean isMphil) {
		this.isMphil = isMphil;
	}
	public Boolean getIsPhd() {
		return isPhd;
	}
	public void setIsPhd(Boolean isPhd) {
		this.isPhd = isPhd;
	}
	
	public String getPercentage() {
		return percentage;
	}
	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}
}
