package com.kp.cms.bo.exam;

import java.util.Date;

@SuppressWarnings("serial")
public class ExamSubDefinitionCourseWiseBO extends ExamGenBO {

	private int subjectId;
	private int subjectOrder;
	private String universitySubjectCode;
	private int subjectSectionId;
	private int theoryHours;
	private int theoryCredit;
	private int practicalHours;
	private int practicalCredit;
	private int dontShowSubType;
	private int dontShowMaxMarks;
	private int dontShowMinMarks;
	private int dontShowAttMarks;
	private int dontAddTotMarkClsDecln;
	private int dontConsiderFailureTotalResult;
	private int showInternalFinalMarkAdded;
	private int showOnlyGrade;
	private int isActiveGradeDefn;
	private int isActiveAttndnc;
	private int courseId;
	private Integer schemeNo;
	private int academicYear;
	
	private SubjectUtilBO subjectUtilBO;
	private ExamSubjectSectionMasterBO examSubjectSectionMasterBO;

	public ExamSubDefinitionCourseWiseBO() {
		super();
	}

	public ExamSubDefinitionCourseWiseBO(int subjectId, int subjectOrder,
			String universitySubjectCode, int subjectSectionId,
			int theoryHours, int theoryCredit, int practicalHours,
			int practicalCredit, int dontShowSubType, int dontShowMaxMarks,
			int dontShowMinMarks, int dontShowAttMarks,
			int dontAddTotMarkClsDecln, int dontConsiderFailureTotalResult,
			int showInternalFinalMarkAdded, int showOnlyGrade,
			int isActiveGradeDefn, int isActiveAttndnc, String userId,
			int courseId, Integer schemeNo,int academicYear) {
		super();
		this.subjectId = subjectId;
		this.subjectOrder = subjectOrder;
		this.universitySubjectCode = universitySubjectCode;
		this.subjectSectionId = subjectSectionId;
		this.theoryHours = theoryHours;
		this.theoryCredit = theoryCredit;
		this.practicalHours = practicalHours;
		this.practicalCredit = practicalCredit;
		this.dontShowSubType = dontShowSubType;
		this.dontShowMaxMarks = dontShowMaxMarks;
		this.dontShowMinMarks = dontShowMinMarks;
		this.dontShowAttMarks = dontShowAttMarks;
		this.dontAddTotMarkClsDecln = dontAddTotMarkClsDecln;
		this.dontConsiderFailureTotalResult = dontConsiderFailureTotalResult;
		this.showInternalFinalMarkAdded = showInternalFinalMarkAdded;
		this.showOnlyGrade = showOnlyGrade;
		this.isActiveGradeDefn = isActiveGradeDefn;
		this.isActiveAttndnc = isActiveAttndnc;
		this.modifiedBy = userId;
		this.createdBy = userId;
		this.createdDate=new Date();
		this.lastModifiedDate = new Date();
		this.courseId = courseId;
		this.schemeNo = schemeNo;
		this.academicYear = academicYear;
	}

	public ExamSubDefinitionCourseWiseBO(int subjectId, int subjectOrder,
			String universitySubjectCode, int subjectSectionId,
			int theoryHours, int theoryCredit, int dontShowSubType,
			int dontShowMaxMarks, int dontShowMinMarks, int dontShowAttMarks,
			int dontAddTotMarkClsDecln, int dontConsiderFailureTotalResult,
			int showInternalFinalMarkAdded, int showOnlyGrade,
			int isActiveGradeDefn, int isActiveAttndnc, String userId,
			int courseId, Integer schemeNo,int academicYear) {
		super();
		this.subjectId = subjectId;
		this.subjectOrder = subjectOrder;
		this.universitySubjectCode = universitySubjectCode;
		this.subjectSectionId = subjectSectionId;
		this.theoryHours = theoryHours;
		this.theoryCredit = theoryCredit;
		this.dontShowSubType = dontShowSubType;
		this.dontShowMaxMarks = dontShowMaxMarks;
		this.dontShowMinMarks = dontShowMinMarks;
		this.dontShowAttMarks = dontShowAttMarks;
		this.dontAddTotMarkClsDecln = dontAddTotMarkClsDecln;
		this.dontConsiderFailureTotalResult = dontConsiderFailureTotalResult;
		this.showInternalFinalMarkAdded = showInternalFinalMarkAdded;
		this.showOnlyGrade = showOnlyGrade;
		this.isActiveGradeDefn = isActiveGradeDefn;
		this.isActiveAttndnc = isActiveAttndnc;
		this.modifiedBy = userId;
		this.createdBy = userId;
		this.createdDate=new Date();
		this.lastModifiedDate = new Date();
		this.courseId = courseId;
		this.schemeNo = schemeNo;
		this.academicYear = academicYear;
	}

