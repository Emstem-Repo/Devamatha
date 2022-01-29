package com.kp.cms.handlers.reports;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.BankMis;
import com.kp.cms.forms.reports.MISComparisionReportForm;
import com.kp.cms.helpers.reports.MISComparisionReportHelper;
import com.kp.cms.to.reports.MISComparisionReportTO;
import com.kp.cms.transactions.reports.IMISComparisionReportTransaction;
import com.kp.cms.transactionsimpl.reports.MISComparisionReportTxnImpl;
import com.kp.cms.utilities.CommonUtil;

public class MISComparisionReportHandler {

private static final Log log = LogFactory.getLog(MISComparisionReportHandler.class);
	
	private static volatile MISComparisionReportHandler misComparisionReportHandler= null;
	
	private MISComparisionReportHandler(){
	}
	
	/**
	 * This method returns the single instance of this MISComparisionReportHandler.
	 * @return
	 */
	public static MISComparisionReportHandler getInstance() {
      if(misComparisionReportHandler == null) {
    	  misComparisionReportHandler = new MISComparisionReportHandler();
      }
      return misComparisionReportHandler;
	}
	
	public List<MISComparisionReportTO> getMISComparisionReport(
			MISComparisionReportForm comparisionReportForm) throws Exception {
		log.info("Entered getMISComparisionReport");
		IMISComparisionReportTransaction misComparision = new MISComparisionReportTxnImpl();
		MISComparisionReportHelper comparisionReportHelper = new MISComparisionReportHelper();
		
		List<AdmAppln> detailsFromApplication = misComparision.getComparisionDetailsFromApplication(CommonUtil.ConvertStringToSQLDate(comparisionReportForm.getTransactionDate()));
		
		List<BankMis> detailsFromBank = misComparision.getComparisionDetailsFromBank(CommonUtil.ConvertStringToSQLDate(comparisionReportForm.getTransactionDate()));
		
		List<MISComparisionReportTO> discrepancyList = comparisionReportHelper.getDiscrepancyList(detailsFromApplication, detailsFromBank);
		
		log.info("Exit getMISComparisionReport");
		return discrepancyList;
	}
}