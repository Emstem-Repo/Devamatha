package com.kp.cms.actions.exam;

import java.util.ArrayList;
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
import com.kp.cms.constants.CMSExamConstants;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.forms.exam.ExamUniversityRegisterNumberEntryForm;
import com.kp.cms.handlers.exam.ExamUniversityRegisterNumberEntryHandler;
import com.kp.cms.to.exam.ExamUniversityRegisterNumberEntryTO;
import com.kp.cms.transactionsimpl.ajax.CommonAjaxImpl;
import com.kp.cms.utilities.CommonUtil;

@SuppressWarnings("deprecation")
public class ExamUniversityRegisterNumberEntryAction extends BaseDispatchAction {
	ExamUniversityRegisterNumberEntryHandler handler = new ExamUniversityRegisterNumberEntryHandler();
	ActionErrors errors = new ActionErrors();
	ActionMessages messages = new ActionMessages();

	public ActionForward initUniversityRegisterNumberEntry(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamUniversityRegisterNumberEntryForm objform = (ExamUniversityRegisterNumberEntryForm) form;

		errors.clear();
		messages.clear();
		objform.setDisplayOrder("rollno");
		objform.setSpecName(null);
		objform.setLangName(null);
		objform.setListSpecialization(handler.getSpecializationList());
		objform.setSecondLanguage(handler.getSecondLanguage_List());
		String academicYear = CommonUtil.getCurrentYear();
		//str = str.substring(6, str.length());
		int year =(academicYear!=null & academicYear.trim().length()>0?Integer.parseInt(academicYear):0) ;
		objform.setMapProgram(handler.getProgramByYear(year));
		return mapping.findForward(CMSExamConstants.EXAM_USN_ENTRY);
	}

