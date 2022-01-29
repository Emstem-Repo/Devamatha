package com.kp.cms.bo.exam;

import java.io.Serializable;

/**
 * Dec 17, 2009 Created By 9Elements Team
 */
public class ExamProgramTypeUtilBO implements Serializable {

	private String programType;
	private int id;
	protected boolean isActive;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProgramType() {
		return programType;
	}

	public void setProgramType(String programType) {
		this.programType = programType;
	}

	public boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}
}
