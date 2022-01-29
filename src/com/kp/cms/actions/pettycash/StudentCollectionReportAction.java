package com.kp.cms.actions.pettycash;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
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
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.pettycash.StudentCollectionReportForm;
import com.kp.cms.handlers.admin.OrganizationHandler;
import com.kp.cms.handlers.pettycash.StudentCollectionReportHandler;
import com.kp.cms.to.admin.OrganizationTO;
import com.kp.cms.to.pettycash.StudentCollectionReportTO;
import com.kp.cms.utilities.CommonUtil;
public class StudentCollectionReportAction extends BaseDispatchAction {
	
	private static final Log log = LogFactory.getLog(StudentCollectionReportAction.class);

	public ActionForward initStudentReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception 
			{
		log.info("Entered initStudentReport..");
		StudentCollectionReportForm studentCollectionReportForm = (StudentCollectionReportForm)form;
		if(request.getSession().getAttribute(CMSConstants.STUDENT_COLLECTION_REPORT)!=null){
			request.getSession().removeAttribute(CMSConstants.STUDENT_COLLECTION_REPORT);			
		}
		studentCollectionReportForm.resetFields();
		log.info("Exit initStudentReport..");
		return mapping.findForward(CMSConstants.INIT_STUDENTS_COLLECTION_REPORT);
	}
	
	public ActionForward submitStudentReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entered submitStudentReport..");
		HttpSession session = request.getSession(false);
		StudentCollectionReportForm studentCollectionReportForm = (StudentCollectionReportForm) form;
		 ActionMessages errors =studentCollectionReportForm.validate(mapping, request);
		validateDate(studentCollectionReportForm, errors);
		if (errors.isEmpty()) {	
			try {
			List<StudentCollectionReportTO> studentFineDetails= StudentCollectionReportHandler.getInstance().getDetails(studentCollectionReportForm);
			if(studentFineDetails.isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.INIT_STUDENTS_COLLECTION_REPORT);
			}	
			if(studentFineDetails !=null){			
				session.setAttribute(CMSConstants.STUDENT_COLLECTION_REPORT,studentFineDetails );
				}
			}
			catch(ApplicationException ae){
				String msg = super.handleApplicationException(ae);
				studentCollectionReportForm.setErrorMessage(msg);
				studentCollectionReportForm.setErrorStack(ae.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				studentCollectionReportForm.setErrorMessage(msg);
				studentCollectionReportForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			return mapping.findForward(CMSConstants.INIT_STUDENTS_COLLECTION_REPORT);
		}
		return mapping.findForward(CMSConstants.SUBMIT_STUDENT_COLLECTION_REPORT);
	}
	
	
	private void validateDate(StudentCollectionReportForm studentCollectionReportForm,
			ActionMessages errors) {
			if(studentCollectionReportForm.getStartDate()!=null && !StringUtils.isEmpty(studentCollectionReportForm.getStartDate())&& !CommonUtil.isValidDate(studentCollectionReportForm.getStartDate())){
					errors.add("errors",new ActionError(CMSConstants.STUDENT_COLLECTION_REPORT_STARTDATE_INVALID));
			}
			if(studentCollectionReportForm.getEndDate()!=null && !StringUtils.isEmpty(studentCollectionReportForm.getEndDate())&& !CommonUtil.isValidDate(studentCollectionReportForm.getEndDate())){
					errors.add("errors",new ActionError(CMSConstants.STUDENT_COLLECTION_REPORT_ENDDATE_INVALID));
			}
			//if start date greater than end date then showing error message
			if(CommonUtil.checkForEmpty(studentCollectionReportForm.getStartDate()) && CommonUtil.checkForEmpty(studentCollectionReportForm.getEndDate())){
				Date startDate = CommonUtil.ConvertStringToDate(studentCollectionReportForm.getStartDate());
				Date endDate = CommonUtil.ConvertStringToDate(studentCollectionReportForm.getEndDate());

				Calendar cal1 = Calendar.getInstance();
				cal1.setTime(startDate);
				Calendar cal2 = Calendar.getInstance();
				cal2.setTime(endDate);
				long daysBetween = CommonUtil.getDaysBetweenDates(cal1, cal2);
				if(daysBetween <= 0) {
					errors.add("error", new ActionError(CMSConstants.KNOWLEDGEPRO_STARTDATE_CONNOTBELESS));
				}
			}
	}
	
	/**
	 * print the student Result
	 */
	public ActionForward printStudentReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		StudentCollectionReportForm studentCollectionReportForm = (StudentCollectionReportForm) form;
		OrganizationHandler orgHandler= OrganizationHandler.getInstance();
		List<OrganizationTO> tos=orgHandler.getOrganizationDetails();
		if(tos!=null && !tos.isEmpty())
		{
			OrganizationTO orgTO=tos.get(0);
			studentCollectionReportForm.setOrganizationName(orgTO.getOrganizationName());
		}
		
		return mapping.findForward(CMSConstants.PRINT_STUDENT_RESULT);
	}
}
