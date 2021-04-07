package com.kp.cms.actions.exam;

import java.util.ArrayList;
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
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.forms.exam.ExamSecuredMarksEntryForm;
import com.kp.cms.handlers.ajax.CommonAjaxExamHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.exam.ExamMarksEntryHandler;
import com.kp.cms.handlers.exam.ExamSecuredMarksEntryHandler;
import com.kp.cms.handlers.exam.ExamSecuredMarksVerificationHandler;
import com.kp.cms.to.exam.ExamSecuredMarksEntryTO;
import com.kp.cms.utilities.CommonUtil;

@SuppressWarnings("deprecation")
public class ExamSecuredMarksEntryAction extends BaseDispatchAction {
	ExamSecuredMarksEntryHandler handler = new ExamSecuredMarksEntryHandler();
	ActionErrors errors = new ActionErrors();
	ActionMessages messages = new ActionMessages();

	public ActionForward initExamSecuredMarksEntry(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		errors.clear();
		ExamSecuredMarksEntryForm objform = (ExamSecuredMarksEntryForm) form;
		objform.clearPage(mapping, request);
		objform.setsCodeName("sCode");
		//objform.setSubjectList(handler.getSubjectList());
		objform.setIsPreviousExam(null);
		objform.setExamType("Regular");
		setRequestedDataToForm(objform, request);
		int examId = 0;
		if(objform.getExamId()!= null &&!objform.getExamId().trim().isEmpty()){
			examId = Integer.parseInt(objform.getExamId());
		}
		objform.setSubjectList(new CommonAjaxExamHandler().getSubjectCodeName(objform.getsCodeName(), examId));		
		return mapping.findForward(CMSConstants.EXAM_SECURED_MARKS_ENTRY);
	}
	
	public void setRequestedDataToForm(ExamSecuredMarksEntryForm objform,HttpServletRequest request) throws Exception {
		String currentExam = null;
		ExamMarksEntryHandler examhandler = new ExamMarksEntryHandler();
		Map<Integer, String> examMap = CommonAjaxHandler.getInstance().getExamNameByExamType(
				objform.getExamType());
		currentExam = examhandler.getCurrentExamName(objform.getExamType());	
		if (currentExam != null) {
			objform.setExamId(currentExam);
			
			request.setAttribute("subjectType", currentExam);
		} else {
			request.setAttribute("subjectType", 0);
			objform.setExamId(null);
		}
		//objform.setExamNameList(examNameMap);
		if(examMap!= null){
			request.setAttribute("examMap", examMap);
		}
		
	}

	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamSecuredMarksEntryForm objform = (ExamSecuredMarksEntryForm) form;
		errors.clear();
		messages.clear();
		errors = objform.validate(mapping, request);
		errors = validateEvaluatorAndAnswerScript(objform);
		if(objform.getExamType()!= null && objform.getExamType().equalsIgnoreCase("Supplementary") && (objform.getSchemeNo() == null|| objform.getSchemeNo().trim().isEmpty())){
			errors.add("error", new ActionError("knowledgepro.fee.semister.required"));
		}
		
		saveErrors(request, errors);

