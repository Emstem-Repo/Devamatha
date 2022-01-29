package com.kp.cms.actions.admission;

import java.util.Calendar;
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
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.admission.CopyInterviewDefinitionForm;
import com.kp.cms.forms.admission.InterviewSubroundsForm;
import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.handlers.admission.CopyInterviewDefinitionHandler;
import com.kp.cms.handlers.admission.InterviewSubroundsHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.to.admission.InterviewSubroundsTO;
import com.kp.cms.utilities.CurrentAcademicYear;

/**
 * Action class for Interview Subrounds Definition
 */
@SuppressWarnings("deprecation")
public class InterviewSubroundsAction extends BaseDispatchAction {
	
	private static final Log log = LogFactory.getLog(InterviewSubroundsAction.class);
	
	/**
	 * Method to set the required data to the form to display in interviewSubrounds.jsp
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getInterviewSubrounds(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		log.info("Entered getInterviewSubrounds Action");
		InterviewSubroundsForm interviewSubroundsForm = (InterviewSubroundsForm) form;
		
		interviewSubroundsForm.clear();
		setProgramTypeDatatoForm(interviewSubroundsForm);
		getInterviewSubroundsList(interviewSubroundsForm);
		setUserId(request, interviewSubroundsForm);
		
		log.info("Exit getInterviewSubrounds Action");	
		return mapping.findForward(CMSConstants.KNOWLEDGEPRO_INTERVIEWSUBROUND);
	}
	
	/**
	 * Method to set all active Program Types to the form
	 * @param interviewSubroundsForm
	 * @throws Exception
	 */
	private void setProgramTypeDatatoForm(InterviewSubroundsForm interviewSubroundsForm) throws Exception {
		//set Program Type to the form
		if(ProgramTypeHandler.getInstance().getProgramType() != null){
			interviewSubroundsForm.setProgramTypeList(ProgramTypeHandler.getInstance().getProgramType());
		}
	}
	
	/**
	 * Method to set all active Interview Subrounds to the form
	 * @param interviewSubroundsForm
	 * @throws Exception
	 */
	public void getInterviewSubroundsList(InterviewSubroundsForm interviewSubroundsForm) throws Exception{
//		List<InterviewSubroundsTO> interviewSubroundsList = InterviewSubroundsHandler.getInstance().getInterviewSubrounds(0);
		
		Calendar calendar=Calendar.getInstance();
		int currentYear = calendar.get(Calendar.YEAR);
		int year=CurrentAcademicYear.getInstance().getCurrentAcademicyear();
		if(year!=0){
			currentYear = year;
		}
		List<InterviewSubroundsTO> interviewSubroundsList = InterviewSubroundsHandler.getInstance().getInterviewSubroundsList(currentYear);
		if( interviewSubroundsList != null ){
			interviewSubroundsForm.setInterviewSubroundsList(interviewSubroundsList);
		}
	}
	
