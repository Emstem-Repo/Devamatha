package com.kp.cms.handlers.employee;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.EmpAttribute;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.employee.AttributeMasterForm;
import com.kp.cms.helpers.employee.AttributeMasterHelper;
import com.kp.cms.to.employee.EmpAttributeTO;
import com.kp.cms.transactions.employee.IAttributeMasterTransaction;
import com.kp.cms.transactionsimpl.employee.AttributeMasterTxImpl;


public class AttributeMasterHandler {
	private static final Log log = LogFactory.getLog(AttributeMasterHandler.class);
	public static volatile AttributeMasterHandler attributeMasterHelper = null;
	
	public static AttributeMasterHandler getInstance() {
		if (attributeMasterHelper == null) {
			attributeMasterHelper = new AttributeMasterHandler();
			return attributeMasterHelper;
		}
		return attributeMasterHelper;
	}
	
	/**
	 * @return list of EmpAttributeTO objects, will be used in UI to dispaly.
	 * @throws Exception
	 */
	public List<EmpAttributeTO> getAttributeMasterDetails() throws Exception {
		log.debug("inside getAttributeMasterDetails");
		IAttributeMasterTransaction iaTransaction = AttributeMasterTxImpl.getInstance();
		List<EmpAttribute> attributeList = iaTransaction.getAttributeDetails();
		List<EmpAttributeTO> EmpAttributeTOList = AttributeMasterHelper.getInstance().copyAttributeBosToTos(attributeList); 
		log.debug("leaving getAttributeMasterDetails");
		return EmpAttributeTOList;
	}
	
	/**
	 * 
	 * @param attributeMasterForm
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public boolean addAttributeMaster(AttributeMasterForm attributeMasterForm, String mode) throws Exception {
		log.debug("inside addAttributeMaster");
		IAttributeMasterTransaction iTransaction = AttributeMasterTxImpl.getInstance();
		boolean isAdded = false;
		
		EmpAttribute attribute = iTransaction.isAttributeDuplcated(attributeMasterForm);  

		if (attribute != null && attribute.getIsActive()) {
			throw new DuplicateException();
		}
		else if (attribute != null && !attribute.getIsActive())
		{
			attributeMasterForm.setDuplId(attribute.getId());
			throw new ReActivateException();
		}		
				
		attribute =AttributeMasterHelper.getInstance().copyDataFromFormToBO(attributeMasterForm); 
		isAdded = iTransaction.addAttributeMaster(attribute, mode); 
		log.debug("leaving addAttributeMaster");
		return isAdded;
	}
	/**
	 * 
	 * @param
	 * 
	 * @return boolean true / false based on result.
	 */

	public boolean deleteAttribute(int id, Boolean activate, AttributeMasterForm attributeMasterForm) throws Exception {
		IAttributeMasterTransaction attributeMasterTransaction = AttributeMasterTxImpl.getInstance();
		return attributeMasterTransaction.deleteAttribute(id, activate, attributeMasterForm); 
	}	
	
}
