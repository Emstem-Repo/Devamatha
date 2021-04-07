package com.kp.cms.handlers.reports;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.forms.reports.BelowRequiredPercentageReportForm;
import com.kp.cms.helpers.reports.BelowRequiredPercentageHelper;
import com.kp.cms.to.reports.BelowRequiredPercentageTO;
import com.kp.cms.transactions.reports.IBelowRequiredPercentageTransaction;
import com.kp.cms.transactionsimpl.reports.BelowRequiredPercentageTransactionImpl;

public class BelowRequiredPercentageHandler {

	private static final Log log = LogFactory.getLog(BelowRequiredPercentageHandler.class);
	public static volatile BelowRequiredPercentageHandler self=null;
	/**
	 * @return
	 * This method gives instance of this method
	 */
	public static BelowRequiredPercentageHandler getInstance(){
		if(self==null)
			self= new BelowRequiredPercentageHandler();
		return self;
	}
	/**
	 * 
	 */
	private BelowRequiredPercentageHandler(){
		
	}
	/**
	 * @param BelowRequiredPercentageReportForm
	 * @return
	 * @throws Exception
	 * This method will give the candidates based on search criteria
	 */
	public List<BelowRequiredPercentageTO> getStudentAttendanceResults(
			BelowRequiredPercentageReportForm requiredPercentageReportForm) throws Exception {
		log.info("entered getStudentAttendanceResults..");
		IBelowRequiredPercentageTransaction transaction = BelowRequiredPercentageTransactionImpl.getInstance(); 		
		List attendanceResults = transaction.getStudentAttendance(BelowRequiredPercentageHelper.getSelectionSearchCriteria(requiredPercentageReportForm));
		List<BelowRequiredPercentageTO> studentSearchTo = BelowRequiredPercentageHelper.populateBoToTo(attendanceResults,requiredPercentageReportForm);
		log.info("exit getStudentAttendanceResults..");
		return studentSearchTo;
	}
}
