package com.kp.cms.to.exam;

import java.io.Serializable;

public class ExamSubjectRuleSettingsSubjectFinalTO implements Serializable
{
	private String maximumSubjectFinal;
	private String subjectFinalPracticalExamChecked;
	private String subjectFinalInternalExamChecked;
	private String subjectFinalAttendanceChecked;
	private String valuatedSubjectFinal;
	private String minimumSubjectFinal;
	private String subjectFinalTheoryExamChecked;
	public ExamSubjectRuleSettingsSubjectFinalTO() {
		super();
	}
	public ExamSubjectRuleSettingsSubjectFinalTO(String maximumSubjectFinal,
			String minimumSubjectFinal, String subjectFinalAttendanceChecked,
			String subjectFinalInternalExamChecked,
			String subjectFinalPracticalExamChecked,
			String subjectFinalTheoryExamChecked, String valuatedSubjectFinal) {
		super();
		this.maximumSubjectFinal = maximumSubjectFinal;
		this.minimumSubjectFinal = minimumSubjectFinal;
		this.subjectFinalAttendanceChecked = subjectFinalAttendanceChecked;
		this.subjectFinalInternalExamChecked = subjectFinalInternalExamChecked;
		this.subjectFinalPracticalExamChecked = subjectFinalPracticalExamChecked;
		this.subjectFinalTheoryExamChecked = subjectFinalTheoryExamChecked;
		this.valuatedSubjectFinal = valuatedSubjectFinal;
	}
	
	public String getMaximumSubjectFinal() {
		return maximumSubjectFinal;
	}
	public void setMaximumSubjectFinal(String maximumSubjectFinal) {
		this.maximumSubjectFinal = maximumSubjectFinal;
	}
	public String getSubjectFinalPracticalExamChecked() {
		return subjectFinalPracticalExamChecked;
	}
	public void setSubjectFinalPracticalExamChecked(
			String subjectFinalPracticalExamChecked) {
		this.subjectFinalPracticalExamChecked = subjectFinalPracticalExamChecked;
	}
	public String getSubjectFinalInternalExamChecked() {
		return subjectFinalInternalExamChecked;
	}
	public void setSubjectFinalInternalExamChecked(
			String subjectFinalInternalExamChecked) {
		this.subjectFinalInternalExamChecked = subjectFinalInternalExamChecked;
	}
	public String getSubjectFinalAttendanceChecked() {
		return subjectFinalAttendanceChecked;
	}
	public void setSubjectFinalAttendanceChecked(
			String subjectFinalAttendanceChecked) {
		this.subjectFinalAttendanceChecked = subjectFinalAttendanceChecked;
	}
	public String getValuatedSubjectFinal() {
		return valuatedSubjectFinal;
	}
	public void setValuatedSubjectFinal(String valuatedSubjectFinal) {
		this.valuatedSubjectFinal = valuatedSubjectFinal;
	}
	public String getMinimumSubjectFinal() {
		return minimumSubjectFinal;
	}
	public void setMinimumSubjectFinal(String minimumSubjectFinal) {
		this.minimumSubjectFinal = minimumSubjectFinal;
	}
	public String getSubjectFinalTheoryExamChecked() {
		return subjectFinalTheoryExamChecked;
	}
	public void setSubjectFinalTheoryExamChecked(
			String subjectFinalTheoryExamChecked) {
		this.subjectFinalTheoryExamChecked = subjectFinalTheoryExamChecked;
	}
}
