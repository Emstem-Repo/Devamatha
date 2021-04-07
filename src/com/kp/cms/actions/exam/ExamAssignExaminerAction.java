/**
 * 
 */
package com.kp.cms.actions.exam;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.forms.exam.ExamAssignExaminerForm;
import com.kp.cms.forms.exam.ExamAssignStudentsToRoomForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.exam.ExamAssignExaminerDutiesHandler;
import com.kp.cms.handlers.exam.ExamAssignStudentsToRoomHandler;
import com.kp.cms.handlers.exam.ExamMarksEntryHandler;
import com.kp.cms.to.exam.ExaminerDutiesTO;
import com.kp.cms.to.exam.InvDutyDetailsTO;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.CurrentAcademicYear;

/**
 * @author akshata.p
 * 
 */
@SuppressWarnings("deprecation")
public class ExamAssignExaminerAction extends BaseDispatchAction {

	ActionErrors errors = new ActionErrors();
	ActionMessages messages = new ActionMessages();

	ExamAssignExaminerDutiesHandler handler = new ExamAssignExaminerDutiesHandler();

	// gets initial list of Exam Definition
	public ActionForward initExamAssignExaminer(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamAssignExaminerForm objForm = (ExamAssignExaminerForm) form;
		objForm.clearPage();
		// Below line has written by Balaji
		objForm.setActionType(null);
		setRequestToList(objForm, request);
		return mapping.findForward(CMSConstants.EXAM_ASSIGN_EXAMINER);
	}

	private ExamAssignExaminerForm setRequestToList(
			ExamAssignExaminerForm objForm, HttpServletRequest request)
			throws Exception {
		objForm.clearPage();
	//	objForm.setExamNameList(handler.getExamName());
		//new addition by Smitha
		int year=0;
		year=CurrentAcademicYear.getInstance().getAcademicyear();
		if(objForm.getYear()!=null && !objForm.getYear().isEmpty()){
			year=Integer.parseInt(objForm.getYear());
		}
		if(year==0){
			Calendar calendar = Calendar.getInstance();
			year = calendar.get(Calendar.YEAR);
		}
		ExamAssignStudentsToRoomHandler examAssignStudenthandler = new ExamAssignStudentsToRoomHandler();
		objForm.setExamTypeList((HashMap<Integer, String>) examAssignStudenthandler
				.getExamTypeList());
		objForm.setExamType("1");
		
		Map<Integer,String> examMap=CommonAjaxHandler.getInstance().getExamNameByExamTypeAndYear(objForm.getExamType(),year); 
		objForm.setExamNameMap(examMap);
		//ends
		
		objForm.setInvigilatorList(handler.getInvigilatorList());
		int currentExamId = 0;
	//added by Smitha
		ExamMarksEntryHandler examhandler = new ExamMarksEntryHandler();
		String currentExam=examhandler.getCurrentExamName(objForm.getExamType());
		if(currentExam!=null){
		currentExamId =Integer.parseInt(currentExam);
		objForm.setExamName(String.valueOf(currentExamId));
		}
		//ends
		String dateTimeValue = "";
		if (currentExamId != 0) {
			ExamAssignStudentsToRoomHandler h = new ExamAssignStudentsToRoomHandler();
			dateTimeValue = h.getDateTimeByExamId(currentExamId);
		}

		String dateValue = "";
		String hh = "00";
		String mm = "00";
		if (!(dateTimeValue.equals("0") || dateTimeValue.isEmpty())) {
			String[] date = dateTimeValue.split(" ");
			String t1 = "";

			for (int i = 0; i < date.length; i += 2) {
				dateValue = date[i];
				t1 = date[i + 1];
			}
			String[] time = t1.split(":");
			for (int i = 0; i < time.length; i += 3) {
				hh = time[i];
				mm = time[i + 1];
			}
		}

		objForm.setDate(dateValue);
		objForm.setHr(hh);
		objForm.setMin(mm);

		return objForm;

	}

