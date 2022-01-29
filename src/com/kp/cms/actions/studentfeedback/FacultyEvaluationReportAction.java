package com.kp.cms.actions.studentfeedback;

import java.util.Calendar;
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
import com.kp.cms.actions.admission.BoardDetailsAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.admission.TCDetailsForm;
import com.kp.cms.forms.studentfeedback.FacultyEvaluationReportForm;
import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.studentfeedback.FacultyEvaluationReportHandler;
import com.kp.cms.handlers.usermanagement.UserInfoHandler;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.studentfeedback.FacultyEvaluationReportTo;
import com.kp.cms.to.usermanagement.UserInfoTO;
import com.kp.cms.utilities.CurrentAcademicYear;

public class FacultyEvaluationReportAction extends BaseDispatchAction{
	
	private static final Log log = LogFactory.getLog(FacultyEvaluationReportAction.class);

	public ActionForward initFacultyEvaluationReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {	
		FacultyEvaluationReportForm facultyEvaluationReportActionForm = (FacultyEvaluationReportForm) form;
		facultyEvaluationReportActionForm.resetFeilds();
		setUserId(request, facultyEvaluationReportActionForm);
		setClassMapToRequest(request, facultyEvaluationReportActionForm);
		setSubjectMapToRequest(request, facultyEvaluationReportActionForm);
		return mapping.findForward(CMSConstants.FACULTY_EVALUATION_REPORT);
	} 
	
	public void setClassMapToRequest(HttpServletRequest request,FacultyEvaluationReportForm facultyEvaluationReportForm) throws Exception {
		log.info("Entering into setClassMapToRequest of FacultyEvaluationReportAction");
		try {
			if (facultyEvaluationReportForm.getAcademicYear() == null
					|| facultyEvaluationReportForm.getAcademicYear().isEmpty()) {
				// Below statements is used to get the current year and for the
				// year get the class Map.
				Calendar calendar = Calendar.getInstance();
				int currentYear = calendar.get(Calendar.YEAR);
				int year = CurrentAcademicYear.getInstance().getAcademicyear();
				if (year != 0) {
					currentYear = year;
				}		

				Map<Integer, String> classMap = CommonAjaxHandler.getInstance().getClassesForFacultyEvaluationReport(facultyEvaluationReportForm,currentYear);
				request.setAttribute("classMap", classMap);
			}
			// Otherwise get the classMap for the selected year
			else {
				int year = Integer.parseInt(facultyEvaluationReportForm.getAcademicYear().trim());
				Map<Integer, String> classMap = CommonAjaxHandler.getInstance().getClassesForFacultyEvaluationReport(facultyEvaluationReportForm,year);
				request.setAttribute("classMap", classMap);
			}
		} catch (Exception e) {
			log.error("Error occured at setClassMapToRequest in  FacultyEvaluationReportAction Batch Action",e);
			throw new ApplicationException(e);
		}
		log.info("Leaving into setClassMapToRequest of FacultyEvaluationReportAction");
	}
	public void setSubjectMapToRequest(HttpServletRequest request,FacultyEvaluationReportForm facultyEvaluationReportForm) throws Exception {
		log.info("Entering into setSubjectMapToRequest of FacultyEvaluationReportAction");
		try {
			if (facultyEvaluationReportForm.getAcademicYear() == null
				|| facultyEvaluationReportForm.getAcademicYear().isEmpty()) {
			// Below statements is used to get the current year and for the
			// year get the class Map.
				Calendar calendar = Calendar.getInstance();
				int currentYear = calendar.get(Calendar.YEAR);
				int year = CurrentAcademicYear.getInstance().getAcademicyear();
				if (year != 0) {
					currentYear = year;
			}
			Map<Integer, String> subjectMap = CommonAjaxHandler.getInstance().getSubjectsForFacultyEvaluationReport(facultyEvaluationReportForm,currentYear);
			request.setAttribute("subjectMap", subjectMap);
			}
			// Otherwise get the classMap for the selected year
			else{
				int year = Integer.parseInt(facultyEvaluationReportForm.getAcademicYear().trim());
				Map<Integer, String> subjectMap = CommonAjaxHandler.getInstance().getSubjectsForFacultyEvaluationReport(facultyEvaluationReportForm,year);
				request.setAttribute("subjectMap", subjectMap);	
			}						
		} catch (Exception e) {
			log.error("Error occured at setSubjectMapToRequest in  FacultyEvaluationReportAction Batch Action",e);
			throw new ApplicationException(e);
		}
		log.info("Leaving into setSubjectMapToRequest of FacultyEvaluationReportAction");
	}
	public ActionForward printReport(ActionMapping mapping, ActionForm form,HttpServletRequest request,
			 HttpServletResponse response)
			throws Exception {	
		FacultyEvaluationReportForm facultyEvaluationReportForm = (FacultyEvaluationReportForm) form;
		boolean exist = FacultyEvaluationReportHandler.getInstance().isDataExist(facultyEvaluationReportForm);
		facultyEvaluationReportForm.setDataExist(exist);
		if(exist==false)
		{
			return mapping.findForward(CMSConstants.FACULTY_EVALUATION_REPORT_PRINT);
		}
		else{
			FacultyEvaluationReportHandler.getInstance().setFacultyEvaluationDetails(facultyEvaluationReportForm);
			FacultyEvaluationReportHandler.getInstance().setClassAverage(facultyEvaluationReportForm);
			FacultyEvaluationReportHandler.getInstance().setDepartmentAverage(facultyEvaluationReportForm);
			FacultyEvaluationReportHandler.getInstance().setRemarksGivenByStudent(facultyEvaluationReportForm);
			return mapping.findForward(CMSConstants.FACULTY_EVALUATION_REPORT_PRINT);
		}
	} 
	
	public ActionForward showFacultyEvaluationReportCalculation(ActionMapping mapping, ActionForm form,HttpServletRequest request,
			 HttpServletResponse response)
			throws Exception {	
		return mapping.findForward(CMSConstants.FACULTY_EVALUATION_REPORT_CALCULATION);		
	} 
}
