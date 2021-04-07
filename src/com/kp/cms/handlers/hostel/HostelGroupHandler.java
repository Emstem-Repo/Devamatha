package com.kp.cms.handlers.hostel;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.HlApplicationForm;
import com.kp.cms.bo.admin.HlGroup;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.hostel.HostelGroupForm;
import com.kp.cms.helpers.hostel.HostelGroupHelper;
import com.kp.cms.to.hostel.ApplicationFormTO;
import com.kp.cms.to.hostel.HostelGroupTO;
import com.kp.cms.transactions.hostel.IHostelGroupTransactions;
import com.kp.cms.transactionsimpl.hostel.HostelGroupTransactionImpl;

public class HostelGroupHandler {
	public static Log log = LogFactory.getLog(HostelGroupHandler.class);
	public static volatile HostelGroupHandler hostelGroupHandler;
	
	public static HostelGroupHandler getInstance(){
		if(hostelGroupHandler == null){
			hostelGroupHandler = new HostelGroupHandler();
			return hostelGroupHandler;
		}
		return hostelGroupHandler;
	}
	/**
	 * 
	 * @return list of ApplicationFormTO objects, will be used in UI to dispaly.
	 * @throws Exception
	 */
	public List<ApplicationFormTO> getStudentDetails(HostelGroupForm hForm) throws Exception {
		log.debug("inside getStudentDetails");
		IHostelGroupTransactions iHTransactions = HostelGroupTransactionImpl.getInstance();
		String query=HostelGroupHelper.getInstance().getSearchQuery(hForm);
		List<HlApplicationForm> studList = iHTransactions.getStudentDeatils(query);
		List<ApplicationFormTO> studTOList = HostelGroupHelper.getInstance().copyHostelGroupBosToTos(studList);
		log.debug("leaving getHostelDetails");
		return studTOList;
	}

	/**
	 * save method
	*/
	public boolean saveHostelGroup(HostelGroupForm hlForm, String mode) throws Exception {
		log.debug("inside saveHostelGroup");
		IHostelGroupTransactions ihTransactions = HostelGroupTransactionImpl.getInstance();
		boolean isAdded = false;
		
		HlGroup duplHlGroup =  ihTransactions.isHostelGroupDuplcated(hlForm.getGroupName(), Integer.parseInt(hlForm.getHostelId()), hlForm.getId());
		
		if (duplHlGroup != null && duplHlGroup.getIsActive()) {
			throw new DuplicateException();
		}
		else if (duplHlGroup != null && !duplHlGroup.getIsActive())
		{
			hlForm.setDuplId(duplHlGroup.getId());
			throw new ReActivateException();
		}		
				
		Iterator<ApplicationFormTO> studItr = hlForm.getStudList().iterator();
		ApplicationFormTO appFormTO;
		Boolean isDuplicated;
		hlForm.setStudentDuplicated(false);
		while (studItr.hasNext()){
			appFormTO = studItr.next();
			if(appFormTO.isSelected()){
				isDuplicated = ihTransactions.isHostelGroupStudentsDuplcated(appFormTO.getId(), hlForm.getId());
				if(isDuplicated){
					hlForm.setStudentDuplicated(true);
					throw new DuplicateException();
				}
				else
				{
					hlForm.setStudentDuplicated(false);
				}
			}
		}		
		
		HlGroup hlGroup;
		if(mode.equalsIgnoreCase("edit")){
			hlGroup = HostelGroupHelper.getInstance().copyDataFromFormToBOForUpdate(hlForm);
		}
		else
		{
			hlGroup = HostelGroupHelper.getInstance().copyDataFromFormToBO(hlForm);
		}
		
		isAdded = ihTransactions.addHostelGroup(hlGroup, mode);
		log.debug("leaving saveHostelGroup");
		return isAdded;
	}

	/**
	 * 
	 * @return list of HostelGroupTO objects, will be used in UI to dispaly.
	 * @throws Exception
	 */
	public List<HostelGroupTO> getHostelGroup(HostelGroupForm hForm) throws Exception {
		log.info("Start of getHostelGroup of Handler");
		IHostelGroupTransactions iTransactions = HostelGroupTransactionImpl.getInstance();
		String query=HostelGroupHelper.getInstance().getSearchQueryForHostelGroup(hForm);
		List<HlGroup> hlGroupList = iTransactions.getHostelGroup(query);
		List<HostelGroupTO> hlGroupToList = HostelGroupHelper.getInstance().copyHlGroupBosToTos(hlGroupList);
		log.info("End of getHostelGroup of Handler");
		return hlGroupToList;
	}	
	/**
	 * 
	 * @return list of HostelGroupTO objects, will be used in UI to dispaly.
	 * @throws Exception
	 */
	public void getHostelGroupById(int id, HostelGroupForm hForm) throws Exception {
		log.info("Start of getHostelGroupById of Handler");
		IHostelGroupTransactions iTransactions = HostelGroupTransactionImpl.getInstance();
		HlGroup hlGroup = iTransactions.getHostelGroupById(id);
		HostelGroupHelper.getInstance().setRequiredDatatoForm(hlGroup, hForm);
		log.info("End of getHostelGroup of Handler");
	}	

	/**
	 * 
	 * @param
	 * 
	 * @return boolean true / false based on result.
	 */

	public boolean deleteHostelGroup(int id, Boolean activate, HostelGroupForm hForm)	throws Exception {
		IHostelGroupTransactions iGroupTransactions = HostelGroupTransactionImpl.getInstance();
		return iGroupTransactions.deleteHostelGroup(id, activate, hForm); 
	}
	
}
