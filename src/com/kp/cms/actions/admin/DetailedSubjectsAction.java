package com.kp.cms.actions.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.admin.DetailedSubjectsForm;
import com.kp.cms.handlers.admin.DetailedSubjectsHandler;
import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.to.admin.DetailedSubjectsTO;
import com.kp.cms.to.admin.ProgramTypeTO;

/**
 * 
 * An action class for DetailedSubjectAction
 * Used in admission module to choose subject from dropdown.
 *
 */
@SuppressWarnings("deprecation")
public class DetailedSubjectsAction extends BaseDispatchAction {
	private static Log log = LogFactory.getLog(DetailedSubjectsAction.class);

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request Initializes Detailed subjects Entry and displays all the records where isActive=1 in UI
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initDetailedSubjects(ActionMapping mapping,
													ActionForm form, HttpServletRequest request,
													HttpServletResponse response) throws Exception {
		
		log.info("Inside of initDetailedSubjects");
		DetailedSubjectsForm detailedSubjectForm = (DetailedSubjectsForm) form;		
		//It use for Help,Don't Remove
		HttpSession session=request.getSession();
		session.setAttribute("field","DetailedSubjects");
		try {
			setUserId(request, detailedSubjectForm);
			assignListToForm(detailedSubjectForm);
			detailedSubjectForm.clear();
		} catch (Exception e) {
			log.error("error occured in initDetailedSubjects of DetailedSubjectsAction class.",e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				detailedSubjectForm.setErrorMessage(msg);
				detailedSubjectForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}else {
				String msg = super.handleApplicationException(e);
				detailedSubjectForm.setErrorMessage(msg);
				detailedSubjectForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}
		log.info("Leaving into initDetailedSubjects");
		return mapping.findForward(CMSConstants.DETAILED_SUBJECT_ENTRY);
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response Adds a detailed entry for a particular course.
	 * @return
	 * @throws Exception
	 */

	public ActionForward addDetailedSubjectEntry(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Enters into addGuidelinesEntry");
		DetailedSubjectsForm detailedSubjectForm = (DetailedSubjectsForm) form;
		ActionErrors errors = detailedSubjectForm.validate(mapping, request);
		ActionMessages messages = new ActionMessages();
		
		try {
			if (errors.isEmpty()) {
				// the below method will throws exception like duplicate, reactivate.
				DetailedSubjectsHandler.getInstance().checkForDuplicate(detailedSubjectForm);
				DetailedSubjectsHandler.getInstance().addDetailedSubjects(detailedSubjectForm);
							
				ActionMessage message = new ActionMessage(CMSConstants.ADMIN_DETAILSUBJECT_ADD_SUCCESS);
				messages.add("messages", message);
				saveMessages(request, messages);
				detailedSubjectForm.clear();
				assignListToForm(detailedSubjectForm);
			} else {
				saveErrors(request, errors);
			}
		} catch (DuplicateException e1) {
	    	errors.add("error", new ActionError(CMSConstants.ADMIN_DETAILEDSUBJET_DUPLICATE));
    		saveErrors(request,errors);
	    } catch (ReActivateException e1) {
	    	errors.add("error", new ActionError(CMSConstants.ADMIN_DETAILEDSUBJET_REACTIVATE));
    		saveErrors(request,errors);
	    } catch (Exception e) {
	    	log.debug("Action :addDetailedSubjectEntry exception occured");
	    	errors.add("error", new ActionError(CMSConstants.ADMIN_DETAILSUBJECT_ADD_FAILURE));
    		saveErrors(request,errors);
		}
	    log.info("Action : Leaving addGuidelinesEntry");
	    return mapping.findForward(CMSConstants.DETAILED_SUBJECT_ENTRY);
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * 			This action method called when delete of detailed subject.
	 * @return       
	 * @throws Exception
	 */
	public ActionForward deleteDetailedSubject(
		    ActionMapping mapping,
		    ActionForm form,
		    HttpServletRequest request,
		    HttpServletResponse response) throws Exception{

		log.debug("Action :Entering deleteDetailedSubject");
		DetailedSubjectsForm detailedSubjectsForm = (DetailedSubjectsForm)form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		try {
			if(detailedSubjectsForm.getId() != null) {
				DetailedSubjectsHandler.getInstance().deleteDetailedSubject(detailedSubjectsForm);
				ActionMessage message = new ActionMessage(CMSConstants.ADMIN_DETAILEDSUBJET_DELETE_SUCCESS);
				messages.add("messages", message);
				saveMessages(request, messages);
				detailedSubjectsForm.clear();
				assignListToForm(detailedSubjectsForm);
			}
		} catch (Exception e) {
		log.debug("Action :deleteDetailedSubject exception occured");
		errors.add("error", new ActionError(CMSConstants.ADMIN_DETAILEDSUBJET_DELETE_FAILURE));
		saveErrors(request,errors);
		}
		log.debug("Action :Leaving deleteDetailedSubject");
		return mapping.findForward(CMSConstants.DETAILED_SUBJECT_ENTRY);	
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * 			This method called when update clicked. 
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateDetailedSubjectEntry(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Enters into updateDetailedSubjectEntry");
		DetailedSubjectsForm detailedSubjectForm = (DetailedSubjectsForm) form;
		ActionErrors errors = detailedSubjectForm.validate(mapping, request);
		ActionMessages messages = new ActionMessages();
		request.setAttribute("detailedSubjectsOperation", "add");
		try {
			if (isCancelled(request)) {
				DetailedSubjectsHandler.getInstance().getDetailedSubjects(detailedSubjectForm);
				setOptionsDataInEditMode(detailedSubjectForm,request);
				request.setAttribute("detailedSubjectsOperation","edit");
				return mapping.findForward(CMSConstants.DETAILED_SUBJECT_ENTRY);
			}
			if (errors.isEmpty()) {
				boolean isCheckrequired = DetailedSubjectsHandler.getInstance().isDuplicateCheckRequired(detailedSubjectForm);
				if(isCheckrequired) {
					DetailedSubjectsHandler.getInstance().checkForDuplicate(detailedSubjectForm);
					DetailedSubjectsHandler.getInstance().updateDetailedSubjects(detailedSubjectForm);
					ActionMessage message = new ActionMessage(CMSConstants.ADMIN_DETAILEDSUBJET_UPDATE_SUCCESS);
					messages.add("messages", message);
					saveMessages(request, messages);
					detailedSubjectForm.clear();
					assignListToForm(detailedSubjectForm);
				} else if(!isCheckrequired){
					ActionMessage message = new ActionMessage(CMSConstants.ADMIN_DETAILEDSUBJET_UPDATE_SUCCESS);
					messages.add("messages", message);
					saveMessages(request, messages);
					detailedSubjectForm.clear();
					assignListToForm(detailedSubjectForm);
				}
		} else {
			log.info("Leaving  from addGuidelinesEntry");
			request.setAttribute("detailedSubjectsOperation","edit");
			setOptionsDataInEditMode(detailedSubjectForm,request);
			saveErrors(request, errors);
		}
		} catch (DuplicateException e1) {
			request.setAttribute("detailedSubjectsOperation","edit");
	    	errors.add("error", new ActionError(CMSConstants.ADMIN_DETAILEDSUBJET_DUPLICATE));
	    	setOptionsDataInEditMode(detailedSubjectForm,request);
    		saveErrors(request,errors);
	    } catch (ReActivateException e1) {
	    	request.setAttribute("detailedSubjectsOperation","edit");
	    	errors.add("error", new ActionError(CMSConstants.ADMIN_DETAILEDSUBJET_REACTIVATE));
    		saveErrors(request,errors);
    		setOptionsDataInEditMode(detailedSubjectForm,request);
	    } catch (Exception e) {
	    	log.debug("Action :updateDetailedSubjectEntry exception occured");
	    	errors.add("error", new ActionError(CMSConstants.ADMIN_DETAILEDSUBJET_UPDATE_FAILURE));
	    	setOptionsDataInEditMode(detailedSubjectForm,request);
    		saveErrors(request,errors);
		}
	    log.debug("Action :Leaving updateDetailedSubjectEntry");
	return mapping.findForward(CMSConstants.DETAILED_SUBJECT_ENTRY);
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * 			This is invoked when activation need.  
	 * @return
	 * @throws Exception
	 */
	public ActionForward activateDetailedSubject(
		    ActionMapping mapping,
		    ActionForm form,
		    HttpServletRequest request,
		    HttpServletResponse response) throws Exception{

		log.debug("Action :Entering activateDetailedSubject");
		DetailedSubjectsForm detailedSubjectsForm = (DetailedSubjectsForm)form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		try {
			if(detailedSubjectsForm.getActivationId() != null) {
				DetailedSubjectsHandler.getInstance().activateDetailedSubject(detailedSubjectsForm);
				ActionMessage message = new ActionMessage(CMSConstants.ADMIN_DETAILEDSUBJET_ACTIVATE_SUCCESS);
				messages.add("messages", message);
				saveMessages(request, messages);
				detailedSubjectsForm.clear();
				assignListToForm(detailedSubjectsForm);
			}
		} catch (Exception e) {
		log.debug("Action :activateDetailedSubject with exception occured");
		errors.add("error", new ActionError(CMSConstants.ADMIN_DETAILEDSUBJET_ACTIVATE_FAILURE));
		saveErrors(request,errors);
		}
		log.debug("Action :exiting activateDetailedSubject");
	return mapping.findForward(CMSConstants.DETAILED_SUBJECT_ENTRY);	
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * 			This method loads the particular detailed subject.
	 * @return
	 * @throws Exception
	 */
	public ActionForward editDetailedSubject(
		    ActionMapping mapping,
		    ActionForm form,
		    HttpServletRequest request,
		    HttpServletResponse response) throws Exception{

		log.debug("Action :Entering editDetailedSubject");
		DetailedSubjectsForm detailedSubjectsForm = (DetailedSubjectsForm)form;
		ActionErrors errors = new ActionErrors();
		try {
			DetailedSubjectsHandler.getInstance().getDetailedSubjects(detailedSubjectsForm);
			setOptionsDataInEditMode(detailedSubjectsForm,request);
			request.setAttribute("detailedSubjectsOperation","edit");
		} catch (Exception e) {
		log.debug("Action :editDetailedSubject exception occured");
		errors.add("error", new ActionError(CMSConstants.ADMIN_DETAILEDSUBJET_DELETE_FAILURE));
		saveErrors(request,errors);
		}
		log.debug("Action :Leaving editDetailedSubject");
		return mapping.findForward(CMSConstants.DETAILED_SUBJECT_ENTRY);	
	}
	/**
	 * 	
	 * @param form
	 * Assigns the list to form while displaying in UI (view part)
	 * @throws Exception
	 */
	
	public void assignListToForm(DetailedSubjectsForm detailedSubjectForm) throws Exception
	{	
		log.info("Inside of assignListToForm");
		
			List<DetailedSubjectsTO> detailedSubjectLists=DetailedSubjectsHandler.getInstance().getDetailedsubjects();
			detailedSubjectForm.setDetailedSubjectLists(detailedSubjectLists);
			log.info("End of assignListToForm");
	}	
	
	/*
	 * 
	 */
	public void setOptionsDataInEditMode(DetailedSubjectsForm detailedSubjectForm,HttpServletRequest request) throws Exception {
		Map<Integer,String> programMap = new HashMap<Integer,String>();
		Map<Integer,String> courseMap = new HashMap<Integer,String>();
		List<ProgramTypeTO> programTypeList = ProgramTypeHandler.getInstance().getProgramType();
		request.setAttribute("programTypeList", programTypeList);
		if(detailedSubjectForm.getProgramType().length() != 0 )
			programMap = CommonAjaxHandler.getInstance().getProgramsByProgramType(Integer.parseInt(detailedSubjectForm.getProgramType()));
		if(detailedSubjectForm.getProgram().length() != 0)
			courseMap = CommonAjaxHandler.getInstance().getCourseByProgram(Integer.parseInt(detailedSubjectForm.getProgram()));
		request.setAttribute("programMap", programMap);
		request.setAttribute("courseMap", courseMap);
	}	
}
