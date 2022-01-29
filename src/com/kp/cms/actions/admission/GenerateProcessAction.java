package com.kp.cms.actions.admission;

import java.util.Date;
import java.util.Iterator;
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
import com.kp.cms.bo.admin.Organisation;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.admission.AdmissionStatusForm;
import com.kp.cms.forms.admission.GenerateProcessForm;
import com.kp.cms.handlers.admin.OrganizationHandler;
import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.handlers.admission.AdmissionStatusHandler;
import com.kp.cms.handlers.admission.GenerateProcessHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.to.admission.AdmissionStatusTO;
import com.kp.cms.transactions.admission.IGenerateProcessTransaction;
import com.kp.cms.transactionsimpl.admission.GenerateProcessTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

public class GenerateProcessAction extends BaseDispatchAction {
	
	private static final Log log = LogFactory.getLog(GenerateProcessAction.class);
	private static final String DATE = "MM/dd/yyyy"; 
	
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * This method is used to display interview edit time search page
	 */
	public ActionForward initGenerateProcess(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered initGenerateProcess input");
		GenerateProcessForm generateProcessForm = (GenerateProcessForm) form;
		generateProcessForm.resetFields();
		setRequiredDatatoForm(generateProcessForm,request);
		log.info("Exit initGenerateProcess input");
		
		return mapping.findForward(CMSConstants.INIT_GENERATE_PROCESS);
	}
	
	/**
	 * @param generateProcessForm
	 * @throws Exception
	 * This method is used to get program type and set it to the form
	 */
	private void setRequiredDatatoForm(GenerateProcessForm generateProcessForm,HttpServletRequest request) throws Exception {
		//set Program Type to the form
		if(ProgramTypeHandler.getInstance().getProgramType() != null){
			generateProcessForm.setProgramTypeList(ProgramTypeHandler.getInstance().getProgramType());
		}
		if(generateProcessForm.getProgramId()!=null && !generateProcessForm.getProgramId().isEmpty())
		{
			Map<Integer, String>coursesMap=CommonAjaxHandler.getInstance().getCourseByProgram(Integer.parseInt(generateProcessForm.getProgramId()));
			request.setAttribute("coursesMap", coursesMap);
			Map<Integer, String>interviewMap=CommonAjaxHandler.getInstance().getInterviewTypeByProgram(Integer.parseInt(generateProcessForm.getProgramId()), Integer.parseInt(generateProcessForm.getAppliedYear()));
			request.setAttribute("interviewMap",interviewMap);
		}
		if(generateProcessForm.getCourseId()!=null && !generateProcessForm.getCourseId().isEmpty())
		{
			Map<Integer, String>interviewMap=CommonAjaxHandler.getInstance().getInterviewTypeByCourse(Integer.parseInt(generateProcessForm.getCourseId()), Integer.parseInt(generateProcessForm.getAppliedYear()));
			request.setAttribute("interviewMap",interviewMap);
		}
	}
	
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * This method will display the candidates based on the selected criteria 
	 */
	public ActionForward generateProccessHtml(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered Interview Result - getSelectedInterCandidates");
		
		GenerateProcessForm generateProcessForm = (GenerateProcessForm) form;
		ActionMessages messages=new ActionMessages();
		ActionErrors errors = generateProcessForm.validate(mapping, request);
		validateTime(generateProcessForm, errors);
		setUserId(request,generateProcessForm);
		if (errors.isEmpty()) {
			try {
				List<AdmissionStatusTO> selectedCandidatesList = GenerateProcessHandler.getInstance().getCandidatesForInput(generateProcessForm,request);
				if (selectedCandidatesList.isEmpty()) {
					errors.add("error", new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND));
					saveErrors(request, errors);
					log.info("Exit Interview  Result - getSelectedInterCandidates size 0");
					return mapping.findForward(CMSConstants.INIT_GENERATE_PROCESS);
				} else {
					//save the selected candidates list to database
					boolean isAdded=GenerateProcessHandler.getInstance().saveDataToDatabase(selectedCandidatesList,generateProcessForm.getUserId());
					if(isAdded){
						messages.add(CMSConstants.MESSAGES,new ActionMessage("knowledgepro.admission.generateProccess.add.success"));
						generateProcessForm.resetFields();
						setRequiredDatatoForm(generateProcessForm,request);
						saveMessages(request, messages);
					}
					else{
						setRequiredDatatoForm(generateProcessForm,request);
						errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.generateProccess.add.failure"));
						addErrors(request, errors);
					}
				}
			}catch (Exception e) {
				String msg = super.handleApplicationException(e);
				generateProcessForm.setErrorMessage(msg);
				generateProcessForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			setRequiredDatatoForm(generateProcessForm,request);
			log.info("Exit Interview Result - getSelectedInterCandidates errors not empty ");
			return mapping.findForward(CMSConstants.INIT_GENERATE_PROCESS);
		}
		log.info("Exit Interview Result - getSelectedInterCandidates");
		return mapping.findForward(CMSConstants.INIT_GENERATE_PROCESS);
	}
	
	
	/**
	 * @param generateProcessForm
	 * @param errors
	 * This method will validate the time
	 */
	private void validateTime(GenerateProcessForm generateProcessForm,
			ActionErrors errors) {
		
		if (generateProcessForm.getInterviewStartDate() != null && !StringUtils.isEmpty(generateProcessForm.getInterviewStartDate())) {
			if (!CommonUtil.isValidDate(generateProcessForm.getInterviewStartDate())) {
				if (errors.get(CMSConstants.KNOWLEDGEPRO_ADMISSION_IOD_INVALID) != null && !errors.get(CMSConstants.KNOWLEDGEPRO_ADMISSION_IOD_INVALID).hasNext()) {
					errors.add(CMSConstants.KNOWLEDGEPRO_ADMISSION_IOD_INVALID, new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_IOD_INVALID));
				}
			} 
		}
		
