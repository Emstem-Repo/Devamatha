package com.kp.cms.transactions.attandance;

import java.util.List;

import com.kp.cms.bo.admin.Period;
import com.kp.cms.forms.attendance.PeriodForm;

public interface IPeriodTransaction {
	public boolean addPeriod(List<Period> periodList, String mode) throws Exception;
	public List<Period> getPeriod(int id) throws Exception;
	public List<Period> getAllPeriod(String year) throws Exception;
	public Boolean isClassAndPeriodNameDuplicated(int classId, String periodName, int periodId)  throws Exception; 	
	public Boolean deletePeriod(int perId,Boolean activate,PeriodForm periodForm) throws Exception;
	public Boolean isClassAndPeriodDuplicated(int classId, String startTime, String endTime, int periodId) throws Exception; 	
}
