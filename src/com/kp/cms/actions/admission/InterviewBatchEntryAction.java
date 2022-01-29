package com.kp.cms.actions.admission;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.kp.cms.forms.admission.InterviewBatchEntryForm;
import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.handlers.admission.InterviewBatchEntryHandler;
import com.kp.cms.handlers.admission.InterviewResultEntryHandler;
import com.kp.cms.to.admission.InterviewResultTO;
import com.kp.cms.utilities.CommonUtil;

/**
 * Action class for Interview Batch Result Entry
 */
@SuppressWarnings("deprecation")
public class InterviewBatchEntryAction extends BaseDispatchAction {
	
	private static final Log log = LogFactory.getLog(InterviewBatchEntryAction.class);
	
	/**
	 * Method to set the required data to the form to display it in interviewBatchInput.jsp
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initStudentSearch(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered Interview Batch Result input");
		InterviewBatchEntryForm interviewBatchEntryForm = (InterviewBatchEntryForm) form;
		interviewBatchEntryForm.resetFields();
		setRequiredDatatoForm(interviewBatchEntryForm);
		log.info("Exit Interview Batch Result input");
		
		return mapping.findForward(CMSConstants.INTERVIEW_BATCH_INPUT);
	}
	
	/**
	 * Method to set all active Program Types to the form
	 * @param interviewBatchEntryForm
	 * @throws Exception
	 */
	private void setRequiredDatatoForm(InterviewBatchEntryForm interviewBatchEntryForm) throws Exception {
		if(ProgramTypeHandler.getInstance().getProgramType() != null){
			interviewBatchEntryForm.setProgramTypeList(ProgramTypeHandler.getInstance().getProgramType());
		}
	}
	
