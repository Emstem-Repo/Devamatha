package com.kp.cms.handlers.reports;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.reports.AdmittedStudentReportForm;
import com.kp.cms.helpers.reports.AdmissionReportHelper;
import com.kp.cms.to.reports.AdmittedStudentsReportsTO;
import com.kp.cms.transactions.reports.IAbsenceInformationSummaryTransaction;
import com.kp.cms.transactionsimpl.reports.AbsenceSummaryInformationTransactionImpl;

public class AdmissionReportHandler {

	private static volatile AdmissionReportHandler admissionReportHandler = null;

	private static final Log log = LogFactory
			.getLog(AdmissionReportHandler.class);

	private AdmissionReportHandler() {

	}

	public static AdmissionReportHandler getInstance() {
		if (admissionReportHandler == null) {
			admissionReportHandler = new AdmissionReportHandler();
		}
		return admissionReportHandler;
	}

	/**
	 * Get the list of AdmittedStudentsReportsTO.
	 * 
	 * @param admittedStudentReportForm
	 * @return
	 * @throws ApplicationException
	 */
	public List<AdmittedStudentsReportsTO> getAdmittedStudentReportToList(
			AdmittedStudentReportForm admittedStudentReportForm)
			throws ApplicationException {
		log
				.info("entering getAdmittedStudentReportToList of AdmissionReportHandler");
		IAbsenceInformationSummaryTransaction admittedThroughTransaction = new AbsenceSummaryInformationTransactionImpl();
		List<Object[]> admissionReportList = admittedThroughTransaction
				.getAbsenceSummaryInformation(AdmissionReportHelper
						.getInstance().getAdmittedStudentQuery(
								admittedStudentReportForm));
		log
				.info("exit of getAdmittedStudentReportToList of AdmissionReportHandler");
		return AdmissionReportHelper.getInstance().convertBotoTo(
				admissionReportList, admittedStudentReportForm);
	}

}
