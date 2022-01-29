package com.kp.cms.transactions.employee;

import java.util.List;

import com.kp.cms.bo.admin.EmpBiometricLogSetupBO;

public interface IBiometricLogSetupTransaction {

	List<EmpBiometricLogSetupBO> getBiometricLogDetails()throws Exception;

	boolean addBiometricLog(EmpBiometricLogSetupBO copyDataFromFormToBO, Integer i)throws Exception;

	EmpBiometricLogSetupBO getBiometricLogDetailsById(int id) throws Exception;

}
