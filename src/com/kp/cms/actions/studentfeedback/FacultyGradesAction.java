package com.kp.cms.actions.studentfeedback;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
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
import com.kp.cms.bo.studentfeedback.FacultyGrades;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.forms.studentfeedback.FacultyGradesForm;
import com.kp.cms.handlers.studentfeedback.FacultyGradesHandler;
import com.kp.cms.to.studentfeedback.FacultyGradesTO;
import com.kp.cms.utilities.CommonUtil;

@SuppressWarnings("deprecation")
public class FacultyGradesAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(FacultyGradesAction.class);
	
	public ActionForward initFacultyGrades(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Start of FacultyGradesAction --- initFacultyGrades");
		FacultyGradesForm facultyGradesForm=(FacultyGradesForm)form;		
		try {
			assignListToForm(facultyGradesForm);
			facultyGradesForm.clearAll();			
		} catch (Exception e) {
			log.error("Error in initializing FacultyGrades",e);
				log.error("Error occured in initFacultyGrades of FacultyGradesAction");
				String msg = super.handleApplicationException(e);
				facultyGradesForm.setErrorMessage(msg);
				facultyGradesForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		log.info("Leaving of FacultyGradesAction --- initFacultyGrades");
		return mapping.findForward(CMSConstants.INIT_FACULTYGRADES);
	}
	
	public void assignListToForm(ActionForm form) throws Exception
	{
		log.info("Entering into -- FacultyGradesAction --- assignListToForm");
		FacultyGradesForm facultyGradesForm	= (FacultyGradesForm) form;
		try {
			List<FacultyGradesTO> facultyGradesList = FacultyGradesHandler.getInstance().getFacultyGradesDetails();
			facultyGradesForm.setFacultyList(facultyGradesList);
		} catch (Exception e) {
			log.error("Error in assignListToForm of FacultyGrades Action",e);
				String msg = super.handleApplicationException(e);
				facultyGradesForm.setErrorMessage(msg);
				facultyGradesForm.setErrorStack(e.getMessage());
			}
		log.info("Leaving from -- FacultyGradesAction --- assignListToForm");
	}
	
	public ActionForward addFacultyGrades(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entring into-- FacultyGradesAction --- addFacultyGrades");
		FacultyGradesForm facultyGradesForm=(FacultyGradesForm)form;
		 ActionErrors errors = facultyGradesForm.validate(mapping, request);
		ActionMessages messages = new ActionMessages();
		
		try {
			if(isCancelled(request))
			{
				facultyGradesForm.clearAll();
				return mapping.findForward(CMSConstants.RESET_ALL);
			}			
			if(errors.isEmpty())
			{
			setUserId(request, facultyGradesForm);
			FacultyGrades facultyGrades=FacultyGradesHandler.getInstance().checkForDuplicateonGrade(facultyGradesForm.getGrade());
			if(facultyGrades!=null ){
				if(facultyGrades.getIsActive()){
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.STUDENTFEEDBACK_FACULTYGRADES_EXISTS));
					assignListToForm(facultyGradesForm);
					}

					else if(!facultyGrades.getIsActive()){
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.STUDENTFEEDBACK_FACULTYGRADES_REACTIVATE));
					assignListToForm(facultyGradesForm);
					}
			}
			
			else{
				String scaleFrom = facultyGradesForm.getScaleFrom();
				String scaleTo = facultyGradesForm.getScaleTo();
				int comparsionResult = scaleFrom.compareTo(scaleTo) ;
//				int scaleFrom = Integer.parseInt(facultyGradesForm.getScaleFrom());
//				int scaleTo = Integer.parseInt(facultyGradesForm.getScaleTo());
//			if(scaleFrom < scaleTo){
			if(comparsionResult<0){
			boolean isFacultyGradesAdded;

			isFacultyGradesAdded = FacultyGradesHandler.getInstance().addFacultyGrades(facultyGradesForm);
			//If add operation is success then display the success message.
			if(isFacultyGradesAdded)
			{
				messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.STUDENTFEEDBACK_FACULTYGRADES_ADD_SUCCESS));
				saveMessages(request, messages);
				facultyGradesForm.clearAll();
				assignListToForm(facultyGradesForm);
				return mapping.findForward(CMSConstants.INIT_FACULTYGRADES);
			}
			//If add operation is failure then add the error message.
			else{
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.STUDENTFEEDBACK_FACULTYGRADES_ADD_FAILED));
				saveErrors(request, errors);
				assignListToForm(facultyGradesForm);
				}
			}
				else{
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.STUDENTFEEDBACK_FACULTYGRADES_SCALENOTCORRECT));
					assignListToForm(facultyGradesForm);
					
				}
			}
			}
		}catch (Exception e) {
				log.error("Error in adding FacultyGrades in FacultyGrades Action",e);
				String msg = super.handleApplicationException(e);
				facultyGradesForm.setErrorMessage(msg);
				facultyGradesForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		log.info("Leaving from -- FacultyGradesAction --- addFacultyGrades");
		saveErrors(request, errors);
		return mapping.findForward(CMSConstants.INIT_FACULTYGRADES);
	}
	
	public ActionForward deleteFacultyGrades(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into--- FacultyGradesAction --- deleteFacultyGrades");
		FacultyGradesForm facultyGradesForm=(FacultyGradesForm)form;
		 ActionErrors errors = facultyGradesForm.validate(mapping, request);
		ActionMessages messages = new ActionMessages();
		
		try {
			setUserId(request, facultyGradesForm);
			int facultyGradesId = facultyGradesForm.getId();
			String userId=facultyGradesForm.getUserId();
			boolean isFacultyGradesFormDeleted;
			/**
			 * Request for deleting a guidelinesEntry based on the Id
			 */
			isFacultyGradesFormDeleted = FacultyGradesHandler.getInstance().deleteFacultyGrades(facultyGradesId, userId);
			//If delete operation is success then append the success message.
			if (isFacultyGradesFormDeleted) {
				messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.STUDENTFEEDBACK_FACULTYGRADES_DELETE_SUCCESS));
				saveMessages(request, messages);
				assignListToForm(facultyGradesForm);
				facultyGradesForm.clearAll();
			}
			//If delete operation is failure then add the error message.
			else {
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.STUDENTFEEDBACK_FACULTYGRADES_DELETE_FAILED));
				saveErrors(request, errors);
				assignListToForm(facultyGradesForm);
			}
		} catch (Exception e) {
			log.error("Error in deleting FacultyGrades in FacultyGrades Action",e);
				String msg = super.handleApplicationException(e);
				facultyGradesForm.setErrorMessage(msg);
				facultyGradesForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		log.info("Leaving from-- FacultyGradesAction --- deleteFacultyGrades");
		return mapping.findForward(CMSConstants.INIT_FACULTYGRADES);
	}
	
	public ActionForward editFacultyGrades(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into---- FacultyGradesAction --- editFacultyGrades");
		FacultyGradesForm facultyGradesForm=(FacultyGradesForm)form;
		
		try {
			//Used to clear the form.
			facultyGradesForm.clearAll();
			/**
			 * Get the particular row based on the id while clicking edit button
			 */
			FacultyGradesTO facultyGradesTO=FacultyGradesHandler.getInstance().getDetailsonId(facultyGradesForm.getId());
				
				//Set the TO properties to formbean
				if(facultyGradesTO!=null){
					if (facultyGradesTO.getGrade() != null && !facultyGradesTO.getGrade().isEmpty()) {
						facultyGradesForm.setGrade(facultyGradesTO.getGrade());
					}
					if (facultyGradesTO.getScaleFrom() != null) {
						facultyGradesForm.setScaleFrom(facultyGradesTO.getScaleFrom().toString());
					}
					if(facultyGradesTO.getScaleTo()!=null){
						facultyGradesForm.setScaleTo(facultyGradesTO.getScaleTo().toString());
					}
			}
			request.setAttribute("facultyOperation", CMSConstants.EDIT_OPERATION);
			assignListToForm(facultyGradesForm);
		} catch (Exception e) {
			log.error("Error in editing FacultyGrades in Action",e);
				String msg = super.handleApplicationException(e);
				facultyGradesForm.setErrorMessage(msg);
				facultyGradesForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		log.info("Leaving from-- FacultyGradesAction --- editFacultyGrades");
		return mapping.findForward(CMSConstants.INIT_FACULTYGRADES);
	}
	
	public ActionForward updateFacultyGrades(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into-- FacultyGradesAction --- updateFacultyGrades");
		FacultyGradesForm facultyGradesForm=(FacultyGradesForm)form; 
		 ActionErrors errors = facultyGradesForm.validate(mapping, request);
		ActionMessages messages = new ActionMessages();
		try {
			if(errors.isEmpty())
			{
				setUserId(request, facultyGradesForm);
				
				String scaleFrom = facultyGradesForm.getScaleFrom();
				String scaleTo = facultyGradesForm.getScaleTo();
				int comparsionResult = scaleFrom.compareTo(scaleTo) ;
				if(comparsionResult<0){
				boolean isUpdated;
						/**
						 * Pass the properties which have to update and call the handler method
						 */
						isUpdated=FacultyGradesHandler.getInstance().updateFacultyGrades(facultyGradesForm, errors);
						
						//If update is successful then add the success message else show the error message
						
						if(isUpdated){
							messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.STUDENTFEEDBACK_FACULTYGRADES_UPDATE_SUCCESS));
							saveMessages(request, messages);
							facultyGradesForm.clearAll();
							assignListToForm(facultyGradesForm);
							return mapping.findForward(CMSConstants.INIT_FACULTYGRADES);				
						}
						else {
							errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.STUDENTFEEDBACK_FACULTYGRADES_UPDATE_FAILED));
							saveErrors(request, errors);
							assignListToForm(facultyGradesForm);
							return mapping.findForward(CMSConstants.INIT_FACULTYGRADES);
						}
				}else{
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.STUDENTFEEDBACK_FACULTYGRADES_SCALENOTCORRECT));
					assignListToForm(facultyGradesForm);
					return mapping.findForward(CMSConstants.INIT_FACULTYGRADES);
				}

			}
		else{
			
			request.setAttribute("facultyOperation",CMSConstants.EDIT_OPERATION);
		
		}
		}catch (DuplicateException e1) {
//			errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.STUDENTFEEDBACK_FACULTYGRADES_EXISTS));
			assignListToForm(facultyGradesForm);
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.INIT_FACULTYGRADES);
		}
		
		catch (Exception e) {
			log.error("Error in updating FacultyGrades in FacultyGrades Action",e);
				String msg = super.handleApplicationException(e);
				facultyGradesForm.setErrorMessage(msg);
				facultyGradesForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		log.info("Leaving from --- FacultyGradesAction --- updateFacultyGrades");
		saveErrors(request, errors);
		assignListToForm(facultyGradesForm);
		return mapping.findForward(CMSConstants.INIT_FACULTYGRADES);
		
	}
	
	public ActionForward reActivateFacultyGrades(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into-- FacultyGradesAction --- reActivateFacultyGrades");
		FacultyGradesForm facultyGradesForm=(FacultyGradesForm)form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();		
		try {
			setUserId(request, facultyGradesForm);
			boolean isReacivate;
			String grade = facultyGradesForm.getGrade();
			//Request for reactivate by calling the handler method and pass the name
			isReacivate = FacultyGradesHandler.getInstance().reActivateFacultyGrades(grade,facultyGradesForm.getUserId());
			//If reactivation successful show the message else add the error message
			if (isReacivate) {
				messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.STUDENTFEEDBACK_FACULTYGRADES_REACTIVATE_SUCCESS));
				saveMessages(request, messages);
				facultyGradesForm.clearAll();
				assignListToForm(facultyGradesForm);
				return mapping.findForward(CMSConstants.INIT_FACULTYGRADES);
			} else {
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.STUDENTFEEDBACK_FACULTYGRADES_REACTIVATE_FAILED));
				saveErrors(request, errors);
				assignListToForm(facultyGradesForm);
			}
		} catch (Exception e) {
			log.error("Error in reactivating FacultyGradesAction",e);
				String msg = super.handleApplicationException(e);
				facultyGradesForm.setErrorMessage(msg);
				facultyGradesForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		log.info("Leaving from-- FacultyGradesAction --- reActivateFacultyGrades");
		return mapping.findForward(CMSConstants.INIT_FACULTYGRADES);
	}
	
	
}
