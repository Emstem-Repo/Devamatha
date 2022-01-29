package com.kp.cms.actions.attendance;

import java.util.Calendar;
import java.util.Map;

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
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.forms.attendance.AssignClassToTeacherForm;
import com.kp.cms.handlers.attendance.AssignClassToTeacherHandler;
import com.kp.cms.handlers.employee.PayScaleDetailsHandler;
import com.kp.cms.handlers.usermanagement.UserInfoHandler;
import com.kp.cms.utilities.CurrentAcademicYear;

public class AssignClassToTeacherAction extends BaseDispatchAction{
	private static Log log = LogFactory.getLog(AssignClassToTeacherAction.class);
	
	/**This method is used to get the Teachers Details,Years and Classes
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initAssignClassToTeacher(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.debug("Entering initAssignClassToTeacher");
		AssignClassToTeacherForm assignTeacherForm = (AssignClassToTeacherForm) form;
		try {
			assignTeacherForm.reset(mapping, request);
			setRequestedDataToForm(assignTeacherForm);
			//assignTeacherForm.setListTeacherClassEntry(AssignClassToTeacherHandler.getInstance().getDetails(assignTeacherForm));
		} catch (Exception e) {
			log.error("error submit course page...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				assignTeacherForm.setErrorMessage(msg);
				assignTeacherForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				String msg = super.handleApplicationException(e);
				assignTeacherForm.setErrorMessage(msg);
				assignTeacherForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}
		return mapping.findForward(CMSConstants.INIT_ASSIGN_CLASS_TEACHER);
	}
	/**
	 * @param assignTeacherForm
	 * @throws Exception
	 */
	public void setRequestedDataToForm(
			AssignClassToTeacherForm assignTeacherForm) throws Exception {
		Calendar calendar = Calendar.getInstance();
		int currentYear = calendar.get(Calendar.YEAR);
		if(assignTeacherForm.getAcademicYear()!=null && !assignTeacherForm.getAcademicYear().isEmpty()){
			currentYear=Integer.parseInt(assignTeacherForm.getAcademicYear());
		}
		else{
			int year=CurrentAcademicYear.getInstance().getAcademicyear();
			 if(year!=0){
				currentYear=year;
			}
		}
		//assignTeacherForm.setAcademicYear(Integer.toString(currentYear));
		setClassListToForm(assignTeacherForm, currentYear);
		Map<Integer, String> teachersMap = UserInfoHandler.getInstance().getTeachingStaff();
		assignTeacherForm.setTeachersMap(teachersMap);
		assignTeacherForm.setAcademicYear(Integer.toString(currentYear));
		assignTeacherForm.setListTeacherClassEntry(AssignClassToTeacherHandler.getInstance().getDetails(assignTeacherForm));	
		
		//assignTeacherForm.setYear(String.valueOf(currentYear));
	}
	/**
	 * @param assignTeacherForm
	 * @param year
	 * @throws Exception
	 */
	public void setClassListToForm(AssignClassToTeacherForm assignTeacherForm,
			int year) throws Exception {
		log.info("Entering into setClassListToForm");
		try {
			Map<Integer, String> classMap = null;
			classMap = AssignClassToTeacherHandler.getInstance().getClassesByYear(year);
			assignTeacherForm.setClassMap(classMap);
			
		} catch (Exception e) {
			log.error("Error occured in setClassListToForm");
		}
		log.info("Leaving into setClassListToForm");
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward setClassEntry(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		log.debug("Entering setClassEntry");
		AssignClassToTeacherForm assignTeacherForm = (AssignClassToTeacherForm) form;
		try {
			
			int currentYear = 0;
			assignTeacherForm.reset(mapping, request);
			String formName = mapping.getName();
			request.getSession().setAttribute(CMSConstants.FORMNAME, formName);
			if(assignTeacherForm.getAcademicYear()!=null && !assignTeacherForm.getAcademicYear().isEmpty()){
				currentYear=Integer.parseInt(assignTeacherForm.getAcademicYear());
			}
			else{
				int year=CurrentAcademicYear.getInstance().getAcademicyear();
				 if(year!=0){
					currentYear=year;
				}
			}
				setClassListToForm(assignTeacherForm, currentYear);

				// 2. Set the teacher's list
				Map<Integer, String> teachersMap = UserInfoHandler.getInstance().getTeachingStaff();
				assignTeacherForm.setTeachersMap(teachersMap);
				// 3. for get details to display ie. teacher, class, subject by
				
				assignTeacherForm.setListTeacherClassEntry(AssignClassToTeacherHandler.getInstance().getDetailsByYear(assignTeacherForm,currentYear));
				assignTeacherForm.setAcademicYear(String.valueOf(currentYear));
				//assignTeacherForm.setYear(String.valueOf(currentYear));
				

		} catch (Exception e) {
			log.error("error submit course page...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				assignTeacherForm.setErrorMessage(msg);
				assignTeacherForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				String msg = super.handleApplicationException(e);
				assignTeacherForm.setErrorMessage(msg);
				assignTeacherForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}
		log.debug("Action class. setClassEntry ");
		return mapping.findForward(CMSConstants.INIT_ASSIGN_CLASS_TEACHER);
	}
	/**This method is used to the add Details in the List
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public ActionForward addTeachers(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.debug("Enter: addTeachers Action");
		AssignClassToTeacherForm assignClassTeacherForm=(AssignClassToTeacherForm)form;
		ActionMessages messages=new ActionMessages();
		ActionErrors errors=assignClassTeacherForm.validate(mapping, request);
		assignClassTeacherForm.setId(0);
		boolean isAdded;
		try{
			if(assignClassTeacherForm.getTeachers() == null || assignClassTeacherForm.getTeachers().isEmpty()){
				errors.add("error", new ActionError("knowledgepro.attendance.teacher.required"));
				saveErrors(request, errors);
				setRequestedDataToForm(assignClassTeacherForm);
				assignClassTeacherForm.reset(mapping, request);
				return mapping.findForward(CMSConstants.INIT_ASSIGN_CLASS_TEACHER);
			}
			if(assignClassTeacherForm.getAcademicYear() ==null || assignClassTeacherForm.getAcademicYear().isEmpty()){
				errors.add("error", new ActionError("knowledgepro.attendance.year.required"));
				saveErrors(request, errors);
				setRequestedDataToForm(assignClassTeacherForm);
				assignClassTeacherForm.reset(mapping, request);
				return mapping.findForward(CMSConstants.INIT_ASSIGN_CLASS_TEACHER);
			}
			if(assignClassTeacherForm.getClassesSelected() == null || assignClassTeacherForm.getClassesSelected().isEmpty()){
				errors.add("error", new ActionError("knowledgepro.attendance.class.required"));
				saveErrors(request, errors);
				setRequestedDataToForm(assignClassTeacherForm);
				assignClassTeacherForm.reset(mapping, request);
				return mapping.findForward(CMSConstants.INIT_ASSIGN_CLASS_TEACHER);
			}
			if(assignClassTeacherForm.getTeacherType() == null || assignClassTeacherForm.getTeacherType().isEmpty()){
				errors.add("error", new ActionError("knowledgepro.attendance.teacher.type.required"));
				saveErrors(request, errors);
				setRequestedDataToForm(assignClassTeacherForm);
				assignClassTeacherForm.reset(mapping, request);
				return mapping.findForward(CMSConstants.INIT_ASSIGN_CLASS_TEACHER);
			}
			setUserId(request, assignClassTeacherForm); // setting user id to update last
			AssignClassToTeacherHandler.getInstance().checkDuplicate(assignClassTeacherForm);
			isAdded=AssignClassToTeacherHandler.getInstance().addTeachers(assignClassTeacherForm);
			assignClassTeacherForm.setListTeacherClassEntry(AssignClassToTeacherHandler.getInstance().getDetails(assignClassTeacherForm));
			if (isAdded) {
				ActionMessage message = new ActionMessage(
						"knowledgepro.attn.classteacher.addsuccess");
				messages.add("messages", message);
				saveMessages(request, messages);
				assignClassTeacherForm.reset(mapping, request);
				setRequestedDataToForm(assignClassTeacherForm);
			} else {
				errors.add("error", new ActionError("knowledgepro.attn.classteacher.addfailure"));
				saveErrors(request, errors);
				assignClassTeacherForm.reset(mapping, request);
				setRequestedDataToForm(assignClassTeacherForm);
			}
		}catch (DuplicateException e1) {
			 if(e1.getMessage().equals("duplicateEntry")){
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ENTRY_DUPLICATE));
			}
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.INIT_ASSIGN_CLASS_TEACHER);
		} 
		catch (Exception e) {
			log.error("error in final submit of admitted through page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				saveMessages(request, messages);
				return mapping.findForward(CMSConstants.INIT_ASSIGN_CLASS_TEACHER);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				assignClassTeacherForm.setErrorMessage(msg);
				assignClassTeacherForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		return mapping.findForward(CMSConstants.INIT_ASSIGN_CLASS_TEACHER);
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.actions.BaseDispatchAction#setUserId(javax.servlet.http.HttpServletRequest, com.kp.cms.forms.BaseActionForm)
	 */
	public void setUserId(HttpServletRequest request, BaseActionForm form) {
		HttpSession session = request.getSession(false);
		if (session.getAttribute("uid") != null) {
			form.setUserId(session.getAttribute("uid").toString());
		}
	}
	
	
	
	public ActionForward editClassEntry(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		log.debug("Entering editTeachers Action");
		AssignClassToTeacherForm aTeacherForm =(AssignClassToTeacherForm)form;
		try{
			AssignClassToTeacherHandler.getInstance().editClassEntry(aTeacherForm);
			request.setAttribute("classentry", "edit");
		}
		catch(Exception e){
			log.error("error in editing classEntry...", e);
			String msg = super.handleApplicationException(e);
			aTeacherForm.setErrorMessage(msg);
			aTeacherForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		
		
		return mapping.findForward(CMSConstants.INIT_ASSIGN_CLASS_TEACHER);
	}
		
	
	public ActionForward updateClassEntry(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.debug("Entering updateTeachers Action");
		AssignClassToTeacherForm assignClassTeacherForm =(AssignClassToTeacherForm)form;
		ActionMessages messages=new ActionMessages();
		ActionErrors errors=assignClassTeacherForm.validate(mapping, request);
		setUserId(request, assignClassTeacherForm);
		boolean isUpdated = false;
		if(errors.isEmpty()){
			try {
				// This condition works when reset button will click in update mode
				if (isCancelled(request)) {
					assignClassTeacherForm.reset(mapping, request);
					String formName = mapping.getName();
					request.getSession().setAttribute(CMSConstants.FORMNAME,formName);
					AssignClassToTeacherHandler.getInstance().editClassEntry(assignClassTeacherForm);
					request.setAttribute("classentry", "edit");
					return mapping.findForward(CMSConstants.INIT_ASSIGN_CLASS_TEACHER);
				}
				AssignClassToTeacherHandler.getInstance().checkDuplicate(assignClassTeacherForm);
				isUpdated = AssignClassToTeacherHandler.getInstance().updateClassEntry(assignClassTeacherForm);
				if (isUpdated) {
					ActionMessage message = new ActionMessage(
							"knowledgepro.employee.assignClassTeacher.update.success");
					messages.add("messages", message);
					saveMessages(request, messages);
					assignClassTeacherForm.reset(mapping, request);
				} else {
					errors.add("error", new ActionError( "knowledgepro.employee.assignClassTeacher.update.failed"));
					//saveErrors(request, errors);
					addErrors(request, errors);
				}
			}catch (DuplicateException e1) {
				 if(e1.getMessage().equals("duplicateEntry")){
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ENTRY_DUPLICATE));
				}
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.INIT_ASSIGN_CLASS_TEACHER);
			}catch (Exception e) {
				log.error("Error occured in edit assignClassTeacher", e);
				String msg = super.handleApplicationException(e);
				assignClassTeacherForm.setErrorMessage(msg);
				assignClassTeacherForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}}else{
				saveErrors(request, errors);
				setRequestedDataToForm(assignClassTeacherForm);
				request.setAttribute("classentry", "edit");
				return mapping.findForward(CMSConstants.INIT_ASSIGN_CLASS_TEACHER);
			}
			setRequestedDataToForm(assignClassTeacherForm);
			log.debug("Exit: action class updateassignClassTeacher");
			
		return mapping.findForward(CMSConstants.INIT_ASSIGN_CLASS_TEACHER);
	
	}
		
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteTeachers(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.debug("Entering deleteTeachers");
		AssignClassToTeacherForm aTeacherForm =(AssignClassToTeacherForm)form;
		ActionMessages messages=new ActionMessages();
		boolean isDeleted;
		try{
			isDeleted=AssignClassToTeacherHandler.getInstance().deleteTeachers(aTeacherForm.getId());
			if(isDeleted){
				ActionMessage message = new ActionMessage("knowledgepro.attn.classteacher.deletsuccess");
				messages.add("messages", message);
				saveMessages(request, messages);
			}else{
				ActionMessage message = new ActionMessage("knowledgepro.attn.classteacher.deletefailure");
				messages.add("messages", message);
				saveMessages(request, messages);
			}
			setRequestedDataToForm(aTeacherForm);
		}catch (Exception exception) {
			log.error("error submit course page...", exception);
			if (exception instanceof ApplicationException) {
				String msg = super.handleApplicationException(exception);
				aTeacherForm.setErrorMessage(msg);
				aTeacherForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				String msg = super.handleApplicationException(exception);
				aTeacherForm.setErrorMessage(msg);
				aTeacherForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}
		log.debug("Action class. deleteTeachers ");
		return mapping.findForward(CMSConstants.INIT_ASSIGN_CLASS_TEACHER);
	}
}

