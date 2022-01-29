package com.kp.cms.handlers.reports;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.FeeDivision;
import com.kp.cms.exceptions.DataNotFoundException;
import com.kp.cms.forms.reports.DivisionReportForm;
import com.kp.cms.helpers.reports.DivisionReportHelper;
import com.kp.cms.to.fee.FeeDivisionTO;
import com.kp.cms.transactions.reports.IDivisionReportTransaction;
import com.kp.cms.transactionsimpl.reports.DivisionReportTransactionImpl;

public class DivisionReportHandler {
	private static final Log log = LogFactory.getLog(DivisionReportHandler.class);
	public static volatile DivisionReportHandler divisionReportHandler = null;

	public static DivisionReportHandler getInstance() {
		if (divisionReportHandler == null) {
			divisionReportHandler = new DivisionReportHandler();
			return divisionReportHandler;
		}
		return divisionReportHandler;
	}

	
	/**
	 * 
	 * @param stickerForm
	 * @throws Exception
	 */
	
	public List<FeeDivisionTO> getFeeDivisionWithAccounts(DivisionReportForm divisionReportForm ) throws Exception {
		log.debug("inside getFeeDivisionWithAccounts");
		IDivisionReportTransaction iTransaction = DivisionReportTransactionImpl.getInstance();
		
		List<FeeDivision> divList = iTransaction.getFeeDivisions(divisionReportForm.getDivId());
		List<FeeDivisionTO> divToList = DivisionReportHelper.getInstance().copyBosToTO(divList, divisionReportForm);
		if(divToList== null || divToList.size() <= 0){
			throw new DataNotFoundException();
		}
		return divToList;
	}
}
