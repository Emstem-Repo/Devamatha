package com.kp.cms.transactions.pettycash;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.PcBankAccNumber;

public interface IConsolidatedCollectionReport {
	List getListOfData(String searchCriteria) throws Exception;
	//getting the list of accNo's from database
	List<PcBankAccNumber> getListOfAccNo() throws Exception;
	Map<Integer, String> getGroupNameMap() throws Exception;
}
