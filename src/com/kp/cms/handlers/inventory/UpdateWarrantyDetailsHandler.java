package com.kp.cms.handlers.inventory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.InvAmc;
import com.kp.cms.bo.admin.InvStockReceipt;
import com.kp.cms.bo.admin.InvStockReceiptItem;
import com.kp.cms.forms.inventory.StockReceiptForm;
import com.kp.cms.helpers.inventory.UpdateWarrantyDetailsHelper;
import com.kp.cms.to.inventory.InvStockRecieptItemTo;
import com.kp.cms.transactions.inventory.IUpdateWarrantyDetailsTransaction;
import com.kp.cms.transactionsimpl.inventory.UpdateWarrantyDetailsTransactionImpl;

public class UpdateWarrantyDetailsHandler {
	private static final Log log = LogFactory.getLog(UpdateWarrantyDetailsHandler.class);
	public static volatile UpdateWarrantyDetailsHandler updateWarrantyDetailsHandler = null;
	
	public static UpdateWarrantyDetailsHandler getInstance() {
		if (updateWarrantyDetailsHandler == null) {
			updateWarrantyDetailsHandler = new UpdateWarrantyDetailsHandler();
			return updateWarrantyDetailsHandler;
		}
		return updateWarrantyDetailsHandler;
	}
	
	/**
	 * @return list of InvAmcTO objects, will be used in UI to dispaly.
	 * @throws Exception
	 */
	public List<InvStockRecieptItemTo> getReceiptDetails(String orderNo) throws Exception {
		log.debug("inside getReceiptDetails");
		IUpdateWarrantyDetailsTransaction iUTransaction = UpdateWarrantyDetailsTransactionImpl.getInstance();
		List<InvStockReceipt> stockList = iUTransaction.getStockReceiptItems(orderNo);
		Iterator<InvStockReceipt> iterator = stockList.iterator();
		InvStockReceipt invStockReceipt;
		InvStockReceiptItem invStockReceiptItem;
		List<InvStockReceiptItem> newStockList  = new ArrayList<InvStockReceiptItem>();
		
		while (iterator.hasNext()){
			invStockReceipt = iterator.next();
			Set<InvStockReceiptItem> invStockReceiptItemSet = invStockReceipt.getInvStockReceiptItems();
			Iterator<InvStockReceiptItem> itemItr = invStockReceiptItemSet.iterator();
			while (itemItr.hasNext()){
				invStockReceiptItem = itemItr.next();
				if(invStockReceiptItem.getInvAmcs() == null || invStockReceiptItem.getInvAmcs().size() == 0){
					newStockList.add(invStockReceiptItem);
				}
			}
			
		}
		
		List<InvStockRecieptItemTo> receiptList = UpdateWarrantyDetailsHelper.getInstance().copyBosToTos(newStockList); 
		log.debug("leaving getReceiptDetails");
		return receiptList;
	}
	
	
	/*
	save method
	*/

	public boolean saveWarrantyDetails(StockReceiptForm stockReceiptForm) throws Exception {
		log.debug("inside saveWarrantyDetails");
		IUpdateWarrantyDetailsTransaction iTransaction = UpdateWarrantyDetailsTransactionImpl.getInstance();
		boolean isAdded;
		List<InvAmc> amcList =UpdateWarrantyDetailsHelper.getInstance().populateWarrantyDetails(stockReceiptForm);;
		isAdded = iTransaction.addWarrantyDetails(amcList);
		log.debug("leaving saveWarrantyDetails");
		return isAdded;
	}
		
}
