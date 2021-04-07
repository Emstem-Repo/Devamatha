package com.kp.cms.actions.examallotment;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.examallotment.RoomAllotmentStatusForm;
import com.kp.cms.handlers.examallotment.ExamRoomAllotmentHandler;
import com.kp.cms.handlers.examallotment.ExamRoomAllotmentStatusHandler;
import com.kp.cms.handlers.examallotment.RoomAllotmentStatusHandler;
import com.kp.cms.handlers.examallotment.StudentClassGroupHandler;
import com.kp.cms.to.examallotment.RoomAllotmentStatusDetailsTo;
import com.kp.cms.to.examallotment.RoomAllotmentStatusTo;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.CurrentAcademicYear;

public class ExamRoomAllotmentStatusAction extends BaseDispatchAction{
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initExamRoomStatus(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		RoomAllotmentStatusForm objForm = (RoomAllotmentStatusForm)form;
		objForm.reset1();
		setRequiredDataToForm(objForm, request);
		return mapping.findForward(CMSConstants.INIT_EXAM_ROOM_ALLOTMENT_STATUS);
	}

	/**
	 * @param objForm
	 * @param request
	 * @throws Exception
	 */
	private void setRequiredDataToForm(RoomAllotmentStatusForm objForm, HttpServletRequest request) throws Exception{
		Map<Integer, String> locationMap=StudentClassGroupHandler.getInstance().getWorkLocation();
		objForm.setWorkLocationMap(locationMap);
		int year=0;
		year=CurrentAcademicYear.getInstance().getAcademicyear();
		if(objForm.getAcademicYear()!=null && !objForm.getAcademicYear().isEmpty()){
			year=Integer.parseInt(objForm.getAcademicYear());
		}
		if(year==0){
			Calendar calendar = Calendar.getInstance();
			year = calendar.get(Calendar.YEAR);
		}
		Map<Integer, String> examMap=RoomAllotmentStatusHandler.getInstance().getExamNameByAcademicYear(year);
		examMap = (Map<Integer, String>) CommonUtil.sortMapByValue(examMap);
		objForm.setExamMap(examMap);
		objForm.setAcademicYear(String.valueOf(year));
		String midEndSem="M";
		if(objForm.getMidEndSem()!=null && !objForm.getMidEndSem().isEmpty()){
			midEndSem=objForm.getMidEndSem();
		}
		/* get the Cycle Details as a map*/
		Map<Integer, String> cycleMap=RoomAllotmentStatusHandler.getInstance().getCycleByMidOrEnd(midEndSem);
		objForm.setCycleMap(cycleMap);
		/* get Exam Session Details as a map*/
		Map<Integer,String> sessionMap = ExamRoomAllotmentHandler.getInstance().getSessionMap();
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
public ActionForward getRoomsDetailsAllotedForCycle(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
	RoomAllotmentStatusForm objForm = (RoomAllotmentStatusForm)form;
	setUserId(request, objForm);
	ActionErrors errors=objForm.validate(mapping, request);
	if(errors.isEmpty()){
    	try{
    		objForm.setStatusDetailsToList(null);
    		List<RoomAllotmentStatusDetailsTo> allotmentStatusToList=ExamRoomAllotmentStatusHandler.getInstance().getRoomsAllotedForCycle(objForm);
    		if(!allotmentStatusToList.isEmpty()){
    			objForm.setStatusDetailsToList(allotmentStatusToList);
    		}else{
    			objForm.setStatusDetailsToList(null);
    			errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.norecords"));
    			addErrors(request, errors);
    		}
    		setRequiredDataToForm(objForm, request);
    		if(objForm.getSessionId()!=null && !objForm.getSessionId().isEmpty()){
    			Map<Integer,String> sessionMap = objForm.getSessionMap();
    			if(sessionMap.containsKey(Integer.parseInt(objForm.getSessionId()))){
    				objForm.setSessionName(sessionMap.get(Integer.parseInt(objForm.getSessionId())));
    			}
    			objForm.setCycleName(null);
    		}
    		if(objForm.getCycleId()!=null && !objForm.getCycleId().isEmpty()){
    			Map<Integer,String> cycleMap = objForm.getCycleMap();
    			if(cycleMap.containsKey(Integer.parseInt(objForm.getCycleId()))){
    				objForm.setCycleName(cycleMap.get(Integer.parseInt(objForm.getCycleId())));
    			}
    			objForm.setSessionName(null);
    		}
	    	} catch (Exception e) {
			String msg = super.handleApplicationException(e);
			objForm.setErrorMessage(msg);
			objForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
	    }
	}else{
		saveErrors(request, errors);
	}
	return mapping.findForward(CMSConstants.INIT_EXAM_ROOM_ALLOTMENT_STATUS);
}
/**
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward goToRoomAllotmentStatus(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
	RoomAllotmentStatusForm allotmentStatusForm=(RoomAllotmentStatusForm) form;
	ActionErrors errors=allotmentStatusForm.validate(mapping, request);
	HttpSession session = request.getSession();
	try{
		if(errors.isEmpty()){
			allotmentStatusForm.setDisableFields("true");
			session.setAttribute("DisplayFields", "true");
			setRequiredDataToForm(allotmentStatusForm, request);
			if(allotmentStatusForm.getCampusName()!=null &&!allotmentStatusForm.getCampusName().isEmpty()){
				Map<Integer, String> roomMasterMap=RoomAllotmentStatusHandler.getInstance().getRoomNoByWorkLocationId(Integer.parseInt(allotmentStatusForm.getCampusName()));
				allotmentStatusForm.setRoomMasterMap(roomMasterMap);
			}
			Map<Integer, String> roomMap = allotmentStatusForm.getRoomMasterMap();
			for(Map.Entry entry: roomMap.entrySet()){
				if(allotmentStatusForm.getRoomId().equals(entry.getValue())){
					allotmentStatusForm.setRoomNo(String.valueOf(entry.getKey())) ;
					break; //breaking because its one to one map
				}
			}
			List<RoomAllotmentStatusTo> allotmentStatusToList=RoomAllotmentStatusHandler.getInstance().getStudentDetailsAndDisplayByRoom(allotmentStatusForm);
			if(allotmentStatusToList!=null && !allotmentStatusToList.isEmpty()){
				allotmentStatusForm.setRoomAllotmentStatusToList(allotmentStatusToList);
				allotmentStatusForm.setDisplayStudents(true);
			}else{
				allotmentStatusForm.setRoomAllotmentStatusToList(null);
				allotmentStatusForm.setDisplayStudents(false);
				errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.norecords"));
				addErrors(request, errors);
			}
		}else{
			saveErrors(request, errors);
		}
	}catch (Exception e) {
		String msg = super.handleApplicationException(e);
		allotmentStatusForm.setErrorMessage(msg);
		allotmentStatusForm.setErrorStack(e.getMessage());
		return mapping.findForward(CMSConstants.ERROR_PAGE);
    }
	return mapping.findForward(CMSConstants.INIT_ROOM_ALLOTMENT_STATUS);
}
}
