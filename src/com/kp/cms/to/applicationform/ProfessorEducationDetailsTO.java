package com.kp.cms.to.applicationform;

import java.io.Serializable;
import java.util.List;

public class ProfessorEducationDetailsTO {
	
	private int id;
	private String subjectName;
	private String collegeName;
	private String universityName;
	private Boolean isMphil;
	private Boolean isPhd;
	
	private int qualificationId;
	private String qualificationName;
	private String percentage;

	private String rankPosition;
	

	



	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	
	public int getQualificationId() {
		return qualificationId;
	}
	public void setQualificationId(int qualificationId) {
		this.qualificationId = qualificationId;
	}
	public String getQualificationName() {
		return qualificationName;
	}
	public void setQualificationName(String qualificationName) {
		this.qualificationName = qualificationName;
	}
	
	public String getPercentage() {
		return percentage;
	}
	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}
	
	public String getRankPosition() {
		return rankPosition;
	}
	public void setRankPosition(String rankPosition) {
		this.rankPosition = rankPosition;
	}


}
