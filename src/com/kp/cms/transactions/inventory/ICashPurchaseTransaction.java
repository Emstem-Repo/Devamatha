package com.kp.cms.transactions.inventory;

import java.util.List;

import com.kp.cms.bo.admin.InvCashPurchase;

public interface ICashPurchaseTransaction {
	
	public boolean itemAdded(List<InvCashPurchase> transferItemList) throws Exception;
	
	public boolean itemAdded(InvCashPurchase invCashPurchaseItem) throws Exception;

}
