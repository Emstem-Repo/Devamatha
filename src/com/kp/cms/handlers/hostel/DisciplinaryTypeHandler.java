package com.kp.cms.handlers.hostel;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.HlDisciplinaryType;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.hostel.DisciplinaryTypeForm;
import com.kp.cms.helpers.hostel.DisciplinaryTypeHelper;
import com.kp.cms.to.hostel.DisciplinaryTypeTO;
import com.kp.cms.transactions.hostel.IDisciplinaryTypeTransactions;
import com.kp.cms.transactionsimpl.hostel.DisciplinaryTypeTransactionImpl;

public class DisciplinaryTypeHandler {
	private static final Log log = LogFactory.getLog(DisciplinaryTypeHandler.class);
	public static volatile DisciplinaryTypeHandler disciplinaryTypeHandler = null;

	public static DisciplinaryTypeHandler getInstance() {
		if (disciplinaryTypeHandler == null) {
			disciplinaryTypeHandler = new DisciplinaryTypeHandler();
			return disciplinaryTypeHandler;
		}
		return disciplinaryTypeHandler;
	}
	/**
	 * 
	 * @return list of DisciplinaryTypeTO objects, will be used in UI to dispaly.
	 * @throws Exception
	 */
	public List<DisciplinaryTypeTO> getDisplinaryTypes() throws Exception {
		log.debug("inside getDisplinaryTypes");
		IDisciplinaryTypeTransactions iDisciplinaryTypeTransactions = DisciplinaryTypeTransactionImpl.getInstance();
		List<HlDisciplinaryType> diList = iDisciplinaryTypeTransactions.getDisciplinary();
		List<DisciplinaryTypeTO> disTypeTOTOList = DisciplinaryTypeHelper.getInstance().copyDisciplinaryTypeBosToTos(diList); 
		log.debug("leaving getGrades");
		return disTypeTOTOList;
	}
	  
	/**
	 * add disciplinary type
	 * @param disciplinaryTypeForm
	 * @return
	 * @throws Exception
	 */
	public boolean addDisciplinaryType(DisciplinaryTypeForm disciplinaryTypeForm, String mode) throws Exception {
		log.debug("inside addDisciplinaryType");
		IDisciplinaryTypeTransactions idTransactions = DisciplinaryTypeTransactionImpl.getInstance();
		boolean isAdded = false;
		
		HlDisciplinaryType duplDisciplinaryType =  idTransactions.isDisciplinaryTypeDuplcated(disciplinaryTypeForm.getName(), disciplinaryTypeForm.getId());
			
		
		if (duplDisciplinaryType != null && duplDisciplinaryType.getIsActive()) {
			throw new DuplicateException();
		}
		else if (duplDisciplinaryType != null && !duplDisciplinaryType.getIsActive())
		{
			disciplinaryTypeForm.setDuplId(duplDisciplinaryType.getId());
			throw new ReActivateException();
		}		
				
		HlDisciplinaryType diType = DisciplinaryTypeHelper.getInstance().copyDataFromFormToBO(disciplinaryTypeForm);
		isAdded = idTransactions.addDisciplinaryType(diType, mode); 
		log.debug("leaving addDisciplinaryType");
		return isAdded;
	}
	

	/**
	 * delete method
	 * @param
	 * 
	 * @return boolean true / false based on result.
	 */

	public boolean deleteDisciplinaryType(int id, Boolean activate, DisciplinaryTypeForm disciplinaryTypeForm)	throws Exception {
		IDisciplinaryTypeTransactions idcTypeTransactions = DisciplinaryTypeTransactionImpl.getInstance();
		return idcTypeTransactions.deleteDisciplinaryType(id, activate, disciplinaryTypeForm);
	}

}