	public ExamSubDefinitionCourseWiseBO(int id, int subjectId,
			int subjectOrder, String universitySubjectCode,
			int subjectSectionId, int theoryHours, int theoryCredit,
			int practicalHours, int practicalCredit, int dontShowSubType,
			int dontShowMaxMarks, int dontShowMinMarks, int dontShowAttMarks,
			int dontAddTotMarkClsDecln, int dontConsiderFailureTotalResult,
			int showInternalFinalMarkAdded, int showOnlyGrade,
			int isActiveGradeDefn, int isActiveAttndnc, String userId,
			int courseId, Integer schemeNo,int academicYear) {
		super();
		this.id = id;
		this.subjectId = subjectId;
		this.subjectOrder = subjectOrder;
		this.universitySubjectCode = universitySubjectCode;
		this.subjectSectionId = subjectSectionId;
		this.theoryHours = theoryHours;
		this.theoryCredit = theoryCredit;
		this.practicalHours = practicalHours;
		this.practicalCredit = practicalCredit;
		this.dontShowSubType = dontShowSubType;
		this.dontShowMaxMarks = dontShowMaxMarks;
		this.dontShowMinMarks = dontShowMinMarks;
		this.dontShowAttMarks = dontShowAttMarks;
		this.dontAddTotMarkClsDecln = dontAddTotMarkClsDecln;
		this.dontConsiderFailureTotalResult = dontConsiderFailureTotalResult;
		this.showInternalFinalMarkAdded = showInternalFinalMarkAdded;
		this.showOnlyGrade = showOnlyGrade;
		this.isActiveGradeDefn = isActiveGradeDefn;
		this.isActiveAttndnc = isActiveAttndnc;
		this.modifiedBy = userId;
		this.createdBy = userId;
		this.createdDate=new Date();
		this.lastModifiedDate = new Date();
		this.courseId = courseId;
		this.schemeNo = schemeNo;
		this.academicYear = academicYear;
	}

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

	public int getDontShowSubType() {
		return dontShowSubType;
	}

	public void setDontShowSubType(int dontShowSubType) {
		this.dontShowSubType = dontShowSubType;
	}

	public int getDontShowMaxMarks() {
		return dontShowMaxMarks;
	}

	public void setDontShowMaxMarks(int dontShowMaxMarks) {
		this.dontShowMaxMarks = dontShowMaxMarks;
	}

	public int getDontShowMinMarks() {
		return dontShowMinMarks;
	}

	public void setDontShowMinMarks(int dontShowMinMarks) {
		this.dontShowMinMarks = dontShowMinMarks;
	}

	public int getDontShowAttMarks() {
		return dontShowAttMarks;
	}

	public void setDontShowAttMarks(int dontShowAttMarks) {
		this.dontShowAttMarks = dontShowAttMarks;
	}

	public int getDontAddTotMarkClsDecln() {
		return dontAddTotMarkClsDecln;
	}

	public void setDontAddTotMarkClsDecln(int dontAddTotMarkClsDecln) {
		this.dontAddTotMarkClsDecln = dontAddTotMarkClsDecln;
	}

	public int getDontConsiderFailureTotalResult() {
		return dontConsiderFailureTotalResult;
	}

	public void setDontConsiderFailureTotalResult(
			int dontConsiderFailureTotalResult) {
		this.dontConsiderFailureTotalResult = dontConsiderFailureTotalResult;
	}

	public int getShowInternalFinalMarkAdded() {
		return showInternalFinalMarkAdded;
	}

	public void setShowInternalFinalMarkAdded(int showInternalFinalMarkAdded) {
		this.showInternalFinalMarkAdded = showInternalFinalMarkAdded;
	}

	public int getShowOnlyGrade() {
		return showOnlyGrade;
	}

	public void setShowOnlyGrade(int showOnlyGrade) {
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

	public SubjectUtilBO getSubjectUtilBO() {
		return subjectUtilBO;
	}

	public void setSubjectUtilBO(SubjectUtilBO subjectUtilBO) {
		this.subjectUtilBO = subjectUtilBO;
	}

	public ExamSubjectSectionMasterBO getExamSubjectSectionMasterBO() {
		return examSubjectSectionMasterBO;
	}

	public void setExamSubjectSectionMasterBO(
			ExamSubjectSectionMasterBO examSubjectSectionMasterBO) {
		this.examSubjectSectionMasterBO = examSubjectSectionMasterBO;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public int getCourseId() {
		return courseId;
	}

	public Integer getSchemeNo() {
		return schemeNo;
	}

	public void setSchemeNo(Integer schemeNo) {
		this.schemeNo = schemeNo;
	}

	public int getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(int academicYear) {
		this.academicYear = academicYear;
	}

	
}
