package com.kp.cms.forms.inventory;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.inventory.InvPurchaseOrderTO;
import com.kp.cms.to.inventory.InvPurchaseReturnItemTO;
import com.kp.cms.to.inventory.InvTxTO;

/**
 * Form bean for Purchase return
 *
 */
public class PurchaseReturnForm extends BaseActionForm {
	private String purchaseOrderNo;
	private String purchaseOrdDate;
	private String vendorNameAddr;
	private String purchaseRtnDate;
	private String vendorBillNo;
	private String vendorBillDt;
	private String returnReason;
	private InvPurchaseOrderTO purchaseOrder;
	List<InvPurchaseReturnItemTO> returnItems;
	private List<InvTxTO> transactions;
	
	public String getPurchaseOrderNo() {
		return purchaseOrderNo;
	}

	public void setPurchaseOrderNo(String purchaseOrderNo) {
		this.purchaseOrderNo = purchaseOrderNo;
	}

	public String getPurchaseOrdDate() {
		return purchaseOrdDate;
	}

	public void setPurchaseOrdDate(String purchaseOrdDate) {
		this.purchaseOrdDate = purchaseOrdDate;
	}

	public String getVendorNameAddr() {
		return vendorNameAddr;
	}

	public void setVendorNameAddr(String vendorNameAddr) {
		this.vendorNameAddr = vendorNameAddr;
	}

	public String getPurchaseRtnDate() {
		return purchaseRtnDate;
	}

	public void setPurchaseRtnDate(String purchaseRtnDate) {
		this.purchaseRtnDate = purchaseRtnDate;
	}

	public String getVendorBillNo() {
		return vendorBillNo;
	}

	public void setVendorBillNo(String vendorBillNo) {
		this.vendorBillNo = vendorBillNo;
	}

	public String getVendorBillDt() {
		return vendorBillDt;
	}

	public void setVendorBillDt(String vendorBillDt) {
		this.vendorBillDt = vendorBillDt;
	}

	public String getReturnReason() {
		return returnReason;
	}

	public void setReturnReason(String returnReason) {
		this.returnReason = returnReason;
	}
	
	public InvPurchaseOrderTO getPurchaseOrder() {
		return purchaseOrder;
	}

	public void setPurchaseOrder(InvPurchaseOrderTO purchaseOrder) {
		this.purchaseOrder = purchaseOrder;
	}

	public List<InvPurchaseReturnItemTO> getReturnItems() {
		return returnItems;
	}

	public void setReturnItems(List<InvPurchaseReturnItemTO> returnItems) {
		this.returnItems = returnItems;
	}

	public List<InvTxTO> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<InvTxTO> transactions) {
		this.transactions = transactions;
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
