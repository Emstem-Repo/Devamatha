package com.kp.cms.actions.exam;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
import com.kp.cms.forms.admin.CourseForm;
import com.kp.cms.forms.exam.ExamTimeTableForm;
import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.exam.ExamMarksEntryHandler;
import com.kp.cms.handlers.exam.ExamTimeTableHandler;
import com.kp.cms.handlers.examallotment.ExamRoomAllotmentCycleHandler;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.exam.ExamSubjectTimeTableTO;
import com.kp.cms.to.exam.ExamTimeTableTO;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.CurrentAcademicYear;

@SuppressWarnings("deprecation")
public class ExamTimeTableAction extends BaseDispatchAction {
	ExamTimeTableHandler handler = new ExamTimeTableHandler();
	ActionErrors errors = new ActionErrors();
	ActionMessages messages = new ActionMessages();

	public ActionForward initExamTimeTable(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamTimeTableForm objform = (ExamTimeTableForm) form;
		// new academic year parameter added by smitha to load exam name based on year and exam type
		int year=0;
		year=CurrentAcademicYear.getInstance().getAcademicyear();
		if(year==0){
			Calendar calendar = Calendar.getInstance();
			year = calendar.get(Calendar.YEAR);
		}
		objform.setYear(String.valueOf(year));
		objform.setExamType("1");
		objform.setExamName(null);
		objform.setExamTypeMap(handler.getExamType());
	//	objform.setMapExamName(new HashMap<Integer, String>());
		Map<Integer,String> examMap=CommonAjaxHandler.getInstance().getExamNameByExamTypeAndYear(objform.getExamType(),year); 
		objform.setExamNameMap(examMap);
		ExamMarksEntryHandler examhandler = new ExamMarksEntryHandler();
		String currentExam=examhandler.getCurrentExamName(objform.getExamType());
		if((objform.getExamName()==null || objform.getExamName().trim().isEmpty()) && currentExam!=null){
			objform.setExamName(currentExam);
		}
		objform.setProgramId(null);
		objform.setProgramTypeId(null);
		setProgramTypeListToRequest(request);
		setprogramMapToRequest(request, objform);
		return mapping.findForward(CMSConstants.EXAM_TIME_TABLE);
	}

//	private void setRequestToList(ExamTimeTableForm objEPCForm,
//			HttpServletRequest request) {
//		// get list of course name in form of ProgramType-Program-Course
//		objEPCForm.setExamTypeMap(handler.getExamType());
//	}

