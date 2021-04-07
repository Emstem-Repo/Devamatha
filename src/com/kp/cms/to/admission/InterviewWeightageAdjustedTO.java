package com.kp.cms.to.admission;

import java.math.BigDecimal;

import com.kp.cms.bo.admin.InterviewResult;

public class InterviewWeightageAdjustedTO {
	
	private InterviewResult interviewResultId;
	private boolean subRound;
	public boolean isSubRound() {
		return subRound;
	}

	public void setSubRound(boolean subRound) {
		this.subRound = subRound;
	}

	private BigDecimal weightageAdjustedMarks;

	public InterviewResult getInterviewResultId() {
		return interviewResultId;
	}

	public void setInterviewResultId(InterviewResult interviewResultId) {
		this.interviewResultId = interviewResultId;
	}

	public BigDecimal getWeightageAdjustedMarks() {
		return weightageAdjustedMarks;
	}

	public void setWeightageAdjustedMarks(BigDecimal weightageAdjustedMarks) {
		this.weightageAdjustedMarks = weightageAdjustedMarks;
	}

}
