package com.kp.cms.handlers.admission;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.OfflineDetails;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.helpers.admission.ApplicationReportHelper;
import com.kp.cms.to.admission.ApplicationReportTO;
import com.kp.cms.transactions.admission.IApplicationReportTransaction;
import com.kp.cms.transactionsimpl.admission.ApplicationReportTransactionImpl;

/**
 * 
 * @author kshirod.k
 * Handler class for Application Report
 *
 */

public class ApplicationReportHandler {

	private static final Log log = LogFactory.getLog(ApplicationReportHandler.class);

	public static volatile ApplicationReportHandler applicationReportHandler = null;

	private ApplicationReportHandler() {

	}

	IApplicationReportTransaction transaction = new ApplicationReportTransactionImpl();

	/**
	 * 
	 * @returns a single instance (Singleton)every time.
	 */

	public static ApplicationReportHandler getInstance() {
		if (applicationReportHandler == null) {
			applicationReportHandler = new ApplicationReportHandler();
			return applicationReportHandler;
		}
		return applicationReportHandler;
	}

	/**
	 * Used to get issued and received applications
	 */

	public List<ApplicationReportTO> getIssuedandReceivedApplications (int courseId, int year)throws Exception {
		log.debug("Inside ApplicationReportHandler getIssuedandReceivedApplications");
		List<OfflineDetails> issuedApplicationList = null;
		List<Student>receivedApplicationList=null;
		/**
		 * Gets the issued application no.s based on the course and year from offlinedetails table
		 */
		if (transaction != null) {
			issuedApplicationList = transaction.getIssuedApplications(courseId, year);
		}
		/**
		 * Gets the issued application no.s based on the course and year from AdmAppln table
		 */
		if(transaction != null) {
			receivedApplicationList = transaction.getreceivedApplications(courseId, year);
		}
		log.debug("End of ApplicationReportHandler getIssuedandReceivedApplications");
		return ApplicationReportHelper.getInstance().populateBOtoTO(issuedApplicationList,receivedApplicationList);
	}
}
