package com.kp.cms.actions.admin;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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
import com.kp.cms.actions.admission.StudentEditAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.admin.CertificateDetailsForm;
import com.kp.cms.forms.admin.CertificateDetailsForm;
import com.kp.cms.forms.admin.CertificateDetailsForm;
import com.kp.cms.forms.admin.TemplateForm;
import com.kp.cms.forms.admission.CertificateCourseEntryForm;
import com.kp.cms.forms.admission.StudentEditForm;
import com.kp.cms.handlers.admin.CertificateDetailsHandler;
import com.kp.cms.handlers.admin.CertificateDetailsHandler;
import com.kp.cms.handlers.admin.CertificateDetailsHandler;
import com.kp.cms.handlers.admin.TemplateHandler;
import com.kp.cms.to.admin.ApplicantMarkDetailsTO;
import com.kp.cms.to.admin.CertificateDetailsTemplateTO;
import com.kp.cms.to.admin.CertificateDetailsTo;
import com.kp.cms.to.admin.CertificatePurposeTO;
import com.kp.cms.to.admin.EdnQualificationTO;
import com.kp.cms.to.admin.CertificateDetailsTemplateTO;
import com.kp.cms.to.admin.CertificateDetailsTemplateTO;
import com.kp.cms.to.admin.CertificateDetailsTemplateTO;
import com.kp.cms.to.admission.StudentQualifyExamDetailsTO;
import com.kp.cms.to.usermanagement.RolesTO;
import com.kp.cms.transactions.admin.ICertificateDetailsTransaction;
import com.kp.cms.transactionsimpl.admin.CertificateDetailsImpl;
import com.kp.cms.utilities.CommonUtil;

public class CertificateDetailsAction extends BaseDispatchAction
{
  private static final Log log=LogFactory.getLog(CertificateDetailsAction.class);
  ICertificateDetailsTransaction transaction = CertificateDetailsImpl.getInstance();
    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward initCertificateDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
        throws Exception{
        log.info("end of initStudentFeedBackQuestion method in CertificateDetailsAction");
        CertificateDetailsForm certificateDetailsForm = (CertificateDetailsForm)form;
        String formName = mapping.getName();
        request.getSession().setAttribute("formName", formName);
        certificateDetailsForm.reset();
        setRequestedDataToForm(certificateDetailsForm);
        log.debug("Leaving initCertificateDetails");
        return mapping.findForward(CMSConstants.CERTIFICATE_DETAILS);
    }

