package com.kp.cms.transactions.pettycash;

import java.util.List;

import com.kp.cms.bo.admin.PcReceipts;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.to.pettycash.StudentCollectionReportTO;

public interface IStudentCollectionReportTransaction {
	
	public List<PcReceipts> getStudentCollectionReport(String criteria)throws ApplicationException;
	
	public List<PcReceipts> getStudentDetails()throws ApplicationException;

}
