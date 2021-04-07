package com.kp.cms.forms.inventory;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.inventory.InvItemTO;
import com.kp.cms.to.inventory.InvLocationTO;
import com.kp.cms.to.inventory.VendorTO;

public class OpeningBalanceForm extends BaseActionForm{
	private String selectedItemId;
	private String selectedItemQty;
	private String searchItem;
	private List<VendorTO> vendorList;
	private List<InvItemTO> itemList;
	private List<InvLocationTO> locations;
	private String locationId;
	
	public String getSelectedItemId() {
		return selectedItemId;
	}

	public void setSelectedItemId(String selectedItemId) {
		this.selectedItemId = selectedItemId;
	}

	public String getSelectedItemQty() {
		return selectedItemQty;
	}

	public void setSelectedItemQty(String selectedItemQty) {
		this.selectedItemQty = selectedItemQty;
	}

	public String getSearchItem() {
		return searchItem;
	}

	public void setSearchItem(String searchItem) {
		this.searchItem = searchItem;
	}

	public List<VendorTO> getVendorList() {
		return vendorList;
	}

	public void setVendorList(List<VendorTO> vendorList) {
		this.vendorList = vendorList;
	}

	public List<InvItemTO> getItemList() {
		return itemList;
	}

	public void setItemList(List<InvItemTO> itemList) {
		this.itemList = itemList;
	}

	public List<InvLocationTO> getLocations() {
		return locations;
	}

	public void setLocations(List<InvLocationTO> locations) {
		this.locations = locations;
	}

	public String getLocationId() {
		return locationId;
	}

	public void setLocationId(String locationId) {
		this.locationId = locationId;
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
}
