package com.kp.cms.actions.admission;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.bo.admin.CertificateCourseTeacher;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.admission.ApplnMandatoryForm;
import com.kp.cms.handlers.admission.ApplnMandatoryHandler;
import com.kp.cms.to.admission.ApplnMandatoryTO;

public class ApplnMandatoryAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(ApplnMandatoryAction.class);
	
	
	public ActionForward initApplnFormMandatory(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		ApplnMandatoryForm applnMandatoryForm = (ApplnMandatoryForm)form;
		try{
			setdataToForm(applnMandatoryForm);
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error("Error occured in ApplnMandatoryFieldsAction", e);
			String msg = super.handleApplicationException(e);
			applnMandatoryForm.setErrorMessage(msg);
			applnMandatoryForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
			
		}
		return mapping.findForward(CMSConstants.INIT_APPLN_MANDATORY_FIELD_ENTRY);
	}
	
	public ActionForward updateMandatoryField(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		ApplnMandatoryForm applnMandatoryForm = (ApplnMandatoryForm)form;
		ActionMessages messages = new ActionMessages();
		boolean isUpdated;
		try{
			
			isUpdated = ApplnMandatoryHandler.getInstance().updateMandatory(applnMandatoryForm);
			setdataToForm(applnMandatoryForm);
		}catch (Exception e) {
			e.printStackTrace();
			log.error("Error occured in ApplnMandatoryFieldsAction", e);
			String msg = super.handleApplicationException(e);
			applnMandatoryForm.setErrorMessage(msg);
			applnMandatoryForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
			
		}
		if(isUpdated){
			ActionMessage message = new ActionMessage("knowledgepro.admin.application.form.mandatory.field.updatesuccess");
			messages.add("messages", message);
			saveMessages(request, messages);
		}
		return mapping.findForward(CMSConstants.INIT_APPLN_MANDATORY_FIELD_ENTRY);
	}
	
	public void setdataToForm(ApplnMandatoryForm applnMandatoryForm) throws Exception{
		List<ApplnMandatoryTO> list = ApplnMandatoryHandler.getInstance().getList(); 
		applnMandatoryForm.setList(list);
	}
}
