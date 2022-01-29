package com.kp.cms.to.exam;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class ExamSubjectRuleSettingsTO implements Serializable
{
  private String academicYear;
  private String schemeType;
  private List<Integer> courseIdList;
  private String programType;
  private String programName;
  private String subjectFinalTheoryExamChecked ;
  private String subjectFinalPracticalExamChecked;
  private String subjectFinalInternalExamChecked;
  private String subjectFinalAttendanceChecked;
  private String maximumSubjectFinal;
  private String minimumSubjectFinal;
  private String valuatedSubjectFinal;
  private boolean isSubjectFinalTheoryExamChecked;
  private boolean isSubjectFinalPracticalExamChecked;
  private boolean isSubjectFinalInternalExamChecked;
  private boolean isSubjectFinalAttendanceChecked;
  
private List<ExamSubjectRuleSettingsEditTO> editTO;
  
public ExamSubjectRuleSettingsTO() {
		super();
	}
  public ExamSubjectRuleSettingsTO(String subjectFinalTheoryExamChecked,
			String subjectFinalPracticalExamChecked,
			String subjectFinalInternalExamChecked,
			String subjectFinalAttendanceChecked, String minimumSubjectFinal,
			String maximumSubjectFinal, String valuatedSubjectFinal) {
		super();
		this.subjectFinalTheoryExamChecked = subjectFinalTheoryExamChecked;
		this.subjectFinalPracticalExamChecked = subjectFinalPracticalExamChecked;
		this.subjectFinalInternalExamChecked = subjectFinalInternalExamChecked;
		this.subjectFinalAttendanceChecked = subjectFinalAttendanceChecked;
		this.minimumSubjectFinal = minimumSubjectFinal;
		this.maximumSubjectFinal = maximumSubjectFinal;
		this.valuatedSubjectFinal = valuatedSubjectFinal;
	}
  public ExamSubjectRuleSettingsTO(String maximumSubjectFinal,
		String minimumSubjectFinal, String valuatedSubjectFinal,
		boolean isSubjectFinalTheoryExamChecked,
		boolean isSubjectFinalPracticalExamChecked,
		boolean isSubjectFinalInternalExamChecked,
		boolean isSubjectFinalAttendanceChecked) {
	super();
	this.maximumSubjectFinal = maximumSubjectFinal;
	this.minimumSubjectFinal = minimumSubjectFinal;
	this.valuatedSubjectFinal = valuatedSubjectFinal;
	this.isSubjectFinalTheoryExamChecked = isSubjectFinalTheoryExamChecked;
	this.isSubjectFinalPracticalExamChecked = isSubjectFinalPracticalExamChecked;
	this.isSubjectFinalInternalExamChecked = isSubjectFinalInternalExamChecked;
	this.isSubjectFinalAttendanceChecked = isSubjectFinalAttendanceChecked;
}
public String getAcademicYear() {
	return academicYear;
}
  
  public ExamSubjectRuleSettingsTO(String academicYear, String schemeType,
			String programType,String programName, List<Integer> courseIdList) {
		super();
		this.academicYear = academicYear;
		this.schemeType = schemeType;
		this.programType = programType;
		this.courseIdList = courseIdList;
		this.programName=programName;
	}
  
public void setAcademicYear(String academicYear) {
	this.academicYear = academicYear;
}
public String getSchemeType() {
	return schemeType;
}
public void setSchemeType(String schemeType) {
	this.schemeType = schemeType;
}
public List<Integer> getCourseIdList() {
	return courseIdList;
}
public void setCourseIdList(List<Integer> courseIdList) {
	this.courseIdList = courseIdList;
}
public String getProgramType() {
	return programType;
}
public void setProgramType(String programType) {
	this.programType = programType;
}

public void setProgramName(String programName) {
	this.programName = programName;
}

public String getProgramName() {
	return programName;
}
public String getSubjectFinalTheoryExamChecked() {
	return subjectFinalTheoryExamChecked;
}
public void setSubjectFinalTheoryExamChecked(
		String subjectFinalTheoryExamChecked) {
	this.subjectFinalTheoryExamChecked = subjectFinalTheoryExamChecked;
}
public String getSubjectFinalPracticalExamChecked() {
	return subjectFinalPracticalExamChecked;
}
public void setSubjectFinalPracticalExamChecked(
		String subjectFinalPracticalExamChecked) {
	this.subjectFinalPracticalExamChecked = subjectFinalPracticalExamChecked;
}
public String getSubjectFinalInternalExamChecked() {
	return subjectFinalInternalExamChecked;
}
public void setSubjectFinalInternalExamChecked(
		String subjectFinalInternalExamChecked) {
	this.subjectFinalInternalExamChecked = subjectFinalInternalExamChecked;
}
public String getSubjectFinalAttendanceChecked() {
	return subjectFinalAttendanceChecked;
}
public void setSubjectFinalAttendanceChecked(
		String subjectFinalAttendanceChecked) {
	this.subjectFinalAttendanceChecked = subjectFinalAttendanceChecked;
}
public String getMaximumSubjectFinal() {
	return maximumSubjectFinal;
}
public void setMaximumSubjectFinal(String maximumSubjectFinal) {
	this.maximumSubjectFinal = maximumSubjectFinal;
}
public String getMinimumSubjectFinal() {
	return minimumSubjectFinal;
}
public void setMinimumSubjectFinal(String minimumSubjectFinal) {
	this.minimumSubjectFinal = minimumSubjectFinal;
}
public String getValuatedSubjectFinal() {
	return valuatedSubjectFinal;
}
public void setValuatedSubjectFinal(String valuatedSubjectFinal) {
	this.valuatedSubjectFinal = valuatedSubjectFinal;
}

public List<ExamSubjectRuleSettingsEditTO> getEditTO() {
	return editTO;
}
public void setEditTO(List<ExamSubjectRuleSettingsEditTO> editTO) {
	this.editTO = editTO;
}

public boolean getIsSubjectFinalTheoryExamChecked() {
	return isSubjectFinalTheoryExamChecked;
}
public void setIsSubjectFinalTheoryExamChecked(
		boolean isSubjectFinalTheoryExamChecked) {
	this.isSubjectFinalTheoryExamChecked = isSubjectFinalTheoryExamChecked;
}
public boolean getIsSubjectFinalPracticalExamChecked() {
	return isSubjectFinalPracticalExamChecked;
}
public void setIsSubjectFinalPracticalExamChecked(
		boolean isSubjectFinalPracticalExamChecked) {
	this.isSubjectFinalPracticalExamChecked = isSubjectFinalPracticalExamChecked;
}
public boolean getIsSubjectFinalInternalExamChecked() {
	return isSubjectFinalInternalExamChecked;
}
public void setIsSubjectFinalInternalExamChecked(
		boolean isSubjectFinalInternalExamChecked) {
	this.isSubjectFinalInternalExamChecked = isSubjectFinalInternalExamChecked;
}
public boolean getIsSubjectFinalAttendanceChecked() {
	return isSubjectFinalAttendanceChecked;
}
public void setIsSubjectFinalAttendanceChecked(
		boolean isSubjectFinalAttendanceChecked) {
	this.isSubjectFinalAttendanceChecked = isSubjectFinalAttendanceChecked;
}
}
