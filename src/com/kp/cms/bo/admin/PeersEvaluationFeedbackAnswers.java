package com.kp.cms.bo.admin;

import java.io.Serializable;

import com.kp.cms.bo.studentfeedback.EvaFacultyFeedbackQuestion;

public class PeersEvaluationFeedbackAnswers implements Serializable{
	private int id;
	private PeersEvaluationFeedbackPeers peersEvaluationFeedbackPeers;
	private EvaFacultyFeedbackQuestion questionsId;
	private String answer;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public EvaFacultyFeedbackQuestion getQuestionsId() {
		return questionsId;
	}
	public void setQuestionsId(EvaFacultyFeedbackQuestion questionsId) {
		this.questionsId = questionsId;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public PeersEvaluationFeedbackPeers getPeersEvaluationFeedbackPeers() {
		return peersEvaluationFeedbackPeers;
	}
	public void setPeersEvaluationFeedbackPeers(
			PeersEvaluationFeedbackPeers peersEvaluationFeedbackPeers) {
		this.peersEvaluationFeedbackPeers = peersEvaluationFeedbackPeers;
	} 
}
