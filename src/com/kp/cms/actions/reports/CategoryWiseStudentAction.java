package com.kp.cms.actions.reports;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.reports.CategoryWiseStudentForm;
import com.kp.cms.handlers.admin.OrganizationHandler;
import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.reports.CategoryWiseStudentHandler;
import com.kp.cms.to.admin.CategoryWithStudentsTO;
import com.kp.cms.to.admin.OrganizationTO;
import com.kp.cms.to.admin.ProgramTypeTO;

public class CategoryWiseStudentAction extends BaseDispatchAction {
	
	private static final Log log = LogFactory.getLog(CategoryWiseStudentAction.class);
	private static final String CATEGORY = "categorystudent";
	private static final String TOTAL_STUDENT = "totalStudent";
/**
 * 
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return Initializes Categorywise Student report
 * @throws Exception
 */
	public ActionForward initCategoryWiseStudent(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("entering into initCategoryWiseStudent. of CategoryWiseStudentAction");	
		CategoryWiseStudentForm studentForm = (CategoryWiseStudentForm)form;
		try {
			//Sets programType to formbean
			setRequiredDataToForm(studentForm, request);
			studentForm.clear();
			HttpSession session = request.getSession(false);
			session.removeAttribute(CATEGORY);
		} catch (Exception e) {
			log.error("Error in initCategoryWiseStudent in CategoryWiseStudentAction",e);
			String msg = super.handleApplicationException(e);
			studentForm.setErrorMessage(msg);
			studentForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("leaving into initCategoryWiseStudent. of CategoryWiseStudentAction");	
		return mapping.findForward(CMSConstants.INIT_CATEGORY_WISE);
	}
	/**
	 * Gets the list of stuents in categorywise
	 */
	public ActionForward submitCategoryWiseStudent(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("entering into submitCategoryWiseStudent. of CategoryWiseStudentAction");	
		CategoryWiseStudentForm studentForm = (CategoryWiseStudentForm)form;
		HttpSession session = request.getSession(false);
		if(session.getAttribute(CATEGORY)==null){
			 ActionMessages errors = studentForm.validate(mapping, request);
			try {
				if(errors.isEmpty()){
					OrganizationHandler orgHandler= OrganizationHandler.getInstance();
					List<OrganizationTO> tos=orgHandler.getOrganizationDetails();
					if(tos!=null && !tos.isEmpty())
					{
						OrganizationTO orgTO=tos.get(0);
						studentForm.setOrganizationName(orgTO.getOrganizationName());
					}
					Calendar cal= Calendar.getInstance();
					cal.setTime(new Date());
					//studentForm.setAcademicYear(String.valueOf(cal.get(cal.YEAR)));
					List<CategoryWithStudentsTO> studentList = CategoryWiseStudentHandler.getInstance().getStudents(studentForm);
					studentForm.setStudentList(studentList);
					session.setAttribute(CATEGORY, studentList);
					session.setAttribute(TOTAL_STUDENT, studentForm.getTotalStudentList());
					
				}
				else{
					addErrors(request, errors);
					//Sets programType and program to formbean
					setRequiredDataToForm(studentForm, request);
					return mapping.findForward(CMSConstants.INIT_CATEGORY_WISE);
				}
			} catch (Exception e) {
				log.error("Error in submitCategoryWiseStudent in CategoryWiseStudentAction",e);
				String msg = super.handleApplicationException(e);
				studentForm.setErrorMessage(msg);
				studentForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}	
		log.info("Leaving into submitCategoryWiseStudent. of CategoryWiseStudentAction");
		return mapping.findForward(CMSConstants.SUBMIT_CATEGORY_WISE);
	}		
	
	/*
	 * This method sets the required data to form and request.
	 */
	public void setRequiredDataToForm(CategoryWiseStudentForm studentForm,HttpServletRequest request) throws Exception{
		log.info("entered setRequiredDataToForm. of CategoryWiseStudentAction");	
		    //setting programTypeList to Request
			List<ProgramTypeTO> programTypeList = ProgramTypeHandler.getInstance().getProgramType();
			request.setAttribute("programTypeList", programTypeList);
			
			Map<Integer,String> courseMap = new HashMap<Integer,String>();
			//Setting courseMap to Request
			if(studentForm.getProgramId()!=null && !studentForm.getProgramId().isEmpty()){
				courseMap = CommonAjaxHandler.getInstance().getCourseByProgram(Integer.parseInt(studentForm.getProgramId()));
			}
			request.setAttribute("courseMap", courseMap);
			log.info("Exit setRequiredDataToForm.CategoryWiseStudentAction");	
	}
}