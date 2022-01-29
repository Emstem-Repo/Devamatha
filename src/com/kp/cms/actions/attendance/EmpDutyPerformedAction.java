package com.kp.cms.actions.attendance;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import com.kp.cms.bo.admin.AttendanceEntryDay;
import com.kp.cms.bo.admin.AttendanceInstructor;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.forms.attendance.AttendanceEntryForm;
import com.kp.cms.handlers.attendance.AttendanceEntryDayHandler;
import com.kp.cms.to.attendance.AttendanceEntryDayTO;
import com.kp.cms.to.attendance.DutyPerformedTo;
import com.kp.cms.transactionsimpl.attendance.AttendanceEntryTransactionImpl;


@SuppressWarnings("deprecation")
public class EmpDutyPerformedAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(EmpDutyPerformedAction.class);

	public ActionForward initEmpDutyPerformed(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AttendanceEntryForm attendanceEntryDayForm = (AttendanceEntryForm) form;
		//It use for Help,Don't Remove
		HttpSession session=request.getSession();
		session.setAttribute("field","Caste");
		try{
		List<DutyPerformedTo> dutyPerformedList = AttendanceEntryDayHandler.getInstance().getDutyPerformedList();
		attendanceEntryDayForm.setDutyPerformedList(dutyPerformedList);
			}catch (Exception e) {
				log.error("Error occured in caste Entry Action", e);
				String msg = super.handleApplicationException(e);
				attendanceEntryDayForm.setErrorMessage(msg);
				attendanceEntryDayForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
				
			}
		return mapping.findForward("dutyPerform");
	}
	
	
	public ActionForward addEmpDutyPerformed(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		
		AttendanceEntryForm attendanceEntryForm = (AttendanceEntryForm) form;
		ActionMessages messages = new ActionMessages();
		setUserId(request, attendanceEntryForm);
		//ActionErrors errors = attendanceEntryForm.validate(mapping, request);
		ActionErrors errors= new ActionErrors();
		if (attendanceEntryForm.getDutyPerformedDate()==null || attendanceEntryForm.getDutyPerformedDate().isEmpty()) {
			errors.add("error", new ActionError("knowledgepro.duty.empty",  attendanceEntryForm.getDutyPerformedDate()));
		}
		if (attendanceEntryForm.getDutyPerformed()==null || attendanceEntryForm.getDutyPerformed().isEmpty()) {
			errors.add("error", new ActionError("knowledgepro.duty.empty",  attendanceEntryForm.getDutyPerformed()));
		}
		/*if (errors.isEmpty()) {
			List<AttendanceInstructor> attList= AttendanceEntryTransactionImpl.getInstance().getAttendanceEntryDetails(attendanceEntryForm);
			if (!attList.isEmpty() ) {
				errors.add("error", new ActionError("knowledgepro.duty.activityattendence.attendencetaken",  attendanceEntryForm.getDutyPerformedDate()));
			}
		}*/
		boolean isAttDayAdded = false;
		if (errors.isEmpty()) {
			String day=attendanceEntryForm.getDutyPerformed();
			try{
			setUserId(request, attendanceEntryForm);
			isAttDayAdded = AttendanceEntryDayHandler.getInstance().addEmplDutyPerformed(attendanceEntryForm,"Add",request);
			List<DutyPerformedTo> dutyPerformedList = AttendanceEntryDayHandler.getInstance().getDutyPerformedList();
			attendanceEntryForm.setDutyPerformedList(dutyPerformedList);
			}catch (Exception e) {
				if(e instanceof DuplicateException){
					errors.add("error", new ActionError("knowledgepro.employee.biometric.duplicate",day));
					saveErrors(request, errors);
					List<DutyPerformedTo> dutyPerformedList = AttendanceEntryDayHandler.getInstance().getDutyPerformedList();
					attendanceEntryForm.setDutyPerformedList(dutyPerformedList);
					return mapping.findForward("dutyPerform");	
				}
				/*if(e instanceof ReActivateException){
					errors.add("error", new ActionError("knowledgepro.attendance.attendanceentryday.addfailure.alreadyexist.reactivate",day));
					saveErrors(request, errors);
					HttpSession session=request.getSession();
					session.setAttribute("AttendanceEntryDay",attendanceEntryForm.getAttendanceEntryDay());
					List<AttendanceEntryDayTO> attendanceEntryDayList = AttendanceEntryDayHandler.getInstance().getAttendanceEntryDays();
					attendanceEntryDayForm.setAttendanceEntryDayList(attendanceEntryDayList);
					return mapping.findForward(CMSConstants.ATTENDANCE_ENTRY_DAY);	
				}*/
				log.error("Error occured in caste Entry Action", e);
				String msg = super.handleApplicationException(e);
				attendanceEntryForm.setErrorMessage(msg);
				attendanceEntryForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
				
			}
			if (isAttDayAdded) {
				ActionMessage message = new ActionMessage("knowledgepro.duty.entry.succsess ", attendanceEntryForm.getDutyPerformed());
				messages.add("messages", message);
				saveMessages(request, messages);
				attendanceEntryForm.reset(mapping, request);
				List<DutyPerformedTo> dutyPerformedList = AttendanceEntryDayHandler.getInstance().getDutyPerformedList();
				attendanceEntryForm.setDutyPerformedList(dutyPerformedList);
			}else{
				// failed
				errors.add("error", new ActionError("knowledgepro.duty.entry.failed",  attendanceEntryForm.getDutyPerformed()));
				saveErrors(request, errors);
				List<DutyPerformedTo> dutyPerformedList = AttendanceEntryDayHandler.getInstance().getDutyPerformedList();
				attendanceEntryForm.setDutyPerformedList(dutyPerformedList);
			}
		} else {
			saveErrors(request, errors);
			List<DutyPerformedTo> dutyPerformedList = AttendanceEntryDayHandler.getInstance().getDutyPerformedList();
			attendanceEntryForm.setDutyPerformedList(dutyPerformedList);
			return mapping.findForward("dutyPerform");
		}
		
		
		return mapping.findForward("dutyPerform");
	}
	
	/*public ActionForward deleteAttendanceEntryDay(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AttendanceEntryForm attendanceEntryDayForm = (AttendanceEntryForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		int id =Integer.parseInt(attendanceEntryDayForm.getId());
		String day=attendanceEntryDayForm.getDay();
		boolean isAttendanceEntryDay=false;
		try{
			setUserId(request, attendanceEntryDayForm);
			isAttendanceEntryDay = AttendanceEntryDayHandler.getInstance()
				.deleteAttendanceEntryDay(id, false, attendanceEntryDayForm);
		}catch (Exception e) {
			log.error("error in submit of delete page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				attendanceEntryDayForm.setErrorMessage(msg);
				attendanceEntryDayForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				String msg = super.handleApplicationException(e);
				attendanceEntryDayForm.setErrorMessage(msg);
				attendanceEntryDayForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}
		
		if (isAttendanceEntryDay) {
			ActionMessage message = new ActionMessage("knowledgepro.admin.attendanceentryday.deletesuccess", day);
			messages.add("messages", message);
			saveMessages(request, messages);
			attendanceEntryDayForm.reset(mapping, request);
			List<AttendanceEntryDayTO> attendanceEntryDayList = AttendanceEntryDayHandler.getInstance().getAttendanceEntryDays();
			attendanceEntryDayForm.setAttendanceEntryDayList(attendanceEntryDayList);
		}else{
			// failed
			errors.add("error", new ActionError("knowledgepro.admin.attendanceentryday.deletefailure",day));
			saveErrors(request, errors);
			List<AttendanceEntryDayTO> attendanceEntryDayList = AttendanceEntryDayHandler.getInstance().getAttendanceEntryDays();
			attendanceEntryDayForm.setAttendanceEntryDayList(attendanceEntryDayList);
		}
		return mapping.findForward(CMSConstants.ATTENDANCE_ENTRY_DAY);
	}*/
	
	/*public ActionForward updateAttendanceEntryDay(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AttendanceEntryForm attendanceEntryDayForm = (AttendanceEntryForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = attendanceEntryDayForm.validate(mapping, request);
		boolean isUpdate = false;
		//if (errors.isEmpty()) {
			try{
				if (errors.isEmpty()) {
					List<AttendanceEntryDayTO> attendanceEntryDayList = AttendanceEntryDayHandler.getInstance().getAttendanceEntryDays();
					attendanceEntryDayForm.setAttendanceEntryDayList(attendanceEntryDayList);
					//return mapping.findForward(CMSConstants.ATTENDANCE_ENTRY_DAY);
				}
			setUserId(request, attendanceEntryDayForm);
			
			isUpdate=AttendanceEntryDayHandler.getInstance().addAttendanceEntryDay(attendanceEntryDayForm,"Edit",request);

			}catch (DuplicateException e1) {
				errors.add("error", new ActionError("knowledgepro.attendance.day.name.exists"));
				saveErrors(request, errors);
				
				request.setAttribute("department", "edit");
				return mapping.findForward(CMSConstants.ATTENDANCE_ENTRY_DAY);
			} catch (ReActivateException e1) {
				errors.add("error", new ActionError("knowledgepro.attendance.attendanceentryday.addfailure.alreadyexist.reactivate"));
				saveErrors(request, errors);
				
				request.setAttribute("department", "edit");
				return mapping.findForward(CMSConstants.ATTENDANCE_ENTRY_DAY);
			} catch (Exception e) {
				log.error("error in update admitted through page...", e);
				if (e instanceof BusinessException) {
					String msgKey = super.handleBusinessException(e);
					ActionMessage message = new ActionMessage(msgKey);
					messages.add("messages", message);
					request.setAttribute("department", "edit");
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				} else if (e instanceof ApplicationException) {
					String msg = super.handleApplicationException(e);
					attendanceEntryDayForm.setErrorMessage(msg);
					attendanceEntryDayForm.setErrorStack(e.getMessage());
					request.setAttribute("department", "edit");
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				} else {
					throw e;
				}
			}
		
		
		if (isUpdate) {
			ActionMessage message = new ActionMessage("knowledgepro.admin.attendanceentryday.updatesuccess", attendanceEntryDayForm.getDay());
			messages.add("messages", message);
			saveMessages(request, messages);
			attendanceEntryDayForm.reset(mapping, request);
			List<DutyPerformedTo> attendanceEntryDayList = AttendanceEntryDayHandler.getInstance().getAttendanceEntryDays();
			attendanceEntryDayForm.setDutyPerformedList(attendanceEntryDayList);
		}else{
			// failed
			errors.add("error", new ActionError("knowledgepro.admin.attendanceentryday.updatefailure",  attendanceEntryDayForm.getDay()));
			saveErrors(request, errors);
			List<AttendanceEntryDayTO> attendanceEntryDayList = AttendanceEntryDayHandler.getInstance().getAttendanceEntryDays();
			attendanceEntryDayForm.setAttendanceEntryDayList(attendanceEntryDayList);
		}
		return mapping.findForward(CMSConstants.ATTENDANCE_ENTRY_DAY);
	}*/
	
	/*public ActionForward reActivateAttendanceEntryDay(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		
		AttendanceEntryForm attendanceEntryDayForm = (AttendanceEntryForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		AttendanceEntryDay attendanceEntryDay=(AttendanceEntryDay)request.getSession().getAttribute("AttendanceEntryDay");
		boolean isActivate = false;
		try{
			if(attendanceEntryDayForm.getDupId()!=null){
				setUserId(request, attendanceEntryDayForm);
				isActivate=AttendanceEntryDayHandler.getInstance().reActivateAttendanceEntryDay(attendanceEntryDay, attendanceEntryDayForm.getUserId());
			}
		}catch (Exception e) {
			log.error("error in delete Department page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				List<AttendanceEntryDayTO> attendanceEntryDayList = AttendanceEntryDayHandler.getInstance().getAttendanceEntryDays();
				attendanceEntryDayForm.setAttendanceEntryDayList(attendanceEntryDayList);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				attendanceEntryDayForm.setErrorMessage(msg);
				attendanceEntryDayForm.setErrorStack(e.getMessage());
				List<AttendanceEntryDayTO> attendanceEntryDayList = AttendanceEntryDayHandler.getInstance().getAttendanceEntryDays();
				attendanceEntryDayForm.setAttendanceEntryDayList(attendanceEntryDayList);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		
		if(isActivate){
			ActionMessage message = new ActionMessage("knowledgepro.admin.attendanceentryday.activate");
			messages.add("messages",message);
			saveMessages(request, messages);
		}
		else{
			errors.add("error", new ActionError("knowledgepro.admin.attendanceentryday.activatefailure"));
			saveErrors(request, errors);
		}
		List<AttendanceEntryDayTO> attendanceEntryDayList = AttendanceEntryDayHandler.getInstance().getAttendanceEntryDays();
		attendanceEntryDayForm.setAttendanceEntryDayList(attendanceEntryDayList);
		return mapping.findForward(CMSConstants.ATTENDANCE_ENTRY_DAY);
	}*/
	
	/*public ActionForward editAttendanceEntryDay(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {

		AttendanceEntryForm attendanceEntryDayForm = (AttendanceEntryForm)form;
		setUserId(request, attendanceEntryDayForm);
		ActionMessages messages = new ActionMessages();
		
		try {
			AttendanceEntryDayHandler.getInstance().editAttendanceEntryDay(attendanceEntryDayForm);
			request.setAttribute("department", "edit");

		} catch (BusinessException businessException) {
			log.info("Exception editMenus");
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {	
			String msg = super.handleApplicationException(exception);
			attendanceEntryDayForm.setErrorMessage(msg);
			attendanceEntryDayForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		//load the details for module drop down and menus list.
		List<AttendanceEntryDayTO> attendanceEntryDayList = AttendanceEntryDayHandler.getInstance().getAttendanceEntryDays();
		attendanceEntryDayForm.setAttendanceEntryDayList(attendanceEntryDayList);
		return mapping.findForward(CMSConstants.ATTENDANCE_ENTRY_DAY);
	}*/
	public void setUserId(HttpServletRequest request, BaseActionForm form){
		HttpSession session = request.getSession(false);
		if(session.getAttribute("uid")!=null){
			form.setUserId(session.getAttribute("uid").toString());
		}
		request.getSession().removeAttribute("baseActionForm");
	}		
}
