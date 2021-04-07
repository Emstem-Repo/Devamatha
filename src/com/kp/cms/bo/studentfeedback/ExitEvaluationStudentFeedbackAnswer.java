package com.kp.cms.bo.studentfeedback;

import java.io.Serializable;

import com.kp.cms.bo.admin.EvaluationStudentFeedback;
import com.kp.cms.bo.admin.EvaluationStudentFeedbackFaculty;

public class ExitEvaluationStudentFeedbackAnswer implements Serializable{
	private int id;
	private ExitEvaluationTopic topicId;
	private ExitEvaluationQuestion questionId;
	private EvaluationStudentFeedback studentFeedBack;
	private String answer;
	private String remark;
	public ExitEvaluationStudentFeedbackAnswer() {
		super();
	}
	public ExitEvaluationStudentFeedbackAnswer(int id,
			ExitEvaluationTopic topicId,
			ExitEvaluationQuestion questionId,
			EvaluationStudentFeedback studentFeedBack,String answer,String remark) {
		super();
		this.id = id;
		this.questionId = questionId;
		this.studentFeedBack = studentFeedBack;
		this.answer = answer;
		this.topicId=topicId;
		this.remark=remark;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public EvaluationStudentFeedback getStudentFeedBack() {
		return studentFeedBack;
	}
	public void setStudentFeedBack(EvaluationStudentFeedback studentFeedBack) {
		this.studentFeedBack = studentFeedBack;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public ExitEvaluationTopic getTopicId() {
		return topicId;
	}
	public void setTopicId(ExitEvaluationTopic topicId) {
		this.topicId = topicId;
	}
	public ExitEvaluationQuestion getQuestionId() {
		return questionId;
	}
	public void setQuestionId(ExitEvaluationQuestion questionId) {
		this.questionId = questionId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
