package com.kp.cms.actions.exam;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.exam.ExamAssignStudentsToRoomForm;
import com.kp.cms.handlers.exam.ExamAssignStudentsToRoomHandler;
import com.kp.cms.handlers.exam.ExamTimeTableHandler;
import com.kp.cms.to.exam.ExamAssignStudentsToRoomTO;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.CurrentAcademicYear;

@SuppressWarnings("deprecation")
public class ExamAssignStudentsToRoomAction extends BaseDispatchAction {

	ActionErrors errors = new ActionErrors();
	ActionMessages messages = new ActionMessages();

	ExamAssignStudentsToRoomHandler handler = new ExamAssignStudentsToRoomHandler();

	// gets initial list of Exam Definition
	public ActionForward initExamAssignStudentsToRoom(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamAssignStudentsToRoomForm objform = (ExamAssignStudentsToRoomForm) form;
		objform.resetFields();
		setRequiredDataToForm(objform);
		objform.setListExamName(new HashMap<Integer, String>());
		objform.setListRoom(handler.getRoomNames());
		objform.setExamTypeList((HashMap<Integer, String>) handler
				.getExamTypeList());
		return mapping.findForward(CMSConstants.EXAM_ASSIGN_STUDENT_TO_ROOM);
	}
// new addition by Smitha to add academic year filter in input
	private void setRequiredDataToForm(ExamAssignStudentsToRoomForm objform) throws Exception {
		int year=0;
		year=CurrentAcademicYear.getInstance().getAcademicyear();
		if(objform.getYear()!=null && !objform.getYear().isEmpty()){
			year=Integer.parseInt(objform.getYear());
		}
		if(year==0){
			Calendar calendar = Calendar.getInstance();
			year = calendar.get(Calendar.YEAR);
		}
		
	}
//ends
	public ActionForward Reset(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamAssignStudentsToRoomForm objform = (ExamAssignStudentsToRoomForm) form;
		errors.clear();
		messages.clear();
		objform.setListRoom(handler.getRoomNames());
		objform.setExamTypeList((HashMap<Integer, String>) handler
				.getExamTypeList());
		objform.resetFields();
		return mapping.findForward(CMSConstants.EXAM_ASSIGN_STUDENT_TO_ROOM);
	}

	public ActionForward Search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamAssignStudentsToRoomForm objform = (ExamAssignStudentsToRoomForm) form;
		errors.clear();
		messages.clear();
		errors = objform.validate(mapping, request);
		saveErrors(request, errors);
		setUserId(request, objform);
		if (errors.isEmpty()) {
			errors = validateTime(objform.getDate(), objform.getHr(), objform
					.getMin(), Integer.parseInt(objform.getExamNameId()));
		}

		saveErrors(request, errors);
		if (errors.isEmpty()) {
			int roomId = 0;
			String roomNo = "";
			if (objform.getRoomId() != null && objform.getRoomId().length() > 0) {
				roomId = Integer.parseInt(objform.getRoomId());
				roomNo = handler.getRoomNoByRoomId(roomId);
			}
			int examTypeId = Integer.parseInt(objform.getExamType());
			int examId = Integer.parseInt(objform.getExamNameId());
			String date = objform.getDate();
			String hr = objform.getHr();
			String min = objform.getMin();
			ArrayList<ExamAssignStudentsToRoomTO> listRoomDetails = handler
					.getDetails(Integer.parseInt(objform.getExamType()),
							Integer.parseInt(objform.getExamNameId()), objform
									.getDate(), objform.getHr(), objform
									.getMin(), roomId);
			objform.setListRoomDetails(listRoomDetails);

			objform.setExamType(Integer.toString(examTypeId));
			objform.setExamNameId(Integer.toString(examId));
			objform.setDate(date);
			objform.setHr(hr);
			objform.setMin(min);
			objform.setNonEligible(objform.getNonEligible());
			objform.setDisplayOrder(objform.getDisplayOrder());
			objform.setRoomNo(roomNo);
			objform.setRoomId(Integer.toString(roomId));
			if (listRoomDetails.size() != 0) {
				return mapping
						.findForward(CMSConstants.EXAM_ASSIGN_STUDENT_TO_ROOM_SEARCH);
			} else {
				objform.resetPage(mapping, request);
				objform.setListRoom(handler.getRoomNames());
				objform.setExamTypeList((HashMap<Integer, String>) handler
						.getExamTypeList());
				ActionMessage message = new ActionMessage(
						"knowledgepro.exam.assignStudentsToRoom.noRoom", "");
				messages.add("messages", message);
				saveMessages(request, messages);
				saveErrors(request, errors);

				return mapping
						.findForward(CMSConstants.EXAM_ASSIGN_STUDENT_TO_ROOM);
			}

		}
		if (objform.getExamType() != null && objform.getExamType().length() > 0) {
			ExamTimeTableHandler h = new ExamTimeTableHandler();
			objform.setListExamName(handler.getExamNameByExamTypeId(h
					.getExamName(Integer.parseInt(objform.getExamType()))));
			if (objform.getExamNameId() != null
					&& objform.getExamNameId().length() > 0) {
				objform.setExamNameId(objform.getExamNameId());
			}

		}
		objform.setListRoom(handler.getRoomNames());
		objform.setExamTypeList((HashMap<Integer, String>) handler
				.getExamTypeList());

