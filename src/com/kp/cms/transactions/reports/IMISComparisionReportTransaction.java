package com.kp.cms.transactions.reports;

import java.util.Date;
import java.util.List;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.BankMis;

public interface IMISComparisionReportTransaction {
	
	public List<AdmAppln> getComparisionDetailsFromApplication(Date transactionDate) throws Exception;
	
	public List<BankMis> getComparisionDetailsFromBank(Date transactionDate) throws Exception;
}
