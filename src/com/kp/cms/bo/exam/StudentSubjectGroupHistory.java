package com.kp.cms.bo.exam;

import java.util.Date;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.SubjectGroup;

public class StudentSubjectGroupHistory implements java.io.Serializable{
	
	private int id;
	private Integer schemeNo;
	private Student student;
	private SubjectGroup subjectGroup;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	
	public StudentSubjectGroupHistory() {
		super();
	}
	
	public StudentSubjectGroupHistory(int id, Integer schemeNo,
			Student student, SubjectGroup subjectGroup) {
		super();
		this.id = id;
		this.schemeNo = schemeNo;
		this.student = student;
		this.subjectGroup = subjectGroup;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Integer getSchemeNo() {
		return schemeNo;
	}
	public void setSchemeNo(Integer schemeNo) {
		this.schemeNo = schemeNo;
	}
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public SubjectGroup getSubjectGroup() {
		return subjectGroup;
	}
	public void setSubjectGroup(SubjectGroup subjectGroup) {
		this.subjectGroup = subjectGroup;
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
