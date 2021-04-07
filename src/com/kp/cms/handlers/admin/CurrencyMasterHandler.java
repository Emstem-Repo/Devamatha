package com.kp.cms.handlers.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.kp.cms.bo.admin.Currency;
import com.kp.cms.forms.admin.CurrencyMasterForm;
import com.kp.cms.to.admin.CurrencyMasterTO;
import com.kp.cms.transactions.admin.ICurrencyMasterTransaction;
import com.kp.cms.transactionsimpl.admin.CurrencyMasterTransactionImpl;

public class CurrencyMasterHandler {

	private static final Logger log=Logger.getLogger(CurrencyMasterHandler.class);	
	public static volatile CurrencyMasterHandler currencyMasterHandler = null;

	/**
	 * This method is used to create a single instance of CurrencyMasterHandler.
	 * @return unique instance of CurrencyMasterHandler class.
	 */
	
	public static CurrencyMasterHandler getInstance() {
		if (currencyMasterHandler == null) {
			currencyMasterHandler = new CurrencyMasterHandler();
			return currencyMasterHandler;
		}
		return currencyMasterHandler;
	}
	
	/**
	 * This method will get currency details by calling IMPL class.
	 * @return list of type CurrencyMasterTO.
	 * @throws Exception
	 */
	
	public List<CurrencyMasterTO> getCurrencyMasterDetails() throws Exception{
		List<CurrencyMasterTO> currencyList = new ArrayList<CurrencyMasterTO>();
		ICurrencyMasterTransaction currencyMasterTransaction = CurrencyMasterTransactionImpl.getInstance();
		List<Currency> list = currencyMasterTransaction.getCurrencyMasters();
		Iterator<Currency> itr = list.iterator();
		Currency currency; 
		CurrencyMasterTO currencyMasterTO;
		while (itr.hasNext()) {
			currency = (Currency) itr.next();
			currencyMasterTO = new CurrencyMasterTO();
			
			currencyMasterTO.setId(currency.getId());
			currencyMasterTO.setName(currency.getName());
			currencyMasterTO.setCurrencyCode(currency.getCurrencyCode());
			currencyList.add(currencyMasterTO);
		}
		log.info("end of getCurrencyMasterDetails method in CurrencyMasterHandler class.");
		return currencyList;
	}
	
	/**
	 * This method is used to check duplicate entry by calling CurrencyMasterTransactionImpl class.
	 * @param currencyName
	 * @return Currency BO instance.
	 * @throws Exception
	 */
	
	public Currency isCurrencyNameExist(String currencyName, int currencyId) throws Exception{
		ICurrencyMasterTransaction currencyMasterTransaction = CurrencyMasterTransactionImpl.getInstance();
		Currency currency = currencyMasterTransaction.isCurrencyMasterExist(currencyName, currencyId);
		log.info("end of isCurrencyNameExist method in CurrencyMasterHandler class.");
		return currency;
	}
	
	/**
	 * This method is used to check duplicate entry by calling CurrencyMasterTransactionImpl class.
	 * @param currencyName
	 * @return Currency BO instance.
	 * @throws Exception
	 */
	
	public Currency isCurrencyShortNameExist(String currencyShortName, int currencyId) throws Exception{
		ICurrencyMasterTransaction currencyMasterTransaction = CurrencyMasterTransactionImpl.getInstance();
		Currency currency = currencyMasterTransaction.isCurrencyShortNameExist(currencyShortName, currencyId);
		log.info("end of isCurrencyNameExist method in CurrencyMasterHandler class.");
		return currency;
	}
	
	/**
	 * This method is used to insert to one record in database by calling CurrencyMasterTransactionImpl class.
	 * @param currencyMasterForm
	 * @return boolean value.
	 * @throws Exception
	 */
	
