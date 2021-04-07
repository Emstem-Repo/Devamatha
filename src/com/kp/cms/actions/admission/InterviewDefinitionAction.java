package com.kp.cms.actions.admission;

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

import com.ibm.icu.util.Calendar;
import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.admission.InterviewDefinitionForm;
import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.handlers.admission.InterviewDefinitionHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.admission.InterviewProgramCourseTO;
import com.kp.cms.utilities.CurrentAcademicYear;

/**
 * Action class for Interview Definition
 */
@SuppressWarnings("deprecation")
public class InterviewDefinitionAction extends BaseDispatchAction {
	
	private static final Log log = LogFactory.getLog(InterviewDefinitionAction.class);
	
	/**
	 * Method to set the required data to the form to display in interviewDefinition.jsp
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getInterviewDefinition(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entered getInterviewDefinition Action");
		InterviewDefinitionForm interviewDefinitionForm = (InterviewDefinitionForm) form;
		ActionMessages messages = new ActionMessages();
		try {
			interviewDefinitionForm.clear();
			getProgramTypeList(interviewDefinitionForm);
			getInterviewDefinitionList(interviewDefinitionForm);
			setUserId(request, interviewDefinitionForm);
		}catch (BusinessException businessException) {
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			interviewDefinitionForm.setErrorMessage(msg);
			interviewDefinitionForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}		
		log.info("Exit getInterviewDefinition Action");		
		return mapping.findForward(CMSConstants.INTERVIEW_DEFINITION);
	}
	
	/**
	 * Method to set all active Program Types to the form
	 * @param interviewDefinitionForm
	 * @throws Exception
	 */
	public void getProgramTypeList(InterviewDefinitionForm interviewDefinitionForm) throws Exception{
		List<ProgramTypeTO> programTypeList = ProgramTypeHandler.getInstance().getProgramType();
		if( programTypeList != null ){
			interviewDefinitionForm.setProgramTypeList(programTypeList);
		}
	}
	
	/**
	 * Method to set all active Interview Definitions to the form
	 * @param interviewDefinitionForm
	 * @throws Exception
	 */
	public void getInterviewDefinitionList(InterviewDefinitionForm interviewDefinitionForm) throws Exception{
//		List<InterviewProgramCourseTO> interviewDefinitionList = InterviewDefinitionHandler.getInstance().getInterviewDefinition(0);
		Calendar calendar=Calendar.getInstance();
		int currentYear = calendar.get(Calendar.YEAR);
		int year=CurrentAcademicYear.getInstance().getCurrentAcademicyear();
		if(year!=0){
			currentYear = year;
		}
		List<InterviewProgramCourseTO> interviewDefinitionList = InterviewDefinitionHandler.getInstance().getInterviewDefinitionList(currentYear);
		if( interviewDefinitionList != null ){
			interviewDefinitionForm.setInterviewDefinitionList(interviewDefinitionList);
		}
	}
	
