package com.kp.cms.handlers.admin;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.College;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.admin.InstituteForm;
import com.kp.cms.helpers.admin.InstituteHelper;
import com.kp.cms.to.admin.CollegeTO;
import com.kp.cms.transactions.admin.IInstituteTransaction;
import com.kp.cms.transactionsimpl.admin.InstituteTransactionImpl;


public class InstituteHandler {
	private static final Log log = LogFactory.getLog(InstituteHandler.class);
	public static volatile InstituteHandler instituteHandler = null;

	public static InstituteHandler getInstance() {
		if (instituteHandler == null) {
			instituteHandler = new InstituteHandler();
			return instituteHandler;
		}
		return instituteHandler;
	}
	/***
	 * getting all the institute records for UI display
	 * @return
	 * @throws Exception
	 */
	
	public List<CollegeTO> getInstitute() throws Exception {
		IInstituteTransaction iInstituteTransaction = InstituteTransactionImpl.getInstance();
		List<College> collegeList = iInstituteTransaction.getCollegeNames();
		List<CollegeTO> collegeToList = InstituteHelper.getInstance().copyCollegeBosToTos(collegeList);
		log.debug("leaving getInstitute");
		return collegeToList;
	}
	
	/****
	 * add method
	 * @param instituteForm
	 * @return
	 * @throws Exception
	 */
	
	public boolean addInstitute(InstituteForm instituteForm) throws Exception {
		IInstituteTransaction iInstituteTransaction = InstituteTransactionImpl.getInstance();
		boolean isAdded = false;

		College duplCollege = InstituteHelper.getInstance().populateStatesDataFormForm(instituteForm); 

		duplCollege = iInstituteTransaction.isInstituteNameDuplcated(duplCollege); 
		if (duplCollege  != null && duplCollege .getIsActive()) {
			throw new DuplicateException();
		} else if (duplCollege  != null && !duplCollege.getIsActive()) {
			instituteForm.setDuplInstId(duplCollege.getId());
			throw new ReActivateException();
		}
		College college = InstituteHelper.getInstance().populateStatesDataFormForm(instituteForm);
		
		college.setCreatedBy(instituteForm.getUserId());
		college.setCreatedDate(new Date());
		college.setLastModifiedDate(new Date());
		college.setModifiedBy(instituteForm.getUserId());
		isAdded = iInstituteTransaction.addInstitute(college);
		log.debug("leaving addInstitute");
		return isAdded;
	}
	
	/**
	 * update method
	 * @param instituteForm
	 * @return
	 * @throws Exception
	 */
	
	public boolean updateInstitute(InstituteForm instituteForm) throws Exception {
		IInstituteTransaction iInstituteTransaction = InstituteTransactionImpl.getInstance();
		boolean isUpdate = false;
		//originalchanged variable is used to check any data is changed or not in the edit mode
		
		Boolean originalNotChanged = false;

		String instName = "";
		String origInstName = "";

		instName = instituteForm.getName().trim();
		origInstName = instituteForm.getOrigInstName().trim();

		int uniId = 0;
		if ((instituteForm.getUniversityId() != null) || (!instituteForm.getUniversityId().isEmpty())){
			uniId = Integer.parseInt(instituteForm.getUniversityId());
		}
		int origUniId = instituteForm.getEditUniId();		
		
		
		if (instName.trim().equalsIgnoreCase(origInstName.trim()) && uniId == origUniId) {
			originalNotChanged = true;
		}

		if (!originalNotChanged) {
			College duplCollege = InstituteHelper.getInstance().populateStatesDataFormForm(instituteForm); 

			duplCollege = iInstituteTransaction.isInstituteNameDuplcated(duplCollege);
			if (duplCollege != null && duplCollege.getIsActive()) {
				throw new DuplicateException();
			} else if (duplCollege != null && !duplCollege.getIsActive()) {
				instituteForm.setDuplInstId(duplCollege.getId());
				throw new ReActivateException();
			}
		}
		College college = InstituteHelper.getInstance().populateStatesDataFormForm(instituteForm);  
		
		college.setModifiedBy(instituteForm.getUserId());
		college.setLastModifiedDate(new Date());
		isUpdate = iInstituteTransaction.updateInstitute(college);
		log.debug("leaving updateInstitute");
		return isUpdate;
	}

	/****
	 * delete method for institute entry
	 * @param id
	 * @param activate
	 * @return
	 * @throws Exception
	 */
	public boolean deleteInstitute(int id, Boolean activate, String userId) throws Exception {
		IInstituteTransaction iInstituteTransaction = InstituteTransactionImpl.getInstance();
		boolean result = iInstituteTransaction.deleteInstitute(id, activate, userId);
		log.debug("leaving deleteInstitute");
		return result;
	}
	
	
}
