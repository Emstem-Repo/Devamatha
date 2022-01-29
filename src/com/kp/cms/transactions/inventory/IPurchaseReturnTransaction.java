package com.kp.cms.transactions.inventory;

import java.util.Set;

import com.kp.cms.bo.admin.InvItemStock;
import com.kp.cms.bo.admin.InvPurchaseOrder;
import com.kp.cms.bo.admin.InvPurchaseReturn;

/**
 * txn interface for purchase return
 *
 */
public interface IPurchaseReturnTransaction {

	InvPurchaseOrder getPurchaseorderDetails(String purchaseOrderNo) throws Exception ;
	int placeFinalPurchaseReturn(InvPurchaseReturn finalOrder, Set<InvItemStock> itemStocks) throws Exception;
	double getAlreadyReturnedQty(int itemId, int purchaseOrderId) throws Exception;
}
