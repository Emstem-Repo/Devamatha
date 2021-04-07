package com.kp.cms.handlers.reports;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.StudentLeave;
import com.kp.cms.forms.reports.LeaveReportForm;
import com.kp.cms.helpers.reports.LeaveReportHelper;
import com.kp.cms.to.reports.LeaveReportTO;
import com.kp.cms.transactions.reports.ILeaveReportTransaction;
import com.kp.cms.transactionsimpl.reports.LeaveReportTransactionImpl;

public class LeaveReportHandler {

	private static final Log log = LogFactory.getLog(LeaveReportHandler.class);
	
	public static volatile LeaveReportHandler leaveReportHandler = null;
	
	/**
	 * This method is used to create a single instance of CurrencyMasterHandler.
	 * @return unique instance of CurrencyMasterHandler class.
	 */
	
	public static LeaveReportHandler getInstance() {
		if (leaveReportHandler == null) {
			leaveReportHandler = new LeaveReportHandler();
			return leaveReportHandler;
		}
		return leaveReportHandler;
	}
	
	public List<LeaveReportTO> getLeaveReportDetails(LeaveReportForm leaveReportForm) throws Exception{
		log.info("entered getLeaveReportDetails method of LeaveReportHandler class.");
		ILeaveReportTransaction transaction = LeaveReportTransactionImpl.getInstance();
		List<StudentLeave> list = transaction.getLeaveReportDetails(LeaveReportHelper.getSelectionSearchCriteria(leaveReportForm));
		if(list != null){
			List<LeaveReportTO> leaveList = LeaveReportHelper.convertBOstoTOs(list);
			return leaveList;
		}
		log.info("Exit of getLeaveReportDetails method of LeaveReportHandler class.");
		return new ArrayList<LeaveReportTO>();
	}
		
}