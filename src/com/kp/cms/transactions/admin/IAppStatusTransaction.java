package com.kp.cms.transactions.admin;

import java.util.List;

import com.kp.cms.bo.admin.ApplicationStatus;
import com.kp.cms.forms.admin.AppStatusForm;

public interface IAppStatusTransaction {

	public List<ApplicationStatus> getApplicationStatus()throws Exception;

	public boolean addAppStatus(ApplicationStatus applicationStatus,String mode)throws Exception;

	public boolean deleteAppStatus(int appStatusId,
			AppStatusForm appStatusForm, boolean appStatusForm2)throws Exception;

	public ApplicationStatus isDuplicateEntry(AppStatusForm appStatusForm)throws Exception;
	
}
