package com.kp.cms.actions.reports;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.reports.LeaveReportForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.reports.LeaveReportHandler;
import com.kp.cms.to.reports.LeaveReportTO;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.CurrentAcademicYear;

@SuppressWarnings("deprecation")

public class LeaveReportAction extends BaseDispatchAction {
	
	private static final Log log = LogFactory.getLog(LeaveReportAction.class);
	
	/**
	 * This method is used to display the leave report when link is clicked.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	
	public ActionForward initLeaveReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entered initLeaveReport method of LeaveReportAction class..");
		LeaveReportForm leaveReportForm = (LeaveReportForm)form;
		leaveReportForm.resetFields();
		setRequiredDataToForm(leaveReportForm,request);
		HttpSession session = request.getSession(false);
		session.removeAttribute("leaveReport");
		log.info("Exit initLeaveReport method of LeaveReportAction class..");
		return mapping.findForward(CMSConstants.LEAVE_REPORT);
	}
	
	/**
	 * This method is used to get the leave report details from database.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	
	public ActionForward submitLeaveReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("entered submitLeaveReport method of LeaveReportAction class..");
		LeaveReportForm leaveReportForm = (LeaveReportForm)form;
		HttpSession session = request.getSession(false);
		if(session.getAttribute("leaveReport")==null){
			ActionMessages messages = new ActionMessages();
			ActionErrors errors = leaveReportForm.validate(mapping, request);
			validateAttendanceDate(leaveReportForm, errors);
			if (errors.isEmpty()) {	
				try {
					Date prevdate = null;
					
					String stdate=CommonUtil.ConvertStringToDateFormat(leaveReportForm.getStartDate(), "dd/MM/yyyy","MM/dd/yyyy");
					String edate=CommonUtil.ConvertStringToDateFormat(leaveReportForm.getEndDate(), "dd/MM/yyyy","MM/dd/yyyy");
					Date startdate=new Date(stdate);
					Date enddate=new Date(edate);
					// to check start date cannot be greater than end date.
					if(startdate.compareTo(enddate)==1){
						
						errors.add("error", new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_DATECOMPARE));
						saveErrors(request, errors);
						setRequiredDataToForm(leaveReportForm, request);
						return mapping.findForward(CMSConstants.LEAVE_REPORT);	
					}
					
					if(prevdate!=null){
							if(prevdate.compareTo(startdate)==1){
								errors.add("error", new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_SEMESTERDATE));
								saveErrors(request, errors);
								setRequiredDataToForm(leaveReportForm, request);
								return mapping.findForward(CMSConstants.LEAVE_REPORT);	
							}if(prevdate.compareTo(startdate)==0){
								errors.add("error", new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_SEMESTERDATESAME));
								saveErrors(request, errors);
								setRequiredDataToForm(leaveReportForm, request);
								return mapping.findForward(CMSConstants.LEAVE_REPORT);	
							}
						}
					String previousdate=CommonUtil.ConvertStringToDateFormat(leaveReportForm.getEndDate(), "dd/MM/yyyy","MM/dd/yyyy");
					prevdate=new Date(previousdate);
					
					startdate=null;
					enddate=null;
					//call of handler.
				List<LeaveReportTO> leaveReportList = LeaveReportHandler.getInstance().getLeaveReportDetails(leaveReportForm);
					if(leaveReportList != null){
						session.setAttribute("leaveReport",leaveReportList);
					}
				}catch (BusinessException businessException) {
					log.info("Exception submitLeaveReport");
					String msgKey = super.handleBusinessException(businessException);
					ActionMessage message = new ActionMessage(msgKey);
					messages.add(CMSConstants.MESSAGES, message);
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				} catch (Exception exception) {	
					String msg = super.handleApplicationException(exception);
					leaveReportForm.setErrorMessage(msg);
					leaveReportForm.setErrorStack(exception.getMessage());
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				}
				}else {
					addErrors(request, errors);
					setRequiredDataToForm(leaveReportForm, request);
					return mapping.findForward(CMSConstants.LEAVE_REPORT);
				}
		}	
		log.info("Exit of submitLeaveReport method of LeaveReportAction class..");
		return mapping.findForward(CMSConstants.LEAVE_REPORT_SUBMIT);
	}

	/**
	 * This method is used to set Program Type to form.
	 * @param leaveReportForm
	 * @param request
	 * @throws Exception
	 */
	
	private void setRequiredDataToForm(LeaveReportForm leaveReportForm,
			HttpServletRequest request) throws Exception {
		Map<Integer, String> classMap = setpClassMapToRequest();
		leaveReportForm.setClassMap(classMap);
	}

	/**
	 * Sets all the classes for the current year in request scope
	 */
	private Map<Integer, String> setpClassMapToRequest() throws ApplicationException {
		log.info("entering into setpClassMapToRequest of MonthlyAttendanceEntryAction class.");
		try {
			Calendar calendar = Calendar.getInstance();
			int currentYear = calendar.get(Calendar.YEAR);
			// code by hari
			int year=CurrentAcademicYear.getInstance().getAcademicyear();
			if(year!=0){
				currentYear=year;
			}// end

			Map<Integer, String> classMap = CommonAjaxHandler.getInstance().getClassesByYear(currentYear);
			log.info("exit of setpClassMapToRequest of LeaveReportAction class.");
			return classMap;
		} catch (Exception e) {
			log.info("error in setpClassMapToRequest of LeaveReportAction class.",e);
			throw new ApplicationException(e);
		}
	}
	
	
	/**
	 * This method is used to validate date fields.
	 * @param leaveReportForm
	 * @param errors
	 * This method is used to validate DATE 
	 */
	
	private void validateAttendanceDate(LeaveReportForm leaveReportForm,ActionMessages errors) {
		
			if(leaveReportForm.getStartDate()!=null && !StringUtils.isEmpty(leaveReportForm.getStartDate())&& !CommonUtil.isValidDate(leaveReportForm.getStartDate())){
					errors.add("errors",new ActionError(CMSConstants.ATTANDANCE_REPORT_STARTDATE_INVALID));
			}
			if(leaveReportForm.getEndDate()!=null && !StringUtils.isEmpty(leaveReportForm.getEndDate())&& !CommonUtil.isValidDate(leaveReportForm.getEndDate())){
					errors.add("errors",new ActionError(CMSConstants.ATTANDANCE_REPORT_ENDDATE_INVALID));
			}
	}
}