	public ActionForward assignExaminer(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamAssignExaminerForm objForm = (ExamAssignExaminerForm) form;
		errors = objForm.validate(mapping, request);
		saveErrors(request, errors);
		String date = (objForm.getDate() != null
				&& objForm.getDate().trim().length() > 0 ? objForm.getDate()
				: "");
		String hr = (objForm.getHr() != null
				&& objForm.getHr().trim().length() > 0 ? objForm.getHr() : "00");
		String min = (objForm.getMin() != null
				&& objForm.getMin().trim().length() > 0 ? objForm.getMin()
				: "00");
		if (errors.isEmpty()) {
			errors = validateTime(objForm.getDate(), objForm.getHr(), objForm
					.getMin(), Integer.parseInt(objForm.getExamName()));

			saveErrors(request, errors);
			setUserId(request, objForm);
			if (errors.isEmpty()) {
				objForm = handler.getExaminerDetails(objForm);

				return mapping
						.findForward(CMSConstants.EXAM_ASSIGN_EXAMINER_RESULT);
			}
		}
		setRequestToList(objForm, request);
		objForm.setDate(date);
		objForm.setHr(hr);
		objForm.setMin(min);
		return mapping.findForward(CMSConstants.EXAM_ASSIGN_EXAMINER);

	}

	public ActionForward deleteAssignExaminer(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		errors.clear();
		messages.clear();
		ExamAssignExaminerForm objForm = (ExamAssignExaminerForm) form;
		try {
			setUserId(request, objForm);
			String id = request.getParameter("id");
			handler.deleteExamDefinition(Integer.parseInt(id), objForm
					.getUserId());
			ActionMessage message = new ActionMessage(
					"knowledgepro.exam.assignExaminer.delte");
			messages.add("messages", message);
			saveMessages(request, messages);
//			objForm = handler.getExaminerDetails(objForm);

		} catch (Exception e) {
			String msg = super.handleApplicationException(e);
			objForm.setErrorMessage(msg);
			objForm.setErrorStack(e.getMessage());
		} finally {
//			setRequestToList(objForm, request);
			objForm = handler.getExaminerDetails(objForm);
		}
		return mapping.findForward(CMSConstants.EXAM_ASSIGN_EXAMINER_RESULT);

	}

	public ActionForward updateAssignExaminer(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamAssignExaminerForm objForm = (ExamAssignExaminerForm) form;
		String date = objForm.getDate();
		errors.clear();
		messages.clear();
		String[] values = null;
		if (objForm.getCheckValidation() != null
				&& objForm.getCheckValidation().equalsIgnoreCase("yes")) {
			errors.add("error", new ActionError(
					"knowledgepro.exam.InvigilatorDuty.required"));
		}
		if (objForm.getListValues() != null
				&& objForm.getListValues().length() == 0) {

			objForm.setMode("reset");

			errors.add("error", new ActionError("knowledgepro.exam.assignExaminer.required"));
			if(objForm.getListValuesDummy()!=null && !objForm.getListValuesDummy().isEmpty() && objForm.getListValuesDummy().length()>0){
			String a = objForm.getListValuesDummy().substring(0,
					objForm.getListValuesDummy().length() - 1);
			values = a.split("#");
			errors = checkSPLCherectors(values);
			}
		}
		else {
			String a = objForm.getListValues().substring(0,
					objForm.getListValues().length() - 1);
			values = a.split("#");
			errors = checkSPLCherectors(values);
		}

		saveErrors(request, errors);
		if (errors.isEmpty()) {

			int examid = objForm.getExamNameId();

			setUserId(request, objForm);
			ArrayList<InvDutyDetailsTO> list = new ArrayList<InvDutyDetailsTO>();
			if (values.length != 0) {
				for (int x = 0; x < values.length; x = x + 5) {

					list.add(new InvDutyDetailsTO(Integer.parseInt(values[x]),
							examid, Integer.parseInt(values[x + 1]),
							values[x + 2], values[x + 3], Integer
									.parseInt(values[x + 4])));
				}
			}

			try {
				handler.updateAssignExaminer(list, objForm.getDate(), objForm
						.getHr(), objForm.getMin());

			} catch (DuplicateException e) {
				objForm.setExamName(Integer.toString(examid));
				objForm = handler.getExaminerDetails(objForm);
				errors.add("error", new ActionError(
						"knowledgepro.exam.assignExaminer.room.valid"));

				saveErrors(request, errors);
				retainRadioValues(objForm, values);
				return mapping
						.findForward(CMSConstants.EXAM_ASSIGN_EXAMINER_RESULT);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				setRequestToList(objForm, request);
			}

		} else {
			retainRadioValues(objForm, values);
			return mapping
					.findForward(CMSConstants.EXAM_ASSIGN_EXAMINER_RESULT);
		}

		setRequestToList(objForm, request);
		if (objForm.getActionType() != null
				&& objForm.getActionType().equalsIgnoreCase("studentsToRoom")
				&& objForm.getActionType().length() > 0) {
			ExamAssignStudentsToRoomForm acform = new ExamAssignStudentsToRoomForm();
			ExamAssignStudentsToRoomHandler ac = new ExamAssignStudentsToRoomHandler();
			// ac.initExamAssignStudentsToRoom(mapping, acform, request,
			// response);
			acform.setExamType(objForm.getExamType());
			acform.setExamNameId(Integer.toString(objForm.getExamNameId()));
			acform.setDate(date);
			acform.setHr(objForm.getHr());
			acform.setMin(objForm.getMin());
			acform.setRoomId(objForm.getRoomId());
			acform.setNonEligible(objForm.getNonEligible());
			acform.setDisplayOrder(objForm.getDisplayOrder());
			ac.add(acform);
			ActionMessage message = new ActionMessage(
					"knowledgepro.exam.assignStudentsToRoom.addeduccess", "");
			messages.add("messages", message);
			saveMessages(request, messages);
			saveErrors(request, errors);
			return mapping
					.findForward(CMSConstants.EXAM_ASSIGN_STUDENT_TO_ROOM_ADD);
		} else {
			ActionMessage message = new ActionMessage("knowledgepro.exam.assignExaminer.success");
			messages.add("messages", message);
			saveMessages(request, messages);
			setRequestToList(objForm, request);
//			objForm = handler.getExaminerDetails(objForm);
			return mapping.findForward(CMSConstants.EXAM_ASSIGN_EXAMINER);
		}
	}

