package com.kp.cms.transactions.reports;

import java.util.List;

import com.kp.cms.bo.admin.FeeVoucher;

public interface IConcessionSlipBookReportTxn {
	public List<FeeVoucher> getSlipBookDetails(String type) throws Exception;
}
