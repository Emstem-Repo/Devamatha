package com.kp.cms.forms.inventory;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.inventory.InvPurchaseOrderItemTO;
import com.kp.cms.to.inventory.InvPurchaseOrderTO;
import com.kp.cms.to.inventory.ItemTO;
import com.kp.cms.to.inventory.VendorTO;

public class VendorWisePOForm extends BaseActionForm{
	private String method;
	private String vendorId;
	private List<VendorTO> vendorList;
	private int purchaseOrderId;
	private List<InvPurchaseOrderItemTO> poItemList;
	private List<InvPurchaseOrderTO> orderNoList;
	private String purchaseOrderNo;
	private int puchaseReturnId;
	private List<ItemTO> itemList;
	
	public int getPurchaseOrderId() {
		return purchaseOrderId;
	}

	public void setPurchaseOrderId(int purchaseOrderId) {
		this.purchaseOrderId = purchaseOrderId;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getVendorId() {
		return vendorId;
	}

	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}
	public List<VendorTO> getVendorList() {
		return vendorList;
	}

	public void setVendorList(List<VendorTO> vendorList) {
		this.vendorList = vendorList;
	}

	

	public List<InvPurchaseOrderItemTO> getPoItemList() {
		return poItemList;
	}

	public void setPoItemList(List<InvPurchaseOrderItemTO> poItemList) {
		this.poItemList = poItemList;
	}

	public List<InvPurchaseOrderTO> getOrderNoList() {
		return orderNoList;
	}

	public void setOrderNoList(List<InvPurchaseOrderTO> orderNoList) {
		this.orderNoList = orderNoList;
	}

	public String getPurchaseOrderNo() {
		return purchaseOrderNo;
	}

	public void setPurchaseOrderNo(String purchaseOrderNo) {
		this.purchaseOrderNo = purchaseOrderNo;
	}

	public int getPuchaseReturnId() {
		return puchaseReturnId;
	}

	public void setPuchaseReturnId(int puchaseReturnId) {
		this.puchaseReturnId = puchaseReturnId;
	}

	public List<ItemTO> getItemList() {
		return itemList;
	}

	public void setItemList(List<ItemTO> itemList) {
		this.itemList = itemList;
	}

	public void clear(){
		this.vendorId = null;
		this.poItemList = null;
		this.purchaseOrderNo = null;
		this.itemList = null;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
}
