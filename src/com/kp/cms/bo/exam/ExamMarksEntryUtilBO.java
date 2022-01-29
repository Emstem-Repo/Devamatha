package com.kp.cms.bo.exam;

/**
 * Feb 6, 2010 Created By 9Elements Team
 */
import java.util.Date;
import java.util.Set;

@SuppressWarnings("serial")
public class ExamMarksEntryUtilBO extends ExamGenBO {

	private Integer evaluatorTypeId;
	private String marksCardNo;
	private Date marksCardDate;
	private Integer answerScriptTypeId;
	private int sequenceEvaluator;
	private int isSecured;
	private String markType;
	private ExamDefinitionBO examDefinitionBO;
	private StudentUtilBO studentUtilBO;
	private ExamMultipleAnswerScriptMasterBO examMultipleAnswerScriptMasterBO;

	// One-to-many
	private Set<ExamMarksEntryDetailUtilBo> examMarksEntryDetailsBOset;
	
	private Set<ExamMarksEntryCorrectionDetailsBO> examMarksEntryCorrectionDetailsBOset;

	public ExamMarksEntryUtilBO() {
		super();
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

	public void setEvaluatorTypeId(Integer evaluatorTypeId) {
		this.evaluatorTypeId = evaluatorTypeId;
	}

	public Integer getEvaluatorTypeId() {
		return evaluatorTypeId;
	}

	public void setAnswerScriptTypeId(Integer answerScriptTypeId) {
		this.answerScriptTypeId = answerScriptTypeId;
	}

	public Integer getAnswerScriptTypeId() {
		return answerScriptTypeId;
	}

	public void setSequenceEvaluator(int sequenceEvaluator) {
		this.sequenceEvaluator = sequenceEvaluator;
	}

	public int getSequenceEvaluator() {
		return sequenceEvaluator;
	}

	public void setIsSecured(int isSecured) {
		this.isSecured = isSecured;
	}

	public int getIsSecured() {
		return isSecured;
	}

	public void setExamMultipleAnswerScriptMasterBO(
			ExamMultipleAnswerScriptMasterBO examMultipleAnswerScriptMasterBO) {
		this.examMultipleAnswerScriptMasterBO = examMultipleAnswerScriptMasterBO;
	}

	public ExamMultipleAnswerScriptMasterBO getExamMultipleAnswerScriptMasterBO() {
		return examMultipleAnswerScriptMasterBO;
	}

	public void setMarkType(String markType) {
		this.markType = markType;
	}

	public String getMarkType() {
		return markType;
	}

	public Set<ExamMarksEntryCorrectionDetailsBO> getExamMarksEntryCorrectionDetailsBOset() {
		return examMarksEntryCorrectionDetailsBOset;
	}

	public void setExamMarksEntryCorrectionDetailsBOset(
			Set<ExamMarksEntryCorrectionDetailsBO> examMarksEntryCorrectionDetailsBOset) {
		this.examMarksEntryCorrectionDetailsBOset = examMarksEntryCorrectionDetailsBOset;
	}



	public Set<ExamMarksEntryDetailUtilBo> getExamMarksEntryDetailsBOset() {
		return examMarksEntryDetailsBOset;
	}



	public void setExamMarksEntryDetailsBOset(
			Set<ExamMarksEntryDetailUtilBo> examMarksEntryDetailsBOset) {
		this.examMarksEntryDetailsBOset = examMarksEntryDetailsBOset;
	}
	
	
}
