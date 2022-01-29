package com.kp.cms.to.studentfeedback;

public class FacultyEvaluationReportAnswersTo {
	
	int questionId;
	String question;
	int answerId;
	int answer;
	int countOfStudents;	
	
	public int getQuestionId() {
		return questionId;
	}
	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}
	public int getAnswerId() {
		return answerId;
	}
	public void setAnswerId(int answerId) {
		this.answerId = answerId;
	}
	
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public int getAnswer() {
		return answer;
	}
	public void setAnswer(int answer) {
		this.answer = answer;
	}
	public int getCountOfStudents() {
		return countOfStudents;
	}
	public void setCountOfStudents(int countOfStudents) {
		this.countOfStudents = countOfStudents;
	}
}
