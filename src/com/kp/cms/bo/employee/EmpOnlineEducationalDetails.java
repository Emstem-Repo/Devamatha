package com.kp.cms.bo.employee;

import java.io.Serializable;
import java.util.Date;

import com.kp.cms.bo.admin.EmpOnlineResume;
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.QualificationLevelBO;

public class EmpOnlineEducationalDetails implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String course;
	private String specialization;
	private String grade;
	private int yearOfCompletion;
	private String institute;
	private boolean active;
	private QualificationLevelBO empQualificationLevel;
	private EmpOnlineResume empOnlineResume;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private Employee employee;
	
	public EmpOnlineEducationalDetails(){
		
	}
	/**
	 * @param id
	 * @param course
	 * @param specialization
	 * @param grade
	 * @param yearOfCompletion
	 * @param institute
	 * @param active
	 * @param empQualificationLevel
	 * @param empOnlineResume
	 * @param createdBy
	 * @param createdDate
	 * @param modifiedBy
	 * @param modifiedDate
	 */
	public EmpOnlineEducationalDetails(int id, String course, String specialization,
			String grade, int yearOfCompletion, String institute,
			boolean active, QualificationLevelBO empQualificationLevel,
			EmpOnlineResume empOnlineResume, String createdBy,
			Date createdDate, String modifiedBy, Date modifiedDate, Employee employee) {
		super();
		this.id = id;
		this.course = course;
		this.specialization = specialization;
		this.grade = grade;
		this.yearOfCompletion = yearOfCompletion;
		this.institute = institute;
		this.active = active;
		this.empQualificationLevel = empQualificationLevel;
		this.empOnlineResume = empOnlineResume;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.employee=employee;
	}
	public int getId() {
		return id;
	}
	public String getCourse() {
		return course;
	}
	public String getSpecialization() {
		return specialization;
	}
	public String getGrade() {
		return grade;
	}
	public int getYearOfCompletion() {
		return yearOfCompletion;
	}
	public String getInstitute() {
		return institute;
	}
	public boolean isActive() {
		return active;
	}
	public QualificationLevelBO getEmpQualificationLevel() {
		return empQualificationLevel;
	}
	public EmpOnlineResume getEmpOnlineResume() {
		return empOnlineResume;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public void setYearOfCompletion(int yearOfCompletion) {
		this.yearOfCompletion = yearOfCompletion;
	}
	public void setInstitute(String institute) {
		this.institute = institute;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public void setEmpQualificationLevel(QualificationLevelBO empQualificationLevel) {
		this.empQualificationLevel = empQualificationLevel;
	}
	public void setEmpOnlineResume(EmpOnlineResume empOnlineResume) {
		this.empOnlineResume = empOnlineResume;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
}