	public boolean addCurrencyMaster(CurrencyMasterForm currencyMasterForm) throws Exception{
		ICurrencyMasterTransaction currencyMasterTransaction = CurrencyMasterTransactionImpl.getInstance();
		Currency currency = new Currency();
		
		currency.setCreatedDate(new Date());
		currency.setCurrencyCode(currencyMasterForm.getCurrencyShortName());
		currency.setIsActive(Boolean.TRUE);
		currency.setName(currencyMasterForm.getCurrencyName());
		currency.setCreatedBy(currencyMasterForm.getUserId());
		currency.setModifiedBy(currencyMasterForm.getUserId());
		currency.setLastModifiedDate(new Date());
		boolean isAdded = currencyMasterTransaction.addCurrencyMaster(currency);
		log.info("end of addCurrencyMaster method in CurrencyMasterHandler class.");
		return isAdded;
	}
	
	/**
	 * This method is used for editing of currency master record by calling CurrencyMasterTransactionImpl class.
	 * @param id
	 * @return CurrencyMasterTO instance.
	 * @throws Exception
	 */
	
	public CurrencyMasterTO editCurrencyMaster(int currencyId) throws Exception{
		CurrencyMasterTO currencyMasterTO = new CurrencyMasterTO();
		ICurrencyMasterTransaction currencyMasterTransaction = CurrencyMasterTransactionImpl.getInstance();
		Currency currency = currencyMasterTransaction.editCurrencyMaster(currencyId);
		currencyMasterTO.setId(currency.getId());
		currencyMasterTO.setName(currency.getName());
		currencyMasterTO.setCurrencyCode(currency.getCurrencyCode());
		log.info("end of editCurrencyMaster method in CurrencyMasterHandler class.");
		return currencyMasterTO;
	}
	
	/**
	 * This method is used to update a record of currency master by calling CurrencyMasterTransactionImpl class.
	 * @param currencyMasterForm
	 * @return boolean value.
	 * @throws Exception
	 */
	
	public boolean updateCurrencyMaster(CurrencyMasterForm currencyMasterForm) throws Exception {
		ICurrencyMasterTransaction currencyMasterTransaction = CurrencyMasterTransactionImpl.getInstance();
		Currency currency = new Currency();
		currency.setId(currencyMasterForm.getCurrencyMasterId());
		currency.setCurrencyCode(currencyMasterForm.getCurrencyShortName());
		currency.setName(currencyMasterForm.getCurrencyName());
		currency.setModifiedBy(currencyMasterForm.getUserId());
		currency.setLastModifiedDate(new Date());
		currency.setIsActive(Boolean.TRUE);
		boolean isUpdated = currencyMasterTransaction.updateCurrencyMaster(currency);
		log.info("end of updateCurrencyMaster method in CurrencyMasterHandler class.");
		return isUpdated;
	}
	
	/**
	 * This method is used to delete a record by calling CurrencyMasterTransactionImpl class.
	 * @param id
	 * @param userId
	 * @return boolean value.
	 * @throws Exception
	 */
	
	public boolean deleteCurrencyMaster(int currencyId, String userId) throws Exception {
		ICurrencyMasterTransaction currencyMasterTransaction = CurrencyMasterTransactionImpl.getInstance();
		boolean isDeleted = currencyMasterTransaction.deleteCurrencyMaster(currencyId, userId);
		log.info("end of deleteCurrencyMaster method in CurrencyMasterHandler class.");
		return isDeleted;
	}
	
	/**
	 * This method is used to activate a record by calling CurrencyMasterTransactionImpl class.
	 * @param currencyName
	 * @param userId
	 * @return boolean value.
	 * @throws Exception
	 */
	
	public boolean reActivateCurrencyMaster(String currencyName, String userId) throws Exception{
		ICurrencyMasterTransaction currencyMasterTransaction = CurrencyMasterTransactionImpl.getInstance();
		boolean isReactivated = currencyMasterTransaction.reActivateCurrencyMaster(currencyName, userId);
		log.info("end of reActivateCurrencyMaster method in CurrencyMasterHandler class.");
		return isReactivated;
	}
}
