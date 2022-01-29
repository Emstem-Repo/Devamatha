package com.kp.cms.actions.exam;

import java.util.ArrayList;
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
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.exam.ExamExamEligibilitySetUpForm;
import com.kp.cms.handlers.exam.ExamEligibilitySetupHandler;

@SuppressWarnings("deprecation")
public class ExamExamEligibilitySetUpAction extends BaseDispatchAction {
	ActionErrors errors = new ActionErrors();
	ActionMessages messages = new ActionMessages();
	ExamEligibilitySetupHandler handler = new ExamEligibilitySetupHandler();

	public ActionForward initExamEligibilitySetUpAction(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamExamEligibilitySetUpForm objform = (ExamExamEligibilitySetUpForm) form;

		objform.setMapClass(handler.getClassList());
		objform.setListExamtype(handler.getExamTypeList());
		objform.setListAdditionalEligibility(handler
				.getListAdditionalEligibility());
		objform.setListExamEligibilitySetUp(handler
				.getListExamEligibilitySetUp());
		objform.setNoEligibilityCheck("off");
		objform.setExamFees("off");
		objform.setAttendance("off");
		objform.setCourseFees("off");
		return mapping.findForward(CMSConstants.EXAM_EXAM_ELIGIBILITY_SET_UP);
	}

	public ActionForward addExamEligibilitySetUp(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamExamEligibilitySetUpForm objform = (ExamExamEligibilitySetUpForm) form;
		errors.clear();
		messages.clear();
		errors = objform.validate(mapping, request);
		saveErrors(request, errors);
		setUserId(request, objform);
		int noEligibilityCheck = 0, examFees = 0, attendance = 0, courseFees = 0;
		if (objform.getNoEligibilityCheck().contains("on")) {
			noEligibilityCheck = 1;
		}
		if (objform.getExamFees().contains("on")) {
			examFees = 1;
		}
		if (objform.getAttendance().contains("on")) {
			attendance = 1;
		}
		if (objform.getCourseFees().contains("on")) {
			courseFees = 1;
		}
		ArrayList<Integer> listClass = new ArrayList<Integer>();
		if (objform.getClassValues() != null
				&& objform.getClassValues().trim().length() > 0) {
			String[] listClassIds = objform.getClassValues().split(",");
			for (String classValues : listClassIds) {
				listClass.add(Integer.parseInt(classValues));
			}
		}
		String[] str = request.getParameterValues("additionalEligibility");
		ArrayList<Integer> listAddElig = new ArrayList<Integer>();
		if (str != null && str.length != 0) {
			listAddElig = new ArrayList<Integer>();
			for (int x = 0; x < str.length; x++) {
				listAddElig.add(Integer.parseInt(str[x]));
			}
		}

		if (errors.isEmpty()) {
			try {

				handler.addElibilitySetup(objform.getClassValues(), Integer
						.parseInt(objform.getExamtypeId()), noEligibilityCheck,
						examFees, attendance, courseFees, listAddElig, objform
								.getUserId());
				ActionMessage message = new ActionMessage(
						"knowledgepro.exam.examEligibilitySetUp.addsuccess", "");
				messages.add("messages", message);
				saveMessages(request, messages);
				objform.clearPage();
			} catch (DuplicateException e1) {
				errors.add("error", new ActionError(
						"knowledgepro.exam.examEligibilitySetUp.exists"));
				saveErrors(request, errors);
			} catch (ReActivateException e1) {
				errors.add("error", new ActionError(
						"knowledgepro.exam.examEligibilitySetUp.reactivate", e1
								.getID()));
				saveErrors(request, errors);
			}
			objform.setMapClass(handler.getClassList());
			objform.setListExamtype(handler.getExamTypeList());
			objform.setListAdditionalEligibility(handler
					.getListAdditionalEligibility());
			objform.setListExamEligibilitySetUp(handler
					.getListExamEligibilitySetUp());
			objform.setNoEligibilityCheck("off");
			objform.setExamFees("off");
			objform.setAttendance("off");
			objform.setCourseFees("off");
		} else {
			objform.setMapClass(handler.getClassList());
			objform = setMapClass(objform, listClass);
			objform.setListExamtype(handler.getExamTypeList());
			objform.setListAdditionalEligibility(handler
					.getListAdditionalEligibilityToEdit(listAddElig));
			objform.setListExamEligibilitySetUp(handler
					.getListExamEligibilitySetUp());

			if (noEligibilityCheck == 1) {
				objform.setNoEligibilityCheck("on");
			} else {
				objform.setNoEligibilityCheck("off");
			}
			if (examFees == 1) {
				objform.setExamFees("on");
			} else {
				objform.setExamFees("off");
			}
			if (attendance == 1) {
				objform.setAttendance("on");
			} else {
				objform.setAttendance("off");
			}
			if (courseFees == 1) {
				objform.setCourseFees("on");
			} else {
				objform.setCourseFees("off");
			}

		}

		return mapping.findForward(CMSConstants.EXAM_EXAM_ELIGIBILITY_SET_UP);
	}

