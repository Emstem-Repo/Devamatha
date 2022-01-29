package com.kp.cms.to.admission;

public class GenderWeightageTO {
	
	private Integer weightageId;	

	private String weightagePercentage;
	
	private String getnder;

	

	public String getWeightagePercentage() {
		return weightagePercentage;
	}

	public void setWeightagePercentage(String weightagePercentage) {
		this.weightagePercentage = weightagePercentage;
	}

	public String getGetnder() {
		return getnder;
	}

	public void setGetnder(String getnder) {
		this.getnder = getnder;
	}

	public Integer getWeightageId() {
		return weightageId;
	}

	public void setWeightageId(Integer weightageId) {
		this.weightageId = weightageId;
	}
}
