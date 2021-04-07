package com.kp.cms.bo.exam;

import java.util.Date;

import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.Subject;

@SuppressWarnings("serial")
public class ExamStudentOverallInternalMarkDetailsBO extends ExamGenBO {

	private int examId;
	private int studentId;
	private int classId;
	private int subjectId;
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
	
	private ExamDefinitionBO examDefinitionBO;
	private ExamInternalExamTypeBO examInternalExamTypeBO;
	private StudentUtilBO studentUtilBO;
	private ClassUtilBO classUtilBO;
	private SubjectUtilBO subjectUtilBO;
	private Student student;
	private Subject subject;
	private Classes classes;

	public ExamStudentOverallInternalMarkDetailsBO() {
		super();
	}

	public ExamStudentOverallInternalMarkDetailsBO(int classId, int examId,
			String passOrFail, String practicalTotalAssignmentMarks,
			String practicalTotalAttendenceMarks,
			String practicalTotalSubInternalMarks, int studentId,
			int subjectId, String theoryTotalAssignmentMarks,
			String theoryTotalAttendenceMarks,
			String theoryTotalSubInternalMarks) {
		super();
		this.classId = classId;
		this.examId = examId;
		this.passOrFail = passOrFail;
		this.practicalTotalAssignmentMarks = practicalTotalAssignmentMarks;
		this.practicalTotalAttendenceMarks = practicalTotalAttendenceMarks;
		this.practicalTotalSubInternalMarks = practicalTotalSubInternalMarks;
		this.studentId = studentId;
		this.subjectId = subjectId;
		this.theoryTotalAssignmentMarks = theoryTotalAssignmentMarks;
		this.theoryTotalAttendenceMarks = theoryTotalAttendenceMarks;
		this.theoryTotalSubInternalMarks = theoryTotalSubInternalMarks;
	}
	
	

	
	
	
	public ExamStudentOverallInternalMarkDetailsBO(int examId, int studentId,
			int classId, int subjectId, String theoryTotalSubInternalMarks,
			String theoryTotalAttendenceMarks,
			String theoryTotalAssignmentMarks, String theoryTotalMarks,
			String practicalTotalSubInternalMarks,
			String practicalTotalAttendenceMarks,
			String practicalTotalAssignmentMarks, String practicalTotalMarks,
			String passOrFail, String comments, int masterId ) {
		super();
		this.examId = examId;
		this.studentId = studentId;
		this.classId = classId;
		this.subjectId = subjectId;
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

	
	
	
	
	
	
	
	
	public int getExamId() {
		return examId;
	}

	
	public void setExamId(int examId) {
		this.examId = examId;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public int getClassId() {
		return classId;
	}

	public void setClassId(int classId) {
		this.classId = classId;
	}

	public int getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
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

	public ExamDefinitionBO getExamDefinitionBO() {
		return examDefinitionBO;
	}

	public void setExamDefinitionBO(ExamDefinitionBO examDefinitionBO) {
		this.examDefinitionBO = examDefinitionBO;
	}

	public ExamInternalExamTypeBO getExamInternalExamTypeBO() {
		return examInternalExamTypeBO;
	}

	public void setExamInternalExamTypeBO(
			ExamInternalExamTypeBO examInternalExamTypeBO) {
		this.examInternalExamTypeBO = examInternalExamTypeBO;
	}

	public StudentUtilBO getStudentUtilBO() {
		return studentUtilBO;
	}

	public void setStudentUtilBO(StudentUtilBO studentUtilBO) {
		this.studentUtilBO = studentUtilBO;
	}

	public ClassUtilBO getClassUtilBO() {
		return classUtilBO;
	}

	public void setClassUtilBO(ClassUtilBO classUtilBO) {
		this.classUtilBO = classUtilBO;
	}

	public SubjectUtilBO getSubjectUtilBO() {
		return subjectUtilBO;
	}

	public void setSubjectUtilBO(SubjectUtilBO subjectUtilBO) {
		this.subjectUtilBO = subjectUtilBO;
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

}
