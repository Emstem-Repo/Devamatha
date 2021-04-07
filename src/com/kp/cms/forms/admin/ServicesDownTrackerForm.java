package com.kp.cms.forms.admin;

import java.util.List;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.MaintenanceTo;
import com.kp.cms.to.admin.ServicesDownTrackerTO;
import com.kp.cms.to.hostel.HlDisciplinaryDetailsTO;

/**
 * @author dIlIp
 *
 */
public class ServicesDownTrackerForm extends BaseActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String servicesId;
	private String date;
	private String downFrom;
	private String downTill;
	private String remarks;	
	private List<ServicesDownTrackerTO> servicesDownTrackerTOList;
	
	/*private String origServicesId;
	private String origDate;
	private String origDownFrom;
	private String origDownTill;
	private String origRemarks;*/
	
	public void reset(){
		this.id = 0;
		this.servicesId = null;
		this.date = null;
		this.downFrom = null;
		this.downTill = null;
		this.servicesDownTrackerTOList = null;
		this.remarks = null;
		
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getServicesId() {
		return servicesId;
	}


	public void setServicesId(String servicesId) {
		this.servicesId = servicesId;
	}


	public String getDate() {
		return date;
	}


	public void setDate(String date) {
		this.date = date;
	}


	public String getDownFrom() {
		return downFrom;
	}


	public void setDownFrom(String downFrom) {
		this.downFrom = downFrom;
	}


	public String getDownTill() {
		return downTill;
	}


	public void setDownTill(String downTill) {
		this.downTill = downTill;
	}


	public String getRemarks() {
		return remarks;
	}


	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}


	public List<ServicesDownTrackerTO> getServicesDownTrackerTOList() {
		return servicesDownTrackerTOList;
	}


	public void setServicesDownTrackerTOList(
			List<ServicesDownTrackerTO> servicesDownTrackerTOList) {
		this.servicesDownTrackerTOList = servicesDownTrackerTOList;
	}


	
}

