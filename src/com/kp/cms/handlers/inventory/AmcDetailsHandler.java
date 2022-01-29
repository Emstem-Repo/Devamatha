package com.kp.cms.handlers.inventory;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.InvAmc;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.forms.inventory.AmcDetailsForm;
import com.kp.cms.helpers.inventory.AmcDetailsHelper;
import com.kp.cms.to.inventory.InvAmcTO;
import com.kp.cms.transactions.inventory.IAmcDetailsTransaction;
import com.kp.cms.transactionsimpl.inventory.AmcDetailsTransactionImpl;

public class AmcDetailsHandler {
	private static final Log log = LogFactory.getLog(AmcDetailsHandler.class);
	public static volatile AmcDetailsHandler amcDetailsHandler = null;
	
	public static AmcDetailsHandler getInstance() {
		if (amcDetailsHandler == null) {
			amcDetailsHandler = new AmcDetailsHandler();
			return amcDetailsHandler;
		}
		return amcDetailsHandler;
	}	
	/**
	 * @return list of InvAmcTO objects, will be used in UI to dispaly.
	 * @throws Exception
	 */
	public List<InvAmcTO> getAmcDetails(int itemCategId, String itemNo) throws Exception {
		log.debug("inside getAmcDetails");
		IAmcDetailsTransaction iAmcDetailsTransaction = AmcDetailsTransactionImpl.getInstance();
		List<InvAmc> amcList = iAmcDetailsTransaction.getAmcDetails(itemCategId, itemNo);
		List<InvAmc> warrantyList = iAmcDetailsTransaction.getWarrenty(itemCategId, itemNo);
		Iterator<InvAmc> iterator = warrantyList.iterator();
		InvAmc invAmc;
		while (iterator.hasNext()){
			invAmc = iterator.next();
			amcList.add(invAmc);
		}
		
		List<InvAmcTO> InvAmcTOList = AmcDetailsHelper.getInstance().copyBosToTos(amcList); 
		log.debug("leaving getAmcDetails");
		return InvAmcTOList;
	}	
	
	/*
	save method
	*/

	public boolean saveAmcDetails(AmcDetailsForm amcDetailsForm) throws DuplicateException, Exception {
		log.debug("inside saveRoomDetails");
		IAmcDetailsTransaction iAmcDetailsTransaction = AmcDetailsTransactionImpl.getInstance();
		boolean isAdded;
		List<InvAmc> amcList = AmcDetailsHelper.getInstance().populateAmcDetails(amcDetailsForm);
		isAdded = iAmcDetailsTransaction.addAmcDetails(amcList);
		log.debug("leaving saveAmcDetails");
		return isAdded;
	}
	

	/** amc history
	 * @return list of InvAmcTO objects, will be used in UI to dispaly.
	 * @throws Exception
	 */
	public List<InvAmcTO> getAmcHistoryDetails(int itemCategId, String itemNo) throws Exception {
		log.debug("inside getAmcDetails");
		IAmcDetailsTransaction iAmcDetailsTransaction = AmcDetailsTransactionImpl.getInstance();
		List<InvAmc> amcList = iAmcDetailsTransaction.getAmcHistoryDetails(itemCategId, itemNo);
		List<InvAmcTO> InvAmcTOList = AmcDetailsHelper.getInstance().copyHistoryBosToTos(amcList); 
		log.debug("leaving getAmcDetails");
		return InvAmcTOList;
	}	

}
