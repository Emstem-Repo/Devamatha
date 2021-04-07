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
import com.kp.cms.forms.phd.PhdGuideDetailsForm;
import com.kp.cms.handlers.phd.PhdGuideDetailsHandler;
import com.kp.cms.to.phd.PhdGuideDetailsTO;
import com.kp.cms.utilities.CommonUtil;

@SuppressWarnings("deprecation")
public class PhdGuideDetailsAction extends BaseDispatchAction {
	private static Log log = LogFactory.getLog(PhdGuideDetailsAction.class);
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
	public ActionForward initGuideDetails(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) 
	       throws Exception {
		   PhdGuideDetailsForm objForm = (PhdGuideDetailsForm) form;
		   objForm.clearPage();
		   setPhdGuideDetails(objForm);
		  return mapping.findForward(CMSConstants.PHD_GUIDE_DETAILS);
	}
	/**
	 * @param objForm
	 * @throws Exception
	 */
	private void setPhdGuideDetails(PhdGuideDetailsForm objForm) throws Exception{
		List<PhdGuideDetailsTO> synopsis=PhdGuideDetailsHandler.getInstance().getPhdGuideDetails(objForm);		
		objForm.setGuideDetails(synopsis);
	}
	/**
	 * @param mappingobjForm.getExamType()
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addGuideDetails(ActionMapping mapping,ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		log.info("call of addSubCategory method in InvSubCategogyAction class.");
		PhdGuideDetailsForm objForm = (PhdGuideDetailsForm) form;
		setUserId(request,objForm);
		HttpSession session=request.getSession();
		ActionMessages messages = new ActionMessages();
		 ActionErrors errors = objForm.validate(mapping, request);
		validateEmailPhone(objForm,errors);
		if (errors.isEmpty()) {
			try {
				boolean isAdded = false;
				boolean isDuplicate=PhdGuideDetailsHandler.getInstance().duplicateCheck(objForm,errors,session);
				if(!isDuplicate){
				isAdded = PhdGuideDetailsHandler.getInstance().addGuideDetails(objForm,"Add");
				if (isAdded) {
					messages.add(CMSConstants.MESSAGES, new ActionMessage( "knowledgepro.phd.guidedetails.addsuccess"));
					saveMessages(request, messages);
					objForm.clearPage();
			 } else {
					errors.add("error", new ActionError( "knowledgepro.phd.guidedetails.addfailure"));
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
			return mapping.findForward(CMSConstants.PHD_GUIDE_DETAILS);
		}
		setPhdGuideDetails(objForm);
		log.info("end of AddValuatorCharges method in ValuatorChargesAction class.");
		return mapping.findForward(CMSConstants.PHD_GUIDE_DETAILS);
	}
	 private void validateEmailPhone(PhdGuideDetailsForm objForm,ActionErrors errors) {
		 
		    if(objForm.getEmail()!= null && !StringUtils.isEmpty(objForm.getEmail()) && !CommonUtil.validateEmailID(objForm.getEmail())){
		    	if (errors.get(CMSConstants.EMPLOYEE_VALID_EMAIL) != null && !errors.get(CMSConstants.EMPLOYEE_VALID_EMAIL).hasNext()) {
					errors.add(CMSConstants.EMPLOYEE_VALID_EMAIL,new ActionError(CMSConstants.EMPLOYEE_VALID_EMAIL));
				}
		    }
		    
		    if(objForm.getMobileNo()!=null && !StringUtils.isEmpty(objForm.getMobileNo()) && objForm.getMobileNo().trim().length()!=10){
		    	if (errors.get(CMSConstants.EMPLOYEE_MOBILE_NO_WRONG) != null && !errors.get(CMSConstants.EMPLOYEE_MOBILE_NO_WRONG).hasNext()) {
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
	public ActionForward editGuideDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
     {
		PhdGuideDetailsForm objForm = (PhdGuideDetailsForm) form;
		/*ActionErrors errors = new ActionErrors();
		errors = objForm.validate(mapping, request);*/
		log.debug("Entering ValuatorCharges ");
		try {
			PhdGuideDetailsHandler.getInstance().editGuideDetails(objForm);
			request.setAttribute("GuideDetails", "edit");
			log.debug("Leaving editValuatorCharges ");
		} catch (Exception e) {
			log.error("error in editing ValuatorCharges...", e);
			String msg = super.handleApplicationException(e);
			objForm.setErrorMessage(msg);
			objForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.PHD_GUIDE_DETAILS);
	}
	
	
	 /**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateGuideDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
     throws Exception
 {
		log.debug("Enter: updatevaluatorCharges Action");
		PhdGuideDetailsForm objForm = (PhdGuideDetailsForm) form;
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
				PhdGuideDetailsHandler.getInstance().editGuideDetails(objForm);
				request.setAttribute("GuideDetails", "edit");
				return mapping.findForward(CMSConstants.PHD_GUIDE_DETAILS);
			}
			setUserId(request, objForm); // setting user id to update
			boolean isDuplicate=PhdGuideDetailsHandler.getInstance().duplicateCheck(objForm,errors,session);
			if(!isDuplicate){
				isUpdated = PhdGuideDetailsHandler.getInstance().updateGuideDetails(objForm,"Edit");
			if (isUpdated) {
				ActionMessage message = new ActionMessage("knowledgepro.phd.guidedetails.update.success");
				messages.add("messages", message);
				saveMessages(request, messages);
				objForm.clearPage();
				
			} else {
				errors.add("error", new ActionError("knowledgepro.phd.guidedetails.update.failed"));
				//saveErrors(request, errors);
				addErrors(request, errors);
				objForm.clear();
			}}
			else{
				request.setAttribute("GuideDetails", "edit");
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
			setPhdGuideDetails(objForm);
			request.setAttribute("GuideDetails", "edit");
			return mapping.findForward(CMSConstants.PHD_GUIDE_DETAILS);
		}
        setPhdGuideDetails(objForm);
		log.debug("Exit: action class updatevaluatorCharges");
		return mapping.findForward(CMSConstants.PHD_GUIDE_DETAILS);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteGuideDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
	log.debug("Action class. Delete valuatorCharges ");
	PhdGuideDetailsForm objForm = (PhdGuideDetailsForm) form;
    ActionMessages messages = new ActionMessages();
    try
    {
        boolean isDeleted = PhdGuideDetailsHandler.getInstance().deleteGuideDetails(objForm);
        if(isDeleted)
        {
            ActionMessage message = new ActionMessage("knowledgepro.phd.guidedetails.deletesuccess");
            messages.add("messages", message);
            saveMessages(request, messages);
        } else
        {
            ActionMessage message = new ActionMessage("knowledgepro.phd.guidedetails.deletefailure");
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
    setPhdGuideDetails(objForm);
    log.debug("Action class. Delete valuatorCharges ");
    return mapping.findForward(CMSConstants.PHD_GUIDE_DETAILS);
}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward reactivateGuideDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering ReactivateValuatorCharges Action");
		PhdGuideDetailsForm objForm = (PhdGuideDetailsForm) form;
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
			isReactivate =  PhdGuideDetailsHandler.getInstance().reactivateGuideDetails(objForm,userId);
			if(isReactivate){
				messages.add(CMSConstants.MESSAGES,new ActionMessage("knowledgepro.phd.guidedetails.activate.success"));
				setPhdGuideDetails(objForm);
				saveMessages(request, messages);
				objForm.clearPage();
			}
			else{
				setPhdGuideDetails(objForm);
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.phd.guidedetails.activate.failed"));
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
		return mapping.findForward(CMSConstants.PHD_GUIDE_DETAILS); 
	}
}
