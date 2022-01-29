package com.kp.cms.actions.attendance;

import java.util.Calendar;

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
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.attendance.CopyClassTeacherForm;
import com.kp.cms.handlers.attendance.AssignClassToTeacherHandler;
import com.kp.cms.handlers.attendance.copyClassTeacherHandler;
import com.kp.cms.utilities.CurrentAcademicYear;


public class CopyClassTeacherAction extends BaseDispatchAction{
	private static Log log = LogFactory.getLog(CopyClassTeacherAction.class);
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initCopyClassTeacher(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.debug("Entering initCopyClassTeacher");
		CopyClassTeacherForm copyClassTeacherForm = (CopyClassTeacherForm) form;
		try {
			copyClassTeacherForm.reset(mapping, request);
			//setRequestedDataToForm(copyClassTeacherForm);
		} catch (Exception e) {
			log.error("error submit course page...", e);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
			} 
		return mapping.findForward(CMSConstants.COPY_CLASS_TEACHER);
	}
	public void setRequestedDataToForm(
			CopyClassTeacherForm copyClassTeacherForm) throws Exception {
		Calendar calendar = Calendar.getInstance();
		int currentYear = calendar.get(Calendar.YEAR);
		if(copyClassTeacherForm.getFromAcademicYear()!=null && !copyClassTeacherForm.getFromAcademicYear().isEmpty()){
			currentYear=Integer.parseInt(copyClassTeacherForm.getFromAcademicYear());
		}
		else{
			int year=CurrentAcademicYear.getInstance().getAcademicyear();
			 if(year!=0){
				currentYear=year;
			}
		}
		copyClassTeacherForm.setFromAcademicYear(Integer.toString(currentYear));
		//assignTeacherForm.setYear(String.valueOf(currentYear));
	}
	
	public ActionForward addTeachers(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.debug("Enter: addTeachers Action");
		CopyClassTeacherForm copyClassTeacherForm = (CopyClassTeacherForm) form;
		ActionMessages messages=new ActionMessages();
		ActionErrors errors=copyClassTeacherForm.validate(mapping, request);
		copyClassTeacherForm.setId(0);
		boolean isAdded;
		try{
			
			
			if(errors.isEmpty()){
				if(Integer.parseInt(copyClassTeacherForm.getFromAcademicYear()) > Integer.parseInt(copyClassTeacherForm.getToAcademicYear())){
					errors.add("error", new ActionError("knowledgepro.attendance.from.year.greater"));
					saveErrors(request, errors);
					//setRequestedDataToForm(copyClassTeacherForm);
					copyClassTeacherForm.reset(mapping, request);
					return mapping.findForward(CMSConstants.COPY_CLASS_TEACHER);
				}
				if(Integer.parseInt(copyClassTeacherForm.getFromAcademicYear()) == Integer.parseInt(copyClassTeacherForm.getToAcademicYear())){
					errors.add("error", new ActionError("knowledgepro.attendance.from.year.equal"));
					saveErrors(request, errors);
					//setRequestedDataToForm(copyClassTeacherForm);
					copyClassTeacherForm.reset(mapping, request);
					return mapping.findForward(CMSConstants.COPY_CLASS_TEACHER);
				}
				
			setUserId(request, copyClassTeacherForm); // setting user id to update last
			isAdded=copyClassTeacherHandler.getInstance().copyTeachers(copyClassTeacherForm);
			
			if (isAdded) {
				ActionMessage message = new ActionMessage(
						"knowledgepro.attn.copy.teacher.success");
				messages.add("messages", message);
				saveMessages(request, messages);
				copyClassTeacherForm.reset(mapping, request);
				//setRequestedDataToForm(copyClassTeacherForm);
			} else {
				errors.add("error", new ActionError("knowledgepro.attn.copy.teacher.failure"));
				saveErrors(request, errors);
				copyClassTeacherForm.reset(mapping, request);
				//setRequestedDataToForm(copyClassTeacherForm);
				}
			}else {
				saveErrors(request, errors);
				}
		}
		catch (Exception e) {
			log.error("error in final submit of admitted through page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				saveMessages(request, messages);
				return mapping.findForward(CMSConstants.COPY_CLASS_TEACHER);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				copyClassTeacherForm.setErrorMessage(msg);
				copyClassTeacherForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		return mapping.findForward(CMSConstants.COPY_CLASS_TEACHER);
	}
}
