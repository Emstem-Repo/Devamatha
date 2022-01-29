package com.kp.cms.actions.reports;

import java.util.Calendar;
import java.util.Date;
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
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;

import com.kp.cms.forms.reports.StudentForUniversityForm;
import com.kp.cms.handlers.admin.OrganizationHandler;
import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;

import com.kp.cms.handlers.reports.StudentForUniversityHandler;
import com.kp.cms.to.admin.OrganizationTO;

import com.kp.cms.to.reports.CourseWithStudentUniversityTO;
import com.kp.cms.to.reports.StudentForUniversityTO;

public class StudentForUniversityReportAction extends BaseDispatchAction {
	
	private static final Log log = LogFactory.getLog(StudentForUniversityReportAction.class);
	/**
	 * Method to set the required data to the form to display it in StudentListForUniversity.jsp
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initStudentForUniversity(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered Student for University report input");
		StudentForUniversityForm studentForUniversityForm = (StudentForUniversityForm) form;
		studentForUniversityForm.resetFields();
		setRequiredDatatoForm(studentForUniversityForm, request);
		log.info("Exit Student for University report input");
		
		return mapping.findForward(CMSConstants.INIT_STUDENT_UNIVERSITY);
	}
	/**
	 * Method to set all active Program Types to the form
	 * @param interviewBatchEntryForm
	 * @throws Exception
	 */
	private void setRequiredDatatoForm(StudentForUniversityForm studentForUniversityForm, HttpServletRequest request) throws Exception {
		if(ProgramTypeHandler.getInstance().getProgramType() != null){
			studentForUniversityForm.setProgramTypeList(ProgramTypeHandler.getInstance().getProgramType());
		}
		if(studentForUniversityForm.getProgramId()!= null && studentForUniversityForm.getProgramId().length() >0){
			
			Map<Integer,String> courseMap = CommonAjaxHandler.getInstance().getCourseByProgram(
					Integer.valueOf(studentForUniversityForm.getProgramId()));
			request.setAttribute("coursesMap", courseMap);
		}
		if(studentForUniversityForm.getProgramTypeId()!= null && studentForUniversityForm.getProgramTypeId().length() >0){
			
			Map<Integer,String> programMap = CommonAjaxHandler.getInstance().getProgramsByProgramType(
					Integer.valueOf(studentForUniversityForm.getProgramTypeId()));
			request.setAttribute("programMap", programMap);
		}		
	}
	/**
	 * getting the list of offline admission students
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getListOfStudent(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("entering into submit student for University  type action");	
		StudentForUniversityForm studentForUniversityForm = (StudentForUniversityForm)form;
		 ActionErrors errors = studentForUniversityForm.validate(mapping, request);
		if (!errors.isEmpty()) {
			saveErrors(request, errors);	
			setRequiredDatatoForm(studentForUniversityForm, request);
			return mapping.findForward(CMSConstants.INIT_STUDENT_UNIVERSITY);
		}
		try {
			StudentForUniversityHandler studentForUniversityHandler=StudentForUniversityHandler.getInstance();
			List<CourseWithStudentUniversityTO> StudentList=studentForUniversityHandler.getAllStudentsForUniversity(studentForUniversityForm);
			studentForUniversityForm.setStudentList(StudentList);
			if (StudentList.isEmpty()) {
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND));
				saveErrors(request, errors);
				setRequiredDatatoForm(studentForUniversityForm, request);
				log.info("Exit offline list In studentFor University Issue Report Action");
				return mapping.findForward(CMSConstants.INIT_STUDENT_UNIVERSITY);
			} 
			OrganizationHandler orgHandler= OrganizationHandler.getInstance();
			List<OrganizationTO> tos=orgHandler.getOrganizationDetails();
			if(tos!=null && !tos.isEmpty())
			{
				OrganizationTO orgTO=tos.get(0);
				studentForUniversityForm.setOrganizationName(orgTO.getOrganizationName());
			}
			/*Calendar cal= Calendar.getInstance();
			cal.setTime(new Date());*/
			int year=Integer.parseInt(studentForUniversityForm.getYear());
			//studentForUniversityForm.setYear((String.valueOf(cal.get(cal.YEAR))));
			studentForUniversityForm.setNextYear(String.valueOf(year+1));
			
			request.getSession().setAttribute("totalStudentList", studentForUniversityForm.getTotalList());
			request.getSession().setAttribute("ListOfStudents", StudentList);
			
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			studentForUniversityForm.setErrorMessage(msg);
			studentForUniversityForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		
		return mapping.findForward(CMSConstants.STUDENT_UNIVERSITY_Report);
	}
}
