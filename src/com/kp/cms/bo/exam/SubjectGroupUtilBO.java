package com.kp.cms.bo.exam;

import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("serial")
public class SubjectGroupUtilBO extends ExamGenBO {

	private int isCommonSubGrp;
	private int courseId;
	private Integer secondLanguageId;

	private ExamCourseUtilBO examCourseUtilBO;
	private Set<SubjectGroupSubjectsUtilBO> subjectGroupSubjectses = new HashSet<SubjectGroupSubjectsUtilBO>(0);

	public SubjectGroupUtilBO() {
		super();
	}

	public void setIsCommonSubGrp(int isCommonSubGrp) {
		this.isCommonSubGrp = isCommonSubGrp;
	}

	public int getIsCommonSubGrp() {
		return isCommonSubGrp;
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

	public void setSecondLanguageId(Integer secondLanguageId) {
		this.secondLanguageId = secondLanguageId;
	}

	public Integer getSecondLanguageId() {
		return secondLanguageId;
	}

	public Set<SubjectGroupSubjectsUtilBO> getSubjectGroupSubjectses() {
		return subjectGroupSubjectses;
	}

	public void setSubjectGroupSubjectses(
			Set<SubjectGroupSubjectsUtilBO> subjectGroupSubjectses) {
		this.subjectGroupSubjectses = subjectGroupSubjectses;
	}
}
