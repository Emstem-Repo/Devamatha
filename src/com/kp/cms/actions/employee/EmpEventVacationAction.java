package com.kp.cms.actions.employee;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
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
import com.kp.cms.actions.admission.TcDetailsOldStudentsAction;
import com.kp.cms.bo.employee.EmpEventVacation;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.forms.admin.DepartmentEntryForm;
import com.kp.cms.forms.employee.EmpEventVacationForm;
import com.kp.cms.handlers.admin.DepartmentEntryHandler;
import com.kp.cms.handlers.employee.EmpEventVacationHandler;
import com.kp.cms.handlers.employee.EmpTypeHandler;
import com.kp.cms.utilities.CommonUtil;

public class EmpEventVacationAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(TcDetailsOldStudentsAction.class);
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initEmpEventVacation(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception{
		EmpEventVacationForm empEventVacationForm=(EmpEventVacationForm)form;
		empEventVacationForm.reset();
		setRequiredDatatoForm(empEventVacationForm);
		setUserId(request,empEventVacationForm);
		
		return mapping.findForward(CMSConstants.EMP_EVENT_VACATION);
	}
	
	private void setRequiredDatatoForm(EmpEventVacationForm empEventVacationForm) throws Exception {
		
		Map<Integer,String> streamMap = EmpEventVacationHandler.getInstance().getStreams();
		empEventVacationForm.setStreamMap(streamMap);
		Map<Integer,String> deptMap = EmpEventVacationHandler.getInstance().getFilteredDepartmentsStreamNames(empEventVacationForm.getStreamId(),empEventVacationForm.getTeachingStaff());
		EmpEventVacationHandler.getInstance().getEmpEventVacationDetails(empEventVacationForm);
		if(deptMap!=null){
			empEventVacationForm.setDeptMap(deptMap);
		}
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	
	public ActionForward submitEvent(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception{
		EmpEventVacationForm empEventVacationForm=(EmpEventVacationForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = empEventVacationForm.validate(mapping, request);
		validateTime(empEventVacationForm, errors);
		boolean isUpdate = false;
			try{
					if(!errors.isEmpty()){
						saveErrors(request, errors);
						setRequiredDatatoForm(empEventVacationForm);
						return mapping.findForward(CMSConstants.EMP_EVENT_VACATION);
					}
					//isUpdate=EmpEventVacationHandler.getInstance().getEmpEventUnique(empEventVacationForm,"Edit");
					isUpdate=EmpEventVacationHandler.getInstance().getEmpEventUnique(empEventVacationForm,"Edit");
					setRequiredDatatoForm(empEventVacationForm);
			}catch (DuplicateException e1) {
				errors.add(CMSConstants.EMP_EVENT_VACATION_EXISTS,new ActionError(CMSConstants.EMP_EVENT_VACATION_EXISTS));
				saveErrors(request, errors);
				setRequiredDatatoForm(empEventVacationForm);
				empEventVacationForm.setMode("edit");
				return mapping.findForward(CMSConstants.EMP_EVENT_VACATION);
			} catch (ReActivateException e1) {
				errors.add(CMSConstants.EMPLOYEE_EVENT_REACTIVE, new ActionError(CMSConstants.EMPLOYEE_EVENT_REACTIVE));
				saveErrors(request, errors);
				setRequiredDatatoForm(empEventVacationForm);
				empEventVacationForm.setMode("edit");
				return mapping.findForward(CMSConstants.EMP_EVENT_VACATION);
			} catch (Exception e) {
				log.error("error in update admitted through page...", e);
				if (e instanceof BusinessException) {
					String msgKey = super.handleBusinessException(e);
					ActionMessage message = new ActionMessage(msgKey);
					messages.add("messages", message);
					empEventVacationForm.setMode("edit");
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				} else if (e instanceof ApplicationException) {
					String msg = super.handleApplicationException(e);
					empEventVacationForm.setErrorMessage(msg);
					empEventVacationForm.setErrorStack(e.getMessage());
					empEventVacationForm.setMode("edit");
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				} else {
					throw e;
				}
			}if(isUpdate){
				ActionMessage actionMessage = new ActionMessage("knowledgepro.employee.event.vacation.update");
				messages.add("messages", actionMessage);
				saveMessages(request, messages);
				empEventVacationForm.reset(mapping, request);
			}else{
				errors.add("error", new ActionError("knowledgepro.admin.Department.updatefailure"));
				saveErrors(request, errors);
			}
			log.debug("Leaving updateDepartmentEntry Action");
			empEventVacationForm.setMode("add");
			empEventVacationForm.reset();
			setRequiredDatatoForm(empEventVacationForm);
			return mapping.findForward(CMSConstants.EMP_EVENT_VACATION);
		}
	/**
	 * @param empEventVacationForm
	 * @param errors
	 */
	private void validateTime(EmpEventVacationForm empEventVacationForm, ActionErrors errors) {
		
		if(empEventVacationForm.getFromDate()!=null && !StringUtils.isEmpty(empEventVacationForm.getFromDate())&& !CommonUtil.isValidDate(empEventVacationForm.getFromDate())){
			if (errors.get(CMSConstants.ATTENDANCE_ATTENDANCE_FROMDATREINVALID) != null&& !errors.get(CMSConstants.ATTENDANCE_ATTENDANCE_FROMDATREINVALID).hasNext()) {
				errors.add(CMSConstants.ATTENDANCE_ATTENDANCE_FROMDATREINVALID,new ActionError(CMSConstants.ATTENDANCE_ATTENDANCE_FROMDATREINVALID));
			}
		}
		if(empEventVacationForm.getToDate()!=null && !StringUtils.isEmpty(empEventVacationForm.getToDate())&& !CommonUtil.isValidDate(empEventVacationForm.getToDate())){
			if (errors.get(CMSConstants.ATTENDANCE_ATTENDANCE_TODATEINVALID) != null&& !errors.get(CMSConstants.ATTENDANCE_ATTENDANCE_TODATEINVALID).hasNext()) {
				errors.add(CMSConstants.ATTENDANCE_ATTENDANCE_TODATEINVALID,new ActionError(CMSConstants.ATTENDANCE_ATTENDANCE_TODATEINVALID));
			}
		}
		if(empEventVacationForm.getFromDate()!=null && !StringUtils.isEmpty(empEventVacationForm.getFromDate())&& CommonUtil.isValidDate(empEventVacationForm.getFromDate()))
			if(empEventVacationForm.getToDate()!=null && !StringUtils.isEmpty(empEventVacationForm.getToDate())&& CommonUtil.isValidDate(empEventVacationForm.getToDate()))
		if(CommonUtil.checkForEmpty(empEventVacationForm.getFromDate()) && CommonUtil.checkForEmpty(empEventVacationForm.getToDate())){
			Date startDate = CommonUtil.ConvertStringToDate(empEventVacationForm.getFromDate());
			Date endDate = CommonUtil.ConvertStringToDate(empEventVacationForm.getToDate());

			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(startDate);
			Calendar cal2 = Calendar.getInstance();
			cal2.setTime(endDate);
			long daysBetween = CommonUtil.getDaysBetweenDates(cal1, cal2);
			if(daysBetween <= 0) {
				errors.add("error", new ActionError(CMSConstants.TODATE_CANNOTLESS_FROMDATE));
			}
		}
	}
	
	public ActionForward editEmpEventVacation(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception{
		EmpEventVacationForm empEventVacationForm=(EmpEventVacationForm)form;
		try {
			setUserId(request,empEventVacationForm);
			EmpEventVacationHandler.getInstance().editEmpEventVacation(empEventVacationForm);
			empEventVacationForm.setMode("edit");
			
			request.setAttribute("eventEdit","edit");
			if(empEventVacationForm.getStreamId().equals("-"))
			{
			request.setAttribute("stream","-");
			}
		} catch (Exception exception) {
			System.out.println(exception);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.EMP_EVENT_VACATION);
	}
	
	public ActionForward resetEventType(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		EmpEventVacationForm empEventVacationForm=(EmpEventVacationForm)form;
		ActionMessages messages = new ActionMessages();
		setUserId(request,empEventVacationForm);
		Boolean flag=false;
			try {
					flag=EmpEventVacationHandler.getInstance().submitEvent(empEventVacationForm);
					if(flag){
						messages.add(CMSConstants.MESSAGES, new ActionMessage("knowledgepro.employee.event.vacation.restore"));
						saveMessages(request, messages);
						empEventVacationForm.setEmpEventVacation(null);
					}
				} catch (Exception e) {
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		setRequiredDatatoForm(empEventVacationForm);
		
		return mapping.findForward(CMSConstants.EMP_EVENT_VACATION);
	}
	
	public ActionForward deleteEvent(ActionMapping mapping, ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception{
		EmpEventVacationForm empEventVacationForm=(EmpEventVacationForm)form;
		ActionMessages messages = new ActionMessages();
		setUserId(request,empEventVacationForm);
		Boolean flag=false;
			try {
					empEventVacationForm.setMode("delete");
					flag=EmpEventVacationHandler.getInstance().submitEvent(empEventVacationForm);
					if(flag){
						messages.add(CMSConstants.MESSAGES, new ActionMessage("knowledgepro.employee.event.vacation.delete"));
						saveMessages(request, messages);
					}
				} catch (Exception e) {
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		empEventVacationForm.reset();
		setRequiredDatatoForm(empEventVacationForm);
		
		return mapping.findForward(CMSConstants.EMP_EVENT_VACATION);
	}
		
}
	
	

