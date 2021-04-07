package com.kp.cms.to.admission;

import java.math.BigDecimal;

public class TotalWeightageAdjustedTO {

	private int admApplnId;

	private BigDecimal weightageAdjustedMarks;

	public int getAdmApplnId() {
		return admApplnId;
	}

	public void setAdmApplnId(int admApplnId) {
		this.admApplnId = admApplnId;
	}

	public BigDecimal getWeightageAdjustedMarks() {
		return weightageAdjustedMarks;
	}

	public void setWeightageAdjustedMarks(BigDecimal weightageAdjustedMarks) {
		this.weightageAdjustedMarks = weightageAdjustedMarks;
	}

}
