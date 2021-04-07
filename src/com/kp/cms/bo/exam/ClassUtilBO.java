package com.kp.cms.bo.exam;

import java.util.Set;

/**
 * Jan 1, 2010 Created By 9Elements Team
 */
public class ClassUtilBO extends ExamGenBO {

	private String sectionName;
	private int courseId;
	private Integer termNumber;
	private ExamCourseUtilBO examCourseUtilBO;
	private Set<ClassSchemewiseUtilBO> classSchemewiseUtilBOSet;

	public ClassUtilBO() {
	}

	public Set<ClassSchemewiseUtilBO> getClassSchemewiseUtilBOSet() {
		return classSchemewiseUtilBOSet;
	}

	public void setClassSchemewiseUtilBOSet(
			Set<ClassSchemewiseUtilBO> classSchemewiseUtilBOSet) {
		this.classSchemewiseUtilBOSet = classSchemewiseUtilBOSet;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	public String getSectionName() {
		return sectionName;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setExamCourseUtilBO(ExamCourseUtilBO examCourseUtilBO) {
		this.examCourseUtilBO = examCourseUtilBO;
	}

	public ExamCourseUtilBO getExamCourseUtilBO() {
		return examCourseUtilBO;
	}

	public Integer getTermNumber() {
		return termNumber;
	}

	public void setTermNumber(Integer termNumber) {
		this.termNumber = termNumber;
	}

}
