package com.kp.cms.handlers.reports;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.ApplnDoc;
import com.kp.cms.forms.reports.CertificateDueReportForm;
import com.kp.cms.helpers.reports.CertificateDueReportHelper;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.transactions.reports.ICertificateDueReportTransaction;
import com.kp.cms.transactionsimpl.reports.CertificateDueReportTransactionImpl;

public class CertificateDueReportHandler {
	private static final Log log = LogFactory.getLog(CertificateDueReportHandler.class);
	public static volatile CertificateDueReportHandler certificateDueReportHandler = null;

	private CertificateDueReportHandler(){
	
	}
	
	/**
	 * 
	 * @returns a single instance (Singleton)every time. 
	 */
	public static CertificateDueReportHandler getInstance() {
		if (certificateDueReportHandler == null) {
			certificateDueReportHandler = new CertificateDueReportHandler();
		}
		return certificateDueReportHandler;
	}
	
	/**
	 * 
	 * @param certificateForm
	 * @returns certificate due students
	 */
	
	public List<StudentTO> getCerificateDueStudents(
			CertificateDueReportForm certificateForm) throws Exception{
		log.info("Entering into getCerificateDueStudents of CertificateDueReportHandler");
		ICertificateDueReportTransaction transaction = new CertificateDueReportTransactionImpl();
		List<ApplnDoc>applnDocBOList = transaction.getCerificateDueStudents(CertificateDueReportHelper.getInstance().getSearchQuery(certificateForm));
		log.info("Leaving into getCerificateDueStudents of CertificateDueReportHandler");
		return CertificateDueReportHelper.getInstance().prepareCertificateDueStudents(applnDocBOList);
	}
}