	/**
	 * Method to add Interview Definition Subround
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addInterviewSubround(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		log.info("Entered addInterviewSubround Action");
		InterviewSubroundsForm interviewSubroundsForm = (InterviewSubroundsForm) form;
		
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = interviewSubroundsForm.validate(mapping, request);
		validateInterviewersPerPanel(interviewSubroundsForm, errors);
		boolean isInterviewSubroundAdded = false;
		
		try{
			if (!errors.isEmpty()) {
				log.info("Entered addInterviewSubround Action errors size 0");
				saveErrors(request, errors);
				getInterviewSubroundsList(interviewSubroundsForm);
				return mapping.findForward(CMSConstants.KNOWLEDGEPRO_INTERVIEWSUBROUND);
			}
			
			isInterviewSubroundAdded = InterviewSubroundsHandler.getInstance().addInterviewSubround(interviewSubroundsForm, "Add");
		
			if (isInterviewSubroundAdded) {
				getInterviewSubroundsList(interviewSubroundsForm);
				
				ActionMessage message = new ActionMessage(CMSConstants.KNOWLEDGEPRO_INTERVIEWSUBROUND_ADDSUCCESS);
				messages.add(CMSConstants.MESSAGES, message);
				saveMessages(request, messages);
				interviewSubroundsForm.clear();
			} else {
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_INTERVIEWSUBROUND_ADDFAILURE));
				saveErrors(request, errors);
			}
		} catch (DuplicateException duplicateException) {
			log.info("Entered addInterviewDefinition Action duplicate exception");
			errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_INTERVIEWSUBROUND_ADDEXIST));
			saveErrors(request, errors);
			getInterviewSubroundsList(interviewSubroundsForm);
			return mapping.findForward(CMSConstants.KNOWLEDGEPRO_INTERVIEWSUBROUND);
		} catch (ReActivateException reActivateException) {
			log.info("Entered addInterviewDefinition Action reactivate exception");
			errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_INTERVIEWSUBROUND_REACTIVATE));
			saveErrors(request, errors);
			getInterviewSubroundsList(interviewSubroundsForm);
			return mapping.findForward(CMSConstants.KNOWLEDGEPRO_INTERVIEWSUBROUND);
		} catch (BusinessException businessException) {
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {	
			String msg = super.handleApplicationException(exception);
			interviewSubroundsForm.setErrorMessage(msg);
			interviewSubroundsForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}	
		return mapping.findForward(CMSConstants.KNOWLEDGEPRO_INTERVIEWSUBROUND);
	}
	
	/**
	 * Method to update Interview Definition Subround
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateInterviewSubround(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		log.info("Entered updateInterviewSubround Action");
		InterviewSubroundsForm interviewSubroundsForm = (InterviewSubroundsForm) form;
		
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = interviewSubroundsForm.validate(mapping, request);
		validateInterviewersPerPanel(interviewSubroundsForm, errors);
		boolean isInterviewSubroundEdited = false;

		try {
			if (isCancelled(request)) {
				setRequiredDataToForm(interviewSubroundsForm, request);
				getInterviewSubroundsList(interviewSubroundsForm);
				request.setAttribute(CMSConstants.CONDITIONS_OPERATION, CMSConstants.EDIT_OPERATION);
				setprogramMapToRequest(request, interviewSubroundsForm);
				setpCourseMapToRequest(request, interviewSubroundsForm);
				setInterviewTypeMapToRequest(request, interviewSubroundsForm);
				return mapping.findForward(CMSConstants.KNOWLEDGEPRO_INTERVIEWSUBROUND);
			}
			
			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				getInterviewSubroundsList(interviewSubroundsForm);
				request.setAttribute(CMSConstants.CONDITIONS_OPERATION, CMSConstants.EDIT_OPERATION);
				setprogramMapToRequest(request, interviewSubroundsForm);
				setpCourseMapToRequest(request, interviewSubroundsForm);
				setInterviewTypeMapToRequest(request, interviewSubroundsForm);
				return mapping.findForward(CMSConstants.KNOWLEDGEPRO_INTERVIEWSUBROUND);
			} 
			
			isInterviewSubroundEdited = InterviewSubroundsHandler.getInstance().addInterviewSubround(interviewSubroundsForm, "Edit");
			
			if (isInterviewSubroundEdited) {
				getInterviewSubroundsList(interviewSubroundsForm);
				
				ActionMessage message = new ActionMessage(CMSConstants.KNOWLEDGEPRO_INTERVIEWSUBROUND_EDITSUCCESS);
				messages.add(CMSConstants.MESSAGES, message);
				saveMessages(request, messages);
				interviewSubroundsForm.clear();
			} else {
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_INTERVIEWSUBROUND_EDITFAILURE));
				saveErrors(request, errors);
			}
		}catch (DuplicateException duplicateException) {
			log.info("Entered updateInterviewSubround Action duplicate exception");
			errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_INTERVIEWSUBROUND_ADDEXIST,
					interviewSubroundsForm.getInterviewTypeId()));
			saveErrors(request, errors);
			getInterviewSubroundsList(interviewSubroundsForm);
			request.setAttribute(CMSConstants.CONDITIONS_OPERATION, CMSConstants.EDIT_OPERATION);
			return mapping.findForward(CMSConstants.KNOWLEDGEPRO_INTERVIEWSUBROUND);
		} catch (ReActivateException reActivateException) {
			log.info("Entered updateInterviewSubround Action reactivate exception");
			errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_INTERVIEWSUBROUND_REACTIVATE));
			saveErrors(request, errors);
			getInterviewSubroundsList(interviewSubroundsForm);
			request.setAttribute(CMSConstants.CONDITIONS_OPERATION, CMSConstants.EDIT_OPERATION);
			return mapping.findForward(CMSConstants.KNOWLEDGEPRO_INTERVIEWSUBROUND);
		}catch (BusinessException businessException) {
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {	
			String msg = super.handleApplicationException(exception);
			interviewSubroundsForm.setErrorMessage(msg);
			interviewSubroundsForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}	
		return mapping.findForward(CMSConstants.KNOWLEDGEPRO_INTERVIEWSUBROUND);
	}
	
	/**
	 * Method to set data to the form when the edit button is clicked
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editInterviewSubround(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		InterviewSubroundsForm interviewSubroundsForm = (InterviewSubroundsForm) form;
		
		setRequiredDataToForm(interviewSubroundsForm, request);
		request.setAttribute(CMSConstants.CONDITIONS_OPERATION, CMSConstants.EDIT_OPERATION);

		return mapping.findForward(CMSConstants.KNOWLEDGEPRO_INTERVIEWSUBROUND);
	}
	
	/**
	 * Method to change the selected interview definition subround to inactive
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteInterviewSubround(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		log.info("Entered deleteInterviewSubround Action");
		InterviewSubroundsForm interviewSubroundsForm = (InterviewSubroundsForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		boolean isDeleted = false;
		try {
			if (interviewSubroundsForm.getId() != 0) {
				int subroundId = interviewSubroundsForm.getId();
				String uid = interviewSubroundsForm.getUserId();
				 
				isDeleted = InterviewSubroundsHandler.getInstance().deleteInterviewSubround(subroundId, false, uid);
			}
			
			if (isDeleted) {
				getInterviewSubroundsList(interviewSubroundsForm);

				ActionMessage message = new ActionMessage(CMSConstants.KNOWLEDGEPRO_INTERVIEWSUBROUND_DELETESUCCESS, interviewSubroundsForm.getId());
				messages.add(CMSConstants.MESSAGES, message);
				saveMessages(request, messages);
				interviewSubroundsForm.clear();
			} else {
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_INTERVIEWSUBROUND_DELETEFAILURE, interviewSubroundsForm.getId()));
				saveErrors(request, errors);
			}
		} catch (BusinessException businessException) {
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {	
			String msg = super.handleApplicationException(exception);
			interviewSubroundsForm.setErrorMessage(msg);
			interviewSubroundsForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}				
		return mapping.findForward(CMSConstants.KNOWLEDGEPRO_INTERVIEWSUBROUND);
	}
	
	/**
	 * Method to populate the selected information to the form
	 * @param interviewSubroundsForm
	 * @param request
	 * @throws Exception
	 */
	public void setRequiredDataToForm(InterviewSubroundsForm interviewSubroundsForm,
			HttpServletRequest request) throws Exception {
		int subroundId = Integer.parseInt(request.getParameter("id"));
		List<InterviewSubroundsTO> InterviewSubroundList = InterviewSubroundsHandler.getInstance().getInterviewSubrounds(subroundId);
		Iterator<InterviewSubroundsTO> iterator = InterviewSubroundList.iterator();
		InterviewSubroundsTO interviewSubroundsTO;

		while (iterator.hasNext()) {
			interviewSubroundsTO = (InterviewSubroundsTO) iterator.next();
			
			interviewSubroundsForm.setProgramTypeId(interviewSubroundsTO.getProgramTypeId());
			interviewSubroundsForm.setProgramId(interviewSubroundsTO.getProgramId());
			interviewSubroundsForm.setCourseId(interviewSubroundsTO.getCourseId());
			interviewSubroundsForm.setInterviewTypeId(interviewSubroundsTO.getInterviewTypeId());
			interviewSubroundsForm.setSubroundName(interviewSubroundsTO.getName());
			interviewSubroundsForm.setAppliedYear(String.valueOf(interviewSubroundsTO.getAppliedYear()));
			interviewSubroundsForm.setInterviewsPerPanel(interviewSubroundsTO.getInterviewProgramCourseTO().getInterviewsPerPanel());
			//setting to check the duplication
			interviewSubroundsForm.setOriginalInterviewTypeId(interviewSubroundsTO.getInterviewTypeId());
			interviewSubroundsForm.setOriginalSubroundName(interviewSubroundsTO.getName());
			interviewSubroundsForm.setProgramTypeId(Integer.toString(interviewSubroundsTO.getInterviewProgramCourseTO().getCourse().getProgramTo().getProgramTypeTo().getProgramTypeId()));
			interviewSubroundsForm.setProgramId(Integer.toString(interviewSubroundsTO.getInterviewProgramCourseTO().getCourse().getProgramTo().getId()));
			interviewSubroundsForm.setCourseId(Integer.toString(interviewSubroundsTO.getInterviewProgramCourseTO().getCourse().getId()));
		}
		
		setprogramMapToRequest(request, interviewSubroundsForm);
		setpCourseMapToRequest(request, interviewSubroundsForm);
		setInterviewTypeMapToRequest(request, interviewSubroundsForm);
		request.setAttribute(CMSConstants.CONDITIONS_OPERATION, CMSConstants.EDIT_OPERATION);
	}
	
