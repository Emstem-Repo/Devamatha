package com.kp.cms.transactions.reports;

import java.util.List;

import com.kp.cms.exceptions.ApplicationException;

public interface IAbsenceInformationSummaryTransaction {

	/**
	 * Get the list of absenceInformation summary list.
	 * @param absenceInformationQuery
	 * @return
	 * @throws ApplicationException
	 */
	public List<Object[]> getAbsenceSummaryInformation(
			String absenceInformationQuery) throws ApplicationException;

	/**
	 * Get the list of subject names.
	 * @param absenceInformationQuery
	 * @return
	 * @throws ApplicationException
	 */
	public List<String> getSubjectInformation(String absenceInformationQuery)
			throws ApplicationException;

	

}