	/**
	 * Method to add Interview Definition
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addInterviewDefinition(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		log.info("Entered addInterviewDefinition Action");
		InterviewDefinitionForm interviewDefinitionForm = (InterviewDefinitionForm) form;
		
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = interviewDefinitionForm.validate(mapping, request);
		validateInterviewersPerPanel(interviewDefinitionForm, errors);
		boolean isInterviewDefinitionAdded;
		
		try{
			if (!errors.isEmpty()) {
				log.info("Entered addInterviewDefinition Action");
				saveErrors(request, errors);
				getInterviewDefinitionList(interviewDefinitionForm);
				return mapping.findForward(CMSConstants.INTERVIEW_DEFINITION);
			}
			if (interviewDefinitionForm.getIntCardContent() != null && interviewDefinitionForm.getIntCardContent().length() > 2000) {
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_INTERVIEWDEFINITION_CONTENTFAIL));
				saveErrors(request, errors);
				getInterviewDefinitionList(interviewDefinitionForm);
				return mapping.findForward(CMSConstants.INTERVIEW_DEFINITION);
			}
			
			//setUserId(request, interviewDefinitionForm);
			isInterviewDefinitionAdded = InterviewDefinitionHandler.getInstance().addInterviewDefinition(interviewDefinitionForm, "Add");
		
			if (isInterviewDefinitionAdded) {
				getInterviewDefinitionList(interviewDefinitionForm);
				
				ActionMessage message = new ActionMessage(CMSConstants.KNOWLEDGEPRO_INTERVIEWDEFINITION_ADDSUCCESS);
				messages.add(CMSConstants.MESSAGES, message);
				saveMessages(request, messages);
				interviewDefinitionForm.clear();
			} else {
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_INTERVIEWDEFINITION_ADDFAILURE));
				saveErrors(request, errors);
			}
		} catch (DuplicateException duplicateException) {
			log.info("Entered addInterviewDefinition Action duplicate exception");
			errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_INTERVIEWDEFINITION_ADDEXIST,
					interviewDefinitionForm.getCourseName(), interviewDefinitionForm.getYear()));
			saveErrors(request, errors);
			getInterviewDefinitionList(interviewDefinitionForm);
			return mapping.findForward(CMSConstants.INTERVIEW_DEFINITION);
		} catch (ReActivateException reActivateException) {
			log.info("Entered addInterviewDefinition Action reactivate exception");
			errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_INTERVIEWDEFINITION_REACTIVATE));
			saveErrors(request, errors);
			getInterviewDefinitionList(interviewDefinitionForm);
			return mapping.findForward(CMSConstants.INTERVIEW_DEFINITION);
		} catch (BusinessException businessException) {
			log.info("Exception addInterviewDefinition");
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {	
			String msg = super.handleApplicationException(exception);
			interviewDefinitionForm.setErrorMessage(msg);
			interviewDefinitionForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.INTERVIEW_DEFINITION);
	}
	
	/**
	 * Method to update Interview Definition
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateInterviewDefinition(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		log.info("Entered editInterviewDefinition Action");
		InterviewDefinitionForm interviewDefinitionForm = (InterviewDefinitionForm) form;
		
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = interviewDefinitionForm.validate(mapping, request);
		validateInterviewersPerPanel(interviewDefinitionForm, errors);
		boolean isInterviewDefinitionEdited;

		try {
			if (isCancelled(request)) {
				setRequiredDataToForm(interviewDefinitionForm, request);
				getInterviewDefinitionList(interviewDefinitionForm);
				request.setAttribute(CMSConstants.CONDITIONS_OPERATION, CMSConstants.EDIT_OPERATION);
				setProgramMapToRequest(request, interviewDefinitionForm);
				setCourseMapToRequest(request, interviewDefinitionForm);
				return mapping.findForward(CMSConstants.INTERVIEW_DEFINITION);
			}
			
			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				getInterviewDefinitionList(interviewDefinitionForm);
				request.setAttribute(CMSConstants.CONDITIONS_OPERATION, CMSConstants.EDIT_OPERATION);
				setProgramMapToRequest(request, interviewDefinitionForm);
				setCourseMapToRequest(request, interviewDefinitionForm);
				return mapping.findForward(CMSConstants.INTERVIEW_DEFINITION);
			}
			
			if (interviewDefinitionForm.getIntCardContent() != null && interviewDefinitionForm.getIntCardContent().length() > 2000) {
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_INTERVIEWDEFINITION_CONTENTFAIL));
				saveErrors(request, errors);
				getInterviewDefinitionList(interviewDefinitionForm);
				setProgramMapToRequest(request, interviewDefinitionForm);
				setCourseMapToRequest(request, interviewDefinitionForm);
				return mapping.findForward(CMSConstants.INTERVIEW_DEFINITION);
			}
			
			isInterviewDefinitionEdited = InterviewDefinitionHandler.getInstance().addInterviewDefinition(interviewDefinitionForm, "Edit");
			
			if (isInterviewDefinitionEdited) {
				getInterviewDefinitionList(interviewDefinitionForm);
				
				ActionMessage message = new ActionMessage(CMSConstants.KNOWLEDGEPRO_INTERVIEWDEFINITION_EDITSUCCESS);
				messages.add(CMSConstants.MESSAGES, message);
				saveMessages(request, messages);
				interviewDefinitionForm.clear();
			} else {
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_INTERVIEWDEFINITION_EDITFAILURE));
				saveErrors(request, errors);
			}
		}catch (DuplicateException duplicateException) {
			log.info("Entered updateInterviewDefinition Action duplicate exception");
			errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_INTERVIEWDEFINITION_ADDEXIST,
					interviewDefinitionForm.getCourseName(), interviewDefinitionForm.getYear()));
			saveErrors(request, errors);
			getInterviewDefinitionList(interviewDefinitionForm);
			request.setAttribute(CMSConstants.CONDITIONS_OPERATION, CMSConstants.EDIT_OPERATION);
			return mapping.findForward(CMSConstants.INTERVIEW_DEFINITION);
		} catch (ReActivateException reActivateException) {
			log.info("Entered updateInterviewDefinition Action reactivate exception");
			errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_INTERVIEWDEFINITION_REACTIVATE));
			saveErrors(request, errors);
			getInterviewDefinitionList(interviewDefinitionForm);
			request.setAttribute(CMSConstants.CONDITIONS_OPERATION, CMSConstants.EDIT_OPERATION);
			return mapping.findForward(CMSConstants.INTERVIEW_DEFINITION);
		} catch (BusinessException businessException) {
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			interviewDefinitionForm.setErrorMessage(msg);
			interviewDefinitionForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Exit editInterviewDefinition Action");
		return mapping.findForward(CMSConstants.INTERVIEW_DEFINITION);
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
	public ActionForward editInterviewDefinition(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		log.info("Entered editInterviewDefinition");
		InterviewDefinitionForm interviewDefinitionForm = (InterviewDefinitionForm) form;
		
		setRequiredDataToForm(interviewDefinitionForm, request);
		request.setAttribute(CMSConstants.CONDITIONS_OPERATION, CMSConstants.EDIT_OPERATION);
		log.info("Exit editInterviewDefinition");
		
		return mapping.findForward(CMSConstants.INTERVIEW_DEFINITION);
	}
	
	/**
	 * Method to change the selected interview definition to inactive state
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteInterviewDefinition(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		log.info("Entered deleteInterviewDefinition Action");
		InterviewDefinitionForm interviewDefinitionForm = (InterviewDefinitionForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		boolean isDeleted;
		try {
			if (interviewDefinitionForm.getId() != 0) {
				int interviewDefinitionId = interviewDefinitionForm.getId();
				String uid = interviewDefinitionForm.getUserId();
				 
				isDeleted = InterviewDefinitionHandler.getInstance().deleteInterviewDefinition(interviewDefinitionId, false, uid);
			
				if (isDeleted) {
					getInterviewDefinitionList(interviewDefinitionForm);
	
					ActionMessage message = new ActionMessage(CMSConstants.KNOWLEDGEPRO_INTERVIEWDEFINITION_DELETESUCCESS, interviewDefinitionForm.getId());
					messages.add(CMSConstants.MESSAGES, message);
					saveMessages(request, messages);
					interviewDefinitionForm.clear();
				}
			}else {
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_INTERVIEWDEFINITION_DELETEFAILURE, interviewDefinitionForm.getId()));
				saveErrors(request, errors);
			}
		} catch (BusinessException businessException) {
			log.info("Exception deleteInterviewDefinition");
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {
			log.info("Exception deleteInterviewDefinition");
			String msg = super.handleApplicationException(exception);
			interviewDefinitionForm.setErrorMessage(msg);
			interviewDefinitionForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.INTERVIEW_DEFINITION);
	}
	
	/**
	 * Method to populate the selected information to the form during edit
	 * @param interviewDefinitionForm
	 * @param request
	 * @throws Exception
	 */
	public void setRequiredDataToForm(InterviewDefinitionForm interviewDefinitionForm,
			HttpServletRequest request) throws Exception {
		int interviewDefinitionId = Integer.parseInt(request.getParameter("id"));
		List<InterviewProgramCourseTO> interviewDefinitionList = InterviewDefinitionHandler.getInstance().getInterviewDefinition(interviewDefinitionId);
		Iterator<InterviewProgramCourseTO> iterator = interviewDefinitionList.iterator();
		InterviewProgramCourseTO interviewProgramCourseTO;

		while (iterator.hasNext()) {
			interviewProgramCourseTO = (InterviewProgramCourseTO) iterator.next();
			int programTypeId = interviewProgramCourseTO.getProgramTypeId();
			interviewDefinitionForm.setProgramTypeId(Integer.toString(programTypeId));
			interviewDefinitionForm.setProgramId(Integer.toString(interviewProgramCourseTO.getProgramId()));
			interviewDefinitionForm.setCourseId(Integer.toString(interviewProgramCourseTO.getCourseId()));
			interviewDefinitionForm.setYear(Integer.toString(interviewProgramCourseTO.getYear()));
			interviewDefinitionForm.setInterviewType(interviewProgramCourseTO.getName());
			interviewDefinitionForm.setSequence(interviewProgramCourseTO.getSequence());
			interviewDefinitionForm.setInterviewsPerPanel(interviewProgramCourseTO.getInterviewsPerPanel());
			//setting to check the duplication
			interviewDefinitionForm.setOriginalCourseId(interviewProgramCourseTO.getCourseId());
			interviewDefinitionForm.setOriginalYear(interviewProgramCourseTO.getYear());
			interviewDefinitionForm.setOriginalSequence(interviewProgramCourseTO.getSequence());
			interviewDefinitionForm.setIntCardContent(interviewProgramCourseTO.getContent());
			
		}
		
		setProgramMapToRequest(request, interviewDefinitionForm);
		setCourseMapToRequest(request, interviewDefinitionForm);
		request.setAttribute(CMSConstants.CONDITIONS_OPERATION, CMSConstants.EDIT_OPERATION);
	}
	
