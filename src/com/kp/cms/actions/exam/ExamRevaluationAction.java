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
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.exceptions.DataNotFoundException;
import com.kp.cms.forms.exam.ExamRevaluationForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.exam.ExamAssignStudentsToRoomHandler;
import com.kp.cms.handlers.exam.ExamMarksEntryHandler;
import com.kp.cms.handlers.exam.ExamRevaluationHandler;
import com.kp.cms.to.exam.ExamRevaluationDetailsTO;
import com.kp.cms.to.exam.ExamRevaluationEntryTO;
import com.kp.cms.to.exam.ExamRevaluationStudentTO;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.CurrentAcademicYear;

@SuppressWarnings("deprecation")
public class ExamRevaluationAction extends BaseDispatchAction {
	ExamRevaluationHandler handler = new ExamRevaluationHandler();
	ActionErrors errors = new ActionErrors();
	ActionMessages messages = new ActionMessages();

	// On - LOAD
	public ActionForward initExamRevaluation(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamRevaluationForm objform = (ExamRevaluationForm) form;
		errors = objform.validate(mapping, request);
		setUserId(request, objform);
		objform.clearPage(mapping, request);
		setRequestToList(objform, request);

		return mapping.findForward(CMSConstants.EXAM_REVALUATION_ENTRY);
	}

	// To get Exam Name list and Re-valuation Type List
	/**
	 * @param objform
	 * @param request
	 * @throws Exception
	 */
	private void setRequestToList(ExamRevaluationForm objform,
			HttpServletRequest request) throws Exception {
	//	objform.setListExamName(handler.getExamNameList());
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
		if((objform.getExamName()==null || objform.getExamName().trim().isEmpty()) && currentExam!=null){
			objform.setExamName(currentExam);
		}
		objform.setListRevaluationType(handler.getListRevaluationType());
	}

	// To FETCH AS - SS based on the rules
	// specified in SUBJECT - RULE - SETTINGS
	public ActionForward getStudentMarksEntry(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamRevaluationForm objform = (ExamRevaluationForm) form;
		errors.clear();
		messages.clear();
		errors = objform.validate(mapping, request);
		errors = validateSubjectType(objform);
		saveErrors(request, errors);
		setUserId(request, objform);
		if (errors.isEmpty()) {
			request.setAttribute("examMarksEntryOperation", "edit");
			if (objform.getMarksEntryFor().contains("Single Student")) {

				objform = handler.getStudentDetails(objform);
				return mapping
						.findForward(CMSConstants.EXAM_REVALUATION_SINGLE_STUDENT);
			} else {
				objform = handler.getUpdatableForm(objform);
				int sub = Integer.parseInt(objform.getSubjectId());
				int subjectType = objform.getSubjectTypeId();
				int courseId = Integer.parseInt(objform.getCourseId());

				ArrayList<ExamRevaluationStudentTO> marksDetails = handler
						.getSingleSubjFor_AllStudents(courseId, sub,
								subjectType, true, objform.getExamNameId(),
								Integer.parseInt(objform.getRevaluationType()),
								Integer.parseInt(objform.getScheme()));
				if (marksDetails.size() > 0) {
					objform.setExamRevaluationStudentTO(marksDetails);
					return mapping
							.findForward(CMSConstants.EXAM_REVALUATION_ALL_STUDENT);

				} else {
					errors.add("error", new ActionError(
							"knowledgepro.exam.MarksCorrection.DataNotFound"));
					saveErrors(request, errors);
					return mapping
							.findForward(CMSConstants.EXAM_REVALUATION_ENTRY);
				}

			}

		} else {
			objform = retainValues(objform);
			request.setAttribute("retainValues", "yes");
			objform.setListExamName(handler.getExamNameList());
			objform.setListRevaluationType(handler.getListRevaluationType());
			return mapping.findForward(CMSConstants.EXAM_REVALUATION_ENTRY);
		}

	}

	// Retain values on error
	private ExamRevaluationForm retainValues(ExamRevaluationForm objform) {

		objform = handler.retainAllValues(objform);
		return objform;
	}

	private ActionErrors validateSubjectType(ExamRevaluationForm objform) {
		if (objform.getMarksEntryFor().contains("All Students")) {
			if (!(objform.getSubject() != null && objform.getSubject().trim()
					.length() > 0)) {
				errors.add("error", new ActionError(
						"knowledgepro.exam.MarksCorrection.Subject"));
			}

		}
		return errors;
	}

	// To FETCH SS - AS based on the rules
	// specified in SUBJECT - RULE - SETTINGS
	public ActionForward getStudentDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamRevaluationForm objform = (ExamRevaluationForm) form;
		errors.clear();
		messages.clear();
		errors = objform.validate(mapping, request);
		saveErrors(request, errors);
		setUserId(request, objform);

