package com.kp.cms.actions.attendance;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.kp.cms.bo.admin.ClassSchemewise;
import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.admin.TeacherClassSubject;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.forms.attendance.TeacherClassEntryForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.attendance.TeacherClassEntryHandler;
import com.kp.cms.handlers.attendance.TeacherDepartmentEntryHandler;
import com.kp.cms.handlers.usermanagement.UserInfoHandler;
import com.kp.cms.to.attendance.TeacherClassEntryTo;
import com.kp.cms.to.usermanagement.UserInfoTO;
import com.kp.cms.utilities.CurrentAcademicYear;
import com.kp.cms.utilities.PropertyUtil;

public class TeacherClassEntryAction extends BaseDispatchAction {
	private static Log log = LogFactory.getLog(TeacherClassEntryAction.class);

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addTeacherClass(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		log.debug("Enter: addTeacherClass Action");
		TeacherClassEntryForm teacherForm = (TeacherClassEntryForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = teacherForm.validate(mapping, request);
		HttpSession session = request.getSession();
		teacherForm.setId(0);
		boolean isAdded;
		try {
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				setRequestedDataToFormWhenError(teacherForm);
				return mapping.findForward("teacherClassEntry");
			}
			setUserId(request, teacherForm); // setting user id to update last
			//checking the duplicate numeric code
			if(teacherForm.getNumericCode()!=null && !teacherForm.getNumericCode().trim().isEmpty()){
				List<TeacherClassSubject> duplicateList=TeacherClassEntryHandler.getInstance().checkDuplicateNumericCode(teacherForm);
				boolean isDuplicate=TeacherClassEntryHandler.getInstance().checkDuplicateCodeInList(duplicateList,teacherForm);
			}
		    TeacherClassEntryHandler.getInstance().checkDuplicate(teacherForm);
		    //TeacherClassEntryHandler.getInstance().checkReactivate(teacherForm);
			// changed details
			isAdded = TeacherClassEntryHandler.getInstance().add(teacherForm);
		} catch (DuplicateException e1) {
			if (e1.getMessage().equals("duplicateCode")) {
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.TEACHER_CLASSES_NUMERICCODE_DUPLICATE));
			}	
			else if(e1.getMessage().equals("duplicateEntry")){
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.TEACHER_CLASSES_ENTRY_DUPLICATE));
			}
			saveErrors(request, errors);
