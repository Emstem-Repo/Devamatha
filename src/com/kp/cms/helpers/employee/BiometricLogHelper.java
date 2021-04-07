package com.kp.cms.helpers.employee;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.EmpBiometricLogSetupBO;
import com.kp.cms.forms.employee.BiometricLogSetupForm;
import com.kp.cms.to.employee.EmpBiometricSetUpTo;

public class BiometricLogHelper {
	private static final Log log = LogFactory.getLog(BiometricLogHelper.class);
	public static volatile BiometricLogHelper objHelper = null;

	public static BiometricLogHelper getInstance() {
		if (objHelper == null) {
			objHelper = new BiometricLogHelper();
			return objHelper;
		}
		return objHelper;
	}


	public EmpBiometricLogSetupBO copyDataFromFormToBO(BiometricLogSetupForm to)
			throws Exception {
		log.debug("inside copyDataFromFormToBO");
		EmpBiometricLogSetupBO bo = new EmpBiometricLogSetupBO();
		if(to.getId()!=0){
			bo.setId(to.getId());
		}
		bo.setTerminalId(to.getTerminalId());
		bo.setFingerPrintId(to.getFingerPrintId());
		bo.setEmployeeCode(to.getEmployeeCode());
		bo.setEmployeeName(to.getEmployeeName());
		bo.setDatetime(to.getDatetime());
		bo.setFunctionkey(to.getFunctionkey());
		bo.setStatus(to.getStatus());
		bo.setDelimitedWith(to.getDelimitedWith());
		bo.setDateFormat(to.getDateFormat());
		bo.setTextFilePath(to.getTextFilePath());
		bo.setTestCode(to.getTestCode());
		bo.setIsActive(true);
		bo.setCreatedBy(to.getUserId());
		bo.setModifiedBy(to.getUserId());
		bo.setCreatedDate(new Date());
		bo.setLastModifiedDate(new Date());
		log.debug("leaving copyDataFromFormToBO");
		return bo;
	}


	/**
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public List<EmpBiometricSetUpTo> convertBOListToToList(
			List<EmpBiometricLogSetupBO> list) throws Exception{
		List<EmpBiometricSetUpTo> finalList=new ArrayList<EmpBiometricSetUpTo>();
		if(list!=null && !list.isEmpty()){
			Iterator<EmpBiometricLogSetupBO> itr=list.iterator();
			while (itr.hasNext()) {
				EmpBiometricLogSetupBO BO = (EmpBiometricLogSetupBO) itr.next();
				EmpBiometricSetUpTo to=new EmpBiometricSetUpTo();
				to.setId(BO.getId());
				to.setTerminalId(BO.getTerminalId());
				to.setFingerPrintId(BO.getFingerPrintId());
				to.setEmployeeCode(BO.getEmployeeCode());
				to.setEmployeeName(BO.getEmployeeName());
				to.setDatetime(BO.getDatetime());
				to.setFunctionkey(BO.getFunctionkey());
				to.setStatus(BO.getStatus());
				to.setDelimitedWith(BO.getDelimitedWith());
				to.setDateFormat(BO.getDateFormat());
				to.setTextFilePath(BO.getTextFilePath());
				to.setTestCode(BO.getTestCode());
				finalList.add(to);
			}
		}
		return finalList;
	}
}
