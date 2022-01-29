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
import com.kp.cms.forms.phd.PhdStudyAggrementForm;
import com.kp.cms.handlers.phd.PhdStudyAggrementHandler;
import com.kp.cms.to.phd.PhdGuideDetailsTO;
import com.kp.cms.to.phd.PhdStudyAggrementTO;

@SuppressWarnings("deprecation")
public class PhdStudyAggrementAction extends BaseDispatchAction {
	private static Log log = LogFactory.getLog(PhdStudyAggrementAction.class);
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
	public ActionForward initPhdStudyAgreement(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) 
	       throws Exception {
		PhdStudyAggrementForm objForm = (PhdStudyAggrementForm) form;
		   objForm.clearPage();
		   setUserId(request, objForm);
		  return mapping.findForward(CMSConstants.PHD_STUDY_AGGREMENT);
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
		PhdStudyAggrementForm objForm = (PhdStudyAggrementForm) form;
		ActionMessages messages = new ActionMessages();
		objForm.setStudentDetails(null);
		ActionErrors errors = objForm.validate(mapping, request);
		if (errors.isEmpty()) {
	try {
			setUserId(request, objForm);
			setGuideDetails(request);
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
			return mapping.findForward(CMSConstants.PHD_STUDY_AGGREMENT);
		}
		log.info("Leaving setClassEntry");
		saveErrors(request, errors);
		return mapping.findForward(CMSConstants.PHD_STUDY_AGGREMENT);
	}
	/**
	 * @param request
	 * @throws Exception
	 */
	private void setGuideDetails(HttpServletRequest request) throws Exception{
		List<PhdGuideDetailsTO> listValues=PhdStudyAggrementHandler.getInstance().setGuideDetails();
        request.getSession().setAttribute("GuideDetails", listValues);
}
	/**
	 * @param objForm
	 * @param request 
	 * @param errors
	 * @throws Exception
	 */
	private void setStudentDetails(PhdStudyAggrementForm objForm,HttpServletRequest request, ActionErrors errors) throws Exception{
		List<PhdStudyAggrementTO> study=PhdStudyAggrementHandler.getInstance().getStudentDetails(objForm,errors);
		if(study==null || study.isEmpty()){
			errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.cancelattendance.norecord"));
			addErrors(request, errors);
		}
		objForm.setStudentDetails(study);
	}
	/**
	 * @param mappingobjForm.getExamType()
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addStudyAggrement(ActionMapping mapping,ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		log.info("call of addSubCategory method in InvSubCategogyAction class.");
		PhdStudyAggrementForm objForm = (PhdStudyAggrementForm) form;
		setUserId(request,objForm);
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = objForm.validate(mapping, request);
		HttpSession session=request.getSession();
		if (errors.isEmpty()) {
			try {
				boolean isAdded = false;
				isAdded = PhdStudyAggrementHandler.getInstance().addStudyAggrement(objForm,errors,session);
				if (isAdded) {
					messages.add(CMSConstants.MESSAGES, new ActionMessage( "knowledgepro.phd.StudyAggrement.addsuccess"));
					saveMessages(request, messages);
			 }else{ if(errors==null || errors.isEmpty()){
				 errors.add("error", new ActionError("knowledgepro.norecords"));
			 }}
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
			objForm.clearPage();
			return mapping.findForward(CMSConstants.PHD_STUDY_AGGREMENT);
		}
		log.info("end of AddValuatorCharges method in ValuatorChargesAction class.");
		saveErrors(request, errors);
		if(errors==null || errors.isEmpty()){
		objForm.clearPage();
		}
		return mapping.findForward(CMSConstants.PHD_STUDY_AGGREMENT);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteStudyAggrement(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
	log.debug("Action class. Delete valuatorCharges ");
	PhdStudyAggrementForm objForm = (PhdStudyAggrementForm) form;
    ActionMessages messages = new ActionMessages();
    try
    {
        boolean isDeleted = PhdStudyAggrementHandler.getInstance().deleteStudyAggrement(objForm);
        if(isDeleted)
        {
            ActionMessage message = new ActionMessage("knowledgepro.phd.StudyAggrement.deletesuccess");
            messages.add("messages", message);
            saveMessages(request, messages);
        } else
        {
            ActionMessage message = new ActionMessage("knowledgepro.phd.StudyAggrement.deletefailure");
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
    setGuideDetails(request);
    setStudentDetails(objForm,request,errors);
	objForm.clearPage1();
    log.debug("Action class. Delete valuatorCharges ");
    return mapping.findForward(CMSConstants.PHD_STUDY_AGGREMENT);
}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward reactivateStudyAggrement(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering ReactivateValuatorCharges Action");
		PhdStudyAggrementForm objForm = (PhdStudyAggrementForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		HttpSession session=request.getSession();
		try {
			setUserId(request, objForm);
			boolean isReactivate;
			String userId = objForm.getUserId();
			String duplicateId=session.getAttribute("ReactivateId").toString();
			objForm.setId(Integer.parseInt(duplicateId));
			isReactivate =  PhdStudyAggrementHandler.getInstance().reactivateStudyAggrement(objForm,userId);
			if(isReactivate){
				messages.add(CMSConstants.MESSAGES,new ActionMessage("knowledgepro.phd.StudyAggrement.activate"));
				saveMessages(request, messages);
			}
			else{
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.phd.StudyAggrement.activate.failed"));
				addErrors(request, errors);
			}
			
		} catch (Exception e) {
			log.error("Error occured in reactivatevaluatorCharges of ValuatorChargesAction", e);
			String msg = super.handleApplicationException(e);
			objForm.setErrorMessage(msg);
			objForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		setGuideDetails(request);
		setStudentDetails(objForm,request,errors);
		log.info("Leaving into reactivatevaluatorCharges of ValuatorChargesAction");
		return mapping.findForward(CMSConstants.PHD_STUDY_AGGREMENT); 
	}
}
