package com.kp.cms.to.admission;

public class CasteWeightageTO {
	
	private int casteId;
	
	private String casteName;
	
	private Integer weightageId;
	
	private String weightagePercentage;

	public int getCasteId() {
		return casteId;
	}

	public void setCasteId(int casteId) {
		this.casteId = casteId;
	}

	public String getCasteName() {
		return casteName;
	}

	public void setCasteName(String casteName) {
		this.casteName = casteName;
	}

	public String getWeightagePercentage() {
		return weightagePercentage;
	}

	public void setWeightagePercentage(String weightagePercentage) {
		this.weightagePercentage = weightagePercentage;
	}

	public Integer getWeightageId() {
		return weightageId;
	}

	public void setWeightageId(Integer weightageId) {
		this.weightageId = weightageId;
	}

}
