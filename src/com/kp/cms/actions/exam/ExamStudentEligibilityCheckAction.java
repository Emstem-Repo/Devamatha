package com.kp.cms.actions.exam;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
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
import com.kp.cms.forms.exam.ExamStudentEligibilityCheckForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.exam.ExamAssignStudentsToRoomHandler;
import com.kp.cms.handlers.exam.ExamMarksEntryHandler;
import com.kp.cms.handlers.exam.ExamStudentEligibilityCheckHandler;
import com.kp.cms.to.exam.ExamExamEligibilityTO;
import com.kp.cms.to.exam.ExamStudentEligibilityCheckTO;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.utilities.CurrentAcademicYear;

@SuppressWarnings("deprecation")
public class ExamStudentEligibilityCheckAction extends BaseDispatchAction {
	ActionErrors errors = new ActionErrors();
	ActionMessages messages = new ActionMessages();
	ExamStudentEligibilityCheckHandler handler = new ExamStudentEligibilityCheckHandler();

	public ActionForward initStudentEligibilityCheck(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamStudentEligibilityCheckForm objform = (ExamStudentEligibilityCheckForm) form;
		objform.clearPage(mapping, request);
		setRequestToList(objform, request);
		return mapping.findForward(CMSConstants.EXAM_STUDENT_ELEGIBILETY_CHECK);
	}

	private void setRequestToList(ExamStudentEligibilityCheckForm objform,
			HttpServletRequest request) throws Exception {
	//	objform.setListExamName(handler.getExamNameList());
		int year=0;
		year=CurrentAcademicYear.getInstance().getAcademicyear();
		if(objform.getYear()!=null && !objform.getYear().isEmpty()){
			year=Integer.parseInt(objform.getYear());
		}
		if(year==0){
			Calendar calendar = Calendar.getInstance();
			year = calendar.get(Calendar.YEAR);
		}
		objform.setListExamType(handler.getListExamType());
		if(objform.getExamType()==null || objform.getExamType().trim().isEmpty())
		objform.setExamType("1");
		
		Map<Integer,String> examMap=CommonAjaxHandler.getInstance().getExamNameByExamTypeAndYear(objform.getExamType(),year); 
		objform.setExamNameMap(examMap);
		
		ExamMarksEntryHandler examhandler = new ExamMarksEntryHandler();
		String currentExam=examhandler.getCurrentExamName(objform.getExamType());
		if((objform.getExamName()==null || objform.getExamName().trim().isEmpty()) && currentExam!=null){
			objform.setExamName(currentExam);
		}
		
	}

	public ActionForward getStudentEligibilityCheck(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamStudentEligibilityCheckForm objform = (ExamStudentEligibilityCheckForm) form;
		errors.clear();
		messages.clear();
		errors = objform.validate(mapping, request);
		saveErrors(request, errors);
		setUserId(request, objform);
		if (errors.isEmpty()) {
			String a = objform.getClassValues();
			String[] str = a.split(",");
			ArrayList<Integer> listClass = new ArrayList<Integer>();
			for (int x = 0; x < str.length; x++) {
				if (str[x] != null && str[x].length() > 0) {
					listClass.add(Integer.parseInt(str[x]));
				}
			}
			int examId = Integer.parseInt(objform.getExamName());
			int examTypeId = Integer.parseInt(objform.getExamType());
			objform.setExamId(examId);
			objform.setExamTypeId(examTypeId);
			char display = 0;
			if (objform.getDisplayFor() != null
					&& objform.getDisplayFor().trim().length() > 0) {
				display = objform.getDisplayFor().charAt(0);
			}
			ArrayList<KeyValueTO> listAddEligibility = handler
					.getAddEligibility(listClass, examTypeId);
			ArrayList<ExamStudentEligibilityCheckTO> list = handler
					.getStudentList(listClass, examId, examTypeId, display);
			if (list.size() > 0) {
				objform.setListStudent(list);
				objform.setExamName(handler.getExamNameByExamId(examId));
				objform
						.setExamType(handler
								.getExamTypeByExamTypeId(examTypeId));
				objform.setListAddEligibility(listAddEligibility);
				if (listAddEligibility != null && listAddEligibility.size() > 0) {
					objform.setRowCount(Integer.toString(listAddEligibility
							.size()));
				}
				objform = handler.getUpdatableForm(objform);
				return mapping
						.findForward(CMSConstants.EXAM_STUDENT_ELEGIBILETY_CHECK_DISPLAY);
			} else {
				ActionMessage message = new ActionMessage(
						"knowledgepro.exam.examEligibilitySetUp.error", "");
				messages.add("messages", message);
				saveMessages(request, messages);
				objform.clearPage(mapping, request);
				setRequestToList(objform, request);
				return mapping
						.findForward(CMSConstants.EXAM_STUDENT_ELEGIBILETY_CHECK);
			}

		} else {
			objform.clearPage(mapping, request);
			setRequestToList(objform, request);
			return mapping
					.findForward(CMSConstants.EXAM_STUDENT_ELEGIBILETY_CHECK);
		}

	}

