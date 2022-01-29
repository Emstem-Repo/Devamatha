package com.kp.cms.transactions.employee;

import com.kp.cms.bo.admin.EmpWorkDairy;

public interface IWorkDiaryTransaction {
	public boolean addWorkDiary(EmpWorkDairy workDairy,String mode) throws Exception;
}