	private void retainRadioValues(ExamAssignExaminerForm objForm,
			String[] values) {
		int examid = objForm.getExamNameId();

		HashMap<Integer, Integer> empInvigilator = new HashMap<Integer, Integer>();
		if (values!=null && values.length != 0) {
			for (int x = 0; x < values.length; x = x + 5) {
				empInvigilator.put(Integer.parseInt(values[x + 4]), Integer
						.parseInt(values[x + 1]));
			}
		}
		objForm.setExamName(Integer.toString(examid));
		ArrayList<InvDutyDetailsTO> list = (ArrayList<InvDutyDetailsTO>) objForm
				.getInvDutyListMain();
		ArrayList<ExaminerDutiesTO> examinerDuties = null;


		ArrayList<InvDutyDetailsTO> listDummy = new ArrayList<InvDutyDetailsTO>();
		for (InvDutyDetailsTO to : list) {
			examinerDuties = to.getListInvigilator();
			Integer assignExaminerId = to.getAssignExaminerExamId();
			Integer InvId = null;
			ArrayList<ExaminerDutiesTO> examinerDutiesTemp = new ArrayList<ExaminerDutiesTO>();
			for (ExaminerDutiesTO examinerDutiesTO : examinerDuties) {
				InvId = empInvigilator.get(assignExaminerId);
				if (InvId != null
						&& InvId == examinerDutiesTO.getInvDutyTypeId()) {
					examinerDutiesTO.setDisplay("on");
				} else {
					examinerDutiesTO.setDisplay("off");
				}

				examinerDutiesTemp.add(examinerDutiesTO);
				to.setListInvigilator(examinerDutiesTemp);
			}
			listDummy.add(to);
		}
		objForm.setInvDutyListMain(listDummy);

	}

	private ActionErrors checkSPLCherectors(String[] values) {

		for (int x = 0; x < values.length; x = x + 5) {
			if (values[x + 3] != null && splCharValidation(values[x + 3])) {
				errors.add("error", new ActionError(
						"knowledgepro.exam.assignExaminer.special"));
				break;
			}
		}
		return errors;
	}

	private boolean splCharValidation(String name) {
		boolean haveSplChar = false;
		Pattern pattern = Pattern.compile("[^A-Z a-z0-9]+");
		Matcher matcher = pattern.matcher(name);
		haveSplChar = matcher.find();
		return haveSplChar;

	}

	private ActionErrors validateTime(String date, String hr, String min,
			int examId) throws Exception {
		if (!handler.isDateTimeValid(CommonUtil.ConvertStringToSQLDateTime(date
				+ " " + hr.concat(":".concat(min)).concat(":00")), examId)) {
			errors.add("error", new ActionError(
					"knowledgepro.exam.assignExaminer.dateTime.invalid"));
		}
		return errors;
	}

}