	/**
	 * Method to select the candidates for batch result entry based on the input selected
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getSelectedCandidates(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered Interview Batch Result - getSelectedCandidates");
		
		InterviewBatchEntryForm interviewBatchEntryForm = (InterviewBatchEntryForm) form;
		 ActionErrors errors = interviewBatchEntryForm.validate(mapping, request);
		validateTime(interviewBatchEntryForm, errors);
		
		if (errors.isEmpty()) {
			if(interviewBatchEntryForm.getSubroundCount() != null &&  !interviewBatchEntryForm.getSubroundCount().isEmpty() && Integer.parseInt(interviewBatchEntryForm.getSubroundCount()) != 0){
				if(interviewBatchEntryForm.getInterviewSubroundId() !=null && interviewBatchEntryForm.getInterviewSubroundId().length()== 0) {
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_INTERVIEWSUBROUND_REQUIRED));
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.INTERVIEW_BATCH_INPUT);
				}	
			}
			try {
				int mainroundId = 0;
				int subroundId = 0;
				int interviewersPerPanel = 1;
				if( interviewBatchEntryForm.getInterviewSubroundId() != null && !interviewBatchEntryForm.getInterviewSubroundId().trim().isEmpty()){
					subroundId = Integer.parseInt(interviewBatchEntryForm.getInterviewSubroundId());
				}else if( interviewBatchEntryForm.getInterviewTypeId() != null && !interviewBatchEntryForm.getInterviewTypeId().trim().isEmpty()){
					mainroundId = Integer.parseInt(interviewBatchEntryForm.getInterviewTypeId());
				} 
				
				interviewersPerPanel = InterviewResultEntryHandler.getInstance().getInterviewersPerPanel(mainroundId, subroundId);
				interviewBatchEntryForm.setInterviewersPerPanel(interviewersPerPanel);
				
				List<InterviewResultTO> selectedCandidates = InterviewBatchEntryHandler.getInstance().getAddIntResultCandidates(interviewBatchEntryForm);
				
				if (selectedCandidates.isEmpty()) {
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND));
					saveErrors(request, errors);
					log.info("Exit Interview Batch Result - getSelectedCandidates size 0");
					return mapping.findForward(CMSConstants.INTERVIEW_BATCH_INPUT);
				} else {
					//save the selected candidates list to form
					interviewBatchEntryForm.setSelectedCandidates(selectedCandidates);
					
					//save the interview status to form
					Map<Integer, String> interviewStatus = InterviewResultEntryHandler.getInstance().getInterviewStatus();
					interviewBatchEntryForm.setInterviewStatus(interviewStatus);
					
					//save the grades to form
					Map<Integer, String> grades = InterviewResultEntryHandler.getInstance().getGrades();
					interviewBatchEntryForm.setGrades(grades);
				}
			}  catch (Exception exception) {	
				String msg = super.handleApplicationException(exception);
				interviewBatchEntryForm.setErrorMessage(msg);
				interviewBatchEntryForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			setRequiredDatatoForm(interviewBatchEntryForm);
			log.info("Exit Interview Batch Result - getSelectedCandidates errors not empty ");
			return mapping.findForward(CMSConstants.INTERVIEW_BATCH_INPUT);
		}
		log.info("Exit Interview Batch Result - getSelectedCandidates");
		return mapping.findForward(CMSConstants.INTERVIEW_BATCH_UPDATE);
	}
	
	/**
	 * Method to batch update the result of the selected candidates
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward interviewResultBatchUpdate(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entered Interview Batch Result - Batch Update");
		
		InterviewBatchEntryForm interviewBatchEntryForm = (InterviewBatchEntryForm) form;
		boolean batchUpdate = false;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors=new ActionErrors();
		
		// validating length
		List<InterviewResultTO> list = interviewBatchEntryForm.getSelectedCandidates();
		Iterator<InterviewResultTO> itr = list.iterator();	
		//Set<Integer> ids = new HashSet<Integer>();
		InterviewResultTO interviewResultTO;
		String comments="";
		boolean condition=true;
		while(itr.hasNext()) {
			interviewResultTO = itr.next();
			if(interviewResultTO.getComments().length() > 0 && interviewResultTO.getComments()!=null){
				comments=interviewResultTO.getComments();

				if (comments.length() > 100) {
					interviewResultTO.setComments("");
					if(condition){
						errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_INTERVIEW_COMMENTS));
						saveErrors(request, errors);	
						condition=false;
					}
					
					
				}

					}
		}
	
		if(errors.isEmpty())
		{
		try{
			setUserId(request, interviewBatchEntryForm);
			batchUpdate = InterviewBatchEntryHandler.getInstance().resultBatchUpdate(interviewBatchEntryForm);
			
			if(batchUpdate){
				ActionMessage message = new ActionMessage(CMSConstants.KNOWLEDGEPRO_ADMISSION_BATCHRESULTADDED);
				messages.add(CMSConstants.MESSAGES, message);
				saveMessages(request, messages);
				interviewBatchEntryForm.resetFields();	
			}
		
		} catch (BusinessException businessException) {
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {	
			String msg = super.handleApplicationException(exception);
			interviewBatchEntryForm.setErrorMessage(msg);
			interviewBatchEntryForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Exit Interview Batch Result - Batch Update");
		}
		else
		{
			saveErrors(request,errors);
			return mapping.findForward(CMSConstants.INTERVIEW_BATCH_UPDATE);
		}
		return mapping.findForward(CMSConstants.INTERVIEW_BATCH_INPUT);
		
	}
	
	/**
	 * Method to compare the entered date with future date
	 */
	private boolean validatefutureDate(String dateString) {
		String formattedString=CommonUtil.ConvertStringToDateFormat(dateString, "dd/MM/yyyy","MM/dd/yyyy");
		Date date = new Date(formattedString);
		Date curdate = new Date();
		if (date.compareTo(curdate) == 1){
			return false;
		}else{
			return true;
		}	
	}
	
