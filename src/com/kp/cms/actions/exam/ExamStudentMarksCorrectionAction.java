package com.kp.cms.actions.exam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
import com.kp.cms.exceptions.DataNotFoundException;
import com.kp.cms.forms.exam.ExamStudentMarksCorrectionForm;
import com.kp.cms.handlers.exam.ExamMarksEntryHandler;
import com.kp.cms.handlers.exam.ExamStudentMarksCorrectionHandler;
import com.kp.cms.to.exam.ExamStudentMarksCorrectionAllstudentsTO;
import com.kp.cms.to.exam.ExamStudentMarksCorrectionSingleStudentTO;
import com.kp.cms.to.exam.ExamStudentMarksCorrectionTO;
import com.kp.cms.transactionsimpl.exam.ExamSecuredMarksEntryImpl;
import com.kp.cms.utilities.CommonUtil;

@SuppressWarnings("deprecation")
public class ExamStudentMarksCorrectionAction extends BaseDispatchAction {
	ExamStudentMarksCorrectionHandler handler = new ExamStudentMarksCorrectionHandler();
	ActionErrors errors = new ActionErrors();
	ActionMessages messages = new ActionMessages();

	// Initialization
	public ActionForward initExamStudentMarksCorrection(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamStudentMarksCorrectionForm objform = (ExamStudentMarksCorrectionForm) form;
		errors = objform.validate(mapping, request);
		setUserId(request, objform);
		objform.clearPage(mapping, request);
		return mapping.findForward(CMSConstants.EXAM__STUDENT_MARKS_CORRECTION);
	}

	// To get all students' single subject marks
	public ActionForward getSubjects(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamStudentMarksCorrectionForm objform = (ExamStudentMarksCorrectionForm) form;
		if (objform.getMarksEntryFor().contains("Single Student")) {
			objform.setScheme(objform.getSchemeId());
			objform.setSchemeId("");
			objform.setDupsecured("on");
		}
		errors.clear();
		messages.clear();
		errors = objform.validate(mapping, request);
		errors = validateData(objform);
		//errors = validateEvaluatorAndAnswerScript(objform);
		saveErrors(request, errors);
		setUserId(request, objform);
		if (errors.isEmpty()) {
			if (objform.getMarksEntryFor().contains("Single Student")) {
				objform = handler.getStudentDetails(objform);
				request.setAttribute("examMarksEntryOperation", "edit");
				getEvaluatorList(Integer.parseInt(objform.getScheme()), objform.getExamNameId(), request);
				
				Integer courseId = null;
				if(objform.getCourse()!= null && !objform.getCourse().trim().isEmpty()){
					courseId = Integer.parseInt(objform.getCourse());
				}
				getAnswerScriptType(Integer.parseInt(objform.getScheme()), 
						objform.getExamNameId(), courseId, request);
				/*if (objform.getMarkType().equals("Internal overAll")
						|| objform.getMarkType().equals("Regular overAll")) {
					return mapping
							.findForward(CMSConstants.EXAM__STUDENT_MARKS_CORRECTION_SINGLE_STUDENT_INTERNAL_OVERALL);
				}
			*/
				return mapping
						.findForward(CMSConstants.EXAM__STUDENT_MARKS_CORRECTION_SINGLE_STUDENT);
			} else {
				objform = handler.getUpdatableForm(objform);
				int subjectId = Integer.parseInt(objform.getSubjectId());
				int examId = objform.getExamNameId();
				int courseId = 0; 
					if(objform.getCourseId()!= null && !objform.getCourseId().trim().isEmpty()){
					   courseId = Integer.parseInt(objform.getCourseId());
					}
				int schemeId = Integer.parseInt(objform.getScheme());
				int secured = 0;
				Integer evaluatorId = null, answerScriptId = null;
				if (objform.getEvaluatorType() != null
						&& objform.getEvaluatorType().trim().length() > 0) {
					evaluatorId = Integer.parseInt(objform.getEvaluatorType());
				}
				if (objform.getAnswerScriptType() != null
						&& objform.getAnswerScriptType().trim().length() > 0) {
					answerScriptId = Integer.parseInt(objform
							.getAnswerScriptType());
				}
				
				
				objform.setSecured(Integer.toString(secured));
				ArrayList<ExamStudentMarksCorrectionAllstudentsTO> list = handler
						.getSingleSubjFor_AllStudents(courseId, subjectId,
								examId, answerScriptId, evaluatorId, secured,
								objform.getMarkType(), schemeId);
				objform.setSingleSubjFor_AllStudents(list);
				objform.setOriginalSingleSubjFor_AllStudents(list);

				if (objform.getMarkType().equals("Internal overAll")
						|| objform.getMarkType().equals("Regular overAll")) {
					return mapping
							.findForward(CMSConstants.EXAM__STUDENT_MARKS_CORRECTION_ALL_STUDENT_INTERNAL_OVERALL);
				}

				return mapping
						.findForward(CMSConstants.EXAM__STUDENT_MARKS_CORRECTION_ALL_STUDENT);
			}

		} else {
			objform = retainValues(objform);
			request.setAttribute("retainValues", "yes");
			/*if (objform.getMarkType().equals("Internal overAll")
					|| objform.getMarkType().equals("Regular overAll")) {
				return mapping
						.findForward(CMSConstants.EXAM__STUDENT_MARKS_CORRECTION_SINGLE_STUDENT_INTERNAL_OVERALL);
			}*/
			return mapping
					.findForward(CMSConstants.EXAM__STUDENT_MARKS_CORRECTION);
		}

	}

