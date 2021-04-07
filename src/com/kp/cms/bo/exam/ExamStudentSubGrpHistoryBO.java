package com.kp.cms.bo.exam;

import java.util.Date;

@SuppressWarnings("serial")
public class ExamStudentSubGrpHistoryBO extends ExamGenBO {

	private int studentId;
	private int subjectGroupId;
	private int schemeNo;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	

	private StudentUtilBO studentUtilBO;
	private SubjectGroupUtilBO subjectGroupUtilBO;

	public ExamStudentSubGrpHistoryBO() {
		super();
	}

	public ExamStudentSubGrpHistoryBO(int studentId, int subjectGroupId,
			int schemeNo) {
		super();
		this.setStudentId(studentId);
		this.subjectGroupId = subjectGroupId;
		this.schemeNo = schemeNo;
	}

	public ExamStudentSubGrpHistoryBO(int studentId, int subjectGroupId) {
		super();
		this.setStudentId(studentId);
		this.subjectGroupId = subjectGroupId;

	}

	public int getSubjectGroupId() {
		return subjectGroupId;
	}

	public void setSubjectGroupId(int subjectGroupId) {
		this.subjectGroupId = subjectGroupId;
	}

	public SubjectGroupUtilBO getSubjectGroupUtilBO() {
		return subjectGroupUtilBO;
	}

	public void setSubjectGroupUtilBO(SubjectGroupUtilBO subjectGroupUtilBO) {
		this.subjectGroupUtilBO = subjectGroupUtilBO;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentUtilBO(StudentUtilBO studentUtilBO) {
		this.studentUtilBO = studentUtilBO;
	}

	public StudentUtilBO getStudentUtilBO() {
		return studentUtilBO;
	}

	public void setSchemeNo(int schemeNo) {
		this.schemeNo = schemeNo;
	}

	public int getSchemeNo() {
		return schemeNo;
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

}
