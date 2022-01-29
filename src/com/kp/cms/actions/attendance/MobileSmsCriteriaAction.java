package com.kp.cms.actions.attendance;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.attendance.MobileSmsCriteriaForm;
import com.kp.cms.handlers.admin.CourseHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.attendance.ClassEntryHandler;
import com.kp.cms.handlers.attendance.MobileSMSCriteriaHandler;
import com.kp.cms.handlers.exam.ExamGenHandler;
import com.kp.cms.to.admin.CourseTO;
import com.kp.cms.to.attendance.ClassesTO;
import com.kp.cms.to.attendance.MobileSMSCriteriaTO;
import com.kp.cms.utilities.CurrentAcademicYear;

public class MobileSmsCriteriaAction extends BaseDispatchAction{

	private static Log log = LogFactory.getLog(MobileSmsCriteriaAction.class);

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 *  MobileSmsCriteriaAction
	 * @throws Exception
	 */

	public ActionForward initMobileSmsCriteria(ActionMapping mapping, ActionForm form,	HttpServletRequest request, 
			HttpServletResponse response) throws Exception
			{
		log.info("Call of initMobileSmsCriteria method in MobileSmsCriteriaAction.class");
		MobileSmsCriteriaForm mobileSmsCriteriaForm=(MobileSmsCriteriaForm)form;
		ActionMessages messages = new ActionMessages();
		try
		{
			mobileSmsCriteriaForm.setYear(null);
			mobileSmsCriteriaForm.clear();
			mobileSmsCriteriaForm.clearAll();
			setUserId(request, mobileSmsCriteriaForm);
			setRequestedDataToForm(mobileSmsCriteriaForm);
		}
		catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			mobileSmsCriteriaForm.setErrorMessage(msg);
			mobileSmsCriteriaForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("end of initMobileSmsCriteria method in MobileSmsCriteriaAction.class");
		return mapping.findForward(CMSConstants.ATTENDANCE_MOBILE_CRITERIA);
			}
	/*
	 * this method set all required data to the form
	 * */
	private void setRequestedDataToForm(MobileSmsCriteriaForm mobileSmsCriteriaForm) throws Exception {
		log.debug("Entering:setRequestedDataToForm  method in MobileSmsCriteriaAction.class");
		List<ClassesTO> classEntryLists = null;
		ExamGenHandler e = new ExamGenHandler();
		mobileSmsCriteriaForm.setParticular("true");
		mobileSmsCriteriaForm.setCourseGroupCodeList(e.getCourseGroupCodeList());
		if (mobileSmsCriteriaForm.getYear() == null
				|| mobileSmsCriteriaForm.getYear().isEmpty()) {
			Calendar calendar = Calendar.getInstance();
			int currentYear = calendar.get(Calendar.YEAR);
			int year = CurrentAcademicYear.getInstance().getAcademicyear();
			if (year != 0) {
				currentYear = year;
			}
			mobileSmsCriteriaForm.setYear(Integer.toString(currentYear));
			classEntryLists = ClassEntryHandler.getInstance().getAllClasses(
					currentYear);
		} else {
			int year = Integer.parseInt(mobileSmsCriteriaForm.getYear().trim());
			classEntryLists = ClassEntryHandler.getInstance().getAllClasses(
					year);
		}
        Map<Integer, String> classMapForAll= new HashMap<Integer, String>();
        classMapForAll=CommonAjaxHandler.getInstance().getClassesByYear(Integer.parseInt(mobileSmsCriteriaForm.getYear().trim()));
		mobileSmsCriteriaForm.setClassMapforAll(classMapForAll);
        List<CourseTO> courseList = CourseHandler.getInstance()
		.getCourses();
		mobileSmsCriteriaForm.setCourseList(courseList);
		Map<Integer, String> classMap=new HashMap<Integer, String>();
		mobileSmsCriteriaForm.setClassMap(classMap);
		List<MobileSMSCriteriaTO> mobileSMSCriteriaTOList=MobileSMSCriteriaHandler.getInstance().getAllSMSCriterias();
		mobileSmsCriteriaForm.setMobileSMSCriteriaTOlist(mobileSMSCriteriaTOList);
		log.debug("Leaving: setRequestedDataToForm  method in MobileSmsCriteriaAction.class");

	}

