package com.kp.cms.actions.phd;

import java.util.List;

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
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.phd.PhdCompletionDetailsForm;
import com.kp.cms.forms.phd.PhdStudyAggrementForm;
import com.kp.cms.handlers.phd.PhdCompletionDetailsHandler;
import com.kp.cms.handlers.phd.PhdStudyAggrementHandler;
import com.kp.cms.to.phd.PhdCompletionDetailsTO;

@SuppressWarnings("deprecation")
public class PhdCompletionDetailsAction extends BaseDispatchAction {
	
	private static Log log = LogFactory.getLog(PhdCompletionDetailsAction.class);
	ActionErrors errors = new ActionErrors();
	ActionMessages messages = new ActionMessages();
	
	public ActionForward initPhdCompletionDetails(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws Exception {
	PhdCompletionDetailsForm objForm = (PhdCompletionDetailsForm) form;
	   objForm.clearPage();
	   setUserId(request, objForm);
	  return mapping.findForward(CMSConstants.PHD_COMPLETION_DETAILS);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward searchStudentList(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) 
    throws Exception {
		log.info("Entering searchStudentList of PhdCompletionDetailsAction");
		PhdCompletionDetailsForm objForm = (PhdCompletionDetailsForm) form;
		ActionMessages messages = new ActionMessages();
		objForm.setStudentDetails(null);
		ActionErrors errors = objForm.validate(mapping, request);
		if (errors.isEmpty()) {
	try {
			setUserId(request, objForm);
			setStudentDetails(objForm,request,errors);
		  
		} catch (BusinessException businessException) {
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			objForm.setErrorMessage(msg);
			objForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		} else {
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.PHD_COMPLETION_DETAILS);
		}
		log.info("Leaving searchStudentList of PhdCompletionDetailsAction");
		saveErrors(request, errors);
		return mapping.findForward(CMSConstants.PHD_COMPLETION_DETAILS);
	}
	
	private void setStudentDetails(PhdCompletionDetailsForm objForm,HttpServletRequest request, ActionErrors errors) throws Exception{
		List<PhdCompletionDetailsTO> complete=PhdCompletionDetailsHandler.getInstance().getStudentDetails(objForm,errors);
		if(complete==null || complete.isEmpty()){
			errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.cancelattendance.norecord"));
			addErrors(request, errors);
		}
		objForm.setStudentDetails(complete);
	}
	
	public ActionForward addCompletionDetails(ActionMapping mapping,ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		log.info("call of addCompletionDetails method in PhdCompletionDetailsAction class.");
		PhdCompletionDetailsForm objForm = (PhdCompletionDetailsForm) form;
		setUserId(request,objForm);
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = objForm.validate(mapping, request);
		HttpSession session=request.getSession();
		if (errors.isEmpty()) {
			try {
				boolean isAdded = false;
				isAdded = PhdCompletionDetailsHandler.getInstance().addCompletionDetails(objForm,errors,session);
				if (isAdded) {
					messages.add(CMSConstants.MESSAGES, new ActionMessage( "knowledgepro.phd.PhdCompletionDetails.addsuccess"));
					saveMessages(request, messages);
			 }else{ if(errors==null || errors.isEmpty()){
				 errors.add("error", new ActionError("knowledgepro.norecords"));
			 }}
				}
			catch (Exception exception) {
				log.error("Error occured in addCompletionDetails Action", exception);
				String msg = super.handleApplicationException(exception);
				objForm.setErrorMessage(msg);
				objForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			saveErrors(request, errors);
			objForm.clearPage();
			return mapping.findForward(CMSConstants.PHD_COMPLETION_DETAILS);
		}
		log.info("end of addCompletionDetails method in PhdCompletionDetailsAction class.");
		saveErrors(request, errors);
		if(errors==null || errors.isEmpty()){
		objForm.clearPage();
		}
		return mapping.findForward(CMSConstants.PHD_COMPLETION_DETAILS);
	}
	
	public ActionForward deleteCompletionDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
	log.debug("Action class. Delete CompletionDetails ");
	PhdCompletionDetailsForm objForm = (PhdCompletionDetailsForm) form;
    ActionMessages messages = new ActionMessages();
    try
    {
        boolean isDeleted = PhdCompletionDetailsHandler.getInstance().deleteCompletionDetails(objForm);
        if(isDeleted)
        {
            ActionMessage message = new ActionMessage("knowledgepro.phd.PhdCompletionDetails.deletesuccess");
            messages.add("messages", message);
            saveMessages(request, messages);
        } else
        {
            ActionMessage message = new ActionMessage("knowledgepro.phd.PhdCompletionDetails.deletefailure");
            messages.add("messages", message);
            saveMessages(request, messages);
        }
    }
    catch(Exception e)
    {
        log.error("error delete CompletionDetails...", e);
        if(e instanceof ApplicationException)
        {
            String msg = super.handleApplicationException(e);
            objForm.setErrorMessage(msg);
            objForm.setErrorStack(e.getMessage());
            return mapping.findForward(CMSConstants.ERROR_PAGE);
        } else
        {
            String msg = super.handleApplicationException(e);
            objForm.setErrorMessage(msg);
            objForm.setErrorStack(e.getMessage());
            return mapping.findForward(CMSConstants.ERROR_PAGE);
        }
    }
    setStudentDetails(objForm,request,errors);
	objForm.clearPage1();
    log.debug("end of Action class. Delete CompletionDetails ");
    return mapping.findForward(CMSConstants.PHD_COMPLETION_DETAILS);
	}
	
	public ActionForward reactivateCompletionDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering ReactivateCompletionDetails Action");
		PhdCompletionDetailsForm objForm = (PhdCompletionDetailsForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		HttpSession session=request.getSession();
		try {
			setUserId(request, objForm);
			boolean isReactivate;
			String userId = objForm.getUserId();
			String duplicateId=session.getAttribute("ReactivateId").toString();
			objForm.setId(Integer.parseInt(duplicateId));
			isReactivate =  PhdCompletionDetailsHandler.getInstance().reactivateCompletionDetails(objForm,userId);
			if(isReactivate){
				messages.add(CMSConstants.MESSAGES,new ActionMessage("knowledgepro.phd.PhdCompletionDetails.activate.success"));
				saveMessages(request, messages);
			}
			else{
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.phd.PhdCompletionDetails.activate.failed"));
				addErrors(request, errors);
			}
			
		} catch (Exception e) {
			log.error("Error occured in reactivateCompletionDetails of CompletionDetailsAction", e);
			String msg = super.handleApplicationException(e);
			objForm.setErrorMessage(msg);
			objForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		setStudentDetails(objForm,request,errors);
		log.info("Leaving into reactivateCompletionDetails of CompletionDetailsAction");
		return mapping.findForward(CMSConstants.PHD_COMPLETION_DETAILS); 
	}
	
}
