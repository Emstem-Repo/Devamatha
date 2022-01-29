package com.kp.cms.actions.attendance;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.attendance.ViewMyAttendanceLeaveForm;
import com.kp.cms.handlers.attendance.TeacherDepartmentEntryHandler;
import com.kp.cms.handlers.attendance.ViewMyAttendanceLeaveHandler;
import com.kp.cms.to.attendance.ViewMyAttendanceLeaveTo;
import com.kp.cms.utilities.CommonUtil;

public class ViewMyAttendanceLeaveAction extends BaseDispatchAction{
/**
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward initViewMyAttnLeave(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
	ViewMyAttendanceLeaveForm attendanceLeaveForm = (ViewMyAttendanceLeaveForm)form;
	attendanceLeaveForm.setAttendanceLeaveDate(null);
	attendanceLeaveForm.setAttendanceLeaveMap(null);
	attendanceLeaveForm.setPrintPage("false");
	attendanceLeaveForm.setTeacherName(null);
	return mapping.findForward(CMSConstants.INIT_VIEW_MYATTN_LEAVE);
}
 /**
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward searchViewMyAttnLeave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	ViewMyAttendanceLeaveForm attendanceLeaveForm = (ViewMyAttendanceLeaveForm)form;
	ActionErrors errors=new ActionErrors();
	ActionMessages messages=new ActionMessages();
	setUserId(request, attendanceLeaveForm);
	attendanceLeaveForm.setPrintPage("false");
	try{
		Date attendanceLeaveDate = CommonUtil.ConvertStringToSQLDate(attendanceLeaveForm.getAttendanceLeaveDate());
		attendanceLeaveForm.setAttendanceLeaveMap(null);
		if(attendanceLeaveForm.getAttendanceLeaveDate()!=null && !attendanceLeaveForm.getAttendanceLeaveDate().isEmpty()){
			Map<String, ViewMyAttendanceLeaveTo> leaveListMap= ViewMyAttendanceLeaveHandler.getInstance().getMyAttendanceLeave(attendanceLeaveForm,attendanceLeaveDate);
			if(leaveListMap!=null && !leaveListMap.isEmpty()){
				attendanceLeaveForm.setAttendanceLeaveMap(leaveListMap);
			}else{
				errors.add("error", new ActionError("knowledgepro.attendance.subjectgroup.no.records"));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.INIT_VIEW_MYATTN_LEAVE);
			}
		}else{
			errors.add("error", new ActionError("knowledgepro.attendance.viewmyattn.leave.date.required"));
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.INIT_VIEW_MYATTN_LEAVE);
		}
	}catch (Exception e) {
		if (e instanceof BusinessException) {
			String msgKey = super.handleBusinessException(e);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add("messages", message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} else if (e instanceof ApplicationException) {
			String msg = super.handleApplicationException(e);
			attendanceLeaveForm.setErrorMessage(msg);
			attendanceLeaveForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} else {
			throw e;
		}
	}
	return mapping.findForward(CMSConstants.INIT_VIEW_MYATTN_LEAVE);
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getDetailsForPrint(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			ViewMyAttendanceLeaveForm attendanceLeaveForm = (ViewMyAttendanceLeaveForm)form;
			setUserId(request, attendanceLeaveForm);
			ActionErrors errors=new ActionErrors();
			ActionMessages messages=new ActionMessages();
			try{
				attendanceLeaveForm.setLeaveTo(null);
				validateSelectedCheckBox(attendanceLeaveForm,errors);
				if(attendanceLeaveForm.getAttendanceLeaveDate()!=null && !attendanceLeaveForm.getAttendanceLeaveDate().isEmpty()){
					Date attendanceLeaveDate = CommonUtil.ConvertStringToSQLDate(attendanceLeaveForm.getAttendanceLeaveDate());
					Map<String, ViewMyAttendanceLeaveTo> leaveListMap= ViewMyAttendanceLeaveHandler.getInstance().getMyAttendanceLeave(attendanceLeaveForm,attendanceLeaveDate);
					if(leaveListMap!=null && !leaveListMap.isEmpty()){
						attendanceLeaveForm.setAttendanceLeaveMap(null);
						attendanceLeaveForm.setAttendanceLeaveMap(leaveListMap);
					}else{
						errors.add("error", new ActionError("knowledgepro.attendance.subjectgroup.no.records"));
						saveErrors(request, errors);
						return mapping.findForward(CMSConstants.INIT_VIEW_MYATTN_LEAVE);
					}
				}
				if(errors!=null && errors.isEmpty()){
					attendanceLeaveForm.setPrintPage("true");
				}else{
					attendanceLeaveForm.setPrintPage("false");
					saveErrors(request,errors);
				}
			}catch (Exception e) {
				if (e instanceof BusinessException) {
					String msgKey = super.handleBusinessException(e);
					ActionMessage message = new ActionMessage(msgKey);
					messages.add("messages", message);
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				} else if (e instanceof ApplicationException) {
					String msg = super.handleApplicationException(e);
					attendanceLeaveForm.setErrorMessage(msg);
					attendanceLeaveForm.setErrorStack(e.getMessage());
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				} else {
					throw e;
				}
			}
			
			return mapping.findForward(CMSConstants.INIT_VIEW_MYATTN_LEAVE);
		}
	/**
	 * @param attendanceLeaveForm
	 * @param errors
	 * @throws Exception
	 */
	private void validateSelectedCheckBox( ViewMyAttendanceLeaveForm attendanceLeaveForm, ActionErrors errors) throws Exception{
		ViewMyAttendanceLeaveHandler.getInstance().printMyAttnLeave(attendanceLeaveForm,errors);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward printMyAttendanceLeave(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
		return mapping.findForward(CMSConstants.PRINT_MYATTN_LEAVE);
		}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initViewEmployeeAttendanceLeave(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
		ViewMyAttendanceLeaveForm attendanceLeaveForm = (ViewMyAttendanceLeaveForm)form;
		setUserId(request, attendanceLeaveForm);
		try{
			attendanceLeaveForm.setAttendanceLeaveDate(null);
			attendanceLeaveForm.setAttendanceLeaveMap(null);
			attendanceLeaveForm.setPrintPage("false");
			attendanceLeaveForm.setTeacherName(null);
			attendanceLeaveForm.setTeachersMap(null);
			attendanceLeaveForm.setTeachers(null);
			setEmployeeNameToForm(attendanceLeaveForm);
		}catch (Exception e) {
			// TODO: handle exception
		}
		return mapping.findForward(CMSConstants.VIEW_EMPLOYEE_ATTN_LEAVE);
		}
	/**
	 * @param attendanceLeaveForm
	 * @throws Exception
	 */
	private void setEmployeeNameToForm( ViewMyAttendanceLeaveForm attendanceLeaveForm) throws Exception{
		Map<Integer,String> teachersMap =TeacherDepartmentEntryHandler.getInstance().getTeacherDepartmentsName();
		attendanceLeaveForm.setTeachersMap(teachersMap);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward searchEmployeeAttnLeave(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
		ViewMyAttendanceLeaveForm attendanceLeaveForm = (ViewMyAttendanceLeaveForm)form;
		ActionErrors errors=new ActionErrors();
		ActionMessages messages=new ActionMessages();
		setUserId(request, attendanceLeaveForm);
		attendanceLeaveForm.setPrintPage("false");
		try{
			Date attendanceLeaveDate = CommonUtil.ConvertStringToSQLDate(attendanceLeaveForm.getAttendanceLeaveDate());
			String teacherId= attendanceLeaveForm.getTeachers();
			attendanceLeaveForm.setAttendanceLeaveMap(null);
			if(attendanceLeaveForm.getTeachers()!=null && !attendanceLeaveForm.getTeachers().isEmpty() && 
					attendanceLeaveForm.getAttendanceLeaveDate()!=null && !attendanceLeaveForm.getAttendanceLeaveDate().isEmpty()){
				Map<String,ViewMyAttendanceLeaveTo> attnLeaveMap = ViewMyAttendanceLeaveHandler.getInstance().getSearchEmpAttendanceLeave(attendanceLeaveDate,teacherId,attendanceLeaveForm);
				if(!attnLeaveMap.isEmpty()){
					attendanceLeaveForm.setAttendanceLeaveMap(attnLeaveMap);
				}else{
					errors.add("error", new ActionError("knowledgepro.attendance.subjectgroup.no.records"));
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.VIEW_EMPLOYEE_ATTN_LEAVE);
				}
			}else{
				errors.add("error", new ActionError("knowledgepro.attendance.viewmyattn.leave.date.required"));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.VIEW_EMPLOYEE_ATTN_LEAVE);
			}
		}catch (Exception e) {
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				attendanceLeaveForm.setErrorMessage(msg);
				attendanceLeaveForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		return mapping.findForward(CMSConstants.VIEW_EMPLOYEE_ATTN_LEAVE);
		}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getDetailsEmpAttnLeaveForPrint(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ViewMyAttendanceLeaveForm attendanceLeaveForm = (ViewMyAttendanceLeaveForm)form;
		setUserId(request, attendanceLeaveForm);
		ActionErrors errors=new ActionErrors();
		ActionMessages messages=new ActionMessages();
		try{
			attendanceLeaveForm.setLeaveTo(null);
			validateSelectedCheckBox(attendanceLeaveForm,errors);
			if(attendanceLeaveForm.getTeachers()!=null && !attendanceLeaveForm.getTeachers().isEmpty() && 
					attendanceLeaveForm.getAttendanceLeaveDate()!=null && !attendanceLeaveForm.getAttendanceLeaveDate().isEmpty()){
				Date attendanceLeaveDate = CommonUtil.ConvertStringToSQLDate(attendanceLeaveForm.getAttendanceLeaveDate());
				String teacherName= attendanceLeaveForm.getTeachers();
				Map<String,ViewMyAttendanceLeaveTo> attnLeaveMap = ViewMyAttendanceLeaveHandler.getInstance().getSearchEmpAttendanceLeave(attendanceLeaveDate,teacherName,attendanceLeaveForm);
				if(!attnLeaveMap.isEmpty()){
					attendanceLeaveForm.setAttendanceLeaveMap(null);
					attendanceLeaveForm.setAttendanceLeaveMap(attnLeaveMap);
				}else{
					errors.add("error", new ActionError("knowledgepro.attendance.subjectgroup.no.records"));
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.VIEW_EMPLOYEE_ATTN_LEAVE);
				}
			}
			if(errors!=null && errors.isEmpty()){
				attendanceLeaveForm.setPrintPage("true");
			}else{
				attendanceLeaveForm.setPrintPage("false");
				saveErrors(request,errors);
			}
		}catch (Exception e) {
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				attendanceLeaveForm.setErrorMessage(msg);
				attendanceLeaveForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		
		return mapping.findForward(CMSConstants.VIEW_EMPLOYEE_ATTN_LEAVE);
	}
	
}