	private ExamStudentMarksCorrectionForm retainValues(
			ExamStudentMarksCorrectionForm objform) throws Exception {
		objform = handler.retainAllValues(objform);
		return objform;
	}

	private ActionErrors validateData(ExamStudentMarksCorrectionForm objform) {
		if (objform.getMarksEntryFor().contains("All Students")) {
			if (!(objform.getSubject() != null && objform.getSubject().trim()
					.length() > 0)) {
				errors.add("error", new ActionError(
						"knowledgepro.exam.MarksCorrection.Subject"));
			}

		}

		return errors;
	}

	// To get single student' all subject marks
	public ActionForward getStudentDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamStudentMarksCorrectionForm objform = (ExamStudentMarksCorrectionForm) form;
		errors.clear();
		messages.clear();
		errors = objform.validate(mapping, request);
		errors = validateSingleStudentData(objform);
		saveErrors(request, errors);
		setUserId(request, objform);
		if (errors.isEmpty()) {
			try {
				Integer evaluatorId = null, answerScriptId = null;
				if (objform.getEvaluatorType() != null
						&& objform.getEvaluatorType().trim().length() > 0) {
					evaluatorId = Integer.parseInt(objform.getEvaluatorType());
				}
				if (objform.getAnswerScriptType() != null
						&& objform.getAnswerScriptType().trim().length() > 0) {
					answerScriptId = Integer.parseInt(objform
							.getAnswerScriptType());
				}
				int secured = 0;
				if (objform.getSecured() != null
						&& objform.getSecured().equals("on")) {
					secured = 1;
				}
				Integer courseId = 0;
				if(objform.getCourseId()!= null && !objform.getCourseId().isEmpty()){
					courseId = Integer.parseInt(objform.getCourseId());
				}
				ExamStudentMarksCorrectionTO to = handler
				.getAllSubjFor_OneStudent(/*Integer.parseInt(objform
						.getCourseId())*/courseId, objform.getRollNo().trim(),
						objform.getRegNo().trim(), objform
								.getExamNameId(), Integer
								.parseInt(objform.getScheme()),
						answerScriptId, evaluatorId, secured, objform
								.getMarkType(), objform.getIsPrevExam() );
				objform.setSingleStuFor_AllSubjects(to.getListMarksDetails());
				objform.setOriginalSingleStuFor_AllSubjects(to
						.getListMarksDetails());
				objform.setStudentName(to.getStudentName());
				objform.setMarksCardNo(to.getMarksCardNo());
				objform.setStudentId(to.getStudentId());
				//objform.setSecured(Integer.toString(secured));
				objform.setMarkType(objform.getMarkType());
				

				if ((to.getId() != null)) {
					objform.setId(to.getId().toString());
				}
				objform.setListStudentDetailsSize(1);
				request.setAttribute("examMarksEntryOperation", "edit");
				
				getEvaluatorList(Integer.parseInt(objform.getScheme()), objform.getExamNameId(), request);
				if(objform.getCourse()!= null && !objform.getCourse().trim().isEmpty()){
					courseId = Integer.parseInt(objform.getCourse());
				}
				getAnswerScriptType(Integer.parseInt(objform.getScheme()), 
						objform.getExamNameId(), courseId, request);

				
				/*if (objform.getMarkType().equals("Internal overAll")
						|| objform.getMarkType().equals("Regular overAll")) {
					return mapping
							.findForward(CMSConstants.EXAM__STUDENT_MARKS_CORRECTION_SINGLE_STUDENT_INTERNAL_OVERALL);
				}*/
				objform.setDupsecured(objform.getSecured());
				objform.setSecured(null);
				return mapping
						.findForward(CMSConstants.EXAM__STUDENT_MARKS_CORRECTION_SINGLE_STUDENT);
			} catch (DataNotFoundException e) {
				getEvaluatorList(Integer.parseInt(objform.getScheme()), objform.getExamNameId(), request);
				Integer courseId = null;
				if(objform.getCourseId()!= null && !objform.getCourseId().isEmpty()){
					courseId = Integer.parseInt(objform.getCourseId());
				}
				if(objform.getCourse()!= null && !objform.getCourse().trim().isEmpty()){
					courseId = Integer.parseInt(objform.getCourse());
				}
				getAnswerScriptType(Integer.parseInt(objform.getScheme()), 
						objform.getExamNameId(), courseId, request);
				objform.setDupsecured(objform.getSecured());
				objform.setSecured(null);
				errors.add("error", new ActionError(
						"knowledgepro.exam.MarksCorrection.DataNotFound"));
			} catch (BusinessException ex) {
				getEvaluatorList(Integer.parseInt(objform.getScheme()), objform.getExamNameId(), request);
				Integer courseId = null;
				if(objform.getCourseId()!= null && !objform.getCourseId().isEmpty()){
					courseId = Integer.parseInt(objform.getCourseId());
				}
				if(objform.getCourse()!= null && !objform.getCourse().trim().isEmpty()){
					courseId = Integer.parseInt(objform.getCourse());
				}
				getAnswerScriptType(Integer.parseInt(objform.getScheme()), 
						objform.getExamNameId(), courseId, request);
				errors.add("error", new ActionError(
						"knowledgepro.exam.MarksCorrection.Inconsistent"));

			} catch (NullPointerException e) {
				getEvaluatorList(Integer.parseInt(objform.getScheme()), objform.getExamNameId(), request);
				Integer courseId = null;
				if(objform.getCourseId()!= null && !objform.getCourseId().isEmpty()){
					courseId = Integer.parseInt(objform.getCourseId());
				}
				if(objform.getCourse()!= null && !objform.getCourse().trim().isEmpty()){
					courseId = Integer.parseInt(objform.getCourse());
				}
				getAnswerScriptType(Integer.parseInt(objform.getScheme()), 
						objform.getExamNameId(), courseId, request);
				errors.add("error", new ActionError(
						"knowledgepro.exam.MarksCorrection.DataNotFound"));
				e.printStackTrace();

			} finally {

				if (!errors.isEmpty()) {
					saveErrors(request, errors);
					objform.setListStudentDetailsSize(0);
					getEvaluatorList(Integer.parseInt(objform.getScheme()), objform.getExamNameId(), request);
					request.setAttribute("examMarksEntryOperation", "edit");
				}
			}

		}
		getEvaluatorList(Integer.parseInt(objform.getScheme()), objform.getExamNameId(), request);
		Integer courseId = null;
		if(objform.getCourseId()!= null && !objform.getCourseId().isEmpty()){
			courseId = Integer.parseInt(objform.getCourseId());
		}
		if(objform.getCourse()!= null && !objform.getCourse().trim().isEmpty()){
			courseId = Integer.parseInt(objform.getCourse());
		}
		getAnswerScriptType(Integer.parseInt(objform.getScheme()), 
				objform.getExamNameId(), courseId, request);
		objform.setListStudentDetailsSize(0);
		request.setAttribute("examMarksEntryOperation", "edit");
		objform.setDupsecured(objform.getSecured());
		objform.setSecured(null);
		return mapping
				.findForward(CMSConstants.EXAM__STUDENT_MARKS_CORRECTION_SINGLE_STUDENT);

	}

	private ActionErrors validateSingleStudentData(
			ExamStudentMarksCorrectionForm objform) {
		if (!((objform.getRegNo() != null && objform.getRegNo().trim().length() > 0) || (objform
				.getRollNo() != null && objform.getRollNo().length() > 0))) {
			errors.add("error", new ActionError(
					"knowledgepro.exam.MarksCorrection.regNoOrrollNumber"));

		}
		return errors;
	}

	public ActionForward addOrUpdateAllStudentMarks(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ExamStudentMarksCorrectionForm objform = (ExamStudentMarksCorrectionForm) form;
		errors.clear();
		messages.clear();
		errors = validateAllStudentCommentsData(objform);
		saveErrors(request, errors);
		setUserId(request, objform);
		if (errors.isEmpty()) {

			if (objform.getMarkType().equals("Internal overAll")
					|| objform.getMarkType().equals("Regular overAll")) {
				handler.updateSingleSubj_AllStudentsOverall(objform);
			} else {
				handler.updateSingleSubj_AllStudents(objform);
			}
			ActionMessage message = new ActionMessage(
					"knowledgepro.exam.ExamMarksEntry.Students.updatesuccess");
			messages.add("messages", message);
			saveMessages(request, messages);
			objform.clearPage(mapping, request);
			return mapping
					.findForward(CMSConstants.EXAM__STUDENT_MARKS_CORRECTION);
		}
		return mapping
				.findForward(CMSConstants.EXAM__STUDENT_MARKS_CORRECTION_ALL_STUDENT);

	}

	private ActionErrors validateAllStudentCommentsData(
			ExamStudentMarksCorrectionForm objform) {
		int count = 0;
		ArrayList<ExamStudentMarksCorrectionAllstudentsTO> updatedTo = objform
				.getSingleSubjFor_AllStudents();
		ArrayList<ExamStudentMarksCorrectionAllstudentsTO> originalTo = objform
				.getOriginalSingleSubjFor_AllStudents();
		for (ExamStudentMarksCorrectionAllstudentsTO updated : updatedTo) {
			ExamStudentMarksCorrectionAllstudentsTO originalToObject = originalTo
					.get(count++);
			if (checkTheoryMarksAltered(originalToObject, updated)
					|| checkPracticalMarksAltered(originalToObject, updated)) {
				if (!(updated.getComments() != null && updated.getComments()
						.trim().length() > 0))
					errors.add("error", new ActionError(
							"knowledgepro.exam.MarksCorrection.comments"));
				break;
			}
		}
		return errors;
	}

	private boolean checkPracticalMarksAltered(
			ExamStudentMarksCorrectionAllstudentsTO originalToObject,
			ExamStudentMarksCorrectionAllstudentsTO updated) {
		if (updated.getPracticalMarks() != null
				&& updated.getPracticalMarks().trim().length() > 0) {
			if (originalToObject.getPracticalMarks() != null
					&& !updated.getPracticalMarks().equals(
							originalToObject.getPracticalMarks())) {
				return true;
			}
		}
		return false;
	}

	private boolean checkTheoryMarksAltered(
			ExamStudentMarksCorrectionAllstudentsTO originalToObject,
			ExamStudentMarksCorrectionAllstudentsTO updated) {
		if (updated.getTheoryMarks() != null
				&& updated.getTheoryMarks().trim().length() > 0) {
			if (originalToObject.getTheoryMarks() != null
					&& !updated.getTheoryMarks().equals(
							originalToObject.getTheoryMarks())) {
				return true;
			}
		}
		return false;
	}

	// add single student marks
	public ActionForward addSingleStudent(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamStudentMarksCorrectionForm objform = (ExamStudentMarksCorrectionForm) form;
		errors.clear();
		messages.clear();
		errors = objform.validate(mapping, request);
		errors = validateSingleStudentComments(objform);
		saveErrors(request, errors);
		setUserId(request, objform);
		
		
		
		//if (errors.isEmpty()) {
			try {
				
				if(objform.getOriginalSingleStuFor_AllSubjects()!= null){
					Iterator<ExamStudentMarksCorrectionSingleStudentTO> itr = objform.getOriginalSingleStuFor_AllSubjects().iterator();
					while (itr.hasNext()) {
						ExamStudentMarksCorrectionSingleStudentTO examStudentMarksCorrectionSingleStudentTO = (ExamStudentMarksCorrectionSingleStudentTO) itr
								.next();
						if(examStudentMarksCorrectionSingleStudentTO.getTheoryMarks()!= null && splCharValidation(examStudentMarksCorrectionSingleStudentTO.getTheoryMarks().trim())){
							errors.add("error", new ActionError("knowledgepro.admin.special"));
							saveErrors(request, errors);
							break;
						}
						else if (examStudentMarksCorrectionSingleStudentTO.getPracticalMarks()!= null && splCharValidation(examStudentMarksCorrectionSingleStudentTO.getPracticalMarks().trim())){
							errors.add("error", new ActionError("knowledgepro.admin.special"));	
							saveErrors(request, errors);
							break;
						}
						
					}
				}
				
				if(!errors.isEmpty()){
					objform.setDupsecured(objform.getSecured());
					objform.setSecured(null);
					return mapping.findForward(CMSConstants.EXAM__STUDENT_MARKS_CORRECTION_SINGLE_STUDENT);
				}
				
				ArrayList<ExamStudentMarksCorrectionSingleStudentTO> listDetailsTO = objform
						.getSingleStuFor_AllSubjects();
				Integer examMarksEntryId = null;
				if (objform.getId() != null && !(objform.getId().equals(""))) {

					examMarksEntryId = Integer.parseInt(objform.getId());
				}
				Integer examId = objform.getExamNameId();
				Integer studentId = objform.getStudentId();
				Integer evaluatorTypeId = null;

				String userId = objform.getUserId();
				Integer answerScriptTypeId = null;
				if (objform.getEvaluatorType()!= null && !objform.getEvaluatorType().trim().isEmpty()){
					evaluatorTypeId = Integer.parseInt(objform
							.getEvaluatorType());
				}
				if (objform.getAnswerScriptType()!= null && !objform.getAnswerScriptType().trim().isEmpty()){
					answerScriptTypeId = Integer.parseInt(objform
							.getAnswerScriptType());
				}
				String marksCardNo = objform.getMarksCardNo();
				boolean secured = false;
				if (objform.getSecured() != null
						&& objform.getSecured().equals("on")) {
					secured = true;
				}
				if (objform.getMarkType().equals("Internal overAll")
						|| objform.getMarkType().equals("Regular overAll")) {

						int courseId = 0;
						if(objform.getCourseId()!= null && !objform.getCourseId().trim().isEmpty()){
							courseId = Integer.parseInt(objform.getCourseId());
						}
						int schemeId = 0;
						if(objform.getSchemeId()!= null && !objform.getSchemeId().trim().isEmpty()){
							schemeId = Integer.parseInt(objform.getSchemeId());
						}
						handler.updateAllSubjFor_OneStudentOverall(
								examMarksEntryId, examId, studentId, objform
										.getMarkType(), listDetailsTO, userId,
								/*Integer.parseInt(objform.getCourseId())*/courseId,
								/*Integer.parseInt(objform.getSchemeId())*/schemeId);
					
				} else {

					handler.updateAllSubjFor_OneStudent(examMarksEntryId,
							examId, studentId, evaluatorTypeId,
							answerScriptTypeId, objform.getMarkType(),
							marksCardNo, listDetailsTO, userId, objform
									.getOriginalSingleStuFor_AllSubjects(), secured);
				}
				ActionMessage message = new ActionMessage(
						"knowledgepro.exam.ExamMarksEntry.Students.updatesuccess");
				messages.add("messages", message);

				saveMessages(request, messages);
				//objform.clearPage(mapping, request);
				objform.setRegNo(null);
				objform.setStudentName(null);
				objform.setSingleStuFor_AllSubjects(null);
				getEvaluatorList(Integer.parseInt(objform.getScheme()), objform.getExamNameId(), request);
				Integer courseId = null;
				if(objform.getCourseId()!= null && !objform.getCourseId().isEmpty()){
					courseId = Integer.parseInt(objform.getCourseId());
				}
				if(objform.getCourse()!= null && !objform.getCourse().trim().isEmpty()){
					courseId = Integer.parseInt(objform.getCourse());
				}
				getAnswerScriptType(Integer.parseInt(objform.getScheme()), 
						objform.getExamNameId(), courseId, request);
				objform.setDupsecured("on");
				/*return mapping
						.findForward(CMSConstants.EXAM__STUDENT_MARKS_CORRECTION);*/
				return mapping.findForward(CMSConstants.EXAM__STUDENT_MARKS_CORRECTION_SINGLE_STUDENT);
				
			} catch (BusinessException e) {

				errors.add("error", new ActionError(
						"knowledgepro.exam.ExamMarksEntry.Students.Fail"));
				saveErrors(request, errors);
			} catch (Exception e) {

				saveErrors(request, errors);
				objform.clearPage(mapping, request);
				return mapping
						.findForward(CMSConstants.EXAM__STUDENT_MARKS_CORRECTION);
			}

			finally {
				objform.setExamType("");
			}

		//}
		objform.setDupsecured(objform.getSecured());
		objform.setSecured(null);
		return mapping
				.findForward(CMSConstants.EXAM__STUDENT_MARKS_CORRECTION_SINGLE_STUDENT);

	}

	private ActionErrors validateSingleStudentComments(
			ExamStudentMarksCorrectionForm objform) {
		int count = 0;
		ArrayList<ExamStudentMarksCorrectionSingleStudentTO> updatedTo = objform
				.getSingleStuFor_AllSubjects();
		ArrayList<ExamStudentMarksCorrectionSingleStudentTO> originalTo = objform
				.getOriginalSingleStuFor_AllSubjects();
		for (ExamStudentMarksCorrectionSingleStudentTO updated : updatedTo) {
			ExamStudentMarksCorrectionSingleStudentTO originalToObject = originalTo
					.get(count++);
			if (checkTheoryMarksAltered(originalToObject, updated)
					|| checkPracticalMarksAltered(originalToObject, updated)) {
				if (!(updated.getComments() != null && updated.getComments()
						.trim().length() > 0))
					errors.add("error", new ActionError(
							"knowledgepro.exam.MarksCorrection.comments"));
			}
		}
		return errors;
	}

	private boolean checkPracticalMarksAltered(
			ExamStudentMarksCorrectionSingleStudentTO originalToObject,
			ExamStudentMarksCorrectionSingleStudentTO updated) {
		if (updated.getPracticalMarks() != null
				&& updated.getPracticalMarks().trim().length() > 0) {
			if (originalToObject.getPracticalMarks() != null
					&& !updated.getPracticalMarks().equals(
							originalToObject.getPracticalMarks())) {
				return true;
			}
		}
		return false;
	}

	private boolean checkTheoryMarksAltered(
			ExamStudentMarksCorrectionSingleStudentTO originalToObject,
			ExamStudentMarksCorrectionSingleStudentTO updated) {
		if (updated.getTheoryMarks() != null
				&& updated.getTheoryMarks().trim().length() > 0) {
			if (originalToObject.getTheoryMarks() != null
					&& !updated.getTheoryMarks().equals(
							originalToObject.getTheoryMarks())) {
				return true;
			}
		}
		return false;
	}

	// view old marks for single student
	public ActionForward viewOldMarks(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamStudentMarksCorrectionForm objform = (ExamStudentMarksCorrectionForm) form;
		setUserId(request, objform);

		handler.viewOldMarksForSingleStudent(objform);

		return mapping
				.findForward(CMSConstants.EXAM_VIEW_OLD_MARKS_CORRECTION_SINGLE_STUDENT);

	}

	// view old marks for single student
	
	public ActionForward viewOldMarksOverall(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamStudentMarksCorrectionForm objform = (ExamStudentMarksCorrectionForm) form;
		setUserId(request, objform);

		handler.viewOldMarksOverall(objform);

		return mapping
				.findForward(CMSConstants.EXAM_VIEW_OLD_MARKS_CORRECTION_SINGLE_STUDENT);

	}

	private ActionErrors validateEvaluatorAndAnswerScript(
			ExamStudentMarksCorrectionForm objform) {
		if (objform.getExamType() != null
				&& !objform.getExamType().equalsIgnoreCase("Internal")) {
			if (objform.getValidationET().equalsIgnoreCase("yes")) {

				if (!CommonUtil.checkForEmpty(objform.getEvaluatorType())) {
					errors.add("error", new ActionError(
							"knowledgepro.exam.evaluatorTypeReq"));
				}
			}
			if (objform.getValidationAST().equalsIgnoreCase("yes")) {
				if (!CommonUtil.checkForEmpty(objform.getAnswerScriptType())) {
					errors.add("error", new ActionError(
							"knowledgepro.exam.answerScriptTypeReq"));
				}
			}

		}
		return errors;
	}
	/**
	 * 
	 * @param scheme
	 * @param examId
	 * @param request
	 * @throws Exception
	 */
	private void getEvaluatorList(Integer scheme, Integer examId, HttpServletRequest request) throws Exception{
		ExamMarksEntryHandler securedhandler = new ExamMarksEntryHandler();
		HashMap<Integer, String> evaluatorList = securedhandler.get_evaluatorList_ruleSettings(
				null, scheme, null, null, examId);
		request.setAttribute("evaluatorMap", evaluatorList);
	}
	/**
	 * 
	 * @param scheme
	 * @param examId
	 * @param courseId
	 * @param request
	 * @throws Exception
	 */
	private void getAnswerScriptType(Integer scheme, Integer examId, Integer courseId, HttpServletRequest request) throws Exception{
		ExamMarksEntryHandler securedhandler = new ExamMarksEntryHandler();
		HashMap<Integer, String> answerScriptType = securedhandler.getListanswerScriptType(courseId,
				scheme, null, null, examId);
		request.setAttribute("answerScriptTypeMap", answerScriptType);
	}
	private boolean splCharValidation(String name) {
		ExamSecuredMarksEntryImpl impl = new ExamSecuredMarksEntryImpl();
		String splChar1 = impl.getCodeForAbsentEntry();
		String splChar2 = impl.getCodeForNotprocessEntry();
		boolean haveSplChar = false;
		Pattern pattern = Pattern.compile("[^0-9" + splChar1 + splChar2 + "." + "]+");
		Matcher matcher = pattern.matcher(name);
		haveSplChar = matcher.find();
		return haveSplChar;

	}
	public ActionForward clearListOnSecuredChange(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamStudentMarksCorrectionForm objform = (ExamStudentMarksCorrectionForm) form;
		objform.setSingleStuFor_AllSubjects(null);
		objform.setDupsecured(objform.getSecured());
		objform.setSecured(null);
		return mapping.findForward(CMSConstants.EXAM__STUDENT_MARKS_CORRECTION_SINGLE_STUDENT);
	}
	
	
}
