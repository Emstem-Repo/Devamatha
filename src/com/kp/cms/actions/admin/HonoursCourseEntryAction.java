package com.kp.cms.actions.admin;

import java.util.List;
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
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.admin.HonoursCourseEntryForm;
import com.kp.cms.handlers.admin.HonoursCourseEntryHandler;
import com.kp.cms.to.admin.HonoursCourseEntryTo;

public class HonoursCourseEntryAction extends BaseDispatchAction{
	private static final Log log = LogFactory.getLog(HonoursCourseEntryAction.class);
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initHonoursCourseEntry(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HonoursCourseEntryForm honoursCourseEntryForm = (HonoursCourseEntryForm)form;
		try{
		setCourseMapToForm(honoursCourseEntryForm);
		setHonoursCourseListToForm(honoursCourseEntryForm);
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			honoursCourseEntryForm.setErrorMessage(msg);
			honoursCourseEntryForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.INIT_HONOURS_COURSE_ENTRY);
	}
	/**
	 * @param honoursCourseEntryForm
	 * @throws Exception
	 */
	private void setHonoursCourseListToForm( HonoursCourseEntryForm honoursCourseEntryForm) throws Exception{
		List<HonoursCourseEntryTo> list = HonoursCourseEntryHandler.getInstance().getHonoursCourseList();
		honoursCourseEntryForm.setHonoursCourseEntryTo(list);
	}
	/**
	 * @param honoursCourseEntryForm
	 * @throws Exception
	 */
	private void setCourseMapToForm(HonoursCourseEntryForm honoursCourseEntryForm) throws Exception{
		Map<Integer,String> courseMap = HonoursCourseEntryHandler.getInstance().getCourseMapDetails();
		honoursCourseEntryForm.setHonoursCourseMap(courseMap);
		honoursCourseEntryForm.setEligiableCourseMap(courseMap);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addHonoursCourseEntry(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
		HonoursCourseEntryForm honoursCourseEntryForm = (HonoursCourseEntryForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = honoursCourseEntryForm.validate(mapping, request);
		setUserId(request, honoursCourseEntryForm);
		try{
			if (errors.isEmpty()) {
			boolean isDuplicate = HonoursCourseEntryHandler.getInstance().checkDuplicateResult(honoursCourseEntryForm);
			if(!isDuplicate){
				boolean isAdded = HonoursCourseEntryHandler.getInstance().addHonoursCourseEntry(honoursCourseEntryForm,"Add");
				if (isAdded) {
					ActionMessage message = new ActionError( "knowledgepro.admin.honours.course.addsuccess");
					messages.add("messages", message);
					saveMessages(request, messages);
					honoursCourseEntryForm.reset(mapping, request);
				} else {
					honoursCourseEntryForm.reset(mapping, request);
					errors .add( "error", new ActionError( "knowledgepro.admin.honours.course.addfailure"));
					saveErrors(request, errors);
				}
			}else{
				errors .add( "error", new ActionError( "knowledgepro.admin.honours.course.exists.already"));
				saveErrors(request, errors);
			}
			}else{
				saveErrors(request, errors);
				}
		}catch (ReActivateException e1) {
			errors.add("error", new ActionError("knowledgepro.admin.honours.course.alreadyexist.reactivate"));
			saveErrors(request, errors);
			setHonoursCourseListToForm(honoursCourseEntryForm);
			return mapping.findForward(CMSConstants.INIT_HONOURS_COURSE_ENTRY);
		}catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			honoursCourseEntryForm.setErrorMessage(msg);
			honoursCourseEntryForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		setHonoursCourseListToForm(honoursCourseEntryForm);
		return mapping.findForward(CMSConstants.INIT_HONOURS_COURSE_ENTRY);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editHonoursCourse(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
		HonoursCourseEntryForm honoursCourseEntryForm = (HonoursCourseEntryForm)form;
		setUserId(request, honoursCourseEntryForm);
		ActionMessages messages = new ActionMessages();
		try{
			HonoursCourseEntryHandler.getInstance().editHonoursCourse(honoursCourseEntryForm);
			request.setAttribute("honoursCourse", "edit");
		}catch (BusinessException businessException) {
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {	
			String msg = super.handleApplicationException(exception);
			honoursCourseEntryForm.setErrorMessage(msg);
			honoursCourseEntryForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		setHonoursCourseListToForm(honoursCourseEntryForm);
		return mapping.findForward(CMSConstants.INIT_HONOURS_COURSE_ENTRY);
		}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateHonoursCourseEntry(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
		HonoursCourseEntryForm honoursCourseEntryForm = (HonoursCourseEntryForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = honoursCourseEntryForm.validate(mapping, request);
		setUserId(request, honoursCourseEntryForm);
		try{
			if (errors.isEmpty()) {
			boolean isDuplicate = HonoursCourseEntryHandler.getInstance().checkDuplicateResult(honoursCourseEntryForm);
			if(!isDuplicate){
				boolean isUpdate = HonoursCourseEntryHandler.getInstance().addHonoursCourseEntry(honoursCourseEntryForm,"Edit");
				if (isUpdate) {
					ActionMessage message = new ActionError( "knowledgepro.admin.honours.course.updatesuccess");
					messages.add("messages", message);
					saveMessages(request, messages);
					honoursCourseEntryForm.reset(mapping, request);
				} else {
					honoursCourseEntryForm.reset(mapping, request);
					request.setAttribute("honoursCourse", "edit");
					errors .add( "error", new ActionError( "knowledgepro.admin.honours.course.updatefailure"));
					saveErrors(request, errors);
				}
			}else{
				errors .add( "error", new ActionError( "knowledgepro.admin.honours.course.exists.already"));
				saveErrors(request, errors);}
			}else{
				saveErrors(request, errors);
			}
		}catch (ReActivateException e1) {
			errors.add("error", new ActionError("knowledgepro.admin.Department.addfailure.alreadyexist.reactivate"));
			saveErrors(request, errors);
			setHonoursCourseListToForm(honoursCourseEntryForm);
			request.setAttribute("honoursCourse", "edit");
			return mapping.findForward(CMSConstants.INIT_HONOURS_COURSE_ENTRY);
		}catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			honoursCourseEntryForm.setErrorMessage(msg);
			honoursCourseEntryForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		setHonoursCourseListToForm(honoursCourseEntryForm);
		request.setAttribute("honoursCourse", "add");
		return mapping.findForward(CMSConstants.INIT_HONOURS_COURSE_ENTRY);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteHonoursCourse(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
		HonoursCourseEntryForm honoursCourseEntryForm = (HonoursCourseEntryForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = honoursCourseEntryForm.validate(mapping, request);
		setUserId(request, honoursCourseEntryForm);
		boolean isDeleted=false;
		try{
			if(honoursCourseEntryForm.getId()!=0){
				int id=honoursCourseEntryForm.getId();
				isDeleted=HonoursCourseEntryHandler.getInstance().deleteHonoursCourse(id,false,honoursCourseEntryForm);
			}
		}catch (Exception e) {
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				honoursCourseEntryForm.setErrorMessage(msg);
				honoursCourseEntryForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		if(isDeleted){
			ActionMessage message = new ActionMessage("knowledgepro.admin.honours.course.deletesuccess");
			messages.add("messages",message);
			saveMessages(request, messages);
			honoursCourseEntryForm.reset(mapping, request);
		}else{
			errors.add("error",new ActionError("knowledgepro.admin.honours.course.deletefailure"));
			saveErrors(request, errors);
		}
		setHonoursCourseListToForm(honoursCourseEntryForm);
		return mapping.findForward(CMSConstants.INIT_HONOURS_COURSE_ENTRY);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward activateHonoursCourse(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
		HonoursCourseEntryForm honoursCourseEntryForm = (HonoursCourseEntryForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = honoursCourseEntryForm.validate(mapping, request);
		setUserId(request, honoursCourseEntryForm);
		boolean isActivate = false;
		try{
			if(honoursCourseEntryForm.getDupId()!=0){
				int id=honoursCourseEntryForm.getDupId();
				isActivate=HonoursCourseEntryHandler.getInstance().deleteHonoursCourse(id,true,honoursCourseEntryForm);
			}
		}catch (Exception e) {
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				honoursCourseEntryForm.setErrorMessage(msg);
				honoursCourseEntryForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		if(isActivate){
			ActionMessage message = new ActionMessage("knowledgepro.admin.honours.course.activesuccess");
			messages.add("messages",message);
			saveMessages(request, messages);
			honoursCourseEntryForm.reset(mapping, request);
		}else{
			errors.add("error",new ActionError("knowledgepro.admin.honours.course.activefailure"));
			saveErrors(request, errors);
		}
		setHonoursCourseListToForm(honoursCourseEntryForm);
		return mapping.findForward(CMSConstants.INIT_HONOURS_COURSE_ENTRY);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getHonoursCourse(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HonoursCourseEntryForm honoursCourseEntryForm = (HonoursCourseEntryForm)form;
		try{
			HttpSession session = request.getSession();
			if(session.getAttribute("stuCourseId") != null)
				honoursCourseEntryForm.setCourseMap(HonoursCourseEntryHandler.getInstance().getHonoursCourseMap(session.getAttribute("stuCourseId").toString()));
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			honoursCourseEntryForm.setErrorMessage(msg);
			honoursCourseEntryForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.HONOURS_COURSE);
	
	}
	 /**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getHonoursProgramApplication(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.debug("Entering the getHonoursProgramApplication");
		HonoursCourseEntryForm honoursCourseEntryForm = (HonoursCourseEntryForm)form;
		ActionErrors errors = new ActionErrors();
		try {
			if(honoursCourseEntryForm.getEligibleCourseId() != null && !honoursCourseEntryForm.getEligibleCourseId().isEmpty()){
				honoursCourseEntryForm.setAppliedCourseId(honoursCourseEntryForm.getEligibleCourseId());
				honoursCourseEntryForm.setSelectedCourse(honoursCourseEntryForm.getCourseMap().get(Integer.parseInt(honoursCourseEntryForm.getEligibleCourseId())));
				HttpSession session = request.getSession();
				if(session.getAttribute("studentid") != null){
					honoursCourseEntryForm.setStudentId(session.getAttribute("studentid").toString());
				}
				if(session.getAttribute("stuCourseId") != null){
					honoursCourseEntryForm.setCourseId(session.getAttribute("stuCourseId").toString());
				}
				Map<Integer, HonoursCourseEntryTo> academicDetails = HonoursCourseEntryHandler.getInstance().getDetails(honoursCourseEntryForm);
				honoursCourseEntryForm.setAcademicDetails(academicDetails);
			}else{
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.student.honours.course.required"));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.HONOURS_COURSE);
			}
		}catch (Exception e) {
			log.error("Error in getHonoursProgramApplication"+e.getMessage());
			String msg = super.handleApplicationException(e);
			honoursCourseEntryForm.setErrorMessage(msg);
			honoursCourseEntryForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.STUDENT_ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.HONOURS_COURSE_DETAILS);
	}
	 /**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward printApplication(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.debug("Entering the printApplication");
		HonoursCourseEntryForm honoursCourseEntryForm = (HonoursCourseEntryForm)form;
		try {
			HttpSession session = request.getSession();
			if(session.getAttribute("studentid") != null){
				setUserId(request, honoursCourseEntryForm);
				honoursCourseEntryForm.setStudentId(session.getAttribute("studentid").toString());
				HonoursCourseEntryHandler.getInstance().setStudentDetailsToForm(honoursCourseEntryForm);
				HonoursCourseEntryHandler.getInstance().saveApplicationDetails(honoursCourseEntryForm);
			}
		}catch (Exception e) {
			log.error("Error in printApplication"+e.getMessage());
			String msg = super.handleApplicationException(e);
			honoursCourseEntryForm.setErrorMessage(msg);
			honoursCourseEntryForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.STUDENT_ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.HONOURS_COURSE_PRINT);
	}
	 /**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initHonourseCourse(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.debug("Entering the initHonourseCourse");
		HonoursCourseEntryForm honoursCourseEntryForm = (HonoursCourseEntryForm)form;
		honoursCourseEntryForm.setCourseMap(HonoursCourseEntryHandler.getInstance().getHonoursCourseMap());
		honoursCourseEntryForm.reset(mapping, request);
		honoursCourseEntryForm.setAppliedDetails(null);
		return mapping.findForward(CMSConstants.HONOURS_COURSE_LIST);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveCourseList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.debug("Entering the initHonourseCourse");
		HonoursCourseEntryForm honoursCourseEntryForm = (HonoursCourseEntryForm)form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		try {
			if(honoursCourseEntryForm.getSemister() == null || honoursCourseEntryForm.getSemister().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.StudentLogin.certificate.semester.Required"));
			}
			if(honoursCourseEntryForm.getHonoursCourseId() == null || honoursCourseEntryForm.getHonoursCourseId().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admin.honours.course.selectedfor.required"));
			}
			if(errors.isEmpty()){
				setUserId(request, honoursCourseEntryForm);
				String msg = HonoursCourseEntryHandler.getInstance().saveCourseDetails(honoursCourseEntryForm);
				if(msg.isEmpty()){
					messages.add("messages",new ActionMessage("knowledgepro.admin.honours.course.list.added.success"));
					saveMessages(request, messages);
					honoursCourseEntryForm.setAppliedDetails(null);
				}else{
					honoursCourseEntryForm.setErrorMessage(msg);
					List<HonoursCourseEntryTo> applliedList = HonoursCourseEntryHandler.getInstance().resetTheData(honoursCourseEntryForm.getAppliedDetails());
					honoursCourseEntryForm.setAppliedDetails(applliedList);
				}
			}else{
				saveErrors(request, errors);
			}
		}catch (Exception e) {
			log.error("Error in initHonourseCourse"+e.getMessage());
			String msg = super.handleApplicationException(e);
			honoursCourseEntryForm.setErrorMessage(msg);
			honoursCourseEntryForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.HONOURS_COURSE_LIST);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getHonoursCourseStudents(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.debug("Entering the getHonoursCourseStudents");
		HonoursCourseEntryForm honoursCourseEntryForm = (HonoursCourseEntryForm)form;
		ActionErrors errors = new ActionErrors();
		try {
			if(honoursCourseEntryForm.getYear() != null && !honoursCourseEntryForm.getYear().trim().isEmpty()){
				honoursCourseEntryForm.setHonoursCourseMap(HonoursCourseEntryHandler.getInstance().getCourseMap());
				List<HonoursCourseEntryTo> applliedList = HonoursCourseEntryHandler.getInstance().getAppliedStudentCourseDetails(honoursCourseEntryForm.getYear(),honoursCourseEntryForm.getCourseId());
				if(!applliedList.isEmpty()){
					honoursCourseEntryForm.setAppliedDetails(applliedList);
					honoursCourseEntryForm.setSemister("5");
				}else{
					errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.employee.interview.comments.norecords"));
					honoursCourseEntryForm.setAppliedDetails(null);
					saveErrors(request, errors);
				}
			}else{
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admin.template.academic.year"));
				saveErrors(request, errors);
			}
		}catch (Exception e) {
			log.error("Error in getHonoursCourseStudents"+e.getMessage());
			String msg = super.handleApplicationException(e);
			honoursCourseEntryForm.setErrorMessage(msg);
			honoursCourseEntryForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.HONOURS_COURSE_LIST);
	}
}
