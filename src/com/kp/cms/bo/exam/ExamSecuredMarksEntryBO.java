package com.kp.cms.bo.exam;

/**
 * Mar 8, 2010 Created By 9Elements Team
 */
@SuppressWarnings("serial")
public class ExamSecuredMarksEntryBO extends ExamGenBO {

	private int examId;
	private int subjectId;
	private int studentId;
	private String evaluatorType;
	private int answerScriptId;
	private String marks;
	private String previousEvaluatorMark;
	private int isMistake;
	private int isRetest;
	private int isFinal;
	private int isSupplementary;
	private int isInternal;

	private ExamDefinitionBO examDefinitionBO;
	private SubjectUtilBO subjectUtilBO;
	private StudentUtilBO studentUtilBO;
	private ExamMultipleAnswerScriptMasterBO examMultipleAnswerScriptMasterBO;

	public ExamSecuredMarksEntryBO() {
		super();
	}

	public ExamSecuredMarksEntryBO(int examId, int subjectId, int studentId,
			String evaluatorType, int answerScriptId, String marks,
			String previousEvaluatorMark, int isMistake, int isRetest,
			int isFinal, int isSupplementary, int isInternal) {
		super();
		this.examId = examId;
		this.subjectId = subjectId;
		this.studentId = studentId;
		this.evaluatorType = evaluatorType;
		this.answerScriptId = answerScriptId;
		this.marks = marks;
		this.previousEvaluatorMark = previousEvaluatorMark;
		this.isMistake = isMistake;
		this.isRetest = isRetest;
		this.isFinal = isFinal;
		this.isSupplementary = isSupplementary;
		this.isInternal = isInternal;
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

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public String getEvaluatorType() {
		return evaluatorType;
	}

	public void setEvaluatorType(String evaluatorType) {
		this.evaluatorType = evaluatorType;
	}

	public int getAnswerScriptId() {
		return answerScriptId;
	}

	public void setAnswerScriptId(int answerScriptId) {
		this.answerScriptId = answerScriptId;
	}

	public String getMarks() {
		return marks;
	}

	public void setMarks(String marks) {
		this.marks = marks;
	}

	public String getPreviousEvaluatorMark() {
		return previousEvaluatorMark;
	}

	public void setPreviousEvaluatorMark(String previousEvaluatorMark) {
		this.previousEvaluatorMark = previousEvaluatorMark;
	}

	public int getIsMistake() {
		return isMistake;
	}

	public void setIsMistake(int isMistake) {
		this.isMistake = isMistake;
	}

	public int getIsRetest() {
		return isRetest;
	}

	public void setIsRetest(int isRetest) {
		this.isRetest = isRetest;
	}

	public int getIsFinal() {
		return isFinal;
	}

	public void setIsFinal(int isFinal) {
		this.isFinal = isFinal;
	}

	public int getIsSupplementary() {
		return isSupplementary;
	}

	public void setIsSupplementary(int isSupplementary) {
		this.isSupplementary = isSupplementary;
	}

	public int getIsInternal() {
		return isInternal;
	}

	public void setIsInternal(int isInternal) {
		this.isInternal = isInternal;
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

}
