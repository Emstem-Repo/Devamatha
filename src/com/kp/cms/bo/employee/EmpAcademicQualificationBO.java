package com.kp.cms.bo.employee;

import java.io.Serializable;
import java.util.Date;

import com.kp.cms.bo.admin.Employee;

public class EmpAcademicQualificationBO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int id;
	private String courseName;
	private String university;
	private String year;
	private String grade;
	private String createdBy;
	private Date createdDate;
	private Employee employee;
	private String modifiedBy;
	private Date lastModifiedDate;
	private GuestFaculty guest;
	
	
	public GuestFaculty getGuest() {
		return guest;
	}
	public void setGuest(GuestFaculty guest) {
		this.guest = guest;
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
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getUniversity() {
		return university;
	}
	public void setUniversity(String university) {
		this.university = university;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
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
	
}
