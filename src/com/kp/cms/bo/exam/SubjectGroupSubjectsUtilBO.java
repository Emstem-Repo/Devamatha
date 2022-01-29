package com.kp.cms.bo.exam;

/**
 * Jan 15, 2010 Created By 9Elements Team
 */

public class SubjectGroupSubjectsUtilBO extends ExamGenBO {

	private SubjectGroupUtilBO subjectGroupUtilBO;
	private SubjectUtilBO subjectUtilBO;

	public SubjectUtilBO getSubjectUtilBO() {
		return subjectUtilBO;
	}

	public void setSubjectUtilBO(SubjectUtilBO subjectUtilBO) {
		this.subjectUtilBO = subjectUtilBO;
	}

	public SubjectGroupSubjectsUtilBO() {
		super();
	}


	public void setSubjectGroupUtilBO(SubjectGroupUtilBO subjectGroupUtilBO) {
		this.subjectGroupUtilBO = subjectGroupUtilBO;
	}

	public SubjectGroupUtilBO getSubjectGroupUtilBO() {
		return subjectGroupUtilBO;
	}

}
