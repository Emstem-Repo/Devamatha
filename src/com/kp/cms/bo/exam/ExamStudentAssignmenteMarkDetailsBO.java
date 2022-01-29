package com.kp.cms.bo.exam;

@SuppressWarnings("serial")
public class ExamStudentAssignmenteMarkDetailsBO extends ExamGenBO {

	private Integer studentId;
	private Integer classId;
	private Integer assignmentMinMark;
	private Integer studentassignmentMark;

	private StudentUtilBO studentUtilBO;
	private ClassUtilBO classUtilBO;

	public ExamStudentAssignmenteMarkDetailsBO() {
		super();
	}

	public ExamStudentAssignmenteMarkDetailsBO(Integer studentId, Integer classId,
			Integer assignmentMinMark, Integer studentassignmentMark) {
		super();
		this.studentId = studentId;
		this.classId = classId;
		this.assignmentMinMark = assignmentMinMark;
		this.studentassignmentMark = studentassignmentMark;
	}

	public Integer getStudentId() {
		return studentId;
	}

	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}

	public Integer getClassId() {
		return classId;
	}

	public void setClassId(Integer classId) {
		this.classId = classId;
	}

	public Integer getAssignmentMinMark() {
		return assignmentMinMark;
	}

	public void setAssignmentMinMark(Integer assignmentMinMark) {
		this.assignmentMinMark = assignmentMinMark;
	}

	public Integer getStudentassignmentMark() {
		return studentassignmentMark;
	}

	public void setStudentassignmentMark(Integer studentassignmentMark) {
		this.studentassignmentMark = studentassignmentMark;
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

}
