package com.kp.cms.transactions.attandance;

import java.util.List;

import com.kp.cms.exceptions.ApplicationException;

public interface IAttendanceSlipDetailsTransaction {

	public List getListOfSlipDetails(String searchCriteria)throws Exception;

	public List getPeriods(String listPeriods)throws ApplicationException;
	
	public List<String> getClassNames(String classid)throws Exception;

}
