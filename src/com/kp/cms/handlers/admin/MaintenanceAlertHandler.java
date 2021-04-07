package com.kp.cms.handlers.admin;

import java.util.Date;

import com.kp.cms.bo.admin.MaintenanceAlert;
import com.kp.cms.forms.admin.MaintenanceAlertForm;
import com.kp.cms.helpers.admin.MaintenanceAlertHelper;
import com.kp.cms.to.admin.MaintenanceTo;
import com.kp.cms.transactions.admin.IMaintenanceAlert;
import com.kp.cms.transactionsimpl.admin.MaintenanceAlertTxnImpl;
import com.kp.cms.utilities.CommonUtil;

public class MaintenanceAlertHandler {

	
	IMaintenanceAlert transaction = MaintenanceAlertTxnImpl.getInstance();
    public static volatile MaintenanceAlertHandler maintenanceAlertHandler = null;
    
    
    /**
     * @return
     */
    public static MaintenanceAlertHandler getInstance() {
		if (maintenanceAlertHandler == null) {
			maintenanceAlertHandler = new MaintenanceAlertHandler();
			return maintenanceAlertHandler;
		}
		return maintenanceAlertHandler;
	}


	/**
	 * @param alertForm
	 * @param mode
	 * @return
	 * @throws Exception 
	 */
	public boolean addOrUpdateMaintenanceAlert(MaintenanceAlertForm alertForm,String mode) throws Exception {
		return transaction.addOrUpdateMaintenanceAlert(MaintenanceAlertHelper.getInstance().convertFormToBo(alertForm,mode),mode);
	}


	/**
	 * @param alertForm
	 * @return
	 * @throws Exception
	 */
	public boolean duplicateCheck(MaintenanceAlertForm alertForm) throws Exception {
		return transaction.duplicateCheck(alertForm);
	}


	public void getMaintenanceDetails(MaintenanceAlertForm alertForm) throws Exception {
         MaintenanceAlert maintenanceAlert=transaction.getMaintenanceDetails();
         if(maintenanceAlert!=null){
        	MaintenanceTo to=new MaintenanceTo();
        	to.setMaintenanceDate(CommonUtil.formatDates(maintenanceAlert.getMaintenanceDate()));
        	to.setMaintenanceMessage(maintenanceAlert.getMaintenanceMessage());
        	to.setMaintenanceFromTime(maintenanceAlert.getMaintenanceFromTime());
        	to.setMaintenanceToTime(maintenanceAlert.getMaintenanceToTime());
        	to.setId(maintenanceAlert.getId());
        	alertForm.setMaintenanceTo(to);
         }
	}


	/**
	 * @param alertForm
	 * @throws Exception
	 */
	public void editMaintenanceDetails(MaintenanceAlertForm alertForm) throws Exception {
             MaintenanceAlert alert=transaction.getMaintenaceDetailsById(alertForm.getId());
             if(alert!=null){
            	alertForm.setMaintenanceDate(CommonUtil.formatDates(alert.getMaintenanceDate()));
            	alertForm.setOrigMaintenanceDate(CommonUtil.formatDates(alert.getMaintenanceDate()));
            	alertForm.setMaintenanceMessage(alert.getMaintenanceMessage());
            	alertForm.setOrigMaintenanceFromTime(alert.getMaintenanceFromTime());
            	alertForm.setOrigMaintenanceMessage(alert.getMaintenanceMessage());
            	alertForm.setOrigMaintenanceToTime(alert.getMaintenanceToTime());
            	alertForm.setMaintenanceFromTime(alert.getMaintenanceFromTime());
            	alertForm.setMaintenanceToTime(alert.getMaintenanceToTime());
             }
	}


