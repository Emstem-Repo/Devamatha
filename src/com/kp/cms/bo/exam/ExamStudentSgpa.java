package com.kp.cms.bo.exam;

import java.util.Date;

import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.Student;

public class ExamStudentSgpa implements java.io.Serializable {
	private Integer id;
	private Student student;
	private Course course;
	private Integer schemeNo;
	private Classes classes;
	private Double sgpa;
	private int credit;
	private Double creditGradePoint;
	private String result;
	private String grade;
	private int appliedYear;
	private int year;
	private int month;
	private int totalMarksAwarded;
	private int totalMaxMarks;
	private String totalGraceMarks;
	private boolean isGraced;
	private String createdBy;;
	private Date createdDate;
	
	
	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	private int getAppliedYear() {
		return appliedYear;
	}

	public void setAppliedYear(int appliedYear) {
		this.appliedYear = appliedYear;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public ExamStudentSgpa(){
		
	}
	
	public ExamStudentSgpa(Integer id, Student student, Integer schemeNo, 
			Classes classes, Double sgpa, int credit, Double creditGradePoint) {
		super();
		this.id = id;
		this.student = student;
		this.schemeNo = schemeNo;
		this.classes = classes;
		this.sgpa = sgpa;
		this.credit = credit;
		this.creditGradePoint = creditGradePoint;
	}
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public Integer getSchemeNo() {
		return schemeNo;
	}
	public void setSchemeNo(Integer schemeNo) {
		this.schemeNo = schemeNo;
	}


	public Classes getClasses() {
		return classes;
	}


	public void setClasses(Classes classes) {
		this.classes = classes;
	}

	public Double getSgpa() {
		return sgpa;
	}

	public void setSgpa(Double sgpa) {
		this.sgpa = sgpa;
	}

	public int getCredit() {
		return credit;
	}

	public void setCredit(int credit) {
		this.credit = credit;
	}

	public Double getCreditGradePoint() {
		return creditGradePoint;
	}

	public void setCreditGradePoint(Double creditGradePoint) {
		this.creditGradePoint = creditGradePoint;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
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

	public boolean getIsGraced() {
		return isGraced;
	}

	public void setIsGraced(boolean isGraced) {
		this.isGraced = isGraced;
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

	public String getTotalGraceMarks() {
		return totalGraceMarks;
	}

	public void setTotalGraceMarks(String totalGraceMarks) {
		this.totalGraceMarks = totalGraceMarks;
	}
	
}
