package com.kp.cms.actions.examallotment;

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
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.examallotment.ExamRoomAllotmentSpecializationForm;
import com.kp.cms.handlers.examallotment.ExamRoomAllotmentSpecializationHandler;
import com.kp.cms.handlers.hostel.HolidaysHandler;
import com.kp.cms.to.examallotment.ExamRoomAllotmentSpecializationTo;

public class ExamRoomAllotmentSpecializationAction extends BaseDispatchAction{
	private static final Log log = LogFactory.getLog(ExamRoomAllotmentSpecializationAction.class);
/**
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward initExamRoomSpecialization(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
	ExamRoomAllotmentSpecializationForm objForm = (ExamRoomAllotmentSpecializationForm)form;
	setUserId(request, objForm);
	objForm.reset();
	try{
		Map<Integer,String> courseMap = HolidaysHandler.getInstance().courseMap();
		objForm.setCourseMap(courseMap);
		objForm.setMidEndSem("Mid Sem");
		setExamSpecializationDataTOForm(objForm);
		
	}catch (Exception e) {
		log.error("error in initExamRoomSpecialization...", e);
		if (e instanceof ApplicationException) {
			String msg = super.handleApplicationException(e);
			objForm.setErrorMessage(msg);
			objForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} else {
			throw e;
		}
	}
	return mapping.findForward(CMSConstants.INIT_EXAM_ROOM_SPECIALIZATION);
}
/**
 * @param objForm
 * @throws Exception
 */
private void setExamSpecializationDataTOForm( ExamRoomAllotmentSpecializationForm objForm) throws Exception{
	if(objForm.getMidEndSem().equalsIgnoreCase("Mid Sem")){
		objForm.setMidEndSem("M");
	}else if(objForm.getMidEndSem().equalsIgnoreCase("End Sem")){
		objForm.setMidEndSem("E");
	}
	Map<String,Map<Integer,ExamRoomAllotmentSpecializationTo>> semWiseCoursesMap = ExamRoomAllotmentSpecializationHandler.getInstance().getExamRoomSpecializationList(objForm);
	objForm.setMidOrEndSemCoursesMap(semWiseCoursesMap);
}
/**
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward saveSpecializationDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	ExamRoomAllotmentSpecializationForm objForm = (ExamRoomAllotmentSpecializationForm)form;
	ActionMessages actionMessages = new ActionMessages();
	 ActionErrors actionErrors = objForm.validate(mapping, request);
	try{
		if(objForm.getSelectedCourses()== null || objForm.getSelectedCourses().toString().isEmpty()){
			actionErrors.add("error", new ActionError("knowledgepro.admission.empty.err.message",
			"Please Select the Course"));
			saveErrors(request, actionErrors);
		}
		if(actionErrors.isEmpty()){
			boolean isAdded = ExamRoomAllotmentSpecializationHandler.getInstance().saveExamRoomSpecializationDetails(objForm);
			if(isAdded){
				ActionMessage message = new ActionMessage("knowledgepro.exam.room.allotment.specialization.addsuccess");
				actionMessages.add("messages", message);
				saveMessages(request, actionMessages);
				objForm.setSelectedCourses(null);
				objForm.setSchemeNo(null);
				objForm.setCourseList(null);
				objForm.setSelectedCourseMap(null);
			}else{
				actionErrors.add("error", new ActionError("knowledgepro.exam.room.allotment.specialization.addfailure"));
				saveErrors(request, actionErrors);
				return mapping.findForward(CMSConstants.INIT_EXAM_ROOM_SPECIALIZATION);
			}
		}else{
			saveErrors(request, actionErrors);
			return mapping.findForward(CMSConstants.INIT_EXAM_ROOM_SPECIALIZATION);
		}
	} catch (Exception e) {
		log.error("error in final submit of Exam Room Specialization page...", e);
		if (e instanceof BusinessException) {
			String msgKey = super.handleBusinessException(e);
			ActionMessage message = new ActionMessage(msgKey);
			actionMessages.add("messages", message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} else if (e instanceof ApplicationException) {
			String msg = super.handleApplicationException(e);
			objForm.setErrorMessage(msg);
			objForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} else {
			throw e;
		}
	}
	objForm.setHide("false");
	Map<Integer,String> courseMap = HolidaysHandler.getInstance().courseMap();
	objForm.setCourseMap(courseMap);
	setExamSpecializationDataTOForm(objForm);
	return mapping.findForward(CMSConstants.INIT_EXAM_ROOM_SPECIALIZATION);
	}
/**
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward editSpecializationDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	ExamRoomAllotmentSpecializationForm objForm = (ExamRoomAllotmentSpecializationForm)form;
	ActionMessages messages = new ActionMessages();
	try{
		Map<Integer,String> courseMap = HolidaysHandler.getInstance().courseMap();
		objForm.setCourseMap(courseMap);
		ExamRoomAllotmentSpecializationHandler.getInstance().getEditSpecializationDetails(objForm,request);
		request.setAttribute("specialization", "edit");
		objForm.setHide("true");
	} catch (ApplicationException applicationException) {
		String msgKey = super.handleBusinessException(applicationException);
		ActionMessage message = new ActionMessage(msgKey);
		messages.add(CMSConstants.MESSAGES, message);
		return mapping.findForward(CMSConstants.INIT_EXAM_ROOM_SPECIALIZATION);
	} catch (Exception exception) {
		String msg = super.handleApplicationException(exception);
		objForm.setErrorMessage(msg);
		objForm.setErrorStack(exception.getMessage());
		return mapping.findForward(CMSConstants.ERROR_PAGE);
	}
	return mapping.findForward(CMSConstants.INIT_EXAM_ROOM_SPECIALIZATION);
	}
/**
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward updateSpecializationDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	ExamRoomAllotmentSpecializationForm objForm = (ExamRoomAllotmentSpecializationForm)form;
	ActionMessages actionMessages = new ActionMessages();
	 ActionErrors actionErrors = objForm.validate(mapping, request);
	try{
		if(actionErrors.isEmpty()){
			boolean isUpdate = ExamRoomAllotmentSpecializationHandler.getInstance().updateExamRoomSpecializationDetails(objForm);
			if(isUpdate){
				ActionMessage message = new ActionMessage("knowledgepro.exam.room.allotment.specialization.updatesuccess");
				actionMessages.add("messages", message);
				saveMessages(request, actionMessages);
				objForm.setSelectedCourses(null);
				objForm.setSchemeNo(null);
				objForm.setCourseList(null);
				objForm.setSelectedCourseMap(null);
			}else{
				actionErrors.add("error", new ActionError("knowledgepro.exam.room.allotment.specialization.updatefailure"));
				saveErrors(request, actionErrors);
				return mapping.findForward(CMSConstants.INIT_EXAM_ROOM_SPECIALIZATION);
			}
		}else{
			saveErrors(request, actionErrors);
			return mapping.findForward(CMSConstants.INIT_EXAM_ROOM_SPECIALIZATION);
		}
	}catch (Exception e) {
		log.error("error in updateSpecializationDetails page...", e);
		if (e instanceof BusinessException) {
			String msgKey = super.handleBusinessException(e);
			ActionMessage message = new ActionMessage(msgKey);
			actionMessages.add("messages", message);
			request.setAttribute("specialization", "edit");
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} else if (e instanceof ApplicationException) {
			String msg = super.handleApplicationException(e);
			objForm.setErrorMessage(msg);
			objForm.setErrorStack(e.getMessage());
			request.setAttribute("specialization", "edit");
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} else {
			throw e;
		}
	}
	objForm.setHide("false");
	request.setAttribute("specialization", "add");
	Map<Integer,String> courseMap = HolidaysHandler.getInstance().courseMap();
	objForm.setCourseMap(courseMap);
	setExamSpecializationDataTOForm(objForm);
	return mapping.findForward(CMSConstants.INIT_EXAM_ROOM_SPECIALIZATION);
	}
/**
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward deleteSpecializationDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	ExamRoomAllotmentSpecializationForm objForm = (ExamRoomAllotmentSpecializationForm)form;
	ActionErrors errors = new ActionErrors();
	ActionMessages messages = new ActionMessages();
	try{
		boolean isDeleted = ExamRoomAllotmentSpecializationHandler.getInstance().deleteExamRoomSpecializationDetails(objForm);
		if(isDeleted){
			ActionMessage message = new ActionMessage("knowledgepro.exam.room.allotment.specialization.deletesuccess");
			messages.add("messages",message);
			saveMessages(request, messages);
			objForm.setSelectedCourses(null);
			objForm.setSchemeNo(null);
			objForm.setCourseList(null);
			objForm.setSelectedCourseMap(null);
		}else{
			errors.add("error",new ActionError("knowledgepro.exam.room.allotment.specialization.deletefailure"));
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.INIT_EXAM_ROOM_SPECIALIZATION);
		}
	}catch (Exception e) {
		log.error("error in  deleteSpecializationDetails page...", e);
		if (e instanceof BusinessException) {
			String msgKey = super.handleBusinessException(e);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add("messages", message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} else if (e instanceof ApplicationException) {
			String msg = super.handleApplicationException(e);
			objForm.setErrorMessage(msg);
			objForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} else {
			throw e;
		}
	}
	objForm.setHide("false");
	Map<Integer,String> courseMap = HolidaysHandler.getInstance().courseMap();
	objForm.setCourseMap(courseMap);
	setExamSpecializationDataTOForm(objForm);
	return mapping.findForward(CMSConstants.INIT_EXAM_ROOM_SPECIALIZATION);
	}
/**
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward sortListByMidOrSemWise(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
		throws Exception {
	ExamRoomAllotmentSpecializationForm objForm = (ExamRoomAllotmentSpecializationForm)form;
	try{
		setExamSpecializationDataTOForm(objForm);
		if(objForm.getMidEndSem().equalsIgnoreCase("M")){
			objForm.setMidEndSem("Mid Sem");
		}else if(objForm.getMidEndSem().equalsIgnoreCase("E")){
			objForm.setMidEndSem("End Sem");
		}
	}catch (Exception exception) {
		String msg = super.handleApplicationException(exception);
		objForm.setErrorMessage(msg);
		objForm.setErrorStack(exception.getMessage());
		return mapping.findForward(CMSConstants.ERROR_PAGE);
	}
	return mapping.findForward(CMSConstants.INIT_EXAM_ROOM_SPECIALIZATION);
}
}