	/**
	 * @param alertForm
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public boolean deleteMaintenanceAlert(MaintenanceAlertForm alertForm,String mode) throws Exception {
		  MaintenanceAlert alert=transaction.getMaintenaceDetailsById(alertForm.getId());
		  boolean isDeleted=false;
		  if(alert!=null){
			  alert.setIsActive(false);
			  isDeleted=transaction.addOrUpdateMaintenanceAlert(alert, mode);
		  }
		return isDeleted;
	}


	public String getMaintenanceDetailsByDate() throws Exception {
		MaintenanceAlert alert=transaction.getMaintenanceDetails();
		String maintenanceMessage=null;
		if(alert!=null){
			Date curDate=new Date();
			int curHours=0;
		  	String[] fromTime=alert.getMaintenanceFromTime().split(" ");
		  	String[] toTime=alert.getMaintenanceToTime().split(" ");
		  	if(fromTime[1].equalsIgnoreCase("pm") && toTime[1].equalsIgnoreCase("pm")){
		  			String[] hourMinToTime=toTime[0].split(":");
			  		int hoursToTime=0;
			  		if(Integer.parseInt(hourMinToTime[0])==12){
			  			hoursToTime=Integer.parseInt(hourMinToTime[0]);
			  		}else{
			  			hoursToTime=Integer.parseInt(hourMinToTime[0])+12;
			  		}
			  		String[] hourMinFromTime=fromTime[0].split(":");
			  		int hoursFromTime=0;
			  		if(Integer.parseInt(hourMinFromTime[0])==12){
			  			hoursFromTime=Integer.parseInt(hourMinFromTime[0]);
			  		}else{
			  			hoursFromTime=Integer.parseInt(hourMinFromTime[0])+12;	
			  		}
			  		if(curDate.getHours()==0){
			  			curHours=curDate.getHours()+12;
			  		}else{
			  			curHours=curDate.getHours();
			  		}
			  		if(hoursFromTime<=curHours && hoursToTime>=curHours){
			  			int totalFromMin=Integer.parseInt(hourMinFromTime[1])+hoursFromTime*60;
			  			int totalCurMin=curHours*60+curDate.getMinutes();
			  			int totalToMin=Integer.parseInt(hourMinToTime[1])+hoursToTime*60;
			  			if(totalFromMin<=totalCurMin && totalToMin>=totalCurMin){
			  				maintenanceMessage=alert.getMaintenanceMessage();
			  			}
			  		}
		  	}else if(fromTime[1].equalsIgnoreCase("am") && toTime[1].equalsIgnoreCase("am")){
		  			String[] hourMinToTime=toTime[0].split(":");
			  		String[] hourMinFromTime=fromTime[0].split(":");
			  		if(curDate.getHours()==0){
			  			curHours=curDate.getHours()+12;
			  		}else{
			  			curHours=curDate.getHours();
			  		}
			  		if(Integer.parseInt(hourMinFromTime[0])<=curHours && Integer.parseInt(hourMinToTime[0])>=curHours){
			  			int totalFromMin=Integer.parseInt(hourMinFromTime[0])*60+Integer.parseInt(hourMinFromTime[1]);
			  			int totalCurMin=curHours*60+curDate.getMinutes();
			  			int totalToMin=Integer.parseInt(hourMinToTime[0])*60+Integer.parseInt(hourMinToTime[1]);
			  			if(totalFromMin<=totalCurMin && totalToMin>=totalCurMin){
			  				maintenanceMessage=alert.getMaintenanceMessage();
			  			}
			  		}
		  	}else if(fromTime[1].equalsIgnoreCase("pm") && toTime[1].equalsIgnoreCase("am")){
		  		String[] hourMinToTime=toTime[0].split(":");
		  		String[] hourMinFromTime=fromTime[0].split(":");
		  		if(curDate.getHours()==0){
		  			curHours=curDate.getHours()+12;
		  		}else{
		  			curHours=curDate.getHours();
		  		}
		  		int hoursFromTime=0;
		  		if(Integer.parseInt(hourMinFromTime[0])==12){
		  			hoursFromTime=Integer.parseInt(hourMinFromTime[0]);
		  		}else{
		  			hoursFromTime=Integer.parseInt(hourMinFromTime[0])+12;	
		  		}
		  		if(hoursFromTime>=curHours && Integer.parseInt(hourMinToTime[0])<=curHours){
		  			int totalFromMin=hoursFromTime*60+Integer.parseInt(hourMinFromTime[1]);
		  			int totalToMin=Integer.parseInt(hourMinToTime[0])*60+Integer.parseInt(hourMinToTime[1]);
		  			int totalCurMin=curHours*60+curDate.getMinutes();
		  			if(totalFromMin<=totalCurMin && totalToMin>=totalCurMin){
		  				maintenanceMessage=alert.getMaintenanceMessage();
		  			}
		  		}
		  		
		  	}else if(fromTime[1].equalsIgnoreCase("am") && toTime[1].equalsIgnoreCase("pm")){
		  		String[] hourMinToTime=toTime[0].split(":");
		  		String[] hourMinFromTime=fromTime[0].split(":");
		  		if(curDate.getHours()==0){
		  			curHours=curDate.getHours()+12;
		  		}else{
		  			curHours=curDate.getHours();
		  		}
		  		int hoursToTime=0;
		  		if(Integer.parseInt(hourMinToTime[0])==12){
		  			hoursToTime=Integer.parseInt(hourMinToTime[0]);
		  		}else{
		  			hoursToTime=Integer.parseInt(hourMinToTime[0])+12;
		  		}
		  		if(Integer.parseInt(hourMinFromTime[0])<=curHours && hoursToTime>=curHours){
		  			int totalFromMin=Integer.parseInt(hourMinFromTime[0])*60+Integer.parseInt(hourMinFromTime[1]);
		  			int totalCurMin=curHours*60+curDate.getMinutes();
		  			int totalToMin=hoursToTime*60+Integer.parseInt(hourMinToTime[1]);
		  			if(totalFromMin<=totalCurMin && totalToMin>=totalCurMin){
		  				maintenanceMessage=alert.getMaintenanceMessage();
		  			}
		  		}
		  	}
		}
		return maintenanceMessage;
	}
}
