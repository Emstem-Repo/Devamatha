package com.kp.cms.transactions.inventory;

import java.util.List;

import com.kp.cms.bo.admin.InvItem;
import com.kp.cms.bo.admin.InvPurchaseOrder;
import com.kp.cms.bo.admin.InvQuotation;
import com.kp.cms.forms.inventory.PurchaseOrderForm;

/**
 * Interface for purchase order DB transactions
 *
 */
public interface IPurchaseOrderTransaction {

	String getLatestPurchaseOrderNo(String purchaseOrderCounter,PurchaseOrderForm orderForm) throws Exception;

	List<InvItem> getItemsList() throws Exception;

	boolean placeFinalPurchaseOrder(InvPurchaseOrder finalOrder,PurchaseOrderForm orderForm)throws Exception;

	List<InvItem> getItemsList(int vendorId) throws Exception;

	InvQuotation getInvQuotation(String quotationNo) throws Exception;

	InvPurchaseOrder getPOForEdit(String purchaseOrderNo) throws Exception;

}
