package com.kp.cms.handlers.inventory;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.InvCounter;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.inventory.CounterMasterForm;
import com.kp.cms.helpers.inventory.CounterMasterHelper;
import com.kp.cms.to.inventory.InvCounterTO;
import com.kp.cms.transactions.inventory.ICounterMasterTransaction;
import com.kp.cms.transactionsimpl.inventory.CounterMasterTransactionImpl;

public class CounterMasterHandler {
	private static final Log log = LogFactory.getLog(CounterMasterHandler.class);
	public static volatile CounterMasterHandler counterMasterHandler = null;

	public static CounterMasterHandler getInstance() {
		if (counterMasterHandler == null) {
			counterMasterHandler = new CounterMasterHandler();
			return counterMasterHandler;
		}
		return counterMasterHandler;
	}
	/**
	 * @return list of InvCounterTO objects, will be used in UI to dispaly.
	 * @throws Exception
	 */
	public List<InvCounterTO> getCounterMasterDetails() throws Exception {
		log.debug("inside getCounterMasterDetails");
		ICounterMasterTransaction iCounterMasterTransaction = CounterMasterTransactionImpl.getInstance();
		List<InvCounter> counterList = iCounterMasterTransaction.getCounterDetails();
		List<InvCounterTO> invCounterTOList = CounterMasterHelper.getInstance().copyInvCounterBosToTos(counterList); 
		log.debug("leaving getCounterMasterDetails");
		return invCounterTOList;
	}	
	
	/**
	 * 
	 * @param counterMasterForm
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public boolean addCounterMaster(CounterMasterForm counterMasterForm, String mode) throws Exception {
		log.debug("inside addCounterMaster");
		ICounterMasterTransaction iTransaction = CounterMasterTransactionImpl.getInstance();
		boolean isAdded = false;
		
		InvCounter duplInvCounter = iTransaction.isCounterDuplcated(counterMasterForm);  

		if (duplInvCounter != null && duplInvCounter.getIsActive()) {
			throw new DuplicateException();
		}
		else if (duplInvCounter != null && !duplInvCounter.getIsActive())
		{
			counterMasterForm.setDuplId(duplInvCounter.getId());
			throw new ReActivateException();
		}		
				
		 InvCounter invCounter = CounterMasterHelper.getInstance().copyDataFromFormToBO(counterMasterForm,mode); 
		isAdded = iTransaction.addCounterMaster(invCounter, mode); 
		log.debug("leaving addCounterMaster");
		return isAdded;
	}

	/**
	 * 
	 * @param
	 * 
	 * @return boolean true / false based on result.
	 */

	public boolean deleteCounter(int id, Boolean activate, CounterMasterForm counterMasterForm) throws Exception {
		ICounterMasterTransaction iCounterMasterTransaction = CounterMasterTransactionImpl.getInstance();
		return iCounterMasterTransaction.deleteCounter(id, activate, counterMasterForm);
	}
	/**
	 * @param counterMasterForm
	 * @return
	 * @throws Exception
	 */
	public boolean checkCurrentNoExists(CounterMasterForm counterMasterForm) throws Exception {
		String query=CounterMasterHelper.getInstance().checkCurrentNoExistsQuery(counterMasterForm);
		ICounterMasterTransaction transaction = CounterMasterTransactionImpl.getInstance();
		return transaction.isAlreadyExists(query);
	}
	/**
	 * @param id
	 * @param counterMasterForm
	 * @return
	 * @throws Exception
	 */
	public void editCounter(int id, CounterMasterForm counterMasterForm) throws Exception {
		ICounterMasterTransaction transaction = CounterMasterTransactionImpl.getInstance();
		InvCounter bo=transaction.getCounterDetails(id);
		CounterMasterHelper.convertBotoTo(bo,counterMasterForm);
	}
	
}
