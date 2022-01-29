package com.kp.cms.transactions.reports;

import java.util.List;

import com.kp.cms.forms.reports.FeesConcessionReportForm;

public interface IFeesConcessionReportTxn {
	public List<Object[]> getFeeConcessionDetails(FeesConcessionReportForm concessionReportForm) throws Exception;
}
