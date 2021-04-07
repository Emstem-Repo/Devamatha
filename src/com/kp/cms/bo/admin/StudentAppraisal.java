package com.kp.cms.bo.admin;

// Generated Sep 22, 2009 4:21:49 PM by Hibernate Tools 3.2.0.b9

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * StudentAppraisal generated by hbm2java
 */
public class StudentAppraisal implements java.io.Serializable {

	private int id;
	private Student student;
	private Employee employee;
	private String comments;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	private Set<StudentAppraisalDetails> studentAppraisalDetailses = new HashSet<StudentAppraisalDetails>(
			0);

	public StudentAppraisal() {
	}

	public StudentAppraisal(int id) {
		this.id = id;
	}

	public StudentAppraisal(int id, Student student, Employee employee,
			String comments, String createdBy, Date createdDate,
			String modifiedBy, Date lastModifiedDate, Boolean isActive,
			Set<StudentAppraisalDetails> studentAppraisalDetailses) {
		this.id = id;
		this.student = student;
		this.employee = employee;
		this.comments = comments;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModifiedDate = lastModifiedDate;
		this.isActive = isActive;
		this.studentAppraisalDetailses = studentAppraisalDetailses;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Student getStudent() {
		return this.student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Employee getEmployee() {
		return this.employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getLastModifiedDate() {
		return this.lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public Boolean getIsActive() {
		return this.isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Set<StudentAppraisalDetails> getStudentAppraisalDetailses() {
		return this.studentAppraisalDetailses;
	}

	public void setStudentAppraisalDetailses(
			Set<StudentAppraisalDetails> studentAppraisalDetailses) {
		this.studentAppraisalDetailses = studentAppraisalDetailses;
	}

}
