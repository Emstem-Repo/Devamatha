package com.kp.cms.to.admission;

public class NationalityWeightageTO {
	
	private int nationalityId;

	private String nationalityName;

	private Integer weightageId;

	private String weightagePercentage;
	
	public int getNationalityId() {
		return nationalityId;
	}

	public void setNationalityId(int nationalityId) {
		this.nationalityId = nationalityId;
	}

	public String getNationalityName() {
		return nationalityName;
	}

	public void setNationalityName(String nationalityName) {
		this.nationalityName = nationalityName;
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
