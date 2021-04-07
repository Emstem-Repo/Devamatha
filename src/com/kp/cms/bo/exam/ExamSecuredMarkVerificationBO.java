package com.kp.cms.bo.exam;

import java.util.Set;

@SuppressWarnings("serial")
public class ExamSecuredMarkVerificationBO extends ExamGenBO {

	private int examId;
	private int subjectId;
	private Integer evaluatorTypeId;
	private Integer answerScriptId;
	private int isSecured;

	private ExamDefinitionBO examDefinitionBO;
	private SubjectUtilBO subjectUtilBO;
	private ExamMultipleAnswerScriptMasterBO examMultipleAnswerScriptMasterBO;

	private Set<ExamSecuredMarksVerificationDetailsBO> examSecuredMarksVerificationDetailsBOset;

	public ExamSecuredMarkVerificationBO() {
		super();
	}

	public ExamSecuredMarkVerificationBO(int examId, int subjectId,
			Integer evaluatorTypeId, Integer answerScriptId, int isSecured) {
		super();
		this.examId = examId;
		this.subjectId = subjectId;
		this.evaluatorTypeId = evaluatorTypeId;
		this.answerScriptId = answerScriptId;
		this.isSecured = isSecured;
	}

	public int getExamId() {
		return examId;
	}

	public void setExamId(int examId) {
		this.examId = examId;
	}

	public int getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}

	public Integer getEvaluatorTypeId() {
		return evaluatorTypeId;
	}

	public void setEvaluatorTypeId(Integer evaluatorTypeId) {
		this.evaluatorTypeId = evaluatorTypeId;
	}

	public Integer getAnswerScriptId() {
		return answerScriptId;
	}

	public void setAnswerScriptId(Integer answerScriptId) {
		this.answerScriptId = answerScriptId;
	}

	public int getIsSecured() {
		return isSecured;
	}

	public void setIsSecured(int isSecured) {
		this.isSecured = isSecured;
	}

	public ExamDefinitionBO getExamDefinitionBO() {
		return examDefinitionBO;
	}

	public void setExamDefinitionBO(ExamDefinitionBO examDefinitionBO) {
		this.examDefinitionBO = examDefinitionBO;
	}

	public SubjectUtilBO getSubjectUtilBO() {
		return subjectUtilBO;
	}

	public void setSubjectUtilBO(SubjectUtilBO subjectUtilBO) {
		this.subjectUtilBO = subjectUtilBO;
	}

	public ExamMultipleAnswerScriptMasterBO getExamMultipleAnswerScriptMasterBO() {
		return examMultipleAnswerScriptMasterBO;
	}

	public void setExamMultipleAnswerScriptMasterBO(
			ExamMultipleAnswerScriptMasterBO examMultipleAnswerScriptMasterBO) {
		this.examMultipleAnswerScriptMasterBO = examMultipleAnswerScriptMasterBO;
	}

	public Set<ExamSecuredMarksVerificationDetailsBO> getExamSecuredMarksVerificationDetailsBOset() {
		return examSecuredMarksVerificationDetailsBOset;
	}

	public void setExamSecuredMarksVerificationDetailsBOset(
			Set<ExamSecuredMarksVerificationDetailsBO> examSecuredMarksVerificationDetailsBOset) {
		this.examSecuredMarksVerificationDetailsBOset = examSecuredMarksVerificationDetailsBOset;
	}

}