	/**
	 * Method to set all active programs to the request object
	 * @param request
	 * @param interviewDefinitionForm
	 */
	public void setProgramMapToRequest(HttpServletRequest request,
			InterviewDefinitionForm interviewDefinitionForm) {
		if (interviewDefinitionForm.getProgramTypeId() != null
				&& (!interviewDefinitionForm.getProgramTypeId().trim().isEmpty())) {
			Map<Integer, String> programMap = CommonAjaxHandler.getInstance().getProgramsByProgramType(Integer.parseInt(interviewDefinitionForm.getProgramTypeId()));
			request.setAttribute("programMap", programMap);
		}
	}

	/**
	 * Method to set all active courses to the request object
	 * @param request
	 * @param interviewDefinitionForm
	 */
	public void setCourseMapToRequest(HttpServletRequest request,
			InterviewDefinitionForm interviewDefinitionForm) {
		if (interviewDefinitionForm.getProgramId() != null
				&& (!interviewDefinitionForm.getProgramId().trim().isEmpty())) {
			Map<Integer, String> courseMap = CommonAjaxHandler.getInstance().getCourseByProgram(Integer.parseInt(interviewDefinitionForm.getProgramId()));
			request.setAttribute("courseMap", courseMap);
		}
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
	public ActionForward activateInterviewDefinition(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		log.debug("Entered activateInterviewDefinition");
		InterviewDefinitionForm interviewDefinitionForm = (InterviewDefinitionForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		boolean isActivated;
		try {
			if (interviewDefinitionForm.getActivationRefId() != 0) {
				int interviewDefinitionId = interviewDefinitionForm.getActivationRefId();
				String uid = interviewDefinitionForm.getUserId();
				
				isActivated = InterviewDefinitionHandler.getInstance().deleteInterviewDefinition(interviewDefinitionId, true, uid);
				
				getInterviewDefinitionList(interviewDefinitionForm);
				if (isActivated) {
					ActionMessage message = new ActionMessage(CMSConstants.KNOWLEDGEPRO_INTERVIEWDEFINITION_ACTIVATESUCCESS);
					messages.add(CMSConstants.MESSAGES, message);
					saveMessages(request, messages);
					interviewDefinitionForm.clear();
				}
			}
		} catch (Exception e) {
			errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_INTERVIEWDEFINITION_ADDFAILURE));
			saveErrors(request, errors);
		}
		log.debug("Exit activateInterviewDefinition");
		return mapping.findForward(CMSConstants.INTERVIEW_DEFINITION);
	}
	
