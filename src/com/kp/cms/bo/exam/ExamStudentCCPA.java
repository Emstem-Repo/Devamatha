package com.kp.cms.bo.exam;

import java.util.Date;

import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.Student;

public class ExamStudentCCPA implements java.io.Serializable {
	private Integer id;
	private Student student;
	private Course course;
	private Double ccpa;
	private int credit;
	private double creditGradePoint;
	private String result;
	private String grade;
	private int appliedYear;
	private String createdBy;;
	private String modifiedBy;
	private Date createdDate;
	private Date lastModifiedDate;
	private int passOutMonth;
	private int passOutYear;
	private int totalMarksAwarded;
	private int totalMaxMarks;
	private String rank;
	private String totalGraceMarks;
	private boolean isGraced;
	private boolean isFailed;
	private boolean isExraCreditFailed;
	
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

	public Double getCcpa() {
		return ccpa;
	}

	public void setCcpa(Double ccpa) {
		this.ccpa = ccpa;
	}
	
	public int getAppliedYear() {
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

	public ExamStudentCCPA(){
		
	}
	
	public ExamStudentCCPA(Integer id, Student student, Integer schemeNo, 
			Classes classes, Double sgpa, int credit, Double creditGradePoint) {
		super();
		this.id = id;
		this.student = student;
		this.credit = credit;
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
	public int getCredit() {
		return credit;
	}

	public void setCredit(int credit) {
		this.credit = credit;
	}


	public double getCreditGradePoint() {
		return creditGradePoint;
	}

	public void setCreditGradePoint(double creditGradePoint) {
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

	public int getPassOutMonth() {
		return passOutMonth;
	}

	public void setPassOutMonth(int passOutMonth) {
		this.passOutMonth = passOutMonth;
	}

	public int getPassOutYear() {
		return passOutYear;
	}

	public void setPassOutYear(int passOutYear) {
		this.passOutYear = passOutYear;
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
	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public boolean getIsGraced() {
		return isGraced;
	}

	public void setIsGraced(boolean isGraced) {
		this.isGraced = isGraced;
	}

	public String getTotalGraceMarks() {
		return totalGraceMarks;
	}

	public void setTotalGraceMarks(String totalGraceMarks) {
		this.totalGraceMarks = totalGraceMarks;
	}

	public Boolean getIsFailed() {
		return isFailed;
	}

	public void setIsFailed(Boolean isFailed) {
		this.isFailed = isFailed;
	}

	public Boolean getIsExraCreditFailed() {
		return isExraCreditFailed;
	}

	public void setIsExraCreditFailed(Boolean isExraCreditFailed) {
		this.isExraCreditFailed = isExraCreditFailed;
	}

	

	
}
