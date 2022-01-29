package com.kp.cms.handlers.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.kp.cms.bo.admin.Caste;
import com.kp.cms.bo.admin.ExtraCreditActivityType;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.admin.ExtraCreditActivityTypeForm;
import com.kp.cms.helpers.admin.CasteHelper;
import com.kp.cms.helpers.admin.ExtraCreditActivityTypeHelper;
import com.kp.cms.to.admin.ExtraCreditActivityTypeTo;
import com.kp.cms.transactions.admin.ICasteTransaction;
import com.kp.cms.transactions.admin.IExtraCreditActivityTypeTransaction;
import com.kp.cms.transactionsimpl.admin.CasteTransactionImpl;
import com.kp.cms.transactionsimpl.admin.ExtraCreditActivityTypeImpl;

public class ExtraCreditActivityTypeHandler {
	private static volatile ExtraCreditActivityTypeHandler extraCreditActivityTypeHandler =null;
	
	private ExtraCreditActivityTypeHandler(){
		
	}
    public static ExtraCreditActivityTypeHandler getInstance(){
    	if(extraCreditActivityTypeHandler==null){
    		extraCreditActivityTypeHandler =new ExtraCreditActivityTypeHandler();
    	}
    	return extraCreditActivityTypeHandler;
    }
	public List<ExtraCreditActivityTypeTo> getActivityTypeList() {
		IExtraCreditActivityTypeTransaction transaction = new ExtraCreditActivityTypeImpl();
		List<ExtraCreditActivityType> activityBo=transaction.getActivityList();
		List<ExtraCreditActivityTypeTo> activityList=ExtraCreditActivityTypeHelper.convertBOtoToActiveList(activityBo);
		return activityList;
	}
	public boolean getAddActivityName(ExtraCreditActivityTypeForm extraCreditActivityTypeForm,HttpServletRequest request)
			throws Exception {
		IExtraCreditActivityTypeTransaction transaction=new ExtraCreditActivityTypeImpl();
		ExtraCreditActivityType extraCreditActivityType = ExtraCreditActivityTypeHelper.convertBOtoTO(extraCreditActivityTypeForm,"Add");
		ExtraCreditActivityType extraCreditActivityType1 = ExtraCreditActivityTypeHelper.convertBOtoTO(extraCreditActivityTypeForm,"Add");
		extraCreditActivityType1=transaction.isActivityDuplicated(extraCreditActivityType1);
		boolean isActivityAdd = false;
		if (extraCreditActivityType1 != null && extraCreditActivityType1.getIsActive()) {
			throw new DuplicateException();
		} else if (extraCreditActivityType1 != null && !extraCreditActivityType1.getIsActive()) {
			request.getSession().setAttribute("Activity",extraCreditActivityType1);
			extraCreditActivityTypeForm.setReactivateid(extraCreditActivityType1.getId());
			throw new ReActivateException();
		}else if(transaction != null) {
			isActivityAdd = transaction.addActivity(extraCreditActivityType);
		}
		return isActivityAdd;
	}
	public boolean updateActivate(
			ExtraCreditActivityTypeForm extraCreditActivityTypeForm,
			HttpServletRequest request) throws Exception{
		IExtraCreditActivityTypeTransaction transaction=new ExtraCreditActivityTypeImpl();
		ExtraCreditActivityType extraCreditActivityType = ExtraCreditActivityTypeHelper.convertBOtoTO(extraCreditActivityTypeForm,"Update");
		ExtraCreditActivityType extraCreditActivityType1 = ExtraCreditActivityTypeHelper.convertBOtoTO(extraCreditActivityTypeForm,"Update");
		extraCreditActivityType1=transaction.isActivityDuplicated(extraCreditActivityType1);
		boolean isActivityEdit = false;
		if (!extraCreditActivityTypeForm.getActivityName().equals(extraCreditActivityTypeForm.getOrgActivityName())){
			if(extraCreditActivityType1!=null && extraCreditActivityType1.getIsActive()){
			throw new DuplicateException();
			}
		} 
		if (extraCreditActivityType1 != null && !extraCreditActivityType1.getIsActive()) {
			request.getSession().setAttribute("Activity",extraCreditActivityType1);
			extraCreditActivityTypeForm.setReactivateid(extraCreditActivityType1.getId());
			throw new ReActivateException();
		}else if(transaction != null) {
			isActivityEdit = transaction.editActivity(extraCreditActivityType);
		}
		return isActivityEdit;
	}
	public boolean deleteActivity(int activityId, String userId)throws Exception {
		IExtraCreditActivityTypeTransaction transaction=new ExtraCreditActivityTypeImpl();
		boolean isActivityDeleted = false;
		if (transaction != null) {
			isActivityDeleted = transaction.deleteCaste(activityId,userId);
		}
		return isActivityDeleted;
	}
	public boolean reActiveActivity(ExtraCreditActivityType type, String userId)throws Exception {
		IExtraCreditActivityTypeTransaction transaction=new ExtraCreditActivityTypeImpl();
		boolean isActivityReActivate=transaction.reActivateCaste(type,userId);
		return isActivityReActivate;
	}
    
    
    
}
