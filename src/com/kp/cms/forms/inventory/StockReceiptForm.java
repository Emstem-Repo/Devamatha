package com.kp.cms.forms.inventory;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.inventory.InvAmcTO;
import com.kp.cms.to.inventory.InvLocationTO;
import com.kp.cms.to.inventory.InvPurchaseOrderTO;
import com.kp.cms.to.inventory.InvStockRecieptItemTo;
import com.kp.cms.to.inventory.InvTxTO;
/**
 * Form bean for Goods Receipt
 *
 */
public class StockReceiptForm extends BaseActionForm {
	private String purchaseOrderNo;
	private InvPurchaseOrderTO purchaseOrder;
	private String receiptDate;
	private String inventoryId;
	private List<InvStockRecieptItemTo> receiptItems;
	private List<InvLocationTO> invLocations;
	private List<InvAmcTO> itemAmcs;
	private List<String> presentItemNos;
	private List<InvTxTO> transactions;
	
	
	public String getPurchaseOrderNo() {
		return purchaseOrderNo;
	}

	public void setPurchaseOrderNo(String purchaseOrderNo) {
		this.purchaseOrderNo = purchaseOrderNo;
	}
	
	public InvPurchaseOrderTO getPurchaseOrder() {
		return purchaseOrder;
	}

	public void setPurchaseOrder(InvPurchaseOrderTO purchaseOrder) {
		this.purchaseOrder = purchaseOrder;
	}

	public List<InvStockRecieptItemTo> getReceiptItems() {
		return receiptItems;
	}

	public void setReceiptItems(List<InvStockRecieptItemTo> receiptItems) {
		this.receiptItems = receiptItems;
	}

	public String getReceiptDate() {
		return receiptDate;
	}

	public void setReceiptDate(String receiptDate) {
		this.receiptDate = receiptDate;
	}

	public String getInventoryId() {
		return inventoryId;
	}

	public void setInventoryId(String inventoryId) {
		this.inventoryId = inventoryId;
	}

	public List<InvLocationTO> getInvLocations() {
		return invLocations;
	}

	public void setInvLocations(List<InvLocationTO> invLocations) {
		this.invLocations = invLocations;
	}

	public List<InvAmcTO> getItemAmcs() {
		return itemAmcs;
	}

	public void setItemAmcs(List<InvAmcTO> itemAmcs) {
		this.itemAmcs = itemAmcs;
	}

	public List<String> getPresentItemNos() {
		return presentItemNos;
	}

	public void setPresentItemNos(List<String> presentItemNos) {
		this.presentItemNos = presentItemNos;
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
