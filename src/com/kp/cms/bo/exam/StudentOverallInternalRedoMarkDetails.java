package com.kp.cms.bo.exam;

import java.io.Serializable;
import java.util.Date;

import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.Subject;

public class StudentOverallInternalRedoMarkDetails implements Serializable {
	
	private int id;
	private ExamDefinition exam;
	private StudentOverallInternalMarkDetails studentOverallInternalMarkDetails;
	private Student student;
	private Classes classes;
	private Subject subject;
	private String theoryTotalMarks;
	private String practicalTotalMarks;
	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
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

	public StudentOverallInternalRedoMarkDetails() {
		super();
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

	public StudentOverallInternalMarkDetails getStudentOverallInternalMarkDetails() {
		return studentOverallInternalMarkDetails;
	}

	public void setStudentOverallInternalMarkDetails(
			StudentOverallInternalMarkDetails studentOverallInternalMarkDetails) {
		this.studentOverallInternalMarkDetails = studentOverallInternalMarkDetails;
	}

	public String getTheoryTotalMarks() {
		return theoryTotalMarks;
	}

	public void setTheoryTotalMarks(String theoryTotalMarks) {
		this.theoryTotalMarks = theoryTotalMarks;
	}

	public String getPracticalTotalMarks() {
		return practicalTotalMarks;
	}

	public void setPracticalTotalMarks(String practicalTotalMarks) {
		this.practicalTotalMarks = practicalTotalMarks;
	}

	
}