package com.kp.cms.actions.exam;

import java.math.BigDecimal;
import java.util.ArrayList;
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
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.exceptions.DataNotFoundException;
import com.kp.cms.forms.exam.ExamMarksEntryForm;
import com.kp.cms.handlers.ajax.CommonAjaxExamHandler;
import com.kp.cms.handlers.exam.ExamMarksEntryHandler;
import com.kp.cms.to.exam.ExamMarksEntryDetailsTO;
import com.kp.cms.to.exam.ExamMarksEntryStudentTO;
import com.kp.cms.to.exam.ExamMarksEntryTO;
import com.kp.cms.utilities.CommonUtil;

@SuppressWarnings("deprecation")
public class ExamMarksEntryAction extends BaseDispatchAction {
	ExamMarksEntryHandler handler = new ExamMarksEntryHandler();
	ActionErrors errors = new ActionErrors();
	ActionMessages messages = new ActionMessages();
	static HashMap<Integer, String> subjectTypeMap = new HashMap<Integer, String>();

	static {
		subjectTypeMap.put(1, "T");
		subjectTypeMap.put(0, "P");
		subjectTypeMap.put(11, "B");
	}

	public ActionForward initExamMarksEntry(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamMarksEntryForm objform = (ExamMarksEntryForm) form;
		errors = objform.validate(mapping, request);
		setUserId(request, objform);
		objform.setExamType("Regular");
		objform.setShowExamType("ShowAll");
		getExamNameListByExamType(objform);
		objform.clearPage(mapping, request);
		return mapping.findForward(CMSConstants.EXAM_MARKS_ENTRY);
	}

	public ActionForward getStudentMarksEntry(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamMarksEntryForm objform = (ExamMarksEntryForm) form;
		errors.clear();
		messages.clear();
		errors = objform.validate(mapping, request);
		errors = validateEvaluatorAndAnswerScript(objform);
		// saveErrors(request, errors);

		errors = validateSubjectType(objform);
		saveErrors(request, errors);
		setUserId(request, objform);
		if (errors.isEmpty()) {
			request.setAttribute("examMarksEntryOperation", "edit");
			if (objform.getMarksEntryFor().contains("Single Student")) {

				objform = handler.getStudentDetails(objform);
				objform.setRollNo(null);
				objform.setRegNo(null);
				return mapping
						.findForward(CMSConstants.EXAM_MARKS_ENTRY_SINGLE_STUDENT);
			} else {

				String subject = objform.getSubject();
				if (!(subject.equalsIgnoreCase("-select-") || subject
						.equals(""))) {
					String s = objform.getRollOrReg();
					if (s != null && s.trim().length() > 0) {
						objform = handler.getUpdatableForm(objform);
						int sub = Integer.parseInt(objform.getSubjectId());
						int subjectType = objform.getSubjectTypeId();
						int courseId = Integer.parseInt(objform.getCourseId());
						int schemeNo = Integer.parseInt(objform.getScheme());
						int classId = 0;
						if(objform.getSectionId()!= null && !objform.getSectionId().trim().isEmpty()){
							classId = Integer.parseInt(objform.getSectionId());
						}
						Integer evaluatorId = null, answerScriptId = null;
						if (objform.getEvaluatorType() != null
								&& objform.getEvaluatorType().trim().length() > 0) {
							evaluatorId = Integer.parseInt(objform
									.getEvaluatorType());
						}
						if (objform.getAnswerScriptType() != null
								&& objform.getAnswerScriptType().trim()
										.length() > 0) {
							answerScriptId = Integer.parseInt(objform
									.getAnswerScriptType());
						}
						ArrayList<ExamMarksEntryStudentTO> marksDetails = handler
								.getSingleSubjFor_AllStudents(courseId, sub,
										subjectType, objform.getRollOrReg(),
										objform.getExamNameId(),
										answerScriptId, evaluatorId, schemeNo,objform.getExamType(), classId);
						if (marksDetails.size() > 0) {

							ArrayList<ExamMarksEntryStudentTO> sourceMarks = marksDetails;
							ArrayList<ExamMarksEntryStudentTO> destMarks = new ArrayList<ExamMarksEntryStudentTO>();
							String originalSubjectType = subjectTypeMap
									.get(objform.getSubjectTypeId());
							for (ExamMarksEntryStudentTO sourceTo : sourceMarks) {
								sourceTo
										.setIsTheoryPractical(originalSubjectType);
								destMarks.add(sourceTo);
							}
							objform.setExamMarksEntryStudentTO(destMarks);
							return mapping
									.findForward(CMSConstants.EXAM_MARKS_ENTRY_ALL_STUDENT);

						} else {
							errors
									.add(
											"error",
											new ActionError(
													"knowledgepro.exam.ExamMarksEntry.Students.DataNotFound"));
							saveErrors(request, errors);
							objform.clearPage(mapping, request);
						}
					} else {
						errors
								.add(
										"error",
										new ActionError(
												"knowledgepro.exam.ExamMarksEntry.Students.regNoOrrollNumber"));
						saveErrors(request, errors);

					}
				} else {
					errors.add("error", new ActionError(
							"knowledgepro.exam.ExamMarksEntry.Subject"));
					saveErrors(request, errors);
					request.setAttribute("retainValues", "yes");
					objform = retainVals(objform);
				}
			}

		} else {
			objform.clearOnSearch(mapping, request);
			request.setAttribute("retainValues", "yes");
			objform = retainVals(objform);

			return mapping.findForward(CMSConstants.EXAM_MARKS_ENTRY);

		}

		return mapping.findForward(CMSConstants.EXAM_MARKS_ENTRY);
	}

