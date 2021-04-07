package com.kp.cms.bo.exam;

/**
 * Mar 1, 2010 Created By 9Elements Team
 */
import java.math.BigDecimal;
import java.util.Date;

@SuppressWarnings("serial")
public class ExamAssignOverallMarksBO extends ExamGenBO {

	private Integer examId;
	private int courseId;
	private int subjectId;
	private int studentId;
	private Integer isOverallAssignment;
	private String overallAssignmentName;
	private BigDecimal theoryMarks;
	private BigDecimal practicalMarks;

	// Many-to-One
	private ExamDefinitionBO examDefinitionBO;
	private ExamCourseUtilBO examCourseUtilBO;
	private SubjectUtilBO subjectUtilBO;
	private StudentUtilBO studentUtilBO;

	public ExamAssignOverallMarksBO() {
		super();
	}

	public ExamAssignOverallMarksBO(Integer examId, int courseId,
			int subjectId, int studentId, Integer isOverallAssignment,
			String overallAssignmentName, BigDecimal theoryMarks,
			BigDecimal practicalMarks, String userID) {
		super();
		this.examId = examId;
		this.courseId = courseId;
		this.subjectId = subjectId;
		this.studentId = studentId;
		this.isOverallAssignment = isOverallAssignment;
		this.overallAssignmentName = overallAssignmentName;
		this.setTheoryMarks(theoryMarks);
		this.setPracticalMarks(practicalMarks);
		this.createdBy = userID;
		this.createdDate = new Date();
		this.modifiedBy = userID;
		this.lastModifiedDate = new Date();
		this.isActive = true;
	}

	public Integer getExamId() {
		return examId;
	}

	public void setExamId(Integer examId) {
		this.examId = examId;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public int getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public Integer getIsOverallAssignment() {
		return isOverallAssignment;
	}

	public void setIsOverallAssignment(Integer isOverallAssignment) {
		this.isOverallAssignment = isOverallAssignment;
	}

	public String getOverallAssignmentName() {
		return overallAssignmentName;
	}

	public void setOverallAssignmentName(String overallAssignmentName) {
		this.overallAssignmentName = overallAssignmentName;
	}

	public ExamDefinitionBO getExamDefinitionBO() {
		return examDefinitionBO;
	}

	public void setExamDefinitionBO(ExamDefinitionBO examDefinitionBO) {
		this.examDefinitionBO = examDefinitionBO;
	}

	public ExamCourseUtilBO getExamCourseUtilBO() {
		return examCourseUtilBO;
	}

	public void setExamCourseUtilBO(ExamCourseUtilBO examCourseUtilBO) {
		this.examCourseUtilBO = examCourseUtilBO;
	}

	public SubjectUtilBO getSubjectUtilBO() {
		return subjectUtilBO;
	}

	public void setSubjectUtilBO(SubjectUtilBO subjectUtilBO) {
		this.subjectUtilBO = subjectUtilBO;
	}

	public StudentUtilBO getStudentUtilBO() {
		return studentUtilBO;
	}

	public void setStudentUtilBO(StudentUtilBO studentUtilBO) {
		this.studentUtilBO = studentUtilBO;
	}

	public void setTheoryMarks(BigDecimal theoryMarks) {
		this.theoryMarks = theoryMarks;
	}

	public BigDecimal getTheoryMarks() {
		return theoryMarks;
	}

	public void setPracticalMarks(BigDecimal practicalMarks) {
		this.practicalMarks = practicalMarks;
	}

	public BigDecimal getPracticalMarks() {
		return practicalMarks;
	}

}
