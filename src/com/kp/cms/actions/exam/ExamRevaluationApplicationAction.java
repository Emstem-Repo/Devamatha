package com.kp.cms.actions.exam;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
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
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.exam.ExamRevaluationApplicationForm;
import com.kp.cms.handlers.ajax.CommonAjaxExamHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.exam.ExamAssignStudentsToRoomHandler;
import com.kp.cms.handlers.exam.ExamMarksEntryHandler;
import com.kp.cms.handlers.exam.ExamRevaluationApplicationHandler;
import com.kp.cms.to.exam.ExamSubjectTO;
import com.kp.cms.utilities.CurrentAcademicYear;

@SuppressWarnings("deprecation")
public class ExamRevaluationApplicationAction extends BaseDispatchAction {
	ActionErrors errors = new ActionErrors();
	ActionMessages messages = new ActionMessages();
	ExamRevaluationApplicationHandler handler = new ExamRevaluationApplicationHandler();

	public ActionForward initRevaluationApplication(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamRevaluationApplicationForm objform = (ExamRevaluationApplicationForm) form;
		objform.clearPage();
	//	objform.setListExamName(handler.getExamNameList());
		setRequiredDataToForm(objform);
		return mapping.findForward(CMSConstants.EXAM_REVALUATION_APPLICTION);
	}
	
	

	/**
	 * new parameters academic year and exam type  added
	 * @param objform
	 * @throws Exception
	 */
	private void setRequiredDataToForm(ExamRevaluationApplicationForm objform) throws Exception {
		//added by Smitha
		int year=0;
		year=CurrentAcademicYear.getInstance().getAcademicyear();
		if(objform.getYear()!=null && !objform.getYear().isEmpty()){
			year=Integer.parseInt(objform.getYear());
		}
		if(year==0){
			Calendar calendar = Calendar.getInstance();
			year = calendar.get(Calendar.YEAR);
		}
		ExamAssignStudentsToRoomHandler examAssignStudenthandler = new ExamAssignStudentsToRoomHandler();
		objform.setExamTypeList((HashMap<Integer, String>) examAssignStudenthandler
				.getExamTypeList());
		if(objform.getExamType()==null || objform.getExamType().trim().isEmpty())
		objform.setExamType("1");
		
		Map<Integer,String> examMap=CommonAjaxHandler.getInstance().getExamNameByExamTypeAndYear(objform.getExamType(),year); 
		objform.setExamNameMap(examMap);
		
		ExamMarksEntryHandler examhandler = new ExamMarksEntryHandler();
		String currentExam=examhandler.getCurrentExamName(objform.getExamType());
		if((objform.getExamNameId()==null || objform.getExamNameId().trim().isEmpty()) && currentExam!=null){
			objform.setExamNameId(currentExam);
		}
		//ends		
	}



