package com.kp.cms.actions.employee;

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
import org.apache.struts.upload.FormFile;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.bo.admin.EmpHrPolicy;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.employee.HrPolicyForm;
import com.kp.cms.handlers.employee.HrPolicyHandler;

public class HrPolicyAction extends BaseDispatchAction{
	
	private static final Log log = LogFactory.getLog(HrPolicyAction.class);
	
	public ActionForward initHRPolicy(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("entering of initHRPolicy in HrPolicyAction class. ");
		HrPolicyForm hrPolicyForm = (HrPolicyForm)form;
		setFormData(hrPolicyForm);
		log.info("end of initHRPolicy in HrPolicyAction class. ");
		return mapping.findForward(CMSConstants.POLICY_PATH);
	}

	/**
	 * This method is used to add the HR policy to database.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	
	public ActionForward addHRPolicy(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("entering of addHRPolicy in HrPolicyAction class. ");
		HrPolicyForm hrPolicyForm = (HrPolicyForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = hrPolicyForm.validate(mapping, request);
		validateHRPolicyName(hrPolicyForm,errors);
		try	{
		if (errors.isEmpty()) {
			setUserId(request, hrPolicyForm);
			FormFile myFile = hrPolicyForm.getFormFile();
			String contentType = myFile.getContentType();
			byte[] fileData    = myFile.getFileData();
			String fileName = myFile.getFileName();
			
			boolean isAdded = HrPolicyHandler.getInstance().saveHrPolicy(hrPolicyForm,contentType, fileData, fileName);
			if(isAdded){
				ActionMessage message = new ActionMessage(CMSConstants.POLICY_ADD_SUCCESS);
				messages.add("messages", message);
    			saveMessages(request, messages);
    			hrPolicyForm.reset(mapping, request);
			}			
		} else {
			addErrors(request, errors);
			}
		} catch (Exception exception) {	
			String msg = super.handleApplicationException(exception);
			hrPolicyForm.setErrorMessage(msg);
			hrPolicyForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		setFormData(hrPolicyForm);
		log.info("end of deleteHRPolicy in HrPolicyAction class. ");
		return mapping.findForward(CMSConstants.POLICY_PATH);
	}

	/**
	 * validating the hr policy name is existed r not
	 * @param hrPolicyForm
	 * @param errors
	 */
	private void validateHRPolicyName(HrPolicyForm hrPolicyForm,
			ActionErrors errors) throws Exception {
		if(hrPolicyForm.getPolicyName()!=null && !hrPolicyForm.getPolicyName().isEmpty()){
			boolean isDuplicate=HrPolicyHandler.getInstance().isDuplicateHrpolicyName(hrPolicyForm.getPolicyName());
			if(isDuplicate){
				errors.add("error", new ActionError("knowledgepro.employee.hrPolicy.already.exists"));
			}
		}
		
	}

	/**
	 * This method is used to delete the HR policy from database.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	
	public ActionForward deleteHRPolicy(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("entering of deleteHRPolicy in HrPolicyAction class. ");
		HrPolicyForm hrPolicyForm = (HrPolicyForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = hrPolicyForm.validate(mapping, request);
		try	{
		if (errors.isEmpty()) {
			setUserId(request, hrPolicyForm);
			int policyId = Integer.parseInt(hrPolicyForm.getPolicyId());
			boolean isDeleted = HrPolicyHandler.getInstance().deleteHrPolicy(policyId);
			if(isDeleted){
				ActionMessage message = new ActionMessage(CMSConstants.POLICY_DELETE_SUCCESS);
				messages.add("messages", message);
    			saveMessages(request, messages);
    			hrPolicyForm.reset(mapping, request);
			}			
		} else {
			addErrors(request, errors);
			}
		} catch (Exception exception) {	
			String msg = super.handleApplicationException(exception);
			hrPolicyForm.setErrorMessage(msg);
			hrPolicyForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		setFormData(hrPolicyForm);
		log.info("end of deleteHRPolicy in HrPolicyAction class. ");
		return mapping.findForward(CMSConstants.POLICY_PATH);
	}

	/**
	 * This method is used to set the required data to form
	 * @param hrPolicyForm
	 * @throws Exception
	 */
	
	private void setFormData(HrPolicyForm hrPolicyForm) throws Exception {
		log.info("entering of setFormData in HrPolicyAction class. ");
		List<EmpHrPolicy> policiesList = HrPolicyHandler.getInstance().getAllHRPolicies();
		if(policiesList != null && policiesList.size() != 0){
			hrPolicyForm.setPoliciesList(policiesList);
		}
		log.info("exit of setFormData in HrPolicyAction class. ");
	}
	
	/**
	 * This method is used to load all the HR policies from database.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	
	public ActionForward initHRPoliciesList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("entering of initHRPoliciesList in HrPolicyAction class. ");
		HrPolicyForm hrPolicyForm = (HrPolicyForm)form;
		setListToForm(hrPolicyForm);
		log.info("exit of initHRPoliciesList in HrPolicyAction class. ");
		return mapping.findForward(CMSConstants.POLICIES_LIST_PATH);
	}

	/**
	 * This method is used to set the required data to form.
	 * @param hrPolicyForm
	 * @throws Exception
	 */
	
	private void setListToForm(HrPolicyForm hrPolicyForm) throws Exception {
		log.info("entering of setListToForm in HrPolicyAction class. ");
		List<EmpHrPolicy> policiesList = HrPolicyHandler.getInstance().getAllHRPolicies();
		if(policiesList != null && policiesList.size() != 0){
			hrPolicyForm.setPoliciesList(policiesList);
		}
		log.info("exit of setListToForm in HrPolicyAction class. ");
	}
}