	private ActionErrors validateSubjectType(ExamMarksEntryForm objform) {
		if (!objform.getMarksEntryFor().contains("Single Student")) {
			if (!(objform.getSubjectType() != null && objform.getSubjectType()
					.trim().length() > 0)) {
				errors.add("error", new ActionError(
						"knowledgepro.exam.ExamMarksEntry.Subject"));
			}

		}
		return errors;
	}

	public ActionForward getStudentDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamMarksEntryForm objform = (ExamMarksEntryForm) form;
		errors.clear();
		messages.clear();
		errors = objform.validate(mapping, request);
		saveErrors(request, errors);
		setUserId(request, objform);

		if (!objform.getRollNo().trim().equals("")
				|| !objform.getRegNo().trim().equals("")) {
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
				objform.setObjExamMarksEntryTO(handler
						.getAllSubjFor_OneStudent(Integer.parseInt(objform
								.getCourseId()), objform.getRollNo().trim(),
								objform.getRegNo().trim(), objform
										.getExamNameId(), Integer
										.parseInt(objform.getScheme()),
								answerScriptId, evaluatorId,objform.getExamType()));
				ExamMarksEntryTO to = objform.getObjExamMarksEntryTO();
				objform.setExamMarksEntryDetailsTO(to.getListMarksDetails());
				objform.setStudentName(to.getStudentName());
				objform.setMarksCardNo(to.getMarksCardNo());
				if (to.getMarksCardDate() != null) {
					objform.setMarksCardDate(CommonUtil
							.ConvertStringToDateFormat(to.getMarksCardDate()
									.toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
				}
				objform.setStudentId(to.getStudentId());
				objform.setClasscode(to.getClassCode());
				if ((to.getId() != null)) {
					objform.setId(to.getId().toString());
				} else {
					objform.setId("");
				}
				objform.setListStudentDetailsSize(1);
				request.setAttribute("examMarksEntryOperation", "edit");
			} catch (DataNotFoundException e) {
				errors
						.add(
								"error",
								new ActionError(
										"knowledgepro.exam.ExamMarksEntry.Students.DataNotFound"));
				saveErrors(request, errors);
				objform.setListStudentDetailsSize(0);

				request.setAttribute("examMarksEntryOperation", "edit");
			} catch (BusinessException ex) {
				if (ex.getLocalizedMessage().equalsIgnoreCase("Inconsistent")) {
					errors
							.add(
									"error",
									new ActionError(
											"knowledgepro.exam.ExamMarksEntry.Students.Inconsistent"));
				}
				if (ex.getLocalizedMessage().equalsIgnoreCase("rollNo")) {
					errors
							.add(
									"error",
									new ActionError(
											"knowledgepro.exam.ExamMarksEntry.Students.rollInvalid",
											objform.getRollNo()));
				}
				if (ex.getLocalizedMessage().equalsIgnoreCase("regNo")) {
					errors
							.add(
									"error",
									new ActionError(
											"knowledgepro.exam.ExamMarksEntry.Students.regInvalid",
											objform.getRegNo()));
				}
				saveErrors(request, errors);
				objform.setListStudentDetailsSize(0);
				request.setAttribute("examMarksEntryOperation", "edit");

			} catch (NullPointerException e) {
				errors
						.add(
								"error",
								new ActionError(
										"knowledgepro.exam.ExamMarksEntry.Students.DataNotFound"));
				saveErrors(request, errors);
				objform.setListStudentDetailsSize(0);
				request.setAttribute("examMarksEntryOperation", "edit");

			} catch (Exception e) {
				e.printStackTrace();
				errors.add("error", new ActionError(
						"knowledgepro.exam.ExamMarksEntry.Students.Fail"));
				saveErrors(request, errors);
				objform.setListStudentDetailsSize(0);
				request.setAttribute("examMarksEntryOperation", "edit");
			}

		} else {
			errors
					.add(
							"error",
							new ActionError(
									"knowledgepro.exam.ExamMarksEntry.Students.regNoOrrollNumber"));
			saveErrors(request, errors);
			objform.setListStudentDetailsSize(0);
			request.setAttribute("examMarksEntryOperation", "edit");
		}
		return mapping
				.findForward(CMSConstants.EXAM_MARKS_ENTRY_SINGLE_STUDENT);
	}

