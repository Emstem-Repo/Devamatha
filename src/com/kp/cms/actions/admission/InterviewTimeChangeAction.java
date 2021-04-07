package com.kp.cms.actions.admission;

import java.util.ArrayList;
import java.util.Calendar;
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
import org.apache.commons.validator.routines.TimeValidator;
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
import com.kp.cms.forms.admission.InterviewTimeChangeForm;
import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.handlers.admission.InterviewTimeChangeHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.to.admission.InterviewTimeChangeTO;
import com.kp.cms.utilities.CommonUtil;


/**
 * @author kalyan.c
 * This class is implemented for edit interview time
 */
@SuppressWarnings("deprecation")
public class InterviewTimeChangeAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(InterviewTimeChangeAction.class);
	
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * This method is used to display interview edit time search page
	 */
	public ActionForward initInterviewSearch(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered Interview input");
		InterviewTimeChangeForm interviewTimeChangeForm = (InterviewTimeChangeForm) form;
		//It use for Help,Don't Remove
		HttpSession session=request.getSession();
		session.setAttribute("field","edit_selection_process_schedule");
		
		interviewTimeChangeForm.resetFields();
		setRequiredDatatoForm(interviewTimeChangeForm,request);
		log.info("Exit Interview input");
		
		return mapping.findForward("initInterviewSearch");
	}
	
	/**
	 * @param interviewTimeChangeForm
	 * @throws Exception
	 * This method is used to get program type and set it to the form
	 */
	private void setRequiredDatatoForm(InterviewTimeChangeForm interviewTimeChangeForm,HttpServletRequest request) throws Exception {
		//set Program Type to the form
		if(ProgramTypeHandler.getInstance().getProgramType() != null){
			interviewTimeChangeForm.setProgramTypeList(ProgramTypeHandler.getInstance().getProgramType());
		}
		if(interviewTimeChangeForm.getProgramId()!=null && !interviewTimeChangeForm.getProgramId().isEmpty())
		{
			Map<Integer, String>coursesMap=CommonAjaxHandler.getInstance().getCourseByProgram(Integer.parseInt(interviewTimeChangeForm.getProgramId()));
			request.setAttribute("coursesMap", coursesMap);
			Map<Integer, String>interviewMap=CommonAjaxHandler.getInstance().getInterviewTypeByProgram(Integer.parseInt(interviewTimeChangeForm.getProgramId()), Integer.parseInt(interviewTimeChangeForm.getAppliedYear()));
			request.setAttribute("interviewMap",interviewMap);
		}
		if(interviewTimeChangeForm.getCourseId()!=null && !interviewTimeChangeForm.getCourseId().isEmpty())
		{
			Map<Integer, String>interviewMap=CommonAjaxHandler.getInstance().getInterviewTypeByCourse(Integer.parseInt(interviewTimeChangeForm.getCourseId()), Integer.parseInt(interviewTimeChangeForm.getAppliedYear()));
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
	public ActionForward getSelectedInterCandidates(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered Interview Result - getSelectedInterCandidates");
		
		InterviewTimeChangeForm interviewTimeChangeForm = (InterviewTimeChangeForm) form;
		 ActionErrors errors = interviewTimeChangeForm.validate(mapping, request);
		validateTime(interviewTimeChangeForm, errors);
		if (errors.isEmpty()) {
			try {
//				List<InterviewTimeChangeTO> selectedCandidatesList1 =  new ArrayList<InterviewTimeChangeTO>();
				 List<InterviewTimeChangeTO> selectedCandidatesList = InterviewTimeChangeHandler.getInstance().getAddIntResultCandidates(interviewTimeChangeForm);
//				selectedCandidatesList1 = InterviewTimeChangeHandler.getInstance().getAddIntResultCandidates(interviewTimeChangeForm);
				if (selectedCandidatesList.isEmpty()) {
					errors.add("error", new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND));
					saveErrors(request, errors);
					log.info("Exit Interview  Result - getSelectedInterCandidates size 0");
					return mapping.findForward("initInterviewSearch");
				} else {
					//save the selected candidates list to form
					interviewTimeChangeForm.setSelectedCandidates(selectedCandidatesList);
//					List tempList = new ArrayList();
//					tempList.addAll(selectedCandidatesList);
					interviewTimeChangeForm.setOriginalCandidates(selectedCandidatesList);
					
				}
			}catch(ApplicationException ae){
				String msg = super.handleApplicationException(ae);
				interviewTimeChangeForm.setErrorMessage(msg);
				interviewTimeChangeForm.setErrorStack(ae.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
			catch (Exception e) {
				log.error("Error Interview Result - getSelectedInterCandidates");
					throw e;
			}
		} else {
			addErrors(request, errors);
			setRequiredDatatoForm(interviewTimeChangeForm,request);
			log.info("Exit Interview Result - getSelectedInterCandidates errors not empty ");
			return mapping.findForward("initInterviewSearch");
		}
		log.info("Exit Interview Result - getSelectedInterCandidates");
		return mapping.findForward("getSelectedInterCandidates");
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * This method will update the time
	 */
	public ActionForward interviewTimeUpdate(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entered Interview Time Update");
		
 		InterviewTimeChangeForm interviewTimeChangeForm = (InterviewTimeChangeForm) form;
		boolean batchUpdate = false;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		/*List originalStudentList = null;
		originalStudentList = interviewTimeChangeForm.getOriginalCandidates();*/
		validateCandidateTime(interviewTimeChangeForm,errors);
		if (errors.isEmpty()) {
		try{
			setUserId(request, interviewTimeChangeForm);
			batchUpdate = InterviewTimeChangeHandler.getInstance().resultBatchUpdate1(interviewTimeChangeForm,request);
			if(batchUpdate){
				ActionMessage message = new ActionMessage(CMSConstants.KNOWLEDGEPRO_ADMISSION_INTERVIEWADDED);
				messages.add("messages", message);
				saveMessages(request, messages);
//				InterviewTimeChangeHelper.sendMail(originalStudentList,interviewTimeChangeForm.getSelectedCandidates());
				 List<InterviewTimeChangeTO> selectedCandidatesList = InterviewTimeChangeHandler.getInstance().getAddIntResultCandidates(interviewTimeChangeForm);
				interviewTimeChangeForm.setOriginalCandidates(selectedCandidatesList);
				interviewTimeChangeForm.setSelectedCandidates(selectedCandidatesList);
//				interviewTimeChangeForm.resetFields();	
			}
		}catch(BusinessException be){
			String msgKey = super.handleBusinessException(be);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add("messages", message);
			return mapping.findForward("getSelectedInterCandidates");
		}catch(ApplicationException ae){
			String msg = super.handleApplicationException(ae);
			interviewTimeChangeForm.setErrorMessage(msg);
			interviewTimeChangeForm.setErrorStack(ae.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		catch (Exception e) {
			log.error("Error Interview Time Update", e);
				throw e;
		}
		} else {
			addErrors(request, errors);
			return mapping.findForward("getSelectedInterCandidates");
		}
		log.info("Exit Interview Time Update");
		return mapping.findForward("getSelectedInterCandidates");
	}

	/**
	 * @param interviewTimeChangeForm
	 * @param errors
	 * This method will validate the time
	 */
	private void validateCandidateTime(InterviewTimeChangeForm interviewTimeChangeForm,
			ActionErrors errors){
		boolean flage = false;
		String date;
		List<InterviewTimeChangeTO> interviewTimeChangeTOList = interviewTimeChangeForm.getSelectedCandidates();
		if (interviewTimeChangeTOList != null) {
			Iterator<InterviewTimeChangeTO> iteratITCTO = interviewTimeChangeTOList.iterator();

			while (iteratITCTO.hasNext()) {
				InterviewTimeChangeTO interviewTimeChangeTO = iteratITCTO.next();
				TimeValidator validateTime = TimeValidator.getInstance();
				flage = validateTime.isValid(interviewTimeChangeTO.getInterviewTime(), "HH:mm:ss");
				if(!flage){
					if (errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMEFORMAT) != null && !errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMEFORMAT).hasNext()) {
						errors.add(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMEFORMAT, new ActionError(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMEFORMAT));
					}
				}
				if(interviewTimeChangeTO.getInterviewDate()==null || interviewTimeChangeTO.getInterviewDate().isEmpty()){
					if (errors.get("knowledgePro.admission.edit.interview.date.required") != null && !errors.get("knowledgePro.admission.edit.interview.date.required").hasNext()) {
						errors.add("knowledgePro.admission.edit.interview.date.required", new ActionError("knowledgePro.admission.edit.interview.date.required"));
					}
				}else if(interviewTimeChangeTO.getInterviewDate()!=null){
				if(!interviewTimeChangeTO.getInterviewDate().equals(interviewTimeChangeTO.getOldInterviewDate())){	
				date=interviewTimeChangeTO.getInterviewDate();
				if(date!=null && !StringUtils.isEmpty(date)&& !CommonUtil.isValidDate(date)){
					if (errors.get(CMSConstants.KNOWLEDGEPRO_ADMISSION_IOD_INVALID) != null && !errors.get(CMSConstants.KNOWLEDGEPRO_ADMISSION_IOD_INVALID).hasNext()) {
						errors.add(CMSConstants.KNOWLEDGEPRO_ADMISSION_IOD_INVALID, new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_IOD_INVALID));
					}
				}
				if(CommonUtil.isValidDate(date)){
					boolean isValid = validatePastDate(date);
				if (!isValid) {
					if (errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_PASTDATE) != null && !errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_PASTDATE).hasNext()) {
						errors.add(CMSConstants.KNOWLEDGEPRO_INTERVIEW_PASTDATE, new ActionError(CMSConstants.KNOWLEDGEPRO_INTERVIEW_PASTDATE));
					}
				}
				}
				}
				}
			}
		}	
	}	
	/**
	 * Method to check the entered date is not a future date
	 * @param dateString
	 * @return boolean value
	 */
	private boolean validatePastDate(String dateString) {
		String formattedString=CommonUtil.ConvertStringToDateFormat(dateString, "dd/MM/yyyy","MM/dd/yyyy");
		Date date = new Date(formattedString);
		Date curdate = new Date();
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(curdate);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date);
		if (CommonUtil.getDaysBetweenDates(cal1, cal2) <= 0){
			return false;
		}
		else{
			return true;
		}	
	}
	
	/**
	 * @param interviewTimeChangeForm
	 * @param errors
	 * This method will validate the time
	 */
	private void validateTime(InterviewTimeChangeForm interviewTimeChangeForm,
			ActionErrors errors) {
		
		if (interviewTimeChangeForm.getInterviewDate() != null && !StringUtils.isEmpty(interviewTimeChangeForm.getInterviewDate())) {
			if (!CommonUtil.isValidDate(interviewTimeChangeForm.getInterviewDate())) {
				if (errors.get(CMSConstants.KNOWLEDGEPRO_ADMISSION_IOD_INVALID) != null && !errors.get(CMSConstants.KNOWLEDGEPRO_ADMISSION_IOD_INVALID).hasNext()) {
					errors.add(CMSConstants.KNOWLEDGEPRO_ADMISSION_IOD_INVALID, new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_IOD_INVALID));
				}
			} 
		}
		
		if(CommonUtil.checkForEmpty(interviewTimeChangeForm.getStartingTimeHours())){
			if(!StringUtils.isNumeric(interviewTimeChangeForm.getStartingTimeHours())){
				if(errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC).hasNext()){									
					errors.add(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC,new ActionError(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC));
				}
			}			
		}
		if(CommonUtil.checkForEmpty(interviewTimeChangeForm.getStartingTimeMins())){
			if(!StringUtils.isNumeric(interviewTimeChangeForm.getStartingTimeMins())){
				if(errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC).hasNext()){									
					errors.add(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC,new ActionError(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC));
				}
			}			
		}
		
		if(CommonUtil.checkForEmpty(interviewTimeChangeForm.getStartingTimeHours())){
			if(Integer.parseInt(interviewTimeChangeForm.getStartingTimeHours())>=24){
				if(errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT).hasNext()){									
					errors.add(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT,new ActionError(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT));
				}
			}			
		}
		if(CommonUtil.checkForEmpty(interviewTimeChangeForm.getStartingTimeMins())){
			if(Integer.parseInt(interviewTimeChangeForm.getStartingTimeMins())>=60){
				if(errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT).hasNext()){									
					errors.add(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT,new ActionError(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT));
				}
			}			
		}
		if(CommonUtil.checkForEmpty(interviewTimeChangeForm.getEndingTimeHours())){
			if(!StringUtils.isNumeric(interviewTimeChangeForm.getEndingTimeHours())){
				if(errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC).hasNext()){									
					errors.add(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC,new ActionError(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC));
				}
			}			
		}
		if(CommonUtil.checkForEmpty(interviewTimeChangeForm.getEndingTimeMins())){
			if(!StringUtils.isNumeric(interviewTimeChangeForm.getEndingTimeMins())){
				if(errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC).hasNext()){									
					errors.add(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC,new ActionError(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC));
				}
			}			
		}
		
		if(CommonUtil.checkForEmpty(interviewTimeChangeForm.getEndingTimeHours())){
			if(Integer.parseInt(interviewTimeChangeForm.getEndingTimeHours())>=24){
				if(errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT).hasNext()){									
					errors.add(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT,new ActionError(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT));
				}
			}			
		}
		if(CommonUtil.checkForEmpty(interviewTimeChangeForm.getEndingTimeMins())){
			if(Integer.parseInt(interviewTimeChangeForm.getEndingTimeMins())>=60){
				if(errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT).hasNext()){									
					errors.add(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT,new ActionError(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT));
				}
			}			
		}
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getRescheduledInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entered Interview input");
		InterviewTimeChangeForm interviewTimeChangeForm = (InterviewTimeChangeForm) form;
		try{
			InterviewTimeChangeHandler.getInstance().getStudentRescheduledInfo(interviewTimeChangeForm);
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			interviewTimeChangeForm.setErrorMessage(msg);
			interviewTimeChangeForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Exit Interview input");
		return mapping.findForward("viewRescheduledInfo");
	}
	
}
