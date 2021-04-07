package com.kp.cms.to.admission;

import java.math.BigDecimal;

public class EducationalWeightageAdjustedTO {
	
	private int educationQualificationId;
	
	private BigDecimal weightageAdjustedMarks;

	public int getEducationQualificationId() {
		return educationQualificationId;
	}

	public void setEducationQualificationId(int educationQualificationId) {
		this.educationQualificationId = educationQualificationId;
	}

	public BigDecimal getWeightageAdjustedMarks() {
		return weightageAdjustedMarks;
	}

	public void setWeightageAdjustedMarks(BigDecimal weightageAdjustedMarks) {
		this.weightageAdjustedMarks = weightageAdjustedMarks;
	}

}