	// All students single subject
	public ActionForward addAllStudentMarks(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamMarksEntryForm objform = (ExamMarksEntryForm) form;
		errors.clear();
		messages.clear();
		if(objform.getExamType().equalsIgnoreCase("Supplementary")){
			errors = subjectRulevalidationForSup(objform.getExamMarksEntryStudentTO(), objform);
		}
		else{
		errors = validate1(objform.getExamMarksEntryStudentTO(), objform);
		}

		saveErrors(request, errors);
		setUserId(request, objform);
		if (errors.isEmpty()) {
			try {
				handler.updateSingleSubj_AllStudents(objform);
				ActionMessage message = new ActionMessage(
						"knowledgepro.exam.ExamMarksEntry.Students.updatesuccess");
				messages.add("messages", message);
				saveMessages(request, messages);
			} catch (NumberFormatException e) {

				errors.add("error", new ActionError(
						"knowledgepro.exam.ExamMarksEntry.Marks.splChar"));
				saveErrors(request, errors);
				return mapping
						.findForward(CMSConstants.EXAM_MARKS_ENTRY_ALL_STUDENT);
			} catch (Exception e) {

				errors.add("error", new ActionError(
						"knowledgepro.exam.ExamMarksEntry.Students.Fail"));
				saveErrors(request, errors);
			} finally {

				objform.clearPage(mapping, request);
			}

		} else {
			objform.setListStudentDetailsSize(1);
			request.setAttribute("examMarksEntryOperation", "edit");
			return mapping
					.findForward(CMSConstants.EXAM_MARKS_ENTRY_ALL_STUDENT);
		}

		return mapping.findForward(CMSConstants.EXAM_MARKS_ENTRY);

	}

