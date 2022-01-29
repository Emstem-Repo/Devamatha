package com.kp.cms.handlers.admin;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.TermsConditionChecklist;
import com.kp.cms.bo.admin.TermsConditions;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.admin.TermsConditionForm;
import com.kp.cms.helpers.admin.TermsConditionHelper;
import com.kp.cms.to.admin.TermsConditionChecklistTO;
import com.kp.cms.to.admin.TermsConditionTO;
import com.kp.cms.transactions.admin.ITermsConditionTransaction;
import com.kp.cms.transactionsimpl.admin.TermsConditionTransactionImpl;

/**
 * 
 * @author 
 *
 */
public class TermsConditionHandler {
	private static final Log log = LogFactory.getLog(TermsConditionHandler.class);
	private static volatile TermsConditionHandler termsConditionHandler = null;

	public static TermsConditionHandler getInstance() {
		if (termsConditionHandler == null) {
			termsConditionHandler = new TermsConditionHandler();
		}
		return termsConditionHandler;
	}
	/**
	 * getting terms&condition from table according to id
	 * @param conditionsId
	 * @return
	 * @throws Exception
	 */

	public List<TermsConditionTO> getTermsConditionWithId(int conditionsId) throws Exception {
		log.debug("inside getTermsConditionWithId");
		ITermsConditionTransaction termsConditionTransaction = new TermsConditionTransactionImpl();
		List<TermsConditions> termsConditionList = termsConditionTransaction.getTermsCondition(conditionsId);

		List<TermsConditionTO> termsandConditionsToList = TermsConditionHelper.getInstance().copyTermsAndConditionsBosToTos(
				   termsConditionList, conditionsId);
		log.debug("leaving getTermsConditionWithId");
		return termsandConditionsToList;

	}

	/***
	 * getting all the terms&conditions
	 * @param conditionsId
	 * @param year
	 * @return
	 * @throws Exception
	 */

	public List<TermsConditionTO> getTermsCondition(int conditionsId,int year) throws Exception {
		log.debug("inside getTermsCondition");
		ITermsConditionTransaction termsConditionTransaction = new TermsConditionTransactionImpl();
		List<TermsConditions> termsConditionList = termsConditionTransaction.getTermsConditionForYear(conditionsId,year);
		List<TermsConditionTO> termsandConditionsToList = TermsConditionHelper.getInstance().copyTermsAndConditionsBosToTos(termsConditionList, conditionsId);
		log.debug("leaving getTermsCondition");
		return termsandConditionsToList;

	}
	/**
	 * method to add terms&conditions
	 * @param termsConditionForm
	 * @param mode
	 * @return
	 * @throws DuplicateException
	 * @throws Exception
	 */
	
	public boolean addTermsCondition(TermsConditionForm termsConditionForm,	String mode) throws DuplicateException, Exception {
		ITermsConditionTransaction iTermsConditionTransaction = TermsConditionTransactionImpl.getInstance();
		boolean isAdded;
		boolean originalNotChanged = false;
		int tempCourseId = Integer.parseInt(termsConditionForm.getCourseId());
		int tempOrigCourseId = termsConditionForm.getOrigCourseId();
		int tempYear = Integer.parseInt(termsConditionForm.getYears());
		int tempOrgYear = termsConditionForm.getOrigYear();
		if ((tempCourseId == tempOrigCourseId) && (tempYear == tempOrgYear)) {
			originalNotChanged = true;
		}
		if("Add".equals(mode)){
			originalNotChanged = false; // for add no need to check original changed
		}
		if (!originalNotChanged) {
			TermsConditions dupltermsConditions = TermsConditionHelper.getInstance().populateTermsConditions(termsConditionForm);

			dupltermsConditions = iTermsConditionTransaction.isTermsAndConditionsDuplcated(dupltermsConditions);
			if (dupltermsConditions != null
					&& dupltermsConditions.getIsActive()) {
				throw new DuplicateException();
			} else if (dupltermsConditions != null
					&& !dupltermsConditions.getIsActive()) {
				termsConditionForm.setDuplId(dupltermsConditions.getId());
				throw new ReActivateException();
			}
		}
		TermsConditions termsConditions = TermsConditionHelper.getInstance().populateTermsConditions(termsConditionForm);
		isAdded = iTermsConditionTransaction.addTermsCondition(termsConditions,	mode, originalNotChanged);
		log.debug("leaving addTermsCondition");
		return isAdded;
	}

