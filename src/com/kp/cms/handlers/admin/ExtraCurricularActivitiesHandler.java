package com.kp.cms.handlers.admin;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.ExtracurricularActivity;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.admin.ExtraCurricularActivitiesForm;
import com.kp.cms.helpers.admin.ExtraCurricularActivitiesHelper;
import com.kp.cms.to.admin.ExtracurricularActivityTO;
import com.kp.cms.transactions.admin.IExtraCurricularActivitiesTransaction;
import com.kp.cms.transactionsimpl.admin.ExtraCurricularActivitiesTransactionImpl;


public class ExtraCurricularActivitiesHandler {
	private static Log log = LogFactory.getLog(ExtraCurricularActivitiesHandler.class);
	public static volatile ExtraCurricularActivitiesHandler extraCurricularActivitiesHandler = null;

	public static ExtraCurricularActivitiesHandler getInstance() {
		if (extraCurricularActivitiesHandler == null) {
			extraCurricularActivitiesHandler = new ExtraCurricularActivitiesHandler();
			return extraCurricularActivitiesHandler;
		}
		return extraCurricularActivitiesHandler;
	}
	/**
	 * getting all the records for Ui display
	 * @return
	 * @throws Exception
	 */
	public List<ExtracurricularActivityTO> getActivity() throws Exception {
		log.debug("inside getActivity");
		IExtraCurricularActivitiesTransaction iExtraCurricularActivitiesTransaction = ExtraCurricularActivitiesTransactionImpl.getInstance();
		List<ExtracurricularActivity> activityList = iExtraCurricularActivitiesTransaction.getActivities();
		List<ExtracurricularActivityTO> activityToList = ExtraCurricularActivitiesHelper.getInstance().copyActivityBosToTos(activityList); 
		log.debug("leaving getActivity");
		return activityToList;
	}
	
	/**
	 * handler class method for adding extracurricular activity
	 * @param extraCurricularActivitiesForm
	 * @return
	 * @throws Exception
	 */
	public boolean addActivity(ExtraCurricularActivitiesForm extraCurricularActivitiesForm) throws Exception {
		log.debug("inside addActivity");
		IExtraCurricularActivitiesTransaction iExtraCurricularActivitiesTransaction = ExtraCurricularActivitiesTransactionImpl.getInstance();
		boolean isAdded = false;

		ExtracurricularActivity duplExtracurricularActivity = ExtraCurricularActivitiesHelper.getInstance().populateActivityDataFromForm(extraCurricularActivitiesForm); 

		duplExtracurricularActivity = iExtraCurricularActivitiesTransaction.isActivityNameDuplcated(duplExtracurricularActivity);
		if (duplExtracurricularActivity  != null && duplExtracurricularActivity.getIsActive()) {
			throw new DuplicateException();
		} else if (duplExtracurricularActivity  != null && !duplExtracurricularActivity .getIsActive()) {
			extraCurricularActivitiesForm.setDuplActId(duplExtracurricularActivity.getId());
			throw new ReActivateException();
		}
		ExtracurricularActivity extracurricularActivity = ExtraCurricularActivitiesHelper.getInstance().populateActivityDataFromForm(extraCurricularActivitiesForm); 
		
		extracurricularActivity.setCreatedBy(extraCurricularActivitiesForm.getUserId());
		extracurricularActivity.setModifiedBy(extraCurricularActivitiesForm.getUserId());
		extracurricularActivity.setCreatedDate(new Date());
		extracurricularActivity.setLastModifiedDate(new Date());
		isAdded = iExtraCurricularActivitiesTransaction.addActivity(extracurricularActivity);
		log.debug("leaving addActivity");
		return isAdded;
	}
	
	/**
	 * hanlder class method for update
	 * @param extraCurricularActivitiesForm
	 * @return
	 * @throws Exception
	 */
	public boolean updateActivity(ExtraCurricularActivitiesForm extraCurricularActivitiesForm) throws Exception {
		log.debug("inside updateActivity");
		IExtraCurricularActivitiesTransaction iExtraCurricularActivitiesTransaction = ExtraCurricularActivitiesTransactionImpl.getInstance();
		boolean isUpdate = false;
		//original variables are used for update. in update no need to check duplication for the current record
		Boolean originalNotChanged = false;

		String actName = extraCurricularActivitiesForm.getName().trim();
		String origActName = extraCurricularActivitiesForm.getOrigName().trim();

		int orgId = 0;
		if ((extraCurricularActivitiesForm.getOrgId() != null) || (!extraCurricularActivitiesForm.getOrgId().isEmpty())){
			orgId = Integer.parseInt(extraCurricularActivitiesForm.getOrgId());
		}
		int origOrgId = extraCurricularActivitiesForm.getEditOrgId();		
		
		
		if (actName.trim().equalsIgnoreCase(origActName.trim()) && orgId == origOrgId) {
			originalNotChanged = true;
		}

		if (!originalNotChanged) {
			ExtracurricularActivity duplExtracurricularActivity = ExtraCurricularActivitiesHelper.getInstance().populateActivityDataFromForm(extraCurricularActivitiesForm); 
			duplExtracurricularActivity = iExtraCurricularActivitiesTransaction.isActivityNameDuplcated(duplExtracurricularActivity); 
			if (duplExtracurricularActivity != null && duplExtracurricularActivity.getIsActive()) {
				throw new DuplicateException();
			} else if (duplExtracurricularActivity != null && !duplExtracurricularActivity.getIsActive()) {
				extraCurricularActivitiesForm.setDuplActId(duplExtracurricularActivity.getId());
				throw new ReActivateException();
			}
		}
		ExtracurricularActivity extracurricularActivity = ExtraCurricularActivitiesHelper.getInstance().populateActivityDataFromForm(extraCurricularActivitiesForm); 
	
		extracurricularActivity.setModifiedBy(extraCurricularActivitiesForm.getUserId());
		extracurricularActivity.setLastModifiedDate(new Date());
		isUpdate = iExtraCurricularActivitiesTransaction.updateActivity(extracurricularActivity); 
		log.debug("leaving updateActivity");
		return isUpdate;
	}

	/****
	 * handler class method for delete
	 * @param id
	 * @param activate
	 * @return
	 * @throws Exception
	 */
	public boolean deleteActivity(int id, Boolean activate, String userId) throws Exception {
		log.debug("inside deleteActivity");
		IExtraCurricularActivitiesTransaction iExtraCurricularActivitiesTransaction = ExtraCurricularActivitiesTransactionImpl.getInstance();
		boolean result = iExtraCurricularActivitiesTransaction.deleteActivity(id, activate, userId) ;
		log.debug("leaving deleteActivity");
		return result;
	}
	
	
}