	/*
	 * add addSMSCriteria
	 * */
	public ActionForward addSMSCriteria(ActionMapping mapping, ActionForm form,	HttpServletRequest request, 
			HttpServletResponse response) throws Exception
			{
		log.info("call of addSMSCriteria method in MobileSmsCriteriaAction.class");
		MobileSmsCriteriaForm mobileSmsCriteriaForm=(MobileSmsCriteriaForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = mobileSmsCriteriaForm.validate(mapping, request);
		boolean isAdded=false;
		boolean isDuplicated=false;
		if(!errors.isEmpty()) 
		{
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.ATTENDANCE_MOBILE_CRITERIA);
		}
		if(mobileSmsCriteriaForm.getParticular()==null && mobileSmsCriteriaForm.getParticular().isEmpty())
		{
			errors.add("error", new ActionError("nowledgepro.new.scheduled.sms.send.criteria.smsM.particularorAll"));
			
		}
		
		if(mobileSmsCriteriaForm.getDisableSMS().equalsIgnoreCase("true"))
		{
			if(mobileSmsCriteriaForm.getFromDate()==null || mobileSmsCriteriaForm.getFromDate().isEmpty())
			{
				errors.add("error", new ActionError("knowledgepro.new.scheduled.sms.send.startDate"));
			}
			if(mobileSmsCriteriaForm.getToDate()==null || mobileSmsCriteriaForm.getToDate().isEmpty())
			{
				errors.add("error", new ActionError("knowledgepro.new.scheduled.sms.send.endDate"));
			}
		}
		if(mobileSmsCriteriaForm.getStartHours().trim().equals("00") || mobileSmsCriteriaForm.getStartHours().trim().equals("0"))
		{
			errors.add("error", new ActionError("knowledgepro.new.scheduled.sms.send.criteria.smsH.invalid"));
		}
		if(mobileSmsCriteriaForm.getStartHours()!=null && !mobileSmsCriteriaForm.getStartHours().isEmpty())
		{
			Integer smsH=Integer.parseInt(mobileSmsCriteriaForm.getStartHours());
			if(smsH>24)
			{
				errors.add("error", new ActionError("knowledgepro.new.scheduled.sms.send.criteria.smsH.invalid"));
			}
		}
		if(mobileSmsCriteriaForm.getStartMins()!=null && !mobileSmsCriteriaForm.getStartMins().isEmpty())
		{
			Integer smsM=Integer.parseInt(mobileSmsCriteriaForm.getStartMins());
			if(smsM>59)
			{
				errors.add("error", new ActionError("knowledgepro.new.scheduled.sms.send.criteria.smsM.invalid"));
			}
		}

		if(!errors.isEmpty())
		{
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.ATTENDANCE_MOBILE_CRITERIA);
		}
		else
		{  

			isDuplicated=MobileSMSCriteriaHandler.getInstance().isDuplicated(mobileSmsCriteriaForm);
			if(isDuplicated)
			{
				errors.add("error", new ActionError("knowledgepro.new.scheduled.sms.send.criteria.alreadyExists"));
				saveErrors(request, errors);
			}
			else
			{
				isAdded=MobileSMSCriteriaHandler.getInstance().addSMSCriteria(mobileSmsCriteriaForm);
				if(isAdded)
				{
					ActionMessage message = new ActionMessage("knowledgepro.new.scheduled.sms.send.criteria.addsuccess");
					messages.add("messages", message);
					saveMessages(request, messages);
					mobileSmsCriteriaForm.clearAll();
					setRequestedDataToForm(mobileSmsCriteriaForm);
				}
				else
				{
					ActionMessage message = new ActionMessage("knowledgepro.new.scheduled.sms.send.criteria.addfailure");
					messages.add("messages", message);
					saveMessages(request, messages);
				}
			}
			log.info("end of addSMSCriteria method in MobileSmsCriteriaAction.class");
			return mapping.findForward(CMSConstants.ATTENDANCE_MOBILE_CRITERIA); 
		}

			}

	public ActionForward deleteSMSCriteria(ActionMapping mapping, ActionForm form,	HttpServletRequest request, 
			HttpServletResponse response) throws Exception
			{
		log.info("call of deleteSMSCriteria method in MobileSmsCriteriaAction.class");
		MobileSmsCriteriaForm mobileSmsCriteriaForm=(MobileSmsCriteriaForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = mobileSmsCriteriaForm.validate(mapping, request);
		boolean isDeleted=false;
		if(mobileSmsCriteriaForm.getClassName()!=null && !mobileSmsCriteriaForm.getClassName().isEmpty()&&
				mobileSmsCriteriaForm.getSmsCriteriaId()!=null && !mobileSmsCriteriaForm.getSmsCriteriaId().isEmpty())
		{
			isDeleted=MobileSMSCriteriaHandler.getInstance().deleteSMSCriteria(mobileSmsCriteriaForm);
			if(isDeleted)
			{

				ActionMessage message = new ActionMessage("knowledgepro.new.scheduled.sms.send.criteria.deleteSuccess");
				messages.add("messages", message);
				saveMessages(request, messages);
				mobileSmsCriteriaForm.clearAll();
				setRequestedDataToForm(mobileSmsCriteriaForm);

			}
			else
			{
				ActionMessage message = new ActionMessage("knowledgepro.new.scheduled.sms.send.criteria.deletefailure");
				messages.add("messages", message);
				saveMessages(request, messages);
				mobileSmsCriteriaForm.clearAll();
				setRequestedDataToForm(mobileSmsCriteriaForm);
			}

		}
		else
		{
			ActionMessage message = new ActionMessage("knowledgepro.new.scheduled.sms.send.criteria.deletefailure");
			messages.add("messages", message);
			saveMessages(request, messages);
			mobileSmsCriteriaForm.clearAll();
			setRequestedDataToForm(mobileSmsCriteriaForm);
		}

		log.info("end of deleteSMSCriteria method in MobileSmsCriteriaAction.class");
		return mapping.findForward(CMSConstants.ATTENDANCE_MOBILE_CRITERIA); 
			}

	public ActionForward editSMSCriteria(ActionMapping mapping, ActionForm form,	HttpServletRequest request, 
			HttpServletResponse response) throws Exception
			{
		log.debug("call of deleteSMSCriteria method in MobileSmsCriteriaAction.class");
		MobileSmsCriteriaForm mobileSmsCriteriaForm=(MobileSmsCriteriaForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = mobileSmsCriteriaForm.validate(mapping, request);
		if(mobileSmsCriteriaForm.getClassName()!=null && !mobileSmsCriteriaForm.getClassName().isEmpty()&&
				mobileSmsCriteriaForm.getSmsCriteriaId()!=null && !mobileSmsCriteriaForm.getSmsCriteriaId().isEmpty())
		{
			mobileSmsCriteriaForm=MobileSMSCriteriaHandler.getInstance()
			.populatedataTOform(mobileSmsCriteriaForm,mobileSmsCriteriaForm.getSmsCriteriaId());
			request.setAttribute("stateOperation", "edit");
		}
		log.debug("end of deleteSMSCriteria method in MobileSmsCriteriaAction.class");
		return mapping.findForward(CMSConstants.ATTENDANCE_MOBILE_CRITERIA); 
			}
	public ActionForward updateSMSCriteria(ActionMapping mapping, ActionForm form,	HttpServletRequest request, 
			HttpServletResponse response) throws Exception
			{
		log.debug("call of updateSMSCriteria method in MobileSmsCriteriaAction.class");
		MobileSmsCriteriaForm mobileSmsCriteriaForm=(MobileSmsCriteriaForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = mobileSmsCriteriaForm.validate(mapping, request);
		boolean isUpdated=false;
		boolean isDuplicated=false;
		if(mobileSmsCriteriaForm.getDisableSMS().equalsIgnoreCase("true"))
		{
			if(mobileSmsCriteriaForm.getFromDate()==null || mobileSmsCriteriaForm.getFromDate().isEmpty())
			{
				errors.add("error", new ActionError("knowledgepro.new.scheduled.sms.send.startDate"));
			}
			if(mobileSmsCriteriaForm.getToDate()==null || mobileSmsCriteriaForm.getToDate().isEmpty())
			{
				errors.add("error", new ActionError("knowledgepro.new.scheduled.sms.send.endDate"));
			}
		}
		
		if(mobileSmsCriteriaForm.getStartHours().trim().equals("00") || mobileSmsCriteriaForm.getStartHours().trim().equals("0"))
		{
			errors.add("error", new ActionError("knowledgepro.new.scheduled.sms.send.criteria.smsH.invalid"));
		}
		if(mobileSmsCriteriaForm.getStartHours()!=null && !mobileSmsCriteriaForm.getStartHours().isEmpty())
		{
			Integer smsH=Integer.parseInt(mobileSmsCriteriaForm.getStartHours());
			if(smsH>24)
			{
				errors.add("error", new ActionError("knowledgepro.new.scheduled.sms.send.criteria.smsH.invalid"));
			}
		}
		if(mobileSmsCriteriaForm.getStartMins()!=null && !mobileSmsCriteriaForm.getStartMins().isEmpty())
		{
			Integer smsM=Integer.parseInt(mobileSmsCriteriaForm.getStartMins());
			if(smsM>59)
			{
				errors.add("error", new ActionError("knowledgepro.new.scheduled.sms.send.criteria.smsM.invalid"));
			}
		}
		if(!errors.isEmpty())
		{
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.ATTENDANCE_MOBILE_CRITERIA);
		}
		else
		{  
			isUpdated=MobileSMSCriteriaHandler.getInstance().updateSMSCriteria(mobileSmsCriteriaForm);
			if(isUpdated)
			{

				ActionMessage message = new ActionMessage("knowledgepro.new.scheduled.sms.send.criteria.updateSuccess");
				messages.add("messages", message);
				saveMessages(request, messages);
				mobileSmsCriteriaForm.clearAll();
				setRequestedDataToForm(mobileSmsCriteriaForm);
			}
			else
			{
				ActionMessage message = new ActionMessage("knowledgepro.new.scheduled.sms.send.criteria.updatefailure");
				messages.add("messages", message);
				saveMessages(request, messages);
				setRequestedDataToForm(mobileSmsCriteriaForm);
			}
		}
		mobileSmsCriteriaForm.clearAll();
		setRequestedDataToForm(mobileSmsCriteriaForm);
		log.debug("end of updateSMSCriteria method in MobileSmsCriteriaAction.class");
		return mapping.findForward(CMSConstants.ATTENDANCE_MOBILE_CRITERIA); 
			}

}