	/**
	 * Method to reactivate selected interview definition
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward activateInterviewSubround(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		log.debug("Entering activateInterviewSubround");
		InterviewSubroundsForm interviewSubroundsForm = (InterviewSubroundsForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		boolean isActivated = false;
		try {
			if (interviewSubroundsForm.getActivationRefId() != 0) {
				int subroundId = interviewSubroundsForm.getActivationRefId();
				String uid = interviewSubroundsForm.getUserId();
				
				isActivated = InterviewSubroundsHandler.getInstance().deleteInterviewSubround(subroundId, true, uid);
			}
		} catch (Exception e) {
			errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_INTERVIEWSUBROUND_ACTIVATEFAILURE));
			saveErrors(request, errors);
		}
		getInterviewSubroundsList(interviewSubroundsForm);
		if (isActivated) {
			ActionMessage message = new ActionMessage(CMSConstants.KNOWLEDGEPRO_INTERVIEWSUBROUND_ACTIVATESUCCESS);
			messages.add(CMSConstants.MESSAGES, message);
			saveMessages(request, messages);
			interviewSubroundsForm.clear();
		}
		log.debug("exit activateInterviewDefinition");
		return mapping.findForward(CMSConstants.KNOWLEDGEPRO_INTERVIEWSUBROUND);
	}
	
	/**
	 * Method to set all active programs to the request object
	 * @param request
	 * @param interviewSubroundsForm
	 */
	public void setprogramMapToRequest(HttpServletRequest request,
			InterviewSubroundsForm interviewSubroundsForm) {
		if (interviewSubroundsForm.getProgramTypeId() != null && !interviewSubroundsForm.getProgramTypeId().trim().isEmpty()) {
			Map<Integer, String> programMap = CommonAjaxHandler.getInstance().getProgramsByProgramType(Integer.parseInt(interviewSubroundsForm.getProgramTypeId()));
			request.setAttribute("programMap", programMap);
		}
	}

