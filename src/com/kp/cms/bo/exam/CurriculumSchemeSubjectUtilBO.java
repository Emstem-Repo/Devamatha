package com.kp.cms.bo.exam;

/**
 * Jan 15, 2010 Created By 9Elements Team
 */
public class CurriculumSchemeSubjectUtilBO extends ExamGenBO {

	private int curriculumSchemeDurationId;
	private int subjectGroupId;

	private SubjectGroupSubjectsUtilBO subjectGroupSubjectsUtilBO;
	private CurriculumSchemeDurationUtilBO curriculumSchemeDurationUtilBO;
	private SubjectGroupUtilBO subjectGroupUtilBO; 

	public SubjectGroupSubjectsUtilBO getSubjectGroupSubjectsUtilBO() {
		return subjectGroupSubjectsUtilBO;
	}

	public void setSubjectGroupSubjectsUtilBO(
			SubjectGroupSubjectsUtilBO subjectGroupSubjectsUtilBO) {
		this.subjectGroupSubjectsUtilBO = subjectGroupSubjectsUtilBO;
	}

	public CurriculumSchemeSubjectUtilBO() {
		super();
	}

	public int getCurriculumSchemeDurationId() {
		return curriculumSchemeDurationId;
	}

	public void setCurriculumSchemeDurationId(int curriculumSchemeDurationId) {
		this.curriculumSchemeDurationId = curriculumSchemeDurationId;
	}

	public int getSubjectGroupId() {
		return subjectGroupId;
	}

	public void setSubjectGroupId(int subjectGroupId) {
		this.subjectGroupId = subjectGroupId;
	}

	public void setCurriculumSchemeDurationUtilBO(
			CurriculumSchemeDurationUtilBO curriculumSchemeDurationUtilBO) {
		this.curriculumSchemeDurationUtilBO = curriculumSchemeDurationUtilBO;
	}

	public CurriculumSchemeDurationUtilBO getCurriculumSchemeDurationUtilBO() {
		return curriculumSchemeDurationUtilBO;
	}

	public void setSubjectGroupUtilBO(SubjectGroupUtilBO subjectGroupUtilBO) {
		this.subjectGroupUtilBO = subjectGroupUtilBO;
	}

	public SubjectGroupUtilBO getSubjectGroupUtilBO() {
		return subjectGroupUtilBO;
	}

}
