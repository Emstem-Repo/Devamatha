package com.kp.cms.to.admin;

public class SyllabusEntryProgramDetailsTo {
	private String departmentOverview;
	private String missionStatement;
	private String introductionProgramme;
	private String programObjective;
	private String assesmentPattern;
	private String examinationAndAssesments;
	private int courseId;
	private int id;
	private int batchYear;
	
	
	public int getCourseId() {
		return courseId;
	}
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getBatchYear() {
		return batchYear;
	}
	public void setBatchYear(int batchYear) {
		this.batchYear = batchYear;
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
}
