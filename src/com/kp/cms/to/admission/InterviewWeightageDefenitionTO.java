package com.kp.cms.to.admission;

import java.util.List;

public class InterviewWeightageDefenitionTO {
	
	private int interviewProgramCourseId;
	
	private String interviewType;
	
	private int weightageId;
	
	private String weightagePercentage;
	private List<InterviewSubroundDisplayTO> subRoundList;
	
	public int getInterviewProgramCourseId() {
		return interviewProgramCourseId;
	}

	public void setInterviewProgramCourseId(int interviewProgramCourseId) {
		this.interviewProgramCourseId = interviewProgramCourseId;
	}

	public String getInterviewType() {
		return interviewType;
	}

	public void setInterviewType(String interviewType) {
		this.interviewType = interviewType;
	}

	public int getWeightageId() {
		return weightageId;
	}

	public void setWeightageId(int weightageId) {
		this.weightageId = weightageId;
	}

	public String getWeightagePercentage() {
		return weightagePercentage;
	}

	public void setWeightagePercentage(String weightagePercentage) {
		this.weightagePercentage = weightagePercentage;
	}

	public List<InterviewSubroundDisplayTO> getSubRoundList() {
		return subRoundList;
	}

	public void setSubRoundList(List<InterviewSubroundDisplayTO> subRoundList) {
		this.subRoundList = subRoundList;
	}

}
