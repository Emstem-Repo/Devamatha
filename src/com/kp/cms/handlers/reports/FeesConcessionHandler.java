package com.kp.cms.handlers.reports;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.forms.reports.FeesConcessionReportForm;
import com.kp.cms.helpers.reports.FeesConcessionHelper;
import com.kp.cms.to.reports.FeeConcessionReportTO;
import com.kp.cms.transactions.reports.IFeesConcessionReportTxn;
import com.kp.cms.transactionsimpl.reports.FeesConcessionReportTxnImpl;

public class FeesConcessionHandler {
	private static final Log log = LogFactory.getLog(FeesConcessionHandler.class);
	public static volatile FeesConcessionHandler feesConcessionHandler = null;

	public static FeesConcessionHandler getInstance() {
		if (feesConcessionHandler == null) {
			feesConcessionHandler = new FeesConcessionHandler();
			return feesConcessionHandler;
		}
		return feesConcessionHandler;
	}
	/**
	 * 
	 * @param stickerForm
	 * @throws Exception
	 */
	
	public List<FeeConcessionReportTO> getFeePaymentDetails(FeesConcessionReportForm concessionReportForm ) throws Exception {
		log.debug("inside getFeePaymentDetails");
		IFeesConcessionReportTxn iTxn = FeesConcessionReportTxnImpl.getInstance();
		
		List<Object[]> concList = iTxn.getFeeConcessionDetails(concessionReportForm);
		List<FeeConcessionReportTO> totalConcList = FeesConcessionHelper.getInstance().copyBosToTO(concList, concessionReportForm);
		return totalConcList;
	}
	  		

}
