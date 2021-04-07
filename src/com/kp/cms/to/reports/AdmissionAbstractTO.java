package com.kp.cms.to.reports;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdmissionAbstractTO  implements Serializable,Comparable<AdmissionAbstractTO>{
	private String programType;
	private String programCode;
	private String courseCode;
	private int karStudents;
	private int otherThanKar;
	private int otherThanInd;
	private int boysCount;
	private int girlsCount;
	private List<AdmAbstractCatgMapTO> admCatgList;
	private Map<Integer, AdmAbstractCatgMapTO> categoryMap = new HashMap<Integer, AdmAbstractCatgMapTO>();
	private int total;
	private int tempNo; 
	private String tempprogramCode;
	
	public String getProgramType() {
		return programType;
	}
	public String getProgramCode() {
		return programCode;
	}
	public String getCourseCode() {
		return courseCode;
	}
	public int getKarStudents() {
		return karStudents;
	}
	public int getOtherThanKar() {
		return otherThanKar;
	}
	public int getOtherThanInd() {
		return otherThanInd;
	}
	public int getBoysCount() {
		return boysCount;
	}
	public int getGirlsCount() {
		return girlsCount;
	}
	public List<AdmAbstractCatgMapTO> getAdmCatgList() {
		return admCatgList;
	}
	public Map<Integer, AdmAbstractCatgMapTO> getCategoryMap() {
		return categoryMap;
	}
	public int getTotal() {
		return total;
	}
	public void setProgramType(String programType) {
		this.programType = programType;
	}
	public void setProgramCode(String programCode) {
		this.programCode = programCode;
	}
	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}
	public void setKarStudents(int karStudents) {
		this.karStudents = karStudents;
	}
	public void setOtherThanKar(int otherThanKar) {
		this.otherThanKar = otherThanKar;
	}
	public void setOtherThanInd(int otherThanInd) {
		this.otherThanInd = otherThanInd;
	}
	public void setBoysCount(int boysCount) {
		this.boysCount = boysCount;
	}
	public void setGirlsCount(int girlsCount) {
		this.girlsCount = girlsCount;
	}
	public void setAdmCatgList(List<AdmAbstractCatgMapTO> admCatgList) {
		this.admCatgList = admCatgList;
	}
	public void setCategoryMap(Map<Integer, AdmAbstractCatgMapTO> categoryMap) {
		this.categoryMap = categoryMap;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getTempNo() {
		return tempNo;
	}
	public void setTempNo(int tempNo) {
		this.tempNo = tempNo;
	}
	public String getTempprogramCode() {
		return tempprogramCode;
	}
	public void setTempprogramCode(String tempprogramCode) {
		this.tempprogramCode = tempprogramCode;
	}
	@Override
	public int compareTo(AdmissionAbstractTO arg0) {
		if(arg0!=null && this!=null && arg0.getProgramCode()!=null 
				 && this.getProgramCode()!=null){
			
				return this.getProgramCode().compareTo(arg0.getProgramCode());
		}else
		return 0;
	}
	
	
}
