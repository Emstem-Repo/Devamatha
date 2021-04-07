package com.kp.cms.transactions.reports;

import java.util.List;

import com.kp.cms.bo.admin.InvTx;

public interface IIssuedReceivedReportTxn {
	public List<InvTx> getItemTransactionsOnDate(String dynamicQuery)
	throws Exception;
}
