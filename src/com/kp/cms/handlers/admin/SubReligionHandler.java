package com.kp.cms.handlers.admin;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.ReligionSection;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.admin.SubReligionForm;
import com.kp.cms.helpers.admin.SubReligionHelper;
import com.kp.cms.to.admin.ReligionSectionTO;
import com.kp.cms.transactions.admin.ISubReligionTransaction;
import com.kp.cms.transactionsimpl.admin.SubReligionTransactionImpl;

/**
 * 
 * @author
 *
 */
public class SubReligionHandler {
	public static volatile SubReligionHandler subReligionHandler = null;
	private static final Log log = LogFactory.getLog(SubReligionHandler.class);

	public static SubReligionHandler getInstance() {
		if (subReligionHandler == null) {
			subReligionHandler = new SubReligionHandler();
			return subReligionHandler;
		}
		return subReligionHandler;
	}

	/**
	 * 
	 * @param subReligionForm
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public boolean addSubReligion(SubReligionForm subReligionForm, String mode) throws Exception{
		ISubReligionTransaction iSubReligionTransaction = SubReligionTransactionImpl.getInstance();
		boolean isAdded = false;
		ReligionSection duplReligionSection = SubReligionHelper.getInstance().populateSubReligionDataFormForm(subReligionForm);
		
		duplReligionSection = iSubReligionTransaction.isSubReligionDuplicated(duplReligionSection);
		
		//duplReligionSection is using to check the duplication. if there is duplication then it will return object
		
		if (duplReligionSection != null && duplReligionSection.getIsActive()) {
			throw new DuplicateException();
		} else if (duplReligionSection != null && !duplReligionSection.getIsActive()) {
			subReligionForm.setDuplId(duplReligionSection.getId());
			throw new ReActivateException();
		}
		
		ReligionSection religionSection = SubReligionHelper.getInstance().populateSubReligionDataFormForm(subReligionForm);  
									//this method will set all the form data to religionSection for saving
		if ("Add".equalsIgnoreCase(mode)) {
			religionSection.setCreatedDate(new Date());
			religionSection.setLastModifiedDate(new Date());
			religionSection.setCreatedBy(subReligionForm.getUserId());
			religionSection.setModifiedBy(subReligionForm.getUserId());
		} else // edit
		{
			religionSection.setModifiedBy(subReligionForm.getUserId());
			religionSection.setLastModifiedDate(new Date());
		}
		isAdded = iSubReligionTransaction.addSubReligion(religionSection, mode);
		log.debug("inside addSubReligion");
		return isAdded;
	}

	/**
	 * 
	 * @return list of subReligionTO objects, will be used in UI to dispaly.
	 */
	public List<ReligionSectionTO> getSubReligion() throws Exception{
		ISubReligionTransaction iSubReligionTransaction = SubReligionTransactionImpl.getInstance();
		List<ReligionSection> subReligionList = iSubReligionTransaction.getSubReligion();   // subReligionList will have list of ReligionSection object 
		List<ReligionSectionTO> subReligionToList = SubReligionHelper.getInstance().copySubReligionBosToTos(subReligionList);
		log.debug("leaving getSubReligion in Handler");
		return subReligionToList;
	}

	/**
	 * 
	 * @param 
	 *        
	 * @return boolean true / false based on result.
	 * @throws Exception 
	 */

	public boolean deleteSubReligion(int relId, Boolean activate, SubReligionForm subReligionForm) throws Exception {
		ISubReligionTransaction iSubReligionTransaction = SubReligionTransactionImpl.getInstance();
		boolean result = iSubReligionTransaction.deleteSubReligion(relId, activate, subReligionForm);
						//same method is calling for activate & de-activate. so activate param is used
		log.debug("leaving deleteSubReligion in Handler");
		return result;
	}

}