	private ExamExamEligibilitySetUpForm setMapClass(
			ExamExamEligibilitySetUpForm objform, ArrayList<Integer> listClass) {

		Map<Integer, String> mapClas = objform.getMapClass();
		Map<Integer, String> mapSelectedClas = new HashMap<Integer, String>();
		for (int i = 0; i < listClass.size(); i++) {
			Integer lcint = listClass.get(i);
			mapSelectedClas.put(lcint, mapClas.get(lcint));
			mapClas.remove(lcint);
		}
		objform.setMapClass(mapClas);
		objform.setMapSelectedClass(mapSelectedClas);

		return objform;
	}

	public ActionForward editExamEligibilitySetUp(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamExamEligibilitySetUpForm objform = (ExamExamEligibilitySetUpForm) form;
		errors.clear();
		messages.clear();
		String rowId = request.getParameter("id");
		String classId = objform.getClassId();
		String examTypeID = objform.getExamtypeId();
		int classIds=handler.getClassId(Integer.parseInt(rowId));
		objform = handler.getUpdatableForm(objform, Integer.parseInt(classId),
				Integer.parseInt(examTypeID));
		setRequestToList(objform, request, Integer.parseInt(rowId));
		ArrayList<Integer> listClass=new ArrayList<Integer>();
		listClass.add(classIds);
		
		objform = setMapClass(objform, listClass);
		objform.setClassId(classId);
		objform.setExamtypeId(examTypeID);
		objform.setEligibilitySetUpId(rowId);
		request.setAttribute("ExamEligibilitySetOperation", "edit");
		request.setAttribute("Update", "Update");
		return mapping.findForward(CMSConstants.EXAM_EXAM_ELIGIBILITY_SET_UP);
	}

	private void setRequestToList(ExamExamEligibilitySetUpForm objform,
			HttpServletRequest request, int rowId) throws Exception {
		objform.setMapClass(handler.getClassList());
		objform.setListExamtype(handler.getExamTypeList());
		objform.setListAdditionalEligibility(handler
				.getListAdditionalEligibilityEdit(rowId));
		objform.setListExamEligibilitySetUp(handler
				.getListExamEligibilitySetUp());

	}

	public ActionForward deleteExamExamEligibilitySetUp(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamExamEligibilitySetUpForm objform = (ExamExamEligibilitySetUpForm) form;
		errors.clear();
		messages.clear();
		setUserId(request, objform);
		String id = request.getParameter("id");
		handler.delete(Integer.parseInt(id), objform.getUserId());
		ActionMessage message = new ActionMessage(
				"knowledgepro.exam.examEligibilitySetUp.delte", "");
		messages.add("messages", message);
		saveMessages(request, messages);
		objform.setMapClass(handler.getClassList());
		objform.setListExamtype(handler.getExamTypeList());
		objform.setListAdditionalEligibility(handler
				.getListAdditionalEligibility());
		objform.setListExamEligibilitySetUp(handler
				.getListExamEligibilitySetUp());
		objform.setNoEligibilityCheck("off");
		objform.setExamFees("off");
		objform.setAttendance("off");
		objform.setCourseFees("off");
		return mapping.findForward(CMSConstants.EXAM_EXAM_ELIGIBILITY_SET_UP);
	}

