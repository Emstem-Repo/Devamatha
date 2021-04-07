package com.kp.cms.bo.admin;

import java.util.Date;

public class SyllabusEntryProgramDetails {
	private int id;
	private String departmentOverview;
	private String missionStatement;
	private String introductionProgramme;
	private String programObjective;
	private String assesmentPattern;
	private String examinationAndAssesments;
	private int batchYear;
	private Course courseId;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	
	
	public int getBatchYear() {
		return batchYear;
	}
	public void setBatchYear(int batchYear) {
		this.batchYear = batchYear;
	}
	public Course getCourseId() {
		return courseId;
	}
	public void setCourseId(Course courseId) {
		this.courseId = courseId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDepartmentOverview() {
		return departmentOverview;
	}
	public void setDepartmentOverview(String departmentOverview) {
		this.departmentOverview = departmentOverview;
	}
	public String getMissionStatement() {
		return missionStatement;
	}
	public void setMissionStatement(String missionStatement) {
		this.missionStatement = missionStatement;
	}
	public String getIntroductionProgramme() {
		return introductionProgramme;
	}
	public void setIntroductionProgramme(String introductionProgramme) {
		this.introductionProgramme = introductionProgramme;
	}
	public String getProgramObjective() {
		return programObjective;
	}
	public void setProgramObjective(String programObjective) {
		this.programObjective = programObjective;
	}
	public String getAssesmentPattern() {
		return assesmentPattern;
	}
	public void setAssesmentPattern(String assesmentPattern) {
		this.assesmentPattern = assesmentPattern;
	}
	public String getExaminationAndAssesments() {
		return examinationAndAssesments;
	}
	public void setExaminationAndAssesments(String examinationAndAssesments) {
		this.examinationAndAssesments = examinationAndAssesments;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	public SyllabusEntryProgramDetails(){
		
	}
	public SyllabusEntryProgramDetails(int id, String departmentOverview,
			String missionStatement, String introductionProgramme,
			String programObjective, String assesmentPattern,
			String examinationAndAssesments, String createdBy,
			Date createdDate, String modifiedBy, Date lastModifiedDate,
			Boolean isActive) {
		super();
		this.id = id;
		this.departmentOverview = departmentOverview;
		this.missionStatement = missionStatement;
		this.introductionProgramme = introductionProgramme;
		this.programObjective = programObjective;
		this.assesmentPattern = assesmentPattern;
		this.examinationAndAssesments = examinationAndAssesments;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModifiedDate = lastModifiedDate;
		this.isActive = isActive;
	}
	
	
}