	public ActionForward goToSecondPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamTimeTableForm objEPCForm = (ExamTimeTableForm) form;
		errors = objEPCForm.validate(mapping, request);
		saveErrors(request, errors);
		setUserId(request, objEPCForm);
		if (errors.isEmpty()) {
   //Commented by manu.No Need to call the method in case of cancel
		//	handler.getMainList(objEPCForm.getExamTypeId(), objEPCForm.getExamNameId());

		}
		return mapping.findForward(CMSConstants.EXAM_TIME_TABLE_DISPLAY);
	}

	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamTimeTableForm objform = (ExamTimeTableForm) form;
		errors = objform.validate(mapping, request);
		saveErrors(request, errors);
		setUserId(request, objform);
		if (errors.isEmpty()) {
			int examNameId = Integer.parseInt(objform.getExamName());
			int examTypeId = Integer.parseInt(objform.getExamType());
			objform.setExamNameId(examNameId);
			objform.setExamTypeId(examTypeId);
			Map<Integer,String> examMap=CommonAjaxHandler.getInstance().getExamNameByExamTypeAndYear(objform.getExamType(),Integer.parseInt(objform.getYear())); 
			objform.setExamNameMap(examMap);
			objform.setExamName(objform.getExamNameMap().get(examNameId));
			ArrayList<ExamTimeTableTO> mainDisplayList = handler.getMainList(
					examTypeId, examNameId,objform);
			if (mainDisplayList != null) {
				objform.setMainList(mainDisplayList);
			}
			return mapping.findForward(CMSConstants.EXAM_TIME_TABLE_DISPLAY);
		}
		objform.setExamType(objform.getExamType());
		if (objform.getExamType() != null && objform.getExamType().length() > 0) {
			/*int examTypeId = Integer.parseInt(objform.getExamType());
			objform.setMapExamName(handler.getExamNameByExamTypeId(handler
					.getExamName(examTypeId)));*/
			objform.setExamName(objform.getExamName());
		}
		//added by smitha
		if(objform.getYear()!=null && !objform.getYear().trim().isEmpty() && objform.getExamType()!=null && !objform.getExamType().trim().isEmpty()){
			Map<Integer,String> examMap=CommonAjaxHandler.getInstance().getExamNameByExamTypeAndYear(objform.getExamType(),Integer.parseInt(objform.getYear())); 
			objform.setExamNameMap(examMap);
			}
		objform.setExamTypeMap(handler.getExamType());
		setProgramTypeListToRequest(request);
		setprogramMapToRequest(request, objform);
		return mapping.findForward(CMSConstants.EXAM_TIME_TABLE);
	}

	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamTimeTableForm objEPCForm = (ExamTimeTableForm) form;
		errors = objEPCForm.validate(mapping, request);
		setUserId(request, objEPCForm);
		objEPCForm.setExamName(objEPCForm.getExamName());
		objEPCForm.setExamNameId(objEPCForm.getExamNameId());
		objEPCForm.setExamTypeId(objEPCForm.getExamTypeId());
		String id = request.getParameter("id");
		if (id != null) {
			objEPCForm.setId(Integer.parseInt(id));
			ExamTimeTableTO subjects = handler
					.getSubjects(Integer.parseInt(id),objEPCForm.getYear(),objEPCForm.getExamTypeId(),objEPCForm.getJoiningBatch());
			if (subjects != null) {
				objEPCForm.setExamTimeTableTO(subjects);

			}
			Map<Integer, String> sessionMap = ExamRoomAllotmentCycleHandler.getInstance().getExamSessionMap();
			objEPCForm.setSessionMap(sessionMap);
		}

		return mapping.findForward(CMSConstants.EXAM_TIME_TABLE_EDIT);
	}

	public ActionForward update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		ExamTimeTableForm objform = (ExamTimeTableForm) form;

		setUserId(request, objform);
		errors.clear();
		messages.clear();
		ExamTimeTableTO t = objform.getExamTimeTableTO();
		if(isCancelled(request)){
			objform.setExamName(objform.getExamName());
			objform.setExamNameId(objform.getExamNameId());
			objform.setExamTypeId(objform.getExamTypeId());
			String id = request.getParameter("id");
			if (id != null) {
				objform.setId(Integer.parseInt(id));
				ExamTimeTableTO subjects = handler
				.getSubjects(Integer.parseInt(id),objform.getYear(),objform.getExamTypeId(),objform.getJoiningBatch());
				if (subjects != null) {
					objform.setExamTimeTableTO(subjects);
				}
			}
			return mapping.findForward(CMSConstants.EXAM_TIME_TABLE_EDIT);
		}

		//errors = validateData(t.getListSubjects());
		saveErrors(request, errors);
		if (errors.isEmpty()) {
			handler.insert_update(t);
			ActionMessage message = new ActionMessage(
					"knowledgepro.exam.attendanceMarks.addsuccess", "");
			messages.add("messages", message);
			saveMessages(request, messages);
			objform.setExamType(null);
			//objform.setExamName(null);
	//		objform.setMapExamName(new HashMap<Integer, String>());
		if(objform.getYear()!=null && !objform.getYear().trim().isEmpty() && objform.getExamType()!=null && !objform.getExamType().trim().isEmpty()){
			Map<Integer,String> examMap=CommonAjaxHandler.getInstance().getExamNameByExamTypeAndYear(objform.getExamType(),Integer.parseInt(objform.getYear())); 
			objform.setExamNameMap(examMap);
		}
			objform.setExamTypeMap(handler.getExamType());
			return mapping.findForward(CMSConstants.EXAM_TIME_TABLE_DISPLAY);
		}
		return mapping.findForward(CMSConstants.EXAM_TIME_TABLE_EDIT);
	}

	private ActionErrors validateData(
			ArrayList<ExamSubjectTimeTableTO> listSubjects) {
		int startTimeHour = 0;
		int startTimeMin = 0;
		int endTimeHour = 0;
		int endTimeMin = 0;
		int flag = 0;
		boolean flagType = true;
		boolean sessionErr = false;
		for (ExamSubjectTimeTableTO to : listSubjects) {
			// Validate Date
			flagType = true;
			if (to.getDate() != null && to.getDate().trim().length() > 0) {
				if (!CommonUtil.isValidDate(to.getDate())) {
					errors.add("error", new ActionError(
							"knowledgepro.exam.ExamTimeTable.InvalidDate"));
					flag = 1;
				} 
				if(to.getSessionId() == null || to.getSessionId().isEmpty()) {
					if(!sessionErr)
						errors.add("error", new ActionError("knowledgepro.exam.ExamTimeTable.session.required"));
					sessionErr = true;
				}
//				else {
//
//					String formattedString = CommonUtil
//							.ConvertStringToDateFormat(to.getDate(),
//									"dd/MM/yyyy", "MM/dd/yyyy");
//					Date date = new Date(formattedString);
//					Date curdate = new Date();
//					Calendar cal = Calendar.getInstance();
//					cal.setTime(curdate);
//					cal.set(Calendar.HOUR_OF_DAY, 0);
//					cal.set(Calendar.MINUTE, 0);
//					cal.set(Calendar.SECOND, 0);
//					cal.set(Calendar.MILLISECOND, 0);
//					Date origdate = cal.getTime();
//
//					if (date.compareTo(origdate) < 0) {
//						errors
//								.add(
//										"error",
//										new ActionError(
//												"knowledgepro.exam.footerAggreement.date.currDate"));
//						flag = 1;
//					}
//
//				}

				// validate individual time components

				if (to.getStartTimeHour() != null
						&& to.getStartTimeHour().trim().length() > 0) {
					startTimeHour = Integer.parseInt(to.getStartTimeHour());

				} else {
					errors.add("error", new ActionError(
							"knowledgepro.exam.ExamTimeTable.emptyStartHour"));
					flag = 1;
					flagType = false;
				}

				if (to.getStartTimeMin() != null
						&& to.getStartTimeMin().trim().length() > 0) {
					startTimeMin = Integer.parseInt(to.getStartTimeMin());

				} else {
					errors.add("error", new ActionError(
							"knowledgepro.exam.ExamTimeTable.emptyEndHour"));
					flag = 1;
					flagType = false;
				}
				if (to.getEndTimeHour() != null
						&& to.getEndTimeHour().trim().length() > 0) {
					endTimeHour = Integer.parseInt(to.getEndTimeHour());

				} else {
					errors.add("error", new ActionError(
							"knowledgepro.exam.ExamTimeTable.emptyStartMin"));
					flag = 1;
					flagType = false;
				}

				if (to.getEndTimeMin() != null
						&& to.getEndTimeMin().trim().length() > 0) {
					endTimeMin = Integer.parseInt(to.getEndTimeMin());

				} else {
					errors.add("error", new ActionError(
							"knowledgepro.exam.ExamTimeTable.emptyEndMin"));
					flag = 1;
					flagType = false;
				}

				if (startTimeHour > 24 || endTimeHour > 24) {
					errors.add("error", new ActionError(
							"knowledgepro.exam.ExamTimeTable.Hour"));
					flag = 1;
				}

				if (startTimeMin > 60 || endTimeMin > 60) {
					errors.add("error", new ActionError(
							"knowledgepro.exam.ExamTimeTable.Min"));

					flag = 1;
				}
				// validate if start time > end time
				if (startTimeHour == endTimeHour && startTimeMin >= endTimeMin) {
					errors.add("error", new ActionError(
							"knowledgepro.exam.ExamTimeTable.validTime"));
					flag = 1;
				} else if (getTimeDiff(startTimeHour, startTimeMin,
						endTimeHour, endTimeMin) < 0
						&& flagType) {
					errors.add("error", new ActionError(
							"knowledgepro.exam.ExamTimeTable.validTime"));

					flag = 1;
				}
				startTimeHour = 0;
				startTimeMin = 0;
				endTimeHour = 0;
				endTimeMin = 0;
			} else {
				if ((to.getStartTimeHour() != null && to.getStartTimeHour()
						.trim().length() > 0)
						|| (to.getEndTimeHour() != null && to.getEndTimeHour()
								.trim().length() > 0)
						|| (to.getStartTimeMin() != null && to
								.getStartTimeMin().trim().length() > 0)
						|| (to.getEndTimeMin() != null && to.getEndTimeMin()
								.trim().length() > 0)) {
					errors
							.add(
									"error",
									new ActionError(
											"knowledgepro.exam.ExamTimeTable.forSelectedTimeField"));
					flag = 1;
				}
			}
			if (flag == 1) {
				break;
			}

		}

		return errors;
	}

	/**
	 * @param srcHrs
	 * @param srsMins
	 * @param destHrs
	 * @param destMins
	 * @return
	 */
	public int getTimeDiff(int srcHrs, int srsMins, int destHrs, int destMins) {
		int diff = -1;


		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, srcHrs);
		cal.set(Calendar.MINUTE, srsMins);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		Date source = cal.getTime();

		cal.set(Calendar.HOUR_OF_DAY, destHrs);
		cal.set(Calendar.MINUTE, destMins);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		Date dest = cal.getTime();
		diff = (int) ((dest.getTime() - source.getTime()) / 3600000);

		return diff;
	}
	
	/**
	 * 
	 * @param request
	 * This method sets the program type list to Request useful in
	 * populating in program type selection.
	 * @throws Exception
	 */
	public void setProgramTypeListToRequest(HttpServletRequest request)	throws Exception {
		List<ProgramTypeTO> programTypeList = ProgramTypeHandler.getInstance().getProgramType();
		request.setAttribute("programTypeList", programTypeList);
	}

	/**
	 * using to load progams in the UI
	 * @param request
	 * @param objform
	 */
	public void setprogramMapToRequest(HttpServletRequest request, ExamTimeTableForm objform) {
		if (objform.getProgramTypeId() != null
				&& !(objform.getProgramTypeId().isEmpty())) {
			Map<Integer, String> programMap = CommonAjaxHandler.getInstance().getProgramsByProgramType(Integer.parseInt(objform.getProgramTypeId()));
			request.setAttribute("programMap", programMap);
			request.setAttribute("courseOperation", "validation");
		}
	}
}