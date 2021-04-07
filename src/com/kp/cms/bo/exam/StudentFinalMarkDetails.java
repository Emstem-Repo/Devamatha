package com.kp.cms.bo.exam;

import java.io.Serializable;
import java.util.Date;

import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.Subject;

public class StudentFinalMarkDetails implements Serializable {
	
	private int id;
	private ExamDefinition exam;
	private Student student;
	private Classes classes;
	private Subject subject;
	private String subjectTheoryMark;
	private String subjectPracticalMark;
	private String studentTheoryMarks;
	private String studentPracticalMarks;
	private String studentTheoryMarksBeforeGrace;
	private String passOrFail;
	private String comments;
	private String createdBy;
	private String modifiedBy;
	private Date createdDate;
	private Date lastModifiedDate;
	private Boolean isGracing;
	private Boolean isRevaluationModeration;

	public StudentFinalMarkDetails() {
		super();
	}
	


	public StudentFinalMarkDetails(int id, ExamDefinition exam,
			Student student, Classes classes, Subject subject,
			String subjectTheoryMark, String subjectPracticalMark,
			String studentTheoryMarks, String studentPracticalMarks,
			String passOrFail, String comments, String createdBy,
			String modifiedBy, Date createdDate, Date lastModifiedDate, Boolean isGracing) {
		super();
		this.id = id;
		this.exam = exam;
		this.student = student;
		this.classes = classes;
		this.subject = subject;
		this.subjectTheoryMark = subjectTheoryMark;
		this.subjectPracticalMark = subjectPracticalMark;
		this.studentTheoryMarks = studentTheoryMarks;
		this.studentPracticalMarks = studentPracticalMarks;
		this.passOrFail = passOrFail;
		this.comments = comments;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
		this.isGracing=isGracing;
	}



	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public ExamDefinition getExam() {
		return exam;
	}
	public void setExam(ExamDefinition exam) {
		this.exam = exam;
	}
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public String getSubjectTheoryMark() {
		return subjectTheoryMark;
	}
	public void setSubjectTheoryMark(String subjectTheoryMark) {
		this.subjectTheoryMark = subjectTheoryMark;
	}
	public String getSubjectPracticalMark() {
		return subjectPracticalMark;
	}
	public void setSubjectPracticalMark(String subjectPracticalMark) {
		this.subjectPracticalMark = subjectPracticalMark;
	}
	public String getStudentTheoryMarks() {
		return studentTheoryMarks;
	}
	public void setStudentTheoryMarks(String studentTheoryMarks) {
		this.studentTheoryMarks = studentTheoryMarks;
	}
	public String getStudentPracticalMarks() {
		return studentPracticalMarks;
	}
	public void setStudentPracticalMarks(String studentPracticalMarks) {
		this.studentPracticalMarks = studentPracticalMarks;
	}
	public String getPassOrFail() {
		return passOrFail;
	}
	public void setPassOrFail(String passOrFail) {
		this.passOrFail = passOrFail;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
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



	public Classes getClasses() {
		return classes;
	}



	public void setClasses(Classes classes) {
		this.classes = classes;
	}



	public Subject getSubject() {
		return subject;
	}



	public void setSubject(Subject subject) {
		this.subject = subject;
	}



	public Boolean getIsGracing() {
		return isGracing;
	}



	public void setIsGracing(Boolean isGracing) {
		this.isGracing = isGracing;
	}



	public void setIsRevaluationModeration(Boolean isRevaluationModeration) {
		this.isRevaluationModeration = isRevaluationModeration;
	}



	public Boolean getIsRevaluationModeration() {
		return isRevaluationModeration;
	}



	public void setStudentTheoryMarksBeforeGrace(
			String studentTheoryMarksBeforeGrace) {
		this.studentTheoryMarksBeforeGrace = studentTheoryMarksBeforeGrace;
	}



	public String getStudentTheoryMarksBeforeGrace() {
		return studentTheoryMarksBeforeGrace;
	}
}