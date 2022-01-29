package com.kp.cms.actions.admin;

import java.io.IOException;
import java.io.InputStream;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import org.apache.struts.upload.FormFile; 



import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.bo.admin.GuidelinesDoc;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admin.GuidelinesEntryForm;
import com.kp.cms.handlers.admin.GuidelinesEntryHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;

import com.kp.cms.to.admin.GuidelinesEntryTO;

import com.kp.cms.utilities.RenderYearList;


@SuppressWarnings("deprecation")
public class GuidelinesEntryAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(GuidelinesEntryAction.class);
	private static final String GUIDELINES_OPERATION = "guidelinesOperation";

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request Initializes Guidelines Entry and displays all the records where isActive=1 in UI
	 * @param response
	 * @returns init page with all guideline entries
	 * @throws Exception
	 */
	public ActionForward initGuidelinesEntry(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		GuidelinesEntryForm guidelinesEntryForm = (GuidelinesEntryForm) form;		
		try {
			assignListToForm(guidelinesEntryForm);
			HttpSession session=request.getSession(false);
			if(session.getAttribute(GUIDELINES_OPERATION)!=null){
			session.removeAttribute(GUIDELINES_OPERATION);
			}
			guidelinesEntryForm.clear();
			setUserId(request, guidelinesEntryForm);
		} catch (Exception e) {
			log.error("Error occured in initGuidelinesEntry GuidelinesEntryAction",e);
				String msg = super.handleApplicationException(e);
				guidelinesEntryForm.setErrorMessage(msg);
				guidelinesEntryForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		return mapping.findForward(CMSConstants.INIT_GUIDELINESENTRY);
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response Adds a guidelines entry for a particular course and year
	 * @return
	 * @throws Exception
	 */

	public ActionForward addGuidelinesEntry(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		GuidelinesEntryForm guidelinesEntryForm = (GuidelinesEntryForm) form;
		ActionErrors errors = guidelinesEntryForm.validate(mapping, request);
		ActionMessages messages = new ActionMessages();
		errors =  validateDocumentSize(guidelinesEntryForm, errors);
		try {
			if (errors.isEmpty() && messages.isEmpty()) {
			
			int courseId=Integer.parseInt(guidelinesEntryForm.getCourse());
			int year=Integer.parseInt(guidelinesEntryForm.getYear());
			
			/**
			 * Check for the duplicate entry if the guidelines is exist for the course and year
			 * And add the appropriate error message
			 */
			GuidelinesDoc doc=GuidelinesEntryHandler.getInstance().isGuidelinesExistCourseYear(courseId,year);
			//If the record already present in active mode append the error message
				if(doc!=null && doc.getIsActive()){
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMIN_GUIDELINESENTRY_EXISTS));				
				}
			//If the record is in inactive state. Then say for activation
				else if(doc!=null && !doc.getIsActive()){
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMIN_GUIDELINESENTRY_REACTIVATE));
					guidelinesEntryForm.setOldCourseId(courseId);
					guidelinesEntryForm.setOldYear(year);
				}
			else{
			boolean isAdded;
			isAdded = GuidelinesEntryHandler.getInstance().addGuidelinesEntry(guidelinesEntryForm);
			/**
			 * If add operation is success then add the success message else add appropriate error message
			 */
				if (isAdded) {				
					ActionMessage message = new ActionMessage(CMSConstants.ADMIN_GUIDELINESENTRY_ADD_SUCCESS);
					messages.add(CMSConstants.MESSAGES, message);
					guidelinesEntryForm.clear();
				}
				else {
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMIN_GUIDELINESENTRY_ADD_FAILED));
				}
			}
			}
		} catch (Exception e) {
			log.error("Error occured in addGuidelinesEntry GuidelinesEntryAction",e);
			if (e instanceof ApplicationException) {
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMIN_GUIDELINESENTRY_ADD_FAILED));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.GET_GUIDELINESENTRY);
			}
			if (e instanceof BusinessException) {
				String msg = super.handleApplicationException(e);
				guidelinesEntryForm.setErrorMessage(msg);
				guidelinesEntryForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}
		saveErrors(request, errors);
		saveMessages(request, messages);
		assignListToForm(guidelinesEntryForm);
		return mapping.findForward(CMSConstants.INIT_GUIDELINESENTRY);
	}
