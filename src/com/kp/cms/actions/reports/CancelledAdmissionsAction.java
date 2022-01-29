package com.kp.cms.actions.reports;

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
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.reports.CancelledAdmissionsForm;
import com.kp.cms.handlers.admin.OrganizationHandler;
import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.handlers.reports.CancelledAdmissionsHandler;
import com.kp.cms.to.admin.OrganizationTO;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.utilities.CommonUtil;

@SuppressWarnings("deprecation")
public class CancelledAdmissionsAction extends BaseDispatchAction{
	private static Log log = LogFactory.getLog(CancelledAdmissionsAction.class);
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initCancelledAdmission(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("Entered initCancelledAdmission");
		CancelledAdmissionsForm cancelledAdmForm = (CancelledAdmissionsForm)form;
		setRequiredDataToForm(request);
		cancelledAdmForm.resetFields();
		HttpSession session = request.getSession(false);
		session.removeAttribute("cancelledList");
		log.info("Exit initCancelledAdmission");
		return mapping.findForward(CMSConstants.SEARCH_CANCELLED_ADMISSIONS_REPORT);
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward searchCancelledAdmissions(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
									HttpServletResponse response) throws Exception {
		log.debug("inside searchCancelledAdmissions");
		CancelledAdmissionsForm cancelledAdmForm = (CancelledAdmissionsForm) form;
		HttpSession session = request.getSession(false);
		if(session.getAttribute("cancelledList")==null){
			ActionMessages messages = new ActionMessages();
			ActionErrors errors = cancelledAdmForm.validate(mapping, request);
			validateTime(cancelledAdmForm, errors);
			try {
				if (!errors.isEmpty()) {
					saveErrors(request, errors);
					setRequiredDataToForm(request);
					return mapping.findForward(CMSConstants.SEARCH_CANCELLED_ADMISSIONS_REPORT);
				}	
				if(cancelledAdmForm.getYear()!= null && !cancelledAdmForm.getYear().trim().isEmpty()){
					int newYear = Integer.parseInt(cancelledAdmForm.getYear())+ 1; 
					cancelledAdmForm.setAcademicYear(cancelledAdmForm.getYear() + "-" +  (Integer.toString(newYear).substring(2, 4)));
				}				
				OrganizationHandler orgHandler= OrganizationHandler.getInstance();
				List<OrganizationTO> tos=orgHandler.getOrganizationDetails();
				if(tos!=null && !tos.isEmpty())
				{
					OrganizationTO orgTO=tos.get(0);
					cancelledAdmForm.setOrganizationName(orgTO.getOrganizationName());
				}	
				List<StudentTO> stuList  = CancelledAdmissionsHandler.getInstance().getCancelledAdmissionDetails(cancelledAdmForm);
				session.setAttribute("cancelledList",stuList);
			}catch (BusinessException businessException) {
				log.info("Exception searchCancelledAdmissions");
				String msgKey = super.handleBusinessException(businessException);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add(CMSConstants.MESSAGES, message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}catch (Exception exception) {	
				String msg = super.handleApplicationException(exception);
				cancelledAdmForm.setErrorMessage(msg);
				cancelledAdmForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}	
		log.debug("exit searchSubjectGroup");
		return mapping.findForward(CMSConstants.SUBMIT_CANCELLED_ADMISSIONS_REPORT);
	}	
	/**
	 * This method sets the required data to the form
	 * @param request
	 * @throws Exception
	 */
	public void setRequiredDataToForm(HttpServletRequest request) throws Exception{
		log.info("start setRequiredDataToForm");	
	    
		//setting programList to Request
		List<ProgramTypeTO> programTypeList = ProgramTypeHandler.getInstance().getProgramType();
		request.setAttribute("programTypeList", programTypeList);

		log.info("Exit setRequiredDataToForm");	
	}
	/**
	 * Method to validate the time format
	 * @param interviewBatchEntryForm
	 * @param errors
	 */
	private void validateTime(CancelledAdmissionsForm cancelledAdmForm, ActionErrors errors) {
		
		if(cancelledAdmForm.getFromDate()!=null && !StringUtils.isEmpty(cancelledAdmForm.getFromDate())&& !CommonUtil.isValidDate(cancelledAdmForm.getFromDate())){
			if (errors.get(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID) != null&& !errors.get(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID).hasNext()) {
				errors.add(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID,new ActionError(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID));
			}
		}
		if(cancelledAdmForm.getToDate()!=null && !StringUtils.isEmpty(cancelledAdmForm.getToDate())&& !CommonUtil.isValidDate(cancelledAdmForm.getToDate())){
			if (errors.get(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID) != null&& !errors.get(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID).hasNext()) {
				errors.add(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID,new ActionError(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID));
			}
		}
		if(CommonUtil.checkForEmpty(cancelledAdmForm.getFromDate()) && CommonUtil.checkForEmpty(cancelledAdmForm.getToDate()) && CommonUtil.isValidDate(cancelledAdmForm.getFromDate()) && CommonUtil.isValidDate(cancelledAdmForm.getToDate())){
			Date startDate = CommonUtil.ConvertStringToDate(cancelledAdmForm.getFromDate());
			Date endDate = CommonUtil.ConvertStringToDate(cancelledAdmForm.getToDate());

			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(startDate);
			Calendar cal2 = Calendar.getInstance();
			cal2.setTime(endDate);
			long daysBetween = CommonUtil.getDaysBetweenDates(cal1, cal2);
			if(daysBetween <= 0) {
				errors.add("error", new ActionError("knowledgepro.attendance.activityattendence.greaterenddate"));
			}
		}
	}
}