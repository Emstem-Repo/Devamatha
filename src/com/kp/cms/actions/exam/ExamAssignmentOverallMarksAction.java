package com.kp.cms.actions.exam;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.exam.ExamAssignmentOverallMarksForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.exam.ExamAssignOverallMarksHandler;
import com.kp.cms.handlers.exam.ExamAssignStudentsToRoomHandler;
import com.kp.cms.handlers.exam.ExamMarksEntryHandler;
import com.kp.cms.handlers.exam.ExamUpdateExcludeWithheldHandler;
import com.kp.cms.to.exam.ExamAssignOverallMarksTO;
import com.kp.cms.utilities.CurrentAcademicYear;

@SuppressWarnings("deprecation")
public class ExamAssignmentOverallMarksAction extends BaseDispatchAction {
	private static final Log log = LogFactory
			.getLog(ExamAssignmentOverallMarksAction.class);
	ActionErrors errors = new ActionErrors();
	ActionMessages messages = new ActionMessages();
	ExamAssignOverallMarksHandler handler = new ExamAssignOverallMarksHandler();

	// Initialization of action
	public ActionForward initAssignmentOverallMarks(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamAssignmentOverallMarksForm objform = (ExamAssignmentOverallMarksForm) form;
		errors.clear();
		messages.clear();
		// map.clear();
		setRequestToList(objform, request);

		return mapping.findForward(CMSConstants.EXAM_ASSIGNMENT_OVERALL_MARKS);
	}

	private void setRequestToList(ExamAssignmentOverallMarksForm objform,
			HttpServletRequest request) throws Exception {

		objform.setCourseId(null);
		objform.setSubjectType(null);
		objform.setMapScheme(new HashMap<String, String>());
		objform.setMapSubject(new HashMap<Integer, String>());
	//	objform.setListExamName(handler.getExamNameListForRRS());
		setExamNameMapToForm(objform);

/*		if (objform.getListExamName() != null
				&& objform.getListExamName().size() != 0) {
			KeyValueTO to = objform.getListExamName().get(0);
*/
			ExamUpdateExcludeWithheldHandler h2 = new ExamUpdateExcludeWithheldHandler();
			if(objform.getExamName()!= null && !objform.getExamName().trim().isEmpty()){
				objform.setListCourseMap(h2.getCourse(objform.getExamName()));
			}
			else{
				objform.setListCourseMap(null);
			}
			/*else{
				objform.setListCourseMap(h2.getCourse(Integer.toString(to.getId())));
			}
			}*/
		objform.setTypeMap(handler.getTypeByAssignmentOverall("overall"));
		objform.setAssignmentOverall("overall");

	}

	private void setExamNameMapToForm(ExamAssignmentOverallMarksForm objform) throws Exception{
		// new input parameters-Academic Year,Exam Type, Exam Name List dynamic loading added by Smitha
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
		objform.setExamType("1");
		
		Map<Integer,String> examMap=CommonAjaxHandler.getInstance().getExamNameByExamTypeAndYear(objform.getExamType(),year); 
		objform.setExamNameMap(examMap);
		
		ExamMarksEntryHandler examhandler = new ExamMarksEntryHandler();
		String currentExam=examhandler.getCurrentExamName(objform.getExamType());
		if(currentExam!=null){
			objform.setExamName(currentExam);
		}
//ends		
	}

