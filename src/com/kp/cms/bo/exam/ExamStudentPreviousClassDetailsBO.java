package com.kp.cms.bo.exam;

import java.util.Date;

@SuppressWarnings("serial")
public class ExamStudentPreviousClassDetailsBO extends ExamGenBO {

	private int studentId;
	private int classId;
	private int subjectGroupId;
	private int academicYear;
	private int schemeNo;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;

	private StudentUtilBO studentUtilBO;
	private ClassUtilBO classUtilBO;
	private SubjectGroupUtilBO subjectGroupUtilBO;

	public ExamStudentPreviousClassDetailsBO() {
		super();
	}

	public ExamStudentPreviousClassDetailsBO(int studentId, int classId,
			int subjectGroupId, int academicYear, int schemeNo) {
		super();
		this.studentId = studentId;
		this.classId = classId;
		this.subjectGroupId = subjectGroupId;
		this.academicYear = academicYear;
		this.schemeNo = schemeNo;
	}

	public ExamStudentPreviousClassDetailsBO(Integer studentId,
			Integer classId, Integer academicYear, Integer schemeNo) {
		this.studentId = studentId;
		this.classId = classId;
		this.academicYear = academicYear;
		this.schemeNo = schemeNo;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public int getClassId() {
		return classId;
	}

	public void setClassId(int classId) {
		this.classId = classId;
	}

	public int getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(int academicYear) {
		this.academicYear = academicYear;
	}

	public int getSchemeNo() {
		return schemeNo;
	}

	public void setSchemeNo(int schemeNo) {
		this.schemeNo = schemeNo;
	}

	public StudentUtilBO getStudentUtilBO() {
		return studentUtilBO;
	}

	public void setStudentUtilBO(StudentUtilBO studentUtilBO) {
		this.studentUtilBO = studentUtilBO;
	}

	public ClassUtilBO getClassUtilBO() {
		return classUtilBO;
	}

	public void setClassUtilBO(ClassUtilBO classUtilBO) {
		this.classUtilBO = classUtilBO;
	}

	public void setSubjectGroupUtilBO(SubjectGroupUtilBO subjectGroupUtilBO) {
		this.subjectGroupUtilBO = subjectGroupUtilBO;
	}

	public SubjectGroupUtilBO getSubjectGroupUtilBO() {
		return subjectGroupUtilBO;
	}

	public void setSubjectGroupId(int subjectGroupId) {
		this.subjectGroupId = subjectGroupId;
	}

	public int getSubjectGroupId() {
		return subjectGroupId;
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