	/**
	 * Method to set all active courses to the request object
	 * @param request
	 * @param interviewSubroundsForm
	 */
	public void setpCourseMapToRequest(HttpServletRequest request,
			InterviewSubroundsForm interviewSubroundsForm) {
		if (interviewSubroundsForm.getProgramId() != null && !interviewSubroundsForm.getProgramId().trim().isEmpty()) {
			Map<Integer, String> courseMap = CommonAjaxHandler.getInstance().getCourseByProgram(Integer.parseInt(interviewSubroundsForm.getProgramId()));
			request.setAttribute("courseMap", courseMap);
		}
	}
	
	/**
	 * Method to set all active interview types to the request object
	 * @param request
	 * @param interviewSubroundsForm
	 */
	public void setInterviewTypeMapToRequest(HttpServletRequest request,
			InterviewSubroundsForm interviewSubroundsForm) {
		if (interviewSubroundsForm.getCourseId() != null && !interviewSubroundsForm.getCourseId().trim().isEmpty()) {
			Map<Integer, String> interviewTypeMap = CommonAjaxHandler.getInstance().getInterviewTypeByCourse(Integer.parseInt(interviewSubroundsForm.getCourseId()), Integer.parseInt(interviewSubroundsForm.getAppliedYear()));
			request.setAttribute("interviewMap", interviewTypeMap);
		}
	}
	
