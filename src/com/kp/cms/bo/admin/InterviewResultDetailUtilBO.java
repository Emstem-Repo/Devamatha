package com.kp.cms.bo.admin;

public class InterviewResultDetailUtilBO {

	private Integer id;
	private Integer interviewResultId;
	private Integer gradeId;

	public InterviewResultDetailUtilBO(Integer id, Integer gradeId,
			Integer interviewResultId) {
		super();
		this.gradeId = gradeId;
		this.id = id;
		this.interviewResultId = interviewResultId;
	}

	public InterviewResultDetailUtilBO(Integer gradeId,
			Integer interviewResultId) {
		super();
		this.gradeId = gradeId;
		this.interviewResultId = interviewResultId;
	}

	public InterviewResultDetailUtilBO() {
	}

	public InterviewResultDetailUtilBO(Integer gradeId) {
		this.gradeId = gradeId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getInterviewResultId() {
		return interviewResultId;
	}

	public void setInterviewResultId(Integer interviewResultId) {
		this.interviewResultId = interviewResultId;
	}

	public Integer getGradeId() {
		return gradeId;
	}

	public void setGradeId(Integer gradeId) {
		this.gradeId = gradeId;
	}

}
