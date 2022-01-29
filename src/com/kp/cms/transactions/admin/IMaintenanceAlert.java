package com.kp.cms.transactions.admin;

import com.kp.cms.bo.admin.MaintenanceAlert;
import com.kp.cms.forms.admin.MaintenanceAlertForm;

public interface IMaintenanceAlert {

	public boolean addOrUpdateMaintenanceAlert(MaintenanceAlert maintenanceAlert,String mode) throws Exception;

	public boolean duplicateCheck(MaintenanceAlertForm alertForm)throws Exception;

	public MaintenanceAlert getMaintenanceDetails() throws Exception;

	public MaintenanceAlert getMaintenaceDetailsById(int id)throws Exception;

}
