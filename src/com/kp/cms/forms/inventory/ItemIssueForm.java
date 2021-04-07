package com.kp.cms.forms.inventory;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.SingleFieldMasterTO;
import com.kp.cms.to.inventory.InvSalvageTO;
import com.kp.cms.to.inventory.InvTxTO;
import com.kp.cms.to.inventory.ItemTO;

public class ItemIssueForm extends BaseActionForm{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String method;
	private String inventoryLocation;
	private String issueDate;
	private String quantity;
	private String remarks;
	private List<SingleFieldMasterTO> inventoryList;
	private List<ItemTO> itemList;
	private String searchItem;
	private String itemId;
	private List<InvSalvageTO> itemtransferList;
	private Map<String, InvSalvageTO> itemMap;
	private Set<String> itemIdSet;
	private String issuedTO;
	private List<InvTxTO> transactions;
	private Map<Integer, String> locationMap;
	
	public String getIssuedTO() {
		return issuedTO;
	}
	public void setIssuedTO(String issuedTO) {
		this.issuedTO = issuedTO;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getInventoryLocation() {
		return inventoryLocation;
	}
	public void setInventoryLocation(String inventoryLocation) {
		this.inventoryLocation = inventoryLocation;
	}
	public String getIssueDate() {
		return issueDate;
	}
	public void setIssueDate(String issueDate) {
		this.issueDate = issueDate;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
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
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public List<InvSalvageTO> getItemtransferList() {
		return itemtransferList;
	}
	public void setItemtransferList(List<InvSalvageTO> itemtransferList) {
		this.itemtransferList = itemtransferList;
	}
	public Map<String, InvSalvageTO> getItemMap() {
		return itemMap;
	}
	public void setItemMap(Map<String, InvSalvageTO> itemMap) {
		this.itemMap = itemMap;
	}
	public Set<String> getItemIdSet() {
		return itemIdSet;
	}
	public void setItemIdSet(Set<String> itemIdSet) {
		this.itemIdSet = itemIdSet;
	}
	
	public void resetFields() {
		this.inventoryLocation = null;
		this.searchItem = null;
		this.quantity = null;
		this.issueDate = null;
		this.remarks = null;
		this.itemIdSet = null;
		this.issuedTO = null;
	}
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	public List<InvTxTO> getTransactions() {
		return transactions;
	}
	public void setTransactions(List<InvTxTO> transactions) {
		this.transactions = transactions;
	}
	public Map<Integer, String> getLocationMap() {
		return locationMap;
	}
	public void setLocationMap(Map<Integer, String> locationMap) {
		this.locationMap = locationMap;
	}

}
