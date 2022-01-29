package com.kp.cms.handlers.reports;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.AttendanceStudent;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.reports.AttendenceFinalSummaryForm;
import com.kp.cms.helpers.reports.AttendanceFinalSummaryHelper;
import com.kp.cms.to.reports.AttendanceFinalSummaryReportTO;
import com.kp.cms.transactions.reports.IAttendanceFinalSummaryTransaction;
import com.kp.cms.transactionsimpl.reports.AttendanceFinalSummaryTransactionImpl;


public class AttendanceFinalSummaryHandler {
	
	private static volatile AttendanceFinalSummaryHandler attendanceFinalSummaryHandler = null;
	private static final Log log = LogFactory.getLog(AttendanceFinalSummaryHandler.class);
	private AttendanceFinalSummaryHandler() {

	}

	/**
	 * @return Single instance of attendanceFinalSummaryHandler object.
	 */
	public static AttendanceFinalSummaryHandler getInstance() {
		if (attendanceFinalSummaryHandler == null) {
			attendanceFinalSummaryHandler = new AttendanceFinalSummaryHandler();
		}
		return attendanceFinalSummaryHandler;
	}

	/**
	 * Get the list of AttendanceFinalSummaryReportTO List.
	 * @param attendenceFinalSummaryForm
	 * @return
	 * @throws ApplicationException
	 */
	public List<AttendanceFinalSummaryReportTO> getAttendanceFinalSummaryReport(
			AttendenceFinalSummaryForm attendenceFinalSummaryForm)
			throws ApplicationException {
		log.info("entering into getAttendanceFinalSummaryReport of AttendanceFinalSummaryHandler class.");
		IAttendanceFinalSummaryTransaction attendanceFinalSummaryTransaction = new AttendanceFinalSummaryTransactionImpl();
		List<AttendanceStudent> attendanceFinalSummaryList = attendanceFinalSummaryTransaction
				.getAttendanceFinalSummaryList(AttendanceFinalSummaryHelper
						.getInstance()
						.getAttendanceFinalSummaryQuery(
								attendenceFinalSummaryForm));
		List<AttendanceFinalSummaryReportTO> attendanceFinalSummaryReport = AttendanceFinalSummaryHelper
				.getInstance().getAttendanceFinalSummaryReportTOListFromMap(
						attendanceFinalSummaryList,attendenceFinalSummaryForm);
		log.info("exit of getAttendanceFinalSummaryReport of AttendanceFinalSummaryHandler class.");
		return attendanceFinalSummaryReport;

	}

	/**
	 * Get the list of monthly attendance final summary report.
	 * @param attendenceFinalSummaryForm
	 * @return
	 * @throws ApplicationException
	 */
	public List<AttendanceFinalSummaryReportTO> getMonthlyAttendanceFinalSummaryReport(
			AttendenceFinalSummaryForm attendenceFinalSummaryForm) throws ApplicationException {
		log.info("entering into getMonthlyAttendanceFinalSummaryReport of AttendanceFinalSummaryHandler class.");
		IAttendanceFinalSummaryTransaction attendanceFinalSummaryTransaction = new AttendanceFinalSummaryTransactionImpl();
		
		List<AttendanceStudent> attendanceFinalSummaryList = attendanceFinalSummaryTransaction
				.getAttendanceFinalSummaryList(AttendanceFinalSummaryHelper
						.getInstance()
						.getAttendanceFinalSummaryQueryForMonthly(
								attendenceFinalSummaryForm));
		List<AttendanceFinalSummaryReportTO> attendanceFinalSummaryReport = AttendanceFinalSummaryHelper
				.getInstance().getAttendanceFinalSummaryReportTOListForMonthly(
						attendanceFinalSummaryList,attendenceFinalSummaryForm);
		log.info("exit of getMonthlyAttendanceFinalSummaryReport of AttendanceFinalSummaryHandler class.");
		return attendanceFinalSummaryReport;
	}
}
