package com.kp.cms.forms.inventory;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.bo.admin.InvTx;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.SingleFieldMasterTO;
import com.kp.cms.to.inventory.ItemStockTO;
import com.kp.cms.to.inventory.ItemTO;

public class StockTransferForm extends BaseActionForm{
	
	private String method;
	private String transferDate;
	private String remarks;
	private String fromInventoryId;
	private String toInventoryId;
	private String transferNo;
	private List<SingleFieldMasterTO> inventoryList;
	private List<ItemTO>itemList;
	private String searchItem;
	private String fromInventoryName;
	private String toInventoryName;
	private String itemId;
	private String quantityIssued;
	private List<ItemTO>transferItemList;
	private Map<String, ItemTO> itemMap;
	private Set<String> itemIdSet;
	private Map<String, ItemStockTO> fromInventoryItemStockMap;
	private Map<String, ItemStockTO> toInventoryItemStockMap;
	private List<ItemStockTO> updatedItemStockList;
	private List<InvTx> invTxList;
	private String prefix;
	
	public List<InvTx> getInvTxList() {
		return invTxList;
	}

	public void setInvTxList(List<InvTx> invTxList) {
		this.invTxList = invTxList;
	}

	public List<ItemStockTO> getUpdatedItemStockList() {
		return updatedItemStockList;
	}

	public void setUpdatedItemStockList(List<ItemStockTO> updatedItemStockList) {
		this.updatedItemStockList = updatedItemStockList;
	}

	public Set<String> getItemIdSet() {
		return itemIdSet;
	}

	public void setItemIdSet(Set<String> itemIdSet) {
		this.itemIdSet = itemIdSet;
	}

	public String getTransferDate() {
		return transferDate;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setTransferDate(String transferDate) {
		this.transferDate = transferDate;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}
	public List<SingleFieldMasterTO> getInventoryList() {
		return inventoryList;
	}

	public String getFromInventoryId() {
		return fromInventoryId;
	}

	public String getToInventoryId() {
		return toInventoryId;
	}

	public void setFromInventoryId(String fromInventoryId) {
		this.fromInventoryId = fromInventoryId;
	}

	public void setToInventoryId(String toInventoryId) {
		this.toInventoryId = toInventoryId;
	}

	public void setInventoryList(List<SingleFieldMasterTO> inventoryList) {
		this.inventoryList = inventoryList;
	}

	public String getTransferNo() {
		return transferNo;
	}

	public void setTransferNo(String transferNo) {
		this.transferNo = transferNo;
	}

	public String getFromInventoryName() {
		return fromInventoryName;
	}

	public String getToInventoryName() {
		return toInventoryName;
	}

	public void setFromInventoryName(String fromInventoryName) {
		this.fromInventoryName = fromInventoryName;
	}

	public void setToInventoryName(String toInventoryName) {
		this.toInventoryName = toInventoryName;
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

	public String getQuantityIssued() {
		return quantityIssued;
	}

	public void setQuantityIssued(String quantityIssued) {
		this.quantityIssued = quantityIssued;
	}

	public List<ItemTO> getTransferItemList() {
		return transferItemList;
	}

	public void setTransferItemList(List<ItemTO> transferItemList) {
		this.transferItemList = transferItemList;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public Map<String, ItemTO> getItemMap() {
		return itemMap;
	}

	public void setItemMap(Map<String, ItemTO> itemMap) {
		this.itemMap = itemMap;
	}

	

	public Map<String, ItemStockTO> getFromInventoryItemStockMap() {
		return fromInventoryItemStockMap;
	}

	public Map<String, ItemStockTO> getToInventoryItemStockMap() {
		return toInventoryItemStockMap;
	}

	public void setFromInventoryItemStockMap(
			Map<String, ItemStockTO> fromInventoryItemStockMap) {
		this.fromInventoryItemStockMap = fromInventoryItemStockMap;
	}

	public void setToInventoryItemStockMap(
			Map<String, ItemStockTO> toInventoryItemStockMap) {
		this.toInventoryItemStockMap = toInventoryItemStockMap;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	public void clear(){
		this.transferDate = null;
		this.remarks = null;
		this.fromInventoryId = null;
		this.toInventoryId = null;
		this.fromInventoryName = null;
		this.toInventoryName = null;
		this.transferItemList = null;
		this.quantityIssued = null;
		this.itemId = null;
		this.searchItem = null;
		this.itemIdSet = null;
		this.itemList = null;
		this.itemMap = null;
		this.fromInventoryItemStockMap = null;
		this.toInventoryItemStockMap = null;
		this.updatedItemStockList = null;
		this.invTxList = null;
	}
}