		if (errors.isEmpty()) {

			objform.setExamId(objform.getExamId());
			int examId = 0;
			if (objform.getExamId() != null && objform.getExamId().length() > 0){
				objform.setExamName(handler.getExamNameByExamId(Integer
						.parseInt(objform.getExamId())));
				examId = Integer.parseInt(objform.getExamId());
			}
			
			int subId = 0;
			if (objform.getSubject() != null
					&& objform.getSubject().length() > 0) {
				subId = Integer.parseInt(objform.getSubject());
				String sname = handler.getSubjectName(subId);
				objform.setSubjectName(sname);
				objform.setSubject(objform.getSubject());
				String subCode = handler.getSubjectCode(subId);
				objform.setSubjectCode(subCode);
			}
			
			if (objform.getSubjectType() != null
					&& objform.getSubjectType().length() > 0) {
				if (objform.getSubjectType().equals("T")
						|| objform.getSubjectType().equals("t")) {
					objform.setSubjectType("Theory");
					objform.setSubjectTypeId(1);
				}
				if (objform.getSubjectType().equals("P")
						|| objform.getSubjectType().equals("p")) {
					objform.setSubjectType("Practical");
					objform.setSubjectTypeId(0);
				}
				if (objform.getSubjectType().equals("B")
						|| objform.getSubjectType().equals("b")) {
					objform.setSubjectType("Theory&Practical");
					objform.setSubjectTypeId(11);
				}
			}
			objform.setEvaluatorType(objform.getEvaluatorType());

			objform.setAnswerScriptType(objform.getAnswerScriptType());
			objform.setRegNoOrRollNumber(handler
					.getSecured_marks_Entry_By_Settings());

			objform.setListSingleStudents(handler.getSingleStudentMarks());
			int evaluatorId = 0;
			int scriptId = 0;
			if(objform.getEvaluatorType()!= null && !objform.getEvaluatorType().trim().isEmpty()){
				evaluatorId = Integer.parseInt(objform.getEvaluatorType()); 
			}
			if(objform.getAnswerScriptType()!= null && !objform.getAnswerScriptType().trim().isEmpty()){
				scriptId = Integer.parseInt(objform.getAnswerScriptType());
			}
			if(objform.getSubjectType().equalsIgnoreCase("Theory")){
				objform.setRegCount(handler.getRegisterNoCount(examId, subId, objform.getSubjectTypeId(), evaluatorId, scriptId, true));
			}
			else{
				objform.setRegCount(handler.getRegisterNoCount(examId, subId, objform.getSubjectTypeId(), evaluatorId, scriptId, false));
			}
			
			
			//-------------
			if (CommonUtil.checkForEmpty(objform.getSubjectType())) {
				int intSubjectTypeId = objform.getSubjectType().equalsIgnoreCase(
						"theory") ? 1 : 0;
				if (CommonUtil.checkForEmpty(objform.getSubject())) {
					objform.setListEvaluatorType(handler
							.getEvaluatorType(Integer
									.parseInt(objform.getSubject()),
									intSubjectTypeId, Integer.parseInt(objform
											.getExamId())));
					objform.setListAnswerScriptType(handler
							.get_answerScript_type(Integer.parseInt(objform
									.getSubject()), intSubjectTypeId, Integer
									.parseInt(objform.getExamId())));
				}
			}
			//-----------------
			
			
			return mapping
					.findForward(CMSConstants.EXAM_SECURED_MARKS_ENTRY_SUBMIT);
		} else {
			objform = retainvalues(objform);
			request.setAttribute("retainValues", "retain");
		}
		return mapping.findForward(CMSConstants.EXAM_SECURED_MARKS_ENTRY);
	}

	private ActionErrors validateEvaluatorAndAnswerScript(
			ExamSecuredMarksEntryForm objform) {
		ExamSecuredMarksVerificationHandler verificationHandler = new ExamSecuredMarksVerificationHandler();
		if (objform.getExamType() != null
				&& !objform.getExamType().equalsIgnoreCase("Internal")) {
			if (objform.getValidationET().equalsIgnoreCase("yes")) {
				if (!CommonUtil.checkForEmpty(objform.getEvaluatorType())
						&& verificationHandler.getEvaluatorType_Status(objform
								.getSubject(), objform.getSubjectType(),
								objform.getExamId())) {

					errors.add("error", new ActionError(
							"knowledgepro.exam.evaluatorTypeReq"));
				}
			}
			if (objform.getValidationAST().equalsIgnoreCase("yes")) {
				if (!CommonUtil.checkForEmpty(objform.getAnswerScriptType())
						&& verificationHandler.getAnswerScriptType_Status(
								objform.getSubject(), objform.getSubjectType(),
								objform.getExamId())) {
					errors.add("error", new ActionError(
							"knowledgepro.exam.answerScriptTypeReq"));
				}
			}
		}
		return errors;
	}

	public ActionForward view(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamSecuredMarksEntryForm objform = (ExamSecuredMarksEntryForm) form;
		errors.clear();
		messages.clear();
		errors = objform.validate(mapping, request);
		saveErrors(request, errors);
		if (errors.isEmpty()) {
			objform.setExamId(objform.getExamId());
			if (objform.getExamId() != null && objform.getExamId().length() > 0)
				objform.setExamName(handler.getExamNameByExamId(Integer
						.parseInt(objform.getExamId())));

			int subId = 0;
			if (objform.getSubject() != null
					&& objform.getSubject().length() > 0) {
				subId = Integer.parseInt(objform.getSubject());
				String sname = handler.getSubjectName(subId);
				objform.setSubjectName(sname);
				objform.setSubject(objform.getSubject());
				String subCode = handler.getSubjectCode(subId);
				objform.setSubjectCode(subCode);
			}
			if (objform.getSubjectType() != null
					&& objform.getSubjectType().length() > 0) {
				if (objform.getSubjectType().equals("T")
						|| objform.getSubjectType().equals("t")) {
					objform.setSubjectType("Theory");
					objform.setSubjectTypeId(1);
				}
				if (objform.getSubjectType().equals("P")
						|| objform.getSubjectType().equals("p")) {
					objform.setSubjectType("Practical");
					objform.setSubjectTypeId(0);
				}
				if (objform.getSubjectType().equals("B")
						|| objform.getSubjectType().equals("b")) {
					objform.setSubjectType("Theory&Practical");
					objform.setSubjectTypeId(11);
				}
			}
			objform.setEvaluatorType(objform.getEvaluatorType());

			objform.setAnswerScriptType(objform.getAnswerScriptType());
			objform.setRegNoOrRollNumber(handler
					.getSecured_marks_Entry_By_Settings());

			objform.setListSingleStudentsView(handler.getSingleStudentMarks());

		} else {
			objform = retainvalues(objform);
			request.setAttribute("retainValues", "retain");
			objform.setSubjectList(handler.getSubjectList());

			return mapping.findForward(CMSConstants.EXAM_SECURED_MARKS_ENTRY);
		}
		return mapping.findForward(CMSConstants.EXAM_SECURED_MARKS_ENTRY_VIEW);
	}


	public ActionForward onSubmit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamSecuredMarksEntryForm objform = (ExamSecuredMarksEntryForm) form;
		errors.clear();
		messages.clear();
		errors = validateData(objform, errors);
		saveErrors(request, errors);
		if (errors.isEmpty()) {
			try {
				int added = handler.addChanges(objform);
				int examId = 0;
				if (objform.getExamId() != null && objform.getExamId().length() > 0){
					objform.setExamName(handler.getExamNameByExamId(Integer
							.parseInt(objform.getExamId())));
					examId = Integer.parseInt(objform.getExamId());
				}
				int subId = 0;
				if (objform.getSubject() != null
						&& objform.getSubject().length() > 0) {
					subId = Integer.parseInt(objform.getSubject());
				}
				int evaluatorId = 0;
				int scriptId = 0;
				if(objform.getEvaluatorType()!= null && !objform.getEvaluatorType().trim().isEmpty()){
					evaluatorId = Integer.parseInt(objform.getEvaluatorType()); 
				}
				if(objform.getAnswerScriptType()!= null && !objform.getAnswerScriptType().trim().isEmpty()){
					scriptId = Integer.parseInt(objform.getAnswerScriptType());
				}
				if(objform.getSubjectType().equalsIgnoreCase("Theory")){
					objform.setRegCount(handler.getRegisterNoCount(examId, subId, objform.getSubjectTypeId(), evaluatorId, scriptId, true));
				}else{
					objform.setRegCount(handler.getRegisterNoCount(examId, subId, objform.getSubjectTypeId(), evaluatorId, scriptId, false));
				}
				if (added > 0) {
					ActionMessage message = new ActionMessage("knowledgepro.exam.ExamMarksEntry.Students.updatesuccess");
					messages.add("messages", message);
				}
				saveMessages(request, messages);
			} catch (DuplicateException e) {
				errors.add("error", new ActionError("knowledgepro.exam.duplicateEntry",e.getMessage()));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.EXAM_SECURED_MARKS_ENTRY_SUBMIT);
			} catch (BusinessException e) {
				errors.add("error", new ActionError("knowledgepro.exam.duplicateEntry",e.getMessage()));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.EXAM_SECURED_MARKS_ENTRY_SUBMIT);
			}
		} else {
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.EXAM_SECURED_MARKS_ENTRY_SUBMIT);
		}
		//objform.clearPage(mapping, request);
		//objform.setSubjectList(handler.getSubjectList());
		//return mapping.findForward(CMSConstants.EXAM_SECURED_MARKS_ENTRY);
		objform.setListSingleStudents(handler.getSingleStudentMarks());
		objform.setSubjectList(handler.getSubjectList());
		return mapping.findForward(CMSConstants.EXAM_SECURED_MARKS_ENTRY_SUBMIT);

	}

	public ActionForward viewUpdate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamSecuredMarksEntryForm objform = (ExamSecuredMarksEntryForm) form;
		errors.clear();
		saveErrors(request, errors);
		if (errors.isEmpty()) {

			try {
				int viewUpdated = handler.addViewChanges(objform);
				if (viewUpdated > 0) {
					ActionMessage message = new ActionMessage(
							"knowledgepro.exam.ExamMarksEntry.Students.updatesuccess");
					messages.add("messages", message);
				}
				saveMessages(request, messages);
			} catch (Exception e) {

				errors.add("error", new ActionError(e.getMessage(),
						"knowledgepro.exam.duplicateEntry"));
				saveErrors(request, errors);
			}
		} else {
			return mapping
					.findForward(CMSConstants.EXAM_SECURED_MARKS_ENTRY_SUBMIT);
		}
		objform.clearPage(mapping, request);
		objform.setSubjectList(handler.getSubjectList());
		return mapping.findForward(CMSConstants.EXAM_SECURED_MARKS_ENTRY);

	}

	private ActionErrors validateData(ExamSecuredMarksEntryForm objform, ActionErrors errors ) {
		ArrayList<ExamSecuredMarksEntryTO> toList = objform
				.getListSingleStudents();
		String subjectType = objform.getSubjectType();
		for (ExamSecuredMarksEntryTO to : toList) {
			if (to.getRegisterNo() != null
					&& to.getRegisterNo().trim().length() > 0) {
				if (subjectType.equalsIgnoreCase("Theory")) {
					if (!(checkForEmpty(to.getTheoryMarks()))
							|| !checkForEmpty(to.getTheoryMarks())) {
						errors.add("error", new ActionError(
								"knowledgepro.exam.marksRequired"));
						break;
					}
				} else {
					if (!(checkForEmpty(to.getPracticalMarks()))
							|| !checkForEmpty(to.getPracticalMarks())) {
						errors.add("error", new ActionError(
								"knowledgepro.exam.marksRequired"));
						break;
					}
				}

				if ((to.getMarksError() != null && to.getMarksError().trim()
						.length() > 0)
						|| (to.getRegisterNoError() != null && to
								.getRegisterNoError().trim().length() > 0)) {
					errors.add("error", new ActionError(
							"knowledgepro.exam.removeErrors"));
					break;
				}

			}
			if (subjectType.equalsIgnoreCase("Theory")) {
				if (to.getTheoryMarks() != null
						&& to.getTheoryMarks().trim().length() > 0) {
					if (splCharValidation(to.getTheoryMarks())) {
						errors.add("error", new ActionError(
								"knowledgepro.exam.Revaluation.splChar"));
					}

				}

			} else {

				if (to.getPracticalMarks() != null
						&& to.getPracticalMarks().trim().length() > 0) {
					if (splCharValidation(to.getPracticalMarks())) {
						errors.add("error", new ActionError(
								"knowledgepro.exam.Revaluation.splChar"));
					}

				}

			}
		}
		return errors;
	}

	private ExamSecuredMarksEntryForm retainvalues(
			ExamSecuredMarksEntryForm objform) throws Exception {
		objform = handler.retainAllValues(objform);

		return objform;
	}

	public boolean checkForEmpty(String check) {
		boolean flag = false;
		if (check != null && check.trim().length() > 0) {
			flag = true;
		}
		return flag;
	}

	private boolean splCharValidation(String name) {
		boolean haveSplChar = false;
		Pattern pattern = Pattern.compile("[^0-9a-zA-Z]\\.+");
		Matcher matcher = pattern.matcher(name);
		haveSplChar = matcher.find();
		return haveSplChar;

	}
}
