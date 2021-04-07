package com.kp.cms.to.exam;

import java.io.Serializable;

public class ExamSubDefinitionCourseWiseDisplayTO  implements Serializable,Comparable<ExamSubDefinitionCourseWiseDisplayTO>
{
	private int id;
	private int subjectId;
	private String subjectCode;
	private int subjectOrder;
	private String universitySubjectCode;
	private int subjectSectionId;
	private int theoryHours;
	private int theoryCredit;
	private int practicalHours;
	private int practicalCredit;
	private String dontShowSubType;
	private String dontShowMaxMarks;
	private String dontShowMinMarks;
	private String dontShowAttMarks;
	private String dontAddTotMarkClsDecln;
	private String dontConsiderFailureTotalResult;
	private String showInternalFinalMarkAdded;
	private String showOnlyGrade;
	private int isActiveGradeDefn;
	private int isActiveAttndnc;
	private String subjectName;
	private String sectionName;
	public int getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}
	public int getSubjectOrder() {
		return subjectOrder;
	}
	public void setSubjectOrder(int subjectOrder) {
		this.subjectOrder = subjectOrder;
	}
	public String getUniversitySubjectCode() {
		return universitySubjectCode;
	}
	public void setUniversitySubjectCode(String universitySubjectCode) {
		this.universitySubjectCode = universitySubjectCode;
	}
	public int getSubjectSectionId() {
		return subjectSectionId;
	}
	public void setSubjectSectionId(int subjectSectionId) {
		this.subjectSectionId = subjectSectionId;
	}
	public int getTheoryHours() {
		return theoryHours;
	}
	public void setTheoryHours(int theoryHours) {
		this.theoryHours = theoryHours;
	}
	public int getTheoryCredit() {
		return theoryCredit;
	}
	public void setTheoryCredit(int theoryCredit) {
		this.theoryCredit = theoryCredit;
	}
	public int getPracticalHours() {
		return practicalHours;
	}
	public void setPracticalHours(int practicalHours) {
		this.practicalHours = practicalHours;
	}
	public int getPracticalCredit() {
		return practicalCredit;
	}
	public void setPracticalCredit(int practicalCredit) {
		this.practicalCredit = practicalCredit;
	}
	
	
	public String getDontShowSubType() {
		return dontShowSubType;
	}
	public void setDontShowSubType(String dontShowSubType) {
		this.dontShowSubType = dontShowSubType;
	}
	public String getDontShowMaxMarks() {
		return dontShowMaxMarks;
	}
	public void setDontShowMaxMarks(String dontShowMaxMarks) {
		this.dontShowMaxMarks = dontShowMaxMarks;
	}
	public String getDontShowMinMarks() {
		return dontShowMinMarks;
	}
	public void setDontShowMinMarks(String dontShowMinMarks) {
		this.dontShowMinMarks = dontShowMinMarks;
	}
	public String getDontShowAttMarks() {
		return dontShowAttMarks;
	}
	public void setDontShowAttMarks(String dontShowAttMarks) {
		this.dontShowAttMarks = dontShowAttMarks;
	}
	public String getDontAddTotMarkClsDecln() {
		return dontAddTotMarkClsDecln;
	}
	public void setDontAddTotMarkClsDecln(String dontAddTotMarkClsDecln) {
		this.dontAddTotMarkClsDecln = dontAddTotMarkClsDecln;
	}
	public String getDontConsiderFailureTotalResult() {
		return dontConsiderFailureTotalResult;
	}
	public void setDontConsiderFailureTotalResult(
			String dontConsiderFailureTotalResult) {
		this.dontConsiderFailureTotalResult = dontConsiderFailureTotalResult;
	}
	public String getShowInternalFinalMarkAdded() {
		return showInternalFinalMarkAdded;
	}
	public void setShowInternalFinalMarkAdded(String showInternalFinalMarkAdded) {
		this.showInternalFinalMarkAdded = showInternalFinalMarkAdded;
	}
	public String getShowOnlyGrade() {
		return showOnlyGrade;
	}
	public void setShowOnlyGrade(String showOnlyGrade) {
		this.showOnlyGrade = showOnlyGrade;
	}
	public int getIsActiveGradeDefn() {
		return isActiveGradeDefn;
	}
	public void setIsActiveGradeDefn(int isActiveGradeDefn) {
		this.isActiveGradeDefn = isActiveGradeDefn;
	}
	public int getIsActiveAttndnc() {
		return isActiveAttndnc;
	}
	public void setIsActiveAttndnc(int isActiveAttndnc) {
		this.isActiveAttndnc = isActiveAttndnc;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public String getSectionName() {
		return sectionName;
	}
	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}
	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}
	public String getSubjectCode() {
		return subjectCode;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
	@Override
	public int compareTo(ExamSubDefinitionCourseWiseDisplayTO arg0) {
		if(arg0!=null && this!=null && arg0.getSubjectName()!=null 
				 && this.getSubjectName()!=null){
			
				return this.getSubjectName().compareTo(arg0.getSubjectName());
		}else
		return 0;
	}

}
