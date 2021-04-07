package com.kp.cms.to.studentfeedback;

public class FacultyEvaluationReportPrincipalTo implements Comparable<FacultyEvaluationReportPrincipalTo>{
	
	private String scoreForEachQue;
	private int questionId;
	private int classId;
	
	public String getScoreForEachQue() {
		return scoreForEachQue;
	}
	public void setScoreForEachQue(String scoreForEachQue) {
		this.scoreForEachQue = scoreForEachQue;
	}
	public int getQuestionId() {
		return questionId;
	}
	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}
	@Override
	public int compareTo(FacultyEvaluationReportPrincipalTo o) {
		return questionId<o.getQuestionId()?-1:questionId>o.getQuestionId()?1:doSecodaryOrderSort(o);
	}
	public int doSecodaryOrderSort(FacultyEvaluationReportPrincipalTo o) {
	    return questionId<o.getQuestionId()?-1:questionId>o.getQuestionId()?1:0;
	}
	public int getClassId() {
		return classId;
	}
	public void setClassId(int classId) {
		this.classId = classId;
	}	
}
