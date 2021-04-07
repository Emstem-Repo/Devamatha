package com.kp.cms.handlers.admin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.kp.cms.bo.admin.BankMis;
import com.kp.cms.to.admin.BankMISTO;
import com.kp.cms.transactions.admin.IBankMISTransaction;
import com.kp.cms.transactionsimpl.admin.BankMISTransactionImpl;

public class BankMISHandler {

	
	private static final Logger log=Logger.getLogger(BankMISHandler.class);	
	public static volatile BankMISHandler bankMISHandler = null;
    private BankMISHandler(){
    	
    }
	/**
	 * This method is used to create a single instance of CurrencyMasterHandler.
	 * @return unique instance of CurrencyMasterHandler class.
	 */
	
	public static BankMISHandler getInstance() {
		if (bankMISHandler == null) {
			bankMISHandler = new BankMISHandler();
			return bankMISHandler;
		}
		return bankMISHandler;
	}
	IBankMISTransaction transaction = BankMISTransactionImpl.getInstance();
	
	public List getRefNos() throws Exception {
		List refList = transaction.getRefNos();

		return refList;
	}
	
	public boolean saveBankDetails(List<BankMISTO> bankDetailsList) throws Exception {
		
		boolean isUploaded = false;
		if(bankDetailsList !=null && bankDetailsList.size() != 0){
		
			isUploaded = transaction.saveBankDetails(bankDetailsList);
			
		}
		
		return isUploaded;
	}
	
}