	private ActionErrors validate1(
			ArrayList<ExamMarksEntryStudentTO> examMarksEntryStudentTO,
			ExamMarksEntryForm objform) throws Exception {
		boolean firstRun = true;
		boolean practRun = true;
		String absentCode = handler.getCodeForAbsentEntry();

		String notProcessCode = handler.getCodeForNotprocessEntry();
		int courseId = (objform.getCourseId() != null && objform.getCourseId()
				.trim().length() > 0) ? Integer.parseInt(objform.getCourseId())
				: 0;

		int schemeNo = Integer.parseInt(objform.getSchemeId());
		int subjectId = (objform.getSubjectId() != null && objform
				.getSubjectId().trim().length() > 0) ? Integer.parseInt(objform
				.getSubjectId()) : 0;
		Integer subjectTypeId = objform.getSubjectTypeId();
		Integer answerScriptId = (objform.getAnswerScriptType() != null
				&& objform.getAnswerScriptType().trim().length() > 0 ? Integer
				.parseInt(objform.getAnswerScriptType()) : 0);
	Integer mulEvalId = (objform.getEvaluatorType() != null
				&& objform.getEvaluatorType().trim().length() > 0 ? Integer
				.parseInt(objform.getEvaluatorType()) : 0);
		Integer examId = objform.getExamNameId();
		String examType = objform.getExamType();
		BigDecimal tMax = new BigDecimal(0);
		BigDecimal pMax = new BigDecimal(0);
		if (subjectTypeId == 1 || subjectTypeId == 11) {
			tMax = handler.getTheoryMaxMarkForSubject(examType, courseId,
					schemeNo, subjectId, subjectTypeId, examId, answerScriptId,
					mulEvalId, null);
		}
		if (subjectTypeId == 0 || subjectTypeId == 11) {
			pMax = handler.getPracticalMaxMarkForSubject(examType, courseId,
					schemeNo, subjectId, subjectTypeId, examId, answerScriptId,
					mulEvalId, null);
		}
		if(tMax==null || pMax==null)
		{
			errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.noSubjectRule"));
		}
		else
		{
			for (ExamMarksEntryStudentTO list : examMarksEntryStudentTO) {
				String tMarks = list.getTheoryMarks();
	
				if (tMarks != null && tMarks.trim().length() > 0 && !objform.getSubjectType().equalsIgnoreCase("Practical")) 
				{
					tMarks = tMarks.trim();
					if (!(tMarks.equalsIgnoreCase(absentCode) || tMarks.equalsIgnoreCase(notProcessCode))) 
					{
						if (firstRun) 
						{
							if (tMarks.length() < 4) 
							{
								if (!CommonUtil.isValidDecimal(tMarks)) 
								{
									errors.add("error",new ActionError("knowledgepro.exam.validDecimal.Theory"));
									firstRun = false;
								}
							} 
							else
							{
								if (tMarks.length() > 3 && tMarks.indexOf(".") == -1) 
								{
									errors.add("error",	new ActionError("knowledgepro.exam.validDecimal.Theory"));
									firstRun = false;
								}
							}
							if (firstRun && splCharValidation(tMarks)) {
								errors.add("error", new ActionError("knowledgepro.exam.Revaluation.splChar"));
								firstRun = false;
							}
						}
	
						if (tMax != null && firstRun && Integer.parseInt(tMarks) > tMax.intValue()) 
						{
							errors.add("error", new ActionError("knowledgepro.exam.TheoryMaxMarks", tMax));
							break;
						}
					}
				}
				String pMarks = list.getPracticalMarks();
				if (pMarks != null && pMarks.trim().length() > 0 && !(subjectTypeId == 1)) 
				{
					pMarks = pMarks.trim();
					if (!(pMarks.equals(absentCode) || pMarks .equals(notProcessCode))) 
					{
						if (practRun) {
							if (pMarks.length() < 4) 
							{
								if (!CommonUtil.isValidDecimal(pMarks)) {
									errors.add("error",	new ActionError("knowledgepro.exam.validDecimal.Practical"));
									practRun = false;
								}
							}
							else
							{
								if (pMarks.length() > 3	&& pMarks.indexOf(".") == -1) 
								{
									errors.add("error",	new ActionError("knowledgepro.exam.validDecimal.Practical"));
									practRun = false;
								}
							}
							if (firstRun && splCharValidation(pMarks)) {
								errors.clear();
								errors.add("error", new ActionError("knowledgepro.exam.Revaluation.splChar"));
								practRun = false;
							}
						}
						if (pMax != null&& Integer.parseInt(pMarks) > pMax.intValue()&& practRun) {
							errors.add("error", new ActionError("knowledgepro.exam.PracticalMaxMarks", pMax));
							break;
						}
					}
				}
			}
		}	
		return errors;

	}

