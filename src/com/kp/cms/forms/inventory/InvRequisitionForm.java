package com.kp.cms.forms.inventory;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.SingleFieldMasterTO;
import com.kp.cms.to.inventory.InvRequestTO;
import com.kp.cms.to.inventory.ItemTO;

public class InvRequisitionForm extends BaseActionForm  {
	private String pageType;
	private Map<String, ItemTO> itemMap;
	private Set<String> itemIdSet;
	private String requisitionNo;
	private String detailId;
	private InvRequestTO invRequestTO; 
	private List<SingleFieldMasterTO> inventoryList;
	private List<ItemTO> itemList;
	private String inventoryId;
	private String dateOfDelivery;		
	private String description;
	private String userName;
	private String searchItem;
	private String itemId;
	private String quantityIssued;
	private List<ItemTO> transferItemList;
	
	private List<InvRequestTO> requistionApprovalList;

	public List<SingleFieldMasterTO> getInventoryList() {
		return inventoryList;
	}
	public String getInventoryId() {
		return inventoryId;
	}
	public String getDateOfDelivery() {
		return dateOfDelivery;
	}
	public String getDescription() {
		return description;
	}
	public void setInventoryList(List<SingleFieldMasterTO> inventoryList) {
		this.inventoryList = inventoryList;
	}
	public void setInventoryId(String inventoryId) {
		this.inventoryId = inventoryId;
	}
	public void setDateOfDelivery(String dateOfDelivery) {
		this.dateOfDelivery = dateOfDelivery;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<ItemTO> getItemList() {
		return itemList;
	}
	public void setItemList(List<ItemTO> itemList) {
		this.itemList = itemList;
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
	public String getSearchItem() {
		return searchItem;
	}
	public String getItemId() {
		return itemId;
	}
	public String getQuantityIssued() {
		return quantityIssued;
	}
	public List<ItemTO> getTransferItemList() {
		return transferItemList;
	}
	public void setSearchItem(String searchItem) {
		this.searchItem = searchItem;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public void setQuantityIssued(String quantityIssued) {
		this.quantityIssued = quantityIssued;
	}
	public void setTransferItemList(List<ItemTO> transferItemList) {
		this.transferItemList = transferItemList;
	}

	public Map<String, ItemTO> getItemMap() {
		return itemMap;
	}
	public void setItemMap(Map<String, ItemTO> itemMap) {
		this.itemMap = itemMap;
	}
	public Set<String> getItemIdSet() {
		return itemIdSet;
	}
	public void setItemIdSet(Set<String> itemIdSet) {
		this.itemIdSet = itemIdSet;
	}
	public String getPageType() {
		return pageType;
	}
	public void setPageType(String pageType) {
		this.pageType = pageType;
	}
	public String getRequisitionNo() {
		return requisitionNo;
	}
	public void setRequisitionNo(String requisitionNo) {
		this.requisitionNo = requisitionNo;
	}
	public void resetFields() {
		this.inventoryId = null;
		this.dateOfDelivery = null;
		this.description = null;
		this.itemId = null;
		this.requisitionNo = null;
		this.transferItemList = null;
		this.quantityIssued = null;
		this.itemIdSet = null;
	}
	public List<InvRequestTO> getRequistionApprovalList() {
		return requistionApprovalList;
	}
	public void setRequistionApprovalList(List<InvRequestTO> requistionApprovalList) {
		this.requistionApprovalList = requistionApprovalList;
	}
	public String getDetailId() {
		return detailId;
	}
	public void setDetailId(String detailId) {
		this.detailId = detailId;
	}
	public InvRequestTO getInvRequestTO() {
		return invRequestTO;
	}
	public void setInvRequestTO(InvRequestTO invRequestTO) {
		this.invRequestTO = invRequestTO;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}
