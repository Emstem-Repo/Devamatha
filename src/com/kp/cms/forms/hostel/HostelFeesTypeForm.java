package com.kp.cms.forms.hostel;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.hostel.HostelFeesTypeTo;

public class HostelFeesTypeForm extends BaseActionForm {	

	private static final long serialVersionUID = 1L;
	
	private int id;
	private String hostelFeesType;	
	private String isActive;	
	private List<HostelFeesTypeTo> hostelFeesTypeToList;
	
	public List<HostelFeesTypeTo> getHostelFeesTypeToList() {
		return hostelFeesTypeToList;
	}

	public void setHostelFeesTypeToList(List<HostelFeesTypeTo> hostelFeesTypeToList) {
		this.hostelFeesTypeToList = hostelFeesTypeToList;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getHostelFeesType() {
		return hostelFeesType;
	}

	public void setHostelFeesType(String hostelFeesType) {
		this.hostelFeesType = hostelFeesType;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	
	
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request){
		String formName=request.getParameter(CMSConstants.FORMNAME);
		ActionErrors errors=super.validate(mapping, request,formName);
		return errors;
	}
	
	public void reset(ActionMapping mapping, HttpServletRequest request){
		super.reset(mapping, request);
	}
	
	public void clear() {
		this.hostelFeesType=null;
		this.isActive=null;
	}
}
