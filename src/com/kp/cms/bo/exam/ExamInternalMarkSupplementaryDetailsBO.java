package com.kp.cms.bo.exam;

import java.util.Date;

import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.Subject;

public class ExamInternalMarkSupplementaryDetailsBO extends ExamGenBO{
	
	private String theoryTotalSubInternalMarks;
	private String theoryTotalAttendenceMarks;
	private String theoryTotalAssignmentMarks;
	private String practicalTotalSubInternalMarks;
	private String practicalTotalAttendenceMarks;
	private String practicalTotalAssignmentMarks;
	private String passOrFail;
	private String comments;
	private String theoryTotalMarks;
	private String practicalTotalMarks;
	
	private Student student;
	private Subject subject;
	private Classes classes;
	private ExamDefinitionBO examDefinitionBO;

	public ExamInternalMarkSupplementaryDetailsBO() {
		super();
	}

	public ExamInternalMarkSupplementaryDetailsBO(String theoryTotalSubInternalMarks,
			String theoryTotalAttendenceMarks,
			String theoryTotalAssignmentMarks, String theoryTotalMarks,
			String practicalTotalSubInternalMarks,
			String practicalTotalAttendenceMarks,
			String practicalTotalAssignmentMarks, String practicalTotalMarks,
			String passOrFail, String comments, int masterId ) {
		super();
		this.theoryTotalSubInternalMarks = theoryTotalSubInternalMarks;
		this.theoryTotalAttendenceMarks = theoryTotalAttendenceMarks;
		this.theoryTotalAssignmentMarks = theoryTotalAssignmentMarks;
		this.theoryTotalMarks = theoryTotalMarks;
		this.practicalTotalSubInternalMarks = practicalTotalSubInternalMarks;
		this.practicalTotalAttendenceMarks = practicalTotalAttendenceMarks;
		this.practicalTotalAssignmentMarks = practicalTotalAssignmentMarks;
		this.practicalTotalMarks = practicalTotalMarks;
		this.passOrFail = passOrFail;
		this.comments = comments;
		this.lastModifiedDate=new Date();
		this.id = masterId;
	}
	public String getPassOrFail() {
		return passOrFail;
	}

	public String getTheoryTotalSubInternalMarks() {
		return theoryTotalSubInternalMarks;
	}

	public void setTheoryTotalSubInternalMarks(
			String theoryTotalSubInternalMarks) {
		this.theoryTotalSubInternalMarks = theoryTotalSubInternalMarks;
	}

	public String getTheoryTotalAttendenceMarks() {
		return theoryTotalAttendenceMarks;
	}

	public void setTheoryTotalAttendenceMarks(String theoryTotalAttendenceMarks) {
		this.theoryTotalAttendenceMarks = theoryTotalAttendenceMarks;
	}

	public String getTheoryTotalAssignmentMarks() {
		return theoryTotalAssignmentMarks;
	}

	public void setTheoryTotalAssignmentMarks(String theoryTotalAssignmentMarks) {
		this.theoryTotalAssignmentMarks = theoryTotalAssignmentMarks;
	}

	public String getPracticalTotalSubInternalMarks() {
		return practicalTotalSubInternalMarks;
	}

	public void setPracticalTotalSubInternalMarks(
			String practicalTotalSubInternalMarks) {
		this.practicalTotalSubInternalMarks = practicalTotalSubInternalMarks;
	}

	public String getPracticalTotalAttendenceMarks() {
		return practicalTotalAttendenceMarks;
	}

	public void setPracticalTotalAttendenceMarks(
			String practicalTotalAttendenceMarks) {
		this.practicalTotalAttendenceMarks = practicalTotalAttendenceMarks;
	}

	public String getPracticalTotalAssignmentMarks() {
		return practicalTotalAssignmentMarks;
	}

	public void setPracticalTotalAssignmentMarks(
			String practicalTotalAssignmentMarks) {
		this.practicalTotalAssignmentMarks = practicalTotalAssignmentMarks;
	}

	public void setPassOrFail(String passOrFail) {
		this.passOrFail = passOrFail;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getComments() {
		return comments;
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

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public Classes getClasses() {
		return classes;
	}

	public void setClasses(Classes classes) {
		this.classes = classes;
	}

	public ExamDefinitionBO getExamDefinitionBO() {
		return examDefinitionBO;
	}

	public void setExamDefinitionBO(ExamDefinitionBO examDefinitionBO) {
		this.examDefinitionBO = examDefinitionBO;
	}
}
