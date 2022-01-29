package com.kp.cms.to.admission;

public class RuralUrbanWeightageTO {
	
	private Integer weightageId;	

	private String weightagePercentage;
	
	private String ruralUrban;

	public String getWeightagePercentage() {
		return weightagePercentage;
	}

	public void setWeightagePercentage(String weightagePercentage) {
		this.weightagePercentage = weightagePercentage;
	}

	public String getRuralUrban() {
		return ruralUrban;
	}

	public void setRuralUrban(String ruralUrban) {
		this.ruralUrban = ruralUrban;
	}

	public Integer getWeightageId() {
		return weightageId;
	}

	public void setWeightageId(Integer weightageId) {
		this.weightageId = weightageId;
	}

}
