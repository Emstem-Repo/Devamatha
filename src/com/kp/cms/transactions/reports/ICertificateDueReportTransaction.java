package com.kp.cms.transactions.reports;

import java.util.List;

import com.kp.cms.bo.admin.ApplnDoc;

public interface ICertificateDueReportTransaction {

	List<ApplnDoc> getCerificateDueStudents(String searchedQuery)throws Exception;

}
