package com.kp.cms.transactions.hostel;

import java.util.List;

import com.kp.cms.bo.employee.BiometricDetails;
import com.kp.cms.bo.hostel.BiometricBo;
import com.kp.cms.forms.hostel.BiometricForm;

public interface IBiometricTransaction {
	public boolean addBiometricDetails(BiometricBo biometricBo)throws Exception;
	public boolean checkDuplicate(BiometricForm biometricForm)throws Exception;
	public List<BiometricBo> getBiometricDetails()throws Exception; 
	public BiometricBo getBiometricDetailsById(int id)throws Exception;
	public boolean updateBiometricDetails(BiometricBo biometric)throws Exception;
	public boolean deleteBiometricDetails(int id)throws Exception;
}
