package com.kp.cms.bo.exam;

import java.util.Date;
import java.util.Set;

@SuppressWarnings("serial")
public class ExamStudentMarksCorrectionBO extends ExamGenBO {

	private Integer examId;
	private Integer studentId;
	private Integer answerScriptId;
	private String markType;
	private Integer evaluatorType;
	private String marksCardNo;
	private Date marksCardDate;

	private ExamDefinitionBO examDefinitionBO;
	private StudentUtilBO studentUtilBO;
	private ExamMultipleAnswerScriptMasterBO examMultipleAnswerScriptMasterBO;

	private Set<ExamStudentMarksCorrectionDetailsBO> examStudentMarksCorrectionDetailsBOSet;

	public ExamStudentMarksCorrectionBO(
			Integer answerScriptId,
			Integer evaluatorType,
			ExamDefinitionBO examDefinitionBO,
			Integer examId,
			ExamMultipleAnswerScriptMasterBO examMultipleAnswerScriptMasterBO,
			Set<ExamStudentMarksCorrectionDetailsBO> examStudentMarksCorrectionDetailsBOSet,
			String markType, Date marksCardDate, String marksCardNo,
			Integer studentId, StudentUtilBO studentUtilBO) {
		super();
		this.answerScriptId = answerScriptId;
		this.evaluatorType = evaluatorType;
		this.examDefinitionBO = examDefinitionBO;
		this.examId = examId;
		this.examMultipleAnswerScriptMasterBO = examMultipleAnswerScriptMasterBO;
		this.examStudentMarksCorrectionDetailsBOSet = examStudentMarksCorrectionDetailsBOSet;
		this.markType = markType;
		this.marksCardDate = marksCardDate;
		this.marksCardNo = marksCardNo;
		this.studentId = studentId;
		this.studentUtilBO = studentUtilBO;
		this.isActive = true;
	}

	public ExamStudentMarksCorrectionBO() {
		super();
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

	public Integer getAnswerScriptId() {
		return answerScriptId;
	}

	public void setAnswerScriptId(Integer answerScriptId) {
		this.answerScriptId = answerScriptId;
	}

	public String getMarkType() {
		return markType;
	}

	public void setMarkType(String markType) {
		this.markType = markType;
	}

	public Integer getEvaluatorType() {
		return evaluatorType;
	}

	public void setEvaluatorType(Integer evaluatorType) {
		this.evaluatorType = evaluatorType;
	}

	public String getMarksCardNo() {
		return marksCardNo;
	}

	public void setMarksCardNo(String marksCardNo) {
		this.marksCardNo = marksCardNo;
	}

	public Date getMarksCardDate() {
		return marksCardDate;
	}

	public void setMarksCardDate(Date marksCardDate) {
		this.marksCardDate = marksCardDate;
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

	public ExamMultipleAnswerScriptMasterBO getExamMultipleAnswerScriptMasterBO() {
		return examMultipleAnswerScriptMasterBO;
	}

	public void setExamMultipleAnswerScriptMasterBO(
			ExamMultipleAnswerScriptMasterBO examMultipleAnswerScriptMasterBO) {
		this.examMultipleAnswerScriptMasterBO = examMultipleAnswerScriptMasterBO;
	}

	public Set<ExamStudentMarksCorrectionDetailsBO> getExamStudentMarksCorrectionDetailsBOSet() {
		return examStudentMarksCorrectionDetailsBOSet;
	}

	public void setExamStudentMarksCorrectionDetailsBOSet(
			Set<ExamStudentMarksCorrectionDetailsBO> examStudentMarksCorrectionDetailsBOSet) {
		this.examStudentMarksCorrectionDetailsBOSet = examStudentMarksCorrectionDetailsBOSet;
	}
	
	
}
