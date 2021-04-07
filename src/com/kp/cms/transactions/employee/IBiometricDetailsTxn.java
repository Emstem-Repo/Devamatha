package com.kp.cms.transactions.employee;

import java.util.List;

import com.kp.cms.bo.employee.BiometricDetails;
import com.kp.cms.bo.employee.EmpLeaveAllotment;

public interface IBiometricDetailsTxn {
	public boolean duplicateCheck(String machineId,String machineIp,String machineName,int id)throws Exception;
	public List<BiometricDetails> getBiometricDetails()throws Exception; 
	public boolean addBiometricDetails(BiometricDetails biometric)throws Exception;
	public BiometricDetails getBiometricDetailsById(int id)throws Exception;
	public boolean updateBiometricDetails(BiometricDetails biometric)throws Exception;
	public boolean deleteBiometricDetails(int id)throws Exception;
}