		if(CommonUtil.checkForEmpty(generateProcessForm.getStartingTimeHours())){
			if(!StringUtils.isNumeric(generateProcessForm.getStartingTimeHours())){
				if(errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC).hasNext()){									
					errors.add(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC,new ActionError(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC));
				}
			}			
		}
		if(CommonUtil.checkForEmpty(generateProcessForm.getStartingTimeMins())){
			if(!StringUtils.isNumeric(generateProcessForm.getStartingTimeMins())){
				if(errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC).hasNext()){									
					errors.add(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC,new ActionError(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC));
				}
			}			
		}
		
		if(CommonUtil.checkForEmpty(generateProcessForm.getStartingTimeHours())){
			if(Integer.parseInt(generateProcessForm.getStartingTimeHours())>=24){
				if(errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT).hasNext()){									
					errors.add(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT,new ActionError(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT));
				}
			}			
		}
		if(CommonUtil.checkForEmpty(generateProcessForm.getStartingTimeMins())){
			if(Integer.parseInt(generateProcessForm.getStartingTimeMins())>=60){
				if(errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT).hasNext()){									
					errors.add(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT,new ActionError(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT));
				}
			}			
		}
		if(CommonUtil.checkForEmpty(generateProcessForm.getEndingTimeHours())){
			if(!StringUtils.isNumeric(generateProcessForm.getEndingTimeHours())){
				if(errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC).hasNext()){									
					errors.add(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC,new ActionError(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC));
				}
			}			
		}
		if(CommonUtil.checkForEmpty(generateProcessForm.getEndingTimeMins())){
			if(!StringUtils.isNumeric(generateProcessForm.getEndingTimeMins())){
				if(errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC).hasNext()){									
					errors.add(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC,new ActionError(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC));
				}
			}			
		}
		
		if(CommonUtil.checkForEmpty(generateProcessForm.getEndingTimeHours())){
			if(Integer.parseInt(generateProcessForm.getEndingTimeHours())>=24){
				if(errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT).hasNext()){									
					errors.add(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT,new ActionError(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT));
				}
			}			
		}
		if(CommonUtil.checkForEmpty(generateProcessForm.getEndingTimeMins())){
			if(Integer.parseInt(generateProcessForm.getEndingTimeMins())>=60){
				if(errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT).hasNext()){									
					errors.add(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT,new ActionError(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT));
				}
			}			
		}
	}
	
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param Initializes
	 *            Admission status
	 * @returns Admission Status with Application No & Date of birth as two
	 *          fields
	 * @throws Exception
	 */

	public ActionForward initOutsideAccessStatus(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entered into initOutsideAccessStatus of AdmissionStatusAction");
		GenerateProcessForm generateProcessForm = (GenerateProcessForm) form;
		HttpSession session= request.getSession(false);
		
		try {
			generateProcessForm.clear();
			generateProcessForm.clearadmissionStatusTO();
			Organisation organisation = OrganizationHandler.getInstance().getRequiredFile();
			if(organisation!=null){
				// set photo to session
				if(organisation.getLogoContentType()!=null){
					if(session!=null){
						session.setAttribute(CMSConstants.KNOWLEDGEPRO_LOGO, organisation.getLogo());
						session.setAttribute(CMSConstants.KNOWLEDGEPRO_TOPBAR, organisation.getTopbar());
					}
				}
			}
		} catch (Exception e) {
			log.error("Error occured at initOutsideAccessStatus of Admission Status Action",e);
			String msg = super.handleApplicationException(e);
			generateProcessForm.setErrorMessage(msg);
			generateProcessForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Leaving into initAdmissionStatus of AdmissionStatusAction");
		return mapping.findForward(CMSConstants.ADMISSION_APPLICATIONSTATUS);
	}
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param Takes
	 *            input as the Application No & Date of birth
	 * @param response
	 *            gives the status Selected/Not selected
	 * @returns the last round result if that candidate is not selected for admission
	 * @throws Exception
	 */
	
	public ActionForward getApplicationStatus(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Inside of getOnlineApplicationStatus of AdmissionStatusAction");
		GenerateProcessForm generateProcessForm = (GenerateProcessForm) form;
		 ActionErrors errors = generateProcessForm.validate(mapping, request);
		
		if (generateProcessForm.getDateOfBirth() != null && !StringUtils.isEmpty(generateProcessForm.getDateOfBirth())) {
			if (CommonUtil.isValidDate(generateProcessForm.getDateOfBirth())) {
				boolean isValid = validatefutureDate(generateProcessForm.getDateOfBirth());
				if (!isValid) {
					if (errors.get(CMSConstants.ADMISSIONFORM_DOB_LARGE) != null && !errors.get(CMSConstants.ADMISSIONFORM_DOB_LARGE).hasNext()) {
						errors.add(CMSConstants.ADMISSIONFORM_DOB_LARGE, new ActionError(CMSConstants.ADMISSIONFORM_DOB_LARGE));
					}
					generateProcessForm.clearadmissionStatusTO();
				}
			}	
		}
		try {
			if (errors.isEmpty()) {
				String applicationNo = generateProcessForm.getApplicationNo().trim();
				/**
				 * Calling the getDateOfBirth() by passing the applicationNo
				 * taken from UI and getting the record (date of birth along with all the fiels of AdmAppln)
				 */
				List<AdmissionStatusTO> AdmAppln = GenerateProcessHandler.getInstance().getDetailsAdmAppln(applicationNo);
				/**
				 * Checks If user enters wrong application no. then add appropriate error message 
				 */
				if (AdmAppln.isEmpty()) {
					errors.add(CMSConstants.ERROR,new ActionError(CMSConstants.ADMISSION_ADMISSIONSTATUS_APPNO_INVALID));
					saveErrors(request, errors);
					generateProcessForm.clearadmissionStatusTO();
					return mapping.findForward(CMSConstants.ADMISSION_APPLICATIONSTATUS);
				}
				/**
				 * Checks if multiple records exists in DB based on the entered application no. from UI then add the error message
				 */
				if(AdmAppln.size()>1){
					errors.add(CMSConstants.ERROR,new ActionError(CMSConstants.ADMISSION_ADMISSIONSTATUS_INVALID_DATA));
					saveErrors(request, errors);
					generateProcessForm.clear();
					generateProcessForm.clearadmissionStatusTO();
					return mapping.findForward(CMSConstants.ADMISSION_APPLICATIONSTATUS);			
				}
				/**
				 * Iterating the list of data getting based on the application no. entered from UI 
				 */
				Iterator<AdmissionStatusTO> it=AdmAppln.iterator();
				while (it.hasNext()) {
					AdmissionStatusTO admissionStatusTO = (AdmissionStatusTO) it.next();
					generateProcessForm.setAdmissionStatusTO(admissionStatusTO);
					generateProcessForm.setAdmStatus(admissionStatusTO.getAdmStatus());
					if (admissionStatusTO.getDateOfBirth()==null) {
						errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_ADMISSIONSTATUS_NULL));
						saveErrors(request, errors);
						generateProcessForm.clear();
						generateProcessForm.clearadmissionStatusTO();
						return mapping.findForward(CMSConstants.ADMISSION_APPLICATIONSTATUS);
					}
					if(admissionStatusTO.getApplicationNo()!=0 && admissionStatusTO.getIsSelected()!=null && admissionStatusTO.getDateOfBirth() !=null)
					{     
				        String uiDob=generateProcessForm.getDateOfBirth();
				        String toDateofBirth=CommonUtil.ConvertStringToDateFormat(admissionStatusTO.getDateOfBirth(), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE);
						if (!uiDob.equals(toDateofBirth)) {
							errors.add(CMSConstants.ERROR,new ActionError(CMSConstants.ADMISSION_ADMISSIONSTATUS_INVALID_DOB));
							saveErrors(request, errors);
							generateProcessForm.clearadmissionStatusTO();
							return mapping.findForward(CMSConstants.ADMISSION_APPLICATIONSTATUS);
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("Error occured at getOnlineApplicationStatus of Admission StatusAction",e);
			String msg = super.handleApplicationException(e);
			generateProcessForm.setErrorMessage(msg);
			generateProcessForm.setErrorStack(e.getMessage());
		}
		saveErrors(request, errors);
		log.info("Leaving from getOnlineApplicationStatus of AdmissionStatusAction");
		return mapping.findForward(CMSConstants.ADMISSION_APPLICATIONSTATUS);
	}
	
	/**
	 * Method to check the entered date is not a future date
	 * @param dateString
	 * @return boolean value
	 */
	
	private boolean validatefutureDate(String dateString) throws Exception{
		log.info("Entering into validatefutureDate of AdmissionStatusAction");
		String formattedString=CommonUtil.ConvertStringToDateFormat(dateString, CMSConstants.DEST_DATE,DATE);
		Date date = new Date(formattedString);
		Date currentDate = new Date();
		if (date.compareTo(currentDate) == 1)
			return false;
		else
			log.info("Leaving into validatefutureDate of AdmissionStatusAction");
			return true;
	}	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param Initializes
	 *            Admission status
	 * @returns Admission Status with Application No & Date of birth as two
	 *          fields
	 * @throws Exception
	 */

	public ActionForward getData(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entered into initOutsideAccessStatus of AdmissionStatusAction");
		GenerateProcessForm generateProcessForm = (GenerateProcessForm) form;
		try {
			AdmissionStatusTO to=generateProcessForm.getAdmissionStatusTO();
			generateProcessForm.setTemplateDescription(to.getFinalTemplate());
			IGenerateProcessTransaction transaction=GenerateProcessTransactionImpl.getInstance();
			transaction.setPhoto(request,to.getId());
			request.setAttribute("templateDescription", to.getFinalTemplate());
		} catch (Exception e) {
			log.error("Error occured at initOutsideAccessStatus of Admission Status Action",e);
			String msg = super.handleApplicationException(e);
			generateProcessForm.setErrorMessage(msg);
			generateProcessForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Leaving into initAdmissionStatus of AdmissionStatusAction");
		return mapping.findForward("admitCardPrint1");
	}
	
}