	// single student all subject
	public ActionForward Submit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamMarksEntryForm objform = (ExamMarksEntryForm) form;
		errors.clear();
		messages.clear();
		errors = objform.validate(mapping, request);
		errors = validate(objform.getExamMarksEntryDetailsTO(), objform);

		saveErrors(request, errors);
		setUserId(request, objform);
		if (errors.isEmpty()) {
			try {
				ArrayList<ExamMarksEntryDetailsTO> listDetailsTO = objform
						.getExamMarksEntryDetailsTO();
				Integer examMarksEntryId = null;
				if (objform.getId() != null && objform.getId().length() > 0) {

					examMarksEntryId = Integer.parseInt(objform.getId());
				}
				int examId = objform.getExamNameId();
				int studentId = objform.getStudentId();
				Integer evaluatorTypeId = null, answerScriptTypeId = null;
				if (objform.getEvaluatorType() != null
						&& objform.getEvaluatorType().trim().length() > 0) {
					evaluatorTypeId = Integer.parseInt(objform
							.getEvaluatorType());
				}
				if (objform.getAnswerScriptType() != null
						&& objform.getAnswerScriptType().trim().length() > 0) {
					answerScriptTypeId = Integer.parseInt(objform
							.getAnswerScriptType());
				}
				String marksCardNo = objform.getMarksCardNo();
				String marksCardDate = objform.getMarksCardDate();
				String userId = objform.getUserId();
				handler.updateAllSubjFor_OneStudent(examMarksEntryId, examId,
						studentId, evaluatorTypeId, marksCardNo, listDetailsTO,
						userId, marksCardDate, answerScriptTypeId);
				ActionMessage message = new ActionMessage(
						"knowledgepro.exam.ExamMarksEntry.Students.updatesuccess");
				messages.add("messages", message);
				saveMessages(request, messages);

			} catch (BusinessException e) {
				errors.add("error", new ActionError(
						"knowledgepro.exam.ExamMarksEntry.Students.Fail"));
				saveErrors(request, errors);
			} catch (NumberFormatException e) {
				errors.add("error", new ActionError(
						"knowledgepro.exam.ExamMarksEntry.Marks.splChar"));
				saveErrors(request, errors);
				objform.setListStudentDetailsSize(1);
				request.setAttribute("examMarksEntryOperation", "edit");
				String RollOrReg = objform.getRollOrReg();
				if (RollOrReg != null && RollOrReg.equalsIgnoreCase("register")) {
					objform.setRegNo(objform.getRegNo());
				} else {
					objform.setRollNo(objform.getRollNo());
				}
				return mapping
						.findForward(CMSConstants.EXAM_MARKS_ENTRY_SINGLE_STUDENT);
			} finally {
				objform.clearPageOnError(mapping, request);
			}
		} else {
			objform.setListStudentDetailsSize(1);
			request.setAttribute("examMarksEntryOperation", "edit");
			return mapping
					.findForward(CMSConstants.EXAM_MARKS_ENTRY_SINGLE_STUDENT);
		}

