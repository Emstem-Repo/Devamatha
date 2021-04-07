package com.kp.cms.to.admin;

import java.util.List;

import com.kp.cms.to.studentfeedback.EvaStudentFeedBackQuestionTo;
import com.kp.cms.to.studentfeedback.ExitEvaluationQuestionTo;

public class EvaluationStudentFeedbackTO {
	private int id;
	private int employeeId;
	private int subjectId;
	List<EvaStudentFeedBackQuestionTo> questionTosList;
	private String remarks;
	private String additionalInfo;
	List<ExitEvaluationQuestionTo> exitEvalQuestionTosList;
	List<EvaStudentFeedBackQuestionTo> questionListSubjectWiseTo;
 
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public int getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	public int getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}
	public List<EvaStudentFeedBackQuestionTo> getQuestionTosList() {
		return questionTosList;
	}
	public void setQuestionTosList(
			List<EvaStudentFeedBackQuestionTo> questionTosList) {
		this.questionTosList = questionTosList;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getAdditionalInfo() {
		return additionalInfo;
	}
	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}
	public List<ExitEvaluationQuestionTo> getExitEvalQuestionTosList() {
		return exitEvalQuestionTosList;
	}
	public void setExitEvalQuestionTosList(
			List<ExitEvaluationQuestionTo> exitEvalQuestionTosList) {
		this.exitEvalQuestionTosList = exitEvalQuestionTosList;
	}
	public List<EvaStudentFeedBackQuestionTo> getQuestionListSubjectWiseTo() {
		return questionListSubjectWiseTo;
	}
	public void setQuestionListSubjectWiseTo(
			List<EvaStudentFeedBackQuestionTo> questionListSubjectWiseTo) {
		this.questionListSubjectWiseTo = questionListSubjectWiseTo;
	}
	
	
	
}