	// To get students for course, scheme & subject
	public ActionForward getStudentAssignmentOverallMarks(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamAssignmentOverallMarksForm objform = (ExamAssignmentOverallMarksForm) form;
		errors.clear();
		messages.clear();
		String assignmentOverall = objform.getAssignmentOverall();
		errors = objform.validate(mapping, request);
		saveErrors(request, errors);
		Integer examId = null;
		Integer accYear = null;
		setUserId(request, objform);
		if (errors.isEmpty()) {

			if (assignmentOverall.equalsIgnoreCase("overall")) {
				examId = Integer.parseInt(objform.getExamName());
			} else {
				accYear = Integer.parseInt(objform.getJoiningBatch());
			}

			int courseId = Integer.parseInt(objform.getCourseId());
			String[] scheme = objform.getSchemeNo().split("_");
			int schemeId = 0;
			int schemeNo = 0;
			for (int i = 0; i < scheme.length; i = +2) {
				schemeId = Integer.parseInt(scheme[0]);
				schemeNo = Integer.parseInt(scheme[1]);
			}

			int subjectId = Integer.parseInt(objform.getSubject());
			String subjectType = objform.getSubjectType();
			String sType = "";
			int type = 0;
			if (subjectType.equalsIgnoreCase("T")) {
				sType = "T";
				subjectType = "Theory";
				type = 1;
			} else if (subjectType.equalsIgnoreCase("P")) {
				sType = "P";
				subjectType = "Practical";
				type = 0;
			} else {
				sType = "B";
				subjectType = "Theory and Practical";
				type = 11;
			}
			// String assignMentOverall = objform.getAssignmentOverall();
			Integer typeVa = null;
			if (objform.getType() != null && objform.getType().length() > 0) {
				typeVa = Integer.parseInt(objform.getType());
			}
			List<ExamAssignOverallMarksTO> listStudents = handler
					.getStudents_List(courseId, schemeId, subjectId, type,
							examId, assignmentOverall, typeVa, schemeNo,
							accYear, objform.getIsPreviousExam(),objform.getExamType() );

			if (listStudents != null && listStudents.size() > 0) {
				if (examId != null) {
					if (handler.getInternalOverallLink(courseId, subjectId,
							subjectType, schemeNo, examId)) {
						objform.setDisplayAssignOverall("Display");
					}
				}

				objform.setListOfStudent(listStudents);
				objform.setCourseId(objform.getCourseId());
				objform.setSchemeNo(Integer.toString(schemeNo));
				objform.setSubjectId(Integer.toString(subjectId));
				objform.setSubjectType(sType);

			} else {
				messages
						.add(
								"messages",
								new ActionMessage(
										"knowledgepro.exam.ExamMarksEntry.Students.DataNotFound"));
				saveMessages(request, messages);
				objform.setCourseId("");
				objform.setSubjectType("");
				objform.setMapScheme(new HashMap<String, String>());
				objform.setMapSubject(new HashMap<Integer, String>());
				setRequestToList(objform, request);
				return mapping
						.findForward(CMSConstants.EXAM_ASSIGNMENT_OVERALL_MARKS);
			}
			if (assignmentOverall.equalsIgnoreCase("overall")) {
			//	objform.setListExamName(handler.getExamNameListForRRS());
				setExamNameMapToForm(objform);
/*				if (objform.getListExamName() != null
						&& objform.getListExamName().size() != 0) {
					KeyValueTO to = objform.getListExamName().get(0);

					ExamUpdateExcludeWithheldHandler h2 = new ExamUpdateExcludeWithheldHandler();
					objform.setListCourseMap(h2.getCourse(Integer.toString(to
							.getId())));
				}*/
				ExamUpdateExcludeWithheldHandler h2 = new ExamUpdateExcludeWithheldHandler();
				if(objform.getExamName()!= null && !objform.getExamName().trim().isEmpty()){
					objform.setListCourseMap(h2	.getCourse(objform.getExamName()));
				}				
				else{
					objform.setListCourseMap(null);
				}
			}

			objform.setCourseName(handler.getCourseName(courseId));
			objform.setSchemeId(Integer.toString(schemeNo));

			objform.setSubject(handler.getSubjectName(subjectId));
			objform.setSubjectType(subjectType);
			objform.setSubjectTypeId(type);
			String typeValue = "";
			// get the internal components(Assignment/SubInternal/Attendance)
			// for the selected subject

			// boolean
			// isPresent=handler.getInternalDetails(courseId,subjectId,subjectType,examId);
			if (assignmentOverall.equalsIgnoreCase("overall")) {
				if (objform.getType() != null && objform.getType().length() > 0) {
					if (objform.getType().contains("1")) {
						typeValue = "Internal Overall";
						objform.setType("1");
					} else {

						typeValue = "Regular Overall";
						objform.setType(objform.getType());
					}
				}

			}
			String strAccYear = "";
			if (assignmentOverall.equalsIgnoreCase("Assignment")) {
				if (objform.getType() != null && objform.getType().length() > 0) {
					typeValue = handler.getAssignmentTypeById(Integer
							.parseInt(objform.getType()));
				}

				strAccYear = objform.getJoiningBatch().concat("-").concat(
						Integer.toString(accYear + 1));
			}
			objform.setOptionNo(objform.getType());
			objform.setType(typeValue);

			objform.setJoiningBatch(strAccYear);
			objform.setAssignmentOverall("Assignment");
			return mapping
					.findForward(CMSConstants.EXAM_ASSIGNMENT_OVERALL_MARKS_ADD);
		} else {
			ExamAssignOverallMarksHandler h1 = new ExamAssignOverallMarksHandler();
			if (assignmentOverall.equalsIgnoreCase("overall")) {
				if (objform.getExamName() != null
						&& objform.getExamName().length() > 0) {
					examId = Integer.parseInt(objform.getExamName());
				}
			} else {
				accYear = Integer.parseInt(objform.getJoiningBatch());
			}

			if (assignmentOverall.equalsIgnoreCase("overall")) {
				ExamUpdateExcludeWithheldHandler updateExtWithHandler = new ExamUpdateExcludeWithheldHandler();
				if (examId != null) {
					objform.setListCourseMap(updateExtWithHandler
							.getCourse(Integer.toString(examId)));
				}
			} else {
				objform.setListCourseMap(handler
						.getCourseByAcademicYear(accYear));
			}

			if (objform.getCourseId() != null
					&& objform.getCourseId().trim().length() > 0) {
				int courseId = Integer.parseInt(objform.getCourseId());
				if (accYear != null && accYear != 0) {
					objform.setMapScheme(handler
							.getSchemeNo_SchemeIDByCourseIdAcademicId(Integer
									.toString(courseId), Integer
									.toString(accYear)));
				} else {
					Integer academicYear = handler.getExamAcademicYear(examId);
					objform.setMapScheme(handler
							.getSchemeNo_SchemeIDByCourseIdAcademicId(Integer
									.toString(courseId), Integer
									.toString(academicYear)));
				}

			}
			if (objform.getSchemeNo() != null
					&& objform.getSchemeNo().trim().length() > 0) {
				int courseId = Integer.parseInt(objform.getCourseId());
				String[] a = objform.getSchemeNo().split("_");
				int schemeId = 0;
				int schemeNo = 0;
				for (int i = 0; i < a.length; i = +2) {
					schemeId = Integer.parseInt(a[0]);
					schemeNo = Integer.parseInt(a[1]);
				}
				objform.setMapSubject(h1.getSubjectsByCourseSchemeExamId(
						courseId, schemeId, schemeNo, Integer.parseInt(objform
								.getExamName())));

			}

			return mapping
					.findForward(CMSConstants.EXAM_ASSIGNMENT_OVERALL_MARKS);
		}

	}

