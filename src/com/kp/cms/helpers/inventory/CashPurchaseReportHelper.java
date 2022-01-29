package com.kp.cms.helpers.inventory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.InvCashPurchaseItem;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.to.inventory.CashPurchaseTO;
import com.kp.cms.to.inventory.InvCashPurchaseItemTO;
import com.kp.cms.utilities.CommonUtil;


public class CashPurchaseReportHelper {
	public static Log log = LogFactory.getLog(CashPurchaseReportHelper.class);
	public static volatile CashPurchaseReportHelper cashPurchaseReportHelper = null;
	public static CashPurchaseReportHelper getInstance(){
		if(cashPurchaseReportHelper == null){
			cashPurchaseReportHelper = new CashPurchaseReportHelper();  
		}
		return cashPurchaseReportHelper;
	}
	
	/**
	 * 
	 * @param itemToList
	 * @return
	 * @throws Exception
	 */
	public List<InvCashPurchaseItemTO> copyBosToTO(List<InvCashPurchaseItem> itemList) throws Exception {
		log.debug("inside copyBosToTO");
		List<InvCashPurchaseItemTO> itemToList = new ArrayList<InvCashPurchaseItemTO>();
		InvCashPurchaseItem invCashPurchaseItem;
		CashPurchaseTO cashPurchaseTO;
		Iterator<InvCashPurchaseItem> itr = itemList.iterator();
		while (itr.hasNext()){
			invCashPurchaseItem = itr.next();
			InvCashPurchaseItemTO invCashPurchaseItemTO = new InvCashPurchaseItemTO();
			cashPurchaseTO = new CashPurchaseTO(); 
			
			if(invCashPurchaseItem.getItemName()!= null && !invCashPurchaseItem.getItemName().trim().isEmpty()){
				invCashPurchaseItemTO.setItemName(invCashPurchaseItem.getItemName());
			}
			invCashPurchaseItemTO.setQuantity(invCashPurchaseItem.getQuantity().toString());
			invCashPurchaseItemTO.setPurchasePrice(invCashPurchaseItem.getPurchasePrice().toString());
			
			if(invCashPurchaseItem.getInvCashPurchase()!= null && invCashPurchaseItem.getInvCashPurchase().getVendorName()!= null 
						&& !invCashPurchaseItem.getInvCashPurchase().getVendorName().trim().isEmpty()){
				cashPurchaseTO.setVendorName(invCashPurchaseItem.getInvCashPurchase().getVendorName());
			}
			if(invCashPurchaseItem.getInvCashPurchase()!= null && invCashPurchaseItem.getInvCashPurchase().getCashPurchaseDate()!= null){
				cashPurchaseTO.setCashPurchaseDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(invCashPurchaseItem.getInvCashPurchase().getCashPurchaseDate()), CMSConstants.SOURCE_DATE ,CMSConstants.DEST_DATE));
				
			}
			if(invCashPurchaseItem.getInvCashPurchase()!= null && invCashPurchaseItem.getInvCashPurchase().getInvLocation()!= null){
				cashPurchaseTO.setLocationId(invCashPurchaseItem.getInvCashPurchase().getInvLocation().getId());
			}
			
			invCashPurchaseItemTO.setCashPurchaseTO(cashPurchaseTO);
		
			itemToList.add(invCashPurchaseItemTO);
		}
		log.debug("exit copyBosToTO");
		return itemToList;
	}	
}
