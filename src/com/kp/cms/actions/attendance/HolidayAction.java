package com.kp.cms.actions.attendance;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hslf.record.ExVideoContainer;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.bo.exam.HolidayBO;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.attendance.HolidayNameForm;
import com.kp.cms.handlers.attendance.HolidayHandler;
import com.kp.cms.to.attendance.HolidayTO;
@SuppressWarnings("deprecation")
public class HolidayAction extends BaseDispatchAction{
	private static final Log log = LogFactory.getLog(HolidayAction.class);
	
	public ActionForward initHolidayEntry(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse responce)throws Exception{
		
		HolidayNameForm holidayForm=(HolidayNameForm)form;
		
		HttpSession session=request.getSession();
		session.setAttribute("holiday","Holiday");
		try{
			setHolidayToRequest(request,holidayForm);
		}catch (Exception e) {
			log.error("error in initHoliday...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				holidayForm.setErrorMessage(msg);
				holidayForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		
		return mapping.findForward(CMSConstants.HOLIDAY_DATA);
		
	}
	
	public ActionForward addHoliday(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse responce)throws Exception{
		HolidayNameForm holidayForm=(HolidayNameForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = holidayForm.validate(mapping, request);
		String holidayDate=holidayForm.getHolidayDate();
		boolean isAdded=false;
		boolean isDateIsExist=false;
		if (errors.isEmpty()) {
		try{
			setUserId(request, holidayForm);
			isDateIsExist=HolidayHandler.getInstance().checkDate(holidayForm);
			if(isDateIsExist){
				errors.add("error", new ActionError("knowledgepro.holiday.isdateisexist"));
				saveErrors(request, errors);
				setHolidayToRequest(request,holidayForm);
				return mapping.findForward(CMSConstants.HOLIDAY_DATA);
			
			}
			isAdded=HolidayHandler.getInstance().addHoliday(holidayForm , request);
			}catch (Exception e) {
				if(e instanceof DuplicateException){
					errors.add("error", new ActionError("knowledgepro.holiday.date.exists"));
					saveErrors(request, errors);
					setHolidayToRequest(request,holidayForm);
					return mapping.findForward(CMSConstants.HOLIDAY_DATA);
					
				}
				if(e instanceof ReActivateException){
					errors.add("error", new ActionError("knowledgepro.holiday.addfailure.alreadyexist.reactivate"));
					saveErrors(request, errors);
					setHolidayToRequest(request,holidayForm);
					return mapping.findForward(CMSConstants.HOLIDAY_DATA);
				}
				log.error("Error occured in holiday entry action");
				String msg = super.handleApplicationException(e);
				holidayForm.setErrorMessage(msg);
				holidayForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}else {
			saveErrors(request, errors);
			setHolidayToRequest(request,holidayForm);
			return mapping.findForward(CMSConstants.HOLIDAY_DATA);
		}
		if(isAdded){
			ActionMessage message=new ActionMessage("knowledgepro.holiday.addsuccess");
			messages.add("messages",message);
			saveMessages(request, messages);
			holidayForm.reset(mapping, request);
			setHolidayToRequest(request,holidayForm);
		
		}else {
			errors.add("error", new ActionError("knowledgepro.holiday.addfailure"));
			saveErrors(request, errors);
			holidayForm.reset(mapping, request);
			setHolidayToRequest(request,holidayForm);
		}
		return mapping.findForward(CMSConstants.HOLIDAY_DATA);
	}
	
	public ActionForward updateHoliday(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse responce)throws Exception{
		
		HolidayNameForm holidayForm=(HolidayNameForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = holidayForm.validate(mapping, request);
		String holidayDate=holidayForm.getHolidayDate();
		boolean isUpdated=false;
		if (errors.isEmpty()) {
		try{
			setUserId(request, holidayForm);
			isUpdated=HolidayHandler.getInstance().updateHoliday(holidayForm , request);
			}catch (Exception e) {
				if(e instanceof DuplicateException){
					errors.add("error", new ActionError("knowledgepro.holiday.date.exists"));
					saveErrors(request, errors);
					setHolidayToRequest(request,holidayForm);
					request.setAttribute("operation", "edit");
					return mapping.findForward(CMSConstants.HOLIDAY_DATA);
					
				}
				if(e instanceof ReActivateException){
					errors.add("error", new ActionError("knowledgepro.holiday.addfailure.alreadyexist.reactivate"));
					saveErrors(request, errors);
					setHolidayToRequest(request,holidayForm);
					request.setAttribute("operation", "edit");
					return mapping.findForward(CMSConstants.HOLIDAY_DATA);
				}
				log.error("Error occured in holiday entry action");
				String msg = super.handleApplicationException(e);
				holidayForm.setErrorMessage(msg);
				holidayForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}else {
			saveErrors(request, errors);
			setHolidayToRequest(request,holidayForm);
			request.setAttribute("operation", "edit");
			return mapping.findForward(CMSConstants.HOLIDAY_DATA);
		}
		if(isUpdated){
			ActionMessage message=new ActionMessage("knowledgepro.holiday.updatesuccess");
			messages.add("messages",message);
			saveMessages(request, messages);
			holidayForm.reset(mapping, request);
			setHolidayToRequest(request,holidayForm);
		
		}else {
			errors.add("error", new ActionError("knowledgepro.holiday.updatefailure"));
			saveErrors(request, errors);
			holidayForm.reset(mapping, request);
			setHolidayToRequest(request,holidayForm);
		}
		return mapping.findForward(CMSConstants.HOLIDAY_DATA);
	}
	
	public ActionForward deleteHoliday(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse responce)throws Exception{
		HolidayNameForm holidayForm=(HolidayNameForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = holidayForm.validate(mapping, request);
		String holidayDate=holidayForm.getHolidayDate();
		int holidayId=holidayForm.getHolidayId();
		boolean isDeleted=false;
		try{
			setUserId(request, holidayForm);
			isDeleted=HolidayHandler.getInstance().deleteHoliday(holidayId,holidayForm.getUserId());
		}catch (Exception e) {
			log.error("Error occured in holiday Entry Action", e);
			String msg = super.handleApplicationException(e);
			holidayForm.setErrorMessage(msg);
			holidayForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		if(isDeleted){
			ActionMessage message=new ActionMessage("knowledgepro.holiday.updatesuccess");
			messages.add("messages",message);
			saveMessages(request, messages);
			holidayForm.reset(mapping, request);
			setHolidayToRequest(request,holidayForm);
		
		}else {
			errors.add("error", new ActionError("knowledgepro.holiday.updatefailure"));
			saveErrors(request, errors);
			holidayForm.reset(mapping, request);
			setHolidayToRequest(request,holidayForm);
		}
		return mapping.findForward(CMSConstants.HOLIDAY_DATA);
	}
	
	public ActionForward reActivateHoliday(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse responce)throws Exception{
		HolidayNameForm holidayForm=(HolidayNameForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = holidayForm.validate(mapping, request);
		HolidayBO holidayBO=(HolidayBO)request.getSession().getAttribute("holiday");
		boolean isHolidayReActivate=false;
		try{
			setUserId(request, holidayForm);
			isHolidayReActivate=HolidayHandler.getInstance().reActivateHoliday(holidayBO,holidayForm.getUserId());
		}catch (Exception e) {
			log.error("Error occured in holiday Entry Action", e);
			String msg = super.handleApplicationException(e);
			holidayForm.setErrorMessage(msg);
			holidayForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		if(isHolidayReActivate){
			ActionMessage message=new ActionMessage("knowledgepro.holiday.updatesuccess");
			messages.add("messages",message);
			saveMessages(request, messages);
			holidayForm.reset(mapping, request);
			setHolidayToRequest(request,holidayForm);
		
		}else {
			errors.add("error", new ActionError("knowledgepro.holiday.updatefailure"));
			saveErrors(request, errors);
			holidayForm.reset(mapping, request);
			setHolidayToRequest(request,holidayForm);
		}
		return mapping.findForward(CMSConstants.HOLIDAY_DATA);
	}
	
	

	
	public void setHolidayToRequest(HttpServletRequest request,HolidayNameForm holidayForm) throws Exception {
		List<HolidayTO> holidayList=HolidayHandler.getInstance().getHolidays();
		holidayForm.setHolidayList(holidayList);
		
	}


}