	// On - SUBMIT(Update)
	public ActionForward addStudentAssignmentOverallMarks(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamAssignmentOverallMarksForm objform = (ExamAssignmentOverallMarksForm) form;
		errors.clear();
		messages.clear();
		errors = validateErrors(objform.getListOfStudent(), objform
				.getSubjectType(), objform.getCourseId(),
				objform.getSchemeNo(), objform.getSubjectId(), objform
						.getAssignmentOverall(), objform.getOptionNo());
		saveErrors(request, errors);
		setUserId(request, objform);
		if (errors.isEmpty()) {
			handler.add(objform.getListOfStudent(), objform.getUserId(),
					objform.getSubjectTypeId());

			objform.setCourseId(null);
			objform.setSubjectType(null);
			objform.setMapScheme(new HashMap<String, String>());
			objform.setMapSubject(new HashMap<Integer, String>());
		//	objform.setListExamName(handler.getExamNameListForRRS());
			setExamNameMapToForm(objform);
/*			if (objform.getListExamName() != null
					&& objform.getListExamName().size() != 0) {
				KeyValueTO to = objform.getListExamName().get(0);

				ExamUpdateExcludeWithheldHandler h2 = new ExamUpdateExcludeWithheldHandler();
				objform.setListCourseMap(h2.getCourse(Integer.toString(to
						.getId())));
			}*/
			ExamUpdateExcludeWithheldHandler h2 = new ExamUpdateExcludeWithheldHandler();
			if(objform.getExamName()!= null && !objform.getExamName().trim().isEmpty()){
				objform.setListCourseMap(h2	.getCourse(objform.getExamName()));
			}				
			else{
				objform.setListCourseMap(null);
			}
			
			objform.setTypeMap(handler.getTypeByAssignmentOverall("overall"));
			objform.setAssignmentOverall("overall");
			messages.add("messages", new ActionMessage(
					"knowledgepro.exam.ExamPromotionCriteria.addsuccess"));
			saveMessages(request, messages);

			return mapping
					.findForward(CMSConstants.EXAM_ASSIGNMENT_OVERALL_MARKS);
		} else {
			return mapping
					.findForward(CMSConstants.EXAM_ASSIGNMENT_OVERALL_MARKS_ADD);

		}

	}

