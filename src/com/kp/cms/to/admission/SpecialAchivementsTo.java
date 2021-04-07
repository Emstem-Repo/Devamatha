package com.kp.cms.to.admission;

import java.io.Serializable;

public class SpecialAchivementsTo implements Serializable
{
	private Integer id;
	private String regNo;
	private String achivement;
	private String termNumber;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getRegNo() {
		return regNo;
	}
	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}
	public String getAchivement() {
		return achivement;
	}
	public void setAchivement(String achivement) {
		this.achivement = achivement;
	}
	public String getTermNumber() {
		return termNumber;
	}
	public void setTermNumber(String termNumber) {
		this.termNumber = termNumber;
	}
}