	/**
	 * Method to validate the time format
	 * @param interviewBatchEntryForm
	 * @param errors
	 */
	private void validateTime(InterviewBatchEntryForm interviewBatchEntryForm, ActionErrors errors) {
		
		if (interviewBatchEntryForm.getInterviewDate() != null && !StringUtils.isEmpty(interviewBatchEntryForm.getInterviewDate())) {
			if (CommonUtil.isValidDate(interviewBatchEntryForm.getInterviewDate())) {
				boolean isValid = validatefutureDate(interviewBatchEntryForm.getInterviewDate());
				if (!isValid) {
					if (errors.get(CMSConstants.KNOWLEDGEPRO_ADMISSION_IOD_FUTUREDATE) != null && !errors.get(CMSConstants.KNOWLEDGEPRO_ADMISSION_IOD_FUTUREDATE).hasNext()) {
						errors.add(CMSConstants.KNOWLEDGEPRO_ADMISSION_IOD_FUTUREDATE, new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_IOD_FUTUREDATE));
					}
				}
			} else {
				if (errors.get(CMSConstants.KNOWLEDGEPRO_ADMISSION_IOD_INVALID) != null && !errors.get(CMSConstants.KNOWLEDGEPRO_ADMISSION_IOD_INVALID).hasNext()) {
					errors.add(CMSConstants.KNOWLEDGEPRO_ADMISSION_IOD_INVALID, new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_IOD_INVALID));
				}
			}
		}
		
		if(CommonUtil.checkForEmpty(interviewBatchEntryForm.getStartingTimeHours())){
			if(!StringUtils.isNumeric(interviewBatchEntryForm.getStartingTimeHours())){
				if(errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC).hasNext()){									
					errors.add(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC,new ActionError(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC));
				}
			}			
		}
		if(CommonUtil.checkForEmpty(interviewBatchEntryForm.getStartingTimeMins())){
			if(!StringUtils.isNumeric(interviewBatchEntryForm.getStartingTimeMins())){
				if(errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC).hasNext()){									
					errors.add(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC,new ActionError(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC));
				}
			}			
		}
		
		if(CommonUtil.checkForEmpty(interviewBatchEntryForm.getStartingTimeHours())){
			if(Integer.parseInt(interviewBatchEntryForm.getStartingTimeHours())>=24){
				if(errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT).hasNext()){									
					errors.add(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT,new ActionError(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT));
				}
			}			
		}
		if(CommonUtil.checkForEmpty(interviewBatchEntryForm.getStartingTimeMins())){
			if(Integer.parseInt(interviewBatchEntryForm.getStartingTimeMins())>=60){
				if(errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT).hasNext()){									
					errors.add(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT,new ActionError(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT));
				}
			}			
		}
		if(CommonUtil.checkForEmpty(interviewBatchEntryForm.getEndingTimeHours())){
			if(!StringUtils.isNumeric(interviewBatchEntryForm.getEndingTimeHours())){
				if(errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC).hasNext()){									
					errors.add(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC,new ActionError(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC));
				}
			}			
		}
		if(CommonUtil.checkForEmpty(interviewBatchEntryForm.getEndingTimeMins())){
			if(!StringUtils.isNumeric(interviewBatchEntryForm.getEndingTimeMins())){
				if(errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC).hasNext()){									
					errors.add(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC,new ActionError(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC));
				}
			}			
		}
		
		if(CommonUtil.checkForEmpty(interviewBatchEntryForm.getEndingTimeHours())){
			if(Integer.parseInt(interviewBatchEntryForm.getEndingTimeHours())>=24){
				if(errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT).hasNext()){									
					errors.add(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT,new ActionError(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT));
				}
			}			
		}
		if(CommonUtil.checkForEmpty(interviewBatchEntryForm.getEndingTimeMins())){
			if(Integer.parseInt(interviewBatchEntryForm.getEndingTimeMins())>=60){
				if(errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT).hasNext()){									
					errors.add(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT,new ActionError(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT));
				}
			}			
		}
	}
}