	public ActionErrors validateErrors(
			List<ExamAssignOverallMarksTO> listOfStudent, String subjectType,
			String courseId, String schemeNo, String subjectId,
			String assignmentOverall, String type) throws Exception {
		int value = 0;
		for (ExamAssignOverallMarksTO objTO : listOfStudent) {

			value = handler.getMaxTheoryMarks((objTO.getTheoryMarks() != null
					&& objTO.getTheoryMarks().trim().length() > 0 ? handler
					.getBDValue(objTO.getTheoryMarks()) : new BigDecimal("0")),
					Integer.parseInt(courseId), Integer.parseInt(schemeNo),
					Integer.parseInt(subjectId), subjectType, objTO
							.getDummyStudentId(), assignmentOverall, type);
			if (errors.isEmpty()) {
				if (value != 0) {
					errors
							.add(
									"error",
									new ActionError(
											"knowledgepro.exam.AssignmentOverall.maximummarks"));

				}
			}

		}
		return errors;
	}

	public ActionForward viewInternalDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamAssignmentOverallMarksForm objform = (ExamAssignmentOverallMarksForm) form;
		errors.clear();
		messages.clear();
		try {
			// objform.setSubInternal(0);
			// objform.setAttendance(0);
			// objform.setAssignment(0);
			objform = handler.getInternalMarcks(objform);
			// if (objform.getSubInternal() == 0 && objform.getAttendance() == 0
			// && objform.getAssignment() == 0) {
			// errors.add("error", new ActionError(
			// "knowledgepro.exam.subjectrulesettings.noInternal"));
			// saveErrors(request, errors);
			// }

		} catch (Exception e) {
			log.error("error in final submit of education page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				objform.setErrorMessage(msg);
				objform.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}

		return mapping
				.findForward(CMSConstants.EXAM_ASSIGNMENT_OVERALL_MARKS_POP_UP);
	}

	public ActionForward resetValues(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamAssignmentOverallMarksForm objform = (ExamAssignmentOverallMarksForm) form;
		errors.clear();
		messages.clear();
		String assignMentOverall = objform.getAssignmentOverall();

		objform.setCourseId("");
		objform.setSubjectType("");
		objform.setMapScheme(new HashMap<String, String>());
		objform.setMapSubject(new HashMap<Integer, String>());
		Integer examId = null;
		Integer accYear = null;
		objform.setTypeMap(handler.getTypeByAssignmentOverall("overall"));

		if (assignMentOverall.equalsIgnoreCase("overall")) {
	//		objform.setListExamName(handler.getExamNameListForRRS());
			setExamNameMapToForm(objform);
			/*if (objform.getListExamName() != null
					&& objform.getListExamName().size() != 0) {
				KeyValueTO to = objform.getListExamName().get(0);
				examId = to.getId();*/

				ExamUpdateExcludeWithheldHandler h2 = new ExamUpdateExcludeWithheldHandler();
				if(objform.getExamName()!= null && !objform.getExamName().trim().isEmpty()){
					objform.setListCourseMap(h2	.getCourse(objform.getExamName()));
				}
				else{
					objform.setListCourseMap(null);
				}
				/*else
				{
					objform.setListCourseMap(h2	.getCourse(Integer.toString(examId)));
				}
			}*/
		} else {
			objform.setExamName("");
			accYear = Integer.parseInt(objform.getJoiningBatch());
			objform.setListCourseMap(handler.getCourseByAcademicYear(accYear));
		}
		objform.setTypeMap(handler
				.getTypeByAssignmentOverall(assignMentOverall));

		return mapping.findForward(CMSConstants.EXAM_ASSIGNMENT_OVERALL_MARKS);
	}

}
