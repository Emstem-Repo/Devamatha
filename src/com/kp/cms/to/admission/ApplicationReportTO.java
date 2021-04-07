package com.kp.cms.to.admission;

import java.io.Serializable;

/**
 * 
 * @author kshirod.k
 * A TO class for Application Report
 *
 */

public class ApplicationReportTO implements Serializable{
	
	private int issuedApplicationNo;
	private String flag;
	private String registerNo; 
	private String rollNo;
		
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public int getIssuedApplicationNo() {
		return issuedApplicationNo;
	}
	public String getRegisterNo() {
		return registerNo;
	}
	public String getRollNo() {
		return rollNo;
	}
	public void setRegisterNo(String registerNo) {
		this.registerNo = registerNo;
	}
	public void setRollNo(String rollNo) {
		this.rollNo = rollNo;
	}
	public void setIssuedApplicationNo(int issuedApplicationNo) {
		this.issuedApplicationNo = issuedApplicationNo;
	}

}
