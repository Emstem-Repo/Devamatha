package com.kp.cms.actions.reports;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import com.kp.cms.actions.attendance.TeacherClassEntryAction;
import com.kp.cms.bo.admin.ClassSchemewise;
import com.kp.cms.bo.admin.TeacherClassSubject;
import com.kp.cms.bo.exam.MarksEntryDetails;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.forms.attendance.TeacherClassEntryForm;
import com.kp.cms.forms.employee.EmployeeInfoEditForm;
import com.kp.cms.forms.employee.EmployeeInfoFormNew;
import com.kp.cms.forms.reports.ViewInternalMarksForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.attendance.StudentAttendanceSummaryHandler;
import com.kp.cms.handlers.attendance.TeacherClassEntryHandler;
import com.kp.cms.handlers.employee.EmployeeInfoEditHandler;
import com.kp.cms.handlers.employee.EmployeeInfoHandlerNew;
import com.kp.cms.handlers.reports.ViewInternalMarkHandler;
import com.kp.cms.helpers.attendance.StudentAttendanceSummaryHelper;
import com.kp.cms.to.employee.EmpQualificationLevelTo;
import com.kp.cms.to.exam.ExamMarksEntryDetailsTO;
import com.kp.cms.to.reports.StudentWiseSubjectSummaryTO;
import com.kp.cms.transactionsimpl.reports.ViewInternalMarksTxnImpl;
import com.kp.cms.utilities.CurrentAcademicYear;

/**
 * @author Administrator
 *
 */
public class ViewInternalMarksAction extends BaseDispatchAction {
	private static Log log = LogFactory.getLog(ViewInternalMarksAction.class);
	
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initViewInternalMarks(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.debug("Enter: addTeacherClass Action");
		ViewInternalMarksForm viewInternalMarksForm= (ViewInternalMarksForm)form;
		viewInternalMarksForm.reset();
		setUserId(request,viewInternalMarksForm);
		int teacherId=(Integer.parseInt(viewInternalMarksForm.getUserId()));
		int year=CurrentAcademicYear.getInstance().getAcademicyear();
		Map<Integer, String> classMap=ViewInternalMarkHandler.getInstance().getClassByYear(year, teacherId);
		viewInternalMarksForm.setClassMap(classMap);
		setUserId(request,viewInternalMarksForm);
		log.debug("Exit: action class addTeacherClassAction");
		return mapping.findForward(CMSConstants.INIT_VIEW_INTERNAL_MARKS_DETAILS);
	}
	

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getInternalMarks(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.debug("Enter: addTeacherClass Action");
		ViewInternalMarksForm viewInternalMarksForm= (ViewInternalMarksForm)form;
		try{
			ViewInternalMarkHandler.getInstance().getStudentInternalMarks(viewInternalMarksForm,request);
			if(viewInternalMarksForm.getListCourseDetails() !=null){	
				HttpSession session = request.getSession();
				session.setAttribute(CMSConstants.TEACHER_INTERNAL_MARKS_REPORT, viewInternalMarksForm.getListCourseDetails());
			}
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			viewInternalMarksForm.setErrorMessage(msg);
			viewInternalMarksForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.debug("Exit: action class addTeacherClassAction");
		return mapping.findForward(CMSConstants.VIEW_INTERNAL_MARKS_DETAILS);
	}
	public ActionForward printTeacherReport(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered TeacherReport");
		log.info("Exit TeacherReport");
		return mapping.findForward(CMSConstants.VIEW_INTERNAL_MARKS_DETAILS_PRINT);
	}
}
