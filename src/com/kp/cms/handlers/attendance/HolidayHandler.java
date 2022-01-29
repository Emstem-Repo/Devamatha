package com.kp.cms.handlers.attendance;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.exam.HolidayBO;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.attendance.HolidayNameForm;
import com.kp.cms.helpers.attendance.HolidayHelper;
import com.kp.cms.to.attendance.HolidayTO;

import com.kp.cms.transactions.attandance.IHolidayTransaction;
import com.kp.cms.transactionsimpl.attendance.HolidayTransactionImp;




public class HolidayHandler {
	public static volatile HolidayHandler holidayHandler=null;
	public static final Log log=LogFactory.getLog(HolidayHandler.class);
	private HolidayHandler(){
		
	}
	public static HolidayHandler getInstance(){
		if(holidayHandler == null){
			holidayHandler=new HolidayHandler();
		}
		return holidayHandler;
	}
	public List<HolidayTO> getHolidays() throws Exception {
		IHolidayTransaction iHolidayTransaction=HolidayTransactionImp.getInstance();
		List<HolidayBO> holidayBoList=iHolidayTransaction.getHolidays();
		List<HolidayTO> holidayToList=HolidayHelper.getInstance().convertBoToTo(holidayBoList);
		log.error("ending of  getHolidays method in HolidayHandler");
		return holidayToList;
	}
	
	public boolean addHoliday(HolidayNameForm holidayForm,
			HttpServletRequest request)throws Exception{
		IHolidayTransaction iHolidayTransaction=new HolidayTransactionImp();
		boolean isHolidayAdd=false;
		HolidayBO holidayBO1=HolidayHelper.getInstance().convertTOToBO(holidayForm,"Add");
		HolidayBO holidayBO=HolidayHelper.getInstance().convertTOToBO(holidayForm,"Add");
		holidayBO=iHolidayTransaction.isHolidayDuplicate(holidayBO);
		
		
		if (holidayBO != null && holidayBO.getIsActive()) {
			throw new DuplicateException();
		}
		if(holidayBO!=null && !holidayBO.getIsActive()){
			request.getSession().setAttribute("holiday", holidayBO);
			holidayForm.setReactiveId(holidayBO.getHolidayId());
			throw new ReActivateException();
		}
		
        
		if(iHolidayTransaction!=null){
			
			isHolidayAdd=iHolidayTransaction.addHoliday(holidayBO1);
		}
		return isHolidayAdd;
	}
	
	public boolean updateHoliday(HolidayNameForm holidayForm,HttpServletRequest request) throws Exception {
		IHolidayTransaction iHolidayTransaction=new HolidayTransactionImp();
		boolean isHolidayEdit=false;
		HolidayBO holidayBO1=HolidayHelper.getInstance().convertTOToBO(holidayForm,"Update");
		HolidayBO holidayBO=HolidayHelper.getInstance().convertTOToBO(holidayForm,"Update");
		holidayBO=iHolidayTransaction.isHolidayDuplicate(holidayBO);
		
		if(holidayForm.getHolidayName().equals(holidayForm.getOrgHolidayName().trim())){
		 if (holidayBO != null && holidayBO.getIsActive()) {
			 
				throw new DuplicateException();
			}
		}
		if(holidayBO!=null && !holidayBO.getIsActive()){
			request.getSession().setAttribute("holiday", holidayBO);
			holidayForm.setReactiveId(holidayBO.getHolidayId());
			throw new ReActivateException();
		}
       
		if(iHolidayTransaction!=null){
			isHolidayEdit=iHolidayTransaction.updateHoliday(holidayBO1);
		}
		return isHolidayEdit;
	}
	public boolean deleteHoliday(int holidayId, String userId)throws Exception {
		IHolidayTransaction iHolidayTransaction=new HolidayTransactionImp();
        boolean isHolidayDeleted=false;
        if(iHolidayTransaction!=null){
        	isHolidayDeleted=iHolidayTransaction.deleteHoliday(holidayId,userId);
        }
        
		return isHolidayDeleted;
	}
	public boolean reActivateHoliday(HolidayBO holidayBO, String userId) throws Exception {
		IHolidayTransaction iHolidayTransaction=new HolidayTransactionImp();
		boolean isHolidayReActivate=iHolidayTransaction.reActivateHoliday(holidayBO,userId);
		return  isHolidayReActivate;
	}
	public boolean checkDate(HolidayNameForm holidayForm) throws Exception {
		IHolidayTransaction iHolidayTransaction=new HolidayTransactionImp();
		HolidayBO holidayList=iHolidayTransaction.isHolidayDuplicate1(holidayForm);
		if(holidayList!=null) return true;
		return false;
	}
	
	
	
	
	
	
	
	
}
