package com.kp.cms.forms.inventory;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.SingleFieldMasterTO;
import com.kp.cms.to.inventory.ItemTO;
import com.kp.cms.to.inventory.VendorTO;

public class CashPurchaseForm extends BaseActionForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<SingleFieldMasterTO> inventoryLocationList;
	private String comments;
	private String quantity;
	private String purchasePrice;
	private List<ItemTO> transferItemList;
	private String vendorName;
	private String itemName;
	private String date;
	private List<VendorTO> vendorList;
	private String vendorId;
	private String name;
	
	public List<VendorTO> getVendorList() {
		return vendorList;
	}
	public void setVendorList(List<VendorTO> vendorList) {
		this.vendorList = vendorList;
	}
	public List<SingleFieldMasterTO> getInventoryLocationList() {
		return inventoryLocationList;
	}
	public void setInventoryLocationList(
			List<SingleFieldMasterTO> inventoryLocationList) {
		this.inventoryLocationList = inventoryLocationList;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getPurchasePrice() {
		return purchasePrice;
	}
	public void setPurchasePrice(String purchasePrice) {
		this.purchasePrice = purchasePrice;
	}
	public List<ItemTO> getTransferItemList() {
		return transferItemList;
	}
	public void setTransferItemList(List<ItemTO> transferItemList) {
		this.transferItemList = transferItemList;
	}
	public String getVendorName() {
		return vendorName;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getVendorId() {
		return vendorId;
	}
	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
		this.vendorName = null;
		this.comments = null;
		this.itemName = null;
		this.quantity = null;
		this.purchasePrice = null;
		this.vendorId=null;
		this.name=null;
	}
}