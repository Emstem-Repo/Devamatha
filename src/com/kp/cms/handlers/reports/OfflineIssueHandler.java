package com.kp.cms.handlers.reports;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.OfflineDetails;
import com.kp.cms.forms.reports.OfflineIssueForm;
import com.kp.cms.helpers.reports.OfflineIssueHelper;
import com.kp.cms.to.admission.OfflineDetailsTO;
import com.kp.cms.to.reports.OfflineIssueTO;
import com.kp.cms.transactions.reports.IOfflineIssueTransaction;
import com.kp.cms.transactionsimpl.reports.OfflineIssueTransactionImpl;


public class OfflineIssueHandler {
	private static final Log log = LogFactory.getLog(OfflineIssueHandler.class);
	public static volatile OfflineIssueHandler offlineIssueHandler = null;
	private OfflineIssueHandler()
	{
	}
	/**
	 * 
	 * @returns a single instance (Singleton)every time. 
	 */

	public static OfflineIssueHandler getInstance() {
		if (offlineIssueHandler == null) {
			offlineIssueHandler = new OfflineIssueHandler();
			return offlineIssueHandler;
		}
		return offlineIssueHandler;
	}
	/**
	 * Gets all the offline Details
	 */
	public List<OfflineIssueTO> getAllOfflineDetails(OfflineIssueForm offlineIssueForm)throws Exception
	{
		log.info("Entering into OfflineDetailsHandler of getAllOfflineDetails");
		IOfflineIssueTransaction transaction=new OfflineIssueTransactionImpl();
		String searchCriteria=OfflineIssueHelper.getInstance().getSearchQuery(offlineIssueForm);
		List<OfflineDetails> offlineDetailsBOList=transaction.getAllOfflineDetails(searchCriteria);
		log.info("Leaving into OfflineDetailsHandler of getAllOfflineDetails");
		return OfflineIssueHelper.getInstance().populateOfflineDetailsBOtoTO(offlineDetailsBOList);
	}
}
