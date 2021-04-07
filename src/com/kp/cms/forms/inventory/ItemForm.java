package com.kp.cms.forms.inventory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.SingleFieldMasterTO;
import com.kp.cms.to.inventory.ItemTO;

public class ItemForm extends BaseActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<SingleFieldMasterTO> itemCategoryList;
	private List<SingleFieldMasterTO> UOMList;
	private List<ItemTO> itemList;
	private String itemCode;
	private String itemName;
	private String itemDescription;
	private String purchaseCost;
	private String originalItemCode;
	private int id;
	private boolean warranty;
	private int activationRefId;
	private String conversion;
	private String itemTypeId;
	private String itemSubCategoryId;
	private Map<Integer, String> itemSubCategoryMap;
	private List<SingleFieldMasterTO> itemTypeList;
	private String remarks;
	private int minStockQuantity;
	
	public List<SingleFieldMasterTO> getItemCategoryList() {
		return itemCategoryList;
	}
	public void setItemCategoryList(List<SingleFieldMasterTO> itemCategoryList) {
		this.itemCategoryList = itemCategoryList;
	}
	public List<SingleFieldMasterTO> getUOMList() {
		return UOMList;
	}
	public void setUOMList(List<SingleFieldMasterTO> list) {
		UOMList = list;
	}
	public List<ItemTO> getItemList() {
		return itemList;
	}
	public void setItemList(List<ItemTO> itemList) {
		this.itemList = itemList;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getItemDescription() {
		return itemDescription;
	}
	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}
	public String getPurchaseCost() {
		return purchaseCost;
	}
	public void setPurchaseCost(String purchaseCost) {
		this.purchaseCost = purchaseCost;
	}
	
	public String getOriginalItemCode() {
		return originalItemCode;
	}
	public void setOriginalItemCode(String originalItemCode) {
		this.originalItemCode = originalItemCode;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public boolean isWarranty() {
		return warranty;
	}
	public void setWarranty(boolean warranty) {
		this.warranty = warranty;
	}
	public int getActivationRefId() {
		return activationRefId;
	}
	public void setActivationRefId(int activationRefId) {
		this.activationRefId = activationRefId;
	}
	public String getConversion() {
		return conversion;
	}
	public void setConversion(String conversion) {
		this.conversion = conversion;
	}
	public String getItemTypeId() {
		return itemTypeId;
	}
	public void setItemTypeId(String itemTypeId) {
		this.itemTypeId = itemTypeId;
	}
	public String getItemSubCategoryId() {
		return itemSubCategoryId;
	}
	public void setItemSubCategoryId(String itemSubCategoryId) {
		this.itemSubCategoryId = itemSubCategoryId;
	}
	public Map<Integer, String> getItemSubCategoryMap() {
		return itemSubCategoryMap;
	}
	public void setItemSubCategoryMap(Map<Integer, String> itemSubCategoryMap) {
		this.itemSubCategoryMap = itemSubCategoryMap;
	}
	public List<SingleFieldMasterTO> getItemTypeList() {
		return itemTypeList;
	}
	public void setItemTypeList(List<SingleFieldMasterTO> itemTypeList) {
		this.itemTypeList = itemTypeList;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public int getMinStockQuantity() {
		return minStockQuantity;
	}
	public void setMinStockQuantity(int minStockQuantity) {
		this.minStockQuantity = minStockQuantity;
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
		warranty = false;
		originalItemCode = null;
		itemCode = null;
		itemName = null;
		itemDescription = null;
		purchaseCost = null;
		conversion = null;
		itemTypeId=null;
		itemSubCategoryId=null;
		remarks=null;
		minStockQuantity=0;
		itemSubCategoryMap=new HashMap<Integer, String>();
	}
}