/**
 * 
 * @param mapping
 * @param form
 * @param request Deletes a guidelines entry (Makes isActive=0 in Database)
 * @param response
 * @return
 * @throws Exception
 */
	public ActionForward deleteGuidelinesEntry(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception 
			{
		log.info("Inside of deleteGuidelinesEntry");
		GuidelinesEntryForm guidelinesEntryForm=(GuidelinesEntryForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = guidelinesEntryForm.validate(mapping, request);		
		try {
			setUserId(request, guidelinesEntryForm);
			String userId=guidelinesEntryForm.getUserId();
			boolean isDeleted;
			int guidelinesId = guidelinesEntryForm.getId();
			
			/*
			 * Get the id from formbean and pass to handler
			 */			
			isDeleted = GuidelinesEntryHandler.getInstance().deleteGuidelinesEntry(guidelinesId, userId);			
			//If deleted from DB then show the message else show the failure message
			if (isDeleted) {
				ActionMessage message = new ActionMessage(CMSConstants.ADMIN_GUIDELINESENTRY_DELETE_SUCCESS);
				messages.add(CMSConstants.MESSAGES, message);
				saveMessages(request, messages);
				HttpSession session=request.getSession(false);
				if(session.getAttribute(GUIDELINES_OPERATION)!=null){
				session.removeAttribute(GUIDELINES_OPERATION);
				}
				assignListToForm(guidelinesEntryForm);
				guidelinesEntryForm.clear();
			}
			else {
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMIN_GUIDELINESENTRY_DELETE_FAILED));
				saveErrors(request, errors);
				HttpSession session=request.getSession(false);
				if(session.getAttribute(GUIDELINES_OPERATION)!=null){
				session.removeAttribute(GUIDELINES_OPERATION);
				}
				assignListToForm(guidelinesEntryForm);
				guidelinesEntryForm.clear();	
			}
		}
		catch (Exception e) {
			log.error("Error occured in deleteGuidelinesEntry GuidelinesEntryAction",e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				guidelinesEntryForm.setErrorMessage(msg);
				guidelinesEntryForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}else {
				assignListToForm(guidelinesEntryForm);
				return mapping.findForward(CMSConstants.INIT_GUIDELINESENTRY);	
			}
		}
		return mapping.findForward(CMSConstants.INIT_GUIDELINESENTRY);		
		}
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response Invoked at the time of clicking edit button. Repopulates program type, program & course in UI 
	 * @return
	 * @throws Exception
	 */

	public ActionForward editGuidelinesEntry(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		GuidelinesEntryForm guidelinesEntryForm=(GuidelinesEntryForm)form;		
		try {
			HttpSession session=request.getSession(false);
			//Below condition is used to get the programMap.
				if(guidelinesEntryForm.getProgramType()!=null && !guidelinesEntryForm.getProgramType().isEmpty()){
				Map<Integer,String> programMap = CommonAjaxHandler.getInstance().getProgramsByProgramType(Integer.parseInt(guidelinesEntryForm.getProgramType()));
				session.setAttribute("programMap", programMap);
				}
				//Below condition is used to get the courseMap.
				if(guidelinesEntryForm.getProgram()!=null && !guidelinesEntryForm.getProgram().isEmpty()){
				Map<Integer,String> courseMap = CommonAjaxHandler.getInstance().getCourseByProgram(Integer.parseInt(guidelinesEntryForm.getProgram()));
				session.setAttribute("courseMap", courseMap);
				}
			session.setAttribute(GUIDELINES_OPERATION,CMSConstants.EDIT_OPERATION);	
		
		/*
		 * Based on the selected id retrieve the record from DB
		 */
		GuidelinesEntryTO guidelinesEntryTO=GuidelinesEntryHandler.getInstance().getGuidelinesbyId(guidelinesEntryForm.getId());
			if(guidelinesEntryTO!=null){
				guidelinesEntryForm.setYear(String.valueOf(guidelinesEntryTO.getYear()));	
				guidelinesEntryForm.setCourse(String.valueOf(guidelinesEntryTO.getCourseTO().getId()));
				guidelinesEntryForm.setProgram(String.valueOf(guidelinesEntryTO.getCourseTO().getProgramTo().getId()));
				guidelinesEntryForm.setProgramType(String.valueOf(guidelinesEntryTO.getCourseTO().getProgramTo().getProgramTypeTo().getProgramTypeId()));
				guidelinesEntryForm.setOldCourseId(guidelinesEntryTO.getCourseTO().getId());
				guidelinesEntryForm.setOldYear(guidelinesEntryTO.getYear());
				}
		
		guidelinesEntryForm.setSelectedId(guidelinesEntryForm.getId());
		}
		catch (Exception e) {
			if (e instanceof ApplicationException) {
				log.error("Error occured in editGuidelinesEntry GuidelinesEntryAction",e);
				String msg = super.handleApplicationException(e);
				guidelinesEntryForm.setErrorMessage(msg);
				guidelinesEntryForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}
		assignListToForm(guidelinesEntryForm);
		return mapping.findForward(CMSConstants.INIT_GUIDELINESENTRY);
	}
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request Updates a guidelines entry in DB
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateGuidelinesEntry(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into updateGuidelinesEntry");
		GuidelinesEntryForm guidelinesEntryForm=(GuidelinesEntryForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = guidelinesEntryForm.validate(mapping, request);
		errors =  validateDocumentSize(guidelinesEntryForm, errors);
		HttpSession session=request.getSession(false);
		try {
			if(isCancelled(request)){
				//Is used while clicking in reset button at update screen (Gets the old saved values)				
					/*
					 * Get the old values based on the Id
					 * Gets the programtypeId, programId from TO and calls commonAjax handler method
					 * Returns course and program map and keep in session scope
					 */
					if(guidelinesEntryForm.getId()!=0){
						GuidelinesEntryTO guidelinesEntryTO=GuidelinesEntryHandler.getInstance().getGuidelinesbyId(guidelinesEntryForm.getId());
						if(guidelinesEntryTO!=null){
							guidelinesEntryForm.setYear(String.valueOf(guidelinesEntryTO.getYear()));	
							guidelinesEntryForm.setCourse(String.valueOf(guidelinesEntryTO.getCourseTO().getId()));
							guidelinesEntryForm.setProgram(String.valueOf(guidelinesEntryTO.getCourseTO().getProgramTo().getId()));
							guidelinesEntryForm.setProgramType(String.valueOf(guidelinesEntryTO.getCourseTO().getProgramTo().getProgramTypeTo().getProgramTypeId()));
							}
					Map<Integer,String> programMap = new HashMap<Integer,String>();
					Map<Integer,String> courseMap = new HashMap<Integer,String>();
					//Get the programMap and courseMap and set in session scope
					if(guidelinesEntryTO!=null){
					     if(guidelinesEntryTO.getCourseTO().getProgramTo().getProgramTypeTo().getProgramTypeId()!=0){
						       programMap = CommonAjaxHandler.getInstance().getProgramsByProgramType(guidelinesEntryTO.getCourseTO().getProgramTo().getProgramTypeTo().getProgramTypeId());
					     }
					     if(guidelinesEntryTO.getCourseTO().getProgramTo().getId()!=0){
					           courseMap = CommonAjaxHandler.getInstance().getCourseByProgram(guidelinesEntryTO.getCourseTO().getProgramTo().getId());
					     }
					}
					session.setAttribute("programMap", programMap);
					session.setAttribute("courseMap", courseMap);
					session.setAttribute(GUIDELINES_OPERATION,CMSConstants.EDIT_OPERATION);		
				}	
					return mapping.findForward(CMSConstants.INIT_GUIDELINESENTRY);
			}
			else if (errors.isEmpty()&& messages.isEmpty()) {
				boolean isUpdated;
				
				int courseId=Integer.parseInt(guidelinesEntryForm.getCourse());
				int year=Integer.parseInt(guidelinesEntryForm.getYear());
				/**
				 * Check for the duplicate entry if the guidelines is exist for the course and year
				 * if that particular record is exist in inactive mode then activate it
				 * If it exists and in active mode. then append the error message
				 */
				if(courseId!=guidelinesEntryForm.getOldCourseId() || year!=guidelinesEntryForm.getOldYear()){
				GuidelinesDoc doc=GuidelinesEntryHandler.getInstance().isGuidelinesExistCourseYear(courseId,year);
				if(doc!=null && doc.getIsActive()){
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMIN_GUIDELINESENTRY_EXISTS));
					session.setAttribute(GUIDELINES_OPERATION,CMSConstants.EDIT_OPERATION);
					setProgramMapToRequest(request, guidelinesEntryForm);
					setCourseMapToRequest(request, guidelinesEntryForm);
					assignListToForm(guidelinesEntryForm);			
					}
				else if(doc!=null && !doc.getIsActive()){
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMIN_GUIDELINESENTRY_REACTIVATE));
					assignListToForm(guidelinesEntryForm);
					session.setAttribute(GUIDELINES_OPERATION,CMSConstants.EDIT_OPERATION);
					setProgramMapToRequest(request, guidelinesEntryForm);
					setCourseMapToRequest(request, guidelinesEntryForm);
					guidelinesEntryForm.setOldCourseId(courseId);
					guidelinesEntryForm.setOldYear(year);
				}
				else{
					//Pass the formbean properties to handler for update the guidelines entry					
					isUpdated=GuidelinesEntryHandler.getInstance().updateGuidelinesEntryFormToTO(guidelinesEntryForm);
					//If update true then show the success message else show the failure message
					if(isUpdated){			
						messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.ADMIN_GUIDELINESENTRY_UPDATE_SUCCESS));
						if(session.getAttribute(GUIDELINES_OPERATION)!=null){
						session.removeAttribute(GUIDELINES_OPERATION);
						}
						assignListToForm(guidelinesEntryForm);
						guidelinesEntryForm.clear();
					}
					else {
						errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMIN_GUIDELINESENTRY_UPDATE_FAILED));
						assignListToForm(guidelinesEntryForm);
					}
				}
			}
		else{
		//Pass the formbean properties to handler for update the guidelines entry		
			isUpdated=GuidelinesEntryHandler.getInstance().updateGuidelinesEntryFormToTO(guidelinesEntryForm);
			//If update true then show the success message else show the failure message
			if(isUpdated){			
				messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.ADMIN_GUIDELINESENTRY_UPDATE_SUCCESS));
				if(session.getAttribute(GUIDELINES_OPERATION)!=null){
				session.removeAttribute(GUIDELINES_OPERATION);
				}
				assignListToForm(guidelinesEntryForm);
				guidelinesEntryForm.clear();
			}
			else {
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMIN_GUIDELINESENTRY_UPDATE_FAILED));
				assignListToForm(guidelinesEntryForm);
			}
		}
			}
			else{
				assignListToForm(guidelinesEntryForm);
				session.setAttribute(GUIDELINES_OPERATION,CMSConstants.EDIT_OPERATION);	
			}
		}
		catch(Exception e)
		{
			log.error("Error occured in updateGuidelinesEntry GuidelinesEntryAction",e);
				String msg = super.handleApplicationException(e);
				guidelinesEntryForm.setErrorMessage(msg);
				guidelinesEntryForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		saveErrors(request, errors);
		saveMessages(request, messages);	
		return mapping.findForward(CMSConstants.INIT_GUIDELINESENTRY);		
	}
	
	
	/**
	 * Validates the uploaded file size.
	 * 
	 * @param Checks for the uploaded file and it allows the user to upload maximum of 2MB size
	 * @param errors
	 * @return ActionMessages
	 */
	private ActionErrors validateDocumentSize(GuidelinesEntryForm guidelinesEntryForm,
			ActionErrors errors)throws Exception {
		FormFile theFile =guidelinesEntryForm.getThefile();
		InputStream propStream=RenderYearList.class.getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES_1);
		int maXSize=0;
		Properties prop= new Properties();
		 try {
			 prop.load(propStream);
			 maXSize=Integer.parseInt(prop.getProperty(CMSConstants.MAX_UPLOAD_FILESIZE_KEY));
		 }catch (IOException e) {
			 log.error("Error occured in validateDocumentSize GuidelinesEntryAction",e);
			 throw new ApplicationException();
		}finally{		
			if(propStream!=null)
			     propStream.close();
		}
			if(theFile!=null && maXSize< theFile.getFileSize())
			{
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSIONFORM_ATTACHMENT_MAXSIZE));
			}
			return errors;
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request Used in reactivating Guidelines Entry
	 * @param response
	 * @return
	 * @throws Exception
	 */

	public ActionForward reActivateGuidelinesEntry(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		GuidelinesEntryForm guidelinesEntryForm=(GuidelinesEntryForm)form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		errors =  validateDocumentSize(guidelinesEntryForm, errors);
		try {
			setUserId(request, guidelinesEntryForm);
			String userId=guidelinesEntryForm.getUserId();
			if(guidelinesEntryForm.getOldCourseId()!=0 && guidelinesEntryForm.getOldYear()!=0){
			int courseId = guidelinesEntryForm.getOldCourseId();
			int year = guidelinesEntryForm.getOldYear();			
			boolean isReactivate;
			//Reactivate the guidelinesEntry by taking the courseId and year
			isReactivate=GuidelinesEntryHandler.getInstance().reActivateGuidelinesEntry(courseId, year, userId);
			if (isReactivate) {
				ActionMessage message = new ActionMessage(CMSConstants.ADMIN_GUIDELINESENTRY_REACTIVATE_SUCCESS);
				messages.add(CMSConstants.MESSAGES, message);
				guidelinesEntryForm.clear();
				assignListToForm(guidelinesEntryForm);
				saveMessages(request, messages);
			} else {
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMIN_GUIDELINESENTRY_REACTIVATE_FAILED));
				assignListToForm(guidelinesEntryForm);
				guidelinesEntryForm.clear();
				saveErrors(request, errors);
			}
			}
		} catch (Exception e) {
			if (e instanceof ApplicationException) {
				log.error("Error occured in Guidelines Entry Action -- reActivateGuidelinesEntry",e);
				String msg = super.handleApplicationException(e);
				guidelinesEntryForm.setErrorMessage(msg);
				guidelinesEntryForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}else {
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMIN_GUIDELINESENTRY_REACTIVATE_FAILED));
			}
		}
		return mapping.findForward(CMSConstants.INIT_GUIDELINESENTRY);
	}
	
	
