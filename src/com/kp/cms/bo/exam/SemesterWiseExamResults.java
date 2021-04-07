package com.kp.cms.bo.exam;

import java.util.Date;

import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.Student;

public class SemesterWiseExamResults implements java.io.Serializable{

	private Student student;
	private ExamDefinitionBO examDef;
	private Classes classes;
	
	private int academicYear;
	private String overallGrade;
	private String semNo;
	private Double sgpa;
	private String createdBy;;
	private String modifiedBy;
	private Date createdDate;
	private Date lastModifiedDate;
	private String result;
	private int id;
	private Double totalCretits;
	private Double totalCreditPoints;
	private double totalGradePoints;
	private int totalMarksAwarded;
	private int totalMaxMarks;

	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public ExamDefinitionBO getExamDef() {
		return examDef;
	}
	public void setExamDef(ExamDefinitionBO examDef) {
		this.examDef = examDef;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public Classes getClasses() {
		return classes;
	}
	public void setClasses(Classes classes) {
		this.classes = classes;
	}
	
	public int getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(int academicYear) {
		this.academicYear = academicYear;
	}
	public String getOverallGrade() {
		return overallGrade;
	}
	public void setOverallGrade(String overallGrade) {
		this.overallGrade = overallGrade;
	}
	public String getSemNo() {
		return semNo;
	}
	public void setSemNo(String semNo) {
		this.semNo = semNo;
	}
	public Double getSgpa() {
		return sgpa;
	}
	public void setSgpa(Double sgpa) {
		this.sgpa = sgpa;
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
	public Double getTotalCretits() {
		return totalCretits;
	}
	public void setTotalCretits(Double totalCretits) {
		this.totalCretits = totalCretits;
	}
	public Double getTotalCreditPoints() {
		return totalCreditPoints;
	}
	public int getTotalMarksAwarded() {
		return totalMarksAwarded;
	}
	public void setTotalMarksAwarded(int totalMarksAwarded) {
		this.totalMarksAwarded = totalMarksAwarded;
	}
	public int getTotalMaxMarks() {
		return totalMaxMarks;
	}
	public void setTotalMaxMarks(int totalMaxMarks) {
		this.totalMaxMarks = totalMaxMarks;
	}
	public void setTotalCreditPoints(Double totalCreditPoints) {
		this.totalCreditPoints = totalCreditPoints;
	}
	public double getTotalGradePoints() {
		return totalGradePoints;
	}
	public void setTotalGradePoints(double totalGradePoints) {
		this.totalGradePoints = totalGradePoints;
	}

}
