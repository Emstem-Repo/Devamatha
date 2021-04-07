package com.kp.cms.actions.phd;



import java.util.List;

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
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.phd.PhdSynopsisDefenseForm;
import com.kp.cms.handlers.phd.PhdSynopsisDefenseHandler;
import com.kp.cms.to.phd.PhdSynopsisDefenseTO;
import com.kp.cms.utilities.CommonUtil;

@SuppressWarnings("deprecation")
public class PhdSynopsisDefenseAction extends BaseDispatchAction {
	private static Log log = LogFactory.getLog(PhdSynopsisDefenseAction.class);
	ActionErrors errors = new ActionErrors();
	ActionMessages messages = new ActionMessages();
	
	// gets initial list of Exam Definition
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initPhdSynopsisDefense(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) 
	       throws Exception {
		PhdSynopsisDefenseForm objForm = (PhdSynopsisDefenseForm) form;
		   objForm.clearPage();
		   objForm.clearregisterNo();
		   errors = objForm.validate(mapping, request);
		   setUserId(request, objForm);
		  return mapping.findForward(CMSConstants.PHD_SYNOPSIS_DEFENCE);
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

		log.info("Entering setClassEntry");
		PhdSynopsisDefenseForm objForm = (PhdSynopsisDefenseForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		objForm.clearPage();
		objForm.setStudentDetails(null);
	try {
	
			setUserId(request, objForm);
		    setStudentDetails(objForm, errors);
		    setPhdSynopsisdefence(objForm);
		
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
		log.info("Leaving setClassEntry");
		saveErrors(request, errors);
		return mapping.findForward(CMSConstants.PHD_SYNOPSIS_DEFENCE);
	}
	/**
	 * @param objForm
	 * @throws Exception
	 */
	private void setPhdSynopsisdefence(PhdSynopsisDefenseForm objForm) throws Exception{
		List<PhdSynopsisDefenseTO> synopsis=PhdSynopsisDefenseHandler.getInstance().getPhdSynopsisdefence(objForm);		
		objForm.setStudentDetails(synopsis);
	}


	/**
	 * @param objForm
	 * @param errors
	 * @throws Exception
	 */
	private void setStudentDetails(PhdSynopsisDefenseForm objForm,ActionErrors errors) throws Exception{
		 PhdSynopsisDefenseHandler.getInstance().getStudentDetails(objForm,errors);
	}
	/**
	 * @param mappingobjForm.getExamType()
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addSynopsisDefense(ActionMapping mapping,ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		log.info("call of addSubCategory method in InvSubCategogyAction class.");
		PhdSynopsisDefenseForm objForm = (PhdSynopsisDefenseForm) form;
		setUserId(request,objForm);
		ActionMessages messages = new ActionMessages();
		 ActionErrors errors = objForm.validate(mapping, request);
		validateEmailPhone(objForm,errors);
		HttpSession session=request.getSession();
		if (errors.isEmpty()) {
			try {
				boolean isAdded = false;
				boolean isDuplicate=PhdSynopsisDefenseHandler.getInstance().duplicateCheck(objForm,errors,session);
				if(!isDuplicate){
				isAdded = PhdSynopsisDefenseHandler.getInstance().addSynopsisDefense(objForm,"Add");
				if (isAdded) {
					messages.add(CMSConstants.MESSAGES, new ActionMessage( "knowledgepro.phd.SynopsisDefense.addsuccess"));
					saveMessages(request, messages);
					objForm.clearPage();
			 } else {
					errors.add("error", new ActionError( "knowledgepro.phd.SynopsisDefense.addfailure"));
					addErrors(request, errors);
			}}
				else{
					addErrors(request, errors);
				}			
				}
			catch (Exception exception) {
				log.error("Error occured in caste Entry Action", exception);
				String msg = super.handleApplicationException(exception);
				objForm.setErrorMessage(msg);
				objForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			saveErrors(request, errors);
			setPhdSynopsisdefence(objForm);
			return mapping.findForward(CMSConstants.PHD_SYNOPSIS_DEFENCE);
		}
		setPhdSynopsisdefence(objForm);
		log.info("end of AddValuatorCharges method in ValuatorChargesAction class.");
		return mapping.findForward(CMSConstants.PHD_SYNOPSIS_DEFENCE);
	}
	 private void validateEmailPhone(PhdSynopsisDefenseForm objForm,ActionErrors errors) {
		 
		    if(objForm.getEmail()!= null && !StringUtils.isEmpty(objForm.getEmail()) && !CommonUtil.validateEmailID(objForm.getEmail())){
		    	if (errors.get(CMSConstants.EMPLOYEE_VALID_EMAIL) != null && !errors.get(CMSConstants.EMPLOYEE_VALID_EMAIL).hasNext()) {
					errors.add(CMSConstants.EMPLOYEE_VALID_EMAIL,new ActionError(CMSConstants.EMPLOYEE_VALID_EMAIL));
				}
		    }
		    
		    if(objForm.getContactNo()!=null && !StringUtils.isEmpty(objForm.getContactNo()) && objForm.getContactNo().trim().length()!=10){
		    	if (errors.get(CMSConstants.EMPLOYEE_MOBILE_NO_WRONG) != null&& !errors.get(CMSConstants.EMPLOYEE_MOBILE_NO_WRONG).hasNext()) {
					errors.add(CMSConstants.EMPLOYEE_MOBILE_NO_WRONG,new ActionError(CMSConstants.EMPLOYEE_MOBILE_NO_WRONG));
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
	public ActionForward editSynopsisDefense(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
     {
		PhdSynopsisDefenseForm objForm = (PhdSynopsisDefenseForm) form;
		log.debug("Entering ValuatorCharges ");
		try {
			 PhdSynopsisDefenseHandler.getInstance().editSynopsisDefense(objForm);
			request.setAttribute("Synopsis", "edit");
			//request.setAttribute("Update", "Update");// setting update attribute
			log.debug("Leaving editValuatorCharges ");
		} catch (Exception e) {
			log.error("error in editing ValuatorCharges...", e);
			String msg = super.handleApplicationException(e);
			objForm.setErrorMessage(msg);
			objForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.PHD_SYNOPSIS_DEFENCE);
	}
	
	
	 /**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateSynopsisDefense(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
     throws Exception
 {
		log.debug("Enter: updatevaluatorCharges Action");
		PhdSynopsisDefenseForm objForm = (PhdSynopsisDefenseForm) form;
		HttpSession session=request.getSession();
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = objForm.validate(mapping, request);
		validateEmailPhone(objForm,errors);
		boolean isUpdated = false;
        if(errors.isEmpty()){
		try {
			// This condition works when reset button will click in update mode
			if (isCancelled(request)) {
				objForm.reset(mapping, request);
				String formName = mapping.getName();
				request.getSession().setAttribute(CMSConstants.FORMNAME,formName);
				PhdSynopsisDefenseHandler.getInstance().editSynopsisDefense(objForm);
				request.setAttribute("Synopsis", "edit");
				return mapping.findForward(CMSConstants.PHD_SYNOPSIS_DEFENCE);
			}
			setUserId(request, objForm); // setting user id to update
			boolean isDuplicate=PhdSynopsisDefenseHandler.getInstance().duplicateCheck(objForm,errors,session);
			if(!isDuplicate){
				isUpdated = PhdSynopsisDefenseHandler.getInstance().updateSynopsisDefense(objForm,"Edit");
			if (isUpdated) {
				ActionMessage message = new ActionMessage("knowledgepro.phd.SynopsisDefense.update.success");
				messages.add("messages", message);
				saveMessages(request, messages);
				objForm.clearPage();
				
			} else {
				errors.add("error", new ActionError("knowledgepro.phd.SynopsisDefense.update.failed"));
				//saveErrors(request, errors);
				addErrors(request, errors);
				objForm.clear();
			}}
			else{
				request.setAttribute("Synopsis", "edit");
				addErrors(request, errors);
				//invSubCategoryForm.reset();
			}
		} catch (Exception e) {
			log.error("Error occured in edit valuatorcharges", e);
			String msg = super.handleApplicationException(e);
			objForm.setErrorMessage(msg);
			objForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}}else{
			saveErrors(request, errors);
			setPhdSynopsisdefence(objForm);
			request.setAttribute("Synopsis", "edit");
			return mapping.findForward(CMSConstants.PHD_SYNOPSIS_DEFENCE);
		}
        setPhdSynopsisdefence(objForm);
		log.debug("Exit: action class updatevaluatorCharges");
		return mapping.findForward(CMSConstants.PHD_SYNOPSIS_DEFENCE);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteSynopsisDefense(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
	log.debug("Action class. Delete valuatorCharges ");
	PhdSynopsisDefenseForm objForm = (PhdSynopsisDefenseForm) form;
    ActionMessages messages = new ActionMessages();
    try
    {
        boolean isDeleted = PhdSynopsisDefenseHandler.getInstance().deleteSynopsisDefense(objForm);
        if(isDeleted)
        {
            ActionMessage message = new ActionMessage("knowledgepro.phd.SynopsisDefense.deletesuccess");
            messages.add("messages", message);
            saveMessages(request, messages);
        } else
        {
            ActionMessage message = new ActionMessage("knowledgepro.phd.SynopsisDefense.deletefailure");
            messages.add("messages", message);
            saveMessages(request, messages);
        }
             
    }
    catch(Exception e)
    {
        log.error("error submit valuatorCharges...", e);
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
    setPhdSynopsisdefence(objForm);
    log.debug("Action class. Delete valuatorCharges ");
    return mapping.findForward(CMSConstants.PHD_SYNOPSIS_DEFENCE);
}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward reactivateSynopsisDefense(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering ReactivateValuatorCharges Action");
		PhdSynopsisDefenseForm objForm = (PhdSynopsisDefenseForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		HttpSession session=request.getSession();
		try {
			setUserId(request, objForm);
			boolean isReactivate;
			//int olddocTypeId =Integer.parseInt(documentExamEntryForm.getDocTypeId());
			//String oldExamName = documentExamEntryForm.getExamName().trim();
			String userId = objForm.getUserId();
			String duplicateId=session.getAttribute("ReactivateId").toString();
			objForm.setId(Integer.parseInt(duplicateId));
			isReactivate =  PhdSynopsisDefenseHandler.getInstance().reactivateSynopsisDefense(objForm,userId);
			if(isReactivate){
				messages.add(CMSConstants.MESSAGES,new ActionMessage("knowledgepro.phd.SynopsisDefense.activate"));
				setPhdSynopsisdefence(objForm);
				saveMessages(request, messages);
				objForm.clearPage();
			}
			else{
				setPhdSynopsisdefence(objForm);
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.phd.SynopsisDefense.activate.failed"));
				addErrors(request, errors);
			}
			
		} catch (Exception e) {
			log.error("Error occured in reactivatevaluatorCharges of ValuatorChargesAction", e);
			String msg = super.handleApplicationException(e);
			objForm.setErrorMessage(msg);
			objForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Leaving into reactivatevaluatorCharges of ValuatorChargesAction");
		return mapping.findForward(CMSConstants.PHD_SYNOPSIS_DEFENCE); 
	}
}
