package com.kp.cms.handlers.reports;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.FeeVoucher;
import com.kp.cms.exceptions.DataNotFoundException;
import com.kp.cms.forms.reports.ConcessionSlipBookReportForm;
import com.kp.cms.helpers.reports.ConcessionSlipBookReportHelper;
import com.kp.cms.to.reports.ConcessionSlipBookReportTO;
import com.kp.cms.transactions.reports.IConcessionSlipBookReportTxn;
import com.kp.cms.transactionsimpl.reports.ConcessionSlipBookReportTxnImpl;

public class ConcessionSlipBookReportHandler {
	private static final Log log = LogFactory.getLog(ConcessionSlipBookReportHandler.class);
	public static volatile ConcessionSlipBookReportHandler concessionSlipBookReportHandler = null;

	public static ConcessionSlipBookReportHandler getInstance() {
		if (concessionSlipBookReportHandler == null) {
			concessionSlipBookReportHandler = new ConcessionSlipBookReportHandler();
			return concessionSlipBookReportHandler;
		}
		return concessionSlipBookReportHandler;
	}

	
	/**
	 * 
	 * @param stickerForm
	 * @throws Exception
	 */
	
	public List<ConcessionSlipBookReportTO> getConcessionSlipBookDetails(ConcessionSlipBookReportForm slipBookReportForm ) throws Exception {
		log.debug("inside getConcessionSlipBookDetails");
		IConcessionSlipBookReportTxn slipBookReportTxn = ConcessionSlipBookReportTxnImpl.getInstance();
		
		List<FeeVoucher> concList = slipBookReportTxn.getSlipBookDetails(slipBookReportForm.getType());
		List<ConcessionSlipBookReportTO> ConcList = ConcessionSlipBookReportHelper.getInstance().copyBosToTO(concList);
		if(ConcList== null || ConcList.size() <= 0){
			throw new DataNotFoundException();
		}
		return ConcList;
	}
	
}
