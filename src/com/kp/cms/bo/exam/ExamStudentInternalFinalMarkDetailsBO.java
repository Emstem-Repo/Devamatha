package com.kp.cms.bo.exam;

@SuppressWarnings("serial")
public class ExamStudentInternalFinalMarkDetailsBO extends ExamGenBO {

	private int examId;
	private int internalExamTypeId;
	private int studentId;
	private int classId;
	private int subjectId;
	private String subjectTheoryInternalMark;
	private String subjectPracticalInternalMark;
	private String studentTheoryInternalMarks;
	private String studentPracticalInternalMarks;
	private String passOrFail;

	private ExamDefinitionBO examDefinitionBO;
	private ExamInternalExamTypeBO examInternalExamTypeBO;
	private StudentUtilBO studentUtilBO;
	private ClassUtilBO classUtilBO;
	private SubjectUtilBO subjectUtilBO;

	public ExamStudentInternalFinalMarkDetailsBO() {
		super();
	}

	public ExamStudentInternalFinalMarkDetailsBO(int examId,
			int internalExamTypeId, int studentId, int classId, int subjectId,
			String subjectTheoryInternalMark,
			String subjectPracticalInternalMark,
			String studentTheoryInternalMarks,
			String studentPracticalInternalMarks, String passOrFail) {
		super();
		this.examId = examId;
		this.internalExamTypeId = internalExamTypeId;
		this.studentId = studentId;
		this.classId = classId;
		this.subjectId = subjectId;
		this.subjectTheoryInternalMark = subjectTheoryInternalMark;
		this.subjectPracticalInternalMark = subjectPracticalInternalMark;
		this.studentTheoryInternalMarks = studentTheoryInternalMarks;
		this.studentPracticalInternalMarks = studentPracticalInternalMarks;
		this.passOrFail = passOrFail;
	}

	public int getExamId() {
		return examId;
	}

	public void setExamId(int examId) {
		this.examId = examId;
	}

	public int getInternalExamTypeId() {
		return internalExamTypeId;
	}

	public void setInternalExamTypeId(int internalExamTypeId) {
		this.internalExamTypeId = internalExamTypeId;
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

	public String getSubjectTheoryInternalMark() {
		return subjectTheoryInternalMark;
	}

	public void setSubjectTheoryInternalMark(String subjectTheoryInternalMark) {
		this.subjectTheoryInternalMark = subjectTheoryInternalMark;
	}

	public String getSubjectPracticalInternalMark() {
		return subjectPracticalInternalMark;
	}

	public void setSubjectPracticalInternalMark(
			String subjectPracticalInternalMark) {
		this.subjectPracticalInternalMark = subjectPracticalInternalMark;
	}

	public String getStudentTheoryInternalMarks() {
		return studentTheoryInternalMarks;
	}

	public void setStudentTheoryInternalMarks(String studentTheoryInternalMarks) {
		this.studentTheoryInternalMarks = studentTheoryInternalMarks;
	}

	public String getStudentPracticalInternalMarks() {
		return studentPracticalInternalMarks;
	}

	public void setStudentPracticalInternalMarks(
			String studentPracticalInternalMarks) {
		this.studentPracticalInternalMarks = studentPracticalInternalMarks;
	}

	public String getPassOrFail() {
		return passOrFail;
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

}
