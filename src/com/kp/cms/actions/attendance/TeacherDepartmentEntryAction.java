package com.kp.cms.actions.attendance;

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
import com.kp.cms.bo.admin.TeacherClassSubject;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.forms.attendance.TeacherClassEntryForm;
import com.kp.cms.forms.attendance.TeacherDepartmentEntryForm;
import com.kp.cms.handlers.attendance.TeacherClassEntryHandler;
import com.kp.cms.handlers.attendance.TeacherDepartmentEntryHandler;
import com.kp.cms.handlers.usermanagement.UserInfoHandler;
import com.kp.cms.to.attendance.TeacherDepartmentTO;

public class TeacherDepartmentEntryAction extends BaseDispatchAction{
	private static Log log = LogFactory.getLog(TeacherDepartmentEntryAction.class);
	
	public ActionForward initTeacherDepartment(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		log.debug("Entering initTeacherDepartment");
		TeacherDepartmentEntryForm teacherDepartmentForm = (TeacherDepartmentEntryForm) form;
		try {
			List<TeacherDepartmentTO> teacherDepTo=TeacherDepartmentEntryHandler.getInstance().getTeacherDepartments();
			teacherDepartmentForm.setTeacherDepartmentTO(teacherDepTo);
			teacherDepartmentForm.reset(mapping, request);
			setRequestedDataToForm(teacherDepartmentForm);
		} catch (Exception e) {
			log.error("error while entering initTeacherDepartment...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				teacherDepartmentForm.setErrorMessage(msg);
				teacherDepartmentForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				String msg = super.handleApplicationException(e);
				teacherDepartmentForm.setErrorMessage(msg);
				teacherDepartmentForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}
		log.debug("Action class. Leaving initTeacherDepartment ");
		return mapping.findForward(CMSConstants.INIT_TEACHER_DEPARTMENT);
	}
	
	public void setRequestedDataToForm(TeacherDepartmentEntryForm teacherDepartmentForm) throws Exception {
		// 1. Set the teacher's list
		Map<Integer, String> teachersMap = UserInfoHandler.getInstance().getTeachingStaff();
		teacherDepartmentForm.setTeachersMap(teachersMap);
		// 2. set the department's list
		Map<Integer, String> departmentMap=UserInfoHandler.getInstance().getDepartment();
		teacherDepartmentForm.setDepartmentMap(departmentMap);
	}
	
	@SuppressWarnings({ "deprecation", "deprecation", "deprecation" })
	public ActionForward addTeacherDepartment(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		log.debug("Enter: addTeacherClass Action");
		TeacherDepartmentEntryForm teacherDepartmentForm = (TeacherDepartmentEntryForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = teacherDepartmentForm.validate(mapping, request);
		
		boolean isAdded=false;
		if ( errors.isEmpty()){
		try {
			 
			setUserId(request, teacherDepartmentForm); // setting user id to update last
			//checking the duplicate record
			
		    boolean isDuplicate=TeacherDepartmentEntryHandler.getInstance().checkDuplicate(teacherDepartmentForm);
		    
			// changed details
		    if(isDuplicate)
		    	errors.add("error", new ActionError("knowledgepro.attn.teacherdepartment.add.failed"));
		    else
		    	isAdded = TeacherDepartmentEntryHandler.getInstance().addTeacherDepartment(teacherDepartmentForm);
		    	
		} 
		catch (Exception e) {
			log.error("error in final submit of admitted through page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				saveMessages(request, messages);
				return mapping.findForward(CMSConstants.INIT_TEACHER_DEPARTMENT);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				teacherDepartmentForm.setErrorMessage(msg);
				teacherDepartmentForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		if (isAdded) {
			ActionMessage message = new ActionMessage(
					"knowledgepro.attn.teacherdepartment");
			messages.add("messages", message);
			saveMessages(request, messages);
			List<TeacherDepartmentTO> teacherDepTo=TeacherDepartmentEntryHandler.getInstance().getTeacherDepartments();
			teacherDepartmentForm.setTeacherDepartmentTO(teacherDepTo);
			teacherDepartmentForm.reset(mapping, request);
			setRequestedDataToForm(teacherDepartmentForm);
		} else {
			errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.DUPLICATE_RECORDS));
			saveErrors(request, errors);
			setRequestedDataToForm(teacherDepartmentForm);
			List<TeacherDepartmentTO> teacherDepTo=TeacherDepartmentEntryHandler.getInstance().getTeacherDepartments();
			teacherDepartmentForm.setTeacherDepartmentTO(teacherDepTo);
		}}else{
				saveErrors(request, errors);
				setRequestedDataToForm(teacherDepartmentForm);
				List<TeacherDepartmentTO> teacherDepTo=TeacherDepartmentEntryHandler.getInstance().getTeacherDepartments();
				teacherDepartmentForm.setTeacherDepartmentTO(teacherDepTo);
				return mapping.findForward(CMSConstants.INIT_TEACHER_DEPARTMENT);
		}
		log.debug("Exit: action class addTeacherDepartmentAction");
		return mapping.findForward(CMSConstants.INIT_TEACHER_DEPARTMENT);

	}
	
	public ActionForward editTeacherDepartmentEntry(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		log.debug("Entering setDepartmentEntry");
		TeacherDepartmentEntryForm teacherDepartmentForm = (TeacherDepartmentEntryForm) form;

		try {
			teacherDepartmentForm.reset(mapping, request);
			String formName = mapping.getName();
			request.getSession().setAttribute(CMSConstants.FORMNAME, formName);
			setRequestedDataToForm(teacherDepartmentForm);
			 TeacherDepartmentEntryHandler.getInstance().editTeacherDepartmentEntry(teacherDepartmentForm);
			request.setAttribute("teacherDepartmentOperation", "edit");
		} catch (Exception e) {
			log.error("error submit course page...", e);
				String msg = super.handleApplicationException(e);
				teacherDepartmentForm.setErrorMessage(msg);
				teacherDepartmentForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.debug("Action class. setClassEntry ");
		return mapping.findForward(CMSConstants.INIT_TEACHER_DEPARTMENT);
	}
	
	public ActionForward updateTeacherDepartment(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.debug("Enter: addTeacherClass Action");
		TeacherDepartmentEntryForm teacherDepartmentForm = (TeacherDepartmentEntryForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = teacherDepartmentForm.validate(mapping, request);
		boolean isUpdated=false;
		
		try {
			//This condition works when reset button will click in update mode
			if(isCancelled(request)){
				teacherDepartmentForm.reset(mapping, request);
				String formName = mapping.getName();
				request.getSession().setAttribute(CMSConstants.FORMNAME, formName);
				TeacherDepartmentEntryHandler.getInstance().editTeacherDepartmentEntry(teacherDepartmentForm);
				request.setAttribute("teacherDepartmentOperation", "edit");
				return mapping.findForward(CMSConstants.INIT_TEACHER_DEPARTMENT);
			}
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				setRequestedDataToForm(teacherDepartmentForm);
				return mapping.findForward(CMSConstants.INIT_TEACHER_DEPARTMENT);
			}
			setUserId(request, teacherDepartmentForm); // setting user id to update last
			boolean isDuplicate=TeacherDepartmentEntryHandler.getInstance().checkDuplicate(teacherDepartmentForm);
			
			if(isDuplicate)
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.DUPLICATE_RECORDS));
			else
			isUpdated = TeacherDepartmentEntryHandler.getInstance().updateTeacherDepartment(teacherDepartmentForm);
			
		}  catch (Exception e) {
			log.error("error in final submit of admitted through page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				saveMessages(request, messages);
				return mapping.findForward(CMSConstants.INIT_TEACHER_DEPARTMENT);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				teacherDepartmentForm.setErrorMessage(msg);
				teacherDepartmentForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		if (isUpdated) {
			ActionMessage message = new ActionMessage(
					"knowledgepro.attn.teacherdepartment.update");
			messages.add("messages", message);
			saveMessages(request, messages);
			teacherDepartmentForm.reset(mapping, request);
			List<TeacherDepartmentTO> teacherDepTo=TeacherDepartmentEntryHandler.getInstance().getTeacherDepartments();
			teacherDepartmentForm.setTeacherDepartmentTO(teacherDepTo);
			setRequestedDataToForm(teacherDepartmentForm);
		} else {
			errors.add("error", new ActionError(
					"knowledgepro.attn.teacherdepartment.update.failed"));
			saveErrors(request, errors);
			setRequestedDataToForm(teacherDepartmentForm);
			List<TeacherDepartmentTO> teacherDepTo=TeacherDepartmentEntryHandler.getInstance().getTeacherDepartments();
			teacherDepartmentForm.setTeacherDepartmentTO(teacherDepTo);
		}
		log.debug("Exit: action class updateTeacherDepartmentAction");
		return mapping.findForward(CMSConstants.INIT_TEACHER_DEPARTMENT);
		
	}
	
	public ActionForward deleteTeacherDepartmentEntry(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.debug("Entering setClassEntry");
		TeacherDepartmentEntryForm teacherDepartmentForm = (TeacherDepartmentEntryForm) form;
		ActionMessages messages = new ActionMessages();
		
		try {
			boolean result=TeacherDepartmentEntryHandler.getInstance().deleteTeacherDepartment(teacherDepartmentForm);
			if(result){
				ActionMessage message = new ActionMessage("knowledgepro.attn.teacherdepartment.delete");
				messages.add("messages", message);
				saveMessages(request, messages);
			}else{
				ActionMessage message = new ActionMessage("knowledgepro.attn.teacherdepartment.delete.failed");
				messages.add("messages", message);
				saveMessages(request, messages);
			}
			
			setRequestedDataToForm(teacherDepartmentForm);
			List<TeacherDepartmentTO> teacherDepTo=TeacherDepartmentEntryHandler.getInstance().getTeacherDepartments();
			teacherDepartmentForm.setTeacherDepartmentTO(teacherDepTo);
		} catch (Exception e) {
			log.error("error submit course page...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				teacherDepartmentForm.setErrorMessage(msg);
				teacherDepartmentForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				String msg = super.handleApplicationException(e);
				teacherDepartmentForm.setErrorMessage(msg);
				teacherDepartmentForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}
		log.debug("Action class. Delete TeacherDepartmentEntry ");
		return mapping.findForward(CMSConstants.INIT_TEACHER_DEPARTMENT);
	}

}
