package com.kp.cms.handlers.inventory;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.InvAmc;
import com.kp.cms.forms.inventory.AmcDueIn30DaysForm;
import com.kp.cms.helpers.inventory.AmcDetailsHelper;
import com.kp.cms.to.inventory.InvAmcTO;
import com.kp.cms.transactions.inventory.IAmcDueIn30DaysReport;
import com.kp.cms.transactionsimpl.inventory.AmcDueIn30DaysTransactionImpl;

public class AmcDueIn30DaysHandler {
	private static final Log log = LogFactory.getLog(AmcDueIn30DaysHandler.class);
	public static volatile AmcDueIn30DaysHandler amcDueIn30DaysHandler = null;
	
	public static AmcDueIn30DaysHandler getInstance() {
		if (amcDueIn30DaysHandler == null) {
			amcDueIn30DaysHandler = new AmcDueIn30DaysHandler();
			return amcDueIn30DaysHandler;
		}
		return amcDueIn30DaysHandler;
	}	
	
	/**
	 * @return list of InvAmcTO objects, will be used in UI to dispaly.
	 * @throws Exception
	 */
	public List<InvAmcTO> getAmcDetails(Date startDate, Date endDate) throws Exception {
		log.debug("inside getAmcDetails");
		IAmcDueIn30DaysReport iAmcDueIn30DaysReport = AmcDueIn30DaysTransactionImpl.getInstance();
		List<InvAmc> amcList = iAmcDueIn30DaysReport.getAmcDueIn30Days(startDate, endDate, 0);
		List<InvAmcTO> InvAmcTOList = AmcDetailsHelper.getInstance().copyHistoryBosToTos(amcList); 
		log.debug("leaving getAmcDetails");
		return InvAmcTOList;
	}
	
	/**
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	public List<InvAmcTO> getWarrantyExpiryDetails(Date fromDate, Date toDate, int invLocationId) throws Exception {
		log.debug("inside getWarrantyExpiryDetails");
		IAmcDueIn30DaysReport iAmcDueIn30DaysReport = AmcDueIn30DaysTransactionImpl.getInstance();
		List<InvAmc> amcList = iAmcDueIn30DaysReport.getAmcDueIn30Days(fromDate, toDate, invLocationId);
		List<InvAmcTO> InvAmcTOList = AmcDetailsHelper.getInstance().copyWarrantyMailBosToTos(amcList); 
		log.debug("leaving getWarrantyExpiryDetails");
		return InvAmcTOList;
	}
	
	/**
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	public boolean sendWarrantyExpiryMail(AmcDueIn30DaysForm amcDueIn30DaysForm) throws Exception {
		log.debug("inside sendWarrantyExpiryMail");
		
		if(AmcDetailsHelper.getInstance().sendWarrantyExpiryMail(amcDueIn30DaysForm)){
			return true;
		}
		log.debug("leaving sendWarrantyExpiryMail");
		return false;
	}
}
