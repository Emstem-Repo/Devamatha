package com.kp.cms.actions.examallotment;

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
import com.kp.cms.bo.examallotment.ExamRoomAllotmentCycle;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.examallotment.ExamRoomAllotmentCycleForm;
import com.kp.cms.handlers.examallotment.ExamRoomAllotmentCycleHandler;
import com.kp.cms.handlers.hostel.HolidaysHandler;
import com.kp.cms.to.examallotment.ExamRoomAllotmentCycleTO;

public class ExamRoomAllotmentCycleAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(ExamRoomAllotmentCycleAction.class);
/**
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward initExamRoomAllotmentCycle(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
	ExamRoomAllotmentCycleForm objForm = (ExamRoomAllotmentCycleForm) form;
	setUserId(request, objForm);
	objForm.reset();
	try{
		Map<Integer,String> cycleMap = ExamRoomAllotmentCycleHandler.getInstance().getCycleMap();
		if(cycleMap!=null && !cycleMap.isEmpty()){
			objForm.setCycleMap(cycleMap);
		}
		Map<Integer,String> courseMap = HolidaysHandler.getInstance().courseMap();
		objForm.setCourseMap(courseMap);
		setRoomAllotmentCycleDetailsToForm(objForm);
	}catch (Exception e) {
		log.error("error in initExamRoomAllotmentCycle...", e);
		if (e instanceof ApplicationException) {
			String msg = super.handleApplicationException(e);
			objForm.setErrorMessage(msg);
			objForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} else {
			throw e;
		}
	}
	return mapping.findForward(CMSConstants.INIT_EXAM_ROOM_ALLOTMENT_CYCLE);
}
/**
 * @param objForm
 * @throws Exception
 */
