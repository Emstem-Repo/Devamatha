package com.kp.cms.handlers.admin;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.GuidelinesChecklist;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.admin.GuideLinesCheckListForm;
import com.kp.cms.helpers.admin.GuideLinesCheckListHelper;
import com.kp.cms.to.admin.GuideLinesCheckListTO;
import com.kp.cms.transactions.admin.IGuideLinesCheckListTransactions;
import com.kp.cms.transactionsimpl.admin.GuideLinesCheckListTransactionImpl;

public class GuideLinesCheckListHandler {
	public static volatile GuideLinesCheckListHandler guideLinesCheckListHandler = null;
	private static final Log log = LogFactory.getLog(GuideLinesCheckListHandler.class);

	public static GuideLinesCheckListHandler getInstance() {
		if (guideLinesCheckListHandler == null) {
			guideLinesCheckListHandler = new GuideLinesCheckListHandler();
			return guideLinesCheckListHandler;
		}
		return guideLinesCheckListHandler;
	}

	/**
	**/
	public boolean addGuideLinesCheckList(GuideLinesCheckListForm guideCheckListForm, String mode) throws Exception{
		IGuideLinesCheckListTransactions igEntryTransaction = GuideLinesCheckListTransactionImpl.getInstance();
		boolean isAdded = false;
		GuidelinesChecklist duplGuidelinesChecklist = GuideLinesCheckListHelper.getInstance().populateGuideLinesCheckListFormForm(guideCheckListForm); 
		
		duplGuidelinesChecklist = igEntryTransaction.isGuideLinesDuplicated(duplGuidelinesChecklist);
		
		//duplReligionSection is using to check the duplication. if there is duplication then it will return object
		
		if (duplGuidelinesChecklist != null && duplGuidelinesChecklist.getIsActive()) {
			throw new DuplicateException();
		} else if (duplGuidelinesChecklist != null && !duplGuidelinesChecklist.getIsActive()) {
			guideCheckListForm.setDuplId(duplGuidelinesChecklist.getId());
			throw new ReActivateException();
		}
		
		GuidelinesChecklist guidelinesChecklist = GuideLinesCheckListHelper.getInstance().populateGuideLinesCheckListFormForm(guideCheckListForm); 
									//this method will set all the form data to religionSection for saving
		if ("Add".equalsIgnoreCase(mode)) {
			guidelinesChecklist.setCreatedDate(new Date());
			guidelinesChecklist.setLastModifiedDate(new Date());
			guidelinesChecklist.setCreatedBy(guideCheckListForm.getUserId());
			guidelinesChecklist.setModifiedBy(guideCheckListForm.getUserId());
		} else // edit
		{
			guidelinesChecklist.setModifiedBy(guideCheckListForm.getUserId());
			guidelinesChecklist.setLastModifiedDate(new Date());
		}
		isAdded = igEntryTransaction.addGuidelinesCheckList(guidelinesChecklist, mode); 
		log.debug("inside addGuideLinesCheckList");
		return isAdded;
	}

	
	/**
	*/
	public List<GuideLinesCheckListTO> getGuidelinesChecklist() throws Exception{
		IGuideLinesCheckListTransactions igTransactions = GuideLinesCheckListTransactionImpl.getInstance(); 
		List<GuidelinesChecklist> guideList = igTransactions.getGuideLineCheckList();   // subReligionList will have list of ReligionSection object 
		List<GuideLinesCheckListTO> checkListToList = GuideLinesCheckListHelper.getInstance().copyCheckListBosToTos(guideList); 
		log.debug("leaving getGuidelinesChecklist in Handler");
		return checkListToList;
	}

	/**
	 * 
	 * @param 
	 *        
	 * @return boolean true / false based on result.
	 * @throws Exception 
	 */

	public boolean deleteGuideLines(int id, Boolean activate, GuideLinesCheckListForm guForm) throws Exception {
		IGuideLinesCheckListTransactions iGuTransactions = GuideLinesCheckListTransactionImpl.getInstance();
		boolean result = iGuTransactions.deleteGuidelinesChecklist(id, activate, guForm);
						//same method is calling for activate & de-activate. so activate param is used
		log.debug("leaving deleteGuideLines in Handler");
		return result;
	}

}
