package com.kp.cms.forms.reports;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.SingleFieldMasterTO;
import com.kp.cms.to.inventory.ItemTO;

public class IssuedReceivedReportForm extends BaseActionForm{
	private String method;
	private List<SingleFieldMasterTO> inventoryList;
	private List<ItemTO> itemList;
	private String searchItem;
	private Map<String, ItemTO> itemMap;
	private String startDate;
	private String endDate;
	private String invLocationId;
	private String itemId;
	private String formName;
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	
	public List<SingleFieldMasterTO> getInventoryList() {
		return inventoryList;
	}
	public void setInventoryList(List<SingleFieldMasterTO> inventoryList) {
		this.inventoryList = inventoryList;
	}
	public List<ItemTO> getItemList() {
		return itemList;
	}
	public void setItemList(List<ItemTO> itemList) {
		this.itemList = itemList;
	}
	public String getSearchItem() {
		return searchItem;
	}
	public void setSearchItem(String searchItem) {
		this.searchItem = searchItem;
	}
	
	public Map<String, ItemTO> getItemMap() {
		return itemMap;
	}
	public void setItemMap(Map<String, ItemTO> itemMap) {
		this.itemMap = itemMap;
	}
	
	public String getStartDate() {
		return startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	public String getInvLocationId() {
		return invLocationId;
	}
	public void setInvLocationId(String invLocationId) {
		this.invLocationId = invLocationId;
	}
	
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	
	public String getFormName() {
		return formName;
	}
	public void setFormName(String formName) {
		this.formName = formName;
	}
	public void resetInventory(){
		super.clear();
		this.inventoryList = null;
		this.itemMap = null;
		this.searchItem = null;
		this.startDate = null;
		this.endDate = null;
		this.invLocationId = null;
		this.itemId = null;
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
