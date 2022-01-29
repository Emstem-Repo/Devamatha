package com.kp.cms.bo.exam;

/**
 * Mar 2, 2010 Created By 9Elements Team
 */
@SuppressWarnings("serial")
public class ExamInternalRetestApplicationBO extends ExamGenBO {

	private String academicYear;
	private Integer examId;
	private Integer studentId;
	private Integer classId;
	private Integer chance;

	private ExamDefinitionBO examDefinitionBO;
	private StudentUtilBO studentUtilBO;
	private ClassUtilBO classUtilBO;

	public ExamInternalRetestApplicationBO() {
		super();
	}

	public ExamInternalRetestApplicationBO(String academicYear, Integer examId,
			Integer studentId, Integer classId, Integer chance) {
		super();
		this.academicYear = academicYear;
		this.examId = examId;
		this.studentId = studentId;
		this.classId = classId;
		this.chance = chance;
		this.isActive = true;
	}

	public String getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
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

	public Integer getChance() {
		return chance;
	}

	public void setChance(Integer chance) {
		this.chance = chance;
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

}
