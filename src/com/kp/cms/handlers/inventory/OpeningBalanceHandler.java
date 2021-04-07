package com.kp.cms.handlers.inventory;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.forms.inventory.OpeningBalanceForm;
import com.kp.cms.transactions.inventory.IOpeningBalanceTransaction;
import com.kp.cms.transactionsimpl.inventory.OpeningBalanceTransaction;
import com.kp.cms.utilities.CommonUtil;

public class OpeningBalanceHandler {
private static final Log log = LogFactory.getLog(OpeningBalanceHandler.class);
	
	public static volatile OpeningBalanceHandler self=null;
	public static OpeningBalanceHandler getInstance(){
		if(self==null){
			self= new OpeningBalanceHandler();
		}
		return self;
	}
	private OpeningBalanceHandler(){
		
	}
	public boolean saveOpeningBalance(OpeningBalanceForm balanceForm) throws Exception {
		boolean result=false;
		IOpeningBalanceTransaction txn= new OpeningBalanceTransaction();
		int locationID=0;
		int itemID=0;
		double qty=0.0;
		if(balanceForm.getLocationId()!=null && !StringUtils.isEmpty(balanceForm.getLocationId())&& StringUtils.isNumeric(balanceForm.getLocationId()))
			locationID=Integer.parseInt(balanceForm.getLocationId());
		if(balanceForm.getSelectedItemId()!=null && !StringUtils.isEmpty(balanceForm.getSelectedItemId())&& StringUtils.isNumeric(balanceForm.getSelectedItemId()))
			itemID=Integer.parseInt(balanceForm.getSelectedItemId());
		
		if(balanceForm.getSelectedItemQty()!=null && !StringUtils.isEmpty(balanceForm.getSelectedItemQty())&& CommonUtil.isValidDecimal(balanceForm.getSelectedItemQty()))
			qty=Double.parseDouble(balanceForm.getSelectedItemQty());
		
		
		result=txn.saveItemStock(locationID,itemID,qty,balanceForm.getUserId());
		return result;
	}
}
