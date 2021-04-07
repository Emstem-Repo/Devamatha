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
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.examallotment.ExamRoomAvailabilityForm;
import com.kp.cms.handlers.examallotment.ExamRoomAvailabilityHandler;
import com.kp.cms.to.studentfeedback.RoomMasterTo;

public class ExamRoomAvailabilityAction extends BaseDispatchAction{
	private static final Log log = LogFactory.getLog(ExamRoomAvailabilityAction.class);
/**
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward initExamRoomAvailability(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
	ExamRoomAvailabilityForm objForm = (ExamRoomAvailabilityForm)form;
	objForm.reset();
	setUserId(request, objForm);
	try{
		Map<Integer,String> workLocationMap = ExamRoomAvailabilityHandler.getInstance().getWorkLocation();
		objForm.setWorkLocationMap(workLocationMap);
	}catch (Exception e) {
		log.error("error in initExamRoomAvailability...", e);
		if (e instanceof ApplicationException) {
			String msg = super.handleApplicationException(e);
			objForm.setErrorMessage(msg);
			objForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} else {
			throw e;
		}
	}
	return mapping.findForward(CMSConstants.INIT_EXAM_ROOM_AVAILABILITY);
}
/**
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward getAvailableRoomsOfWorkLocation(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	ExamRoomAvailabilityForm objForm = (ExamRoomAvailabilityForm)form;
	 ActionErrors actionErrors=objForm.validate(mapping, request);
	try{
			if(actionErrors.isEmpty()){
				List<RoomMasterTo> roomMasterTo = ExamRoomAvailabilityHandler.getInstance().getRoomAvailability(objForm);
				if(roomMasterTo!=null && !roomMasterTo.isEmpty()){
					objForm.setRoomMasterTO(roomMasterTo);
				}else{
					objForm.setRoomMasterTO(null);
					actionErrors.add("error", new ActionError("knowledgepro.norecords"));
					saveErrors(request, actionErrors);
				}
			}else{
			saveErrors(request, actionErrors);
			objForm.setRoomMasterTO(null);
		}
	}catch (Exception e) {
		log.error("error in getAvailableRoomsOfWorkLocation...", e);
		if (e instanceof ApplicationException) {
			String msg = super.handleApplicationException(e);
			objForm.setErrorMessage(msg);
			objForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} else {
			throw e;
		}
	}
	return mapping.findForward(CMSConstants.INIT_EXAM_ROOM_AVAILABILITY);
	}
/**
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward saveSelectedRoomDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	ExamRoomAvailabilityForm objForm = (ExamRoomAvailabilityForm)form;
	ActionMessages actionMessages = new ActionMessages();
	ActionErrors actionErrors = new ActionErrors();
	
	try{
			boolean isAdded = ExamRoomAvailabilityHandler.getInstance().saveRoomDetails(objForm);
			if(isAdded){
				ActionMessage message = new ActionMessage("knowledgepro.exam.room.availability.addsuccess");
				actionMessages.add("messages", message);
				saveMessages(request, actionMessages);
				objForm.setWorkLocatId(null);
				objForm.setRoomMasterTO(null);
			}else{
				actionErrors.add("error", new ActionError("knowledgepro.exam.room.availability.addfailure"));
				saveErrors(request, actionErrors);
			}
	}catch (Exception e) {
		log.error("error in final submit of saveSelectedRoomDetails page...", e);
		if (e instanceof BusinessException) {
			String msgKey = super.handleBusinessException(e);
			ActionMessage message = new ActionMessage(msgKey);
			actionMessages.add("messages", message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} else if (e instanceof ApplicationException) {
			actionErrors.add("error", new ActionError("knowledgepro.exam.room.availability.select.room"));
			saveErrors(request, actionErrors);
		} else {
			throw e;
		}
	}
	return mapping.findForward(CMSConstants.INIT_EXAM_ROOM_AVAILABILITY);
	}
}
