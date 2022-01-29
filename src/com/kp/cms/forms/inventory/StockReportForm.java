package com.kp.cms.forms.inventory;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.inventory.InvLocationTO;
import com.kp.cms.to.inventory.InvTxTO;

/**
 *Stock report
 *
 */
public class StockReportForm extends BaseActionForm {
	private String date;
	private String locationId;
	private List<InvLocationTO> locations;
	private String locationName;
	private List<InvTxTO> itemList;
	private String txnId;
	private String selectedDate;
	private String selectedLocation;
	private List<InvTxTO> txnList;
	private String endDate;
	
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}


	public String getLocationId() {
		return locationId;
	}

	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}

	



	public List<InvTxTO> getItemList() {
		return itemList;
	}

	public void setItemList(List<InvTxTO> itemList) {
		this.itemList = itemList;
	}

	public List<InvLocationTO> getLocations() {
		return locations;
	}

	public void setLocations(List<InvLocationTO> locations) {
		this.locations = locations;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public String getTxnId() {
		return txnId;
	}

	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}

	public String getSelectedDate() {
		return selectedDate;
	}

	public void setSelectedDate(String selectedDate) {
		this.selectedDate = selectedDate;
	}

	public String getSelectedLocation() {
		return selectedLocation;
	}

	public void setSelectedLocation(String selectedLocation) {
		this.selectedLocation = selectedLocation;
	}

	public List<InvTxTO> getTxnList() {
		return txnList;
	}

	public void setTxnList(List<InvTxTO> txnList) {
		this.txnList = txnList;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/* (non-Javadoc)
	 * validation call
	 * @see org.apache.struts.validator.ValidatorForm#validate(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	
	public void resetFields(){
		this.endDate=null;
		this.date=null;
		this.locationId=null;
	}
}