//			teacherForm.reset(mapping, request);
			setRequestedDataToFormWhenError(teacherForm);
			return mapping.findForward("teacherClassEntry");
		} 
		catch (ReActivateException e2) {
			errors.add("error", new ActionError(CMSConstants.TEACHERCLASSENTRY_REACTIVATE));
			saveErrors(request, errors);
			session.setAttribute("ReacivateId", teacherForm.getId());
			setRequestedDataToFormWhenError(teacherForm);
			return mapping.findForward("teacherClassEntry");
		} 
		catch (Exception e) {
			log.error("error in final submit of admitted through page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				saveMessages(request, messages);
				return mapping.findForward("teacherClassEntry");
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				teacherForm.setErrorMessage(msg);
				teacherForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		if (isAdded) {
			ActionMessage message = new ActionMessage(
					"knowledgepro.attn.teacherclass.addsuccess");
			messages.add("messages", message);
			saveMessages(request, messages);
			teacherForm.reset(mapping, request);
			setRequestedDataToForm(teacherForm);
		} else {
			errors.add("error", new ActionError("knowledgepro.attn.teacherclass.addfailure"));
			saveErrors(request, errors);
			setRequestedDataToFormWhenError(teacherForm);
		}
		log.debug("Exit: action class addTeacherClassAction");
		return mapping.findForward("teacherClassEntry");

	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initTeacherClass(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		log.debug("Entering initTeacherClassEntry");
		TeacherClassEntryForm teacherForm = (TeacherClassEntryForm) form;
		try {
			teacherForm.reset(mapping, request);
			setUserId(request, teacherForm);
			List<UserInfoTO> userinfo=UserInfoHandler.getInstance().getUserDetailsById(Integer.parseInt(teacherForm.getUserId()));
			teacherForm.setRoleId(String.valueOf(userinfo.get(0).getRolesTO().getId()));
//			String formName = mapping.getName();
//			request.getSession().setAttribute(CMSConstants.FORMNAME, formName);
//			teacherForm.setYear(null);
			setRequestedDataToForm(teacherForm);
			request.setAttribute("teacherClassOperation", "add");
		} catch (Exception e) {
			log.error("error submit course page...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				teacherForm.setErrorMessage(msg);
				teacherForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				String msg = super.handleApplicationException(e);
				teacherForm.setErrorMessage(msg);
				teacherForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}
		log.debug("Action class. Leaving initPeriod ");
		return mapping.findForward("teacherClassEntry");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.struts.actions.DispatchAction#unspecified(org.apache.struts
	 * .action.ActionMapping, org.apache.struts.action.ActionForm,
	 * javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	protected ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String message = messages.getMessage("dispatch.parameter", mapping
				.getPath(), mapping.getParameter());
		log.error(message);
		return super.unspecified(mapping, form, request, response);
	}

	/**
	 * 
	 */

	public void setUserId(HttpServletRequest request, BaseActionForm form) {
		HttpSession session = request.getSession(false);
		if (session.getAttribute("uid") != null) {
			form.setUserId(session.getAttribute("uid").toString());
		}
	}

	/**
	 * 
	 * @param teacherForm
	 * @param year
	 * @throws Exception
	 */
	public void setClassListToForm(TeacherClassEntryForm teacherForm, int year)
			throws Exception {
		log.info("Entering into setClassListToForm");
		try {
			Map<Integer, String> classMap = null;
			classMap = CommonAjaxHandler.getInstance().getClassesByYear(year);
			teacherForm.setClassMap(classMap);
		} catch (Exception e) {
			log.error("Error occured in setClassListToForm");
		}
		log.info("Leaving into setClassListToForm");
	}

	/**
	 * setting the requested data to form
	 * @param teacherForm
	 * @throws Exception
	 */
	public void setRequestedDataToForm(TeacherClassEntryForm teacherForm) throws Exception {
		Calendar calendar = Calendar.getInstance();
		int currentYear = calendar.get(Calendar.YEAR);
		int year = CurrentAcademicYear.getInstance().getAcademicyear();
		if (year != 0) {
			currentYear = year;
		}
		teacherForm.setYear(Integer.toString(currentYear));
		// 1. The current year is loaded by default and it's corresponding
		// classes
		setClassListToForm(teacherForm, currentYear);
		// 2. Set the teacher's list
		//Map<Integer, String> teachersMap = UserInfoHandler.getInstance().getTeachingStaff();
		Map<Integer, String> teachersMap = TeacherClassEntryHandler.getInstance().getTeacherDepartmentNames();
		teacherForm.setTeachersMap(teachersMap);
		// 3. for get details to display ie. teacher, class, subject by year
		teacherForm.setListTeacherClassEntry(TeacherClassEntryHandler.getInstance().getDetails(currentYear, teacherForm));
	}

	public ActionForward setClassEntry(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		log.debug("Entering setClassEntry");
		TeacherClassEntryForm objForm = (TeacherClassEntryForm) form;
		try {
			objForm.reset(mapping, request);
			String formName = mapping.getName();
			request.getSession().setAttribute(CMSConstants.FORMNAME, formName);
			if (objForm.getAcademicYear() != null
					&& objForm.getAcademicYear().trim().length() > 0) {
				int currentYear = Integer.parseInt(objForm.getAcademicYear());
				// 1. The current year is loaded by default and it's
				// corresponding classes
				setClassListToForm(objForm, currentYear);

				// 2. Set the teacher's list
				//Map<Integer, String> teachersMap = UserInfoHandler.getInstance().getTeachingStaff();
				Map<Integer, String> teachersMap = TeacherClassEntryHandler.getInstance().getTeacherDepartmentNames();
				objForm.setTeachersMap(teachersMap);
				// 3. for get details to display ie. teacher, class, subject by
				// year
				objForm.setListTeacherClassEntry(TeacherClassEntryHandler
						.getInstance().getDetails(currentYear, objForm));
				objForm.setYear(Integer.toString(currentYear));
			}

		} catch (Exception e) {
			log.error("error submit course page...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				objForm.setErrorMessage(msg);
				objForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				String msg = super.handleApplicationException(e);
				objForm.setErrorMessage(msg);
				objForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}
		log.debug("Action class. setClassEntry ");
		return mapping.findForward("teacherClassEntry");
	}

	public ActionForward deleteTeacherClassEntry(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.debug("Entering setClassEntry");
		TeacherClassEntryForm objForm = (TeacherClassEntryForm) form;
		ActionMessages messages = new ActionMessages();
		try {
			boolean result=TeacherClassEntryHandler.getInstance().deleteTeacherClassEntry(objForm.getId(),false,objForm);
			if(result){
				ActionMessage message = new ActionMessage("knowledgepro.attn.teacherclass.deletsuccess");
				messages.add("messages", message);
				saveMessages(request, messages);
			}else{
				ActionMessage message = new ActionMessage("knowledgepro.attn.teacherclass.deletefailure");
				messages.add("messages", message);
				saveMessages(request, messages);
			}
			
			if (objForm.getYear() != null && objForm.getYear().trim().length() > 0) {
				int currentYear = Integer.parseInt(objForm.getYear());
				// 1. The current year is loaded by default and it's
				// corresponding classes
				setClassListToForm(objForm, currentYear);
				// 2. Set the teacher's list
				//Map<Integer, String> teachersMap = UserInfoHandler
				//		.getInstance().getTeachingStaff();
				Map<Integer, String> teachersMap = TeacherClassEntryHandler.getInstance().getTeacherDepartmentNames();
				objForm.setTeachersMap(teachersMap);
				// 3. for get details to display ie. teacher, class, subject by
				// year
				objForm.setListTeacherClassEntry(TeacherClassEntryHandler
						.getInstance().getDetails(currentYear, objForm));
				objForm.setYear(Integer.toString(currentYear));
			}

		} catch (Exception e) {
			log.error("error submit course page...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				objForm.setErrorMessage(msg);
				objForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				String msg = super.handleApplicationException(e);
				objForm.setErrorMessage(msg);
				objForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}
		log.debug("Action class. setClassEntry ");
		return mapping.findForward("teacherClassEntry");
	}

	public ActionForward editTeacherClassEntry(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		log.debug("Entering setClassEntry");
		TeacherClassEntryForm objForm = (TeacherClassEntryForm) form;

		try {
			objForm.reset(mapping, request);
			String formName = mapping.getName();
			request.getSession().setAttribute(CMSConstants.FORMNAME, formName);
			objForm = TeacherClassEntryHandler.getInstance()
					.editTeacherClassEntry(objForm, objForm.getId());

			request.setAttribute("teacherClassOperation", "edit");
		} catch (Exception e) {
			log.error("error submit course page...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				objForm.setErrorMessage(msg);
				objForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				String msg = super.handleApplicationException(e);
				objForm.setErrorMessage(msg);
				objForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}
		log.debug("Action class. setClassEntry ");
		return mapping.findForward("teacherClassEntry");
	}
	
	
	public ActionForward updateTeacherClass(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.debug("Enter: addTeacherClass Action");
		TeacherClassEntryForm teacherForm = (TeacherClassEntryForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = teacherForm.validate(mapping, request);
		boolean isUpdated;
		try {
			//This condition works when reset button will click in update mode
			if(isCancelled(request)){
				teacherForm.reset(mapping, request);
				String formName = mapping.getName();
				request.getSession().setAttribute(CMSConstants.FORMNAME, formName);
				teacherForm = TeacherClassEntryHandler.getInstance().editTeacherClassEntry(teacherForm, teacherForm.getId());
				request.setAttribute("teacherClassOperation", "edit");
				return mapping.findForward("teacherClassEntry");
			}
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				setRequestedDataToFormWhenError(teacherForm);
				request.setAttribute("teacherClassOperation", "edit");
				return mapping.findForward("teacherClassEntry");
			}
			setUserId(request, teacherForm); // setting user id to update last
			//checking the duplicate numeric code
			if(teacherForm.getNumericCode()!=null && !teacherForm.getNumericCode().trim().isEmpty()){
				List<TeacherClassSubject> duplicateList=TeacherClassEntryHandler.getInstance().checkDuplicateNumericCode(teacherForm);
				boolean isDuplicate=TeacherClassEntryHandler.getInstance().checkDuplicateCodeInList(duplicateList,teacherForm);
			}
			// changed details
			isUpdated = TeacherClassEntryHandler.getInstance().update(teacherForm);
		} catch (DuplicateException e1) {
			if (e1.getMessage().equals("duplicateCode")) {
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.TEACHER_CLASSES_NUMERICCODE_DUPLICATE));
			}else if(e1.getMessage().equals("duplicateEntry")){
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.TEACHER_CLASSES_ENTRY_DUPLICATE));
			}
			saveErrors(request, errors);
//			teacherForm.reset(mapping, request);
			setRequestedDataToFormWhenError(teacherForm);
			request.setAttribute("teacherClassOperation", "edit");
			return mapping.findForward("teacherClassEntry");
		} catch (Exception e) {
			log.error("error in final submit of admitted through page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				saveMessages(request, messages);
				return mapping.findForward("teacherClassEntry");
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				teacherForm.setErrorMessage(msg);
				teacherForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		if (isUpdated) {
			ActionMessage message = new ActionMessage(
					"knowledgepro.attn.teacherclass.updatedsuccess");
			messages.add("messages", message);
			saveMessages(request, messages);
			teacherForm.reset(mapping, request);
			teacherForm.setSubjectMap(new HashMap<Integer, String>());
			setRequestedDataToForm(teacherForm);
		} else {
			errors.add("error", new ActionError(
					"knowledgepro.attn.teacherclass.addfailure"));
			saveErrors(request, errors);
			request.setAttribute("teacherClassOperation", "edit");
			setRequestedDataToFormWhenError(teacherForm);
			
		}
		log.debug("Exit: action class addTeacherClassAction");
		return mapping.findForward("teacherClassEntry");
		
	}
	/**
	 * setting the requested data to form
	 * @param teacherForm
	 * @throws Exception
	 */
	public void setRequestedDataToFormWhenError(TeacherClassEntryForm teacherForm) throws Exception {
		Calendar calendar = Calendar.getInstance();
		int currentYear = calendar.get(Calendar.YEAR);
		if(teacherForm.getYear()!=null && !teacherForm.getYear().isEmpty()){
			currentYear=Integer.parseInt(teacherForm.getYear());
		}else{
			int year = CurrentAcademicYear.getInstance().getAcademicyear();
			if (year != 0) {
				currentYear = year;
			}
		}
		// 1. The current year is loaded by default and it's corresponding
		// classes
		setClassListToForm(teacherForm, currentYear);
		// 2. Set the teacher's list
		//Map<Integer, String> teachersMap = UserInfoHandler.getInstance().getTeachingStaff();
		Map<Integer, String> teachersMap = TeacherClassEntryHandler.getInstance().getTeacherDepartmentNames();
		teacherForm.setTeachersMap(teachersMap);
		//setting the subject map
		if(teacherForm.getSelectedClasses()!=null && !(teacherForm.getSelectedClasses().toString()).isEmpty() && teacherForm.getSelectedClasses().length !=0){
			Map<Integer, String> subjectMap = new LinkedHashMap<Integer, String>();
			Set<Integer> classesIdsSet = new HashSet<Integer>();
			
			// code commented by chandra 
			/*classesIdsSet.add(Integer.parseInt(teacherForm.getSelectedClasses()));
			List<ClassSchemewise> classSchemewiseList = CommonAjaxHandler
			.getInstance().getDetailsonClassSchemewiseId(classesIdsSet);
			Iterator<ClassSchemewise> itr = classSchemewiseList.iterator();
			ClassSchemewise classSchemewise;
			while (itr.hasNext()) {
				classSchemewise = itr.next();
				if (classSchemewise.getCurriculumSchemeDuration()
						.getAcademicYear() != null
						&& classSchemewise.getClasses().getCourse().getId() != 0
						&& classSchemewise.getClasses().getTermNumber() != 0) {
					int year = classSchemewise.getCurriculumSchemeDuration()
					.getAcademicYear();
					int courseId = classSchemewise.getClasses().getCourse()
					.getId();
					int term = classSchemewise.getClasses().getTermNumber();
					Map<Integer, String> tempMap = CommonAjaxHandler.getInstance()
					.getSubjectByCourseIdTermYear(courseId, year, term);
					subjectMap.putAll(tempMap);
				}
			}*/
			
			
			// /* code added by chandra
			String selectedClasses[] = teacherForm.getSelectedClasses();
			for (int i = 0; i < selectedClasses.length; i++) {
				if(selectedClasses[i]!=null && !selectedClasses[i].isEmpty() && Integer.parseInt(selectedClasses[i])!=0){
					classesIdsSet.add(Integer.parseInt(selectedClasses[i]));
				}
			}
			if(classesIdsSet!=null && !classesIdsSet.isEmpty()){
				subjectMap=CommonAjaxHandler.getInstance().getCommonSubjectsByClass(classesIdsSet);
				teacherForm.setSubjectMap(subjectMap);
			}
		}
		// 3. for get details to display ie. teacher, class, subject by year
		teacherForm.setListTeacherClassEntry(TeacherClassEntryHandler.getInstance().getDetails(currentYear, teacherForm));
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward activateTeacherClassSubjectEntry(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.debug("Enter: activateTeacherClassSubjectEntry Action");
		TeacherClassEntryForm teacherForm = (TeacherClassEntryForm) form;
		ActionMessages messages = new ActionMessages();
		HttpSession session = request.getSession();
		ActionErrors errors = teacherForm.validate(mapping, request);
		boolean isActivated = false;
		try{
			int id=(Integer) session.getAttribute("ReacivateId");
			isActivated = TeacherClassEntryHandler.getInstance().deleteTeacherClassEntry(id, true, teacherForm);
		}catch (Exception e) {
			errors.add("error", new ActionError(CMSConstants.TEACHER_CLASS_SUBJECT_ACTIVATE_FAILURE));
			saveErrors(request, errors);
		}
		setRequestedDataToForm(teacherForm);
		if(isActivated){
			ActionMessage message = new ActionMessage(CMSConstants.TEACHER_CLASS_SUBJECT_ACTIVATE_SUCESS);
			messages.add("messages", message);
			saveMessages(request, messages);
		}
		log.debug("leaving activateTeacherClassSubjectEntry");
		return mapping.findForward("teacherClassEntry");
	}
	
	public ActionForward getCommonSubjectsForClass(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TeacherClassEntryForm teacherForm = (TeacherClassEntryForm) form;
		Map<Integer, String> subjectMap = new LinkedHashMap<Integer, String>();
		try {
			String selectedClasses[] = teacherForm.getSelectedClassesArray1()
					.split(",");

			Set<Integer> classesIdsSet = new HashSet<Integer>();
			for (int i = 0; i < selectedClasses.length; i++) {
				classesIdsSet.add(Integer.parseInt(selectedClasses[i]));
			}
			subjectMap=CommonAjaxHandler.getInstance().getCommonSubjectsByClass(classesIdsSet);
			
			if(!CMSConstants.ACTIVITY_SUBJECT_IDS.isEmpty()){
				
				for (Integer sid : CMSConstants.ACTIVITY_SUBJECT_IDS) {
					String query="from Subject s where s.id="+sid;
					Subject s=(Subject)PropertyUtil.getDataForUniqueObject(query);
					subjectMap.put(sid, s.getName()+"("+s.getCode()+")");
				}
			}
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		request.setAttribute("subjectOptionMap", subjectMap);
		return mapping.findForward("ajaxResponseForCommonSubject");
	}
}