package com.kp.cms.actions.phd;



import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.kp.cms.forms.phd.PhdInternalGuideForm;
import com.kp.cms.handlers.phd.PhdInternalGuideHandler;
import com.kp.cms.to.phd.PhdInternalGuideTO;
import com.kp.cms.transactions.phd.IPhdDocumentSubmissionTransactions;
import com.kp.cms.transactionsimpl.phd.PhdDocumentSubmissionImpl;

/**
 * @author lenovo
 *
 */
@SuppressWarnings("deprecation")
public class PhdInternalGuideAction extends BaseDispatchAction {
	private static Log log = LogFactory.getLog(PhdInternalGuideAction.class);
	ActionErrors errors = new ActionErrors();
	ActionMessages messages = new ActionMessages();
	
	IPhdDocumentSubmissionTransactions trancations=new PhdDocumentSubmissionImpl();
	// gets initial list of Exam Definition
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initInternalGuide(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) 
	       throws Exception {
		   PhdInternalGuideForm objForm = (PhdInternalGuideForm) form;
		   objForm.setInternalGuideList(null);
		   objForm.clearPage();
		   initializaData(objForm);
		   setUserId(request, objForm);
		  return mapping.findForward(CMSConstants.PHD_INTERNAL_GUIDE);
	}
	
	
	/**
	 * @param objForm
	 * @throws Exception 
	 */
	private void initializaData(PhdInternalGuideForm objForm) throws Exception {
		PhdInternalGuideHandler.getInstance().initializaData(objForm);
	}


	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward searchPhdEmployeeDetails(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) 
    throws Exception {

		log.info("Entering setClassEntry");
		PhdInternalGuideForm objForm = (PhdInternalGuideForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		objForm.setInternalGuideList(null);
	try {
			setUserId(request, objForm);
			setInternalGuideDetails(request,objForm);
		  if(objForm.getInternalGuideList()==null || objForm.getInternalGuideList().isEmpty()){
			  errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.cancelattendance.norecord"));
				addErrors(request, errors);
		  }
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
		return mapping.findForward(CMSConstants.PHD_INTERNAL_GUIDE);
	}
	/**
	 * @param request
	 * @param objForm
	 * @throws Exception
	 */
	private void setInternalGuideDetails(HttpServletRequest request,PhdInternalGuideForm objForm) throws Exception{
		
		List<PhdInternalGuideTO> guideList=PhdInternalGuideHandler.getInstance().setInternalGuideDetails(objForm);
		objForm.setInternalGuideList(guideList);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward submitEmpDetails(ActionMapping mapping,ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		log.info("call of addSubCategory method in InvSubCategogyAction class.");
		PhdInternalGuideForm objForm = (PhdInternalGuideForm) form;
		ActionMessages messages = new ActionMessages();
		 ActionErrors errors = objForm.validate(mapping, request);
		setUserId(request,objForm);
		if (errors.isEmpty()) {
			try {
				boolean isAdded = false;
			
				isAdded = PhdInternalGuideHandler.getInstance().addPhdEmployee(objForm,errors);
				if (isAdded) {
					if(errors.isEmpty()){
					messages.add(CMSConstants.MESSAGES, new ActionMessage( "knowledgepro.phd.internalGuide.addsuccess"));
					saveMessages(request, messages);
					setInternalGuideDetails(request,objForm);
					objForm.clearPage();
					initializaData(objForm);
					}else{
						initializaData(objForm);
						saveErrors(request, errors);
						return mapping.findForward(CMSConstants.PHD_INTERNAL_GUIDE);
					}
			      }else{
			    	  initializaData(objForm);
					  errors.add("error", new ActionError("knowledgepro.phd.internalGuide.failure"));
					  saveErrors(request, errors);
					  return mapping.findForward(CMSConstants.PHD_INTERNAL_GUIDE);
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
			initializaData(objForm);
			return mapping.findForward(CMSConstants.PHD_INTERNAL_GUIDE);
		}
		log.info("end of AddValuatorCharges method in ValuatorChargesAction class.");
		saveErrors(request, errors);
		return mapping.findForward(CMSConstants.PHD_INTERNAL_GUIDE);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editPhdemployee(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
     {
		PhdInternalGuideForm objForm = (PhdInternalGuideForm) form;
		log.debug("Entering editPhdemployee ");
		try {
			PhdInternalGuideHandler.getInstance().editPhdemployee(objForm);
			request.setAttribute("empInternal", "edit");
			log.debug("Leaving editValuatorCharges ");
		} catch (Exception e) {
			log.error("error in editing ValuatorCharges...", e);
			String msg = super.handleApplicationException(e);
			objForm.setErrorMessage(msg);
			objForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.PHD_INTERNAL_GUIDE);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updatePhdEmployee(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
{
		log.debug("Enter: updatevaluatorCharges Action");
		PhdInternalGuideForm objForm = (PhdInternalGuideForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = objForm.validate(mapping, request);
		boolean isUpdated = false;
       if(errors.isEmpty()){
		try {
			if (isCancelled(request)) {
				objForm.reset(mapping, request);
				String formName = mapping.getName();
				request.getSession().setAttribute(CMSConstants.FORMNAME,formName);
				PhdInternalGuideHandler.getInstance().editPhdemployee(objForm);
				request.setAttribute("empInternal", "edit");
				return mapping.findForward(CMSConstants.PHD_INTERNAL_GUIDE);
			}
			setUserId(request, objForm); // setting user id to update
			isUpdated = PhdInternalGuideHandler.getInstance().addPhdEmployee(objForm,errors);
			if (isUpdated) {
				if(errors.isEmpty()){
				ActionMessage message = new ActionMessage("knowledgepro.phd.internalguide.update.success");
				messages.add("messages", message);
				saveMessages(request, messages);
				objForm.clearPage();
				setInternalGuideDetails(request,objForm);
				initializaData(objForm);
				}else{
					initializaData(objForm);
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.PHD_INTERNAL_GUIDE);
				}
			} else {
				errors.add("error", new ActionError("knowledgepro.phd.internal.update.failed"));
				addErrors(request, errors);
				objForm.clear();
				initializaData(objForm);
			}
			
		} catch (Exception e) {
			log.error("Error occured in edit valuatorcharges", e);
			String msg = super.handleApplicationException(e);
			objForm.setErrorMessage(msg);
			objForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}}else{
			saveErrors(request, errors);
			request.setAttribute("empInternal", "edit");
			return mapping.findForward(CMSConstants.PHD_INTERNAL_GUIDE);
		}
		log.debug("Exit: action class updatevaluatorCharges");
		return mapping.findForward(CMSConstants.PHD_INTERNAL_GUIDE);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deletePhdemployee(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
	log.debug("Action class. Delete valuatorCharges ");
	PhdInternalGuideForm objForm = (PhdInternalGuideForm) form;
    ActionMessages messages = new ActionMessages();
    try
    {
        boolean isDeleted = PhdInternalGuideHandler.getInstance().deletePhdemployee(objForm);
        if(isDeleted)
        {
            ActionMessage message = new ActionMessage("knowledgepro.phd.internal.deletesuccess");
            messages.add("messages", message);
            saveMessages(request, messages);
        } else
        {
            ActionMessage message = new ActionMessage("knowledgepro.phd.ginternal.deletefailure");
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
    setInternalGuideDetails(request,objForm);
    log.debug("Action class. Delete valuatorCharges ");
    return mapping.findForward(CMSConstants.PHD_INTERNAL_GUIDE);
}
}
