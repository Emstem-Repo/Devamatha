package com.kp.cms.actions.examallotment;

import java.util.Calendar;
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
import com.kp.cms.forms.examallotment.ExamRoomAllotmentGroupWiseForm;
import com.kp.cms.forms.examallotment.RoomAllotmentStatusForm;
import com.kp.cms.handlers.examallotment.ExamRoomAllotmentGroupWiseHandler;
import com.kp.cms.handlers.examallotment.ExamRoomAllotmentStatusHandler;
import com.kp.cms.handlers.examallotment.RoomAllotmentStatusHandler;
import com.kp.cms.handlers.examallotment.StudentClassGroupHandler;
import com.kp.cms.to.examallotment.RoomAllotmentStatusDetailsTo;
import com.kp.cms.to.examallotment.RoomAllotmentStatusTo;
import com.kp.cms.utilities.CurrentAcademicYear;

public class RoomAllotmentStatusAction extends BaseDispatchAction {

	private static final Log log = LogFactory.getLog(RoomAllotmentStatusAction.class);
	
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initRoomAllotmentStatus(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		RoomAllotmentStatusForm allotmentStatusForm= (RoomAllotmentStatusForm) form;
		allotmentStatusForm.reset();
		setRequiredDataToForm(allotmentStatusForm, request);
		return mapping.findForward(CMSConstants.INIT_ROOM_ALLOTMENT_STATUS);
	}
	
	
	/**
	 * @param allotmentStatusForm
	 * @param request
	 * @throws Exception
	 */
	public void setRequiredDataToForm(RoomAllotmentStatusForm allotmentStatusForm,HttpServletRequest request) throws Exception{
		Map<Integer, String> locationMap=StudentClassGroupHandler.getInstance().getWorkLocation();
		allotmentStatusForm.setWorkLocationMap(locationMap);
		int year=0;
		year=CurrentAcademicYear.getInstance().getAcademicyear();
		if(allotmentStatusForm.getAcademicYear()!=null && !allotmentStatusForm.getAcademicYear().isEmpty()){
			year=Integer.parseInt(allotmentStatusForm.getAcademicYear());
		}
		if(year==0){
			Calendar calendar = Calendar.getInstance();
			year = calendar.get(Calendar.YEAR);
		}
		Map<Integer, String> examMap=RoomAllotmentStatusHandler.getInstance().getExamNameByAcademicYear(year);
		allotmentStatusForm.setExamMap(examMap);
		String midEndSem="M";
		if(allotmentStatusForm.getMidEndSem()!=null && !allotmentStatusForm.getMidEndSem().isEmpty()){
			midEndSem=allotmentStatusForm.getMidEndSem();
		}
		Map<Integer, String> cycleMap=RoomAllotmentStatusHandler.getInstance().getCycleByMidOrEnd(midEndSem);
		allotmentStatusForm.setCycleMap(cycleMap);
		if(allotmentStatusForm.getCampusName()!=null &&!allotmentStatusForm.getCampusName().isEmpty()){
			Map<Integer, String> roomMasterMap=RoomAllotmentStatusHandler.getInstance().getRoomNoByWorkLocationId(Integer.parseInt(allotmentStatusForm.getCampusName()));
			allotmentStatusForm.setRoomMasterMap(roomMasterMap);
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
	public ActionForward getRoomNoByWorkLocationId(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		RoomAllotmentStatusForm allotmentStatusForm= (RoomAllotmentStatusForm) form;
		try{
			Map<Integer, String> roomMasterMap=RoomAllotmentStatusHandler.getInstance().getRoomNoByWorkLocationId(Integer.parseInt(allotmentStatusForm.getCampusName()));
			request.setAttribute(CMSConstants.OPTION_MAP, roomMasterMap);
		 } catch (Exception e) {
				log.error("Error occured in edit pool Details", e);
				String msg = super.handleApplicationException(e);
				allotmentStatusForm.setErrorMessage(msg);
				allotmentStatusForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
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
	public ActionForward getCyclesByExamType(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		RoomAllotmentStatusForm allotmentStatusForm= (RoomAllotmentStatusForm) form;
		try{
	         Map<Integer, String> cycleMap=RoomAllotmentStatusHandler.getInstance().getCycleByMidOrEnd(allotmentStatusForm.getMidEndSem());
			 request.setAttribute(CMSConstants.OPTION_MAP, cycleMap);
		 } catch (Exception e) {
				log.error("Error occured in edit pool Details", e);
				String msg = super.handleApplicationException(e);
				allotmentStatusForm.setErrorMessage(msg);
				allotmentStatusForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		return mapping.findForward("ajaxResponseForOptions");
	}
	
	public ActionForward getStudentDetailsByRoom(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		RoomAllotmentStatusForm statusForm=(RoomAllotmentStatusForm) form;
		setUserId(request, statusForm);
		ActionErrors errors=statusForm.validate(mapping, request);
		if(errors.isEmpty()){
	    	try{
	    		List<RoomAllotmentStatusTo> allotmentStatusToList=RoomAllotmentStatusHandler.getInstance().getStudentDetailsAndDisplayByRoom(statusForm);
	    		if(!allotmentStatusToList.isEmpty()){
	    		statusForm.setRoomAllotmentStatusToList(allotmentStatusToList);
	    		statusForm.setDisplayStudents(true);
	    		}else{
	    			statusForm.setRoomAllotmentStatusToList(null);
	    			statusForm.setDisplayStudents(false);
	    			errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.norecords"));
	    			addErrors(request, errors);
	    		}
	    		setRequiredDataToForm(statusForm, request);
		    	} catch (Exception e) {
				log.error("Error occured in addExamRoomAllotGroupWise", e);
				String msg = super.handleApplicationException(e);
				statusForm.setErrorMessage(msg);
				statusForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
		    }
		}else{
			saveErrors(request, errors);
		}
		return mapping.findForward(CMSConstants.INIT_ROOM_ALLOTMENT_STATUS);
	}
	
	public ActionForward updateRegNoForAllotmentStatus(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		RoomAllotmentStatusForm allotmentStatusForm=(RoomAllotmentStatusForm) form;
		ActionMessages messages = new ActionMessages();
		setUserId(request, allotmentStatusForm);
		ActionErrors errors=new ActionErrors();
		HttpSession session = request.getSession();
		try{
			boolean isUpdated=false;
			isUpdated=RoomAllotmentStatusHandler.getInstance().updateRegNoAndStudentForAllotedRoom(allotmentStatusForm,errors);
			if(!isUpdated){
				if(!errors.isEmpty()){
					addErrors(request, errors);
				}else{
					messages.add(CMSConstants.MESSAGES, new ActionMessage("knowledgepro.exam.allotment.room.status.entered.regNo.updated.sucess"));
					saveMessages(request, messages);
				}
			}else{
				messages.add(CMSConstants.MESSAGES, new ActionMessage("knowledgepro.exam.allotment.room.status.entered.regNo.updated.sucess"));
				saveMessages(request, messages);
//				allotmentStatusForm.reset();
				log.info("added updateGroupClassWithStudents method success");
			}
			allotmentStatusForm.setStatusDetailsToList(null);
    		List<RoomAllotmentStatusDetailsTo> allotmentStatusToList=ExamRoomAllotmentStatusHandler.getInstance().getRoomsAllotedForCycle(allotmentStatusForm);
    		if(!allotmentStatusToList.isEmpty()){
    			allotmentStatusForm.setStatusDetailsToList(allotmentStatusToList);
    		}
			setRequiredDataToForm(allotmentStatusForm, request);
		} catch (Exception e) {
			log.error("Error occured in addExamRoomAllotGroupWise", e);
			String msg = super.handleApplicationException(e);
			allotmentStatusForm.setErrorMessage(msg);
			allotmentStatusForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
	    }
		
		String displayPage = session.getAttribute("DisplayFields").toString();
		if(displayPage.equalsIgnoreCase("true")){
			return mapping.findForward(CMSConstants.INIT_EXAM_ROOM_ALLOTMENT_STATUS);
		}else{
			return mapping.findForward(CMSConstants.INIT_ROOM_ALLOTMENT_STATUS);
		}
	}
	
	
}