	/**
	 * This is going to validate interviewers per panel
	 * @param interviewDefinitionForm
	 * @param errors
	 * @return
	 */
	
	private ActionErrors validateInterviewersPerPanel(InterviewSubroundsForm interviewSubroundsForm,
			ActionErrors errors) {
		log.info("entering into validateInterviewersPerPanel in InterviewSubRoundsAction class..");
		if (errors == null)
			errors = new ActionErrors();
		if(StringUtils.isEmpty(interviewSubroundsForm.getInterviewsPerPanel())){
			errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.interviewsperpanel.required"));
		}
		if(!StringUtils.isEmpty(interviewSubroundsForm.getInterviewsPerPanel()) &&
				!StringUtils.isNumeric(interviewSubroundsForm.getInterviewsPerPanel()) ){
			errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.interviewsperpanel.integer"));
		}
		if(!StringUtils.isEmpty(interviewSubroundsForm.getInterviewsPerPanel()) 
				&& StringUtils.isNumeric(interviewSubroundsForm.getInterviewsPerPanel()) 
				&& Integer.parseInt(interviewSubroundsForm.getInterviewsPerPanel()) == 0){
			errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.interviewsperpanel.integer"));
		}
		
		
		log.info("exit of validateInterviewersPerPanel in InterviewSubRoundsAction class..");	
		return errors;
	}
	
	
	public ActionForward initCopySubRoundDefinition(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		log.info("Entered initCopySubRoundDefinition Action");
		InterviewSubroundsForm interviewSubroundsForm = (InterviewSubroundsForm) form;
		interviewSubroundsForm.clear();
		interviewSubroundsForm.setInterviewSubroundsList(null);
		setUserId(request, interviewSubroundsForm);
		log.info("Exit initCopySubRoundDefinition Action");	
		return mapping.findForward(CMSConstants.KNOWLEDGEPRO_COPY_INTERVIEWSUBROUND);
	}
	