	/**
	 * method for deleting terms&conditions
	 * @param conditionsId
	 * @param activate
	 * @return
	 * @throws Exception
	 */
	public boolean deleteTermsCondition(int conditionsId, Boolean activate, String uId) throws Exception {
		log.debug("inside deleteTermsCondition");
		ITermsConditionTransaction iTermsConditionTransaction = TermsConditionTransactionImpl.getInstance();
		boolean result = iTermsConditionTransaction.deleteTermsCondition(conditionsId, activate, uId);
		log.debug("leaving deleteTermsCondition");
		return result;

	}
	
	/**
	 * 
	 * @param termsConditionForm
	 * @return
	 * @throws DuplicateException
	 * @throws Exception
	 */
	
	public boolean saveTermsConditionDeatils(TermsConditionForm termsConditionForm) throws DuplicateException, Exception {
		log.debug("inside addTermsCondition");
		ITermsConditionTransaction iTermsConditionTransaction = TermsConditionTransactionImpl.getInstance();
		boolean isAdded;
		List<TermsConditionChecklist> tcChecklist = TermsConditionHelper.getInstance().populateTermsConditionsDetails(termsConditionForm);
		isAdded = iTermsConditionTransaction.addTermsConditionCheckList(tcChecklist);
		log.debug("leaving addTermsCondition");
		return isAdded;
	}

	/***
	 * getting all the terms&conditions
	 * @param conditionsId
	 * @param year
	 * @return
	 * @throws Exception
	 */

	public List<TermsConditionChecklistTO> getTermsConditionCheckList() throws Exception {
		log.debug("inside getTermsConditionList");
		ITermsConditionTransaction termsConditionTransaction = new TermsConditionTransactionImpl();
		List<TermsConditionChecklist> tcCheckList = termsConditionTransaction.getTermsConditionCheckList();
		List<TermsConditionChecklistTO> tcCheckListToList = TermsConditionHelper.getInstance().copyTermsAndConditionsCheckListBosToTos(tcCheckList);
		log.debug("leaving getTermsCondition");
		return tcCheckListToList;

	}	
	/**
	 * 
	 * @param courseId
	 * @return
	 * @throws Exception
	 */
	public List<TermsConditionChecklistTO> getTermsConditionCheckListWithCourseId(int courseId/*, Integer year*/) throws Exception {
		log.debug("inside getTermsConditionCheckListWithCourseId");
		ITermsConditionTransaction termsConditionTransaction = new TermsConditionTransactionImpl();
		List<TermsConditionChecklist> tcCheckList = termsConditionTransaction.getTermsConditionCheckListWithCourseId(courseId/*, year*/);
		List<TermsConditionChecklistTO> tcCheckListToList = TermsConditionHelper.getInstance().copyTermsAndConditionsCheckListBosToTos(tcCheckList);
		log.debug("leaving getTermsConditionCheckListWithCourseId");
		return tcCheckListToList;

	}	
	/**
	 * 
	 * @param courseId
	 * @param year
	 * @return
	 * @throws Exception
	 */
	public boolean deleteTermsConditionCheckList(int courseId/*, int year*/) throws Exception {
		log.debug("inside deleteTermsConditionCheckList");
		ITermsConditionTransaction iTermsConditionTransaction = TermsConditionTransactionImpl.getInstance();
		boolean result = iTermsConditionTransaction.deleteTermsConditionCheckList(courseId/*, year*/);
		log.debug("leaving deleteTermsConditionCheckList");
		return result;

	}
	
	/**
	 * 
	 * @param courseId
	 * @param year
	 * @return
	 * @throws Exception
	 */
	public boolean deleteTermsConditionCheckListById(int id) throws Exception {
		log.debug("inside deleteTermsConditionCheckList");
		ITermsConditionTransaction iTermsConditionTransaction = TermsConditionTransactionImpl.getInstance();
		boolean result = iTermsConditionTransaction.deleteTermsConditionCheckListById(id);
		log.debug("leaving deleteTermsConditionCheckList");
		return result;

	}
	public boolean updateTermsConditionList(List<TermsConditionChecklistTO> termsConditionList,String userid) throws Exception {
		ITermsConditionTransaction iTermsConditionTransaction = TermsConditionTransactionImpl.getInstance();
		return iTermsConditionTransaction.updateTermsConditionList(termsConditionList,userid);
	}
}