	public ActionForward Search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamUniversityRegisterNumberEntryForm objform = (ExamUniversityRegisterNumberEntryForm) form;
		errors.clear();
		messages.clear();
		errors = objform.validate(mapping, request);
		saveErrors(request, errors);
		setUserId(request, objform);
		if (errors.isEmpty()) {
			objform.setAcademicYearFull(handler.getacademicYear(Integer
					.parseInt(objform.getAcademicYear())));
			int academicYear = Integer.parseInt(objform.getAcademicYear());

			int courseId = Integer.parseInt(objform.getCourseName());

			int programId = Integer.parseInt(objform.getProgramName());
			String[] schemeStr = objform.getScheme().split("_");
			int schemeNo = 0;
			int schemeId = 0;

			objform.setCourseId(objform.getCourseName());
			objform.setProgramId(objform.getProgramName());
			objform.setYear(objform.getAcademicYear());

			if (schemeStr.length > 1) {
				schemeId = Integer.parseInt(schemeStr[0]);
				schemeNo = Integer.parseInt(schemeStr[1]);
			}
			objform.setSchemeId(Integer.toString(schemeId));
			objform.setCourseName(handler.getCourseName(courseId));
			objform.setScheme(Integer.toString(schemeNo));
			objform.setSchemeNo(Integer.toString(schemeNo));
			objform
					.setProgramName(handler
							.getProgramNameByProgramId(programId));

			String orderBy = "";
			objform.setProgramId(Integer.toString(programId));

			if (objform.getDisplayOrder().equalsIgnoreCase("rollno")) {
				orderBy = "rollno";
			} else {
				orderBy = "name";
			}
			ArrayList<ExamUniversityRegisterNumberEntryTO> list = handler
					.getStudentDetails(academicYear, courseId, schemeId,
							schemeNo, orderBy);
			objform.setListStudentSize(list.size());

			objform.setStudentDetails(list);
			return mapping.findForward(CMSExamConstants.EXAM_USN_ENTRY_RESULT);

		} else {
			int academicYear = Integer.parseInt(objform.getAcademicYear());
			objform.setMapProgram(handler.getProgramByYear(academicYear));
			if (objform.getProgramName() != null
					&& objform.getProgramName().length() > 0) {
				int id = Integer.parseInt(objform.getProgramName());
				CommonAjaxImpl i = new CommonAjaxImpl();
				objform.setMapCourse(i.getCourseByProgram(id));
			}
			objform.setDisplayOrder("rollno");
			objform.setListSpecialization(handler.getSpecializationList());
			objform.setSecondLanguage(handler.getSecondLanguage_List());

			return mapping.findForward(CMSExamConstants.EXAM_USN_ENTRY);
		}

	}

	public ActionForward Submit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamUniversityRegisterNumberEntryForm objform = (ExamUniversityRegisterNumberEntryForm) form;
		ActionErrors error = validate(objform.getStudentDetails());
		saveErrors(request, error);
		try {
			setUserId(request, objform);

			objform.setDisplayOrder("rollno");
			if (error.isEmpty()) {
				handler.update(objform);
				ActionMessage message = new ActionMessage(
						"knowledgepro.exam.UnvRegEntry.success");
				messages.add("messages", message);
				saveMessages(request, messages);
				objform.clearPage();
			} else {
				saveErrors(request, error);

				objform.setAcademicYearFull(handler.getacademicYear(Integer
						.parseInt(objform.getAcademicYear())));

				int courseId = Integer.parseInt(objform.getCourseId());
				int programId = Integer.parseInt(objform.getProgramId());
				int schemeNo = Integer.parseInt(objform.getSchemeNo());
				//int schemeId = Integer.parseInt(objform.getSchemeId());
				String[] schemeStr = objform.getScheme().split("_");

				if (schemeStr.length > 1) {
					//schemeId = Integer.parseInt(schemeStr[0]);
					schemeNo = Integer.parseInt(schemeStr[1]);
				}

				objform.setCourseName(handler.getCourseName(courseId));
				objform.setScheme(Integer.toString(schemeNo));
				objform.setProgramName(handler
						.getProgramNameByProgramId(programId));

				String orderBy = "";
				objform.setProgramId(Integer.toString(programId));

				if (objform.getDisplayOrder().equalsIgnoreCase("rollno")) {
					orderBy = "rollno";
				} else {
					orderBy = "name";
				}
				// ----------------------- Commented By
				// Ashwin--------------------------------
				// above code also has to comment
				// ArrayList<ExamUniversityRegisterNumberEntryTO> list = handler
				// .getStudentDetails(academicYear, courseId, schemeId,
				// schemeNo, orderBy);
				// objform.setListStudentSize(list.size());

				// objform.setStudentDetails(list);
				//--------------------------------------------------------------
				// -----------------------------------------
				return mapping
						.findForward(CMSExamConstants.EXAM_USN_ENTRY_RESULT);

			}

		} catch (DuplicateException e1) {
			String lineNumber = e1.getMessage();
			error.clear();
			errors.add("error", new ActionError(
					"knowledgepro.exam.UnvRegEntry.exists", lineNumber));
			saveErrors(request, errors);
			return mapping.findForward(CMSExamConstants.EXAM_USN_ENTRY_RESULT);
		} finally {
			objform.setListSpecialization(handler.getSpecializationList());
			objform.setSecondLanguage(handler.getSecondLanguage_List());
		}

		return mapping.findForward(CMSExamConstants.EXAM_USN_ENTRY);

	}

	private ActionErrors validate(
			ArrayList<ExamUniversityRegisterNumberEntryTO> studentDetails) {
		ActionErrors errors = new ActionErrors();
		errors.clear();
		messages.clear();
		for (ExamUniversityRegisterNumberEntryTO to : studentDetails) {
			if (to.getRegNo() != null && to.getRegNo().length() == 0) {
				if (errors.isEmpty()) {
					errors.add("error", new ActionError(
							"knowledgepro.exam.UnvRegEntry.reg_entry"));
				}
			}
			if (splCharValidation(to.getRegNo(), "\\-\\_\\/")) {
				if (errors.isEmpty()) {
					errors.add("error", new ActionError(
							"knowledgepro.exam.Revaluation.splChar"));
				}
			}

		}
		return errors;
	}

	private boolean splCharValidation(String name, String splChar) {

		boolean haveSplChar = false;
		if (name != null) {
			Pattern pattern = Pattern.compile("[^A-Za-z0-9" + splChar + "]+");
			Matcher matcher = pattern.matcher(name);
			haveSplChar = matcher.find();
		}
		return haveSplChar;
	}
}
