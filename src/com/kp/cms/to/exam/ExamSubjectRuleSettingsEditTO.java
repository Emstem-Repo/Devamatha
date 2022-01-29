package com.kp.cms.to.exam;

import java.io.Serializable;

public class ExamSubjectRuleSettingsEditTO implements Serializable{
	
	
	private int subjectRuleSettingsId;
	private int subjectId;
	private String subjectName;
	private String courseName;
	private String schemeName;
	private String subjectCode;
	public ExamSubjectRuleSettingsEditTO() {
		super();
	}
	public ExamSubjectRuleSettingsEditTO(int subjectRuleSettingsId,
			String courseName, String schemeName, String subjectName,
			int subjectId, String subjectCode) {
		super();
		this.subjectRuleSettingsId = subjectRuleSettingsId;
		this.courseName = courseName;
		this.schemeName = schemeName;
		this.subjectName = subjectName;
		this.subjectId = subjectId;
		this.subjectCode = subjectCode;
	}
	
	
	public int getSubjectRuleSettingsId() {
		return subjectRuleSettingsId;
	}
	public void setSubjectRuleSettingsId(int subjectRuleSettingsId) {
		this.subjectRuleSettingsId = subjectRuleSettingsId;
	}
	public int getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getSchemeName() {
		return schemeName;
	}
	public void setSchemeName(String schemeName) {
		this.schemeName = schemeName;
	}
	public String getSubjectCode() {
		return subjectCode;
	}
	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}
	
	
	

}
