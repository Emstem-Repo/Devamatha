package com.kp.cms.helpers.employee;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.EmpLeaveType;
import com.kp.cms.bo.employee.BiometricDetails;
import com.kp.cms.bo.employee.EmpLeaveAllotment;
import com.kp.cms.bo.employee.EmpType;
import com.kp.cms.forms.employee.BiometricDetailsForm;
import com.kp.cms.forms.employee.EmpLeaveAllotmentForm;
import com.kp.cms.to.employee.BiometricDetailsTO;
import com.kp.cms.to.employee.EmpLeaveAllotTO;

public class BiometricDetailsHelper {
	private static final Log log = LogFactory.getLog(BiometricDetailsHelper.class);
	public static volatile BiometricDetailsHelper biometric=null;
	/**
	 * @return
	 * This method gives instance of this method
	 */
	public static BiometricDetailsHelper getInstance(){
		if(biometric==null){
			biometric= new BiometricDetailsHelper();}
		return biometric;
	}
	/**
	 * @param biometric
	 * @return
	 */
	public List<BiometricDetailsTO> convertBosToTOs(List<BiometricDetails> biometric,BiometricDetailsForm biometricDetailsForm){
    	List<BiometricDetailsTO> biometricTOs=new ArrayList<BiometricDetailsTO>();
		Iterator itr=biometric.iterator();
		while(itr.hasNext()){
			BiometricDetails biometricBo=(BiometricDetails)itr.next();
			BiometricDetailsTO biometricTo=new BiometricDetailsTO();
			biometricTo.setId(biometricBo.getId());
			biometricTo.setMachineId(biometricBo.getMachineId());
			biometricDetailsForm.setOrigMachineId(biometricBo.getMachineId());
			biometricTo.setMachineIp(biometricBo.getMachineIp());
			biometricDetailsForm.setOrigMachineIp(biometricBo.getMachineIp());
			if(biometricBo.getMachineName()!=null){
				biometricTo.setMachinName(biometricBo.getMachineName());
				biometricDetailsForm.setOrigMachineName(biometricBo.getMachineName());
			}
			biometricTo.setWorkLocation(biometricBo.getWorkLocation());
			biometricDetailsForm.setOrigWorkLocation(biometricBo.getWorkLocation());
			biometricTOs.add(biometricTo);
		}
		return biometricTOs;
	}
	/**
	 * @param biometricDetailsForm
	 * @return
	 */
	public BiometricDetails convertFormTOBO(BiometricDetailsForm biometricDetailsForm){
		BiometricDetails biometric=new BiometricDetails();
		biometric.setMachineId(biometricDetailsForm.getMachineId());
		biometric.setMachineIp(biometricDetailsForm.getMachineIp());
		if(biometricDetailsForm.getMachineName()!=null && !biometricDetailsForm.getMachineName().isEmpty())
		biometric.setMachineName(biometricDetailsForm.getMachineName());
		biometric.setWorkLocation(biometricDetailsForm.getWorkLocation());
		biometric.setIsActive(true);
		return biometric;
	}
	/**
	 * @param biometricDetailsForm
	 * @param biometric
	 */
	public void setBotoForm(BiometricDetailsForm biometricDetailsForm,BiometricDetails biometric){
    	if(biometric!=null){
    		biometricDetailsForm.setMachineId(biometric.getMachineId());
    		biometricDetailsForm.setOrigMachineId(biometric.getMachineId());
    		biometricDetailsForm.setMachineIp(biometric.getMachineIp());
    		biometricDetailsForm.setOrigMachineIp(biometric.getMachineIp());
    		if(biometric.getMachineName()!=null && !biometric.getMachineName().isEmpty()){
    		biometricDetailsForm.setMachineName(biometric.getMachineName());
    		biometricDetailsForm.setOrigMachineName(biometric.getMachineName());
    		}else{
    			biometricDetailsForm.setMachineName(biometric.getMachineName());
    			biometricDetailsForm.setOrigMachineName(biometric.getMachineName());
    		}
    		biometricDetailsForm.setWorkLocation(biometric.getWorkLocation());
    		biometricDetailsForm.setOrigWorkLocation(biometric.getWorkLocation());
    	}
    }
	/**
	 * @param biometricDetailsForm
	 * @return
	 */
	public BiometricDetails convertFormToBo(BiometricDetailsForm biometricDetailsForm){
		BiometricDetails biometric=new BiometricDetails();
		biometric.setId(biometricDetailsForm.getId());
		biometric.setMachineId(biometricDetailsForm.getMachineId());
		biometric.setMachineIp(biometricDetailsForm.getMachineIp());
		if(biometricDetailsForm.getMachineName()!=null && !biometricDetailsForm.getMachineName().isEmpty())
		biometric.setMachineName(biometricDetailsForm.getMachineName());
		biometric.setIsActive(true);
		biometric.setModifiedBy(biometricDetailsForm.getUserId());
		biometric.setLastModifiedDate(new Date());
		biometric.setWorkLocation(biometricDetailsForm.getWorkLocation());
    	return biometric;
    }
}
