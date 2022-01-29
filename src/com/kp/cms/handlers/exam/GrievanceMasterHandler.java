package com.kp.cms.handlers.exam;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.TCNumber;
import com.kp.cms.bo.exam.GrievanceNumber;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.admin.TCMasterForm;
import com.kp.cms.forms.exam.GrievanceMasterForm;
import com.kp.cms.helpers.admin.TCMasterHelper;
import com.kp.cms.helpers.exam.GrievanceMasterHelper;
import com.kp.cms.to.exam.GrievanceMasterTo;
import com.kp.cms.transactions.exam.IGrievanceMasterTransaction;

import com.kp.cms.transactionsimpl.exam.GrievanceMasterTransactionImpl;

public class GrievanceMasterHandler {
private static volatile GrievanceMasterHandler grievanceMasterHandler = null;
	
	IGrievanceMasterTransaction transaction=GrievanceMasterTransactionImpl.getInstance();
	
	private static final Log log = LogFactory.getLog(GrievanceMasterHandler.class);
	private GrievanceMasterHandler() {
		
	}
	
	public static GrievanceMasterHandler getInstance() {
		if (grievanceMasterHandler == null) {
			grievanceMasterHandler = new GrievanceMasterHandler();
		}
		return grievanceMasterHandler;
	}
	public List<GrievanceMasterTo> getAllGrievanceNumber() throws Exception {
		List<GrievanceNumber> list=transaction.getAllGrievanceNumber();
		return GrievanceMasterHelper.getInstance().convertBOtoTOList(list);
	}
	
	public boolean addGrievanceMaster(GrievanceMasterForm grievanceMasterForm , String mode) throws Exception {
		log.debug("inside addTCMaster");
		//boolean isAdded = false;
		
		GrievanceNumber duplGrievanceNumber = transaction.isGrievanceNumberDuplcated(grievanceMasterForm);  
		if(grievanceMasterForm.getId()!=0 && duplGrievanceNumber!=null && grievanceMasterForm.getId()!=duplGrievanceNumber.getId()){
		if (duplGrievanceNumber != null && duplGrievanceNumber.getIsActive()) {
			throw new DuplicateException();
		}
		else if (duplGrievanceNumber != null && !duplGrievanceNumber.getIsActive())
		{
			grievanceMasterForm.setDuplId(duplGrievanceNumber.getId());
			throw new ReActivateException();
		}		
		}else{
			if(mode.equalsIgnoreCase("add")){
				if (duplGrievanceNumber != null && duplGrievanceNumber.getIsActive()) {
					throw new DuplicateException();
				}
				else if (duplGrievanceNumber != null && !duplGrievanceNumber.getIsActive())
				{
					grievanceMasterForm.setDuplId(duplGrievanceNumber.getId());
					throw new ReActivateException();
				}	
			}
		}
		GrievanceNumber bo=GrievanceMasterHelper.getInstance().convertFormToBo(grievanceMasterForm,mode);
		log.debug("leaving addTCMaster");
		return transaction.addGrievanceMaster(bo,mode);
	}
	
	public boolean deleteGrievanceMaster(int id, Boolean activate, GrievanceMasterForm grievanceMasterForm) throws Exception {
		return transaction.deleteGrievanceMaster(id, activate, grievanceMasterForm);
	}

}
