package com.kp.cms.bo.exam;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import com.kp.cms.bo.admin.ProgramType;

public class OpenInternalMark implements Serializable {
	
	private int id;
	private ExamDefinition exam;
	private Date startDate;
	private Date endDate;
	private Boolean isActive;
	private Date createdDate;
	private Date lastModifiedDate;
	private String createdBy;
	private String modifiedBy;
	private String theoryPractical;
	private ProgramType programType;
	private Set<OpenInternalExamForClasses> classesSet;
	private String displayName;
	
	public OpenInternalMark() {
		super();
		// TODO Auto-generated constructor stub
	}


	public OpenInternalMark(int id, ExamDefinition exam, Date startDate,
			Date endDate, Boolean isActive, Date createdDate,
			Date lastModifiedDate, String createdBy, String modifiedBy, String theoryPractical, ProgramType programType,Set<OpenInternalExamForClasses> classesSet) {
		super();
		this.id = id;
		this.exam = exam;
		this.startDate = startDate;
		this.endDate = endDate;
		this.isActive = isActive;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
		this.theoryPractical=theoryPractical;
		this.programType=programType;
		this.classesSet=classesSet;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public ExamDefinition getExam() {
		return exam;
	}


	public void setExam(ExamDefinition exam) {
		this.exam = exam;
	}


	public Date getStartDate() {
		return startDate;
	}


	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}


	public Date getEndDate() {
		return endDate;
	}


	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}


	public Boolean getIsActive() {
		return isActive;
	}


	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}


	public Date getCreatedDate() {
		return createdDate;
	}


	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}


	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}


	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}


	public String getCreatedBy() {
		return createdBy;
	}


	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}


	public String getModifiedBy() {
		return modifiedBy;
	}


	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}


	public String getTheoryPractical() {
		return theoryPractical;
	}


	public void setTheoryPractical(String theoryPractical) {
		this.theoryPractical = theoryPractical;
	}


	public ProgramType getProgramType() {
		return programType;
	}


	public void setProgramType(ProgramType programType) {
		this.programType = programType;
	}


	public Set<OpenInternalExamForClasses> getClassesSet() {
		return classesSet;
	}


	public void setClassesSet(Set<OpenInternalExamForClasses> classesSet) {
		this.classesSet = classesSet;
	}


	public String getDisplayName() {
		return displayName;
	}


	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	
}
