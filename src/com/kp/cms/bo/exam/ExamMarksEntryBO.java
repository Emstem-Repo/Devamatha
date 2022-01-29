package com.kp.cms.bo.exam;

/**
 * Feb 6, 2010 Created By 9Elements Team
 */
import java.util.Date;
import java.util.Set;

import com.kp.cms.bo.admin.Classes;

@SuppressWarnings("serial")
public class ExamMarksEntryBO extends ExamGenBO {

	private int examId;
	private int studentId;
	private Integer evaluatorTypeId;
	private String marksCardNo;
	private Date marksCardDate;

	// For Secured Marks Entry Screen
	private Integer answerScriptTypeId;
	private Integer sequenceEvaluator;
	private Integer isSecured;
	private String markType;

	// Many-to-one
	private ExamDefinitionBO examDefinitionBO;
	private StudentUtilBO studentUtilBO;
	private ExamMultipleAnswerScriptMasterBO examMultipleAnswerScriptMasterBO;
	private Classes classes;

	// One-to-many
	private Set<ExamMarksEntryDetailsBO> examMarksEntryDetailsBOset;
	
	private Set<ExamMarksEntryCorrectionDetailsBO> examMarksEntryCorrectionDetailsBOset;

	public ExamMarksEntryBO() {
		super();
	}

	public ExamMarksEntryBO(String userId) {
		super();
		this.modifiedBy = userId;
		this.lastModifiedDate = new Date();
		this.createdBy = userId;
		this.createdDate = new Date();
	}

	public ExamMarksEntryBO(int examId, int studentId, int subjectGroupId,
			int evaluatorTypeId, String marksCardNo, Date marksCardDate,
			String userId) {
		super();
		this.examId = examId;
		this.studentId = studentId;
		this.setEvaluatorTypeId(evaluatorTypeId);
		this.marksCardNo = marksCardNo;
		this.marksCardDate = marksCardDate;
		this.modifiedBy = userId;
		this.lastModifiedDate = new Date();
	}

	public ExamMarksEntryBO(Integer id, int examId, int studentId,
			int subjectGroupId, int evaluatorTypeId, String marksCardNo,
			Date marksCardDate, String userId) {
		super();
		this.id = id;
		this.examId = examId;
		this.studentId = studentId;
		this.setEvaluatorTypeId(evaluatorTypeId);
		this.marksCardNo = marksCardNo;
		this.marksCardDate = marksCardDate;
		this.modifiedBy = userId;
		this.lastModifiedDate = new Date();
	}

	public int getExamId() {
		return examId;
	}

	public void setExamId(int examId) {
		this.examId = examId;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
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

	public void setExamMarksEntryDetailsBOset(
			Set<ExamMarksEntryDetailsBO> examMarksEntryDetailsBOset) {
		this.examMarksEntryDetailsBOset = examMarksEntryDetailsBOset;
	}

	public Set<ExamMarksEntryDetailsBO> getExamMarksEntryDetailsBOset() {
		return examMarksEntryDetailsBOset;
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

	public Integer getSequenceEvaluator() {
		return sequenceEvaluator;
	}

	public Integer getIsSecured() {
		return isSecured;
	}

	public void setSequenceEvaluator(Integer sequenceEvaluator) {
		this.sequenceEvaluator = sequenceEvaluator;
	}

	public void setIsSecured(Integer isSecured) {
		this.isSecured = isSecured;
	}

	public Classes getClasses() {
		return classes;
	}

	public void setClasses(Classes classes) {
		this.classes = classes;
	}
}
