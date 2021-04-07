package com.kp.cms.bo.exam;

/**
 * Jan 22, 2010 Created By 9Elements Team
 */
public class ExamRevaluationApplicationSubjectBO extends ExamGenBO {
	private int revaluationAppId;
	private int subjectId;

	private ExamRevaluationApplicationBO examRevaluationApplicationBO;
	private SubjectUtilBO subjectUtilBO;

	public ExamRevaluationApplicationSubjectBO() {
		super();

	}

	public ExamRevaluationApplicationSubjectBO(int revaluationAppId,
			int subjectId) {
		super();
		this.revaluationAppId = revaluationAppId;
		this.subjectId = subjectId;
	}

	public int getRevaluationAppId() {
		return revaluationAppId;
	}

	public void setRevaluationAppId(int revaluationAppId) {
		this.revaluationAppId = revaluationAppId;
	}

	public int getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}

	public ExamRevaluationApplicationBO getExamRevaluationApplicationBO() {
		return examRevaluationApplicationBO;
	}

	public void setExamRevaluationApplicationBO(
			ExamRevaluationApplicationBO examRevaluationApplicationBO) {
		this.examRevaluationApplicationBO = examRevaluationApplicationBO;
	}

	public SubjectUtilBO getSubjectUtilBO() {
		return subjectUtilBO;
	}

	public void setSubjectUtilBO(SubjectUtilBO subjectUtilBO) {
		this.subjectUtilBO = subjectUtilBO;
	}

}