	/**
	 * method returns the searched interview sub-round definition for the selected fromYear
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward searchSubRoundDefinition(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		log.debug("inside searchSubRoundDefinition in Action");
		InterviewSubroundsForm copyForm = (InterviewSubroundsForm) form;
		 ActionErrors errors = new ActionErrors();
		
		validateYear(errors, copyForm);
		setUserId(request, copyForm);
		List<InterviewSubroundsTO> interviewSubRoundsList=null;
		try{
			if(errors.isEmpty()){
			if (copyForm.getFromYear() == null) {
				Calendar calendar = Calendar.getInstance();
				int currentYear = calendar.get(Calendar.YEAR);
				// code by hari
				int year = CurrentAcademicYear.getInstance().getAcademicyear();
				if (year != 0) {
					currentYear = year;
				}// end
				interviewSubRoundsList = InterviewSubroundsHandler.getInstance().getSubRoundDefinitionByYear(currentYear,copyForm);;
			} else {
				int year = Integer.parseInt(copyForm.getFromYear());
				interviewSubRoundsList = InterviewSubroundsHandler.getInstance().getSubRoundDefinitionByYear(year,copyForm);
			}
			if (interviewSubRoundsList != null) {
				copyForm.setInterviewSubroundsList(interviewSubRoundsList);
			}
			else{
				errors.add("error", new ActionError(CMSConstants.KNOWLEDGEPRO_NORECORDS));
				saveErrors(request, errors);
			}
			}
			
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			copyForm.setErrorMessage(msg);
			copyForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.KNOWLEDGEPRO_COPY_INTERVIEWSUBROUND);
	}
	
	/**
	 * validates the entered year by user
	 * @param errors
	 * @param copyCheckListAssignmentForm
	 */
	public void validateYear(ActionErrors errors,InterviewSubroundsForm copyForm){
		
		if(copyForm.getFromYear()==null && copyForm.getFromYear().isEmpty()){
			errors.add("error", new ActionError("knowledgepro.admission.copySubRoundDefinition.fromYear.required"));
		}
		if(copyForm.getToYear()==null && copyForm.getToYear().isEmpty()){
			errors.add("error", new ActionError("knowledgepro.admission.copySubRoundDefinition.toYear.required"));
		}
		if(copyForm.getToYear()!=null && !copyForm.getToYear().isEmpty() && copyForm.getFromYear()!=null && !copyForm.getFromYear().isEmpty()){
		if(Integer.parseInt(copyForm.getToYear())< Integer.parseInt(copyForm.getFromYear())){
			errors.add("error", new ActionError("knowledgepro.admission.validate.year"));
		}
		if(copyForm.getToYear().equals(copyForm.getFromYear())){
			errors.add("error", new ActionError("knowledgepro.admission.validate.equalyear"));
		}
	}
}
	
	
	/**
	 * copying the selected interview sub round definition 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward copySubRoundDefinition(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		log.debug("inside copySubRoundDefinition in Action");
		InterviewSubroundsForm copyForm = (InterviewSubroundsForm) form;
		 ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		setUserId(request, copyForm);
		ActionMessage message = null;
		boolean isCopied=false;
		try{
			isCopied=InterviewSubroundsHandler.getInstance().copySubRoundDefinition(copyForm);
			if(isCopied){
				 message = new ActionMessage("knowledgepro.admission.copyClasses.success");
				messages.add(CMSConstants.MESSAGES, message);
				saveMessages(request, messages);
				copyForm.clear();
				copyForm.setInterviewSubroundsList(null);
				
			}else{
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.copyInterviewSubRoundDefinition.failure"));
				saveErrors(request, errors);
			}
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			copyForm.setErrorMessage(msg);
			copyForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.KNOWLEDGEPRO_COPY_INTERVIEWSUBROUND);
	}
	/**
	 * Method to set the required data to the form to display in interviewSubrounds.jsp
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getInterviewSubroundList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		InterviewSubroundsForm interviewSubroundsForm = (InterviewSubroundsForm) form;
		try{
			if(interviewSubroundsForm.getAppliedYear() != null && !interviewSubroundsForm.getAppliedYear().isEmpty()){
				List<InterviewSubroundsTO> interviewSubroundsList = InterviewSubroundsHandler.getInstance().getInterviewSubroundsList(Integer.parseInt(interviewSubroundsForm.getAppliedYear()));
				interviewSubroundsForm.setInterviewSubroundsList(interviewSubroundsList);
			}
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			interviewSubroundsForm.setErrorMessage(msg);
			interviewSubroundsForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		
		return mapping.findForward(CMSConstants.KNOWLEDGEPRO_INTERVIEWSUBROUND);
	}
}