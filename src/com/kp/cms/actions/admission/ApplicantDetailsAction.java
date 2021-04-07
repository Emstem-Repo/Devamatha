package com.kp.cms.actions.admission;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.admission.InterviewResultEntryForm;
import com.kp.cms.handlers.admission.FinalMeritListSearchHandlar;
import com.kp.cms.handlers.admission.InterviewResultEntryHandler;
import com.kp.cms.to.admission.AdmApplnTO;
import com.kp.cms.utilities.CommonUtil;

/**
 * @author manjunatha.br
 * view applicant details and selects the preference
 */
public class ApplicantDetailsAction extends BaseDispatchAction {

	/**
	 * Initilize and view the application details.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getApplicantDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		InterviewResultEntryForm interviewResultEntryForm = (InterviewResultEntryForm) form;
		interviewResultEntryForm.setViewParish(false);
		try {
			String applicationNumber = interviewResultEntryForm.getApplicationNumber().trim();
			int applicationYear = Integer.parseInt(interviewResultEntryForm.getApplicationYear().trim());
			int courseId = Integer.parseInt(interviewResultEntryForm.getCourseId().trim());

			AdmApplnTO applicantDetails = InterviewResultEntryHandler.getInstance().getApplicantDetails(applicationNumber, applicationYear, courseId,request);
			interviewResultEntryForm.setApplicantDetails(applicantDetails);
			Properties prop = new Properties();
			try {
				InputStream inputStream = CommonUtil.class.getClassLoader()
						.getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
				prop.load(inputStream);
			} 
			catch (IOException e) {
				//log.error("Error occured at convertToExcel of studentDetailsReportHelper ",e);
				throw new IOException(e);
			}
			String fileName=prop.getProperty(CMSConstants.PRG_TYPE);
			if(interviewResultEntryForm.getApplicantDetails().getCourse().getProgramTypeId()==(Integer.parseInt(fileName))){
				interviewResultEntryForm.setViewextradetails(true);
			}
			interviewResultEntryForm.setCheckReligionId(CMSConstants.RELIGION_CHRISTIAN_TYPE);
			if(interviewResultEntryForm.getCheckReligionId()==(Integer.parseInt(interviewResultEntryForm.getApplicantDetails().getPersonalData().getReligionId()))){
				interviewResultEntryForm.setViewParish(true);
			}
		} catch (Exception e) {
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				interviewResultEntryForm.setErrorMessage(msg);
				interviewResultEntryForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		return mapping.findForward(CMSConstants.INTERVIEW_RESULT_ENTRY);
	}
	
	/**
	 * Initialize and view the application details for preference selection.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward selectPreference(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		InterviewResultEntryForm interviewResultEntryForm = (InterviewResultEntryForm) form;
		try {
			String applicationNumber = interviewResultEntryForm.getApplicationNumber().trim();
			int applicationYear = Integer.parseInt(interviewResultEntryForm.getApplicationYear().trim());

			int selectedCourseId = InterviewResultEntryHandler.getInstance().getSelectedCourse(applicationNumber, applicationYear);
			AdmApplnTO applicantDetails = InterviewResultEntryHandler.getInstance().getApplicantDetailsByPreference(applicationNumber, applicationYear, selectedCourseId,request);
			interviewResultEntryForm.setApplicantDetails(applicantDetails);
			
		} catch (Exception e) {
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				interviewResultEntryForm.setErrorMessage(msg);
				interviewResultEntryForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		return mapping.findForward(CMSConstants.SELECT_PREFERENCE);
	}
	
	/**
	 * Updates the selected preference.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateSelectedPreference(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ActionMessages messages = new ActionMessages();
		ActionMessage message = null;
		InterviewResultEntryForm interviewResultEntryForm = (InterviewResultEntryForm) form;

		setUserId(request, interviewResultEntryForm);

		try {
			FinalMeritListSearchHandlar finalMeritListSearchHandlar = FinalMeritListSearchHandlar
					.getInstanse();
			finalMeritListSearchHandlar
					.updateSelectedPreference(interviewResultEntryForm);
			message = new ActionMessage(
					"knowledgepro.admission.updatepreference");
			messages.add("messages", message);
			saveMessages(request, messages);
		} catch (Exception e) {
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				interviewResultEntryForm.setErrorMessage(msg);
				interviewResultEntryForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		return mapping.findForward(CMSConstants.SELECT_PREFERENCE);
	}
}