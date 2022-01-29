package com.kp.cms.forms.inventory;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;

public class CashPurchaseReportForm extends BaseActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String startDate;
	private String endDate;
	private String invLocationId;
	private String inLocationName;
	
	public String getStartDate() {
		return startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public String getInvLocationId() {
		return invLocationId;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public void setInvLocationId(String invLocationId) {
		this.invLocationId = invLocationId;
	}
	public String getInLocationName() {
		return inLocationName;
	}
	public void setInLocationName(String inLocationName) {
		this.inLocationName = inLocationName;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	public void resetFields() {
		startDate = null;
		endDate = null;
		invLocationId = null;
	}	
}
