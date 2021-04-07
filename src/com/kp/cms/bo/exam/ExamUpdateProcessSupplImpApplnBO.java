package com.kp.cms.bo.exam;

@SuppressWarnings("serial")
public class ExamUpdateProcessSupplImpApplnBO extends ExamGenBO {

	private Integer examId;
	private Integer studentId;
	private Integer classId;
	private Integer subjectId;
	private String subjectTheoryMark;
	private String subjectPracticalMark;
	private String studentTheoryMark;
	private String studentPracticalMark;
	private String subjectTheoryMarkInternal;
	private String subjectPracticalMarkInternal;
	private String studentTheoryMarkInternal;
	private String studentPracticalMarkInternal;
	private Integer chance;
	private String passOrFail;

	private ExamDefinitionBO examDefinitionBO;
	private StudentUtilBO studentUtilBO;
	private ClassUtilBO classUtilBO;
	private SubjectUtilBO subjectUtilBO;

	public ExamUpdateProcessSupplImpApplnBO() {
		super();
	}

	public ExamUpdateProcessSupplImpApplnBO(Integer examId, Integer studentId,
			Integer classId, Integer subjectId, String subjectTheoryMark,
			String subjectPracticalMark, String studentTheoryMark,
			String studentPracticalMark, String subjectTheoryMarkInternal,
			String subjectPracticalMarkInternal,
			String studentTheoryMarkInternal,
			String studentPracticalMarkInternal, String passOrFail) {
		super();
		this.examId = examId;
		this.studentId = studentId;
		this.classId = classId;
		this.subjectId = subjectId;
		this.subjectTheoryMark = subjectTheoryMark;
		this.subjectPracticalMark = subjectPracticalMark;
		this.studentTheoryMark = studentTheoryMark;
		this.studentPracticalMark = studentPracticalMark;
		this.subjectTheoryMarkInternal = subjectTheoryMarkInternal;
		this.subjectPracticalMarkInternal = subjectPracticalMarkInternal;
		this.studentTheoryMarkInternal = studentTheoryMarkInternal;
		this.studentPracticalMarkInternal = studentPracticalMarkInternal;
		this.passOrFail = passOrFail;
	}

	public ExamUpdateProcessSupplImpApplnBO(Integer examId, Integer studentId,
			Integer classId, Integer subjectId, String subjectTheoryMark,
			String subjectPracticalMark, String studentTheoryMark,
			String studentPracticalMark, String subjectTheoryMarkInternal,
			String subjectPracticalMarkInternal,
			String studentTheoryMarkInternal,
			String studentPracticalMarkInternal, Integer chance,
			String passOrFail) {
		super();
		this.examId = examId;
		this.studentId = studentId;
		this.classId = classId;
		this.subjectId = subjectId;
		this.subjectTheoryMark = subjectTheoryMark;
		this.subjectPracticalMark = subjectPracticalMark;
		this.studentTheoryMark = studentTheoryMark;
		this.studentPracticalMark = studentPracticalMark;
		this.subjectTheoryMarkInternal = subjectTheoryMarkInternal;
		this.subjectPracticalMarkInternal = subjectPracticalMarkInternal;
		this.studentTheoryMarkInternal = studentTheoryMarkInternal;
		this.studentPracticalMarkInternal = studentPracticalMarkInternal;
		this.chance = chance;
		this.passOrFail = passOrFail;
	}

	public Integer getExamId() {
		return examId;
	}

	public void setExamId(Integer examId) {
		this.examId = examId;
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

	public Integer getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Integer subjectId) {
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

	public String getStudentTheoryMark() {
		return studentTheoryMark;
	}

	public void setStudentTheoryMark(String studentTheoryMark) {
		this.studentTheoryMark = studentTheoryMark;
	}

	public String getStudentPracticalMark() {
		return studentPracticalMark;
	}

	public void setStudentPracticalMark(String studentPracticalMark) {
		this.studentPracticalMark = studentPracticalMark;
	}

	public String getSubjectTheoryMarkInternal() {
		return subjectTheoryMarkInternal;
	}

	public void setSubjectTheoryMarkInternal(String subjectTheoryMarkInternal) {
		this.subjectTheoryMarkInternal = subjectTheoryMarkInternal;
	}

	public String getSubjectPracticalMarkInternal() {
		return subjectPracticalMarkInternal;
	}

	public void setSubjectPracticalMarkInternal(
			String subjectPracticalMarkInternal) {
		this.subjectPracticalMarkInternal = subjectPracticalMarkInternal;
	}

	public String getStudentTheoryMarkInternal() {
		return studentTheoryMarkInternal;
	}

	public void setStudentTheoryMarkInternal(String studentTheoryMarkInternal) {
		this.studentTheoryMarkInternal = studentTheoryMarkInternal;
	}

	public String getStudentPracticalMarkInternal() {
		return studentPracticalMarkInternal;
	}

	public void setStudentPracticalMarkInternal(
			String studentPracticalMarkInternal) {
		this.studentPracticalMarkInternal = studentPracticalMarkInternal;
	}

	public Integer getChance() {
		return chance;
	}

	public void setChance(Integer chance) {
		this.chance = chance;
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

}
