package com.kp.cms.handlers.employee;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.employee.BiometricDetails;
import com.kp.cms.bo.employee.EmpLeaveAllotment;
import com.kp.cms.forms.employee.BiometricDetailsForm;
import com.kp.cms.forms.employee.EmpLeaveAllotmentForm;
import com.kp.cms.helpers.employee.BiometricDetailsHelper;
import com.kp.cms.helpers.employee.EmpLeaveAllotmentHelper;
import com.kp.cms.to.employee.BiometricDetailsTO;
import com.kp.cms.to.employee.EmpLeaveAllotTO;
import com.kp.cms.transactions.employee.IBiometricDetailsTxn;
import com.kp.cms.transactions.employee.IEmpLeaveAllotmentTxn;
import com.kp.cms.transactionsimpl.employee.BiometricDetailsTxnImpl;
import com.kp.cms.transactionsimpl.employee.EmpLeaveAllotmentTxnImpl;

public class BiometricDetailsHandler {
	private static final Log log = LogFactory.getLog(BiometricDetailsHandler.class);
	public static volatile BiometricDetailsHandler biometric=null;
	/**
	 * @return
	 * This method gives instance of this method
	 */
	public static BiometricDetailsHandler getInstance(){
		if(biometric==null){
			biometric= new BiometricDetailsHandler();}
		return biometric;
	}
	public boolean duplicateCheck(BiometricDetailsForm biometricDetailsForm)throws Exception{
		IBiometricDetailsTxn transaction=new BiometricDetailsTxnImpl();
		boolean duplicate=transaction.duplicateCheck(biometricDetailsForm.getMachineId(),biometricDetailsForm.getMachineIp(),biometricDetailsForm.getMachineName(),biometricDetailsForm.getId());
		return duplicate;
	}
	public List<BiometricDetailsTO> getBiometricList(BiometricDetailsForm biometricDetailsForm)throws Exception{
		IBiometricDetailsTxn transaction=new BiometricDetailsTxnImpl();
		List<BiometricDetails> biometric=transaction.getBiometricDetails();
		List<BiometricDetailsTO> biometricTO=BiometricDetailsHelper.getInstance().convertBosToTOs(biometric,biometricDetailsForm);
		return biometricTO;
	}
	public boolean addBimetricDetails(BiometricDetailsForm biometricDetailsForm)throws Exception{
		BiometricDetails biometric=BiometricDetailsHelper.getInstance().convertFormTOBO(biometricDetailsForm);
		IBiometricDetailsTxn transaction=new BiometricDetailsTxnImpl();
		boolean isAdded=transaction.addBiometricDetails(biometric);
		return isAdded;
	}
	public void editBiometricDetails(BiometricDetailsForm biometricDetailsForm)throws Exception{
		IBiometricDetailsTxn transaction=new BiometricDetailsTxnImpl();
		BiometricDetails biometric=transaction.getBiometricDetailsById(biometricDetailsForm.getId());
		BiometricDetailsHelper.getInstance().setBotoForm(biometricDetailsForm, biometric);
	}
	public boolean updateBiometricDetails(BiometricDetailsForm biometricDetailsForm)throws Exception{
		BiometricDetails biometric=BiometricDetailsHelper.getInstance().convertFormToBo(biometricDetailsForm);
		IBiometricDetailsTxn transaction=new BiometricDetailsTxnImpl();
		boolean isUpdated=transaction.updateBiometricDetails(biometric);
		return isUpdated;
	}
	public boolean deleteBiometricDetails(BiometricDetailsForm biometricDetailsForm)throws Exception{
		
		IBiometricDetailsTxn transaction=new BiometricDetailsTxnImpl();
				boolean isDeleted=transaction.deleteBiometricDetails(biometricDetailsForm.getId());
				return isDeleted;
			}
}