    /**
     * @param studentFeedBackQuestionForm
     * @throws Exception
     */
    private void setRequestedDataToForm(CertificateDetailsForm certificateDetailsForm) throws Exception{
    	List<CertificateDetailsTo> questionList = CertificateDetailsHandler.getInstance().getCertificateList();
    	certificateDetailsForm.setCertificateList(questionList);
    }

    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward addCertificateDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
        throws Exception{
        CertificateDetailsForm certificateDetailsForm = (CertificateDetailsForm)form;
        setUserId(request, certificateDetailsForm);
        ActionMessages messages = new ActionMessages();
        ActionErrors errors = certificateDetailsForm.validate(mapping, request);
        errors=validatefees(certificateDetailsForm,errors);
        HttpSession session = request.getSession();
        validateCertificateDetails(certificateDetailsForm,errors);
        if(errors.isEmpty())
        {
            try
            {
                boolean isAdded = false;
                boolean isDuplicate =CertificateDetailsHandler.getInstance().duplicateCheck(certificateDetailsForm, errors, session);
                if(!isDuplicate)
                {
                    isAdded = CertificateDetailsHandler.getInstance().addcertificateDetails(certificateDetailsForm,"Add");
                    if(isAdded)
                    {
                        messages.add("messages", new ActionMessage("knowledgepro.certifacatedetails.addsuccess"));
                        saveMessages(request, messages);
                        setRequestedDataToForm(certificateDetailsForm);
                        certificateDetailsForm.reset();
                    } else
                    {
                        errors.add("error", new ActionError("knowledgepro.certifacatedetails.addfailure"));
                        addErrors(request, errors);
                        certificateDetailsForm.reset();
                    }
                } else
                {
                    addErrors(request, errors);
                }
            }
            catch(Exception exception)
            {
                log.error("Error occured in Certificate Details Entry Action", exception);
                String msg = super.handleApplicationException(exception);
                certificateDetailsForm.setErrorMessage(msg);
                certificateDetailsForm.setErrorStack(exception.getMessage());
                return mapping.findForward(CMSConstants.ERROR_PAGE);
            }
        } else
        {
            saveErrors(request, errors);
            setRequestedDataToForm(certificateDetailsForm);
            return mapping.findForward(CMSConstants.CERTIFICATE_DETAILS);
        }
        return mapping.findForward(CMSConstants.CERTIFICATE_DETAILS);
    }
	/**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward editCertificateDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
        throws Exception
    {
    	CertificateDetailsForm certificateDetailsForm = (CertificateDetailsForm)form;
        ActionErrors errors = certificateDetailsForm.validate(mapping, request);
        validateCertificateDetails(certificateDetailsForm,errors);
        log.debug("Entering certificateDetailsForm ");
        try
        {
        	CertificateDetailsHandler.getInstance().editCertificateDetails(certificateDetailsForm);
            request.setAttribute("CertificateDetails", "edit");
            log.debug("Leaving editCertificateDetails ");
        }
        catch(Exception e)
        {
            log.error("error in editing FeedBackQuestion...", e);
            String msg = super.handleApplicationException(e);
            certificateDetailsForm.setErrorMessage(msg);
            certificateDetailsForm.setErrorStack(e.getMessage());
            return mapping.findForward(CMSConstants.ERROR_PAGE);
        }
        return mapping.findForward(CMSConstants.CERTIFICATE_DETAILS);
    }

    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward updateCertificateDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
        throws Exception
    {
    	log.debug("Enter: updateCertificateDetails Action");
    	CertificateDetailsForm certificateDetailsForm = (CertificateDetailsForm)form;
        HttpSession session=request.getSession();
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = certificateDetailsForm.validate(mapping, request);
		errors=validatefees(certificateDetailsForm,errors);
		validateCertificateDetails(certificateDetailsForm,errors);
		boolean isUpdated = false;
		if(errors.isEmpty()){
			try {
				// This condition works when reset button will click in update mode
				if (isCancelled(request)) {
					certificateDetailsForm.reset();
			        String formName = mapping.getName();
			        request.getSession().setAttribute("formName", formName);
			        CertificateDetailsHandler.getInstance().editCertificateDetails(certificateDetailsForm);
			        request.setAttribute("CertificateDetails", "edit");
			        return mapping.findForward(CMSConstants.CERTIFICATE_DETAILS);
				}
				setUserId(request, certificateDetailsForm);
				 boolean isDuplicate =CertificateDetailsHandler.getInstance().duplicateCheck(certificateDetailsForm, errors, session);
				if(!isDuplicate){
					isUpdated = CertificateDetailsHandler.getInstance().updatecertificateDetails(certificateDetailsForm, "Edit");
				if (isUpdated) {
                    ActionMessage message = new ActionMessage("knowledgepro.CertificateDetails.updatesuccess");
                    messages.add("messages", message);
                    saveMessages(request, messages);
                    certificateDetailsForm.reset();
                } else {
                    errors.add("error", new ActionError("knowledgepro.CertificateDetails.updatefailure"));
                    addErrors(request, errors);
                    certificateDetailsForm.reset();
                }}
				else{
	                request.setAttribute("CertificateDetails", "edit");
	                addErrors(request, errors);
	            }
			} catch (Exception e) {
	            log.error("Error occured in edit valuatorcharges", e);
	            String msg = super.handleApplicationException(e);
	            certificateDetailsForm.setErrorMessage(msg);
	            certificateDetailsForm.setErrorStack(e.getMessage());
	            return mapping.findForward(CMSConstants.ERROR_PAGE);
	        }}else{
				saveErrors(request, errors);
				setRequestedDataToForm(certificateDetailsForm);
		        request.setAttribute("CertificateDetails", "edit");
				return mapping.findForward(CMSConstants.CERTIFICATE_DETAILS);
			}
		setRequestedDataToForm(certificateDetailsForm);
        return mapping.findForward(CMSConstants.CERTIFICATE_DETAILS);
    }

    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward deleteCertificateDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
        throws Exception {
        CertificateDetailsForm certificateDetailsForm = (CertificateDetailsForm)form;
        ActionMessages messages = new ActionMessages();
        try
        {
            boolean isDeleted = CertificateDetailsHandler.getInstance().deleteCertificateDetails(certificateDetailsForm);
            if(isDeleted)
            {
                ActionMessage message = new ActionMessage("knowledgepro.certificate.deletesuccess");
                messages.add("messages", message);
                saveMessages(request, messages);
            } else
            {
                ActionMessage message = new ActionMessage("knowledgepro.studentFeedBack.deletefailure");
                messages.add("messages", message);
                saveMessages(request, messages);
            }
            certificateDetailsForm.reset();
            setRequestedDataToForm(certificateDetailsForm);
        }
        catch(Exception e)
        {
            log.error("error submit valuatorCharges...", e);
            if(e instanceof ApplicationException)
            {
                String msg = super.handleApplicationException(e);
                certificateDetailsForm.setErrorMessage(msg);
                certificateDetailsForm.setErrorStack(e.getMessage());
                return mapping.findForward(CMSConstants.ERROR_PAGE);
            } else
            {
                String msg = super.handleApplicationException(e);
                certificateDetailsForm.setErrorMessage(msg);
                certificateDetailsForm.setErrorStack(e.getMessage());
                return mapping.findForward(CMSConstants.ERROR_PAGE);
            }
        }
        return mapping.findForward(CMSConstants.CERTIFICATE_DETAILS);
    }

    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward reActivateCertificateDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
        throws Exception
    {
        CertificateDetailsForm certificateDetailsForm = (CertificateDetailsForm)form;
        ActionErrors errors = new ActionErrors();
        ActionMessages messages = new ActionMessages();
        HttpSession session = request.getSession();
        try
        {
            setUserId(request, certificateDetailsForm);
            String userId = certificateDetailsForm.getUserId();
            String duplicateId = session.getAttribute("ReactivateId").toString();
            certificateDetailsForm.setId(Integer.parseInt(duplicateId));
            boolean isReactivate = CertificateDetailsHandler.getInstance().reActivateCertificateDetails(certificateDetailsForm, userId);
            if(isReactivate)
            {
                messages.add("messages", new ActionMessage("knowledgepro.certificatedetails.reactivatesuccess"));
                setRequestedDataToForm(certificateDetailsForm);
                certificateDetailsForm.reset();
                saveMessages(request, messages);
            } else
            {
            	setRequestedDataToForm(certificateDetailsForm);
                errors.add("error", new ActionError("knowledgepro.certificatedetails.reactivatefailure"));
                addErrors(request, errors);
            }
        }
        catch(Exception e)
        {
            log.error("Error occured in reActivateCertificateDetails of Action", e);
            String msg = super.handleApplicationException(e);
            certificateDetailsForm.setErrorMessage(msg);
            certificateDetailsForm.setErrorStack(e.getMessage());
            return mapping.findForward(CMSConstants.ERROR_PAGE);
        }
        return mapping.findForward(CMSConstants.CERTIFICATE_DETAILS);
    }
    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward forwardAssignToroles(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
    {
    	CertificateDetailsForm certificateDetailsForm = (CertificateDetailsForm)form;
    	  String formName = mapping.getName();
          request.getSession().setAttribute("formName", formName);
          setAssignTorolesDataToForm(certificateDetailsForm);
          certificateDetailsForm.clear();
          //setRolesToForm(certificateDetailsForm);
    	log.info("Leaving into reActivateCertificateDetails of Action");
        return mapping.findForward(CMSConstants.ASSIGN_TO_ROLES);	
    }
    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward forwardAssignPurpose(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
    {
    	log.info("Entering reActivateCertificateDetails Action");
    	CertificateDetailsForm certificateDetailsForm = (CertificateDetailsForm)form;
    	  String formName = mapping.getName();
          request.getSession().setAttribute("formName", formName);
          List<CertificatePurposeTO> purposeList = CertificateDetailsHandler.getInstance().getAssignPurpose(certificateDetailsForm);
  		  certificateDetailsForm.setAssignPurposeList(purposeList);
          certificateDetailsForm.clear();
          //setRolesToForm(certificateDetailsForm);
    	log.info("Leaving into reActivateCertificateDetails of Action");
        return mapping.findForward(CMSConstants.ASSIGN_PURPOSE);	
    }
    
	/**
	 * @param certificateDetailsForm
	 * @throws Exception
	 */
	private void setAssignTorolesDataToForm(CertificateDetailsForm certificateDetailsForm) throws Exception{
		log.debug("inside Assign To roles");
		List<RolesTO> rolesList = CertificateDetailsHandler.getInstance().getAssignRoles(certificateDetailsForm);
		certificateDetailsForm.setAssignToRolesList(rolesList);
		int halfLength = 0;
		int totLength = rolesList.size();
		if (totLength % 2 == 0) {
			halfLength = totLength / 2;
		} else {
			halfLength = (totLength / 2) + 1;
		}
		certificateDetailsForm.setHalfLength(halfLength);
		
		
	}

