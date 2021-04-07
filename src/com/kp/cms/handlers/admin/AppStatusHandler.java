package com.kp.cms.handlers.admin;

import java.util.Date;
import java.util.List;

import com.kp.cms.bo.admin.ApplicationStatus;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.admin.AppStatusForm;
import com.kp.cms.helpers.admin.AppStatusHelper;
import com.kp.cms.to.admin.AppStatusTO;
import com.kp.cms.transactions.admin.IAppStatusTransaction;
import com.kp.cms.transactionsimpl.admin.AppStatusTransactionImpl;

public class AppStatusHandler {
 public static volatile AppStatusHandler appStatusHandler = null;
 private AppStatusHandler(){
	 
 }
 public static AppStatusHandler getInstance(){
	 if(appStatusHandler == null){
		 appStatusHandler = new AppStatusHandler();
		 return appStatusHandler;
	 }
	 return appStatusHandler;
 }
/**
 * @return
 * @throws Exception 
 */
public List<AppStatusTO> getApplicationStatus() throws Exception {
	 List<AppStatusTO> appStatusTOs=null;
	IAppStatusTransaction iAppStatusTransaction = AppStatusTransactionImpl.getInstance();
	List<ApplicationStatus> appStatus = iAppStatusTransaction.getApplicationStatus();
	appStatusTOs=AppStatusHelper.getInstance().copyBODataToTO(appStatus);
	return appStatusTOs;
}
/**
 * @param appStatusForm
 * @param mode
 * @return
 * @throws Exception 
 */
public boolean addAppStatusEntry(AppStatusForm appStatusForm,String mode) throws Exception {
	boolean isAdded=false;
	IAppStatusTransaction iAppStatusTransaction = AppStatusTransactionImpl.getInstance();
	Boolean originalNotChanged=false;
	String appStatus="";
	String orgAppStatus="";
	if(appStatusForm.getApplicationStatus()!=null && !appStatusForm.getApplicationStatus().isEmpty()){
		appStatus=appStatusForm.getApplicationStatus().trim();
	}
	if(appStatusForm.getOrgApplicationStatus()!=null && !appStatusForm.getOrgApplicationStatus().isEmpty()){
		orgAppStatus=appStatusForm.getOrgApplicationStatus().trim();
	}
	if(appStatus.equalsIgnoreCase(orgAppStatus)){
		originalNotChanged = true;
	}
	if(mode.equals("Add")){
		originalNotChanged = false;
	}
	if(!originalNotChanged){
		ApplicationStatus dupAppStatus=iAppStatusTransaction.isDuplicateEntry(appStatusForm);
		if(dupAppStatus!=null && dupAppStatus.getIsActive() == true){
			throw new DuplicateException();
		}
		if(dupAppStatus!=null && dupAppStatus.getIsActive() == false){
			appStatusForm.setDupId(dupAppStatus.getId());
			throw new ReActivateException();
		}
	}
	ApplicationStatus applicationStatus=AppStatusHelper.getInstance().populateAppStatusDataFromForm(appStatusForm);
	if(mode.equals("Add")){
		applicationStatus.setCreatedBy(appStatusForm.getUserId());
		applicationStatus.setCreatedDate(new Date());
		applicationStatus.setLastModifiedDate(new Date());
		applicationStatus.setModifiedBy(appStatusForm.getUserId());
		
	}else{//Edit
		applicationStatus.setLastModifiedDate(new Date());
		applicationStatus.setModifiedBy(appStatusForm.getUserId());
	}
	isAdded=iAppStatusTransaction.addAppStatus(applicationStatus,mode);
	return isAdded;
}
/**
 * @param appStatusId
 * @param appStatusForm
 * @param b
 * @return
 * @throws Exception 
 */
public boolean deleteAppStatusEntry(int appStatusId, AppStatusForm appStatusForm, boolean Activate) throws Exception {
	boolean isDeleted=false;
	IAppStatusTransaction iAppStatusTransaction = AppStatusTransactionImpl.getInstance();
	isDeleted=iAppStatusTransaction.deleteAppStatus(appStatusId,appStatusForm,Activate);
	return isDeleted;
}

}
