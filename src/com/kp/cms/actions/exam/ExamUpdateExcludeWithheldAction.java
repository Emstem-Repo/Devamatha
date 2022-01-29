package com.kp.cms.actions.exam;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.exam.ExamUpdateExcludeWithheldForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.exam.ExamAssignStudentsToRoomHandler;
import com.kp.cms.handlers.exam.ExamMarksEntryHandler;
import com.kp.cms.handlers.exam.ExamUpdateExcludeWithheldHandler;
import com.kp.cms.utilities.CurrentAcademicYear;

public class ExamUpdateExcludeWithheldAction extends BaseDispatchAction {
	ExamUpdateExcludeWithheldHandler handler = new ExamUpdateExcludeWithheldHandler();
	ActionErrors errors = new ActionErrors();
	ActionMessages messages = new ActionMessages();

	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamUpdateExcludeWithheldForm objform = (ExamUpdateExcludeWithheldForm) form;
	//	objform.setListExamName(handler.getExamNameList());
		setRequiredDataToForm(objform);
		return mapping.findForward(CMSConstants.EXAM_UDT_EXU_WITH);

	}

	/**
	 * adding  new parameters to the form-academic year and exam type- Smitha
	 * @param objform
	 * @throws Exception
	 */
	private void setRequiredDataToForm(ExamUpdateExcludeWithheldForm objform) throws Exception{
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
	}

	public ActionForward getUpdateExcludeWithheld(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamUpdateExcludeWithheldForm objform = (ExamUpdateExcludeWithheldForm) form;
		errors.clear();
		messages.clear();
		errors = objform.validate(mapping, request);
		saveErrors(request, errors);
		setUserId(request, objform);
		
		if (errors.isEmpty()) {
			String examId = objform.getExamNameId();
			String courseId = objform.getCourseId();
			String schemeNo = objform.getSchemeNo();
			String oldSchemeNo = objform.getOldScheme();
			int academicYear = handler.getAcademicYearForExam(Integer.parseInt(examId));
			objform.setListUpdateExcludeWithheld(handler.getStudent_data(
					Integer.parseInt(courseId),Integer.parseInt(examId),Integer.parseInt(schemeNo), academicYear, Integer.parseInt(oldSchemeNo)));
			objform.setExamNameId_value(examId);
			objform.setCourseId(courseId);
			
			objform
					.setExamNameId(handler
							.getExamNameByExamId(Integer.parseInt(examId)));
			objform.setCourseName(handler.getCourseName(Integer
					.parseInt(courseId)));
			objform.setSchemeName(handler.getSchemeName(Integer
					.parseInt(schemeNo)));
			objform.setSchemeNo(schemeNo);
			
			return mapping.findForward(CMSConstants.EXAM_UDT_EXU_WITH_ADD);
		}
		objform.clearPage();
	//	objform.setListExamName(handler.getExamNameList());
		setRequiredDataToForm(objform);
		return mapping.findForward(CMSConstants.EXAM_UDT_EXU_WITH);
	}

	public ActionForward addUpdateExcludeWithheld(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamUpdateExcludeWithheldForm objform = (ExamUpdateExcludeWithheldForm) form;
		errors.clear();
		messages.clear();
		errors = objform.validate(mapping, request);
		saveErrors(request, errors);
		setUserId(request, objform);
		String examId = objform.getExamNameId_value();
		String courseId = objform.getCourseId();
//		String schemeNo = objform.getSchemeNo();
		String schemeNo = objform.getOldScheme();
		if (errors.isEmpty()) {
			String[] subjectIds = request.getParameterValues("exclude");
			ArrayList<Integer> listExcludeStudents = new ArrayList<Integer>();
			if (subjectIds != null &&  subjectIds.length != 0) {
				for (int x = 0; x < subjectIds.length; x++) {
					listExcludeStudents.add(Integer.parseInt(subjectIds[x]));
				}
			}

			String[] withheldIds = request.getParameterValues("withheld");
			ArrayList<Integer> listWithheldStudents = new ArrayList<Integer>();
			if (withheldIds != null && withheldIds.length != 0) {
				for (int x = 0; x < withheldIds.length; x++) {
					listWithheldStudents.add(Integer.parseInt(withheldIds[x]));
				}
			}
			
			handler.add(listExcludeStudents, listWithheldStudents, Integer
					.parseInt(examId), Integer.parseInt(courseId),Integer.parseInt(schemeNo), objform
					.getUserId());
		}
	//	objform.setListExamName(handler.getExamNameList());
		setRequiredDataToForm(objform);
		return mapping.findForward(CMSConstants.EXAM_UDT_EXU_WITH);
	}
}
