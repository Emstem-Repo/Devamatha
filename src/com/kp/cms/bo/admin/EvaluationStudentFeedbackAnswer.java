package com.kp.cms.bo.admin;

import java.io.Serializable;

import com.kp.cms.bo.studentfeedback.EvaStudentFeedbackQuestion;

public class EvaluationStudentFeedbackAnswer implements Serializable{
	private int id;
	private EvaStudentFeedbackQuestion questionId;
	private EvaluationStudentFeedbackFaculty feedbackFaculty;
	private String answer;
	public EvaluationStudentFeedbackAnswer() {
		super();
	}
	public EvaluationStudentFeedbackAnswer(int id,
			EvaStudentFeedbackQuestion questionId,
			EvaluationStudentFeedbackFaculty feedbackFaculty,String answer) {
		super();
		this.id = id;
		this.questionId = questionId;
		this.feedbackFaculty = feedbackFaculty;
		this.answer = answer;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public EvaStudentFeedbackQuestion getQuestionId() {
		return questionId;
	}
	public void setQuestionId(EvaStudentFeedbackQuestion questionId) {
		this.questionId = questionId;
	}
	public EvaluationStudentFeedbackFaculty getFeedbackFaculty() {
		return feedbackFaculty;
	}
	public void setFeedbackFaculty(EvaluationStudentFeedbackFaculty feedbackFaculty) {
		this.feedbackFaculty = feedbackFaculty;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
}
