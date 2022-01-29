package com.kp.cms.bo.exam;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.Subject;

public class StudentOverallInternalMarkDetails implements Serializable {
	
	private int id;
	private ExamDefinition exam;
	private Student student;
	private Classes classes;
	private Subject subject;
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
	private String theoryBeforeGracing;
	private Date lastModifiedDate;
	private Boolean isGracing;
	private boolean isInternalRedo;
	private String previousMark;
	Set<StudentOverallInternalRedoMarkDetails> studentOverallInternalMarkDetailsSet= new HashSet<StudentOverallInternalRedoMarkDetails>();
	private ExamDefinition examForRedo;
	
	public StudentOverallInternalMarkDetails() {
		super();
	}
	public StudentOverallInternalMarkDetails(int id, ExamDefinition exam,
			Student student, Classes classes, Subject subject,
			String theoryTotalSubInternalMarks,
			String theoryTotalAttendenceMarks,
			String theoryTotalAssignmentMarks,
			String practicalTotalSubInternalMarks,
			String practicalTotalAttendenceMarks,
			String practicalTotalAssignmentMarks, String passOrFail,
			String comments, String theoryTotalMarks,
			String practicalTotalMarks, Date lastModifiedDate, Boolean isGracing,
			boolean isInternalRedo, String previousMark, ExamDefinition examForRedo) {
		super();
		this.id = id;
		this.exam = exam;
		this.student = student;
		this.classes = classes;
		this.subject = subject;
		this.theoryTotalSubInternalMarks = theoryTotalSubInternalMarks;
		this.theoryTotalAttendenceMarks = theoryTotalAttendenceMarks;
		this.theoryTotalAssignmentMarks = theoryTotalAssignmentMarks;
		this.practicalTotalSubInternalMarks = practicalTotalSubInternalMarks;
		this.practicalTotalAttendenceMarks = practicalTotalAttendenceMarks;
		this.practicalTotalAssignmentMarks = practicalTotalAssignmentMarks;
		this.passOrFail = passOrFail;
		this.comments = comments;
		this.theoryTotalMarks = theoryTotalMarks;
		this.practicalTotalMarks = practicalTotalMarks;
		this.lastModifiedDate = lastModifiedDate;
		this.isGracing = isGracing;
		this.isInternalRedo = isInternalRedo;
		this.previousMark = previousMark;
		this.examForRedo = examForRedo;
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
	public String getTheoryTotalSubInternalMarks() {
		return theoryTotalSubInternalMarks;
	}
	public void setTheoryTotalSubInternalMarks(String theoryTotalSubInternalMarks) {
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
	public void setTheoryBeforeGracing(String theoryBeforeGracing) {
		this.theoryBeforeGracing = theoryBeforeGracing;
	}
	public String getTheoryBeforeGracing() {
		return theoryBeforeGracing;
	}
	public boolean getIsInternalRedo() {
		return isInternalRedo;
	}
	public void setIsInternalRedo(boolean isInternalRedo) {
		this.isInternalRedo = isInternalRedo;
	}
	public String getPreviousMark() {
		return previousMark;
	}
	public void setPreviousMark(String previousMark) {
		this.previousMark = previousMark;
	}
	public Set<StudentOverallInternalRedoMarkDetails> getStudentOverallInternalMarkDetailsSet() {
		return studentOverallInternalMarkDetailsSet;
	}
	public void setStudentOverallInternalMarkDetailsSet(Set<StudentOverallInternalRedoMarkDetails> studentOverallInternalMarkDetailsSet) {
		this.studentOverallInternalMarkDetailsSet = studentOverallInternalMarkDetailsSet;
	}
	public ExamDefinition getExamForRedo() {
		return examForRedo;
	}
	public void setExamForRedo(ExamDefinition examForRedo) {
		this.examForRedo = examForRedo;
	}
}