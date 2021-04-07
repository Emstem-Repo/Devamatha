package com.kp.cms.actions.phd;



import java.util.Calendar;
import java.util.Date;
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
import com.kp.cms.forms.phd.PhdSettingForm;
import com.kp.cms.handlers.phd.PhdSettingHandler;
import com.kp.cms.handlers.phd.PhdStudyAggrementHandler;
import com.kp.cms.to.phd.PhdGuideDetailsTO;
import com.kp.cms.to.phd.PhdSettingTO;
import com.kp.cms.utilities.CommonUtil;

@SuppressWarnings("deprecation")
public class PhdSettingAction extends BaseDispatchAction {
	private static Log log = LogFactory.getLog(PhdSettingAction.class);
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
	public ActionForward initPhdSettings(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) 
	       throws Exception {
		 PhdSettingForm objForm = (PhdSettingForm) form;
		   objForm.clearPage();
		   setUserId(request, objForm);
		   setDatatoForm(objForm);
		  return mapping.findForward(CMSConstants.PHD_SETTINGS);
	}
	
	
	/**
	 * @param objForm
	 * @throws Exception
	 */
	private void setDatatoForm(PhdSettingForm objForm) throws Exception {
		List<PhdSettingTO> setting=PhdSettingHandler.getInstance().getSettingDetails(objForm);
		objForm.setSettingDetails(setting);
		
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addPhdSettings(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) 
    throws Exception {
		log.info("call of addSubCategory method in InvSubCategogyAction class.");
		 PhdSettingForm objForm = (PhdSettingForm) form;
		setUserId(request,objForm);
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = objForm.validate(mapping, request);
		HttpSession session=request.getSession();
		if (errors.isEmpty()) {
			try {
				boolean isAdded = false;
				boolean isDuplicate=PhdSettingHandler.getInstance().duplicateCheck(objForm,errors,session);
				if(!isDuplicate){
				isAdded = PhdSettingHandler.getInstance().addSetting(objForm,"Add");
				if (isAdded) {
					messages.add(CMSConstants.MESSAGES, new ActionMessage( "knowledgepro.phd.Setting.addsuccess"));
					saveMessages(request, messages);
					objForm.clearPage();
			 } else {
					errors.add("error", new ActionError( "knowledgepro.phd.Setting.addfailure"));
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
			setDatatoForm(objForm);
			return mapping.findForward(CMSConstants.PHD_SETTINGS);
		}
		setDatatoForm(objForm);
		log.info("end of AddValuatorCharges method in ValuatorChargesAction class.");
		return mapping.findForward(CMSConstants.PHD_SETTINGS);
	}
	/**
	 * @param objForm
	 * @param request 
	 * @param errors
	 * @throws Exception
	 */
	public ActionForward editPhdSettings(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
     {
		PhdSettingForm objForm = (PhdSettingForm) form;
		log.debug("Entering ValuatorCharges ");
		try {
			 PhdSettingHandler.getInstance().editSetting(objForm);
			request.setAttribute("PhdSettings", "edit");
			log.debug("Leaving editValuatorCharges ");
		} catch (Exception e) {
			log.error("error in editing ValuatorCharges...", e);
			String msg = super.handleApplicationException(e);
			objForm.setErrorMessage(msg);
			objForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.PHD_SETTINGS);
	}
	/**
	 * @param mappingobjForm.getExamType()
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updatePhdSettings(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
{
		log.debug("Enter: updatevaluatorCharges Action");
		PhdSettingForm objForm = (PhdSettingForm) form;
		HttpSession session=request.getSession();
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = objForm.validate(mapping, request);
		boolean isUpdated = false;
       if(errors.isEmpty()){
		try {
			// This condition works when reset button will click in update mode
			if (isCancelled(request)) {
				objForm.reset(mapping, request);
				String formName = mapping.getName();
				request.getSession().setAttribute(CMSConstants.FORMNAME,formName);
				PhdSettingHandler.getInstance().editSetting(objForm);
				request.setAttribute("PhdSettings", "edit");
				return mapping.findForward(CMSConstants.PHD_SETTINGS);
			}
			setUserId(request, objForm); // setting user id to update
			boolean isDuplicate=PhdSettingHandler.getInstance().duplicateCheck(objForm,errors,session);
			if(!isDuplicate){
				isUpdated = PhdSettingHandler.getInstance().addSetting(objForm,"Edit");
			if (isUpdated) {
				ActionMessage message = new ActionMessage("knowledgepro.phd.Setting.update.success");
				messages.add("messages", message);
				saveMessages(request, messages);
				objForm.clearPage();
				
			} else {
				errors.add("error", new ActionError("knowledgepro.phd.Setting.update.failed"));
				//saveErrors(request, errors);
				addErrors(request, errors);
				objForm.clear();
			}}
			else{
				request.setAttribute("PhdSettings", "edit");
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
			setDatatoForm(objForm);
			request.setAttribute("PhdSettings", "edit");
			return mapping.findForward(CMSConstants.PHD_SETTINGS);
		}
        setDatatoForm(objForm);
		log.debug("Exit: action class updatevaluatorCharges");
		return mapping.findForward(CMSConstants.PHD_SETTINGS);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deletePhdSettings(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
	log.debug("Action class. Delete valuatorCharges ");
	PhdSettingForm objForm = (PhdSettingForm) form;
    ActionMessages messages = new ActionMessages();
    try
    {
        boolean isDeleted = PhdSettingHandler.getInstance().deleteSetting(objForm);
        if(isDeleted)
        {
            ActionMessage message = new ActionMessage("knowledgepro.phd.Setting.deletesuccess");
            messages.add("messages", message);
            saveMessages(request, messages);
        } else
        {
            ActionMessage message = new ActionMessage("knowledgepro.phd.Setting.deletefailure");
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
    setDatatoForm(objForm);
    log.debug("Action class. Delete valuatorCharges ");
    return mapping.findForward(CMSConstants.PHD_SETTINGS);
}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward reactivatePhdSettings(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering ReactivateValuatorCharges Action");
		PhdSettingForm objForm = (PhdSettingForm) form;
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
			isReactivate =  PhdSettingHandler.getInstance().reactivateSetting(objForm,userId);
			if(isReactivate){
				messages.add(CMSConstants.MESSAGES,new ActionMessage("knowledgepro.phd.Setting.activate"));
				setDatatoForm(objForm);
				saveMessages(request, messages);
				objForm.clearPage();
			}
			else{
				setDatatoForm(objForm);
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.phd.Setting.activate.failed"));
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
		return mapping.findForward(CMSConstants.PHD_SETTINGS); 
	}

}
