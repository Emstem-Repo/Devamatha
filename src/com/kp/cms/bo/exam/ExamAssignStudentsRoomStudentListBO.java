package com.kp.cms.bo.exam;

@SuppressWarnings("serial")
public class ExamAssignStudentsRoomStudentListBO extends ExamGenBO {

	private int assignStuRoomId;
	private int studentId;
	private int isPresent;

	private ExamAssignStudentsRoomBO examAssignStudentsRoomBO;
	private StudentUtilBO studentUtilBO;

	public ExamAssignStudentsRoomStudentListBO() {
		super();
	}

	public ExamAssignStudentsRoomStudentListBO(Integer id, int isPresent) {
		super();
		this.id = id;
		this.isPresent = isPresent;
	}

	public ExamAssignStudentsRoomStudentListBO(int assignStuRoomId,
			int studentId) {
		super();
		this.assignStuRoomId = assignStuRoomId;
		this.studentId = studentId;
	}

	public ExamAssignStudentsRoomStudentListBO(int assignStuRoomId,
			int studentId, int isPresent) {
		super();
		this.assignStuRoomId = assignStuRoomId;
		this.studentId = studentId;
		this.setIsPresent(isPresent);
	}

	public int getAssignStuRoomId() {
		return assignStuRoomId;
	}

	public void setAssignStuRoomId(int assignStuRoomId) {
		this.assignStuRoomId = assignStuRoomId;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public ExamAssignStudentsRoomBO getExamAssignStudentsRoomBO() {
		return examAssignStudentsRoomBO;
	}

	public void setExamAssignStudentsRoomBO(
			ExamAssignStudentsRoomBO examAssignStudentsRoomBO) {
		this.examAssignStudentsRoomBO = examAssignStudentsRoomBO;
	}

	public StudentUtilBO getStudentUtilBO() {
		return studentUtilBO;
	}

	public void setStudentUtilBO(StudentUtilBO studentUtilBO) {
		this.studentUtilBO = studentUtilBO;
	}

	public void setIsPresent(int isPresent) {
		this.isPresent = isPresent;
	}

	public int getIsPresent() {
		return isPresent;
	}

}
