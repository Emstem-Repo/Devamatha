package com.kp.cms.helpers.admin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.kp.cms.bo.admin.ApplicationStatus;
import com.kp.cms.forms.admin.AppStatusForm;
import com.kp.cms.to.admin.AppStatusTO;

public class AppStatusHelper {
 public static volatile AppStatusHelper appStatusHelper = null;
 private AppStatusHelper(){
	 
 }
 public static AppStatusHelper getInstance(){
	 if(appStatusHelper == null){
		 appStatusHelper = new AppStatusHelper();
		 return appStatusHelper;
	 }
	 return appStatusHelper;
 }
/**
 * @param appStatus
 * @return
 */
public List<AppStatusTO> copyBODataToTO(List<ApplicationStatus> appStatus)throws Exception {
	List<AppStatusTO> statusTOs= new ArrayList<AppStatusTO>();
	if(appStatus!=null){
		Iterator<ApplicationStatus> iterator=appStatus.iterator();
		while (iterator.hasNext()) {
			ApplicationStatus applicationStatus = (ApplicationStatus) iterator.next();
			AppStatusTO updateTO = new AppStatusTO();
			if(applicationStatus.getId()!=0){
				updateTO.setId(applicationStatus.getId());
			}
			if(applicationStatus.getName()!=null && !applicationStatus.getName().isEmpty()){
				updateTO.setApplicationStatus(applicationStatus.getName());
			}
			if(applicationStatus.getShortName()!=null && !applicationStatus.getShortName().isEmpty()){
				updateTO.setShortName(applicationStatus.getShortName());
			}
			
			statusTOs.add(updateTO);
		}
	}
	return statusTOs;
}
/**
 * @param appStatusForm
 * @return
 */
public ApplicationStatus populateAppStatusDataFromForm( AppStatusForm appStatusForm) {
	ApplicationStatus applicationStatus = new ApplicationStatus();
	applicationStatus.setId(appStatusForm.getId());
	applicationStatus.setName(appStatusForm.getApplicationStatus());
	applicationStatus.setShortName(appStatusForm.getShortName());
	applicationStatus.setIsActive(true);
	return applicationStatus;
}

}
