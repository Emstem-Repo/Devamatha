package com.kp.cms.forms.admin;

import java.util.List;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.FreeFoodIssueTo;

public class FreeFoodIssueForm extends BaseActionForm{
	private String registerNo;
	private List<FreeFoodIssueTo> toList;
	private int studentId;
	private Boolean isEligible;
	private Boolean flag;
	private String time;
	private Boolean flag1;
	private int foodIssuedTimes;

	public String getRegisterNo() {
		return registerNo;
	}

	public void setRegisterNo(String registerNo) {
		this.registerNo = registerNo;
	}

	public List<FreeFoodIssueTo> getToList() {
		return toList;
	}

	public void setToList(List<FreeFoodIssueTo> toList) {
		this.toList = toList;
	}
	
	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public Boolean getIsEligible() {
		return isEligible;
	}

	public void setIsEligible(Boolean isEligible) {
		this.isEligible = isEligible;
	}

	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}
	

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
	public void reset() {
		this.toList=null;
		this.registerNo=null;
		this.isEligible=false;
		this.studentId=0;
        this.flag=false;
        this.flag1=false;
        this.foodIssuedTimes=0;
	}
	public void reset1() {
		 this.foodIssuedTimes=0;
		 this.toList=null;
	}

	public Boolean getFlag1() {
		return flag1;
	}

	public void setFlag1(Boolean flag1) {
		this.flag1 = flag1;
	}

	public int getFoodIssuedTimes() {
		return foodIssuedTimes;
	}

	public void setFoodIssuedTimes(int foodIssuedTimes) {
		this.foodIssuedTimes = foodIssuedTimes;
	}

}