		return mapping.findForward(CMSConstants.EXAM_MARKS_ENTRY);

	}

	// single student All subjects
	private ActionErrors validate(
			ArrayList<ExamMarksEntryDetailsTO> listDetailsTO,
			ExamMarksEntryForm objform) throws Exception {
		boolean firstRun = true;
		boolean practRun = true;
		int courseId = (objform.getCourseId() != null
				&& objform.getCourseId().trim().length() > 0 ? Integer
				.parseInt(objform.getCourseId()) : 0);

		int schemeNo = (objform.getScheme() != null
				&& objform.getScheme().trim().length() > 0 ? Integer
				.parseInt(objform.getScheme()) : 0);
		Integer answerScriptId = (objform.getAnswerScriptType() != null
				&& objform.getAnswerScriptType().trim().length() > 0 ? Integer
				.parseInt(objform.getAnswerScriptType()) : 0);
			Integer mulEvalId = (objform.getEvaluatorType() != null
				&& objform.getEvaluatorType().trim().length() > 0 ? Integer
				.parseInt(objform.getEvaluatorType()) : 0);
		Integer examId = objform.getExamNameId();
		String examType = objform.getExamType();
		BigDecimal tMax = new BigDecimal(0);
		BigDecimal pMax = new BigDecimal(0);

		for (ExamMarksEntryDetailsTO list : listDetailsTO) {
			String tMarks = list.getTheoryMarks();
			if (tMarks != null && tMarks.trim().length() > 0
					&& !objform.getSubjectType().equalsIgnoreCase("Practical")) {

				tMax = handler.getTheoryMaxMarkForSubject(examType, courseId,
						schemeNo, list.getSubjectId(), 1, examId,
						answerScriptId, mulEvalId, null);
				tMarks = tMarks.trim();
				if (!(tMarks.equals(handler.getCodeForAbsentEntry()) || tMarks
						.equals(handler.getCodeForNotprocessEntry()))) {
					if (firstRun) {
						if (tMarks.trim().length() < 4) {
							if (!CommonUtil.isValidDecimal(tMarks)) {
								errors
										.add(
												"error",
												new ActionError(
														"knowledgepro.exam.Reval.validDecimal"));
								firstRun = false;
							}
						} else {
							if (tMarks.length() > 3
									&& tMarks.indexOf(".") == -1) {
								errors
										.add(
												"error",
												new ActionError(
														"knowledgepro.exam.Reval.validDecimal"));
								firstRun = false;
							}
						}
						if (firstRun && splCharValidation(tMarks)) {
							errors.add("error", new ActionError(
									"knowledgepro.exam.Revaluation.splChar"));
							firstRun = false;

						}
					}
					if (tMax != null && firstRun
							&& Integer.parseInt(tMarks) > tMax.intValue()) {
						errors.add("error", new ActionError(
								"knowledgepro.exam.TheoryMaxMarks", tMax, list
										.getSubjectName()));
						break;
					}
				}

			}
			String pMarks = list.getPracticalMarks();

			if (pMarks != null && pMarks.trim().length() > 0
					&& !objform.getSubjectType().equalsIgnoreCase("Theory")) {
				pMax = handler.getPracticalMaxMarkForSubject(examType,
						courseId, schemeNo, list.getSubjectId(), 0, examId,
						answerScriptId, mulEvalId, null);
				pMarks = pMarks.trim();
				if (!(pMarks.equals(handler.getCodeForAbsentEntry()) || pMarks
						.equals(handler.getCodeForNotprocessEntry()))) {
					if (practRun) {
						if (pMarks.trim().length() < 4) {
							if (!CommonUtil.isValidDecimal(pMarks)) {
								errors
										.add(
												"error",
												new ActionError(
														"knowledgepro.exam.Reval.validDecimal"));
								practRun = false;
							}
						} else {
							if (pMarks.length() > 3
									&& pMarks.indexOf(".") == -1) {
								errors
										.add(
												"error",
												new ActionError(
														"knowledgepro.exam.Reval.validDecimal"));

								practRun = false;
							}

						}
						if (practRun && splCharValidation(pMarks)) {
							errors.clear();
							errors.add("error", new ActionError(
									"knowledgepro.exam.Revaluation.splChar"));
							practRun = false;
						}
					}
					if (pMax != null && practRun
							&& Integer.parseInt(pMarks) > pMax.intValue()) {
						errors.add("error", new ActionError(
								"knowledgepro.exam.PracticalMaxMarks", pMax,
								list.getSubjectName()));
						break;
					}
				}

			}
		}
		return errors;
	}

	private boolean splCharValidation(String name) {
		boolean haveSplChar = false;
		Pattern pattern = Pattern.compile("[^0-9]\\.+");
		Matcher matcher = pattern.matcher(name);
		haveSplChar = matcher.find();
		return haveSplChar;

	}

	private ExamMarksEntryForm retainVals(ExamMarksEntryForm objform)
			throws Exception {
		objform = handler.retainAllValues(objform);

		return objform;

	}

	private ActionErrors validateEvaluatorAndAnswerScript(
			ExamMarksEntryForm objform) {
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
	
	@SuppressWarnings("unchecked")
	public void getExamNameListByExamType(ExamMarksEntryForm objform) throws Exception{
		CommonAjaxExamHandler handler = new CommonAjaxExamHandler();
		String examType = objform.getExamType();
		Map<Integer, String> examNameMap = handler.getExamNameByExamType(examType);
		examNameMap = (Map<Integer, String>) CommonUtil.sortMapByValue(examNameMap);
		objform.setExamNameList(examNameMap);
		
	}
	
	public ActionForward initExamMarksEntryForRegular(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception 
	{
		ExamMarksEntryForm objform = (ExamMarksEntryForm) form;
		errors = objform.validate(mapping, request);
		setUserId(request, objform);
		objform.setExamType("Regular");
		objform.setShowExamType("ShowRegularAndSupply");
		getExamNameListByExamType(objform);
		objform.clearPage(mapping, request);
		return mapping.findForward(CMSConstants.EXAM_MARKS_ENTRY);
	}
	
	public ActionForward initExamMarksEntryForInternal(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception 
	{
		ExamMarksEntryForm objform = (ExamMarksEntryForm) form;
		errors = objform.validate(mapping, request);
		setUserId(request, objform);
		objform.setExamType("Internal");
		objform.setShowExamType("ShowInternal");
		getExamNameListByExamType(objform);
		objform.clearPage(mapping, request);
		return mapping.findForward(CMSConstants.EXAM_MARKS_ENTRY);
	}

	private ActionErrors subjectRulevalidationForSup(
			ArrayList<ExamMarksEntryStudentTO> examMarksEntryStudentTO,
			ExamMarksEntryForm objform) throws Exception {
		boolean firstRun = true;
		boolean practRun = true;
		String absentCode = handler.getCodeForAbsentEntry();

		String notProcessCode = handler.getCodeForNotprocessEntry();
		int courseId = (objform.getCourseId() != null && objform.getCourseId()
				.trim().length() > 0) ? Integer.parseInt(objform.getCourseId())
				: 0;

		int schemeNo = Integer.parseInt(objform.getSchemeId());
		int subjectId = (objform.getSubjectId() != null && objform
				.getSubjectId().trim().length() > 0) ? Integer.parseInt(objform
				.getSubjectId()) : 0;
		Integer subjectTypeId = objform.getSubjectTypeId();
		Integer answerScriptId = (objform.getAnswerScriptType() != null
				&& objform.getAnswerScriptType().trim().length() > 0 ? Integer
				.parseInt(objform.getAnswerScriptType()) : 0);
	Integer mulEvalId = (objform.getEvaluatorType() != null
				&& objform.getEvaluatorType().trim().length() > 0 ? Integer
				.parseInt(objform.getEvaluatorType()) : 0);
		Integer examId = objform.getExamNameId();
		String examType = objform.getExamType();
		BigDecimal tMax = new BigDecimal(0);
		BigDecimal pMax = new BigDecimal(0);
		/*if (subjectTypeId == 1 || subjectTypeId == 11) {
			tMax = handler.getTheoryMaxMarkForSubject(examType, courseId,
					schemeNo, subjectId, subjectTypeId, examId, answerScriptId,
					mulEvalId);
		}
		if (subjectTypeId == 0 || subjectTypeId == 11) {
			pMax = handler.getPracticalMaxMarkForSubject(examType, courseId,
					schemeNo, subjectId, subjectTypeId, examId, answerScriptId,
					mulEvalId);
		}
		/*if(tMax==null || pMax==null)
		{
			errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.noSubjectRule"));
		}
		else
		{*/
			for (ExamMarksEntryStudentTO list : examMarksEntryStudentTO) {
				String tMarks = list.getTheoryMarks();
				Integer academicYear = handler.getAcademicYearFromPreviousClassDetails(list.getStudentId(), objform.getSchemeId());
				if (subjectTypeId == 1 || subjectTypeId == 11) {
					tMax = handler.getTheoryMaxMarkForSubject(examType, courseId,
							schemeNo, subjectId, subjectTypeId, examId, answerScriptId,
							mulEvalId, academicYear);
					if(tMax==null)
					{
						errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.noSubjectRule.sup",list.getRegNo()));
						continue;
					}
				}
				if (subjectTypeId == 0 || subjectTypeId == 11) {
					pMax = handler.getPracticalMaxMarkForSubject(examType, courseId,
							schemeNo, subjectId, subjectTypeId, examId, answerScriptId,
							mulEvalId, academicYear);
					if(pMax==null)
					{
						errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.noSubjectRule.sup",list.getRegNo()));
						continue;
					}
				}
				if (tMarks != null && tMarks.trim().length() > 0 && !objform.getSubjectType().equalsIgnoreCase("Practical")) 
				{
					tMarks = tMarks.trim();
					if (!(tMarks.equalsIgnoreCase(absentCode) || tMarks.equalsIgnoreCase(notProcessCode))) 
					{
						if (firstRun) 
						{
							if (tMarks.length() < 4) 
							{
								if (!CommonUtil.isValidDecimal(tMarks)) 
								{
									errors.add("error",new ActionError("knowledgepro.exam.validDecimal.Theory"));
									firstRun = false;
								}
							} 
							else
							{
								if (tMarks.length() > 3 && tMarks.indexOf(".") == -1) 
								{
									errors.add("error",	new ActionError("knowledgepro.exam.validDecimal.Theory"));
									firstRun = false;
								}
							}
							if (firstRun && splCharValidation(tMarks)) {
								errors.add("error", new ActionError("knowledgepro.exam.Revaluation.splChar"));
								firstRun = false;
							}
						}
	
						if (tMax != null && firstRun && Integer.parseInt(tMarks) > tMax.intValue()) 
						{
							errors.add("error", new ActionError("knowledgepro.exam.TheoryMaxMarks", tMax));
							break;
						}
					}
				}
				String pMarks = list.getPracticalMarks();
				if (pMarks != null && pMarks.trim().length() > 0 && !(subjectTypeId == 1)) 
				{
					pMarks = pMarks.trim();
					if (!(pMarks.equals(absentCode) || pMarks .equals(notProcessCode))) 
					{
						if (practRun) {
							if (pMarks.length() < 4) 
							{
								if (!CommonUtil.isValidDecimal(pMarks)) {
									errors.add("error",	new ActionError("knowledgepro.exam.validDecimal.Practical"));
									practRun = false;
								}
							}
							else
							{
								if (pMarks.length() > 3	&& pMarks.indexOf(".") == -1) 
								{
									errors.add("error",	new ActionError("knowledgepro.exam.validDecimal.Practical"));
									practRun = false;
								}
							}
							if (firstRun && splCharValidation(pMarks)) {
								errors.clear();
								errors.add("error", new ActionError("knowledgepro.exam.Revaluation.splChar"));
								practRun = false;
							}
						}
						if (pMax != null&& Integer.parseInt(pMarks) > pMax.intValue()&& practRun) {
							errors.add("error", new ActionError("knowledgepro.exam.PracticalMaxMarks", pMax));
							break;
						}
					}
				}
			}
		//}	
		return errors;

	}
}