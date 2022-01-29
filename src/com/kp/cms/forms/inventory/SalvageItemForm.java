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

public class SalvageItemForm extends BaseActionForm{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String method;
	private String inventoryLocation;
	private String salvageDate;
	private String quantity;
	private String remarks;
	private List<SingleFieldMasterTO> inventoryList;
	private List<ItemTO> itemList;
	private String searchItem;
	private List<InvSalvageTO> itemtransferList;
	private Map<String, InvSalvageTO> itemMap;
	private Set<String> itemIdSet;
	private List<InvTxTO> transactions;
	
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

	public String getSalvageDate() {
		return salvageDate;
	}

	public void setSalvageDate(String salvageDate) {
		this.salvageDate = salvageDate;
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
		this.salvageDate = null;
		this.remarks = null;
		this.itemIdSet = null;
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

}