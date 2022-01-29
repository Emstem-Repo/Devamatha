package com.kp.cms.helpers.admin;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.MaintenanceAlert;
import com.kp.cms.forms.admin.MaintenanceAlertForm;
import com.kp.cms.utilities.CommonUtil;

public class MaintenanceAlertHelper {
	
    public static volatile MaintenanceAlertHelper maintenanceAlertHelper = null;
    private static Log log = LogFactory.getLog(MaintenanceAlertHelper.class);
    
    /**
     * @return
     */
    public static MaintenanceAlertHelper getInstance() {
		if (maintenanceAlertHelper == null) {
			maintenanceAlertHelper = new MaintenanceAlertHelper();
			return maintenanceAlertHelper;
		}
		return maintenanceAlertHelper;
	}

	/**
	 * @param alertForm
	 * @return
	 */
	public MaintenanceAlert convertFormToBo(MaintenanceAlertForm alertForm,String mode)throws Exception {
      MaintenanceAlert maintenanceAlert=new MaintenanceAlert();
      maintenanceAlert.setMaintenanceMessage(alertForm.getMaintenanceMessage());
      maintenanceAlert.setMaintenanceDate(CommonUtil.ConvertStringToDate(alertForm.getMaintenanceDate()));
      maintenanceAlert.setMaintenanceFromTime(alertForm.getMaintenanceFromTime());
      maintenanceAlert.setMaintenanceToTime(alertForm.getMaintenanceToTime());
      if(mode.equalsIgnoreCase("Add")){
		  maintenanceAlert.setCreatedBy(alertForm.getUserId());
		  maintenanceAlert.setCreatedDate(new Date());
      }else if(mode.equalsIgnoreCase("Update")){
    	  maintenanceAlert.setId(alertForm.getId());
    	  maintenanceAlert.setModifiedBy(alertForm.getUserId());
    	  maintenanceAlert.setLastModifiedDate(new Date());
      }
	  maintenanceAlert.setIsActive(true);
	  return maintenanceAlert;
		
	}
     
}
