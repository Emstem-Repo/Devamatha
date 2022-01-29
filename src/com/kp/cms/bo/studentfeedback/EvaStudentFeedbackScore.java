package com.kp.cms.bo.studentfeedback;

public class EvaStudentFeedbackScore {
	private int id;
	private EvaStudentFeedbackOverallAverage evaStudentFeedbackOverallAverage;
	private EvaStudentFeedbackQuestion question;
	private int count1;
	private int count2;
	private int count3;
	private int count4;
	private int count5;
	private double score;
	private int totalCount;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public EvaStudentFeedbackOverallAverage getEvaStudentFeedbackOverallAverage() {
		return evaStudentFeedbackOverallAverage;
	}
	public void setEvaStudentFeedbackOverallAverage(
			EvaStudentFeedbackOverallAverage evaStudentFeedbackOverallAverage) {
		this.evaStudentFeedbackOverallAverage = evaStudentFeedbackOverallAverage;
	}	
	public EvaStudentFeedbackQuestion getQuestion() {
		return question;
	}
	public void setQuestion(EvaStudentFeedbackQuestion question) {
		this.question = question;
	}
	public int getCount1() {
		return count1;
	}
	public void setCount1(int count1) {
		this.count1 = count1;
	}
	public int getCount2() {
		return count2;
	}
	public void setCount2(int count2) {
		this.count2 = count2;
	}
	public int getCount3() {
		return count3;
	}
	public void setCount3(int count3) {
		this.count3 = count3;
	}
	public int getCount4() {
		return count4;
	}
	public void setCount4(int count4) {
		this.count4 = count4;
	}
	public int getCount5() {
		return count5;
	}
	public void setCount5(int count5) {
		this.count5 = count5;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}	
}
