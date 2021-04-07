package com.kp.cms.actions.exam;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.exam.ExamStudentEligibilityEntryForm;
import com.kp.cms.handlers.exam.ExamStudentEligibilityEntryHandler;
import com.kp.cms.to.exam.ExamStudentEligibilityEntryTO;
import com.kp.cms.utilities.CommonUtil;

@SuppressWarnings("deprecation")
public class ExamStudentEligibilityEntryAction extends BaseDispatchAction {
	ActionErrors errors = new ActionErrors();
	ActionMessages messages = new ActionMessages();
	ExamStudentEligibilityEntryHandler handler = new ExamStudentEligibilityEntryHandler();

	public ActionForward initStudentEligibilityEntry(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamStudentEligibilityEntryForm objform = (ExamStudentEligibilityEntryForm) form;
		objform
				.setListEligibilityCriteria(handler
						.getListEligibilityCriteria());
		ArrayList<ExamStudentEligibilityEntryTO> listStudent = new ArrayList<ExamStudentEligibilityEntryTO>();
		objform.setListStudent(listStudent);
		if (objform.getYear() == null) {
			String str = CommonUtil.getTodayDate();
			str = str.substring(6, str.length());
			objform.setYear(str);
			objform.setMapClass(handler.getClassCode(Integer.parseInt(str)));
			objform.setStudentSize(0);

		} else {
			objform.setYear(objform.getYear());

			objform.setMapClass(handler.getClassCode(Integer.parseInt(objform
					.getYear())));
		}

		return mapping.findForward(CMSConstants.EXAM_STUDENT_ELEGIBILETY_ENTRY);
	}

	public ActionForward getStudentEligibilityEntry(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamStudentEligibilityEntryForm objform = (ExamStudentEligibilityEntryForm) form;
		errors.clear();
		messages.clear();
		errors = objform.validate(mapping, request);
		saveErrors(request, errors);
		setUserId(request, objform);
		String year = objform.getYear();
		String a = objform.getClassValues();
		String[] str = null;
		if (a != null && a.length() > 0) {
			if (!a.equals(" "))
				str = a.split(",");
		}
		ArrayList<Integer> listClass = new ArrayList<Integer>();
		if (str != null && str.length > 0) {
			for (int x = 0; x < str.length; x++) {
				if (str[x] != null && str[x].length() > 0) {
					listClass.add(Integer.parseInt(str[x]));
				}

			}
		}

		if (str != null && str.length != 0) {
			objform.setMapClass(handler.getClassCode(Integer.parseInt(year)));
			objform = setMapClass(objform, listClass);
		} else {
			objform.setMapClass(handler.getClassCode(Integer.parseInt(year)));
		}

		if (errors.isEmpty()) {

			ArrayList<ExamStudentEligibilityEntryTO> list = handler.search(
					listClass, Integer.parseInt(objform
							.getEligibilityCriteria()), year);
			if (list.size() != 0) {
				objform.setListStudent(list);
				objform.setYear(year);
				objform.setClassValues(a);
				objform.setStudentSize(1);
				objform
						.setEligibilityCriteria(objform
								.getEligibilityCriteria());

				request
						.setAttribute("StudentEligibilityEntryOperation",
								"edit");

				objform.setMapClass(handler
						.getClassCode(Integer.parseInt(year)));
				objform.setYear(objform.getYear());
				objform = setMapClass(objform, listClass);

			} else {
				objform.setStudentSize(0);
				ActionMessage message = new ActionMessage(
						"knowledgepro.exam.examEligibilitySetUp.error", "");
				messages.add("messages", message);
				saveMessages(request, messages);
				saveErrors(request, errors);
				objform.clearPage();

				objform.setMapClass(handler
						.getClassCode(Integer.parseInt(year)));
				objform.setMapSelectedClass(new HashMap<Integer, String>());
			}

		} else {

			objform.setStudentSize(0);
			ArrayList<ExamStudentEligibilityEntryTO> listStudent = new ArrayList<ExamStudentEligibilityEntryTO>();
			objform.setMapClass(handler.getClassCode(Integer.parseInt(year)));
			objform = setMapClass(objform, listClass);
			objform.setListStudent(listStudent);
			objform.setClassValues("");
		}
		objform
				.setListEligibilityCriteria(handler
						.getListEligibilityCriteria());
		objform.setYear(objform.getYear());
		return mapping.findForward(CMSConstants.EXAM_STUDENT_ELEGIBILETY_ENTRY);
	}

