package com.kp.cms.bo.teacherfeedback;

import java.io.Serializable;

public class EvaluationTeacherFeedbackAnswer implements Serializable{

	private int id;
	private EvaTeacherFeedbackQuestion questionId;
	private EvaluationTeacherFeedbackStudent feedbackStudent;
	private String answer;
	public EvaluationTeacherFeedbackAnswer() {
		super();
	}
	public EvaluationTeacherFeedbackAnswer(int id,
			EvaTeacherFeedbackQuestion questionId,
			EvaluationTeacherFeedbackStudent feedbackStudent,String answer) {
		super();
		this.id = id;
		this.questionId = questionId;
		this.feedbackStudent = feedbackStudent;
		this.answer = answer;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public EvaTeacherFeedbackQuestion getQuestionId() {
		return questionId;
	}
	public void setQuestionId(EvaTeacherFeedbackQuestion questionId) {
		this.questionId = questionId;
	}
	public EvaluationTeacherFeedbackStudent getFeedbackStudent() {
		return feedbackStudent;
	}
	public void setFeedbackStudent(EvaluationTeacherFeedbackStudent feedbackStudent) {
		this.feedbackStudent = feedbackStudent;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	

}
