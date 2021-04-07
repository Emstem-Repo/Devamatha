package com.kp.cms.transactions.employee;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.employee.BiometricDetails;
import com.kp.cms.bo.employee.EventLoactionBiometricDetailsBo;
import com.kp.cms.forms.employee.EventLocationBiometricDetailsForm;
import com.kp.cms.to.employee.EventLoactionBiometricDetailsTo;

public interface IEventLoactionBiometricDetailsTransaction {
	public boolean duplicateCheck(String machineId,String machineIp,String machineName,EventLocationBiometricDetailsForm form)throws Exception;
	public boolean addBiometricDetails(EventLoactionBiometricDetailsBo biometric)throws Exception;
	public List<EventLoactionBiometricDetailsBo> getBiometricDetails()throws Exception; 
	public EventLoactionBiometricDetailsBo getBiometricDetailsById(int id)throws Exception;
	public boolean updateBiometricDetails(EventLoactionBiometricDetailsBo biometric)throws Exception;
	public boolean deleteBiometricDetails(int id)throws Exception;
	public Map<String,String> getEventLocationData()throws Exception;
	public boolean duplicateCheckInUpdateMode(String machineId,String machineIp,String machineName,EventLocationBiometricDetailsForm form)throws Exception;
}
