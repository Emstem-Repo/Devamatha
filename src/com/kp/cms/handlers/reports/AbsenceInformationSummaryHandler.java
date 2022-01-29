package com.kp.cms.handlers.reports;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.reports.AbsenceInformationSummaryForm;
import com.kp.cms.helpers.reports.AbsenceInformationSummarHelper;
import com.kp.cms.to.reports.AbsenceInformationSummaryTO;
import com.kp.cms.transactions.reports.IAbsenceInformationSummaryTransaction;
import com.kp.cms.transactionsimpl.reports.AbsenceSummaryInformationTransactionImpl;

public class AbsenceInformationSummaryHandler {

	
	/**
	 * Singleton object of AbsenceInformationSummaryHandler
	 */
	private static volatile AbsenceInformationSummaryHandler absenceInformationSummaryHandler = null;
	private static final Log log = LogFactory.getLog(AbsenceInformationSummaryHandler.class);
	private AbsenceInformationSummaryHandler() {

	}

	/**
	 * return singleton object of AbsenceInformationSummaryHandler.
	 * @return
	 */
	public static AbsenceInformationSummaryHandler getInstance() {
		if (absenceInformationSummaryHandler == null) {
			absenceInformationSummaryHandler = new AbsenceInformationSummaryHandler();
		}
		return absenceInformationSummaryHandler;
	}

	/**
	 * Get the absence information summary list.
	 * @param absenceInformationSummaryForm
	 * @return
	 * @throws ApplicationException
	 */
	public List<AbsenceInformationSummaryTO> getAbsenceInformationSummaryTOList(
			AbsenceInformationSummaryForm absenceInformationSummaryForm)
			throws ApplicationException {
		log.info("entering into getAbsenceInformationSummaryTOList of AbsenceInformationSummaryHandler class.");
		IAbsenceInformationSummaryTransaction absenceInformationSummaryTransaction = new AbsenceSummaryInformationTransactionImpl();

		List<String> subjectsList = absenceInformationSummaryTransaction
				.getSubjectInformation(AbsenceInformationSummarHelper
						.getInstance().getSubjectList(
								absenceInformationSummaryForm));

		absenceInformationSummaryForm.setSubjectList(subjectsList);

		List absenceInformationList = absenceInformationSummaryTransaction
				.getAbsenceSummaryInformation(AbsenceInformationSummarHelper
						.getInstance().getAbsenceInformationSummaryQuery(
								absenceInformationSummaryForm));
		List<AbsenceInformationSummaryTO> absenceInformationSummaryTOList = AbsenceInformationSummarHelper
				.getInstance().convertBotoTO(absenceInformationList,
						absenceInformationSummaryForm);
		log.info("exit of getAbsenceInformationSummaryTOList of AbsenceInformationSummaryHandler class.");
		return absenceInformationSummaryTOList;

	}

	/**
	 * Get Monthly Absence information summary list.
	 * @param absenceInformationSummaryForm
	 * @return
	 * @throws ApplicationException
	 */
	public List<AbsenceInformationSummaryTO> getMonthlyAbsenceInformationSummaryTOList(
			AbsenceInformationSummaryForm absenceInformationSummaryForm) throws ApplicationException {
		log.info("entering into getMonthlyAbsenceInformationSummaryTOList of AbsenceInformationSummaryHandler class.");
		IAbsenceInformationSummaryTransaction absenceInformationSummaryTransaction = new AbsenceSummaryInformationTransactionImpl();
		List<String> subjectsList = absenceInformationSummaryTransaction
				.getSubjectInformation(AbsenceInformationSummarHelper
						.getInstance().getSubjectListMonthly(
								absenceInformationSummaryForm));

		absenceInformationSummaryForm.setSubjectList(subjectsList);
		List monthlyAbsenceInformationList = absenceInformationSummaryTransaction
		.getAbsenceSummaryInformation(AbsenceInformationSummarHelper
				.getInstance().getMonthlyAbsenceInformationSummaryQuery(
						absenceInformationSummaryForm));
		List<AbsenceInformationSummaryTO> monthlyAbsenceInformationSummaryTOList = AbsenceInformationSummarHelper
		.getInstance().convertBotoTOMonthly(monthlyAbsenceInformationList,
				absenceInformationSummaryForm);
		log.info("exit of getMonthlyAbsenceInformationSummaryTOList of AbsenceInformationSummaryHandler class.");
		return monthlyAbsenceInformationSummaryTOList;
	}

}
