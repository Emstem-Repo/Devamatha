package com.kp.cms.forms.inventory;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.SingleFieldMasterTO;
import com.kp.cms.to.inventory.InvAmcTO;

public class AmcDueIn30DaysForm extends BaseActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String date;
	private String fromDate;
	private String toDate;
	private List<InvAmcTO> amcTOList;
	private String templateDescription;
	private List<SingleFieldMasterTO> inventoryLocationList;
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public List<InvAmcTO> getAmcTOList() {
		return amcTOList;
	}
	public void setAmcTOList(List<InvAmcTO> amcTOList) {
		this.amcTOList = amcTOList;
	}
	public String getTemplateDescription() {
		return templateDescription;
	}
	public void setTemplateDescription(String templateDescription) {
		this.templateDescription = templateDescription;
	}
	public List<SingleFieldMasterTO> getInventoryLocationList() {
		return inventoryLocationList;
	}
	public void setInventoryLocationList(
			List<SingleFieldMasterTO> inventoryLocationList) {
		this.inventoryLocationList = inventoryLocationList;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	
	@Override
	public void clear() {
		super.clear();
		this.fromDate = null;
		this.toDate = null;
		super.setInvLocationId(null);
	}
}
