package com.kp.cms.handlers.reports;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.reports.ApplicationReceivedForm;
import com.kp.cms.helpers.reports.ApplicationReceivedHelper;
import com.kp.cms.to.reports.ApplicationReceivedTO;
import com.kp.cms.transactions.reports.IApplicationReceivedTransaction;
import com.kp.cms.transactionsimpl.reports.ApplicationReceivedTransactionImpl;

public class ApplicationReceivedHandler {
	private static final Log log = LogFactory.getLog(ApplicationReceivedHandler.class);
	public static volatile ApplicationReceivedHandler applicationReceivedHandler = null;

	private ApplicationReceivedHandler(){
	
	}
	
	/**
	 * 
	 * @returns a single instance (Singleton)every time. 
	 */
	public static ApplicationReceivedHandler getInstance() {
		if (applicationReceivedHandler == null) {
			applicationReceivedHandler = new ApplicationReceivedHandler();
		}
		return applicationReceivedHandler;
	}
	/**
	 * 
	 * @param receivedForm
	 * @returnS Received applications (Online/Offline)
	 * @throws Exception
	 */
	public List<ApplicationReceivedTO> getReceivedApplications(
			ApplicationReceivedForm receivedForm)throws Exception {
		log.info("Entering into getReceivedApplications of ApplicationReceivedHandler");
		boolean isOnline = false;
		if(receivedForm.getApplicationType().equals("1")){
			isOnline = true;
			receivedForm.setReceivedApplication(CMSConstants.ONLINE_RECEIVED_APPLICATIONS);
		}
		else{
			isOnline = false;
			receivedForm.setReceivedApplication(CMSConstants.OFFLINE_RECEIVED_APPLICATIONS);
		}
		IApplicationReceivedTransaction transaction = new ApplicationReceivedTransactionImpl();
		List<Integer> appNoRangeList = transaction.getAppNoRangeList(ApplicationReceivedHelper.getInstance().createSeacrQuery(receivedForm), isOnline);
		List<AdmAppln> admApplnList = transaction.getAllAdmAppls(ApplicationReceivedHelper.getInstance().createSeacrQueryForAdmAppln(receivedForm));
		log.info("Leaving into getReceivedApplications of ApplicationReceivedHandler");
		return ApplicationReceivedHelper.getInstance().getReceivedApplications(appNoRangeList, admApplnList);
	}
}
