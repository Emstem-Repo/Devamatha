package com.kp.cms.actions.examallotment;

import java.util.Calendar;
import java.util.HashMap;
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
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.examallotment.ExamRoomAllotmentForCJCForm;
import com.kp.cms.handlers.exam.ExamPublishHallTicketHandler;
import com.kp.cms.handlers.exam.ExamValidationDetailsHandler;
import com.kp.cms.handlers.examallotment.ExamRoomAllotmentForCJCHandler;
import com.kp.cms.handlers.examallotment.ExamRoomAllotmentHandler;
import com.kp.cms.handlers.examallotment.ExamRoomAvailabilityHandler;
import com.kp.cms.handlers.examallotment.RoomAllotmentStatusHandler;
import com.kp.cms.to.examallotment.RoomAllotmentStatusTo;
import com.kp.cms.utilities.CurrentAcademicYear;

public class ExamRoomAllotmentForCJCAction extends BaseDispatchAction{
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initExamRoomAllotmentForCJC(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) throws Exception {
		ExamRoomAllotmentForCJCForm objForm = (ExamRoomAllotmentForCJCForm) form;
		objForm.reset(); //resets the entire form
		setUserId(request, objForm);
		try {
//			set the required data to the form.
			setRequiredDetailsToForm(objForm);
		} catch (Exception e) {
			String msg = super.handleApplicationException(e);
			objForm.setErrorMessage(msg);
			objForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping .findForward(CMSConstants.INIT_EXAM_ROOM_ALLOTMENT_FOR_CJC);
	}

	/**
	 * @param objForm
	 * @throws Exception
	 */
	private void setRequiredDetailsToForm(ExamRoomAllotmentForCJCForm objForm) throws Exception {
		//to get Exam List
		Calendar calendar = Calendar.getInstance();
		int currentYear = calendar.get(Calendar.YEAR);
		if(objForm.getYear()!=null && !objForm.getYear().isEmpty()){
			currentYear=Integer.parseInt(objForm.getYear());
		}else{
			int year=CurrentAcademicYear.getInstance().getAcademicyear();
			 if(year!=0){
				currentYear=year;
				
			}
			 objForm.setAcademicYear(Integer.toString(currentYear));
		}
		Map<Integer, String> workLocationMap = ExamRoomAvailabilityHandler .getInstance().getWorkLocation();
		objForm.setCampusMap(workLocationMap);
		/* get Exam Session Details as a map */
		Map<Integer, String> sessionMap = ExamRoomAllotmentHandler .getInstance().getSessionMap();
		objForm.setSessionMap(sessionMap);
	}
	/** This method gets the selected Room details List.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getRoomDetailsForAllotment(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		ExamRoomAllotmentForCJCForm objForm = (ExamRoomAllotmentForCJCForm) form;
		try {
			resetFields(objForm);
			String examType = "";
			if (objForm.getExamType().equalsIgnoreCase("Int")) {
				examType = "M";
			} else if (objForm.getExamType().equalsIgnoreCase("Reg")) {
				examType = "E";
			}
			List<RoomAllotmentStatusTo> roomAllotmentStatusTos = ExamRoomAllotmentForCJCHandler
					.getInstance().getRoomDetailsAllotment(objForm, request, examType);
			if (roomAllotmentStatusTos != null && !roomAllotmentStatusTos.isEmpty()) {
				objForm.setRoomAllotmentStatusTo(roomAllotmentStatusTos);
			}
			setRequiredDataForm(objForm, examType);
		} catch (Exception e) {
			String msg = super.handleApplicationException(e);
			objForm.setErrorMessage(msg);
			objForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping .findForward(CMSConstants.EXAM_ROOM_ALLOTMENT_FOR_CJC_SECOND_PAGE);
	}

	/** this method reset the required properties of the form.
	 * @param objForm
	 * @throws Exception
	 */
	private void resetFields(ExamRoomAllotmentForCJCForm objForm) throws Exception {
		objForm.setAllotmentDetailsToList(null);
		objForm.setAllotStudentDetailsMap(null);
		objForm.setRoomAllotmentStatusTo(null);
		if (objForm.getIsDateWise().equalsIgnoreCase("Yes")) {
			objForm.setCycleId(null);
		} else if (objForm.getIsDateWise().equalsIgnoreCase("No")) {
			objForm.setAllottedDate(null);
			objForm.setSessionId(null);
		}
		objForm.setCycleMap(null);
		objForm.setClassMap(null);
		objForm.setClassMap1(null);
		objForm.setSubjectMap(null);
		objForm.setSubjectMap1(null);
		objForm.setColOneAllotedSeats(0);
		objForm.setColOneAvailableSeats(0);
		objForm.setColTwoAllotedSeats(0);
		objForm.setColTwoAvailableSeats(0);
		objForm.setColumnOneTotalSeats(0);
		objForm.setColumnTwoTotalSeats(0);
		objForm.setExamName(null);
		objForm.setCampusName(null);
		objForm.setRoomName(null);
		objForm.setDateName(null);
		objForm.setSessionName(null);
		objForm.setCycleName(null);
		objForm.setClassId(null);
		objForm.setClassId1(null);
		objForm.setSubjectId(null);
		objForm.setSubjectId1(null);
		objForm.setNoOfStudents(null);
		objForm.setNoOfStudents1(null);
		objForm.setNoOfStudents2(null);
		objForm.setNoOfStudents3(null);
		objForm.setFromRegNo(null);
		objForm.setFromRegNo1(null);
		objForm.setFromRegNo2(null);
		objForm.setFromRegNo3(null);
		objForm.setToRegNo(null);
		objForm.setToRegNo1(null);
		objForm.setToRegNo2(null);
		objForm.setToRegNo3(null);
		objForm.setExamRoomAllotmentId(0);
		objForm.setEvenClassOrSubjectCount(0);
		objForm.setOddClassOrSubjectCount(0);
		objForm.setLastRegNoMap(null);
		objForm.setAllotAllCol("off");
	}

	/**
	 * @param objForm
	 * @throws Exception
	 */
	private void setRequiredDataForm(ExamRoomAllotmentForCJCForm objForm,
			String examType) throws Exception {
		/* set exam map to form */
		Map<Integer, String> examMap = ExamValidationDetailsHandler
				.getInstance().getExamNameByExamType(objForm.getExamType(), objForm.getAcademicYear());
		objForm.setExamMap(examMap);
		objForm.setExamName(examMap.get(objForm.getExamid()));
		/*
		 * String currentExam =
		 * ExamMarksEntryHandler.getInstance().getCurrentExamName
		 * (objForm.getExamType()); if(currentExam!=null &&
		 * !currentExam.isEmpty()){
		 * objForm.setExamid(Integer.parseInt(currentExam)); }
		 */
		Map<Integer, String> campusMap = objForm.getCampusMap();
		objForm.setCampusName(campusMap.get(Integer.parseInt(objForm .getCampusId())));
		/* set room map to form */
		Map<Integer, String> roomMasterMap = RoomAllotmentStatusHandler
				.getInstance().getRoomNoByWorkLocationId( Integer.parseInt(objForm.getCampusId()));
		objForm.setRoomMap(roomMasterMap);
		objForm.setRoomName(roomMasterMap.get(Integer.parseInt(objForm
				.getRoomId())));
		/* set cycle map to form */
		if (objForm.getIsDateWise().equalsIgnoreCase("Yes")) {
			/* set subject map to form */
			String subCode = "sName";
			/*Map<Integer, String> subjectMap = ExamValidationDetailsHandler .getInstance().getSubjectCodeName("", subCode,
							objForm.getExamid(), objForm.getExamType());*/
			Map<Integer,String> subjectMap = ExamRoomAllotmentForCJCHandler.getInstance().getSubjectMapForDateAndSession(objForm);
			objForm.setSubjectMap(subjectMap);
//			String subjectName = subjectMap.get(objForm.getSubjectId());
//			objForm.setSubjectName(subjectName);
			Map<Integer, String> sessionMap = objForm.getSessionMap();
			objForm.setSessionName(sessionMap.get(Integer.parseInt(objForm
					.getSessionId())));
			objForm.setDateName(objForm.getDate());
		} else if (objForm.getIsDateWise().equalsIgnoreCase("No")) {
			/* set classes map to form */
			String examTypeForClass = "";
			if (objForm.getExamType().equalsIgnoreCase("Int")) {
				examTypeForClass = "4";
			} else if (objForm.getExamType().equalsIgnoreCase("Reg")) {
				examTypeForClass = "1";
			}
			Map<Integer, String> classesMap = ExamPublishHallTicketHandler
					.getInstance().getclassesMap( String.valueOf(objForm.getExamid()), examTypeForClass, objForm.getProgramId(),
							objForm.getDeanaryName());
			objForm.setClassMap(classesMap);
			objForm.setClassMap1(classesMap);
			Map<Integer, String> cycleMap = RoomAllotmentStatusHandler
					.getInstance().getCycleByMidOrEnd(examType);
			objForm.setCycleMap(cycleMap);
			String cycleName = cycleMap.get(Integer.parseInt(objForm .getCycleId()));
			objForm.setCycleName(cycleName);
		}
		objForm.setExamType(examType);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getRegisterNosRange(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamRoomAllotmentForCJCForm objForm = (ExamRoomAllotmentForCJCForm) form;
		HttpSession session = request.getSession();
		try {
			ExamRoomAllotmentForCJCHandler.getInstance() .getRegisterNOsBWFromAndTo(objForm, session, request);
		} catch (Exception e) {
			String msg = super.handleApplicationException(e);
			objForm.setErrorMessage(msg);
			objForm.setErrorStack(e.getMessage());
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
	public ActionForward studentsAllotment(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		ExamRoomAllotmentForCJCForm objForm = (ExamRoomAllotmentForCJCForm) form;
		HttpSession session = request.getSession();
		try {
			String lastRegisterNo = "";
			String noOfStudents = "";
			String oddOrEven = "";
			int remainingStudents = 0;
			int classId = 0;
			int subjectId = 0;
			if (objForm.getTempField().equalsIgnoreCase("Classes")) {
				if (objForm.getPropertyName().equalsIgnoreCase("odd")) {
					if (session.getAttribute("regNOCol1") != null) {
						lastRegisterNo = session.getAttribute("regNOCol1")
								.toString();
					}
					noOfStudents = objForm.getNoOfStudents();
					oddOrEven = "odd";
					classId = Integer.parseInt(objForm.getClassId());
					remainingStudents = objForm.getOddClassOrSubjectCount();
					remainingStudents =remainingStudents - Integer.valueOf(noOfStudents);
					objForm.setOddClassOrSubjectCount(remainingStudents);
				} else if (objForm.getPropertyName().equalsIgnoreCase("even")) {
					if (session.getAttribute("regNOCol2") != null) {
						lastRegisterNo = session.getAttribute("regNOCol2")
								.toString();
					}
					noOfStudents = objForm.getNoOfStudents1();
					oddOrEven = "even";
					classId = Integer.parseInt(objForm.getClassId1());
					remainingStudents = objForm.getEvenClassOrSubjectCount();
					remainingStudents =remainingStudents - Integer.valueOf(noOfStudents);
					objForm.setEvenClassOrSubjectCount(remainingStudents);
				}
			} else if (objForm.getTempField().equalsIgnoreCase("Subject")) {
				if (objForm.getPropertyName().equalsIgnoreCase("odd")) {
					if (session.getAttribute("regNOCol1") != null) {
						lastRegisterNo = session.getAttribute("regNOCol1")
								.toString();
					}
					noOfStudents = objForm.getNoOfStudents2();
					oddOrEven = "odd";
					subjectId = Integer.parseInt(objForm.getSubjectId());
					remainingStudents = objForm.getOddClassOrSubjectCount();
					remainingStudents =remainingStudents - Integer.valueOf(noOfStudents);
					objForm.setOddClassOrSubjectCount(remainingStudents);
				} else if (objForm.getPropertyName().equalsIgnoreCase("even")) {
					if (session.getAttribute("regNOCol2") != null) {
						lastRegisterNo = session.getAttribute("regNOCol2")
								.toString();
					}
					noOfStudents = objForm.getNoOfStudents3();
					oddOrEven = "even";
					subjectId = Integer.parseInt(objForm.getSubjectId1());
					remainingStudents = objForm.getEvenClassOrSubjectCount();
					remainingStudents =remainingStudents - Integer.valueOf(noOfStudents);
					objForm.setEvenClassOrSubjectCount(remainingStudents);
				}
			}
			
			List<RoomAllotmentStatusTo> allotmentList = ExamRoomAllotmentForCJCHandler
					.getInstance().studentsAllotmentForRoom(objForm,
							lastRegisterNo, Integer.parseInt(noOfStudents),
							oddOrEven, classId, subjectId,objForm.getCampusId());
			objForm.setRoomAllotmentStatusTo(allotmentList);
			
			resetFields1(objForm);
		} catch (Exception e) {
			String msg = super.handleApplicationException(e);
			objForm.setErrorMessage(msg);
			objForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping .findForward(CMSConstants.EXAM_ROOM_ALLOTMENT_FOR_CJC_SECOND_PAGE);
	}
	/**
	 * @param objForm
	 * @throws Exception
	 */
	private void resetFields1(ExamRoomAllotmentForCJCForm objForm) throws Exception {
		if (objForm.getTempField().equalsIgnoreCase("Classes")) {
			if (objForm.getPropertyName().equalsIgnoreCase("odd")) {
				objForm.setNoOfStudents(null);
				objForm.setFromRegNo(null);
				objForm.setToRegNo(null);
			} else if (objForm.getPropertyName().equalsIgnoreCase("even")) {
				objForm.setNoOfStudents1(null);
				objForm.setFromRegNo1(null);
				objForm.setToRegNo1(null);
			}
		} else if (objForm.getTempField().equalsIgnoreCase("Subject")) {
			if (objForm.getPropertyName().equalsIgnoreCase("odd")) {
				objForm.setNoOfStudents2(null);
				objForm.setFromRegNo2(null);
				objForm.setToRegNo2(null);
			} else if (objForm.getPropertyName().equalsIgnoreCase("even")) {
				objForm.setNoOfStudents3(null);
				objForm.setFromRegNo3(null);
				objForm.setToRegNo3(null);
			}
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
	public ActionForward clearStudentsAllotment(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		ExamRoomAllotmentForCJCForm objForm = (ExamRoomAllotmentForCJCForm) form;
		try {
			String oddOrEvenColumn = objForm.getPropertyName();
			List<RoomAllotmentStatusTo> tos = null;
			if(objForm.getAllotAllCol().equalsIgnoreCase("Off")){
				 tos = ExamRoomAllotmentForCJCHandler .getInstance().clearStudentAllotmentDetailsList(objForm, oddOrEvenColumn);
			}else {
				tos = ExamRoomAllotmentForCJCHandler.getInstance().clearSingleColumnStudentAllotmentDetailsList(objForm, oddOrEvenColumn);
			}
			objForm.setRoomAllotmentStatusTo(tos);
			resetFields1(objForm);
			objForm.setClassId(null);
			objForm.setClassId1(null);
			objForm.setSubjectId(null);
			objForm.setSubjectId1(null);
			objForm.setEvenClassOrSubjectCount(0);
			objForm.setOddClassOrSubjectCount(0);
			objForm.setLastRegNoMap(null);
		} catch (Exception e) {
			String msg = super.handleApplicationException(e);
			objForm.setErrorMessage(msg);
			objForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping .findForward(CMSConstants.EXAM_ROOM_ALLOTMENT_FOR_CJC_SECOND_PAGE);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward submitStudentsAllotment(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		ExamRoomAllotmentForCJCForm objForm = (ExamRoomAllotmentForCJCForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		try {
			boolean isAdded = ExamRoomAllotmentForCJCHandler.getInstance()
					.getSubmitStudentAllotList(objForm, errors);
			if (isAdded) {
				if (errors.isEmpty()) { messages.add(CMSConstants.MESSAGES, new ActionMessage(
							"knowledgepro.exam.room.allotment.success"));
					saveMessages(request, messages);
				} else {
					saveErrors(request, errors);
				}
			} else {
				errors.add("error", new ActionError( "knowledgepro.exam.room.allotment.failed"));
				saveErrors(request, errors);
				return mapping .findForward(CMSConstants.EXAM_ROOM_ALLOTMENT_FOR_CJC_SECOND_PAGE);
			}
			objForm.setRoomId(null);
			objForm.setLastRegNoMap(null);
			objForm.setAllotAllCol("off");
			setRequiredDetailsToForm(objForm);
		} catch (Exception e) {
			String msg = super.handleApplicationException(e);
			objForm.setErrorMessage(msg);
			objForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping .findForward(CMSConstants.INIT_EXAM_ROOM_ALLOTMENT_FOR_CJC);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getRemainingCount(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamRoomAllotmentForCJCForm objForm = (ExamRoomAllotmentForCJCForm) form;
		HttpSession session = request.getSession();
		try{
//			when ever the request comes first clear the previous session data.
			Map<Integer,Integer> map = new HashMap<Integer, Integer>();
			map.put(0, 0);
			session.setAttribute("optionMap", map);
//			
			ExamRoomAllotmentForCJCHandler.getInstance().getRemainingStudentsCount(request,objForm,session);
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			objForm.setErrorMessage(msg);
			objForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward("ajaxResponseForOptions");
	}
}