	public ActionForward updateStudentEligibilityCheck(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamStudentEligibilityCheckForm objform = (ExamStudentEligibilityCheckForm) form;
		errors.clear();
		errors = validateData(objform);
		saveErrors(request, errors);
		if (errors.isEmpty()) {
			handler.updateStudentElegibility(objform);
		} else {
			return mapping
					.findForward(CMSConstants.EXAM_STUDENT_ELEGIBILETY_CHECK_DISPLAY);
		}
		objform.clearPage(mapping, request);
		setRequestToList(objform, request);
		return mapping.findForward(CMSConstants.EXAM_STUDENT_ELEGIBILETY_CHECK);
	}

	private ActionErrors validateData(ExamStudentEligibilityCheckForm objform) {
		ArrayList<ExamStudentEligibilityCheckTO> listStudent = objform
				.getListStudent();
		boolean flag = true;
		for (ExamStudentEligibilityCheckTO objTo : listStudent) {

			if (objTo.getCheckExam()) {
				if (objTo.getExamElgibility() == null) {
					if (!(objTo.getRemarks() != null && objTo.getRemarks()
							.trim().length() > 0)) {
						errors.add("error", new ActionError(
								"knowledgepro.inventory.remarks.required"));
						flag = false;
						break;
					}
				}
			} else if (objTo.getExamElgibility() != null) {
				if (!(objTo.getRemarks() != null && objTo.getRemarks().trim()
						.length() > 0)) {
					errors.add("error", new ActionError(
							"knowledgepro.inventory.remarks.required"));
					flag = false;
					break;

				}
			}
		}

		if (!flag) {
			for (ExamStudentEligibilityCheckTO objTo : listStudent) {
				if (objTo.getCourseFees() != null) {
					objTo.setDummycourseFees(true);
					objTo.setCourseFees(null);
				} else {
					objTo.setDummycourseFees(false);
				}
				if (objTo.getExamFees() != null) {
					objTo.setDummyexamFees(true);
					objTo.setExamFees(null);
				} else {
					objTo.setDummyexamFees(false);
				}
				if (objTo.getAttendance() != null) {
					objTo.setDummyattendance(true);
					objTo.setAttendance(null);
				} else {
					objTo.setDummyattendance(false);
				}
				if (objTo.getExamElgibility() != null) {
					objTo.setDummyexamElgibility(true);
					objTo.setExamElgibility(null);
				} else {
					objTo.setDummyexamElgibility(false);
				}
				ArrayList<ExamExamEligibilityTO> elgibilityList = objTo
						.getListOfEligibility();
				for (ExamExamEligibilityTO to : elgibilityList) {
					if (to.getDisplay() != null
							&& to.getDisplay().equalsIgnoreCase("on")) {
						to.setDummyAdditionalFee(true);
						to.setDisplay(null);
					} else {
						to.setDummyAdditionalFee(false);
					}

				}
				objTo.setListOfEligibility(elgibilityList);
			}
		}

		return errors;
	}
}