		return mapping.findForward(CMSConstants.EXAM_ASSIGN_STUDENT_TO_ROOM);
	}

	public ActionForward Add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamAssignStudentsToRoomForm objform = (ExamAssignStudentsToRoomForm) form;

		errors.clear();
		messages.clear();
		errors = objform.validate(mapping, request);
		saveErrors(request, errors);
		if (objform.getRoomId() == null || objform.getRoomId().length() == 0) {
			errors.add("error", new ActionError(
					"knowledgepro.exam.assignStudentsToRoom.roomNoReq"));
		}
		saveErrors(request, errors);
		if (errors.isEmpty()) {
			errors = validateTime(objform.getDate(), objform.getHr(), objform
					.getMin(), Integer.parseInt(objform.getExamNameId()));
		}
		saveErrors(request, errors);
		setUserId(request, objform);
		if (errors.isEmpty()) {

			try {
				objform = handler.add(objform);

			} catch (Exception e) {
				e.printStackTrace();
				errors
						.add(
								"error",
								new ActionError(
										"knowledgepro.exam.assignStudentsToRoom.searchFailure"));
				saveErrors(request, errors);
			}
			return mapping
					.findForward(CMSConstants.EXAM_ASSIGN_STUDENT_TO_ROOM_ADD);
		}

		if (objform.getExamType() != null && objform.getExamType().length() > 0) {
			ExamTimeTableHandler h = new ExamTimeTableHandler();
			objform.setListExamName(handler.getExamNameByExamTypeId(h
					.getExamName(Integer.parseInt(objform.getExamType()))));
			if (objform.getExamNameId() != null
					&& objform.getExamNameId().length() > 0) {
				objform.setExamNameId(objform.getExamNameId());
			}

		}
		objform.setListRoom(handler.getRoomNames());
		objform.setExamTypeList((HashMap<Integer, String>) handler
				.getExamTypeList());
		return mapping.findForward(CMSConstants.EXAM_ASSIGN_STUDENT_TO_ROOM);
	}

	public ActionForward deleteExaminars(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamAssignStudentsToRoomForm objform = (ExamAssignStudentsToRoomForm) form;
		errors.clear();
		messages.clear();
		handler.deleteExaminars(objform.getId());
		objform = handler.add(objform);
		ActionMessage message = new ActionMessage(
				"knowledgepro.exam.assignStudentsToRoom.deletesuccess", "");
		messages.add("messages", message);
		saveMessages(request, messages);
		saveErrors(request, errors);

		return mapping
				.findForward(CMSConstants.EXAM_ASSIGN_STUDENT_TO_ROOM_ADD);
	}

	public ActionForward deleteRooms(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamAssignStudentsToRoomForm objform = (ExamAssignStudentsToRoomForm) form;
		errors.clear();
		messages.clear();
		handler.deleteRooms(objform.getId());
		ActionMessage message = new ActionMessage(
				"knowledgepro.exam.assignStudentsToRoom.deletesuccess", "");
		messages.add("messages", message);
		saveMessages(request, messages);
		saveErrors(request, errors);

		int roomno = 0;
		int examTypeId = Integer.parseInt(objform.getExamType());
		int examId = Integer.parseInt(objform.getExamNameId());
		String date = objform.getDate();
		String hr = objform.getHr();
		String min = objform.getMin();
		ArrayList<ExamAssignStudentsToRoomTO> listRoomDetails = handler
				.getDetails(Integer.parseInt(objform.getExamType()), Integer
						.parseInt(objform.getExamNameId()), objform.getDate(),
						objform.getHr(), objform.getMin(), roomno);
		objform.setListRoomDetails(listRoomDetails);

		objform.setExamType(Integer.toString(examTypeId));
		objform.setExamNameId(Integer.toString(examId));
		objform.setDate(date);
		objform.setHr(hr);
		objform.setMin(min);
		objform.setNonEligible(objform.getNonEligible());
		objform.setDisplayOrder(objform.getDisplayOrder());
		objform.setRoomId(objform.getRoomId());

		if (listRoomDetails.size() != 0) {
			return mapping
					.findForward(CMSConstants.EXAM_ASSIGN_STUDENT_TO_ROOM_SEARCH);
		} else {
			objform.resetPage(mapping, request);
			objform.setListRoom(handler.getRoomNames());
			objform.setExamTypeList((HashMap<Integer, String>) handler
					.getExamTypeList());
			message = new ActionMessage(
					"knowledgepro.exam.assignStudentsToRoom.noRoom", "");
			messages.add("messages", message);
			saveMessages(request, messages);
			saveErrors(request, errors);

			return mapping
					.findForward(CMSConstants.EXAM_ASSIGN_STUDENT_TO_ROOM);
		}
	}

	public ActionForward deleteStudents(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamAssignStudentsToRoomForm objform = (ExamAssignStudentsToRoomForm) form;
		errors.clear();
		messages.clear();

		handler.deleteStudents(objform.getId(), objform.getAlloted(), Integer
				.parseInt(objform.getRoomId()));

		objform = handler.add(objform);
		ActionMessage message = new ActionMessage(
				"knowledgepro.exam.assignStudentsToRoom.deletesuccess", "");
		messages.add("messages", message);
		saveMessages(request, messages);
		saveErrors(request, errors);

		return mapping
				.findForward(CMSConstants.EXAM_ASSIGN_STUDENT_TO_ROOM_ADD);
	}

	public ActionForward addMarkAttendance(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamAssignStudentsToRoomForm objform = (ExamAssignStudentsToRoomForm) form;
		errors.clear();
		messages.clear();
		String date = objform.getDate();
		String hr = objform.getHr();
		String min = objform.getMin();
		objform.setDate(date);
		objform.setTime(hr + ":" + min);
		int roomId = Integer.parseInt(objform.getRoomId());
		String roomNo = handler.getRoomNoByRoomId(roomId);

		int examId = Integer.parseInt(objform.getExamNameId());

		objform.setListInvigilator(handler.getInvigilatorList(examId,
				CommonUtil.ConvertStringToSQLDateTime(date + " " + hr + ":"
						+ min + ":00"), roomNo));

		List<ExamAssignStudentsToRoomTO> list1 = handler
				.getStudentsList(examId, CommonUtil
						.ConvertStringToSQLDateTime(date + " " + hr + ":" + min
								+ ":00"), objform.getDisplayOrder(), roomId);

		objform.setListStudentsSize(list1.size());
		objform.setListStudents(list1);
		objform.setRoomNo(roomNo);
		return mapping
				.findForward(CMSConstants.EXAM_ASSIGN_STUDENT_TO_ROOM_ADD_MARK_ATT);
	}

	public ActionForward addStudents(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamAssignStudentsToRoomForm objform = (ExamAssignStudentsToRoomForm) form;
		errors.clear();
		messages.clear();
		errors = validateError(objform.getClassId(), objform.getSubjectId());
		saveErrors(request, errors);
		objform.setNoOfStudents(null);
		if (errors.isEmpty()) {
			int examId = Integer.parseInt(objform.getExamNameId());
			String date = objform.getDate();
			String hr = objform.getHr();
			String min = objform.getMin();
			ArrayList<Integer> listClass = new ArrayList<Integer>();
			ArrayList<Integer> listSubjects = new ArrayList<Integer>();

			String[] s1 = objform.getClassId().split(",");
			for (String string : s1) {
				listClass.add(Integer.parseInt(string));

			}

			String[] s2 = objform.getSubjectId().split(",");

			for (String string : s2) {
				listSubjects.add(Integer.parseInt(string));
			}

			objform.setExamName(handler.getExamNameList(examId));
			objform.setDate(date);
			objform.setTime(hr + ":" + min);
			int Av = objform.getRoomCapacity()
					- handler.getRoomAlloted(examId, Integer.parseInt(objform
							.getRoomId()), CommonUtil
							.ConvertStringToSQLDateTime(date + " " + hr + ":"
									+ min + ":00"));
			objform.setAvailable(Av);

			objform.setListStudentsSize(0);
			return mapping
					.findForward(CMSConstants.EXAM_ASSIGN_STUDENT_TO_ROOM_ADD_STUDENTS);

		} else {
			objform = handler.add(objform);
			return mapping
					.findForward(CMSConstants.EXAM_ASSIGN_STUDENT_TO_ROOM_ADD);
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
	public ActionForward getStudents(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamAssignStudentsToRoomForm objform = (ExamAssignStudentsToRoomForm) form;
		errors.clear();
		messages.clear();
		errors = validateErrorStudents(objform.getAvailable(), objform
				.getNoOfStudents());
		saveErrors(request, errors);

		if (errors.isEmpty()) {
			int examId = Integer.parseInt(objform.getExamNameId());
			String date = objform.getDate();
			String hr = objform.getHr();
			String min = objform.getMin();
			ArrayList<Integer> listClass = new ArrayList<Integer>();
			ArrayList<Integer> listSubjects = new ArrayList<Integer>();

			String[] s1 = objform.getClassId().split(",");
			for (String string : s1) {
				listClass.add(Integer.parseInt(string));

			}

			String[] s2 = objform.getSubjectId().split(",");

			for (String string : s2) {
				listSubjects.add(Integer.parseInt(string));
			}

			objform.setExamName(handler.getExamNameList(examId));
			objform.setDate(date);
			objform.setTime(hr + ":" + min);
			// Code Written by 
			List<ExamAssignStudentsToRoomTO> list=null;
			if(objform.getExamType()!=null && (objform.getExamType().equalsIgnoreCase("3") || objform.getExamType().equalsIgnoreCase("6"))){
				list=handler.getStudentListForExam(listSubjects, Integer
						.parseInt(objform.getNoOfStudents()), objform
						.getNonEligible(), listClass, objform
						.getDisplayOrder(), examId,date,hr,min,objform.getType());
			}else{
				list = handler
				.getStudentList_forSubjects(listSubjects, Integer
						.parseInt(objform.getNoOfStudents()), objform
						.getNonEligible(), listClass, objform
						.getDisplayOrder(), examId,date,hr,min);
				if(objform.getExamType()!=null && objform.getExamType().equalsIgnoreCase("2")){
					List<ExamAssignStudentsToRoomTO> supList=handler.getStudentListForExam(listSubjects, Integer
							.parseInt(objform.getNoOfStudents()), objform
							.getNonEligible(), listClass, objform
							.getDisplayOrder(), examId,date,hr,min,objform.getType());
					if(supList!=null){
						list.addAll(supList);
					}
				}
			}
			
			objform.setListStudents(list);

			objform.setListStudentsSize(list.size());

			if (list.size() == 0) {
				ActionMessage message = new ActionMessage(
						"knowledgepro.exam.assignStudentsToRoom.NoStudents", "");
				messages.add("messages", message);
				saveMessages(request, messages);
				saveErrors(request, errors);
			} else {
				request.setAttribute("examAssignOperation", "edit");
			}

		} else {
			objform.setListStudentsSize(0);
		}
		return mapping
				.findForward(CMSConstants.EXAM_ASSIGN_STUDENT_TO_ROOM_ADD_STUDENTS);

	}

	private ActionErrors validateTime(String dateValue, String hr, String min,
			int examId) throws Exception {

		/*String formattedString = CommonUtil.ConvertStringToDateFormat(
				dateValue, "dd/MM/yyyy", "MM/dd/yyyy");*/
		Date curdate = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(curdate);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		/*if (date.compareTo(origdate) < 0) {
			errors.add("error", new ActionError(
					"knowledgepro.exam.assignStudentsToRoom.dateTime",
					dateValue, cdate));
		}*/

		if (errors != null && errors.isEmpty()) {
			if (!handler.isDateTimeValid(CommonUtil
					.ConvertStringToSQLDateTime(dateValue + " " + hr + ":"
							+ min + ":00"), examId)) {
				errors.add("error", new ActionError(
						"knowledgepro.exam.assignExaminer.dateTime.invalid"));
			}
		}

		return errors;
	}

	// private String getDate(String date, int hr, int min) {
	// String dateValue = CommonUtil.ConvertStringToDateFormat(date + " " + hr
	// + ":" + min + ":00", "dd/MM/yyyy hh:mm:ss",
	// "yyyy-MM-dd k:mm:ss");
	// if (hr > 12) {
	// dateValue = dateValue + ".1";
	// } else {
	// dateValue = dateValue + ".0";
	// }
	//
	// return dateValue;
	// }

	public ActionForward updateMarkAttendance(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamAssignStudentsToRoomForm objform = (ExamAssignStudentsToRoomForm) form;
		errors.clear();
		messages.clear();

		handler.updateMarkAttendance(objform.getListInvigilator(), objform
				.getListStudents());

		objform.setListRoom(handler.getRoomNames());
		objform.setExamTypeList((HashMap<Integer, String>) handler
				.getExamTypeList());

		ActionMessage message = new ActionMessage(
				"knowledgepro.exam.assignStudentsToRoom.updatesuccess", "");
		messages.add("messages", message);
		saveMessages(request, messages);
		saveErrors(request, errors);

		objform.setExamType("");
		objform.setDate("");
		objform.setHr("00");
		objform.setMin("00");
		objform.setRoomId("");
		objform.setDisplayOrder("");
		objform.setExamNameId("");
		objform.setNonEligible("off");
		objform.setNoOfStudents("");

		return mapping.findForward(CMSConstants.EXAM_ASSIGN_STUDENT_TO_ROOM);
	}

	public ActionForward addStudentsToRoom(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamAssignStudentsToRoomForm objform = (ExamAssignStudentsToRoomForm) form;
		errors.clear();
		messages.clear();
		String hr = objform.getHr();
		String min = objform.getMin();
		setUserId(request, objform);

		boolean flag = handler.addStudentsToRoom(objform.getListStudents(),
				Integer.parseInt(objform.getExamNameId()), Integer
						.parseInt(objform.getRoomId()), handler.getDate(objform
						.getDate(), hr, min), getDateFormat(objform.getDate(),
						hr, min), objform.getUserId(), objform.getAlloted());
		if (flag) {
			ActionMessage message = new ActionMessage(
					"knowledgepro.exam.assignStudentsToRoom.addeduccess", "");
			messages.add("messages", message);
			saveMessages(request, messages);
			saveErrors(request, errors);
		}

//		objform.setListRoom(handler.getRoomNames());
//		objform.setExamTypeList((HashMap<Integer, String>) handler
//				.getExamTypeList());
//		objform.setExamType("");
//		objform.setDate("");
//		objform.setRoomId("");
//		objform.setDisplayOrder("");
//		objform.setExamNameId("");
//		objform.setNonEligible("off");
//		objform.setNoOfStudents("");
//		objform.setHr("00");
//		objform.setMin("00");
		objform = handler.add(objform);
		return mapping.findForward(CMSConstants.EXAM_ASSIGN_STUDENT_TO_ROOM_ADD);
	}

	public Date getDateFormat(String date, String hr, String min) {
		String dateTimeStr = date + " " + hr + ":" + min + ":00";
		String formatDate = CommonUtil.ConvertStringToDateFormat(dateTimeStr,
				"dd/M/yyyy hh:mm:ss", "M/d/yyyy h:mm:ss a");

		Date newdate = new Date(formatDate);
		return newdate;
	}

	private ActionErrors validateErrorStudents(int available,
			String noOfStudents) {
		if (noOfStudents == null || noOfStudents.length() == 0) {
			errors.add("error", new ActionError(
					"knowledgepro.exam.assignStudentsToRoom.NoofStudents"));
		}
		if (noOfStudents != null && noOfStudents.length() > 0) {
			if (available < Integer.parseInt(noOfStudents)) {
				errors.add("error", new ActionError(
						"knowledgepro.exam.assignStudentsToRoom.errors"));
			}
		}

		return errors;
	}

	private ActionErrors validateError(String classId, String subjectId) {
		if (classId == null || classId.length() == 0) {
			errors.add("error", new ActionError(
					"admissionFormForm.class.required"));
		}
		if (subjectId == null || subjectId.length() == 0) {
			errors.add("error", new ActionError(
					"admissionFormForm.subject.required"));
		}

		return errors;
	}

}
