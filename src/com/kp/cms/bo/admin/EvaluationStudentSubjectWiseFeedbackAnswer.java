package com.kp.cms.bo.admin;

import java.io.Serializable;

import com.kp.cms.bo.studentfeedback.EvaStudentFeedbackQuestion;

public class EvaluationStudentSubjectWiseFeedbackAnswer implements Serializable{
	

	private int id;
	private Subject subject;
	private EvaluationStudentFeedback evaStuFeedback;
	private String answer;
	private EvaStudentFeedbackQuestion questionId;

	public EvaluationStudentSubjectWiseFeedbackAnswer() {
		super();
	}
	public EvaluationStudentSubjectWiseFeedbackAnswer(int id,
			Subject subject,
			EvaluationStudentFeedbackFaculty feedbackFaculty,String answer) {
		super();
		this.id = id;
		this.subject = subject;
		this.evaStuFeedback = evaStuFeedback;
		this.answer = answer;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Subject getSubject() {
		return subject;
	}
	public void setSubject(Subject subject) {
		this.subject = subject;
	}
	public EvaluationStudentFeedback getEvaStuFeedback() {
		return evaStuFeedback;
	}
	public void setEvaStuFeedback(EvaluationStudentFeedback evaStuFeedback) {
		this.evaStuFeedback = evaStuFeedback;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public EvaStudentFeedbackQuestion getQuestionId() {
		return questionId;
	}
	public void setQuestionId(EvaStudentFeedbackQuestion questionId) {
		this.questionId = questionId;
	}
	

}
