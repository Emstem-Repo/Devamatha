package com.kp.cms.forms.inventory;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;

public class InventoryLocationForm extends BaseActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String location;
	private int id;
	private String searchEmp;
	private int duplId;
	private String origEmplId;
	private String origLocation;
	private String invCampusId;
	
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSearchEmp() {
		return searchEmp;
	}
	public void setSearchEmp(String searchEmp) {
		this.searchEmp = searchEmp;
	}
	public int getDuplId() {
		return duplId;
	}
	public void setDuplId(int duplId) {
		this.duplId = duplId;
	}
	public String getOrigEmplId() {
		return origEmplId;
	}
	public String getOrigLocation() {
		return origLocation;
	}
	public void setOrigEmplId(String origEmplId) {
		this.origEmplId = origEmplId;
	}
	public void setOrigLocation(String origLocation) {
		this.origLocation = origLocation;
	}
	
	public String getInvCampusId() {
		return invCampusId;
	}
	public void setInvCampusId(String invCampusId) {
		this.invCampusId = invCampusId;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		location=null;
		invCampusId=null;
		this.id =0;
	}
}