	/**
	 * Method to display selected interview definition in view mode
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward viewInterviewDefinition(
		    ActionMapping mapping,
		    ActionForm form,
		    HttpServletRequest request,
		    HttpServletResponse response) throws Exception{
		
		InterviewDefinitionForm interviewDefinitionForm = (InterviewDefinitionForm) form;
		List<InterviewProgramCourseTO> interviewDefinitionList = InterviewDefinitionHandler.getInstance().getInterviewDefinition(interviewDefinitionForm.getId());
		Iterator<InterviewProgramCourseTO> iterator = interviewDefinitionList.iterator();
		InterviewProgramCourseTO interviewProgramCourseTO;

		while (iterator.hasNext()) {
			interviewProgramCourseTO = (InterviewProgramCourseTO) iterator.next();
			interviewDefinitionForm.setViewDesc(interviewProgramCourseTO.getContent());
		}
		return mapping.findForward(CMSConstants.VIEW_INTERVIEW_CARD_CONTENT);
	}	
	
	
	/**
	 * This is going to validate interviewers per panel
	 * @param interviewDefinitionForm
	 * @param errors
	 * @return
	 */
	
	private ActionErrors validateInterviewersPerPanel(InterviewDefinitionForm interviewDefinitionForm,
			ActionErrors errors) {
		log.info("entering into validateInterviewersPerPanel in InterviewDefinitionAction class..");
		if (errors == null)
			errors = new ActionErrors();
		if(StringUtils.isEmpty(interviewDefinitionForm.getInterviewsPerPanel())){
			errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.interviewsperpanel.required"));
		}
		if(!StringUtils.isEmpty(interviewDefinitionForm.getInterviewsPerPanel()) &&
				!StringUtils.isNumeric(interviewDefinitionForm.getInterviewsPerPanel()) ){
			errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.interviewsperpanel.integer"));
		}
		if(!StringUtils.isEmpty(interviewDefinitionForm.getInterviewsPerPanel()) 
				&& StringUtils.isNumeric(interviewDefinitionForm.getInterviewsPerPanel()) 
				&& Integer.parseInt(interviewDefinitionForm.getInterviewsPerPanel()) == 0){
			errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.interviewsperpanel.integer"));
		}
		
		
		log.info("exit of validateInterviewersPerPanel in InterviewDefinitionAction class..");	
		return errors;
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
	public ActionForward getInterviewDefinitionList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		log.debug("Entered activateInterviewDefinition");
		InterviewDefinitionForm interviewDefinitionForm = (InterviewDefinitionForm) form;
		ActionErrors errors = new ActionErrors();
		try {
			if(interviewDefinitionForm.getYear() != null && !interviewDefinitionForm.getYear().isEmpty()){
				List<InterviewProgramCourseTO> interviewDefinitionList = InterviewDefinitionHandler.getInstance().getInterviewDefinitionList(Integer.parseInt(interviewDefinitionForm.getYear()));
				interviewDefinitionForm.setInterviewDefinitionList(interviewDefinitionList);
			}
		} catch (Exception e) {
			errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_INTERVIEWDEFINITION_ADDFAILURE));
			saveErrors(request, errors);
		}
		log.debug("Exit activateInterviewDefinition");
		return mapping.findForward(CMSConstants.INTERVIEW_DEFINITION);
	}
	
}