	public ActionForward reActivateEligibilitySetUp(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamExamEligibilitySetUpForm objform = (ExamExamEligibilitySetUpForm) form;
		errors.clear();
		messages.clear();
		setUserId(request, objform);
		String id = request.getParameter("id");

		handler.reactivate(Integer.parseInt(id), objform.getUserId());
		ActionMessage message = new ActionMessage(
				"knowledgepro.exam.examEligibilitySetUp.reativeSuccess", "");
		messages.add("messages", message);
		saveMessages(request, messages);
		objform.setMapClass(handler.getClassList());
		objform.setListExamtype(handler.getExamTypeList());
		objform.setListAdditionalEligibility(handler
				.getListAdditionalEligibility());
		objform.setListExamEligibilitySetUp(handler
				.getListExamEligibilitySetUp());
		objform.setNoEligibilityCheck("off");
		objform.setExamFees("off");
		objform.setAttendance("off");
		objform.setCourseFees("off");
		return mapping.findForward(CMSConstants.EXAM_EXAM_ELIGIBILITY_SET_UP);
	}

	public ActionForward updateExamEligibilitySetUp(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamExamEligibilitySetUpForm objform = (ExamExamEligibilitySetUpForm) form;
		errors.clear();
		messages.clear();
		// errors = objform.validate(mapping, request);
		saveErrors(request, errors);
		setUserId(request, objform);

		int noEligibilityCheck = 0, examFees = 0, attendance = 0, courseFees = 0;

		if (objform.getNoEligibilityCheck().contains("on")) {
			noEligibilityCheck = 1;
		}
		if (objform.getExamFees().contains("on")) {
			examFees = 1;
		}
		if (objform.getAttendance().contains("on")) {
			attendance = 1;
		}
		if (objform.getCourseFees().contains("on")) {
			courseFees = 1;
		}
		try {

			if (errors.isEmpty()) {

				String[] str = request
						.getParameterValues("additionalEligibility");

				ArrayList<Integer> listAddElig = new ArrayList<Integer>();
				if (str != null && str.length != 0) {
					listAddElig = new ArrayList<Integer>();
					for (int x = 0; x < str.length; x++) {
						listAddElig.add(Integer.parseInt(str[x]));
					}
				}

				handler.updateEligibilitySetup(Integer.parseInt(objform
						.getEligibilitySetUpId()), Integer.parseInt(objform
						.getClassId()), Integer.parseInt(objform
						.getExamtypeId()), noEligibilityCheck, examFees,
						attendance, courseFees, listAddElig, objform
								.getUserId());
				ActionMessage message = new ActionMessage(
						"knowledgepro.exam.examEligibilitySetUp.addsuccess", "");
				messages.add("messages", message);
				saveMessages(request, messages);
				objform.clearPage();
			}

		} catch (DuplicateException e1) {
			errors.add("error", new ActionError(
					"knowledgepro.exam.examEligibilitySetUp.exists"));
			saveErrors(request, errors);
		} catch (ReActivateException e1) {
			errors.add("error", new ActionError(
					"knowledgepro.exam.examEligibilitySetUp.reactivate", e1
							.getID()));
			saveErrors(request, errors);
		}
		objform.setMapClass(handler.getClassList());
		objform.setListExamtype(handler.getExamTypeList());
		// objform.setListcheckEligibility(handler.getListcheckEligibility());
		objform.setListAdditionalEligibility(handler
				.getListAdditionalEligibility());
		objform.setListExamEligibilitySetUp(handler
				.getListExamEligibilitySetUp());
		return mapping.findForward(CMSConstants.EXAM_EXAM_ELIGIBILITY_SET_UP);
	}
}
