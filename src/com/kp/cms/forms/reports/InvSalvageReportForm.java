package com.kp.cms.forms.reports;

import java.util.List;
import java.util.Map;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.SingleFieldMasterTO;
import com.kp.cms.to.inventory.ItemTO;

public class InvSalvageReportForm extends BaseActionForm{
	
	private String method;
	private List<SingleFieldMasterTO> inventoryList;
	private List<ItemTO> itemList;
	private String searchItem;
	private Map<String, ItemTO> itemMap;

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
	
	public void reset(){
		super.clear();
		this.inventoryList = null;
		this.itemMap = null;
		this.searchItem = null;
	}
	
}