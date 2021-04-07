package com.kp.cms.bo.exam;

import java.util.Set;

/**
 * Jan 1, 2010 Created By 9Elements Team
 */
public class ClassSchemewiseUtilBO extends ExamGenBO {

	private int classId;
	private Set<StudentUtilBO> studentUtilBOSet;
	private CurriculumSchemeDurationUtilBO curriculumSchemeDurationUtilBO;
	private ClassUtilBO classUtilBO;
	
	public ClassSchemewiseUtilBO() {
	}

	public int getClassId() {
		return classId;
	}

	public void setClassId(int classId) {
		this.classId = classId;
	}

	public Set<StudentUtilBO> getStudentUtilBOSet() {
		return studentUtilBOSet;
	}

	public void setStudentUtilBOSet(Set<StudentUtilBO> studentUtilBOSet) {
		this.studentUtilBOSet = studentUtilBOSet;
	}

	public void setCurriculumSchemeDurationUtilBO(
			CurriculumSchemeDurationUtilBO curriculumSchemeDurationUtilBO) {
		this.curriculumSchemeDurationUtilBO = curriculumSchemeDurationUtilBO;
	}

	public CurriculumSchemeDurationUtilBO getCurriculumSchemeDurationUtilBO() {
		return curriculumSchemeDurationUtilBO;
	}

	public void setClassUtilBO(ClassUtilBO classUtilBO) {
		this.classUtilBO = classUtilBO;
	}

	public ClassUtilBO getClassUtilBO() {
		return classUtilBO;
	}

	
}