/**
 * 	
 * @param form
 * Assigns the list to form while displaying in UI (view part)
 * @throws Exception
 */
	
	public void assignListToForm(ActionForm form) throws Exception
	{	
		GuidelinesEntryForm guidelinesEntryForm = (GuidelinesEntryForm) form;		
			List<GuidelinesEntryTO> guidelinesEntryList = GuidelinesEntryHandler.getInstance().getGuidelinesEntryAll();			
			guidelinesEntryForm.setGuideLinesDetails(guidelinesEntryList);		
			log.info("End of assignListToForm");	
	}
	/**
	 * Method to set all active programs to the session scope
	 */
	public void setProgramMapToRequest(HttpServletRequest request,
			GuidelinesEntryForm guidelinesEntryForm) throws Exception{
		if (guidelinesEntryForm.getProgramType() != null && (!guidelinesEntryForm.getProgramType().equals(""))) {
			Map<Integer,String> programMap = CommonAjaxHandler.getInstance().getProgramsByProgramType(Integer.parseInt(guidelinesEntryForm.getProgramType()));
			HttpSession session=request.getSession(false);
			session.setAttribute("programMap", programMap);			
		}
	}
	/**
	 * Method to set all active courses to the session scope
	 */
	public void setCourseMapToRequest(HttpServletRequest request,
			GuidelinesEntryForm guidelinesEntryForm)throws Exception {
		if (guidelinesEntryForm.getProgram() != null && (!guidelinesEntryForm.getProgram().equals(""))) {
			Map<Integer,String> courseMap = CommonAjaxHandler.getInstance().getCourseByProgram(Integer.parseInt(guidelinesEntryForm.getProgram()));
			HttpSession session=request.getSession(false);
			session.setAttribute("courseMap", courseMap);			
		}
	}
}