		if ((objform.getRegNo() != null && objform.getRegNo().trim().length() > 0)
				|| (objform.getRollNo() != null && objform.getRollNo().trim()
						.length() > 0)) {
			try {

				ExamRevaluationEntryTO to = handler.getAllSubjFor_OneStudent(
						Integer.parseInt(objform.getCourseId()), objform
								.getRollNo().trim(), objform.getRegNo().trim(),
						objform.getExamNameId(), Integer.parseInt(objform
								.getScheme()), Integer.parseInt(objform
								.getRevaluationType()));
				objform.setExamRevaluationDetailsTO(to.getListMarksDetails());
				objform.setObjExamRevaluationEntryTO(to);
				if (to.getListMarksDetails().size() < 0) {
					errors.add("error", new ActionError(
							"knowledgepro.exam.MarksCorrection.DataNotFound"));
					saveErrors(request, errors);
					return mapping
							.findForward(CMSConstants.EXAM_REVALUATION_SINGLE_STUDENT);
				}
				objform.setStudentName(to.getStudentName());
				objform.setMarksCardNo(to.getMarksCardNo());
				objform.setStudentId(to.getStudentId());
				objform.setClasscode(to.getClassCode());
				objform.setMarksCardDate(to.getMarksCardDate());
				if ((to.getId() != null)) {
					objform.setId(to.getId().toString());
				}
				objform.setListStudentDetailsSize(1);
				request.setAttribute("examMarksEntryOperation", "edit");
			} catch (DataNotFoundException e) {
				errors.add("error", new ActionError(
						"knowledgepro.exam.MarksCorrection.DataNotFound"));
				saveErrors(request, errors);
				objform.setListStudentDetailsSize(0);
				request.setAttribute("examMarksEntryOperation", "edit");
			} catch (BusinessException ex) {

				errors.add("error", new ActionError(
						"knowledgepro.exam.MarksCorrection.Inconsistent"));
				saveErrors(request, errors);
				objform.setListStudentDetailsSize(0);
				request.setAttribute("examMarksEntryOperation", "edit");

			} catch (NullPointerException e) {
				errors.add("error", new ActionError(
						"knowledgepro.exam.MarksCorrection.DataNotFound"));
				saveErrors(request, errors);
				objform.setListStudentDetailsSize(0);
				request.setAttribute("examMarksEntryOperation", "edit");
			} catch (Exception e) {
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
		request.setAttribute("examMarksEntryOperation", "edit");
		return mapping
				.findForward(CMSConstants.EXAM_REVALUATION_SINGLE_STUDENT);

	}

	// To INSERT & UPDATE single subject marks for all students
	public ActionForward addAllStudentMarks(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamRevaluationForm objform = (ExamRevaluationForm) form;
		errors.clear();
		messages.clear();
		errors = validate1(objform.getExamRevaluationStudentTO());
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
						"knowledgepro.exam.Reval.validDecimal"));
				saveErrors(request, errors);
				return mapping
						.findForward(CMSConstants.EXAM_REVALUATION_ALL_STUDENT);
			} catch (Exception e) {
				errors.add("error", new ActionError(
						"knowledgepro.exam.ExamMarksEntry.Students.Fail"));
				saveErrors(request, errors);
			} finally {
				setRequestToList(objform, request);
				objform.clearPage(mapping, request);
			}

		} else {
			return mapping
					.findForward(CMSConstants.EXAM_REVALUATION_ALL_STUDENT);
		}

		return mapping.findForward(CMSConstants.EXAM_REVALUATION_ENTRY);

	}

	private ActionErrors validate1(
			ArrayList<ExamRevaluationStudentTO> ExamRevaluationStudentTO) {

		boolean firstRun = true;
		for (ExamRevaluationStudentTO list : ExamRevaluationStudentTO) {
			String tMarks = list.getCurrentTheoryMarks();
			String pMarks = list.getCurrentPracticalMarks();

			if (tMarks != null) {
				if (firstRun) {
					if (tMarks.trim().length() < 4) {
						if (!CommonUtil.isValidDecimal(tMarks)) {
							errors.add("error", new ActionError(
									"knowledgepro.exam.Reval.validDecimal"));
							firstRun = false;
						}
					} else {
						if (tMarks.length() > 3 && tMarks.indexOf(".") == -1) {
							errors.add("error", new ActionError(
									"knowledgepro.exam.Reval.validDecimal"));
							firstRun = false;
						}

					}
					if (firstRun && splCharValidation(tMarks)) {
						errors.add("error", new ActionError(
								"knowledgepro.exam.Reval.validDecimal"));
						firstRun = false;
					}

				}

			}
			if (pMarks != null) {
				if (firstRun) {
					if (pMarks.trim().length() < 4) {
						if (!CommonUtil.isValidDecimal(pMarks)) {
							errors.add("error", new ActionError(
									"knowledgepro.exam.Reval.validDecimal"));
							firstRun = false;
						}
					} else {
						if (pMarks.length() > 3 && pMarks.indexOf(".") == -1) {
							errors.add("error", new ActionError(
									"knowledgepro.exam.Reval.validDecimal"));
							firstRun = false;
						}

					}
					if (firstRun && splCharValidation(pMarks)) {
						errors.add("error", new ActionError(
								"knowledgepro.exam.Reval.validDecimal"));
						firstRun = false;
					}

				}

			}
		}
		return errors;
	}

	// To INSERT & UPDATE SS - AS
	public ActionForward addSingleStudent(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamRevaluationForm objform = (ExamRevaluationForm) form;
		errors.clear();
		messages.clear();
		errors = objform.validate(mapping, request);
		errors = validate(objform);
		saveErrors(request, errors);
		setUserId(request, objform);
		if (errors.isEmpty()) {
			try {
				ArrayList<ExamRevaluationDetailsTO> listDetailsTO = objform
						.getExamRevaluationDetailsTO();
				Integer examMarksEntryId = null;
				if (objform.getId() != null && objform.getId().length() > 0) {

					examMarksEntryId = Integer.parseInt(objform.getId());
				}
				int examId = objform.getExamNameId();
				int studentId = objform.getStudentId();
				Integer revaluationTypeId = null;
				if (objform.getRevaluationType() != null
						&& objform.getRevaluationType().trim().length() > 0)
					revaluationTypeId = Integer.parseInt(objform
							.getRevaluationType());
				String marksCardNo = objform.getMarksCardNo();
				String marksCardDate = objform.getMarksCardDate();
				String userId = objform.getUserId();
				handler.updateAllSubjFor_OneStudent(examMarksEntryId, examId,
						studentId, revaluationTypeId, marksCardNo,
						listDetailsTO, userId, marksCardDate);

				ActionMessage message = new ActionMessage(
						"knowledgepro.exam.ExamMarksEntry.Students.updatesuccess");
				messages.add("messages", message);

				saveMessages(request, messages);
				objform.clearPage(mapping, request);
				return mapping.findForward(CMSConstants.EXAM_REVALUATION_ENTRY);

			} catch (BusinessException e) {

				errors.add("error", new ActionError(
						"knowledgepro.exam.ExamMarksEntry.Students.Fail"));
				saveErrors(request, errors);
			} catch (NumberFormatException e) {
				errors.add("error", new ActionError(
						"knowledgepro.exam.Reval.validDecimal"));
				saveErrors(request, errors);
				objform.setListStudentDetailsSize(1);
				request.setAttribute("examMarksEntryOperation", "edit");

			}

			finally {
				setRequestToList(objform, request);

			}

		}
		request.setAttribute("examMarksEntryOperation", "edit");
		return mapping
				.findForward(CMSConstants.EXAM_REVALUATION_SINGLE_STUDENT);

	}

	private ActionErrors validate(ExamRevaluationForm objform) {

		boolean firstRun = true;
		if ((objform.getMarksCardDate() != null && objform.getMarksCardDate()
				.trim().length() > 0)
				&& !CommonUtil.isValidDate(objform.getMarksCardDate())) {
			errors.add("error", new ActionError(
					"knowledgepro.exam.ExamTimeTable.InvalidDate"));
		}

		ArrayList<ExamRevaluationDetailsTO> listDetailsTO = objform
				.getExamRevaluationDetailsTO();
		for (ExamRevaluationDetailsTO list : listDetailsTO) {
			String pMarks = list.getCurrentPracticalMarks();
			String tMarks = list.getCurrentTheoryMarks();
			if (tMarks != null && tMarks.trim().length() > 0) {
				if (firstRun) {
					if (tMarks.trim().length() < 4) {
						if (!CommonUtil.isValidDecimal(tMarks)) {
							errors.add("error", new ActionError(
									"knowledgepro.exam.Reval.validDecimal"));
							firstRun = false;
						}
					} else {
						if (tMarks.length() > 3 && tMarks.indexOf(".") == -1) {
							errors.add("error", new ActionError(
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

				if (pMarks != null && pMarks.trim().length() > 0) {
					if (pMarks.trim().length() < 4) {
						if (!CommonUtil.isValidDecimal(pMarks)) {
							errors.add("error", new ActionError(
									"knowledgepro.exam.Reval.validDecimal"));
							firstRun = false;
						}
					} else {
						if (pMarks.length() > 3 && pMarks.indexOf(".") == -1) {
							errors.add("error", new ActionError(
									"knowledgepro.exam.Reval.validDecimal"));
							firstRun = false;
						}

					}
					if (firstRun && splCharValidation(pMarks)) {
						errors.clear();
						errors.add("error", new ActionError(
								"knowledgepro.exam.Revaluation.splChar"));
						firstRun = false;
					}

				}

			}
		}
		return errors;
	}

	// Special Character Validation
	private boolean splCharValidation(String name) {
		boolean haveSplChar = false;
		Pattern pattern = Pattern.compile("[^0-9]\\.+");
		Matcher matcher = pattern.matcher(name);
		haveSplChar = matcher.find();
		return haveSplChar;

	}
}