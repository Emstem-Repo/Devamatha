package com.kp.cms.handlers.employee;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.EmpEducationMaster;
import com.kp.cms.bo.admin.EmpQualificationLevel;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.employee.EducationMasterForm;
import com.kp.cms.helpers.admin.SingleFieldMasterHelper;
import com.kp.cms.helpers.employee.EducationMasterHelper;
import com.kp.cms.to.admin.SingleFieldMasterTO;
import com.kp.cms.to.employee.EmpEducationMasterTO;
import com.kp.cms.transactions.admin.ISingleFieldMasterTransaction;
import com.kp.cms.transactions.employee.IEducationMasterTransaction;
import com.kp.cms.transactionsimpl.admin.SingleFieldMasterTransactionImpl;
import com.kp.cms.transactionsimpl.employee.EducationMasterTransactionImpl;

public class EducationMasterHandler {
	private static final Log log = LogFactory.getLog(EducationMasterHandler.class);
	public static volatile EducationMasterHandler educationMasterHandler = null;

	public static EducationMasterHandler getInstance() {
		if (educationMasterHandler == null) {
			educationMasterHandler = new EducationMasterHandler();
			return educationMasterHandler;
		}
		return educationMasterHandler;
	}

	/**
	 * @return qualifTOList
	 * @throws Exception
	 */
	public List<SingleFieldMasterTO> getQualification() throws Exception {
		log.debug("start getQualification");
		ISingleFieldMasterTransaction singleFieldMasterTransaction = SingleFieldMasterTransactionImpl.getInstance();
		List<SingleFieldMasterTO> qualifTOList = null;
		
		List<EmpQualificationLevel> empQalification = singleFieldMasterTransaction.getEmpQualidication();
		qualifTOList = SingleFieldMasterHelper.getInstance().copySingleFieldMasterHelper(empQalification, "EmpQualificationLevel");
		log.debug("end getQualification");
		return qualifTOList;
	}
	
	/**
	 * 
	 * @param educationMasterForm
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public boolean addEducationMaster(EducationMasterForm educationMasterForm, String mode) throws Exception {
		log.debug("inside addEducationMaster");
		IEducationMasterTransaction iTransaction = EducationMasterTransactionImpl.getInstance();
		boolean isAdded = false;
		EmpEducationMaster empEducationMaster;
		empEducationMaster = iTransaction.isEducationDuplcated(educationMasterForm);  

		if (empEducationMaster != null && empEducationMaster.getIsActive()) {
			throw new DuplicateException();
		}
		else if (empEducationMaster != null && !empEducationMaster.getIsActive())
		{
			educationMasterForm.setDuplId(empEducationMaster.getId());
			throw new ReActivateException();
		}		
				
		empEducationMaster =EducationMasterHelper.getInstance().copyDataFromFormToBO(educationMasterForm); 
		isAdded = iTransaction.addEducationMaster(empEducationMaster, mode); 
		log.debug("leaving addEducationMaster");
		return isAdded;
	}
	
	/**
	 * @return list of EmpEducationMasterTO objects, will be used in UI to dispaly.
	 * @throws Exception
	 */
	public List<EmpEducationMasterTO> getEducationMasterDetails() throws Exception {
		log.debug("inside getEducationMasterDetails");
		IEducationMasterTransaction ieTransaction = EducationMasterTransactionImpl.getInstance();
		List<EmpEducationMaster> educationList = ieTransaction.getEducationDetails();
		List<EmpEducationMasterTO> EmpEducationMasterTOList = EducationMasterHelper.getInstance().copyEducationBosToTos(educationList); 
		log.debug("leaving getEducationMasterDetails");
		return EmpEducationMasterTOList;
	}
	/**
	 * 
	 * @param
	 * 
	 * @return boolean true / false based on result.
	 */

	public boolean deleteEducation(int id, Boolean activate, EducationMasterForm educationMasterForm) throws Exception {
		IEducationMasterTransaction edTransaction = EducationMasterTransactionImpl.getInstance();
		return edTransaction.deleteEducation(id, activate, educationMasterForm); 
	}	
}
