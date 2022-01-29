package com.kp.cms.actions.studentfeedback;

import java.util.Calendar;
import java.util.Map;

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

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.studentfeedback.FacultyEvaluationReportForm;
import com.kp.cms.forms.studentfeedback.FacultyEvaluationReportPrincipalForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.studentfeedback.FacultyEvaluationReportHandler;
import com.kp.cms.handlers.studentfeedback.FacultyEvaluationReportPrincipalHandler;
import com.kp.cms.handlers.usermanagement.UserInfoHandler;
import com.kp.cms.utilities.CurrentAcademicYear;

public class FacultyEvaluationReportPrincipalAction extends BaseDispatchAction{
	private static final Log log = LogFactory.getLog(FacultyEvaluationReportAction.class);

	public ActionForward initFacultyEvaluationReportPrincipal(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {	
		FacultyEvaluationReportPrincipalForm facultyEvaluationReportPrincipalForm = (FacultyEvaluationReportPrincipalForm) form;
		facultyEvaluationReportPrincipalForm.resetFeilds();
		setUserId(request, facultyEvaluationReportPrincipalForm);
		setDepartmentMapToRequest(request, facultyEvaluationReportPrincipalForm);
		setClassMapToRequest(request, facultyEvaluationReportPrincipalForm);
		setSubjectMapToRequest(request, facultyEvaluationReportPrincipalForm);
		return mapping.findForward(CMSConstants.FACULTY_EVALUATION_REPORT_PRINCIPAL);
	}	

	public void setTeacherMapToRequest(HttpServletRequest request,FacultyEvaluationReportPrincipalForm facultyEvaluationReportPrincipalForm)	throws Exception {
		log.info("Entering into setTeacherMapToRequest of FacultyEvaluationReportPrincipalAction");
		try {
			if (facultyEvaluationReportPrincipalForm.getAcademicYear() == null
					|| facultyEvaluationReportPrincipalForm.getAcademicYear().isEmpty()) {
				// Below statements is used to get the current year and for the
				// year get the class Map.
				Calendar calendar = Calendar.getInstance();
				int currentYear = calendar.get(Calendar.YEAR);
				int year = CurrentAcademicYear.getInstance().getAcademicyear();
				if (year != 0) {
					currentYear = year;
				}		
				facultyEvaluationReportPrincipalForm.setAcademicYear(Integer.toString(currentYear));
				Map<Integer, String> teacherMap = CommonAjaxHandler.getInstance().getTeachersForFacultyEvaluationReportPrincipal(facultyEvaluationReportPrincipalForm);
				request.setAttribute("teacherMap", teacherMap);
			}
			// Otherwise get the classMap for the selected year
			else {
				int year = Integer.parseInt(facultyEvaluationReportPrincipalForm.getAcademicYear().trim());
				Map<Integer, String> teacherMap = CommonAjaxHandler.getInstance().getTeachersForFacultyEvaluationReportPrincipal(facultyEvaluationReportPrincipalForm);
				request.setAttribute("teacherMap", teacherMap);
			}
		} catch (Exception e) {
			log.error("Error occured at setTeacherMapToRequest in  FacultyEvaluationReportPrincipalAction Batch Action",e);
			throw new ApplicationException(e);
		}
		log.info("Leaving into setTeacherMapToRequest of FacultyEvaluationReportPrincipalAction");
	}
	public void setClassMapToRequest(HttpServletRequest request,FacultyEvaluationReportPrincipalForm facultyEvaluationReportPrincipalForm) throws Exception {
		log.info("Entering into setClassMapToRequest of FacultyEvaluationReportPrincipalAction");
		try {
			Map<Integer, String> classMap = CommonAjaxHandler.getInstance().getClassesForFacultyEvaluationReportPrincipal(facultyEvaluationReportPrincipalForm);
				request.setAttribute("classMap", classMap);			
		} catch (Exception e) {
			log.error("Error occured at setClassMapToRequest in  FacultyEvaluationReportPrincipalAction Batch Action",e);
			throw new ApplicationException(e);
		}
		log.info("Leaving into setClassMapToRequest of FacultyEvaluationReportPrincipalAction");
	}
	public void setSubjectMapToRequest(HttpServletRequest request,FacultyEvaluationReportPrincipalForm facultyEvaluationReportPrincipalForm) throws Exception {
		log.info("Entering into setSubjectMapToRequest of FacultyEvaluationReportAction");
		int classId;
		if (facultyEvaluationReportPrincipalForm.getClassId() != null
				&& facultyEvaluationReportPrincipalForm.getClassId().length() != 0) {
			try {
				classId = Integer.parseInt(facultyEvaluationReportPrincipalForm.getClassId());
				// The below map contains key as id and value as name of subject.
				Map<Integer, String> subjectMap = CommonAjaxHandler.getInstance().getSubjectsForFacultyEvaluationReportPrincipal(facultyEvaluationReportPrincipalForm,classId);
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}
		log.info("Leaving into setSubjectMapToRequest of FacultyEvaluationReportAction");
	}
	public ActionForward printReport(ActionMapping mapping, ActionForm form,HttpServletRequest request,
			 HttpServletResponse response)
			throws Exception {	
		FacultyEvaluationReportPrincipalForm facultyEvaluationReportPrincipalForm = (FacultyEvaluationReportPrincipalForm) form;		
		boolean exist = FacultyEvaluationReportPrincipalHandler.getInstance().isDataExist(facultyEvaluationReportPrincipalForm);
		facultyEvaluationReportPrincipalForm.setDataExist(exist);
		if(exist==false)
		{			
			return mapping.findForward(CMSConstants.FACULTY_EVALUATION_REPORT_PRINCIPAL_PRINT);
		}
		else{
			FacultyEvaluationReportPrincipalHandler.getInstance().setFacultyEvaluationDetails(facultyEvaluationReportPrincipalForm);
			FacultyEvaluationReportPrincipalHandler.getInstance().setClassAverage(facultyEvaluationReportPrincipalForm);
			FacultyEvaluationReportPrincipalHandler.getInstance().setDepartmentAverage(facultyEvaluationReportPrincipalForm);
			return mapping.findForward(CMSConstants.FACULTY_EVALUATION_REPORT_PRINCIPAL_PRINT);
		}
	}
	public void setDepartmentMapToRequest(HttpServletRequest request,FacultyEvaluationReportPrincipalForm facultyEvaluationReportPrincipalForm)	throws Exception {
		log.info("Entering into setDepartmentMapToRequest of FacultyEvaluationReportPrincipalAction");
		try {
			if (facultyEvaluationReportPrincipalForm.getAcademicYear() == null
					|| facultyEvaluationReportPrincipalForm.getAcademicYear().isEmpty()) {
				// Below statements is used to get the current year and for the
				// year get the class Map.
				Calendar calendar = Calendar.getInstance();
				int currentYear = calendar.get(Calendar.YEAR);
				int year = CurrentAcademicYear.getInstance().getAcademicyear();
				if (year != 0) {
					currentYear = year;
				}		
				facultyEvaluationReportPrincipalForm.setAcademicYear(Integer.toString(currentYear));
				Map<Integer, String> departmentMap = CommonAjaxHandler.getInstance().getDepartmentsForFacultyEvaluationReportPrincipal(facultyEvaluationReportPrincipalForm);
				request.setAttribute("departmentMap", departmentMap);
			}
			// Otherwise get the classMap for the selected year
			else {
				int year = Integer.parseInt(facultyEvaluationReportPrincipalForm.getAcademicYear().trim());
				Map<Integer, String> departmentMap = CommonAjaxHandler.getInstance().getDepartmentsForFacultyEvaluationReportPrincipal(facultyEvaluationReportPrincipalForm);
				request.setAttribute("departmentMap", departmentMap);
				facultyEvaluationReportPrincipalForm.setDepartmentMap(departmentMap);
			}
		} catch (Exception e) {
			log.error("Error occured at setDepartmentMapToRequest in  FacultyEvaluationReportPrincipalAction Batch Action",e);
			throw new ApplicationException(e);
		}
		log.info("Leaving into setDepartmentMapToRequest of FacultyEvaluationReportPrincipalAction");
	}
	
	public ActionForward printReportDepartmentWise(ActionMapping mapping, ActionForm form,HttpServletRequest request,
			 HttpServletResponse response)
			throws Exception {	
		FacultyEvaluationReportPrincipalForm facultyEvaluationReportPrincipalForm = (FacultyEvaluationReportPrincipalForm) form;
		FacultyEvaluationReportPrincipalHandler.getInstance().setFacultyEvaluationDetailsDepartmentWise(facultyEvaluationReportPrincipalForm);		
		request.setAttribute("facultyAverage", facultyEvaluationReportPrincipalForm.getTotalFacultyAverage());
		return mapping.findForward(CMSConstants.FACULTY_EVALUATION_REPORT_PRINCIPAL_PRINT);		
	}
}