	private ExamStudentEligibilityEntryForm setMapClass(
			ExamStudentEligibilityEntryForm objform,
			ArrayList<Integer> listClass) throws Exception {

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

	public ActionForward setStudentEligibilityEntry(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamStudentEligibilityEntryForm objform = (ExamStudentEligibilityEntryForm) form;
		errors.clear();
		messages.clear();

		setUserId(request, objform);
		String[] str = request.getParameterValues("studentId");
		int id = Integer.parseInt(objform.getEligibilityCriteria());
		if (str == null) {

			String a = objform.getClassValues();
			String[] str1 = a.split(",");
			ArrayList<Integer> listClass = new ArrayList<Integer>();
			if (str1 != null && str1.length > 0) {
				for (int x = 0; x < str1.length; x++) {
					if (str1[x] != null && str1[x].length() > 0) {
						listClass.add(Integer.parseInt(str1[x]));
					}

				}
			}
			handler.deleteStudentEligibilityEntry(listClass, id);
			objform.setListEligibilityCriteria(handler
					.getListEligibilityCriteria());
			ArrayList<ExamStudentEligibilityEntryTO> listStudent = new ArrayList<ExamStudentEligibilityEntryTO>();
			objform.setListStudent(listStudent);
			String year = CommonUtil.getTodayDate();
			year = year.substring(6, year.length());
			objform.setMapClass(handler.getClassCode(Integer.parseInt(year)));

			// Date curdate = new Date();
			// objform.setMapClass(handler.getClassCode(curdate.getYear() +
			// 1900));
			objform.setStudentSize(0);
			objform.setEligibilityCriteria("");
			return mapping
					.findForward(CMSConstants.EXAM_STUDENT_ELEGIBILETY_ENTRY);
		}
		if (errors.isEmpty()) {

			ArrayList<ExamStudentEligibilityEntryTO> listStudents = new ArrayList<ExamStudentEligibilityEntryTO>();
			for (int x = 0; x < str.length; x++) {
				String[] name = str[x].split("-");
				// int studentId, int classId,
				// int eligibilityCriteriaId, String eligibilityCheck
				for (int i = 0; i < name.length; i = +2) {
					listStudents.add(new ExamStudentEligibilityEntryTO(Integer
							.parseInt(name[i]), Integer.parseInt(name[i + 1]),
							id, "on"));
				}

			}
			String a = objform.getClassValues();
			String[] str1 = a.split(",");
			ArrayList<Integer> listClass = new ArrayList<Integer>();
			for (int x = 0; x < str1.length; x++) {
				listClass.add(Integer.parseInt(str1[x]));
			}

			handler.update(listStudents, listClass, Integer.parseInt(objform
					.getEligibilityCriteria()), objform.getUserId());

			objform.setEligibilityCriteria(objform.getEligibilityCriteria());

			request
					.setAttribute("StudentEligibilityEntryOperation",
							null);

			ActionMessage message = new ActionMessage(
					"knowledgepro.exam.studentEligibilityEntry.success", "");
			messages.add("messages", message);
			saveMessages(request, messages);
			saveErrors(request, errors);
			objform.clearPage();
		} else {
			String year = objform.getYear();
			String a = objform.getClassValues();
			String[] str1 = a.split(",");
			ArrayList<Integer> listClass = new ArrayList<Integer>();
			for (int x = 0; x < str1.length; x++) {
				listClass.add(Integer.parseInt(str1[x]));
			}
			ArrayList<ExamStudentEligibilityEntryTO> list = handler.search(
					listClass, Integer.parseInt(objform
							.getEligibilityCriteria()), year);
			if (list.size() != 0) {
				objform.setListStudent(list);
				objform.setYear(year);
				objform.setClassValues(a);
				objform.setStudentSize(1);
				objform
						.setEligibilityCriteria(objform
								.getEligibilityCriteria());

				request
						.setAttribute("StudentEligibilityEntryOperation",
								"edit");
			}
		}
		objform
				.setListEligibilityCriteria(handler
						.getListEligibilityCriteria());
		String acYear = CommonUtil.getTodayDate();
		acYear = acYear.substring(6, acYear.length());
		objform.setMapClass(handler.getClassCode(Integer.parseInt(acYear)));

		return mapping.findForward(CMSConstants.EXAM_STUDENT_ELEGIBILETY_ENTRY);
	}
}
