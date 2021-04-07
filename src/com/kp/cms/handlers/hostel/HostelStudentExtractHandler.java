package com.kp.cms.handlers.hostel;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.kp.cms.to.hostel.HostelStudentExtractTo;
import com.kp.cms.helpers.hostel.HostelStudentExtractHelper;
import com.kp.cms.forms.hostel.HostelStudentExtractForm;
import com.kp.cms.helpers.hostel.HostelReqReportHelper;
import com.kp.cms.transactions.hostel.IHostelStudentExtractTransactions;
import com.kp.cms.transactionsimpl.hostel.HostelStudentExtractTransactionImpl;

public class HostelStudentExtractHandler {
	
	private static Log log = LogFactory.getLog(HostelReqReportHandler.class);
	private static volatile HostelStudentExtractHandler hostelStudentExtractHandler = null;
	IHostelStudentExtractTransactions transaction = HostelStudentExtractTransactionImpl.getInstance();

	private HostelStudentExtractHandler() {
	}

	/**
	 * This method returns the single instance of this HostelReqReportHandler.
	 * 
	 * @return
	 */
	public static HostelStudentExtractHandler getInstance() {
		if (hostelStudentExtractHandler == null) {
			hostelStudentExtractHandler = new HostelStudentExtractHandler();
		}
		return hostelStudentExtractHandler;
	}

	/**
	 * 
	 * @param HostelReqReportForm
	 * @return
	 * @throws Exception
	 */
	public List<HostelStudentExtractTo> getStudentExtractDetails(HostelStudentExtractForm hostelStudentExtractForm) throws Exception {
		log.info("Entering getStudentExtractDetails of HostelStudentExtractHandler");
		HostelStudentExtractHelper hostelStudentExtractHelper = HostelStudentExtractHelper.getInstance();
		List<Object> hostelExtractDetailsBo = transaction.getStudentExtractDetailsBo(hostelStudentExtractHelper.getSearchCriteria(hostelStudentExtractForm));
		List<HostelStudentExtractTo> hostelStudentExtractTo = hostelStudentExtractHelper.convertBOtoTO(hostelExtractDetailsBo,hostelStudentExtractForm );
		log.info("Exiting getStudentExtractDetails of HostelStudentExtractHandler");
		return hostelStudentExtractTo;
	}
	

}
