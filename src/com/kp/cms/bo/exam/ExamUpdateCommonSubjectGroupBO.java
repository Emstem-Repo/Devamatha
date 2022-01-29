package com.kp.cms.bo.exam;

/**
 * Feb 20, 2010 Created By 9Elements Team
 */
public class ExamUpdateCommonSubjectGroupBO extends ExamGenBO {
	
	private Integer classId;
	private Integer studentId;
	private Integer subjectGroupId;

	//many-to-one
	private ClassUtilBO classUtilBO;
	private StudentUtilBO studentUtilBO;
	private SubjectGroupUtilBO subjectGroupUtilBO;

	public ExamUpdateCommonSubjectGroupBO() {
		super();
	}

	public ExamUpdateCommonSubjectGroupBO(Integer classId, Integer studentId,
			Integer subjectGroupId) {
		super();
		this.classId = classId;
		this.studentId = studentId;
		this.subjectGroupId = subjectGroupId;
	}

	public Integer getClassId() {
		return classId;
	}

	public void setClassId(Integer classId) {
		this.classId = classId;
	}

	public Integer getStudentId() {
		return studentId;
	}

	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}

	public Integer getSubjectGroupId() {
		return subjectGroupId;
	}

	public void setSubjectGroupId(Integer subjectGroupId) {
		this.subjectGroupId = subjectGroupId;
	}

	public ClassUtilBO getClassUtilBO() {
		return classUtilBO;
	}

	public void setClassUtilBO(ClassUtilBO classUtilBO) {
		this.classUtilBO = classUtilBO;
	}

	public StudentUtilBO getStudentUtilBO() {
		return studentUtilBO;
	}

	public void setStudentUtilBO(StudentUtilBO studentUtilBO) {
		this.studentUtilBO = studentUtilBO;
	}

	public SubjectGroupUtilBO getSubjectGroupUtilBO() {
		return subjectGroupUtilBO;
	}

	public void setSubjectGroupUtilBO(SubjectGroupUtilBO subjectGroupUtilBO) {
		this.subjectGroupUtilBO = subjectGroupUtilBO;
	}

}
