package com.kp.cms.bo.exam;

@SuppressWarnings("serial")
public class ExamStudentSubInternalMarkDetailsBO extends ExamGenBO {

	private int studentId;
	private int classId;
	private int internalExamTypeId;
	private int subInternalMinMark;
	private int studentSubInternalMark;

	private StudentUtilBO studentUtilBO;
	private ClassUtilBO classUtilBO;
	private ExamInternalExamTypeBO examInternalExamTypeBO;

	public ExamStudentSubInternalMarkDetailsBO() {
		super();
	}

	public ExamStudentSubInternalMarkDetailsBO(int studentId, int classId,
			int internalExamTypeId, int subInternalMinMark,
			int studentSubInternalMark) {
		super();
		this.studentId = studentId;
		this.classId = classId;
		this.internalExamTypeId = internalExamTypeId;
		this.subInternalMinMark = subInternalMinMark;
		this.studentSubInternalMark = studentSubInternalMark;
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

	public int getInternalExamTypeId() {
		return internalExamTypeId;
	}

	public void setInternalExamTypeId(int internalExamTypeId) {
		this.internalExamTypeId = internalExamTypeId;
	}

	public int getSubInternalMinMark() {
		return subInternalMinMark;
	}

	public void setSubInternalMinMark(int subInternalMinMark) {
		this.subInternalMinMark = subInternalMinMark;
	}

	public int getStudentSubInternalMark() {
		return studentSubInternalMark;
	}

	public void setStudentSubInternalMark(int studentSubInternalMark) {
		this.studentSubInternalMark = studentSubInternalMark;
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

	public ExamInternalExamTypeBO getExamInternalExamTypeBO() {
		return examInternalExamTypeBO;
	}

	public void setExamInternalExamTypeBO(
			ExamInternalExamTypeBO examInternalExamTypeBO) {
		this.examInternalExamTypeBO = examInternalExamTypeBO;
	}

}
