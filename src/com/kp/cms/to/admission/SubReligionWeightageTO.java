package com.kp.cms.to.admission;

public class SubReligionWeightageTO {
	
private int religionSectionId;
	
	private String religionSectionName;
	
	private Integer weightageId;
	
	private String weightagePercentage;

	public int getReligionSectionId() {
		return religionSectionId;
	}

	public void setReligionSectionId(int religionSectionId) {
		this.religionSectionId = religionSectionId;
	}

	public String getReligionSectionName() {
		return religionSectionName;
	}

	public void setReligionSectionName(String religionSectionName) {
		this.religionSectionName = religionSectionName;
	}

	public Integer getWeightageId() {
		return weightageId;
	}

	public void setWeightageId(Integer weightageId) {
		this.weightageId = weightageId;
	}

	public String getWeightagePercentage() {
		return weightagePercentage;
	}

	public void setWeightagePercentage(String weightagePercentage) {
		this.weightagePercentage = weightagePercentage;
	}

}
