package com.kp.cms.to.reports;

import java.io.Serializable;


public class AdmAbstractCatgMapTO implements Serializable,Comparable<AdmAbstractCatgMapTO>{
	private String categoryName;
	private int noOfStudents;
	private int tempNo;
	
	public int getTempNo() {
		return tempNo;
	}

	public void setTempNo(int tempNo) {
		this.tempNo = tempNo;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public int getNoOfStudents() {
		return noOfStudents;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public void setNoOfStudents(int noOfStudents) {
		this.noOfStudents = noOfStudents;
	}

	@Override
	public int compareTo(AdmAbstractCatgMapTO arg0) {
		if(arg0!=null && arg0.getCategoryName()!=null 
				 && this.getCategoryName()!=null){
			return this.getCategoryName().compareTo(arg0.getCategoryName());
		}else
		return 0;
	}
	

}
