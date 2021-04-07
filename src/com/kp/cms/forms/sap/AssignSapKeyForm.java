package com.kp.cms.forms.sap;

import java.util.List;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.sap.SapKeysTo;

public class AssignSapKeyForm extends BaseActionForm{
	private int id;
	private String startDate;
	private String endDate;
	private String status;
	private List<SapKeysTo> sapKeysTos;
	
	public List<SapKeysTo> getSapKeysTos() {
		return sapKeysTos;
	}
	public void setSapKeysTos(List<SapKeysTo> sapKeysTos) {
		this.sapKeysTos = sapKeysTos;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
