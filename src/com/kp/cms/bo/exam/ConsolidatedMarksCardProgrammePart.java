package com.kp.cms.bo.exam;

import java.math.BigDecimal;
import java.util.Date;

import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.SubjectType;

public class ConsolidatedMarksCardProgrammePart 
{
	private int id;
	private Student student;
	private SubjectType examSubjectSection;
	private ConsolidatedSubjectStream subjectStream;
	private double maxMarksObtained;
	private BigDecimal maxMarksAwarded;
	private String creditPoints;
	private int credit;
	private String ccpa;
	private String grade;
	private String createdBy;
	private Date createdDate;
	private Course course;
	private int appliedYear;
	private boolean isFailed;
	
	public ConsolidatedMarksCardProgrammePart() {
		super();
	}
	
	public ConsolidatedMarksCardProgrammePart(int id, Student student,
			ConsolidatedSubjectStream subjectStream, double maxMarksObtained,
			BigDecimal maxMarksAwarded, String creditPoints, int credit,
			String ccpa, String grade, String createdBy, Date createdDate,
			Course course, int appliedYear) {
		super();
		this.id = id;
		this.student = student;
		this.subjectStream = subjectStream;
		this.maxMarksObtained = maxMarksObtained;
		this.maxMarksAwarded = maxMarksAwarded;
		this.creditPoints = creditPoints;
		this.credit = credit;
		this.ccpa = ccpa;
		this.grade = grade;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.course = course;
		this.appliedYear = appliedYear;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public SubjectType getExamSubjectSection() {
		return examSubjectSection;
	}
	public void setExamSubjectSection(SubjectType examSubjectSection) {
		this.examSubjectSection = examSubjectSection;
	}
	public ConsolidatedSubjectStream getSubjectStream() {
		return subjectStream;
	}
	public void setSubjectStream(ConsolidatedSubjectStream subjectStream) {
		this.subjectStream = subjectStream;
	}
	public double getMaxMarksObtained() {
		return maxMarksObtained;
	}
	public void setMaxMarksObtained(double maxMarksObtained) {
		this.maxMarksObtained = maxMarksObtained;
	}
	public BigDecimal getMaxMarksAwarded() {
		return maxMarksAwarded;
	}
	public void setMaxMarksAwarded(BigDecimal maxMarksAwarded) {
		this.maxMarksAwarded = maxMarksAwarded;
	}
	public String getCreditPoints() {
		return creditPoints;
	}
	public void setCreditPoints(String creditPoints) {
		this.creditPoints = creditPoints;
	}
	public int getCredit() {
		return credit;
	}
	public void setCredit(int credit) {
		this.credit = credit;
	}
	public String getCcpa() {
		return ccpa;
	}
	public void setCcpa(String ccpa) {
		this.ccpa = ccpa;
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
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	public int getAppliedYear() {
		return appliedYear;
	}
	public void setAppliedYear(int appliedYear) {
		this.appliedYear = appliedYear;
	}

	public boolean getIsFailed() {
		return isFailed;
	}

	public void setIsFailed(boolean isFailed) {
		this.isFailed = isFailed;
	}
	
}
