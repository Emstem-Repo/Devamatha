package com.kp.cms.to.admin;

import java.math.BigDecimal;
import java.util.Date;

import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.Program;

public class WeightageTO {
	private int id;
	private String createdBy;;
	private String modifiedBy;
	private Program program;
	private Course course;
	private String name;
	private String description;
	private Integer year;
	private Date createdDate;
	private Date lastModifiedDate;
	private BigDecimal educationWeightage;
	private BigDecimal interviewWeightage;
	private BigDecimal prerequisiteWeightage;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Program getProgram() {
		return program;
	}
	public void setProgram(Program program) {
		this.program = program;
	}
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	public BigDecimal getEducationWeightage() {
		return educationWeightage;
	}
	public void setEducationWeightage(BigDecimal educationWeightage) {
		this.educationWeightage = educationWeightage;
	}
	public BigDecimal getInterviewWeightage() {
		return interviewWeightage;
	}
	public void setInterviewWeightage(BigDecimal interviewWeightage) {
		this.interviewWeightage = interviewWeightage;
	}
	public BigDecimal getPrerequisiteWeightage() {
		return prerequisiteWeightage;
	}
	public void setPrerequisiteWeightage(BigDecimal prerequisiteWeightage) {
		this.prerequisiteWeightage = prerequisiteWeightage;
	}
	
	
}