private void setRoomAllotmentCycleDetailsToForm( ExamRoomAllotmentCycleForm objForm)throws Exception {
	Map<Integer,Map<String,Map<String,List<Integer>>>> allotmentCycleToList = ExamRoomAllotmentCycleHandler.getInstance().getRoomAllotmentCycleDetails();
	if(allotmentCycleToList!=null && !allotmentCycleToList.isEmpty()){
		objForm.setCycleDetailsMap(allotmentCycleToList);
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
public ActionForward saveExamRoomAllotmentCycleDetails(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response) throws Exception {
	ExamRoomAllotmentCycleForm objForm = (ExamRoomAllotmentCycleForm) form;
	ActionMessages messages = new ActionMessages();
	 ActionErrors errors = objForm.validate(mapping, request);
	try{
		
		if(objForm.getCourseIds()==null ||objForm.getCourseIds().toString().isEmpty()){
			errors.add("error", new ActionError("knowledgepro.admission.empty.err.message",
			"Please Select the Course"));
			saveErrors(request, errors);
		}
		if(errors.isEmpty()){
			boolean isAdded = ExamRoomAllotmentCycleHandler.getInstance().saveRoomAllotmentCycleDetails(objForm);
			if(isAdded){
				//add success message
				ActionMessage message = new ActionMessage("knowledgepro.exam.roomallotment.cycle.details.add.success");
				messages.add("messages", message);
				objForm.setFlag("false");
				request.setAttribute("operation", "add");
				saveMessages(request, messages);
				objForm.setCycleId(null);
				objForm.setSchemeNo(null);
				objForm.setCourseMap(null);
				objForm.setSelectedCourseMap(null);
				objForm.setMidOrEndSem(null);
				objForm.setSessionName(null);
			}else{
				// failed message
				errors.add("error", new ActionError("knowledgepro.exam.roomallotment.cycle.details.add.failure"));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.INIT_EXAM_ROOM_ALLOTMENT_CYCLE);
			}
		}else{
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.INIT_EXAM_ROOM_ALLOTMENT_CYCLE);
		}
	}catch (Exception e) {
		e.printStackTrace();
		log.error("error in final saveExamRoomAllotmentCycleDetails page...", e);
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
	Map<Integer,String> courseMap = HolidaysHandler.getInstance().courseMap();
	objForm.setCourseMap(courseMap);
	setRoomAllotmentCycleDetailsToForm(objForm);
	return mapping.findForward(CMSConstants.INIT_EXAM_ROOM_ALLOTMENT_CYCLE);
	}
/**
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward getCoursesBySchemeNo(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
	ExamRoomAllotmentCycleForm objForm = (ExamRoomAllotmentCycleForm) form;
	try{
		Map<Integer,String> courseMap = ExamRoomAllotmentCycleHandler.getInstance().getCourseListBySchemeNo(objForm);
		request.setAttribute(CMSConstants.OPTION_MAP, courseMap);
	}catch (Exception e) {
		log.debug(e.getMessage());
	}
	return mapping.findForward("ajaxResponseForOptions");
}
/**
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward editCycleDetails(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
	ExamRoomAllotmentCycleForm objForm = (ExamRoomAllotmentCycleForm) form;
	ActionMessages messages = new ActionMessages();
	try{
		Map<Integer,String> courseMap = HolidaysHandler.getInstance().courseMap();
		objForm.setCourseMap(courseMap);
		ExamRoomAllotmentCycleHandler.getInstance().getEditCycleListDetails(objForm);
		objForm.setFlag("true");
		request.setAttribute("operation", "edit");
	}catch (ApplicationException applicationException) {
		String msgKey = super.handleBusinessException(applicationException);
		ActionMessage message = new ActionMessage(msgKey);
		messages.add(CMSConstants.MESSAGES, message);
		return mapping.findForward(CMSConstants.INIT_EXAM_ROOM_ALLOTMENT_CYCLE);
	} catch (Exception exception) {
		String msg = super.handleApplicationException(exception);
		objForm.setErrorMessage(msg);
		objForm.setErrorStack(exception.getMessage());
		return mapping.findForward(CMSConstants.ERROR_PAGE);
	}
	return mapping.findForward(CMSConstants.INIT_EXAM_ROOM_ALLOTMENT_CYCLE);
}
/**
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward updateCycleDetails(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
	ExamRoomAllotmentCycleForm objForm = (ExamRoomAllotmentCycleForm) form;
	ActionMessages messages = new ActionMessages();
	 ActionErrors errors = objForm.validate(mapping, request);
	try{
		if(errors.isEmpty()){
			boolean isUpdated = ExamRoomAllotmentCycleHandler.getInstance().updateCycleDetails(objForm);
			if(isUpdated){
				//sucess message
				ActionMessage message = new ActionMessage("knowledgepro.exam.roomallotment.cycle.details.update.success");
				messages.add("messages", message);
				objForm.setFlag("false");
				request.setAttribute("operation", "add");
				saveMessages(request, messages);
				objForm.setCycleId(null);
				objForm.setSchemeNo(null);
				objForm.setCourseMap(null);
				objForm.setSelectedCourseMap(null);
				objForm.setMidOrEndSem(null);
				objForm.setSessionName(null);
			}else{
				// failed message
				errors.add("error", new ActionError("knowledgepro.exam.roomallotment.cycle.update.details.failure"));
				saveErrors(request, errors);
				request.setAttribute("operation", "edit");
				return mapping.findForward(CMSConstants.INIT_EXAM_ROOM_ALLOTMENT_CYCLE);
			}
		}else{
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.INIT_EXAM_ROOM_ALLOTMENT_CYCLE);
		}
	}catch (Exception e) {
		log.error("error in updateCycleDetails page...", e);
		if (e instanceof BusinessException) {
			String msgKey = super.handleBusinessException(e);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add("messages", message);
			request.setAttribute("operation", "edit");
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} else if (e instanceof ApplicationException) {
			String msg = super.handleApplicationException(e);
			objForm.setErrorMessage(msg);
			objForm.setErrorStack(e.getMessage());
			request.setAttribute("operation", "edit");
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} else {
			throw e;
		}
	}
	Map<Integer,String> courseMap = HolidaysHandler.getInstance().courseMap();
	objForm.setCourseMap(courseMap);
	setRoomAllotmentCycleDetailsToForm(objForm);
	return mapping.findForward(CMSConstants.INIT_EXAM_ROOM_ALLOTMENT_CYCLE);
}
/**
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward deleteCycleDetails(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
	ExamRoomAllotmentCycleForm objForm = (ExamRoomAllotmentCycleForm) form;
	ActionErrors errors = new ActionErrors();
	ActionMessages messages = new ActionMessages();
	try{
		boolean isDeleted = ExamRoomAllotmentCycleHandler.getInstance().deleteCycleDetails(objForm);
		if(isDeleted){
			// success deleted
			ActionMessage message = new ActionMessage("knowledgepro.exam.roomallotment.cycle.details.delete.success");
			messages.add("messages", message);
			saveMessages(request, messages);
			objForm.setCycleId(null);
			objForm.setSchemeNo(null);
			objForm.setCourseMap(null);
			objForm.setSelectedCourseMap(null);
			objForm.setMidOrEndSem(null);
			objForm.setSessionName(null);
		}else{
			// failure error message.
			errors.add("error", new ActionError("knowledgepro.exam.roomallotment.cycle.details.delete.failure"));
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.INIT_EXAM_ROOM_ALLOTMENT_CYCLE);
		}
	} catch (Exception e) {
		log.error("error in deleteRoomCyleEntry  page...", e);
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
	objForm.setFlag("false");
	Map<Integer,String> courseMap = HolidaysHandler.getInstance().courseMap();
	objForm.setCourseMap(courseMap);
	setRoomAllotmentCycleDetailsToForm(objForm);
	return mapping.findForward(CMSConstants.INIT_EXAM_ROOM_ALLOTMENT_CYCLE);
}
/**
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward initRoomCycleEntry(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
	ExamRoomAllotmentCycleForm objForm = (ExamRoomAllotmentCycleForm) form;
	setUserId(request, objForm);
	objForm.reset1();
	try{
		setCycleEntryDetailsToForm(objForm);
	}catch (Exception e) {
		log.error("error in initRoomCycleEntry...", e);
		if (e instanceof ApplicationException) {
			String msg = super.handleApplicationException(e);
			objForm.setErrorMessage(msg);
			objForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} else {
			throw e;
		}
	}
	return mapping.findForward(CMSConstants.INIT_ROOM_CYCLE_ENTRY);
}
/**
 * @param objForm
 */
	private void setCycleEntryDetailsToForm(ExamRoomAllotmentCycleForm objForm)throws Exception {
		List<ExamRoomAllotmentCycleTO> toList = ExamRoomAllotmentCycleHandler.getInstance().getCycleEntryDetails();
		objForm.setToList(toList);
		Map<Integer, String> sessionMap = ExamRoomAllotmentCycleHandler.getInstance().getExamSessionMap();
		objForm.setSessionMap(sessionMap);
	}
/**
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward addRoomCycleEntry(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
	ExamRoomAllotmentCycleForm objForm = (ExamRoomAllotmentCycleForm) form;
	ActionMessages messages = new ActionMessages();
	 ActionErrors errors = objForm.validate(mapping, request);
	try{
		if(errors.isEmpty()){
			boolean isDuplicate = ExamRoomAllotmentCycleHandler.getInstance().checkDuplicateEntry(objForm);
				if(!isDuplicate){
					boolean isAdded = ExamRoomAllotmentCycleHandler.getInstance().addRoomCycleDetails(objForm,"Add");
						if(isAdded){
							//add success message
							ActionMessage message = new ActionMessage("knowledgepro.exam.roomallotment.cycle.add.success");
							messages.add("messages", message);
							saveMessages(request, messages);
							objForm.reset1();
						}else{
							// failed message
							errors.add("error", new ActionError("knowledgepro.exam.roomallotment.cycle.add.failure"));
							saveErrors(request, errors);
							return mapping.findForward(CMSConstants.INIT_ROOM_CYCLE_ENTRY);
						}
				}else{
					errors.add("error", new ActionError("knowledgepro.exam.roomallotment.cycle.duplicate.entry"));
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.INIT_ROOM_CYCLE_ENTRY);
				}
			}else{
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.INIT_ROOM_CYCLE_ENTRY);
			}
	}catch (Exception e) {
		e.printStackTrace();
		log.error("error in final addRoomCycleEntry page...", e);
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
	setCycleEntryDetailsToForm(objForm);
	return mapping.findForward(CMSConstants.INIT_ROOM_CYCLE_ENTRY);
}
/**
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward updateRoomCycleEntry(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
	ExamRoomAllotmentCycleForm objForm = (ExamRoomAllotmentCycleForm) form;
	ActionMessages messages = new ActionMessages();
	 ActionErrors errors = objForm.validate(mapping, request);
	try{
		if(errors.isEmpty()){
			boolean isDuplicate = ExamRoomAllotmentCycleHandler.getInstance().checkDuplicateEntry(objForm);
			if(!isDuplicate) {
				boolean isUpdate = ExamRoomAllotmentCycleHandler.getInstance().addRoomCycleDetails(objForm,"Edit");
				if(isUpdate){
					//sucess message
					ActionMessage message = new ActionMessage("knowledgepro.exam.roomallotment.cycle.update.success");
					messages.add("messages", message);
					saveMessages(request, messages);
					objForm.reset1();
				}else{
					// failed message
					errors.add("error", new ActionError("knowledgepro.exam.roomallotment.cycle.update.failure"));
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.INIT_ROOM_CYCLE_ENTRY);
				}
			}else{
				errors.add("error", new ActionError("knowledgepro.exam.roomallotment.cycle.duplicate.entry"));
				saveErrors(request, errors);
				request.setAttribute("operation", "edit");
				return mapping.findForward(CMSConstants.INIT_ROOM_CYCLE_ENTRY);
			}
		}else{
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.INIT_ROOM_CYCLE_ENTRY);
		}
	} catch (Exception e) {
		log.error("error in updateRoomCycleEntry page...", e);
		if (e instanceof BusinessException) {
			String msgKey = super.handleBusinessException(e);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add("messages", message);
			request.setAttribute("operation", "edit");
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} else if (e instanceof ApplicationException) {
			String msg = super.handleApplicationException(e);
			objForm.setErrorMessage(msg);
			objForm.setErrorStack(e.getMessage());
			request.setAttribute("operation", "edit");
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} else {
			throw e;
		}
	}
	setCycleEntryDetailsToForm(objForm);
	return mapping.findForward(CMSConstants.INIT_ROOM_CYCLE_ENTRY);
}
/**
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward deleteRoomCyleEntry(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
	ExamRoomAllotmentCycleForm objForm = (ExamRoomAllotmentCycleForm) form;
	ActionMessages messages = new ActionMessages();
	ActionErrors errors = new ActionErrors();
	try{
		boolean isDelete = ExamRoomAllotmentCycleHandler.getInstance().deleteRoomCycleDetails(objForm.getId(),false,objForm.getUserId());
		if(isDelete){
			// success deleted
			ActionMessage message = new ActionMessage("knowledgepro.exam.roomallotment.cycle.delete.success");
			messages.add("messages", message);
			saveMessages(request, messages);
			objForm.reset1();
		}else{
			// failure error message.
			errors.add("error", new ActionError("knowledgepro.exam.roomallotment.cycle.delete.failure"));
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.INIT_ROOM_CYCLE_ENTRY);
		}
	} catch (Exception e) {
		log.error("error in deleteRoomCyleEntry  page...", e);
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
	setCycleEntryDetailsToForm(objForm);
	return mapping.findForward(CMSConstants.INIT_ROOM_CYCLE_ENTRY);
}
/**
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public void getMidEndSessionByCycleId(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
	ExamRoomAllotmentCycleForm objForm = (ExamRoomAllotmentCycleForm) form;
	try{
		int cycleId = Integer.parseInt(objForm.getCycleId());
		if(cycleId>0){
			String details="";
			ExamRoomAllotmentCycle examAllotmentCycle = ExamRoomAllotmentCycleHandler.getInstance().getMidOrEndAndSessionDetailsByCycleId(cycleId);
			if(examAllotmentCycle.getMidOrEnd()!=null && !examAllotmentCycle.getMidOrEnd().isEmpty()){
				details= examAllotmentCycle.getMidOrEnd();
			}
			if(examAllotmentCycle.getExaminationSessions() != null && examAllotmentCycle.getExaminationSessions().getSession() != null){
				details=details +","+examAllotmentCycle.getExaminationSessions().getSession();
			}
			response.getWriter().write(details);
		}
	}catch (Exception e) {
		log.debug(e.getMessage());
	}
}
}
