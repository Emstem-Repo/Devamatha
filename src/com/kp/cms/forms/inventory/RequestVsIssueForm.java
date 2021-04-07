package com.kp.cms.forms.inventory;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.SingleFieldMasterTO;
import com.kp.cms.to.inventory.RequestVsIssueTO;

public class RequestVsIssueForm extends BaseActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<SingleFieldMasterTO> inventoryLocationList;
	private List<RequestVsIssueTO> requestVsIssueList;
	private String invLocationName;
	private String startDate;
	private String endDate;
	
	public List<SingleFieldMasterTO> getInventoryLocationList() {
		return inventoryLocationList;
	}
	public void setInventoryLocationList(
			List<SingleFieldMasterTO> inventoryLocationList) {
		this.inventoryLocationList = inventoryLocationList;
	}
	public List<RequestVsIssueTO> getRequestVsIssueList() {
		return requestVsIssueList;
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
	public void setRequestVsIssueList(List<RequestVsIssueTO> requestVsIssueList) {
		this.requestVsIssueList = requestVsIssueList;
	}
	
	public String getInvLocationName() {
		return invLocationName;
	}
	public void setInvLocationName(String invLocationName) {
		this.invLocationName = invLocationName;
	}
	@Override
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	
	@Override
	public void clear() {
		super.clear();
		this.inventoryLocationList = null;
		this.startDate = null;
		this.endDate = null;
	}
}
