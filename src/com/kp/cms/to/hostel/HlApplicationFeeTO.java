package com.kp.cms.to.hostel;

import java.io.Serializable;


public class HlApplicationFeeTO implements Serializable,Comparable<HlApplicationFeeTO>{

	private int feeTypeId;
	private String feeTypeName;
	private String amount;
	
	// properties for Application Number Entry
	private int id;
	private String startNumber;
	private String prefix;
	private String hosteId;
	private String academicyear;
	private String hostelName;
	
	
	
	public int getFeeTypeId() {
		return feeTypeId;
	}
	public void setFeeTypeId(int feeTypeId) {
		this.feeTypeId = feeTypeId;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getFeeTypeName() {
		return feeTypeName;
	}
	public void setFeeTypeName(String feeTypeName) {
		this.feeTypeName = feeTypeName;
	}
	@Override
	public int compareTo(HlApplicationFeeTO arg0) {
			if(arg0!=null && this!=null){
				if(this.getFeeTypeId() > arg0.getFeeTypeId())
					return 1;
				else if(this.getFeeTypeId() < arg0.getFeeTypeId())
					return -1;
				else
					return 0;
			}
			return 0;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getStartNumber() {
		return startNumber;
	}
	public void setStartNumber(String startNumber) {
		this.startNumber = startNumber;
	}
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public String getHosteId() {
		return hosteId;
	}
	public void setHosteId(String hosteId) {
		this.hosteId = hosteId;
	}
	public String getAcademicyear() {
		return academicyear;
	}
	public void setAcademicyear(String academicyear) {
		this.academicyear = academicyear;
	}
	public String getHostelName() {
		return hostelName;
	}
	public void setHostelName(String hostelName) {
		this.hostelName = hostelName;
	}
}