	/**
	 * @param certificateDetailsForm
	 */
	/*private void setRolesToForm(CertificateDetailsForm certificateDetailsForm) throws Exception{
		List<RolesTO> rolesList = CertificateDetailsHandler.getInstance().getAssignRoles(certificateDetailsForm);
		certificateDetailsForm.setAssignToRolesList(rolesList);
	 }*/
    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward saveCertificateDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception{
		
		CertificateDetailsForm certificateDetailsForm = (CertificateDetailsForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		validateCertificateDetails(certificateDetailsForm,errors);
		 setUserId(request, certificateDetailsForm);

			try {
				boolean flag=false;
				flag=CertificateDetailsHandler.getInstance().saveCertificateDetails(certificateDetailsForm);
				if(!flag && certificateDetailsForm.getCountt()==0){
					messages.add("messages",new ActionMessage(CMSConstants.NOT_SELECTED_ASSIGNROLE));
					saveMessages(request, messages);
				}else{
					if(certificateDetailsForm.getCountt()==0){
						errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.NOT_SELECTED_ASSIGNROLE));
						saveErrors(request,errors);
						messages.add("messages",new ActionMessage(CMSConstants.CERTIFICATE_REMOVE_SUCCESSFULLY));
						saveMessages(request, messages);
					}else if(certificateDetailsForm.getOldCountt()>certificateDetailsForm.getCountt()){
						messages.add("messages",new ActionMessage(CMSConstants.CERTIFICATE_REMOVE_SUCCESSFULLY));
						saveMessages(request, messages);
					}else{
						messages.add("messages",new ActionMessage(CMSConstants.CERTIFICATE_ADDED_SUCCESSFULLY));
						saveMessages(request, messages);
					}
				}
				certificateDetailsForm.clear();
				setRequestedDataToForm(certificateDetailsForm);
				saveMessages(request, messages);
			} catch (Exception e) {
				log.debug(e.getMessage());
				errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.CERTIFICATE_ADDING_FAILED));
	    		saveErrors(request,errors);
	    		return mapping.findForward(CMSConstants.CERTIFICATE_DETAILS);
			}
			log.info("Leaving into saveAttendance");
			return mapping.findForward(CMSConstants.CERTIFICATE_DETAILS);
		}
    
    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward saveCertificateDetailsPurpose(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception{
		
		CertificateDetailsForm certificateDetailsForm = (CertificateDetailsForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		validateCertificateDetails(certificateDetailsForm,errors);
		 setUserId(request, certificateDetailsForm);

			try {
				boolean flag=false;
				flag=CertificateDetailsHandler.getInstance().saveCertificateDetailsPurpose(certificateDetailsForm);
				if(!flag && certificateDetailsForm.getCountPurpose()==0){
					messages.add("messages",new ActionMessage(CMSConstants.NOT_SELECTED_ASSIGN_PURPOSE));
					saveMessages(request, messages);
				}else{
					if(certificateDetailsForm.getCountt()==0){
						errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.NOT_SELECTED_ASSIGN_PURPOSE));
						saveErrors(request,errors);
						messages.add("messages",new ActionMessage(CMSConstants.CERTIFICATE_PURPOSE_REMOVE_SUCCESSFULLY));
						saveMessages(request, messages);
					}else if(certificateDetailsForm.getOldCountt()>certificateDetailsForm.getCountt()){
						messages.add("messages",new ActionMessage(CMSConstants.CERTIFICATE_PURPOSE_REMOVE_SUCCESSFULLY));
						saveMessages(request, messages);
					}else{
						messages.add("messages",new ActionMessage(CMSConstants.CERTIFICATE_PURPOSE_ADDED_SUCCESSFULLY));
						saveMessages(request, messages);
					}
				}
				certificateDetailsForm.clear();
				setRequestedDataToForm(certificateDetailsForm);
				saveMessages(request, messages);
			} catch (Exception e) {
				log.debug(e.getMessage());
				errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.CERTIFICATE_PURPOSE_ADDING_FAILED));
	    		saveErrors(request,errors);
	    		return mapping.findForward(CMSConstants.CERTIFICATE_DETAILS);
			}
			log.info("Leaving into saveAttendance");
			return mapping.findForward(CMSConstants.CERTIFICATE_DETAILS);
		}
    
    public ActionForward saveCertificateDetailsTemplate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception{
    	
		CertificateDetailsForm certificateDetailsForm = (CertificateDetailsForm)form;
		String templateString = request.getParameter(CMSConstants.EDITOR_DEFAULT);
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		validateCertificateDetails(certificateDetailsForm,errors);
		 setUserId(request, certificateDetailsForm);
		 validateGroupData(templateString,errors,certificateDetailsForm);
			
			try {
				if(errors.isEmpty()) {
					certificateDetailsForm.setTemplateDescription(templateString);
		 
				boolean flag=false;
				String templateName;
				templateName = transaction.getCertificateName(certificateDetailsForm.getCertificateDetailsId());
				if(templateName!=null && !templateName.isEmpty())
					certificateDetailsForm.setSelectedCertificateName(templateName);
				if(CertificateDetailsHandler.getInstance().checkDuplicateCertificateTemplate(certificateDetailsForm.getSelectedCertificateName()).size()>=1){
					errors.add(CMSConstants.ERRORS,new ActionError(CMSConstants.TEMPLATE_ALREADYEXIST));
					saveErrors(request,errors);
		    		return mapping.findForward(CMSConstants.STUDENT_CERTIFICATE_TEMPLATE);
				}
				else
				{
				flag=CertificateDetailsHandler.getInstance().saveCertificateDetailsTemplate(certificateDetailsForm);
				}
				if(flag)
				{
						messages.add("messages",new ActionMessage(CMSConstants.CERTIFICATE_TEMPLATE_ADD_SUCCESSFULLY));
						saveMessages(request, messages);
				}
				//}
				certificateDetailsForm.clear();
				setRequestedDataToForm(certificateDetailsForm);
				saveMessages(request, messages);
				}else {
					saveErrors(request, errors);
				}
			} catch (Exception e) {
				log.debug(e.getMessage());
				errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.CERTIFICATE_TEMPLATE_ADDING_FAILED));
	    		saveErrors(request,errors);
	    		return mapping.findForward(CMSConstants.CERTIFICATE_DETAILS);
			}
			log.info("Leaving into saveAttendance");
			return mapping.findForward(CMSConstants.CERTIFICATE_DETAILS);
		}

    private ActionErrors validatefees(CertificateDetailsForm certificateDetailsForm, ActionErrors errors) {
    	if(certificateDetailsForm.getFees()!=null && !certificateDetailsForm.getFees().isEmpty()){
			if(!CommonUtil.isValidDecimal(certificateDetailsForm.getFees())){
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.CERTIFICATE_DETAILS_AMOUNT_INTEGER));
			}
		}
		return errors;
	}
    
    private void validateCertificateDetails(CertificateDetailsForm certificateDetailsForm,ActionErrors errors) throws Exception {
		if(certificateDetailsForm.getDescription()!=null && !certificateDetailsForm.getDescription().isEmpty() && certificateDetailsForm.getDescription().length()>10000){
			errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.cc.description.length.modified"));
			
		}
	}
    public ActionForward initGroupTemplate(ActionMapping mapping,
			   ActionForm form, HttpServletRequest request,
			   HttpServletResponse response) throws Exception 
	{
    	CertificateDetailsForm certificateDetailsForm = (CertificateDetailsForm)form;
    	setUserId(request, certificateDetailsForm);
    	cleanUpData(certificateDetailsForm);
    	loadGroupTemplateList(certificateDetailsForm);
    	return mapping.findForward(CMSConstants.STUDENT_CERTIFICATE_TEMPLATE);
    }	
    
    public void loadGroupTemplateList(CertificateDetailsForm certificateDetailsForm) throws Exception{
    	String templateName = transaction.getCertificateName(certificateDetailsForm.getCertificateDetailsId());
		if(templateName!=null && !templateName.isEmpty())
			certificateDetailsForm.setSelectedCertificateName(templateName);
    	List<CertificateDetailsTemplateTO> templateTOlist =CertificateDetailsHandler.getInstance().getGroupTemplateList(certificateDetailsForm.getSelectedCertificateName());
    	certificateDetailsForm.setTemplateList(templateTOlist);
	}
    public ActionForward helpMenuCertificate(ActionMapping mapping,
			   ActionForm form, HttpServletRequest request,
			   HttpServletResponse response) throws Exception {
   return mapping.findForward(CMSConstants.CERTIFICATE_TEMPLATE_HELPTEMPLATE);
   }
    
	public ActionForward createGroupTemplate(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CertificateDetailsForm certificateDetailsForm = (CertificateDetailsForm)form;
		try {
			String description = certificateDetailsForm.getTemplateDescription();
			certificateDetailsForm.setTemplateDescription(description);
		} catch (Exception e) {
			log.error("error in createGroupTemplate...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				certificateDetailsForm.setErrorMessage(msg);
				certificateDetailsForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		log.info("exit submitSemesterEditMark...");
		return mapping.findForward(CMSConstants.CERTIFICATE_DETAILS);
	}
				
	
	
			/**
			* 
			* @param mapping
			* @param form
			* @param request
			* @param response
			* @return this will update the template to database.
			* @throws Exception
			*/
			public ActionForward updateGroupTemplate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
			
			String templateString = request.getParameter(CMSConstants.EDITOR_DEFAULT);
			ActionMessages messages = new ActionMessages();
			CertificateDetailsForm templateForm = (CertificateDetailsForm)form;
			ActionErrors errors = templateForm.validate(mapping, request);
			if(templateString == null || templateString.length() == 0){
			errors.add(CMSConstants.ERRORS,new ActionError(CMSConstants.TEMPLATE_CANNOTBEEMPTY));
			}
			
			try {
			if(errors.isEmpty()) {
			templateForm.setTemplateDescription(templateString);
			if(CertificateDetailsHandler.getInstance().updateGroupTemplate(templateForm)){
			ActionMessage message = new ActionMessage(CMSConstants.TEMPLATE_UPDATESUCCESS);
			messages.add(CMSConstants.MESSAGES, message);
			loadGroupTemplateList(templateForm);
			saveMessages(request, messages);
			templateForm.clear();
			}
			} else {
			saveErrors(request, errors);
			request.setAttribute(CMSConstants.OPERATION,CMSConstants.EDIT_OPERATION);
			}
			} catch (Exception e) {
			log.debug(e.getMessage());
			errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.TEMPLATE_UPDATEFAILED));
			saveErrors(request,errors);
			}
			log.debug("Leaving updateTemplate ");
			return mapping.findForward(CMSConstants.STUDENT_CERTIFICATE_TEMPLATE);
			}
			
			/**
			* @param templateString
			* @param errors
			*/
			public void validateGroupData(String templateString,ActionErrors errors,CertificateDetailsForm templateForm) throws Exception{
			if(templateString == null || templateString.length() == 0){
			errors.add(CMSConstants.ERRORS,new ActionError(CMSConstants.TEMPLATE_CANNOTBEEMPTY));
			}
			if(CertificateDetailsHandler.getInstance().checkDuplicateCertificateTemplate(templateForm.getSelectedCertificateName()).size()>=1){
			errors.add(CMSConstants.ERRORS,new ActionError(CMSConstants.TEMPLATE_ALREADYEXIST));
			}
			}
			
			
			
			/**
			* Method to display selected template description in view mode
			* @param mapping
			* @param form
			* @param request
			* @param response
			* @return
			* @throws Exception
			*/
			public ActionForward viewTemplateDescription(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
			
			CertificateDetailsForm templateForm = (CertificateDetailsForm) form;
			List<CertificateDetailsTemplateTO> groupTemplateList = CertificateDetailsHandler.getInstance().getGroupTemplateList(templateForm.getSelectedCertificateName());
			Iterator<CertificateDetailsTemplateTO> iterator = groupTemplateList.iterator();
			CertificateDetailsTemplateTO groupTemplateTO;

			while (iterator.hasNext()) {
				groupTemplateTO = (CertificateDetailsTemplateTO) iterator.next();
				templateForm.setTemplateDescription(groupTemplateTO.getTemplateDescription());
			}
			return mapping.findForward(CMSConstants.VIEW_TEMPLATE_DESCRIPTION_CERT);
			}
			
			
							
			public ActionForward editGroupTemplate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
				
				CertificateDetailsForm templateForm = (CertificateDetailsForm)form;
				List<CertificateDetailsTemplateTO> groupTemplateList = CertificateDetailsHandler.getInstance().getGroupTemplateList(templateForm.getSelectedCertificateName());
				Iterator<CertificateDetailsTemplateTO> iterator = groupTemplateList.iterator();
				CertificateDetailsTemplateTO groupTemplateTO;
				//templateForm.clear();
				while (iterator.hasNext()) {
				groupTemplateTO = (CertificateDetailsTemplateTO) iterator.next();
				templateForm.setTemplateName(groupTemplateTO.getTemplateName());
				templateForm.setTemplateDescription(groupTemplateTO.getTemplateDescription());
				templateForm.setCertificateDetailsId(groupTemplateTO.getCertificateId().getId());
				}
				loadGroupTemplateList(templateForm);
				request.setAttribute(CMSConstants.OPERATION,CMSConstants.EDIT_OPERATION);
				return mapping.findForward(CMSConstants.STUDENT_CERTIFICATE_TEMPLATE);
				}
				
			public void cleanUpData(CertificateDetailsForm certificateDetailsForm) throws Exception{
				if(certificateDetailsForm!=null)
				{
					certificateDetailsForm.setTemplateDescription(null);
					certificateDetailsForm.setTemplateName(null);
					certificateDetailsForm.setTemplateList(null);
				}
			}
			
			public ActionForward deleteGroupTemplate(ActionMapping mapping, ActionForm form,
					HttpServletRequest request, HttpServletResponse response)
					throws Exception {
			
				ActionMessages errors = new ActionErrors();
				ActionMessages messages = new ActionMessages();
				CertificateDetailsForm templateForm = (CertificateDetailsForm)form;
				try {
					if(errors.isEmpty()) {
						if(CertificateDetailsHandler.getInstance().deleteGroupTemplate(Integer.parseInt(templateForm.getTemplateId()))){
							ActionMessage message = new ActionMessage(CMSConstants.TEMPLATE_DELETESUCCESS);
							messages.add(CMSConstants.MESSAGES, message);
							loadGroupTemplateList(templateForm);
							saveMessages(request, messages);
							templateForm.clear();
						}
					} else {
						saveErrors(request, errors);
					}
				} catch (Exception e) {
					log.debug(e.getMessage());
					errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.TEMPLATE_DELETEFAILED));
		    		saveErrors(request,errors);
				}
				log.debug("Leaving updateTemplate ");
				return mapping.findForward(CMSConstants.STUDENT_CERTIFICATE_TEMPLATE);
			}
				
				

}