	public ActionForward Add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamRevaluationApplicationForm objform = (ExamRevaluationApplicationForm) form;
		errors.clear();
		messages.clear();
		errors = objform.validate(mapping, request);
		saveErrors(request, errors);
		setUserId(request, objform);
		String rollNo = objform.getRollNo().trim();
		String regNo = objform.getRegNo().trim();
		validateAdd(objform.getSchemeNo(), regNo, rollNo);
		if (errors.isEmpty()) {
			try {
				objform = handler.getDetailsToAdd(objform);
				if (objform.getListSubject() != null
						&& objform.getListSubject().size() > 0) {
					return mapping
							.findForward(CMSConstants.EXAM_REVALUATION_APPLICTION_ADD);
				} else {
					ActionMessage message = new ActionMessage(
							"knowledgepro.norecords");
					messages.add("messages", message);
					saveMessages(request, messages);
					objform.clearPage();
				//	objform.setListExamName(handler.getExamNameList());
					setRequiredDataToForm(objform);
					return mapping
							.findForward(CMSConstants.EXAM_REVALUATION_APPLICTION);
				}
			} catch (BusinessException e) {
				errors.add("error", new ActionError(
						"knowledgepro.exam.reJoin.rollNoReg.notMatch", rollNo,
						regNo));
				saveErrors(request, errors);
			}
	//		objform.setListExamName(handler.getExamNameList());
			setRequiredDataToForm(objform);
			saveErrors(request, errors);
			return mapping
					.findForward(CMSConstants.EXAM_REVALUATION_APPLICTION);
		} else {
		//	objform.setListExamName(handler.getExamNameList());
			setRequiredDataToForm(objform);
			retainIntialValues(objform);
			saveErrors(request, errors);
			return mapping
					.findForward(CMSConstants.EXAM_REVALUATION_APPLICTION);

		}

	}

	private void retainIntialValues(ExamRevaluationApplicationForm objform) throws Exception {
		CommonAjaxExamHandler commHandler = new CommonAjaxExamHandler();
		if (objform.getExamNameId() != null
				&& objform.getExamNameId().trim().length() > 0)
			objform.setCourseMap(commHandler.getCourseByExamNameRegNoRollNo(
					Integer.parseInt(objform.getExamNameId()), objform
							.getRegNo(), objform.getRollNo()));
		if (objform.getCourseId() != null
				&& objform.getCourseId().trim().length() > 0) {
			objform.setSchemeMap(commHandler.getSchemeNoByCourseId(Integer
					.parseInt(objform.getCourseId())));
		}
	}

	public ActionForward Search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamRevaluationApplicationForm objform = (ExamRevaluationApplicationForm) form;
		errors.clear();
		messages.clear();
		errors = objform.validate(mapping, request);

		saveErrors(request, errors);
		setUserId(request, objform);
		if (errors.isEmpty()) {

			objform = handler.getSearchDetails(objform);
			if (objform.getListStudentName().size() == 0) {
				ActionMessage message = new ActionMessage(
						"knowledgepro.norecords");
				messages.add("messages", message);
				saveMessages(request, messages);
				objform.clearPage();
			//	objform.setListExamName(handler.getExamNameList());
				setRequiredDataToForm(objform);
				return mapping
						.findForward(CMSConstants.EXAM_REVALUATION_APPLICTION);
			}

			return mapping
					.findForward(CMSConstants.EXAM_REVALUATION_APPLICTION_SEARCH);
		} else {
		//	objform.setListExamName(handler.getExamNameList());
			setRequiredDataToForm(objform);
			retainIntialValues(objform);
			return mapping
					.findForward(CMSConstants.EXAM_REVALUATION_APPLICTION);
		}
	}

	public ActionForward editExamRevaluationApplication(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamRevaluationApplicationForm objform = (ExamRevaluationApplicationForm) form;

		objform = handler.getDetailsToEdit(objform);

		return mapping
				.findForward(CMSConstants.EXAM_REVALUATION_APPLICTION_ADD);
	}

	public ActionForward updateRevaluationApplication(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamRevaluationApplicationForm objform = (ExamRevaluationApplicationForm) form;
		errors.clear();
		messages.clear();
		errors = objform.validate(mapping, request);
		errors = validateErrors(objform);
		setUserId(request, objform);
		if (errors.isEmpty()) {
			if (objform.getId() == 0) {
				handler.add(objform);
			} else {
				handler.update(objform, objform.getListSubject());
			}
			objform.clearPage();

			ActionMessage message = new ActionMessage(
					"knowledgepro.admin.addsuccess", "Revaluation Subjects");
			messages.add("messages", message);
			saveMessages(request, messages);
		//	objform.setListExamName(handler.getExamNameList());
			setRequiredDataToForm(objform);
			objform.clearPage();
			return mapping
					.findForward(CMSConstants.EXAM_REVALUATION_APPLICTION);
		} else {
			retainValues(objform.getListSubject(), objform);
			saveErrors(request, errors);
			return mapping
					.findForward(CMSConstants.EXAM_REVALUATION_APPLICTION_ADD);
		}
	}

	private ActionErrors validateErrors(ExamRevaluationApplicationForm objform) {

		ArrayList<ExamSubjectTO> list = (ArrayList<ExamSubjectTO>) objform
				.getListSubject();
		int flag = 0;
		for (ExamSubjectTO to : list) {
			if (to.getValue() != null) {
				flag = 1;
			}
		}
		if (flag == 0) {
			errors.add("error", new ActionError(
					"knowledgepro.exam.revaluationApplication.subject.select"));
		}
		return errors;
	}

	private void retainValues(List<ExamSubjectTO> list,
			ExamRevaluationApplicationForm objform) {
		ArrayList<ExamSubjectTO> newList = new ArrayList<ExamSubjectTO>();
		for (ExamSubjectTO to : list) {
			if (to.getValue() != null) {
				to.setDummyValue(true);
				to.setValue(null);
			} else {
				to.setDummyValue(false);

			}
			to.setId(to.getId());
			to.setSubjectCode(to.getSubjectCode());
			to.setSubjectName(to.getSubjectName());
			newList.add(to);
		}
		objform.setListSubject(newList);
	}

	public ActionForward deleteExamRevaluationApplication(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamRevaluationApplicationForm objform = (ExamRevaluationApplicationForm) form;
		errors.clear();
		messages.clear();
		setUserId(request, objform);
		String id = request.getParameter("id");
		handler.delete(Integer.parseInt(id), objform.getUserId());
		ActionMessage message = new ActionMessage(
				"knowledgepro.exam.revaluationApplication.deletesuccess", "");
		messages.add("messages", message);
		saveMessages(request, messages);
	//	objform.setListExamName(handler.getExamNameList());
		setRequiredDataToForm(objform);
		return mapping.findForward(CMSConstants.EXAM_REVALUATION_APPLICTION);
	}

	private void validateAdd(String schemeNo, String regNo, String rollNo) {
		if (schemeNo.length() == 0 || schemeNo.isEmpty() || schemeNo == null) {
			errors.add("error", new ActionError(
					"knowledgepro.exam.revaluationApplication.scheme.select"));
		}
		if ((regNo.length() == 0 || regNo.isEmpty() || regNo == null)
				&& (rollNo.length() == 0 || rollNo.isEmpty() || rollNo == null)) {
			errors
					.add(
							"error",
							new ActionError(
									"knowledgepro.exam.revaluationApplication.regNo_rollNo.required"));
		}
	}

}
