package com.kp.cms.to.reports;

import java.io.Serializable;

public class CategoryWiseMapTO implements Serializable {
	
	private String casteName;
	private int intakeValue;

	public String getCasteName() {
		return casteName;
	}
	public void setCasteName(String casteName) {
		this.casteName = casteName;
	}
	public int getIntakeValue() {
		return intakeValue;
	}
	public void setIntakeValue(int intakeValue) {
		this.intakeValue = intakeValue;
	}
}