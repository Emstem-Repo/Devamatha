package com.kp.cms.bo.exam;

@SuppressWarnings("serial")
public class ExamSupplStudentFinalMarkDetailsBO extends ExamGenBO {
	private int examId;
	private int studentId;
	private int classId;
	private int subjectId;
	private String subjectTheoryMark;
	private String subjectPracticalMark;
	private String studentTheoryMarks;
	private String studentPracticalMarks;
	private String passOrFail;
	private int chance;

	private ExamDefinitionBO examDefinitionBO;
	private StudentUtilBO studentUtilBO;
	private ClassUtilBO classUtilBO;
	private SubjectUtilBO subjectUtilBO;

	public ExamSupplStudentFinalMarkDetailsBO() {
		super();
	}

	public ExamSupplStudentFinalMarkDetailsBO(int examId, int studentId,
			int classId, int subjectId, String subjectTheoryMark,
			String subjectPracticalMark, String studentTheoryMarks,
			String studentPracticalMarks, String passOrFail, int chance) {
		super();
		this.examId = examId;
		this.studentId = studentId;
		this.classId = classId;
		this.subjectId = subjectId;
		this.subjectTheoryMark = subjectTheoryMark;
		this.subjectPracticalMark = subjectPracticalMark;
		this.studentTheoryMarks = studentTheoryMarks;
		this.studentPracticalMarks = studentPracticalMarks;
		this.passOrFail = passOrFail;
		this.chance = chance;
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

	public ExamDefinitionBO getExamDefinitionBO() {
		return examDefinitionBO;
	}

	public void setExamDefinitionBO(ExamDefinitionBO examDefinitionBO) {
		this.examDefinitionBO = examDefinitionBO;
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

	public void setChance(int chance) {
		this.chance = chance;
	}

	public int getChance() {
		return chance;
	}

}
