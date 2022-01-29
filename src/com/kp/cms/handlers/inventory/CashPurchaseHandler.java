package com.kp.cms.handlers.inventory;

import java.util.List;

import com.kp.cms.bo.admin.InvCashPurchase;
import com.kp.cms.forms.inventory.CashPurchaseForm;
import com.kp.cms.helpers.inventory.CashPurchaseHelper;
import com.kp.cms.transactions.inventory.ICashPurchaseTransaction;
import com.kp.cms.transactionsimpl.inventory.CashPurchaseTxnImpl;

public class CashPurchaseHandler {

	private static volatile CashPurchaseHandler cashPurchaseHandler = null;

	private CashPurchaseHandler() {
	}

	public static CashPurchaseHandler getInstance() {
		if (cashPurchaseHandler == null) {
			cashPurchaseHandler = new CashPurchaseHandler();
		}
		return cashPurchaseHandler;
	}
	
	/**
	 * 
	 * @param cashPurchaseForm
	 * @return
	 * @throws Exception
	 */
	public boolean getItemAdded(CashPurchaseForm cashPurchaseForm) throws Exception{
		ICashPurchaseTransaction cashPurchaseTransaction = new CashPurchaseTxnImpl();
		CashPurchaseHelper cashPurchaseHelper = new CashPurchaseHelper();
		boolean itemAdded = false;
		
		if(cashPurchaseForm.getTransferItemList()!=null && !cashPurchaseForm.getTransferItemList().isEmpty()){
			List<InvCashPurchase> cashPurchaseList = cashPurchaseHelper.convertTOtoBO(cashPurchaseForm.getTransferItemList(),cashPurchaseForm.getUserId());	
			itemAdded = cashPurchaseTransaction.itemAdded(cashPurchaseList);
		}else{
			InvCashPurchase invCashPurchase = cashPurchaseHelper.convertTOtoBO(cashPurchaseForm);
			itemAdded = cashPurchaseTransaction.itemAdded(invCashPurchase);
		}
		return itemAdded;
	}
}